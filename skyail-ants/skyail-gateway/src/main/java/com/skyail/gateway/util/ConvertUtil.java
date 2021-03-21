package com.skyail.gateway.util;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ConvertUtil {

    /**
     * 将字符串转换为list
     * @param param 原字符串
     * @param split 分隔符号
     * @return
     */
    public static List<String> toList (String param , String split){
        String[] splitArray = param.split(split);
        return Arrays.asList(splitArray);
    }
}
