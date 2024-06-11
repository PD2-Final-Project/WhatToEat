package com.whattoeat;

import java.io.File;
import java.io.IOException;

public class Env {
    private static final String ENV = System.getenv("ENV");
    private static final String API_KEY = System.getenv("API_KEY");
    private static final String QUERY_RESULTS_FILE_PATH = setQueryResultsFilePath();

    public static String getApiKey() {
        return API_KEY;
    }

    public static String getQueryResultsFilePath() {
        return QUERY_RESULTS_FILE_PATH;
    }

    public static String getENV() {
        return ENV;
    }

    private static String setQueryResultsFilePath() {
        String osName = System.getProperty("os.name").toLowerCase();
        String userHome = System.getProperty("user.home");
        String path;

        if (osName.contains("win")) {
            path = System.getenv("LOCALAPPDATA") + "\\WhatToEat\\queryResults.json";
        } else if (osName.contains("mac")) {
            path = userHome + "/Library/Application Support/WhatToEat/queryResults.json";
        } else {
            path = userHome + "~/.WhatToEat/queryResults.json";
        }

        File file = new File(path);
        File parentDir = file.getParentFile();
        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }

        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file.getAbsolutePath();
    }
}
