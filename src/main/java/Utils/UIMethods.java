package Utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.Duration;
import java.util.List;
import java.util.Set;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import BaseFramework.GlobalStore;

public class UIMethods {
    static int MaxInteractiveWaitTime = 15;
    static int pageLoadTime = 30;
    GlobalStore GS = new GlobalStore();

    
    /**
     * Wait for the page to load with default page load time 30secs
     * @param driver
     */
    public static void waitForPageLoad(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver,  Duration.ofSeconds(pageLoadTime));
        wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState")
                .equals("complete"));
    }


    /************ CLICK HELPER METHODS *********************/

    /**
     * Clicks on link
     *
     * @param UIElement
     * @param driver
     * @return
     *         false if exception thrown, true otherwise
     */
    public boolean ClickOnLink(WebElement UIElement, WebDriver driver) {
        boolean status = false;
        WebDriverWait wait = null;
        try {
            wait = new WebDriverWait(driver,  Duration.ofSeconds(pageLoadTime));
            wait.until( new ExpectedCondition<Boolean>() {
                @Override
                public Boolean apply( WebDriver webDriver ) {
                    try {
                        UIElement.click();
                    } catch ( StaleElementReferenceException e ) {
                        GS.reportStep( e.getMessage() + "\n", "INFO", driver);
                        GS.reportStep( "Trying again...", "INFO", driver);
                    }
                }
            } );
            status = true;
        } catch (NoSuchElementException NSE) {
            GS.reportStep(NSE.getMessage(), "FAIL", driver);
        } catch (TimeoutException TO) {
            GS.reportStep(TO.getMessage(), "FAIL",driver);
        } catch (Exception E) {
            GS.reportStep(E.getMessage(), "FAIL_FAIL", driver);
        }
        return status;
    }

    /**
     * Checks for the presence of element and clicks
     *
     * @param UIElement
     * @param driver
     * @return
     *         false if exception thrown, true otherwise
     */
    public boolean CheckButtonExistsAndClick(WebElement UIElement, WebDriver driver) {
        boolean status = false;
        WebDriverWait wait = null;
        try {
            wait = new WebDriverWait(driver,  Duration.ofSeconds(pageLoadTime));
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(UIElement));
            mouseOverEventOnWebElement(UIElement, driver);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", UIElement);
            status = true;
        } catch (NoSuchElementException NSE) {
            GS.reportStep(NSE.getMessage(), "FAIL", driver);
        } catch (TimeoutException TO) {
            GS.reportStep(TO.getMessage(), "FAIL", driver);
        } catch (Exception E) {
            GS.reportStep(E.getMessage(), "FAIL_FAIL", driver);
        }
        return status;

    }

    
    /**
     * This method will click on React JS Drop down
     *
     * @param driver
     * @return
     *    false if exception thrown, true otherwise
     */
    public boolean ClickONReactJSDropDown(WebDriver driver) {
        boolean status = false;
        String xpath = "//div[@class='react-select__indicators css-1wy0on6']//div";
        try {
            WebElement dropDown = driver.findElement(By.xpath(xpath));
            dropDown.click();
            Thread.sleep(2000);
            // Grab the elements
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(12));//Prachi - increased wait by 2 secs
            wait.until(ExpectedConditions.elementToBeClickable((By.className("react-select__menu"))));
            List<WebElement> options = driver.findElements(By.className("react-select__menu"));
            for (WebElement option : options) {
                option.click();
                status = true;
            }
            // Click on downarrow
            dropDown.click();
        } catch (Exception e) {
            GS.reportStep(e.getMessage(), "FAIL",driver);
        }
        return status;
    }


    /**
     * This method clicks the element using Actions class
     * 
     * @param UIElement
     * @param keys
     * @param driver
     * @return
     *    false if exception thrown, true otherwise
     */
    public boolean clickKeys(WebElement UIElement, Keys key, WebDriver driver) {
        boolean status = false;
        WebDriverWait wait = null;
        try {
            wait = new WebDriverWait(driver,  Duration.ofSeconds(pageLoadTime));
            WebElement element = wait.until(ExpectedConditions.visibilityOf(UIElement));
            element.click();
            Actions actions = new Actions(driver);
	    //press down arrow key
	    actions.sendKeys(key).build().perform();
	    //press enter
            actions.sendKeys(key).build().perform();
	    status = true;
            Thread.sleep(GS.waitTime);
        }catch (InterruptedException e) {
            GS.reportStep("Clicking On Security Link not working", "FAIL", driver);
        }
        return status;
    }


    
    /**
     * Mouse Over to the Web Element
     *
     * @param UIElement
     * @param driver
     * @return
     *    true if able to mouse over, false otherwise
     */

    public boolean mouseOverEventOnWebElement(WebElement UIElement, WebDriver driver) {
        boolean status = false;
        WebDriverWait wait = null;
        try {
            wait = new WebDriverWait(driver,  Duration.ofSeconds(pageLoadTime));
            wait.until(ExpectedConditions.visibilityOf(UIElement));
            Actions actions = new Actions(driver);
            ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", UIElement);
            actions.moveToElement(UIElement).perform();
            status = true;
        } catch (NoSuchElementException NSE) {
            GS.reportStep(NSE.getMessage(), "FAIL", driver);
        } catch (TimeoutException TO) {
            GS.reportStep(TO.getMessage(), "FAIL", driver);
        } catch (Exception E) {
            GS.reportStep(E.getMessage(), "FAIL_FAIL", driver);
        }
        return status;

    }


    /****************** TEXT HELPER METHODS **********************/
    
    /**
     * Gets data within the HTML tag
     *
     * @param UIElement
     * @param driver
     * @return
     *         data - text within the tag
     */
    public String getText(WebElement UIElement, WebDriver driver) {
        String data = "NoDataFound";
        WebDriverWait wait = null;
        try {
            wait = new WebDriverWait(driver, Duration.ofSeconds(pageLoadTime));
            wait.until(ExpectedConditions.visibilityOf(UIElement));
            data = UIElement.getText();
        } catch (NoSuchElementException NSE) {
            GS.reportStep(NSE.getMessage(), "FAIL", driver);
            GS.takeScreenShot(driver);
        } catch (TimeoutException TO) {
            GS.reportStep(TO.getMessage(), "FAIL", driver);
            GS.takeScreenShot(driver);
        }
        return data;
    }

    /**
     * It modifies existing text field value to new value 1. Retrieve existing value
     * 2. Add new name to the existing one 3. Now, set this new value to the field
     *
     * @param fieldName
     * @param newName
     * @param driver
     * @return
     *	      true if successfuly modified, false otherwise
     */
    public boolean getTextAndModify(WebElement fieldName, String newName, WebDriver driver) {
        boolean status = false;
        WebDriverWait wait = null;
        try {
            wait = new WebDriverWait(driver,  Duration.ofSeconds(MaxInteractiveWaitTime));
            wait.until(ExpectedConditions.visibilityOf(fieldName));
            Thread.sleep(500);
            String data = fieldName.getAttribute("value");
            if (data != null) {
                isfieldEmpty(fieldName, driver);
                GS.reportStep("\tNew Data entering is:" + (data + newName), "INFO", driver);
                fieldName.sendKeys(data + newName);
            }
            status = true;
        } catch (NoSuchElementException NSE) {
            GS.reportStep(NSE.getMessage(), "FAIL", driver);
            GS.takeScreenShot(driver);
        } catch (TimeoutException TO) {
            GS.reportStep(TO.getMessage(), "FAIL", driver);
            GS.takeScreenShot(driver);
        } catch (Exception e) {
            GS.reportStep(e.getMessage(), "FAIL_FAIL", driver);
            GS.takeScreenShot(driver);
        }
        return status;
    }

   
    /**
     * Gets the url inside href 
     *
     * @param UIElement
     * @param driver
     * @return
     *     data - url link inside href
     */
    public String getUrl(WebElement UIElement, WebDriver driver) {

        String data = "NoDataFound";
        WebDriverWait wait = null;
        try {
            wait = new WebDriverWait(driver, Duration.ofSeconds(MaxInteractiveWaitTime));
            wait.until(ExpectedConditions.visibilityOf(UIElement));
            data = UIElement.getAttribute("href");
            GS.reportStep("DEBUG:  Data read from WEB UI=" + data, "INFO", driver);
        } catch (NoSuchElementException NSE) {
            GS.reportStep(NSE.getMessage(), "FAIL", driver);
        } catch (TimeoutException TO) {
            GS.reportStep(TO.getMessage(), "FAIL", driver);
        }
        GS.reportStep("Data Returning is: " + data, "PASS", driver);
        return data;
    }

    
    /**
    * This method gets the text from an attribute
    *
    * @param UIElement
    * @param attribute
    * @param driver
    * @return
    *   data - text from the attribute of the UIElement
    */
    public String getTextFromAttribute(WebElement UIElement, String attribute, WebDriver driver) {
        String data = "NoDataFound";
        WebDriverWait wait = null;
        try {
            wait = new WebDriverWait(driver,  Duration.ofSeconds(pageLoadTime));
            wait.until(ExpectedConditions.visibilityOf(UIElement));
            data = UIElement.getAttribute(attribute);
        } catch (NoSuchElementException NSE) {
            GS.reportStep(NSE.getMessage(), "FAIL", driver);
        } catch (TimeoutException TO) {
            GS.reportStep(TO.getMessage(), "FAIL", driver);
        }
        GS.reportStep("Data Returning is: " + data, "PASS", driver);
        return data;

    }

    /**
     * This method enters data to text field
     *
     * @param UIElement
     * @param data
     * @param driver
     * @return
     *   true if data is successfully entered, false otherwise
     */
    public boolean enterDataToTextField(WebElement UIElement, String data, WebDriver driver) {
        boolean status = false;
        WebDriverWait wait = null;
        try {
            wait = new WebDriverWait(driver,  Duration.ofSeconds(pageLoadTime));
	    wait.until(ExpectedConditions.visibilityOf(UIElement));
            isfieldEmpty(UIElement, driver);
            UIElement.sendKeys(data);
            status = true;
        } catch (NoSuchElementException NSE) {
            GS.reportStep(NSE.getMessage(), "FAIL", driver);
        } catch (TimeoutException TO) {
            GS.reportStep(TO.getMessage(), "FAIL", driver);
        } catch (Exception E) {
            GS.reportStep(E.getMessage(), "FAIL_FAIL", driver);
        }
        return status;
    }



    /**
     * This method enters the text into text field and presses ENTER Key
     *
     * @param UIElement
     * @param data
     * @param driver
     * @return
     *   true if successfully entered, false otherwise
     */
    public boolean enterTextAndPressEnter(WebElement UIElement, String data, WebDriver driver) {
        boolean status = false;
        WebDriverWait wait = null;
        try {
            wait = new WebDriverWait(driver,  Duration.ofSeconds(pageLoadTime));
            wait.until(ExpectedConditions.visibilityOf(UIElement));
            isfieldEmpty(UIElement, driver);
            UIElement.sendKeys(data);
            Thread.sleep(5000);
            UIElement.sendKeys(Keys.RETURN);
            Thread.sleep(GS.waitTime);
            status = true;
        } catch (NoSuchElementException NSE) {
            GS.reportStep(NSE.getMessage(), "FAIL", driver);
        } catch (TimeoutException TO) {
            GS.reportStep(TO.getMessage(), "FAIL", driver);
        } catch (Exception E) {
            GS.reportStep(E.getMessage(), "FAIL_FAIL", driver);
        }
        return status;
    }

    
     /**
     * This method verifies whether the given text is present in the element
     *
     * @param element
     * @param whatToLookFor
     * @param driver
     * @return
     *   true if text is present, false otherwise
     */
    public boolean verifyTextOfAnElement(WebElement element, String whatToLookFor, WebDriver driver) {
        WebDriverWait wait = null;
        boolean status = false;
        try {
            wait = new WebDriverWait(driver, Duration.ofSeconds(MaxInteractiveWaitTime));
            wait.until(ExpectedConditions.visibilityOf(element));
            boolean isPresent = element.getText().contains(whatToLookFor);
            if (isPresent) {
                GS.reportStep("\tCheck: Expected message(" + whatToLookFor + ") was found", "PASS", driver);
                status = true;
            } else
                GS.reportStep("\tCheck: Expected message(" + whatToLookFor + ") was not found", "FAIL", driver);

        } catch (NoSuchElementException NSE) {
            GS.reportStep(NSE.getMessage(), "FAIL", driver);
        } catch (TimeoutException TO) {
            GS.reportStep(TO.getMessage(), "FAIL", driver);
        } catch (Exception E) {
            GS.reportStep(E.getMessage(), "FAIL_FAIL", driver);
        }

        return status;
    }


     /**
     * This method verifies whether the given text is present in the page
     *
     * @param whatToLookFor
     * @param driver
     * @return
     *   true if text is present, false otherwise
     */
    public boolean verifyTextPresentOnPage(String whatToLookFor,WebDriver driver) {
        boolean status = false;

        try {
            UIMethods.waitForPageLoad(driver);
            boolean isPresent = driver.getPageSource().contains(whatToLookFor);
            if (isPresent) {
                GS.reportStep("\tCheck: Expected message(" + whatToLookFor + ") was found", "PASS", driver);
                status = true;
            } else
                GS.reportStep("\tCheck: Expected message(" + whatToLookFor + ") was not found", "INFO", driver);

        } catch (NoSuchElementException NSE) {
            GS.reportStep(NSE.getMessage(), "FAIL", driver);
        } catch (TimeoutException TO) {
            GS.reportStep(TO.getMessage(), "FAIL", driver);
        } catch (Exception E) {
            GS.reportStep(E.getMessage(), "FAIL_FAIL", driver);
        }

        return status;
    }


   /**
   * This method selects an item from dropdown by entering text
   *
   * @param UIElement
   * @param data
   * @param driver
   * @return
   *     true if successfully selected, false otherwise
   */
    public boolean selectDropDownMenuItemByEnteringText(WebElement UIElement, String data, WebDriver driver) {
        boolean status = false;
        WebDriverWait wait = null;
        try {
            wait = new WebDriverWait(driver, Duration.ofSeconds(MaxInteractiveWaitTime));
            WebElement element = wait.until(ExpectedConditions.visibilityOf(UIElement));
            UIElement.sendKeys(data);
            UIElement.click();
            status = true;
        } catch (NoSuchElementException NSE) {
            GS.reportStep(NSE.getMessage(), "FAIL", driver);
        } catch (TimeoutException TO) {
            GS.reportStep(TO.getMessage(), "FAIL", driver);
        } catch (Exception E) {
            GS.reportStep(E.getMessage(), "FAIL_FAIL", driver);
        }
        return status;
    }



   /************** ELEMENT STATUS HELPER METHODS ***********************/
	
  
    /**
     * This method will return whether the given element is displayed or not
     *
     * @param UIElement
     * @param driver
     * @return
     *   true if displayed, false otherwise
     */
    public boolean isElementPresent(WebElement UIElement, WebDriver driver) {

	WebDriverWait wait = null;
        try {
            wait = new WebDriverWait(driver,  Duration.ofSeconds(pageLoadTime));
            wait.until(ExpectedConditions.visibilityOf(UIElement));
            return UIElement.isDisplayed();
        } catch (NoSuchElementException NSE) {
            GS.reportStep(NSE.getMessage(), "FAIL", driver);
        } catch (TimeoutException TO) {
            GS.reportStep(TO.getMessage(), "FAIL", driver);
        } catch (Exception E) {
            GS.reportStep(E.getMessage(), "FAIL_FAIL", driver);
        }
	return false;
    }

       
    /**
     * This method verifies whether the given tag is present in the current page or not
     *
     * @param tagName
     * @param driver
     * @return
     *    true if tag is present in the page, false otherwise
     */
    public boolean isTagPresent(String tagName, WebDriver driver) {
        boolean status=true;
        WebDriverWait wait = null;
        try {
            wait = new WebDriverWait(driver, Duration.ofSeconds(MaxInteractiveWaitTime));
            WebElement UIElement = driver.findElement(By.tagName(tagName));
            wait.until(ExpectedConditions.visibilityOf(UIElement));
        } catch (NoSuchElementException NSE) {
            status=false;
        } catch (TimeoutException TO) {
            GS.reportStep(TO.getMessage(), "FAIL", driver);
            status=false;
        }

        return status;
    }

    /**
     * This method checks if the given link is found and clickable
     *
     * @param UIElement
     * @param driver
     * @return
     *  true if the link is clickable, false otherwise
     */
    public boolean isLinkPresentAndClickable(WebElement UIElement, WebDriver driver) {
        boolean status = false;
        WebDriverWait wait = null;
        try {
            wait = new WebDriverWait(driver, Duration.ofSeconds(MaxInteractiveWaitTime));
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(UIElement));
            status = true;
        } catch (NoSuchElementException NSE) {
            GS.reportStep(NSE.getMessage(), "FAIL", driver);
        } catch (TimeoutException TO) {
            GS.reportStep(TO.getMessage(), "FAIL", driver);
        } catch (Exception E) {
            GS.reportStep(E.getMessage(), "FAIL_FAIL", driver);
        }
        return status;
    }

    	
    /**
     * This method will verify the status of the checkbox
     * @param UIElement
     * @param driver
     * @return
     *   true if selected, false otherwise
     */
    public static boolean verifyIsSelected(WebElement UIElement, WebDriver driver) {
	WebDriverWait wait = null;
        try {
            wait = new WebDriverWait(driver,  Duration.ofSeconds(pageLoadTime));
            wait.until(ExpectedConditions.visibilityOf(UIElement));
            return UIElement.isSelected();
        } catch (NoSuchElementException NSE) {
            GS.reportStep(NSE.getMessage(), "FAIL", driver);
        } catch (TimeoutException TO) {
            GS.reportStep(TO.getMessage(), "FAIL", driver);
        } catch (Exception E) {
            GS.reportStep(E.getMessage(), "FAIL_FAIL", driver);
        } 
	return false;
    }

     /**
     * This method will return enabled or disabled for the given element
     * @param UIElement
     * @param driver
     * @return
     *   true if enabled, false otherwise
     */
    public static boolean isElementEnabled( WebElement UIElement, WebDriver driver) {
       WebDriverWait wait = null;
        try {
            wait = new WebDriverWait(driver,  Duration.ofSeconds(pageLoadTime));
            wait.until(ExpectedConditions.visibilityOf(UIElement));
            return UIElement.isEnabled();
        } catch (NoSuchElementException NSE) {
            GS.reportStep(NSE.getMessage(), "FAIL", driver);
        } catch (TimeoutException TO) {
            GS.reportStep(TO.getMessage(), "FAIL", driver);
        } catch (Exception E) {
            GS.reportStep(E.getMessage(), "FAIL_FAIL", driver);
        } 
	return false;
    }

    /**
     * This method clears the data from a field
     * Also it sets the cursor back to starting position
     *
     * @param driver
     * @param UIElement
     * @return
     */
    public boolean isfieldEmpty(WebElement UIElement, WebDriver driver) {
        String data = "EMPTY";
        WebDriverWait wait = null;
        boolean status=false;

        try {
            wait = new WebDriverWait(driver, Duration.ofSeconds(MaxInteractiveWaitTime));
            WebElement element = wait.until(ExpectedConditions.visibilityOf(UIElement));
            isElementPresent(driver, element);
            data = UIElement.getAttribute("value");
            UIMethods.waitForPageLoad(driver);
            if(!data.isEmpty()) {
                if(driver.toString().contains("FirefoxDriver"))//Firefox
                {
                    for(int j=0; j<=30;j++)
                    {
                        UIElement.sendKeys(Keys.BACK_SPACE);
                    }
                    status = true;
                }else if(driver.toString().contains("ChromeDriver"))//Chrome
                {
                    for(int j=0; j<=30;j++)
                    {
                        UIElement.sendKeys("\u0008");
                    }
                    status = true;
                }
                else if(driver.toString().contains("EdgeDriver"))//Edge
                {
                    for(int j=0; j<=30;j++)
                    {
                        UIElement.sendKeys("\u0008");
                        UIElement.sendKeys(Keys.BACK_SPACE);
                    }
                    status = true;
                }
                UIMethods.waitForPageLoad(driver);
            }
            else
                return true;
        } catch (NoSuchElementException NSE) {
            GS.reportStep(NSE.getMessage(), "FAIL", driver);
        } catch (TimeoutException TO) {
            GS.reportStep(TO.getMessage(), "FAIL", driver);
        } catch (Exception E) {
            GS.reportStep(E.getMessage(), "FAIL_FAIL", driver);
        }
        return status;

    }




    /************* SWITCH WINDOW/FRAME HELPER METHODS *************************/

    /**
     * This method switches to another window
     * @param driver
     */
    public void switchToAnotherWindow(WebDriver driver)
    {
        try {
            String pwin=driver.getWindowHandle();
            Set<String> win=driver.getWindowHandles();
            win.remove(pwin);
            driver.switchTo().window(win.iterator().next());
            GS.reportStep("Switched to another window", "PASS", driver);
        } catch (Exception e) {
            GS.reportStep("Unable to switch to another window", "FAIL", driver);
        }
    }
   
    /**
     * This method switches frame using webelement
     * @param element
     * @param driver
     */
    public void switchToFrame(WebElement element, WebDriver driver)
    {
        try {
            driver.switchTo().frame(element);
            GS.reportStep("Switched to frame ", "PASS", driver);
        } catch (Exception e) {
            GS.reportStep("There is no frame unable to switch to frame ", "FAIL", driver);
        }
    }






}
