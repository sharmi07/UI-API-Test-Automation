package AppPages;

import BaseFramework.GlobalStore;
import Utils.CommonUtils;
import Utils.UIMethods;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage extends BasePage{

    GlobalStore GS = new GlobalStore();
    UIMethods UI = new UIMethods();
    CommonUtils util = new CommonUtils();
    @FindBy(xpath = "//a[@id='login2']")
    WebElement loginLink;

    @FindBy(xpath = "//a[@id='signin2']")
    WebElement signUpLink;

    @FindBy(xpath = "//a[@id='loginusername']")
    WebElement loginUnameField;

    @FindBy(xpath = "//a[@id='loginpassword']")
    WebElement loginPasswordField;

    @FindBy(xpath = "//div[@id='logInModal']")
    WebElement loginModal;

    @FindBy(xpath = "//div[@id='logInModal']//button[contains(@class,'primary')]")
    WebElement loginButton;

    @FindBy(xpath = "//div[@id='signInModal']")
    WebElement signinModal;

    @FindBy(xpath = "//input[@id='sign-username']")
    WebElement signUnameField;

    @FindBy(xpath = "//input[@id='sign-password']")
    WebElement signPasswordField;

    @FindBy(xpath = "//div[@id='signInModal']//button[contains(@class,'primary')]")
    WebElement signUpButton;

    public HomePage(WebDriver driver) {
        super(driver);
        //localDriver = driver;
        GS.reportStep("Home page Loading...", "INFO", driver);
        // Wait until some field is visible
        boolean st = UI.waitForThisFieldToBeVisible(loginLink, driver);
        if (!st)
            GS.reportStep("Homepage elements are not fully loaded yet,....", "FAIL",driver);
    }

    public HomePage clickLoginLink(WebDriver driver) {
        GS.reportStep("Clicking on login link", "INFO", driver);
        UI.ClickOnLink(loginLink, driver);
        return this;
    }

    public HomePage clickSignUpLink(WebDriver driver) {
        GS.reportStep("Clicking on sign up link", "INFO", driver);
        UI.ClickOnLink(signUpLink, driver);
        return this;
    }

    public HomePage signUpInPortal(WebDriver driver) {
        GS.reportStep("Signing up with username and password", "INFO", driver);
        clickSignUpLink(driver);
       // driver.switchTo().frame(signinModal);
        UI.waitForThisFieldToBeVisible(signinModal, driver);
        UI.enterDataToTextField(signinModal.findElement(By.xpath(".//input[@id='sign-username']")), driver, util.randomGenerator("hello", 10, "string"));
        UI.enterDataToTextField(signinModal.findElement(By.xpath(".//input[@id='sign-password']")), driver, util.randomGenerator("hello", 10, "string")+"10!");
        UI.ClickOnLink(signUpButton,driver);
        acceptAlertPopup(driver);
        return this;
    }

    public HomePage acceptAlertPopup(WebDriver driver){
        new WebDriverWait(driver, Duration.ofSeconds(60)).ignoring(NoAlertPresentException.class)
                .until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        System.out.println(alert.getText());
        alert.accept();
        return this;
    }

    public HomePage loginIntoPortal(WebDriver driver, String username, String password) {
        GS.reportStep("Logging in with username and password", "INFO", driver);
        clickLoginLink(driver);
        // driver.switchTo().frame(signinModal);
        UI.waitForThisFieldToBeVisible(loginModal, driver);
        UI.enterDataToTextField(loginModal.findElement(By.xpath(".//input[@id='loginusername']")), driver, username);
        UI.enterDataToTextField(loginModal.findElement(By.xpath(".//input[@id='loginpassword']")), driver, password);
        UI.ClickOnLink(loginButton,driver);
        return this;
    }
}
