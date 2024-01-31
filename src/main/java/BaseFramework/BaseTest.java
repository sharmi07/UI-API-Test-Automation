package BaseFramework;

import Utils.AllureListener;
import Utils.DataLoader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.*;

import java.lang.reflect.Method;

@Listeners({ AllureListener.class})
public class BaseTest extends GlobalStore {

    public static String appURL;
    public static String apiURL;

    GlobalStore GS = new GlobalStore();
    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() {
        appURL = GS.getAppURL();
        apiURL = GS.getApiURL();
    }

    @BeforeMethod(alwaysRun = true)
    public void setupDriver() {

        String browserType = "Chrome";
        switch (browserType) {
            case "Firefox":
                WebDriverManager.firefoxdriver().setup();
                DriverThreadManager.setDriver(new FirefoxDriver());
                break;
            case "Safari":
                WebDriverManager.safaridriver().setup();
                DriverThreadManager.setDriver(new SafariDriver());
                break;
            case "Edge":
                WebDriverManager.edgedriver().setup();
                DriverThreadManager.setDriver(new EdgeDriver());
                break;
            case "Chrome":
            default:
                WebDriverManager.chromedriver().setup();
                DriverThreadManager.setDriver(new ChromeDriver());
                break;
        }
    }

    @AfterMethod
    public void closeDriver() throws InterruptedException {
        reportStep("Thread ID in aftermethod is:" + String.valueOf(Thread.currentThread().getId()), "INFO");
        reportStep("Thread Name in aftermethod is:" + Thread.currentThread().getName(), "INFO");
        DriverThreadManager.unload();
    }

    @AfterTest
    public void afterTest() {
        reportStep("TESTNG: END of (Test) execution", "INFO");
    }

    @AfterSuite
    public void afterSuite() {
        reportStep("TESTNG: END of (Suite) execution.", "INFO");
    }

    /**
     * This helper method reads data from test case data sheet file and returns to
     * test cases.
     *
     * @param m
     * @return.
     */
    @DataProvider(name = "readTestData")
    protected Object[][] readTestData(Method m) {
        String methodName = m.getName();
        reportStep("Method Name is: " + methodName, "INFO");
        DataLoader DL = new DataLoader();
        String data[] = DL.getDataSheetName(methodName);
        return DL.getAllSheetData(data[0], data[1]);
    }
}
