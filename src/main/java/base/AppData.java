package base;


public class AppData {
    public static String platform = System.getProperty("platform", "android");
    public static String useGesturePlugin = System.getProperty("useGesturePlugin", "true");
    public static String chromeAutoDownloadDriver = System.getProperty("chromeAutoDownloadDriver", "true");
    public static byte LONG_TIMEOUT = 30;
    public static byte SHORT_TIMEOUT = 5;
}

