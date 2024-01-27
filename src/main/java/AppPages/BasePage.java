package AppPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public abstract class BasePage {
    //protected WebDriver driver;
    // GlobalStore GS = new GlobalStore();
    public BasePage(WebDriver driver) {
        //this.driver = driver;
        //  GS.reportStep("In BasePage class initializing PageFactory....., ", "INFO");
        PageFactory.initElements(driver, this);
        //  GS.reportStep("Initialization Done.", "INFO");
    }

}