package com.zfz.common.util;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * 基于Jackson的JSON转换工具类
 */
@Slf4j
public class JacksonUtils {

	public final static ObjectMapper om;

	static {
		om = createNewOm();
	}

	public static ObjectMapper createNewOm() {
		ObjectMapper om = new ObjectMapper();
		;
		// 对象的所有字段全部列入，还是其他的选项，可以忽略null等
		om.setSerializationInclusion(Include.ALWAYS);
		// 设置Date类型的序列化及反序列化格式
		om.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

		// 忽略空Bean转json的错误
		om.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		// 忽略未知属性，防止json字符串中存在，java对象中不存在对应属性的情况出现错误
		om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		// 枚举处理方式
		om.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
		// om.configure(SerializationFeature.WRITE_ENUMS_USING_INDEX, true);

		// 大小写脱敏 默认为false  需要改为true
		om.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, false);

		// 非规范化输出。
		//om.configure(MapperFeature.USE_STD_BEAN_NAMING, true);

		// 注册一个时间序列化及反序列化的处理模块，用于解决jdk8中localDateTime等的序列化问题
		om.registerModule(new JavaTimeModule());

		/**
		 * 序列换成json时,将所有的long变成string
		 * 因为js中得数字类型不能包含所有的java long值
		 */
		SimpleModule module = new SimpleModule();
		module.addSerializer(Long.class, ToStringSerializer.instance);
		module.addSerializer(Long.TYPE, ToStringSerializer.instance);
		module.addSerializer(BigInteger.class, ToStringSerializer.instance);

		om.registerModule(module);
		return om;
	}

	/**
	 * 对象 => json字符串
	 *
	 * @param obj 源对象
	 */
	public static <T> String toJSONString(T obj) {

		String json = null;
		if (obj != null) {
			try {
				json = om.writeValueAsString(obj);
			} catch (JsonProcessingException e) {
				log.warn(e.getMessage(), e);
				throw new IllegalArgumentException(e.getMessage());
			}
		}
		return json;
	}


	/**
	 * json字符串 => jsonNode
	 *
	 * @param json 源json串
	 * @return JsonNode
	 */
	public static JsonNode toJSONNode(String json) {
		try {
			return om.readTree(json);
		} catch (IOException e) {
			log.warn(e.getMessage(), e);
			throw new IllegalArgumentException(e.getMessage());
		}

	}

	public static <T> T toObject(JsonNode jsonNode, Class<T> clazz) {
		if (jsonNode == null) {
			return null;
		}
		try {
			return om.treeToValue(jsonNode, clazz);
		} catch (JsonProcessingException e) {
			log.warn(e.getMessage(), e);
			throw new RuntimeException(e.getMessage());
		}
	}


	public static Map toMap(JsonNode jsonNode) {
		if (jsonNode == null) {
			return null;
		}
		return om.convertValue(jsonNode, Map.class);

	}

	/**
	 * json字符串 => 对象
	 *
	 * @param json  源json串
	 * @param clazz 对象类
	 * @param <T>   泛型
	 */
	public static <T> T toObject(String json, Class<T> clazz) {

		return toObject(json, clazz, null);
	}

	/**
	 * json字符串 => 对象
	 *
	 * @param json 源json串
	 * @param type 对象类型
	 * @param <T>  泛型
	 */
	public static <T> T toObject(String json, TypeReference type) {

		return toObject(json, null, type);
	}


	/**
	 * json => 对象处理方法
	 * <br>
	 * 参数clazz和type必须一个为null，另一个不为null
	 * <br>
	 * 此方法不对外暴露，访问权限为private
	 *
	 * @param json  源json串
	 * @param clazz 对象类
	 * @param type  对象类型
	 * @param <T>   泛型
	 */
	private static <T> T toObject(String json, Class<T> clazz, TypeReference type) {

		T obj = null;
		if (!StringUtils.isEmpty(json)) {
			try {
				if (clazz != null) {
					obj = om.readValue(json, clazz);
				} else {
					obj = (T) om.readValue(json, type);
				}
			} catch (IOException e) {
				log.warn(e.getMessage(), e);
				throw new IllegalArgumentException(e.getMessage());
			}
		}
		return obj;
	}

	/**
	 * json字符串 => 对象列表
	 *
	 * @param json 源json串
	 * @param type 对象类型
	 * @param <T>  泛型
	 */
	public static <T> List<T> toArray(String json, TypeReference type) {

		return toArray(json, null, type);
	}

	/**
	 * json字符串 => 对象列表
	 *
	 * @param json  源json串
	 * @param clazz 对象类
	 * @param <T>   泛型
	 */
	public static <T> List<T> toArray(String json, Class<T> clazz) {

		return toArray(json, clazz, null);
	}

	/**
	 * json => 对象列表处理方法
	 * <br>
	 * 参数clazz和type必须一个为null，另一个不为null
	 * <br>
	 * 此方法不对外暴露，访问权限为private
	 *
	 * @param json  源json串
	 * @param clazz 对象类
	 * @param type  对象类型
	 * @param <T>   泛型
	 */
	public static <T> List<T> toArray(String json, Class<T> clazz, TypeReference type) {
		List<T> list = null;
		if (!StringUtils.isEmpty(json)) {
			try {
				if (clazz != null) {
					list = om.readValue(json, new TypeReference<List<T>>() {
					});
				} else {
					list = (List<T>) om.readValue(json, type);
				}
			} catch (IOException e) {
				log.warn(e.getMessage(), e);
				throw new IllegalArgumentException(e.getMessage());
			}
		}
		return list;
	}


	public static void main(String[] args) throws IOException {
      /*  ResultBean resultBean = ResultBean.success();
        List<KeyValueBean> list = new ArrayList<KeyValueBean>();
        for (int i = 0, l = 20; i < l; i++) {
            list.add(new KeyValueBean("" + i, "" + i));
        }
        PageBean pageBean = new PageBean();
        pageBean.setRecords(list);
        resultBean.setData(pageBean);
        String jsonStr = JacksonUtils.toJSONString(resultBean);
        JsonNode jsonNode = om.readTree(jsonStr);
        jsonNode.asLong();*/
		System.out.println(JacksonUtils.toJSONString(new Student()));

	}

	@Data
	public static class Student {

		// 【注意】不要那个用vStatus,用vrStatus.避免lombok 自动生成getVStatus方法
		private int vStatus;

	}
}
