package com.zfz.common.util;

import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigDecimal;
import java.text.NumberFormat;


/**
 * 创建时间：2013-8-27 <br>
 * 功能描述： 处理数字的Util、计算百分比<br>
 */
public class NumUtil {

	/**
	 * 转换为BigDecimal
	 *
	 * @param o
	 * @return BigDecimal
	 * @author fantasy
	 * @date 2013-8-27
	 */
	public static BigDecimal toBig(Object o) {
		if (o == null || o.toString().equals("") || o.toString().equals("NaN")) {
			return new BigDecimal(0);
		}
		return new BigDecimal(o.toString());
	}

	/**
	 * 计算百分比
	 *
	 * @param divisor
	 * @param dividend
	 * @return String
	 * @author fantasy
	 * @date 2013-8-27
	 */
	public static String getPercent(Object divisor, Object dividend) {
		if (divisor == null || dividend == null) {
			return "";
		}
		NumberFormat percent = NumberFormat.getPercentInstance();
		//建立百分比格式化引用
		percent.setMaximumFractionDigits(2);
		BigDecimal a = toBig(divisor);
		BigDecimal b = toBig(dividend);
		if (a.equals(toBig(0)) || b.equals(toBig(0)) || a.equals(toBig(0.0)) || b.equals(toBig(0.0))) {
			return "0.00%";
		}
		BigDecimal c = a.divide(b, 4, BigDecimal.ROUND_DOWN);
		return percent.format(c);
	}

	/**
	 * 计算比例
	 *
	 * @param divisor
	 * @param dividend
	 * @return String
	 * @author fantasy
	 * @date 2013-10-9
	 */
	public static String divideNumber(Object divisor, Object dividend) {
		if (divisor == null || dividend == null) {
			return "";
		}
		BigDecimal a = toBig(divisor);
		BigDecimal b = toBig(dividend);
		if (a.equals(toBig(0)) || b.equals(toBig(0))) {
			return "0";
		}
		BigDecimal c = a.divide(b, 2, BigDecimal.ROUND_DOWN);
		return c.toString();
	}

	/**
	 * 去两个数的平均值，四舍五入
	 *
	 * @param divisor
	 * @param dividend
	 * @return int
	 * @author fantasy
	 * @date 2013-11-6
	 */
	public static int averageNumber(Object divisor, Object dividend) {
		if (divisor == null || dividend == null) {
			return 0;
		}
		BigDecimal a = toBig(divisor);
		BigDecimal b = toBig(dividend);
		if (a.equals(toBig(0)) || b.equals(toBig(0))) {
			return 0;
		}
		BigDecimal c = a.divide(b, 0, BigDecimal.ROUND_HALF_UP);
		return c.intValue();
	}

	public static int toInt(String str) {
		int re = 0;
		try {
			re = Integer.parseInt(str);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return re;
	}


	public static long toLong(String str) {
		long re = 0;
		try {
			re = Long.parseLong(str);
		} catch (Exception ex) {

		}
		return re;
	}


	public static double sum(double a, double b) {
		BigDecimal bd1 = new BigDecimal(Double.toString(a));
		BigDecimal bd2 = new BigDecimal(Double.toString(b));
		return bd1.add(bd2).doubleValue();
	}


	public static double sum(double... a) {
		BigDecimal bd1 = new BigDecimal("0");
		for (double t : a) {
			bd1 = bd1.add(new BigDecimal(Double.toString(t)));
		}
		return bd1.doubleValue();
	}

	public static double sum(double a, float b) {
		BigDecimal bd1 = new BigDecimal(Double.toString(a));
		BigDecimal bd2 = new BigDecimal(Float.toString(b));
		return bd1.add(bd2).doubleValue();
	}

	public static double sum(double a, int b) {
		BigDecimal bd1 = new BigDecimal(Double.toString(a));
		BigDecimal bd2 = new BigDecimal(Integer.toString(b));
		return bd1.add(bd2).doubleValue();
	}

	public static double toDouble(String str) {
		double re = 0;
		try {
			re = Double.parseDouble(str);
		} catch (Exception ex) {

		}
		return re;
	}

	public static double toDouble(byte[] arr) {
		long value = 0;
		for (int i = 0; i < 8; i++) {
			value |= ((long) (arr[i] & 0xff)) << (8 * i);
		}
		return Double.longBitsToDouble(value);
	}

	public static byte[] toBytes(double d) {
		//第 63 位（掩码 0x8000000000000000L 选定的位）表示浮点数的符号，第62～52位（掩码 0x7ff0000000000000L 选定的位）表示指数，
		// 第51～0位（掩码 0x000fffffffffffffL 选定的位）表示浮点数的有效数字（有时也称为尾数）
		long value = Double.doubleToLongBits(d);
		byte[] byteRet = new byte[8];
		for (int i = 0; i < 8; i++) {
			byteRet[i] = (byte) ((value >> 8 * i) & 0xff);//小端取出

		}
		return byteRet;
	}

	/**
	 * byte[]转int
	 *
	 * @param bytes
	 * @return
	 */
	public static int toInt(byte[] bytes) {
		int value = 0;
		//由低位到高位
		for (int i = 3; i >= 0; i--) {
			value = (value << 8) + bytes[i];
		}
		return value;
	}

	/**
	 * int到byte[]
	 *
	 * @param i
	 * @return
	 */
	public static byte[] toBytes(int i) {
		byte[] result = new byte[4];
		//低位到高位
		result[0] = (byte) (i & 0xFF);
		result[1] = (byte) ((i >> 8) & 0xFF);
		result[2] = (byte) ((i >> 16) & 0xFF);
		result[3] = (byte) ((i >> 24) & 0xFF);
		return result;
	}


	public static String toBinaryString(byte b) {
		return toBinaryString(b, true);
	}

	/**
	 * 因为是已byte为单位处理或发送，所以byte中不再分大端和小端口
	 * byte发送或处理先后顺序为“大端”的。isDaDuan==true
	 * <p>
	 * (由于Intel CPU的架构原因，它是按字节倒序存储的,所以是xiao端，
	 * short int型变量的值是1000，那么它的二进制表达就是：00000011 11101000。由于Intel CPU的架构原因，它是按字节倒序存储的，
	 * 那么就因该是这样：11101000 00000011，这就是定点数1000在内存中的结构。)
	 *
	 * @param b
	 * @param isDaDuan
	 * @return
	 */
	public static String toBinaryString(byte b, boolean isDaDuan) {
		StringBuilder builder = new StringBuilder();
		int tByte = b;
		boolean[] booleans = new boolean[8];
		boolean id = isDaDuan;
		if (id) {
			for (int j = 7; j >= 0; j--) {
				booleans[j] = (tByte & 1) > 0;
				tByte = tByte >> 1;
			}
		} else {
			for (int j = 0; j < 8; j++) {
				booleans[j] = (tByte & 1) > 0;
				tByte = tByte >> 1;
			}
		}
		for (boolean bl : booleans) {
			builder.append(bl ? "1" : "0");
		}
		return builder.toString();
	}


	public static String toBinaryString(double d, boolean isDaDuan, String split) {
		byte[] bytes = toBytes(d);//小端
		StringBuilder builder = new StringBuilder();
		if (isDaDuan) {
			//bytes小端，如果取大端，反过来取
			for (int i = bytes.length - 1; i >= 0; i--) {
				builder.append(toBinaryString(bytes[i])).append(split);
			}
		} else {

			for (int i = 0; i < bytes.length; i++) {
				builder.append(toBinaryString(bytes[i])).append(split);
			}
		}
		return builder.toString();
	}


	public static String toBinaryString(int d, boolean isDaDuan, String split) {
		byte[] bytes = toBytes(d);//小端
		StringBuilder builder = new StringBuilder();
		if (isDaDuan) {
			//bytes小端，如果取大端，反过来取
			for (int i = bytes.length - 1; i >= 0; i--) {
				builder.append(toBinaryString(bytes[i])).append(split);
			}
		} else {

			for (int i = 0; i < bytes.length; i++) {
				builder.append(toBinaryString(bytes[i])).append(split);
			}
		}
		return builder.toString();
	}

	public static String toBinaryString(double d, String split) {
		return toBinaryString(d, true, split);
	}

	public static String toBinaryString(double d) {
		return toBinaryString(d, " ");
	}

	public static String toBinaryString(int d, String split) {
		return toBinaryString(d, true, split);
	}

	public static String toBinaryString(int d) {
		return toBinaryString(d, " ");
	}


	public static String getRandomNum(int length) {
		return RandomStringUtils.randomNumeric(length);
	}

	/**
	 * 提供精确的加法运算。
	 *
	 * @param v1 被加数
	 * @param v2 加数
	 * @return 两个参数的和
	 */
	public static BigDecimal add(BigDecimal v1, BigDecimal v2) {
		return v1.add(v2);
	}

	public static BigDecimal jia(BigDecimal v1, BigDecimal v2) {
		return add(v1, v2);
	}

	/**
	 * 提供精确的减法运算。
	 *
	 * @param v1 被减数
	 * @param v2 减数
	 * @return 两个参数的差
	 */
	public static BigDecimal sub(BigDecimal v1, BigDecimal v2) {
		return v1.subtract(v2);
	}

	public static BigDecimal jian(BigDecimal v1, BigDecimal v2) {
		return sub(v1, v2);
	}

	/**
	 * 提供精确的乘法运算。
	 *
	 * @param v1 被乘数
	 * @param v2 乘数
	 * @return 两个参数的积
	 */
	public static BigDecimal mul(BigDecimal v1, BigDecimal v2) {
		BigDecimal bigDecimal = v1.multiply(v2);
		return bigDecimal;
	}

	public static BigDecimal chen(BigDecimal v1, BigDecimal v2) {
		return mul(v1, v2);
	}

	/**
	 * 提供（相对）精确的除法运算。
	 *
	 * @param v1 被除数
	 * @param v2 除数
	 * @return 结果
	 */
	public static BigDecimal div(BigDecimal v1, BigDecimal v2) {
		return div(v1, v2, 8);
	}

	public static BigDecimal chu(BigDecimal v1, BigDecimal v2) {
		return div(v1, v2);
	}

	public static BigDecimal div(BigDecimal v1, BigDecimal v2, int scale) {
		return v1.divide(v2, scale, BigDecimal.ROUND_UP);
	}

	public static BigDecimal chu(BigDecimal v1, BigDecimal v2, int scale) {
		return div(v1, v2, scale);
	}

	public static BigDecimal shang(BigDecimal v1, BigDecimal v2) {
		BigDecimal[] result = v1.divideAndRemainder(v2);
		return result[0];//0是商
	}

	public static BigDecimal yu(BigDecimal v1, BigDecimal v2) {
		BigDecimal[] result = v1.divideAndRemainder(v2);
		return result[1];//1 是余
	}

	public static String toString(BigDecimal val) {
		if (val != null) {
			return val.stripTrailingZeros().toPlainString();
		}
		return "";
	}

	public static double toYuan(long fen) {
		double f = fen;
		return f / 100;
		//return chu(new BigDecimal(fen), new BigDecimal(100)).doubleValue();
	}

}