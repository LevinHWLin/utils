package com.common.polyv;

import java.util.UUID;

public class BaseConfig {

    public static final String POLYV_USER_ID = "aaa";

    public static final String POLYV_APP_ID = "bbbb";

    public static final String POLYV_APP_SECRET = "ddddd";

    /**
     * 获得uuid
     */
    public static String getUuid()
    {
        UUID uuid = UUID.randomUUID();
        String uuidStr = uuid.toString();
        uuidStr = uuidStr.replace("-", "");
        return uuidStr;
    }
}
