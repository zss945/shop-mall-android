package com.zss.kit;

import java.math.BigDecimal;

public class BigDecimalUtils {

    private BigDecimalUtils() {
    }

    /**
     * 精确加法
     */
    public static String add(String value1, String value2) {
        BigDecimal b1 = new BigDecimal(value1);
        BigDecimal b2 = new BigDecimal(value2);
        return b1.add(b2).toPlainString();
    }

    /**
     * 精确减法
     */
    public static String sub(String value1, String value2) {
        BigDecimal b1 = new BigDecimal(value1);
        BigDecimal b2 = new BigDecimal(value2);
        return b1.subtract(b2).toPlainString();
    }

    /**
     * 精确乘法
     */
    public static String mul(String value1, String value2) {
        BigDecimal b1 = new BigDecimal(value1);
        BigDecimal b2 = new BigDecimal(value2);
        return b1.multiply(b2).toPlainString();
    }

    /**
     * 精确除法
     *
     * @param scale 精度
     */
    public static String div(String value1, String value2, int scale) {
        assert scale < 0;
        BigDecimal b1 = new BigDecimal(value1);
        BigDecimal b2 = new BigDecimal(value2);
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).toPlainString();
    }

    /**
     * 四舍五入
     *
     * @param scale 小数点后保留几位
     */
    public static String scale(String value, int scale) {
        BigDecimal b1 = new BigDecimal(value);
        return b1.setScale(scale, BigDecimal.ROUND_HALF_UP).toPlainString();
    }

    /**
     * 比较大小
     */
    public static boolean equalTo(BigDecimal b1, BigDecimal b2) {
        if (b1 == null || b2 == null) {
            return false;
        }
        return 0 == b1.compareTo(b2);
    }

}