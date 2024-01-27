package Utils;

import BaseFramework.GlobalStore;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;

import java.util.Set;

public class AllureListener implements ITestListener {
	
	GlobalStore GS = new GlobalStore();

	public AllureListener() {
	}

	private static String getTestMethodName(ITestResult iTestResult) {
		return iTestResult.getMethod().getConstructorOrMethod().getName();

	}

	@Attachment(value = "{0}", type = "text/plain")
	public static String saveTextLog(String msg) {
		return msg;
	}

	@Attachment
	public byte[] saveFailureScreenShot(WebDriver driver) {
		return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
	}

	@Override
	public void onStart(ITestContext itContext) {
		GS.reportStep("OnStart method...." + itContext.getName(), "INFO");
	}

	@Override
	public void onFinish(ITestContext itContext) {
		GS.reportStep("On Finish method", "INFO");
		Set<ITestResult> failedTests = itContext.getFailedTests().getAllResults();
		for (ITestResult temp : failedTests) {
			ITestNGMethod method = temp.getMethod();
			if (itContext.getFailedTests().getResults(method).size() > 1) {
				failedTests.remove(temp);
			} else {
				if (itContext.getPassedTests().getResults(method).size() > 0) {
					failedTests.remove(temp);
				}
			}
		}

	}

	@Override
	public void onTestFailure(ITestResult ITResult) {
		GS.reportStep("onTestFailure method...." + getTestMethodName(ITResult) + " Failed", "INFO");
		Object testClass = ITResult.getInstance();
	}

	@Override
	public void onTestStart(ITestResult ITestResult) {
	}

	@Override
	public void onTestSuccess(ITestResult ITestResult) {
		GS.reportStep("onTestSuccess method...." + getTestMethodName(ITestResult) + " Passed", "INFO");
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		GS.reportStep("onTestSkipped method...." + getTestMethodName(result) + " Skipped", "INFO");
		onTestFailure(result);
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult ITestResult) {
	}

}
