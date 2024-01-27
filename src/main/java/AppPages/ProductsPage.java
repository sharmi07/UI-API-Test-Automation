package AppPages;

import BaseFramework.GlobalStore;
import Utils.CommonUtils;
import Utils.UIMethods;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProductsPage extends BasePage{

    GlobalStore GS = new GlobalStore();
    UIMethods UI = new UIMethods();
    CommonUtils util = new CommonUtils();
    @FindBy(xpath = "//div[contains(@class,'list-group')]/a[2]")
    WebElement phonesLink;

    @FindBy(xpath = "//div[contains(@class,'list-group')]/a[3]")
    WebElement laptopsLink;

    @FindBy(xpath = "//div[contains(@class,'list-group')]/a[3]")
    WebElement monitorsLink;

    @FindBy(xpath = "//a[@id='loginpassword']")
    WebElement loginPasswordField;



    public ProductsPage(WebDriver driver) {
        super(driver);
        //localDriver = driver;
        GS.reportStep("Products page Loading...", "INFO", driver);
        // Wait until some field is visible
        boolean st = UI.waitForThisFieldToBeVisible(phonesLink, driver);
        if (!st)
            GS.reportStep("Productpage elements are not fully loaded yet,....", "FAIL",driver);
    }

}
