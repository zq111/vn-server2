package com.framework.utils;

import com.framework.Exception.CommonException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

public class GsonTools {

    /**
     * Gson对象
     */
    private static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
            .registerTypeAdapter(BigDecimal.class, new BigDecimalTypeAdapter()).disableHtmlEscaping().serializeNulls()
            .create();

    /**
     * 把json字符串转为指定对象
     *
     * @param jsonStr
     * @param t
     * @return
     * @throws CommonException
     */
    public static <T> T str2T(String jsonStr, Class<T> t) throws Exception {
        if (StringUtils.isEmpty(jsonStr)) {
            throw new CommonException("请求参数不允许为空");
        }

        try {
            T target = gson.fromJson(jsonStr, t);
            FieldCheckUtils.validate(target);
            return target;
        } catch (JsonSyntaxException e) {
            throw new CommonException("不是正确的json字符串!");
        }
//        catch (IllegalAccessException e) {
//            throw new CommonException("参数校验转换失败!");
//        }
    }


    public static <T> T jsonElemen2T(JsonObject json, Class<T> t) throws CommonException {
        try {
            return gson.fromJson(json, t);
        } catch (JsonSyntaxException e) {
            throw new CommonException("不是正确的json字符串");
        }
    }

    /**
     * 把json字符串转为Map<String,String>的格式
     *
     * @param jsonStr
     * @return
     * @throws CommonException
     */
    @SuppressWarnings("unchecked")
    public static Map<String, String> str2Map(String jsonStr) throws CommonException {
        try {
            return (Map<String, String>) gson.fromJson(jsonStr, new TypeToken<Map<String, String>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            throw new CommonException("不是正确的json字符串");
        }
    }

    /**
     * 对象转为Json字符串
     *
     * @param obj
     * @return
     * @throws CommonException
     */
    public static String toGsonString(Object obj) {
        return gson.toJson(obj, obj.getClass());
    }

    /**
     * json转Map<String, String>
     *
     * @param json
     * @return
     * @throws CommonException
     */
    @SuppressWarnings("unchecked")
    public static Map<String, String> jsonElemen2Map(JsonObject json) throws CommonException {
        try {
            return (Map<String, String>) gson.fromJson(json, new TypeToken<Map<String, String>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            throw new CommonException("不是正确的json字符串");
        }
    }

    /**
     * json转LinkedHashMap<String, String>
     *
     * @param json
     * @return
     * @throws CommonException
     */
    @SuppressWarnings("unchecked")
    public static LinkedHashMap<String, String> jsonElement2LinkedHashMap(JsonObject json) throws CommonException {
        try {
            return (LinkedHashMap<String, String>) gson.fromJson(json, new TypeToken<LinkedHashMap<String, String>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            throw new CommonException("不是正确的json字符串");
        }
    }

}
