package com.zfz.common.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 时间日期工具类
 * @author DreamSaddle
 */
public class DateTimeUtil {

	public static final String generalFormat = "yyyy-MM-dd HH:mm:ss";
	public static final String ymdFormat = "yyyy-MM-dd";
	public static final String chineseFormat = "yyyy年MM月dd日 HH时mm分ss秒";
	public static final String chineseFormat_YMDH = "yyyy年MM月dd日 HH时";

	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(generalFormat);

	/** 单位-年 */
	public static final String YEAR_UNIT = "year";
	/** 单位-月 */
	public static final String MONTH_UNIT = "month";
	/** 单位-日 */
	public static final String DAY_UNIT = "day";
	/** 单位-时 */
	public static final String HOUR_UNIT = "hour";
	/** 单位-分 */
	public static final String MINUTE_UNIT = "minute";

	/** 偏移类型 - 向前偏移(减) */
	private static final int OFFSET_TYPE_MINUS = 1;
	/** 偏移类型 - 向后偏移(加) */
	private static final int OFFSET_TYPE_PLUS = 2;


	public static Date now() {
		return Calendar.getInstance().getTime();
	}

	/**
	 * Date 转通用格式字符串
	 * 年-月-日 时:分:秒
	 * @param date
	 * @return
	 */
	public static String toGeneralFormat(Date date) {
		LocalDateTime localDateTime = dateToLocalDateTime(date);
		return localDateTime.format(DateTimeFormatter.ofPattern(generalFormat));
	}

	/**
	 * Date 转汉语格式
	 * @param date
	 * @return
	 */
	public static String toChineseFormat(Date date) {
		return toAssignFormat(date, chineseFormat);
	}


	/**
	 * Date 转汉语 年月日时 格式
	 * @param date
	 * @return
	 */
	public static String toChineseYMDHFormat(Date date) {
		return toAssignFormat(date, chineseFormat_YMDH);
	}

	/**
	 * 日期转换为指定搁置
	 * @param date
	 * @param ofPattern
	 * @return
	 */
	public static String toAssignFormat(Date date, String ofPattern) {
		DateTimeFormatter pattern = DateTimeFormatter.ofPattern(ofPattern);
		return dateToLocalDateTime(date).format(pattern);
	}

	/**
	 * 当前时间是否早于被比较时间 {@code beforeMinutes} 分钟
	 * @param compareDate 被比较时间
	 * @param beforeMinutes 分钟值
	 * @return true: 是
	 */
	public static boolean nowIsBefore(Date compareDate, Integer beforeMinutes) {
		LocalDateTime localDateTime = dateToLocalDateTime(compareDate);
		localDateTime = localDateTime.minusMinutes(beforeMinutes);
		return LocalDateTime.now().isBefore(localDateTime);
	}

	/**
	 * 当前时间是否早于被比较时间
	 * @param compareDate 被比较时间
	 * @return true: 是
	 */
	public static boolean nowIsBefore(Date compareDate) {
		LocalDateTime compareLocalDateTime = dateToLocalDateTime(compareDate);
		return LocalDateTime.now().isBefore(compareLocalDateTime);
	}

	/**
	 * 传入日期是否早于当前
	 * @param compareDate 比较日期
	 * @return
	 */
	public static boolean isBeforeNow(Date compareDate) {
		LocalDateTime compareLocalDateTime = dateToLocalDateTime(compareDate);
		return compareLocalDateTime.isBefore(LocalDateTime.now());
	}

	/**
	 * 左侧日期是否早于右侧日期
	 * @param left 比较对象
	 * @param right 被比较对象
	 * @return
	 */
	public static boolean isBefore(Date left, Date right) {
		return dateToLocalDateTime(left).isBefore(dateToLocalDateTime(right));
	}


	/**
	 * 左侧日期是否晚于右侧日期
	 * @param left 比较对象
	 * @param right 被比较对象
	 * @return
	 */
	public static boolean isAfter(Date left, Date right) {
		return !isBefore(left, right);
	}

	/**
	 * 当前时间是否晚于被比较时间
	 * @param compareDate 被比较时间
	 * @return true: 是
	 */
	public static boolean nowIsAfter(Date compareDate) {
		LocalDateTime compareLocalDateTime = dateToLocalDateTime(compareDate);
		return LocalDateTime.now().isAfter(compareLocalDateTime);
	}

	/**
	 * 传入日期是否晚于当前
	 * @param compareDate 比较日期
	 * @return
	 */
	public static boolean isAfterNow(Date compareDate) {
		LocalDateTime compareLocalDateTime = dateToLocalDateTime(compareDate);
		return compareLocalDateTime.isAfter(LocalDateTime.now());
	}

	/**
	 * 日期大小比较 leftDate > rightDate
	 * @param leftDate
	 * @param rightDate
	 * @return
	 */
	public static boolean gt(Date leftDate, Date rightDate) {
		return dateToLocalDateTime(leftDate).isAfter(dateToLocalDateTime(rightDate));
	}

	/**
	 * 日期大小比较 leftDate < rightDate
	 * @param leftDate
	 * @param rightDate
	 * @return
	 */
	public static boolean lt(Date leftDate, Date rightDate) {
		return !gt(leftDate, rightDate);
	}

	/**
	 * 日期大小比较 leftDate = rightDate
	 * @param leftDate
	 * @param rightDate
	 * @return
	 */
	public static boolean eq(Date leftDate, Date rightDate) {
		return dateToLocalDateTime(leftDate).equals(dateToLocalDateTime(rightDate));
	}

	/**
	 * 日期大小比较 leftDate != rightDate
	 * @param leftDate
	 * @param rightDate
	 * @return
	 */
	public static boolean neq(Date leftDate, Date rightDate) {
		return !eq(leftDate, rightDate);
	}

	/**
	 * Date 转 LocalDateTime
	 * @param date Date Object
	 * @return LocalDateTime Object
	 */
	public static LocalDateTime dateToLocalDateTime(Date date) {
		return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
	}

	/**
	 * LocalDateTime 转 Date
	 * @param localDateTime
	 * @return
	 */
	public static Date localDateTimeToDate(LocalDateTime localDateTime) {
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	/**
	 * 时间日期字符串转 Date
	 * @param dateTimeStr 2020-02-02 02:02:00
	 * @return
	 */
	public static Date parseToDate(String dateTimeStr) {
		return localDateTimeToDate(parseToLocalDateTime(dateTimeStr));
	}

	/**
	 * 时间日期字符串转 LocalDateTime
	 * @param dateTimeStr 2020-02-02 02:02:00
	 * @return
	 */
	public static LocalDateTime parseToLocalDateTime(String dateTimeStr) {
		return LocalDateTime.parse(dateTimeStr, formatter);
	}

	/**
	 * 获取当前日期字符串
	 * example: 20200310
	 * @return
	 */
	public static String currentDateStr() {
		return dateTimeStr(new Date(), true, false);
	}

	/**
	 * 获取指定日期字符串
	 * example: 20200310
	 * @return
	 */
	public static String dateStr(Date date) {
		return dateTimeStr(date, true, false);
	}

	/**
	 * 获取当前日期时间字符串
	 * @param zeroFill 是否补零
	 * @param includeHMS 是否包含时分秒
	 * @return
	 */
	public static String dateTimeStr(boolean zeroFill, boolean includeHMS) {
		return dateTimeStr(new Date(), zeroFill, includeHMS);
	}

	/**
	 * 获取当前日期时间字符串
	 * @param date Date
	 * @param zeroFill 是否补零
	 * @param includeHMS 是否包含时分秒
	 * @return
	 */
	public static String dateTimeStr(Date date, boolean zeroFill, boolean includeHMS) {
		LocalDateTime localDateTime = dateToLocalDateTime(date);
		StringBuilder str = new StringBuilder();
		str.append(localDateTime.getYear())
		   .append(zeroFill(zeroFill, localDateTime.getMonthValue()))
		   .append(zeroFill(zeroFill, localDateTime.getDayOfMonth()));

		if (includeHMS) {
			str.append(zeroFill(zeroFill, localDateTime.getHour()))
			   .append(zeroFill(zeroFill, localDateTime.getMinute()))
			   .append(zeroFill(zeroFill, localDateTime.getSecond()));
		}

		return str.toString();
	}

	private static String zeroFill(boolean zeroFill, int origin) {
		if (! zeroFill) {
			return String.valueOf(origin);
		}
		return zeroFill(origin);
	}

	private static String zeroFill(int origin) {
		return origin > 9 ? String.valueOf(origin) : String.format("0%d", origin);
	}

	/**
	 * 当前年份
	 * @return
	 */
	public static int thisYear() {
		return thisYear(Calendar.getInstance().getTime());
	}

	/**
	 * 获取传入日期的年份
	 * @param date
	 * @return
	 */
	public static int thisYear(Date date) {
		if (date == null) {
			return 0;
		}
		return dateToLocalDateTime(date).getYear();
	}

	/**
	 * 当前月份
	 * @return
	 */
	public static int thisMonth() {
		return thisMonth(Calendar.getInstance().getTime());
	}

	/**
	 * 获取传入日期的月份
	 * @param date
	 * @return
	 */
	public static int thisMonth(Date date) {
		if (date == null) {
			return 0;
		}
		return dateToLocalDateTime(date).getMonthValue();
	}

	/**
	 * 当日
	 * @return
	 */
	public static int thisDay() {
		return thisDay(Calendar.getInstance().getTime());
	}

	/**
	 * 获取穿日日期天数(以月份为参考)
	 * @param date
	 * @return
	 */
	public static int thisDay(Date date) {
		if (date == null) {
			return 0;
		}
		return dateToLocalDateTime(date).getDayOfMonth();
	}

	/**
	 * 当前日期加上 {@code days} 天
	 * @param days
	 * @return
	 */
	public static Date plusDays(int days) {
		return plusDays(Calendar.getInstance().getTime(), days);
	}

	/**
	 * 指定日期加上 {@code days} 天
	 * @param originDate
	 * @param days
	 * @return
	 */
	public static Date plusDays(Date originDate, int days) {
		if (days <= 0) {
			return originDate;
		}
		return localDateTimeToDate(dateToLocalDateTime(originDate).plusDays(days));
	}


	/**
	 * 当前日期加上 {@code hours} 小时
	 * @param hours
	 * @return
	 */
	public static Date plusHours(int hours) {
		return plusHours(Calendar.getInstance().getTime(), hours);
	}

	/**
	 * 指定日期加上 {@code hours} 小时
	 * @param originDate
	 * @param hours
	 * @return
	 */
	public static Date plusHours(Date originDate, int hours) {
		if (hours <= 0) {
			return originDate;
		}
		return localDateTimeToDate(dateToLocalDateTime(originDate).plusHours(hours));
	}

	/**
	 * 当前日期减去 {@code days} 天
	 * @param days
	 * @return
	 */
	public static Date minusDays(int days) {
		return minusDays(Calendar.getInstance().getTime(), days);
	}

	/**
	 * 指定日期减去 {@code days} 天
	 * @param originDate
	 * @param days
	 * @return
	 */
	public static Date minusDays(Date originDate, int days) {
		if (days <= 0) {
			return originDate;
		}

		return localDateTimeToDate(dateToLocalDateTime(originDate).minusDays(days));
	}

	/**
	 * 当前日期减去 {@code months} 月
	 * @param months
	 * @return
	 */
	public static Date minusMonths(int months) {
		return minusMonths(Calendar.getInstance().getTime(), months);
	}

	/**
	 * 指定日期减去 {@code months} 月
	 * @param originDate
	 * @param months
	 * @return
	 */
	public static Date minusMonths(Date originDate, int months) {
		if (months <= 0) {
			return originDate;
		}

		return localDateTimeToDate(dateToLocalDateTime(originDate).minusMonths(months));
	}

	/**
	 * 当前日期减去 {@code hours} 小时
	 * @param hours
	 * @return
	 */
	public static Date minusHours(int hours) {
		return minusHours(Calendar.getInstance().getTime(), hours);
	}

	/**
	 * 指定日期减去 {@code hours} 小时
	 * @param originDate
	 * @param hours
	 * @return
	 */
	public static Date minusHours(Date originDate, int hours) {
		if (hours <= 0) {
			return originDate;
		}
		return localDateTimeToDate(dateToLocalDateTime(originDate).minusHours(hours));
	}

	/**
	 * 当前时间拆分数组
	 * 年 月 日 时 分
	 * @return 数组
	 */
	public static int[] current() {
		LocalDateTime now = LocalDateTime.now();
		int[] arr = new int[5];
		arr[0] = now.getYear();
		arr[1] = now.getMonthValue();
		arr[2] = now.getDayOfMonth();
		arr[3] = now.getHour();
		arr[4] = now.getMinute();

		return arr;
	}

	/**
	 * 当前时间向前偏移, 包含当前时间
	 * @param offset 偏移量
	 * @param offsetUnit 偏移单位 {@link #YEAR_UNIT #MONTH_UNIT}...
	 * @return 日期列表
	 */
	public static List<Date> nowMinusRange(long offset, String offsetUnit) {
		return minusRange(now(), offset, offsetUnit, true);
	}

	/**
	 * 当前时间向前偏移
	 * 可选择是否包含当前时间
	 * @param referDate 对照时间(在这个时间上进行偏移操作)
	 * @param offset 偏移量
	 * @param offsetUnit 偏移单位 {@link #YEAR_UNIT #MONTH_UNIT}...
	 * @param includeReferDate 是否包含对照时间, 如果包含，会将对照时间也加入到偏移列表中
	 * @return 日期列表
	 */
	public static List<Date> minusRange(Date referDate, long offset, String offsetUnit, boolean includeReferDate) {
		return dateOffset(referDate, OFFSET_TYPE_MINUS, offset, offsetUnit, includeReferDate);
	}

	/**
	 * 当前时间向后偏移, 包含当前时间
	 * @param offset 偏移量
	 * @param offsetUnit 偏移单位 {@link #YEAR_UNIT #MONTH_UNIT}...
	 * @return 日期列表
	 */
	public static List<Date> nowPlusRange(long offset, String offsetUnit) {
		return plusRange(now(), offset, offsetUnit, true);
	}

	/**
	 * 当前时间向后偏移
	 * 可选择是否包含当前时间
	 * @param referDate 对照时间(在这个时间上进行偏移操作)
	 * @param offset 偏移量
	 * @param offsetUnit 偏移单位 {@link #YEAR_UNIT #MONTH_UNIT}...
	 * @param includeReferDate 是否包含对照时间, 如果包含，会将对照时间也加入到偏移列表中
	 * @return 日期列表
	 */
	public static List<Date> plusRange(Date referDate, long offset, String offsetUnit, boolean includeReferDate) {
		return dateOffset(referDate, OFFSET_TYPE_PLUS, offset, offsetUnit, includeReferDate);
	}

	/**
	 * 获取时间偏移范围的全部时间
	 * 也可以包含偏移参照时间
	 * @param referDate 偏移参照时间
	 * @param offsetType 偏移类型
	 * @param offset 偏移量
	 * @param offsetUnit 偏移单位
	 * @param includeReferDate 是否包含偏移参照时间
	 * @return
	 */
	private static List<Date> dateOffset(Date referDate, int offsetType, long offset, String offsetUnit, boolean includeReferDate) {
		List<Date> dateList = offsetRange(referDate, offsetType, offset, offsetUnit);
		// 包含对照时间
		if (includeReferDate) {
			// 如果是向前偏移，则需要将对照时间放置到偏移列表末尾
			// 向后偏移放置到偏移列表开头
			if (OFFSET_TYPE_MINUS == offsetType) {
				dateList.add(referDate);
			} else if (OFFSET_TYPE_PLUS == offsetType) {
				dateList.add(0, referDate);
			} else {
				throw new UnsupportedOperationException("错误的偏移类型");
			}
		}

		return dateList;
	}

	/**
	 * 获取时间偏移范围的全部时间
	 * @param date 偏移参照时间
	 * @param type 偏移类型
	 * @param offset 偏移量
	 * @param offsetUnit 偏移单位
	 * @return
	 */
	private static List<Date> offsetRange(Date date, int type, long offset, String offsetUnit) {
		if (offset < 1) {
			throw new IllegalArgumentException("偏移量不能小于1");
		}
		if (date == null) {
			throw new NullPointerException("输入日期为空");
		}

		LocalDateTime referDate = dateToLocalDateTime(date);
		List<Date> result = new ArrayList<>();
		long offsetTemp = offset;
		for (long i = 1; i <= offset; i++) {
			LocalDateTime offsetDate = null;
			long divisor = i;
			// 如果是向前偏移，需要根据偏移递减计算以保持 result 是从小到大的顺序
			if (type == OFFSET_TYPE_MINUS) {
				divisor = -(offsetTemp--);
			}

			switch (offsetUnit) {
				case YEAR_UNIT: offsetDate = referDate.plusYears(divisor);break;
				case MONTH_UNIT: offsetDate = referDate.plusMonths(divisor);break;
				case DAY_UNIT: offsetDate = referDate.plusDays(divisor);break;
				case HOUR_UNIT: offsetDate = referDate.plusHours(divisor);break;
				case MINUTE_UNIT: offsetDate = referDate.plusMinutes(divisor);break;
			}

			if (offsetDate == null) {
				throw new NullPointerException("偏移时间为空");
			}
			result.add(localDateTimeToDate(offsetDate));
		}

		return result;
	}

	public static void main(String[] args) {
	}
}
