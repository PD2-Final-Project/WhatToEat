package com.whattoeat;

import com.google.gson.JsonObject;
import com.whattoeat.model.StoresDataQuery;
import com.whattoeat.model.api.DataParser;
import com.whattoeat.ui.FrameController;


public class Main {
    public static void main(String[] args) {
        String apiKey;
        String runEnv;

        Env env = new Env("src/env.json");
        apiKey = env.getApiKey();
        runEnv = env.getRunEnv();

        if (runEnv.equals(Env.DEV)) {

            FrameController.initialize();

        } else if (runEnv.equals(Env.PRODUCT)) {
            // TODO:
        }
    }
}