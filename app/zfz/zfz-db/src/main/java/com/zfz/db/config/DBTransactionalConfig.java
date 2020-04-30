package com.zfz.db.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import javax.sql.DataSource;
import java.util.Collections;

/**
 * 事务配置,默认 : 开启
 * <p>
 * 如何配置? yml中示例如下
 * <pre>
 *      pm:
 *        transactional:
 *          enabled: true # 默认已开启
 *          customize-transaction-bean-names: *Client # 多个以都和 ',' 分隔
 *          customize-read-only-method-rule-transaction-attributes: *exist # 多个以都和 ',' 分隔
 *          customize-required-method-rule-transaction-attributes: pay*,do*,build* # 多个以都和 ',' 分隔
 * </pre>
 *
 * @author : tongding.wu
 * @date : 2017/9/8
 */
@Configuration
@Slf4j
public class DBTransactionalConfig {


	private static final String CUSTOMIZE_TRANSACTION_INTERCEPTOR_NAME = "customizeTransactionInterceptor";
	/**
	 * 默认只对 "*Service" , "*ServiceImpl" Bean 进行事务处理,"*"表示模糊匹配, 比如 : userService,orderServiceImpl
	 */
	private static final String[] DEFAULT_TRANSACTION_BEAN_NAMES = {"*Service", "*ServiceImpl"};

	private final String BEAN_CLASS_PREFIX = "com.pm.";
	/**
	 * 可传播事务配置
	 */
	private static final String[] DEFAULT_REQUIRED_METHOD_RULE_TRANSACTION_ATTRIBUTES = {
			"do*",
			"add*",
			"save*",
			"insert*",
			"delete*",
			"update*",
			"edit*",
			"batch*",
			"create*",
			"remove*",
	};
	/**
	 * 默认的只读事务
	 */
	private static final String[] DEFAULT_READ_ONLY_METHOD_RULE_TRANSACTION_ATTRIBUTES = {
			"get*",
			"count*",
			"find*",
			"query*",
			"select*",
			"list*",
	};
	/**
	 * 自定义事务 BeanName 拦截
	 */
	private String[] customizeTransactionBeanNames = {};
	/**
	 * 自定义方法名的事务属性相关联,可以使用通配符(*)字符关联相同的事务属性的设置方法; 只读事务
	 */
	private String[] customizeReadOnlyMethodRuleTransactionAttributes = {};
	/**
	 * 自定义方法名的事务属性相关联,可以使用通配符(*)字符关联相同的事务属性的设置方法;
	 * 传播事务(默认的){@link org.springframework.transaction.annotation.Propagation#REQUIRED}
	 */
	private String[] customizeRequiredMethodRuleTransactionAttributes = {};

	/**
	 * 手动指定事物类型，项目做的比较大，添加的持久化依赖比较多，我们还是会选择人为的指定使用哪个事务管理器
	 *
	 * @param dataSource
	 * @return
	 * @EnableTransactionManagement 如果你添加的是 spring-boot-starter-jdbc 依赖，框架会默认注入 DataSourceTransactionManager 实例。如果你添加的是 spring-boot-starter-data-jpa 依赖，框架会默认注入 JpaTransactionManager 实例。
	 */
	@Bean(name = "transactionManager")
	public PlatformTransactionManager transactionManager(DataSource dataSource) {
		log.info("---transactionManager init");
		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
		return transactionManager;
	}

	/**
	 * 配置事务拦截器
	 *
	 * @param transactionManager : 事务管理器
	 */
	@Bean(CUSTOMIZE_TRANSACTION_INTERCEPTOR_NAME)
	public TransactionInterceptor customizeTransactionInterceptor(PlatformTransactionManager transactionManager) {
		NameMatchTransactionAttributeSource transactionAttributeSource = new NameMatchTransactionAttributeSource();
		RuleBasedTransactionAttribute readOnly = this.readOnlyTransactionRule();
		RuleBasedTransactionAttribute required = this.requiredTransactionRule();
		// 默认的只读事务配置
		for (String methodName : DEFAULT_READ_ONLY_METHOD_RULE_TRANSACTION_ATTRIBUTES) {
			transactionAttributeSource.addTransactionalMethod(methodName, readOnly);
		}
		// 默认的传播事务配置
		for (String methodName : DEFAULT_REQUIRED_METHOD_RULE_TRANSACTION_ATTRIBUTES) {
			transactionAttributeSource.addTransactionalMethod(methodName, required);
		}

		// 定制的只读事务配置
		for (String methodName : customizeReadOnlyMethodRuleTransactionAttributes) {
			transactionAttributeSource.addTransactionalMethod(methodName, readOnly);
		}
		// 定制的传播事务配置
		for (String methodName : customizeRequiredMethodRuleTransactionAttributes) {
			transactionAttributeSource.addTransactionalMethod(methodName, required);
		}


		TransactionInterceptor transactionInterceptor = new TransactionInterceptor(new PlatformTransactionManager() {

			@Override
			public TransactionStatus getTransaction(TransactionDefinition definition) throws TransactionException {

				if (log.isDebugEnabled()) {
					log.info("事物申请getTransaction->" + definition.getName());
				}
				return transactionManager.getTransaction(definition);
			}

			@Override
			public void commit(TransactionStatus status) throws TransactionException {
				transactionManager.commit(status);

				if (log.isDebugEnabled()) {
					log.info("事物提交");
				}
			}

			@Override
			public void rollback(TransactionStatus status) throws TransactionException {
				transactionManager.rollback(status);

				log.info("事物回滚");
			}
		}, transactionAttributeSource);

		return transactionInterceptor;
	}

	/**
	 * 配置事务拦截
	 * <p>
	 * {@link #customizeTransactionInterceptor(PlatformTransactionManager)}
	 */
	@Bean
	public BeanNameAutoProxyCreator customizeTransactionBeanNameAutoProxyCreator() {

		BeanNameAutoProxyCreator beanNameAutoProxyCreator = new BeanNameAutoProxyCreator() {

			@SuppressWarnings("rawtypes")
			protected Object createProxy(Class beanClass, String beanName, Object[] specificInterceptors,
			                             TargetSource targetSource) {
				try {
					Object target = targetSource.getTarget();
					if (beanClass.getName().startsWith(BEAN_CLASS_PREFIX)) {

						return super.createProxy(beanClass, beanName, specificInterceptors, targetSource);
					} else {
						log.info("BeanNameAutoProxyCreator忽略bean:" + beanName);
						return target;
					}

				} catch (Throwable ex) {
					log.error(ex.getMessage(), ex);
					return super.createProxy(beanClass, beanName, specificInterceptors, targetSource);
				}
			}
		};

		// 设置定制的事务拦截器
		beanNameAutoProxyCreator.setInterceptorNames(CUSTOMIZE_TRANSACTION_INTERCEPTOR_NAME);

        /*
        // 默认
        for (String defaultTransactionBeanNameSuffix : DEFAULT_TRANSACTION_BEAN_NAMES) {
            beanNameAutoProxyCreator.setBeanNames(defaultTransactionBeanNameSuffix);
        }
        // 定制
        for (String customizeTransactionBeanName : customizeTransactionBeanNames) {
            beanNameAutoProxyCreator.setBeanNames(customizeTransactionBeanName);
        }
        */

		beanNameAutoProxyCreator.setBeanNames(ArrayUtils.addAll(DEFAULT_TRANSACTION_BEAN_NAMES, customizeTransactionBeanNames));

		beanNameAutoProxyCreator.setProxyTargetClass(true);
		return beanNameAutoProxyCreator;
	}

	/**
	 * 支持当前事务;如果不存在创建一个新的
	 * {@link org.springframework.transaction.annotation.Propagation#REQUIRED}
	 */
	private RuleBasedTransactionAttribute requiredTransactionRule() {
		RuleBasedTransactionAttribute required = new RuleBasedTransactionAttribute();
		required.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
		required.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		required.setTimeout(TransactionDefinition.TIMEOUT_DEFAULT);
		return required;
	}

	/**
	 * 只读事务
	 * {@link org.springframework.transaction.annotation.Propagation#NOT_SUPPORTED}
	 */
	private RuleBasedTransactionAttribute readOnlyTransactionRule() {
		RuleBasedTransactionAttribute readOnly = new RuleBasedTransactionAttribute();
		readOnly.setReadOnly(true);
		readOnly.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
		return readOnly;
	}


}
