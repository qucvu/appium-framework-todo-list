package commons;

import base.AppData;
import base.AppDriverManager;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.remote.SupportsContextSwitching;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageUIs.TodoListBasePageUI;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class BasePage {
    WebDriverWait explicitWait = new WebDriverWait(AppDriverManager.getCurrentDriver(), Duration.ofSeconds(30));
    private final int longTimeout = AppData.LONG_TIMEOUT;
    private final int shortTimeout = AppData.SHORT_TIMEOUT;

    protected void overrideImplicitTimeout(int shortTimeout) {
        AppDriverManager.getCurrentDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(shortTimeout));
    }

    private By getByLocator(String locatorType) {
        if (locatorType.startsWith("id=") || locatorType.startsWith("ID=") || locatorType.startsWith("Id=")) {
            return By.id(locatorType.substring(3));
        } else if (locatorType.startsWith("css=") || locatorType.startsWith("CSS=") || locatorType.startsWith("Css=")) {
            return By.cssSelector(locatorType.substring(4));
        } else if (locatorType.startsWith("xpath=") || locatorType.startsWith("XPATH=") || locatorType.startsWith("Xpath=")) {
            return By.xpath(locatorType.substring(6));
        } else if (locatorType.startsWith("name=") || locatorType.startsWith("Name=") || locatorType.startsWith("NAME=")) {
            return By.cssSelector(locatorType.substring(5));
        } else if (locatorType.startsWith("uiautomator=") || locatorType.startsWith("Uiautomator=") || locatorType.startsWith("UIAUTOMATOR=")) {
            return new AppiumBy.ByAndroidUIAutomator(locatorType.substring(12));
        } else if (locatorType.startsWith("accessId=") || locatorType.startsWith("AccessId=")) {
            return new AppiumBy.ByAccessibilityId(locatorType.substring(9));
        } else {
            throw new RuntimeException("The locator is not supported");
        }
    }

    protected void sleepInSecond(long second) {
        try {
            Thread.sleep(second * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public void openPageUrl(String pageUrl) {
        AppDriverManager.getCurrentDriver().get(pageUrl);
    }

    public String getTitle() {
        return AppDriverManager.getCurrentDriver().getTitle();
    }

    public String getCurrentUrl() {
        return AppDriverManager.getCurrentDriver().getCurrentUrl();
    }

    public String getPageSource() {
        return AppDriverManager.getCurrentDriver().getPageSource();
    }

    public void backToPage() {
        AppDriverManager.getCurrentDriver().navigate().back();
    }

    public void refreshCurrentPage() {
        AppDriverManager.getCurrentDriver().navigate().refresh();
    }

    protected Alert waitForAlertPresence() {
        return explicitWait.until(ExpectedConditions.alertIsPresent());
    }

    protected boolean isAlertPresent() {
        boolean presentFlag = false;
        try {
            AppDriverManager.getCurrentDriver().switchTo().alert();
            presentFlag = true;
        } catch (NoAlertPresentException ex) {
            ex.printStackTrace();
        }

        return presentFlag;
    }

    public Set<Cookie> getAllCookies() {
        return AppDriverManager.getCurrentDriver().manage().getCookies();
    }

    public void setCookies(Set<Cookie> cookies) {
        for (Cookie cookie : cookies) {
            AppDriverManager.getCurrentDriver().manage().addCookie(cookie);
        }
        sleepInSecond(1);
    }

    public void acceptAlert() {
        waitForAlertPresence().accept();
    }

    protected void cancelAlert() {
        waitForAlertPresence().dismiss();
    }

    protected String getAlertText() {
        return waitForAlertPresence().getText();
    }

    protected void sendKeyToAlert(String textValue) {
        waitForAlertPresence().sendKeys(textValue);
    }

    protected Set<String> getContexts() {
        return ((SupportsContextSwitching) AppDriverManager.getCurrentDriver()).getContextHandles();
    }

    protected String getCurrentContext() {
        return ((SupportsContextSwitching) AppDriverManager.getCurrentDriver()).getContext();
    }

    public static String getDynamicLocator(String locator, String... dynamicValues) {
        return String.format(locator, (Object[]) dynamicValues);
    }

    private WebElement getElement(String locatorType) {
        return AppDriverManager.getCurrentDriver().findElement(getByLocator(locatorType));
    }

    protected List<WebElement> getListElements(String locatorType) {
        return AppDriverManager.getCurrentDriver().findElements(getByLocator(locatorType));
    }

    protected void clickToElement(String locatorType) {
        getElement(locatorType).click();
    }

    protected void clearElement(String locatorType) {
        WebElement element = getElement(locatorType);
        element.clear();
    }

    protected void sendKeyToElement(String locatorType, String textValue) {
        clearElement(locatorType);
        getElement(locatorType).sendKeys(textValue);
    }

    protected void sendKeyToElement(String locatorType, String textValue, String... dynamicValue) {
        clearElement(locatorType);
        getElement(getDynamicLocator(locatorType, dynamicValue)).sendKeys(textValue);
    }


    protected String getElementText(String locatorType) {
        return getElement(locatorType).getText();
    }

    protected void selectItemInDefaultDropdown(String locatorType, String textItem) {
        new Select(getElement(locatorType)).selectByVisibleText(textItem);
    }

    protected String getSelectedItemDefaultDropdown(String locatorType) {
        return new Select(getElement(locatorType)).getFirstSelectedOption().getText();
    }

    protected List<WebElement> getDefaultDropDownOptions(String locatorType) {
        return new Select(getElement(locatorType)).getOptions();
    }

    protected List<WebElement> getDefaultDropDownAllSelectedOptions(String locatorType) {
        return new Select(getElement(locatorType)).getAllSelectedOptions();
    }

    protected boolean isDefaultDropDownItemscontain(String locatorType, String text) {
        boolean flag = false;
        List<WebElement> els = new Select(getElement(locatorType)).getOptions();
        for (WebElement el : els) {
            if (el.getText().contains(text)) {
                flag = true;
                break;
            }
        }
        return flag;
    }


    protected boolean isDropdownMultiple(String locatorType) {
        return new Select(getElement(locatorType)).isMultiple();
    }

    protected String getElementAttribute(String attributeName, String locatorType) {
        return getElement(locatorType).getAttribute(attributeName);
    }

    protected String getElementCssValue(String locatorType, String propertyName) {
        return getElement(locatorType).getCssValue(propertyName);
    }

    protected int getElementsSize(String locatorType) {
        return getListElements(locatorType).size();
    }

    protected void checkToDefaultCheckboxRadio(String locatorType) {
        WebElement element = getElement(locatorType);
        if (!element.isSelected()) {
            element.click();
        }
    }

    protected boolean isElementDisplayed(String locatorType) {
        try {
            return getElement(locatorType).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    protected boolean isElementUndisplayed(String locatorType) {
        overrideImplicitTimeout(shortTimeout);
        List<WebElement> elements = getListElements(locatorType);
        overrideImplicitTimeout(longTimeout);
        return elements.size() == 0 || !elements.get(0).isDisplayed();
    }

    protected boolean isElementEnabled(String locatorType) {
        return getElement(locatorType).isEnabled();
    }

    protected boolean isElementSelected(String locatorType) {
        return getElement(locatorType).isSelected();
    }

    protected void waitForElementVisibility(String locatorType) {
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(getByLocator(locatorType)));
    }

    protected void waitForElementVisibility(String locatorType, String... dynamicValues) {
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(getByLocator(getDynamicLocator(locatorType, dynamicValues))));
    }


    protected void waitForElementInvisibility(String locatorType) {
        explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(getByLocator(locatorType)));

    }

    protected void waitForAllElementVisibility(String locatorType) {
        explicitWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(getByLocator(locatorType)));

    }

    protected void waitForAllElementInVisibility(String locatorType) {
        explicitWait.until(ExpectedConditions.invisibilityOfAllElements(getListElements(locatorType)));
    }

    protected void waitForElementClickable(String locatorType) {
        WebDriverWait explicitWait = new WebDriverWait(AppDriverManager.getCurrentDriver(), Duration.ofSeconds(longTimeout));
        explicitWait.until(ExpectedConditions.elementToBeClickable(getByLocator(locatorType)));
    }

    protected void waitForElementStaleness(String locatorType) {
        explicitWait.until(ExpectedConditions.stalenessOf(getElement(locatorType)));
    }

    public boolean isDataStringSortAscLamDa(String locatorType) {
        List<WebElement> elementList = getListElements(locatorType);
        List<String> dataList = elementList.stream().map(WebElement::getText).toList();
        List<String> sortList = new ArrayList<String>(dataList);
        Collections.sort(sortList);
        return sortList.equals(dataList);
    }

    public boolean isDataStringSortDescLamDa(String locatorType) {
        List<WebElement> elementList = getListElements(locatorType);
        List<String> dataList = elementList.stream().map(WebElement::getText).toList();
        List<String> sortList = new ArrayList<String>(dataList);
        Collections.sort(sortList);
        Collections.reverse(sortList);
        return sortList.equals(dataList);
    }

    /**
     * This method input the specific value to the textbox by id
     *
     * @param textID id of input locator
     * @param value  value send to textbox
     */

    public void enterToDynamicTextboxById(String nameTextbox, String textID, String value) {
        waitForElementVisibility(TodoListBasePageUI.DYNAMIC_TEXTBOX_BY_ID, textID);
        sendKeyToElement(TodoListBasePageUI.DYNAMIC_TEXTBOX_BY_ID, value, textID);
    }


}
