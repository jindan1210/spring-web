package com.king.util;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by 金丹 on 2017/12/22.
 */
public class CommonUtils implements Runnable {
    public static final String SYSPROP_RUNMODE = "runMode";
    public static final String SYSPROP_RUNMODE_TEST = "test";
    public static final String SYSPROP_TESTMODE = "testMode";
    public static final String SYSPROP_TESTMODE_ON = "on";
    private static long lastTimeMillis = System.currentTimeMillis();
    private static int count = 0;

    public CommonUtils() {
    }

    @Override
    public void run() {
        String s = generateNumber(GenNumberType.OD);
        System.out.println(s);
    }

    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String generateNumber(GenNumberType prefix) {
        return generateNumber(prefix.toString());
    }

    public static synchronized String generateNumber(String prefix) {
        StringBuilder sb = new StringBuilder();
        sb.append(prefix);
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis == lastTimeMillis) {
            ++count;
        } else {
            lastTimeMillis = currentTimeMillis;
            count = 0;
        }

        sb.append((new SimpleDateFormat("yyMMddHHmmssSSS")).format(Long.valueOf(currentTimeMillis))).append(count);
        return sb.toString();
    }

    public static synchronized String generateNumberByTime() {
        return String.valueOf(System.currentTimeMillis());
    }

    public static Map<String, String> loadProps(String uri) {
        OrderedProperties props = new OrderedProperties();
        Map result = new LinkedHashMap();
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(uri);

        try {
            props.loadMap(is, result);
        } catch (Exception var12) {
            throw new RuntimeException("load resource fail, uri:" + uri + " errorMsg:" + var12.getMessage(), var12);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException var11) {
                    ;
                }
            }

        }

        return result;
    }

    public static void loadProps(String uri, Map map) {
        CheckUtils.notNull(map, "map");
        map.putAll(loadProps(uri));
    }

    public static List<Map.Entry<String, String>> loadList(String uri) {
        OrderedProperties props = new OrderedProperties();
        List<Map.Entry<String, String>> list = new ArrayList();
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(uri);

        try {
            props.loadList(is, list);
        } catch (Exception var12) {
            throw new RuntimeException("load resource fail, uri:" + uri + " errorMsg:" + var12.getMessage(), var12);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException var11) {
                    ;
                }
            }

        }

        return list;
    }

    public static Object newInstance(String className) {
        try {
            return Class.forName(className).newInstance();
        } catch (Exception var2) {
            throw new RuntimeException("new instance fail : " + var2.getMessage(), var2);
        }
    }

    public boolean isTestMode() {
        return "test".equals(System.getProperty("runMode"));
    }

    public boolean isTestMode(String funcName) {
        return this.isTestMode() ? true : "on".equals(System.getProperty("testMode." + funcName));
    }
}

