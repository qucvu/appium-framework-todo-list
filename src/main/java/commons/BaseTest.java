package commons;

import base.AppDriverManager;
import base.AppFactory;
import base.AppiumServer;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.*;
import utilities.CaptureHelper;

import java.io.IOException;
import java.net.MalformedURLException;

public class BaseTest {

    @BeforeTest
    public void beforeTest() throws MalformedURLException {
        AppFactory.launchApp();
    }

    @AfterTest
    private void afterTest() throws MalformedURLException {
        AppDriverManager.closeDriver();
    }

    @BeforeSuite
    public void startServer() {
        AppiumServer.start();
    }

    @AfterSuite
    public void afterSuite() {
        AppiumServer.stop();
    }

    @AfterMethod
    public void afterMethod(ITestResult result) throws IOException {
        if (result.getStatus() == ITestResult.FAILURE){
            CaptureHelper.getScreenshot(result.getTestName());
        }
    }

    protected boolean verifyTrue(boolean condition) {
        boolean pass = true;
        try {
            Assert.assertTrue(condition);
        } catch (Throwable e) {
            pass = false;

            VerificationFailures.getFailures().addFailureForTest(Reporter.getCurrentTestResult(), e);
            Reporter.getCurrentTestResult().setThrowable(e);
        }
        return pass;
    }

    protected boolean verifyFalse(boolean condition) {
        boolean pass = true;
        try {
            Assert.assertFalse(condition);
        } catch (Throwable e) {
            pass = false;
            VerificationFailures.getFailures().addFailureForTest(Reporter.getCurrentTestResult(), e);
            Reporter.getCurrentTestResult().setThrowable(e);
        }
        return pass;
    }

    //    @Step("Verify the expected data is: '{expected}'")
    protected boolean verifyEquals(Object actual, Object expected) {
        boolean pass = true;
        try {
            Assert.assertEquals(actual, expected);
        } catch (Throwable e) {
            pass = false;
            VerificationFailures.getFailures().addFailureForTest(Reporter.getCurrentTestResult(), e);
            Reporter.getCurrentTestResult().setThrowable(e);
        }
        return pass;
    }


}
