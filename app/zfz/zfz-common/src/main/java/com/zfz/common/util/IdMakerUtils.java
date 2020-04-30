package com.zfz.common.util;


import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 分布式ID生成工具类
 */
public class IdMakerUtils {
    private static int workerId = 0;
    private static int dataCenterId = 0;

    //存放不同表或实体对应的id生成器，（生成器生成id是线程同步安全的，如果多表共用一个生成器可能会降低性能）
    private static Map<String, SnowflakeIdMaker> idWorkerMap = new HashMap<String, SnowflakeIdMaker>();


    //从配置中读取数据中心和机器号信息进行初始化
    //本类是由spring启动创建实例，进行初始化实例，简介设置static dataCenterId和workerId
    @Resource
    public void setConfigProperties(List<Properties> properties) {
        if (properties != null) {
            properties.forEach(t -> {
                if (t.containsKey("workerId")) {
                    workerId = NumUtil.toInt(t.getProperty("workerId"));
                }
                if (t.containsKey("dataCenterId")) {
                    dataCenterId = NumUtil.toInt(t.getProperty("dataCenterId"));
                }
            });
        }

    }

    public void setConfigProperties(String wId, String cId) {
        workerId = NumUtil.toInt(wId);
        dataCenterId = NumUtil.toInt(cId);
    }

    public void setConfigProperties(int wId, int cId) {
        workerId = wId;
        dataCenterId = cId;
    }

    /**
     * 全局公用的id，如果支付订单，在微信和支付宝支付的时候，需要保证订单号唯一。
     *
     * @return
     */
    public static long getOrderId() {
        return getId("ARIVN_ORDER");
    }

    /**
     * 根据实体名或表明，生成id
     *
     * @param type
     * @return
     */
    public static long getId(String type) {
        SnowflakeIdMaker idWorker = null;
        if (!idWorkerMap.containsKey(type)) {
            //安全创建id生成器
            synchronized (idWorkerMap) {
                if (!idWorkerMap.containsKey(type)) {
                    idWorker = new SnowflakeIdMaker(dataCenterId, workerId);
                    idWorkerMap.put(type, idWorker);
                } else {
                    idWorker = idWorkerMap.get(type);
                }
            }
        } else {
            idWorker = idWorkerMap.get(type);
        }
        //生成id
        return idWorker.nextId();
    }

    /**
     * 重载
     *
     * @param classType
     * @return
     */
    public static long getId(Class classType) {
        String type = classType.getSimpleName();
        return getId(type);
    }
}
