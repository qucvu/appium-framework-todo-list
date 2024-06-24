package base;

import org.openqa.selenium.WebDriver;

public class AppDriverManager {
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();
    private static AppDriverManager instance = null;

    private AppDriverManager(){

    }

    private static AppDriverManager getInstance(){
        if (instance == null){
            return new AppDriverManager();
        }
        return instance;
    }

    public static WebDriver getCurrentDriver(){
        return getInstance().getDriver();

    }

    public WebDriver getDriver(){
        return driver.get();
    }


    public static void setDriver(WebDriver Driver){
        driver.set(Driver);
    }

    public static void closeDriver(){
        if (instance != null){
            driver.get().quit();
        }
    }

}
