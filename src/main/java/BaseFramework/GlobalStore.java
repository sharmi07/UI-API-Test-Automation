package BaseFramework;

import Utils.AllureListener;
import io.qameta.allure.Attachment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.io.File;

public class GlobalStore {

	private static final Logger logger = LogManager.getLogger(GlobalStore.class);
	public static long waitTime;

	public static String appURL;
	public static String apiURL;

	public GlobalStore() {
		/**
		 * Loading config files
		 */
		waitTime = 3000;
		LoggerContext context = (org.apache.logging.log4j.core.LoggerContext) LogManager.getContext(false);
		File file = new File("./resource/log4j2.properties");

		// this will force a reconfiguration
		context.setConfigLocation(file.toURI());

		appURL = "https://www.demoblaze.com/index.html";
		apiURL = "https://petstore.swagger.io/v2";
	}

	public String getApiURL() {
		return apiURL;
	}

	public String getAppURL() {
		return appURL;
	}

	@Attachment
	public byte[] takeScreenShot(WebDriver driver) {

		return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

	}

	/**
	 * Centralized reporting
	 * 
	 * @param msg
	 * @param status
	 */
	public void reportStep(String msg, String status, WebDriver driver) {
		switch (status) {
		case "PASS":
			logger.info(msg);
			if ( driver !=null  ) {
				AllureListener.saveTextLog(msg);
				takeScreenShot(driver);
			break;
			}
		case "FAIL":
			logger.error(msg);
			if ( driver !=null  ) {
				AllureListener.saveTextLog(msg);
				takeScreenShot(driver);
			}
			break;
		case "FAIL_FAIL":
			logger.error(msg);
			if ( driver !=null  ) {
				AllureListener.saveTextLog(msg);
				takeScreenShot(driver);
				Assert.fail(msg);	
			}
			break;			
		case "INFO":
			logger.info(msg);
			AllureListener.saveTextLog(msg);
			break;
		case "TERMINATE":
			break;

		}
	}
	
	public void reportStep(String msg, String status) {
		switch (status) {
		case "PASS":
			logger.info(msg);
			AllureListener.saveTextLog(msg);
			break;
		case "FAIL":
			logger.error(msg);
			AllureListener.saveTextLog(msg);
			break;
		case "FAIL_FAIL":
			logger.error(msg);
			AllureListener.saveTextLog(msg);
			Assert.fail(msg);	
			break;			
		case "INFO":
			logger.info(msg);
			AllureListener.saveTextLog(msg);
			break;
		case "TERMINATE":
			break;

		}
	}

}
