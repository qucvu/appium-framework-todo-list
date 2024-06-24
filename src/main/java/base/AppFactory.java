package base;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import org.testng.SkipException;

import java.net.MalformedURLException;
import java.net.URL;

public class AppFactory {
    private static AppiumDriver driver;
    private static void androidLaunchApp() throws MalformedURLException {
        UiAutomator2Options options = new UiAutomator2Options();
        options.setPlatformVersion("14").setDeviceName("Pixel 8").setAppPackage("com.todoist").setAppActivity(".activity.HomeActivity");
        driver = new AndroidDriver(new URL("http://0.0.0.0:4723"), options);
        AppDriverManager.setDriver(driver);
    }

    private static void iosLaunchApp() throws MalformedURLException {
        XCUITestOptions options = new XCUITestOptions();
        options.setPlatformVersion("14").setDeviceName("Pixel 8").setBundleId("xxxxxx");

        driver = new IOSDriver(new URL("http://0.0.0.0:4723"), options);
        AppDriverManager.setDriver(driver);
    }

    public static void launchApp() throws MalformedURLException {
        if (AppData.platform.contains("android")){
            androidLaunchApp();
        }else if (AppData.platform.contains("ios")){
            iosLaunchApp();
        }else {
            throw new SkipException("Invalid platform name, android/ios");
        }
    }

}
