package com.whattoeat;

import java.io.File;
import java.io.IOException;

public class Env {
    private static final String ENV = System.getenv("ENV");
    private static final String API_KEY = System.getenv("API_KEY");
    private static final String DATA_STORAGE_FOLDER_PATH = setDataStorageFolderPath();

    public static String getApiKey() {
        return API_KEY;
    }

    public static String getENV() {
        return ENV;
    }

    public static String getDataStorageFolderPath() {
        return DATA_STORAGE_FOLDER_PATH;
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
}
