package myProject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class arraytest {

    public static void main(String[] args){
      // WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.get("http://google.com");
        System.out.println(driver.getCurrentUrl());
        System.out.println(driver.getTitle());
        driver.findElement(By.tagName("textArea")).sendKeys("when was google launched?");
        String text = "testhello";
        int index=0;
        if(text.contains("hello"))
            index = text.indexOf("hello");
        System.out.println(index);
        driver.quit();

    }

   @DataProvider(name="DP1")
    public Object[][] getData(){
        int[] a1 = {1,2};
        int[] a2 = {3,4};
        int[] merged1 = {1,2,3,4};
        int[] merged2 = {3,4,1,2};
        return new Object[][]{{a1,a2,merged1},{a2,a1,merged2}};
    }


    @Test(dataProvider="DP1")
    public void testMergeArray(int[] a1, int[] a2, int[] merged){
        int[] actual=new int[a1.length+a2.length];
        int j =0;
        for(int i=0;i<a1.length;i++){
            actual[j] = a1[i];
            j++;
        }
        for(int i=0;i<a2.length;i++){
            actual[j] = a2[i];
            j++;
        }
        Assert.assertEquals(actual, merged);
    }
}
