package com.qb.Utils;

/**
 * Created by qubian on 15/8/13.
 */
public class StringUtils {

    public static boolean isEmpty(String str)
    {
        if (str == null || "".equals(str)) {
            return true;
        }
        return false;
    }
}
