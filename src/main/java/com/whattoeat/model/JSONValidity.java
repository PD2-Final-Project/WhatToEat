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
            return false;
        }

        try (Reader reader = new FileReader(file)) {
            JSONTokener jsonTokener = new JSONTokener(reader);
            Object obj = jsonTokener.nextValue();

            return obj instanceof JSONObject || obj instanceof JSONArray;
        } catch (IOException | RuntimeException e) {
            return false;
        }
    }
}
