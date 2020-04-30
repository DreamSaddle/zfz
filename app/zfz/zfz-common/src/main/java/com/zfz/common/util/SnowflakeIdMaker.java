package com.zfz.common.util;

/**
 * Twitter_Snowflake<br>
 * SnowFlake的结构如下(每部分用-分开):<br>
 * 0 - 0000000000 0000000000 0000000000 0000000000 0 - 000000000000- 00000 - 00000  <br>
 * 1位标识，由于long基本类型在Java中是带符号的，最高位是符号位，正数是0，负数是1，所以id一般是正数，最高位是0<br>
 * 41位时间截(毫秒级)，注意，41位时间截不是存储当前时间的时间截，而是存储时间截的差值（当前时间截 - 开始时间截)
 * 得到的值），这里的的开始时间截，一般是我们的id生成器开始使用的时间，由我们程序来指定的（如下下面程序IdWorker类的startTime属性）。41位的时间截，可以使用69年，年T = (1L << 41) / (1000L * 60 * 60 * 24 * 365) = 69<br>
 * 12位序号，毫秒内的计数，12位的计数顺序号支持每个节点每毫秒(同一机器，同一时间截)产生4096个ID序号<br>
 * 10位的数据机器位，可以部署在1024个节点，包括5位dataCenterId和5位workerId<br>
 * 加起来刚好64位，为一个Long型。<br>
 * SnowFlake的优点是，整体上按照时间自增排序，并且整个分布式系统内不会产生ID碰撞(由数据中心ID和机器ID作区分)，并且效率较高，经测试，SnowFlake每秒能够产生26万ID左右。
 */
public class SnowflakeIdMaker {


    //开始时间截 (2015-01-01)
    private final long anchor = 1420041600000L;

    //机器id所占的位数
    private final long workerIdBits = 5L;
    //支持的最大机器id，结果是31 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数)
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);

    //数据中心id所占的位数
    private final long dataCenterIdBits = 5L;
    //支持的最大数据中心id，结果是31
    private final long maxDataCenterId = -1L ^ (-1L << dataCenterIdBits);
    //时间戳中序号在id中占的位数
    private final long sequenceBits = 12L;


    /*********
     * 位移时的偏移量计算
     *********/
    //机器ID向左移0位
    private final long workerIdShift = 0;
    //数据标识id向左移5位(5)
    private final long dataCenterIdShift = workerIdBits;
    //时间戳中序号向左移17位(5+5)
    private final long sequenceShift = dataCenterIdBits + workerIdBits;
    //时间截向左移22位(5+5+12)
    private final long timestampLeftShift = sequenceBits + workerIdBits + dataCenterIdBits;

    //生成序号的掩码，这里为4095 (0b-111111111111=0xfff=4095)
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    //工作机器ID(0~31)
    private long workerId;
    //数据中心ID(0~31)
    private long dataCenterId;
    //毫秒内序号(0~4095)
    private long sequence = 0L;
    //上次生成ID的时间截
    private long lastTimestamp = -1L;


    /**
     * 构造函数
     *
     * @param dataCenterId 数据中心ID (0~31)
     * @param workerId     工作ID (0~31)
     */
    public SnowflakeIdMaker(long dataCenterId, long workerId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (dataCenterId > maxDataCenterId || dataCenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDataCenterId));
        }
        this.workerId = workerId;
        this.dataCenterId = dataCenterId;
    }

    /**
     * 获得下一个ID (该方法是线程安全的)
     *
     * @return SnowflakeId
     */
    public synchronized long nextId() {
        long timestamp = timeGen();

        //如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(
                    String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        //如果是同一时间生成的，则进行毫秒内序号
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            //毫秒内序号溢出
            if (sequence == 0) {
                //阻塞到下一个毫秒,获得新的时间戳
                timestamp = tilNextMillis(lastTimestamp);
            }
        }
        //时间戳改变，毫秒内序号重置
        else {
            sequence = 0L;
        }

        //上次生成ID的时间截
        lastTimestamp = timestamp;
        //移位并通过或运算拼到一起组成64位的ID
        return ((timestamp - anchor) << timestampLeftShift) //加入时间戳
                | (sequence << sequenceShift) //加入时间戳中的序号
                | (dataCenterId << dataCenterIdShift) //加入数据中心ID
                | (workerId << workerIdShift) //加入机器ID
                ;
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     *
     * @param lastTimestamp 上次生成ID的时间截
     * @return 当前时间戳
     */
    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 返回以毫秒为单位的当前时间
     *
     * @return 当前时间(毫秒)
     */
    protected long timeGen() {
        return System.currentTimeMillis();
    }


    /*********
     * 信息提取方法
     *********/
    //信息提取偏移量，时间（偏移22）+序号（偏移12）+中心（偏移5）+机器号（0）
    public static int getWorkId(long id) {
        return (int) (id & 31);
    }

    public static int getDataCenterId(long id) {
        return (int) (id >> 5 & 31);
    }

    public static int getSequence(long id) {
        return (int) (id >> 10 & 4095);
    }

    public static long getTimestamp(long id) {
        return id >> 22 & Long.MAX_VALUE;
    }

}