package com.qhkj.zyzb.utils;

import java.math.BigDecimal;

public class DigitHelper extends BaseHelper {
    public static float keepTwoDecimalPlaces(float digit) {
        BigDecimal bd = new BigDecimal(digit);
        return bd.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
    }
    
    public static float keepZeroDecimalPlaces(float digit) {
        BigDecimal bd = new BigDecimal(digit);
        return bd.setScale(0, BigDecimal.ROUND_HALF_UP).floatValue();
    }
}
