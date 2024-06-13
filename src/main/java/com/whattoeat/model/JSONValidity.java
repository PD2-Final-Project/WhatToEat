package com.whattoeat.model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;

/**
 * @author Jess
 * This class is to check whether the json file is valid.
 * */
public class JSONValidity {
    public static boolean isValidJSONFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            return false; // 文件不存在或者不是一个文件，直接返回 false
        }

        try (Reader reader = new FileReader(file)) {
            JSONTokener jsonTokener = new JSONTokener(reader);
            Object obj = jsonTokener.nextValue(); // 尝试解析文件内容

            // 检查解析结果是否是 JSONObject 或者 JSONArray
            return obj instanceof JSONObject || obj instanceof JSONArray;
        } catch (IOException | RuntimeException e) {
            // IOException：文件读取错误
            // RuntimeException：JSONTokener 解析失败，比如文件内容不是有效的 JSON 格式
            //e.printStackTrace(); // 这里可以根据需要处理异常或者记录日志
            return false;
        }
    }
}
