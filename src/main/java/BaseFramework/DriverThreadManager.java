package BaseFramework;

import org.openqa.selenium.WebDriver;

/*
 * Manages webdriver instance while running multiple tests in parallel
 */
public final class DriverThreadManager {

    private DriverThreadManager() {

    }
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    public static void setDriver(WebDriver driverRef) {
        driver.set(driverRef);
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    /* deleting reference of driver thread to avoid memory leak */
    public static void unload() {
        getDriver().quit();
        driver.remove();
    }
}
