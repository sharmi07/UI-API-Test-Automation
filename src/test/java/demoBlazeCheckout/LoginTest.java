package demoBlazeCheckout;

import AppPages.HomePage;
import BaseFramework.BaseTest;
import BaseFramework.DriverThreadManager;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Set;

public class LoginTest extends BaseTest {
    WebDriver driver;
    @BeforeClass
    public void setUP() {
        reportStep("Executing LoginTest", "INFO");
    }

    @Description("Verify signing up...")
    @Epic("SignUp/Login")
    @Feature("UserCreation")
    @Test()
    public void verifySigningUpTest() throws Exception {
        reportStep("Execution STARTED for the test: verifySigningUpTest", "INFO");
        reportStep("Step 1: Signing up...", "INFO");
        driver = DriverThreadManager.getDriver();
        driver.get(appURL);

        HomePage hp = new HomePage(driver);
        hp.signUpInPortal(driver);
        reportStep("PASSED: Signed up successfully", "PASS",driver);
        reportStep("Execution COMPLETED for the test: verifySigningUpTest", "INFO");
    }

   @Description("Verify signing up...")
    @Epic("SignUp/Login")
    @Feature("UserCreation")
    @Test()
    public void verifyLoginTest() throws Exception {
        reportStep("Execution STARTED for the test: verifyLoginTest", "INFO");
        reportStep("Step 1: Logging in...", "INFO");
        driver = DriverThreadManager.getDriver();
        driver.get(appURL);

        HomePage hp = new HomePage(driver);
        hp.loginIntoPortal(driver, "admin", "admin");
        reportStep("PASSED: Signed up successfully", "PASS",driver);
        reportStep("Execution COMPLETED for the test: verifyLoginTest", "INFO");
    }

    @AfterClass
    public void tearDown() {
        reportStep("TESTNG: Executing After class....verifySigningUP", "INFO");
    }

}
