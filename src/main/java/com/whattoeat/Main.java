package com.whattoeat;


import com.whattoeat.model.StoresDataQuery;

public class Main {
    public static void main(String[] args) {
        String apiKey;
        String runEnv;

        Env env = new Env("src/env.json");
        apiKey = env.getApiKey();
        runEnv = env.getRunEnv();

        if (runEnv.equals(Env.DEV)) {
            String location = "台南";
            String keyword = "restaurant";
            int radius = 1000;
            StoresDataQuery sdq = new StoresDataQuery(location, keyword, radius);
            for(int i = 0 ; i < sdq.storesData.getLength() ; i ++){
                System.out.println(sdq.storesData.getAddresses()[i]);
            }
        } else if (runEnv.equals(Env.PRODUCT)) {
            // TODO:
        }
    }
}