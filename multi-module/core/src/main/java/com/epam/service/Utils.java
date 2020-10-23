package com.epam.service;


import com.epam.utils.StringUtils;

public class Utils {
    private StringUtils stringUtils = new StringUtils();


    public boolean isAllPositiveNumbers(String... str) {
        if (str == null || str.length == 0) {
            return false;
        }

        return helper(str);
    }

    private boolean helper(String[] str) {
        for (String s : str) {
            if (!stringUtils.isPositiveNumber(s)) {
                return false;
            }
        }
        return true;
    }
}
