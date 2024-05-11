package com.whattoeat;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.io.IOException;

public class Env {
    public static final String DEV = "DEV";
    public static final String PRODUCT = "PRODUCT";
    private String runEnv;
    private String apiKey;
    public Env(String envFilePath) {
        setRunEnv(envFilePath);
        if (runEnv.equals(DEV)) {
            setApiKey(envFilePath);
        } else if (runEnv.equals(PRODUCT)) {
            setApiKey();
        }
    }

    private void setRunEnv(String envFilePath) {
        if (envFilePath == null) {
            System.err.println("envFilePath cannot be null!!!");
            return;
        } else if (envFilePath.isEmpty()) {
            System.err.println("string length of the envFilePath is 0!!!");
            return;
        }
        try {
            FileReader reader = new FileReader(envFilePath);
            JsonParser jsonParser = new JsonParser();
            JsonObject json = jsonParser.parse(reader).getAsJsonObject();
            runEnv = json.get("env").getAsString();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setApiKey(String envFilePath) {
        if (envFilePath == null) {
            System.err.println("envFilePath cannot be null!!!");
            return;
        } else if (envFilePath.isEmpty()) {
            System.err.println("string length of the envFilePath is 0!!!");
            return;
        }
        try {
            FileReader reader = new FileReader(envFilePath);
            JsonParser jsonParser = new JsonParser();
            JsonObject json = jsonParser.parse(reader).getAsJsonObject();
            apiKey = json.get("key").getAsString();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setApiKey() {
        apiKey = "";
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getRunEnv() {
        return runEnv;
    }
}
