package com.whattoeat;

import java.io.File;

public class Env {
    private static final String ENV = System.getenv("ENV");
    private static final String API_KEY = System.getenv("API_KEY");
    private static final String DATA_STORAGE_FOLDER_PATH = setDataStorageFolderPath();
    private static final String LOG_FILE_PATH = setLogFilePath();

    public static String getApiKey() {
        return API_KEY;
    }

    public static String getENV() {
        return ENV;
    }

    public static String getDataStorageFolderPath() {
        return DATA_STORAGE_FOLDER_PATH;
    }

    public static String getLogFilePath() {
        return LOG_FILE_PATH;
    }

    private static String setDataStorageFolderPath() {
        String osName = System.getProperty("os.name").toLowerCase();
        String userHome = System.getProperty("user.home");
        String path;
        if (osName.contains("win")) {
            path = System.getenv("LOCALAPPDATA") + "\\WhatToEat\\";
        } else if (osName.contains("mac")) {
            path = userHome + "/Library/Application Support/WhatToEat/";
        } else {
            path = userHome + "~/.WhatToEat/";
        }
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return path;
    }

    private static String setLogFilePath() {
        String osName = System.getProperty("os.name").toLowerCase();
        String userHome = System.getProperty("user.home");
        String path;
        if (osName.contains("win")) {
            path = System.getenv("LOCALAPPDATA") + "\\WhatToEat\\logs";
        } else if (osName.contains("mac")) {
            path = userHome + "/Library/Application Support/WhatToEat/logs";
        } else {
            path = userHome + "/.WhatToEat/logs";
        }
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return path;
    }

}
