package com.zss.kit;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;


public class FastjsonUtils {

    private static final SerializeConfig config = new SerializeConfig();

    public static String toString(Object object) {
        return JSON.toJSONString(object, config);
    }

    public static <T> T toObject(String text, Class<T> clazz) {
        return JSON.parseObject(text, clazz);
    }

    public static <T> List<T> toList(String text, Class<T> clazz) {
        return JSON.parseArray(text, clazz);
    }

}