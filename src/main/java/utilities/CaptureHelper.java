package utilities;

import base.AppDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

public class CaptureHelper {
    public static void captureScreenShotOf(WebElement element, String fileName){
        File newImg = element.getScreenshotAs(OutputType.FILE);
        try{
            FileUtils.copyFile(newImg, new File("./screenshot/"+fileName+".jpg"));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void captureFullPageShot(String fileName){
        File newImg = ((FirefoxDriver) AppDriverManager.getCurrentDriver()).getFullPageScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(newImg, new File("./screenshot/"+ fileName+ ".jpg"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getScreenshot(String imageName) throws IOException, IOException {
        TakesScreenshot ts = (TakesScreenshot) AppDriverManager.getCurrentDriver();
        File f = ts.getScreenshotAs(OutputType.FILE);
        String filePath = "./screenshot/"+imageName+".jpg";
        FileUtils.copyFile(f, new File(filePath));
        return filePath;
    }

    public static String convertImg_Base64(String screenshotPath) throws IOException, IOException {
        byte[] file = FileUtils.readFileToByteArray(new File(screenshotPath));
        String base64Img = Base64.getEncoder().encodeToString(file);
        return  base64Img;
    }

}
