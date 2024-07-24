package petStoreApiTests;

import BaseFramework.BaseTest;
import Pojos.Category;
import Pojos.Pet;
import Pojos.PetDemo;
import Pojos.Tags;
import Utils.APIcaller;
import Utils.CommonUtils;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class petTest extends BaseTest {
    WebDriver driver;
    CommonUtils util = new CommonUtils();
    @BeforeClass
    public void setUP() {
        reportStep("Executing petTest", "INFO");
    }


    @Description("Verify adding new pet...")
    @Epic("Pet")
    @Feature("Post call")
    @Test(dataProviderClass = BaseTest.class , dataProvider="readTestData")
    public void verifyAddingPetTest(String petId, String petName, String categoryId, String categoryName,
                                    String url, String tagId, String tagName, String status, String word) throws Exception {
        reportStep("Execution STARTED for the test: verifyGettingPetTest", "INFO");

        List<String> Urls = new ArrayList<>();
        Urls.add(url);

        Category cat = new Category(
                (int) Float.parseFloat(categoryId), categoryName
        );

        List<Tags> tagsList = new ArrayList<>();
        Tags tag = new Tags(
                (int) Float.parseFloat(tagId), tagName
        );
        tagsList.add(tag);

        Pet p = new Pet(
                (int) Float.parseFloat(petId), petName, cat, Urls, tagsList, status
        );

        PetDemo demo = new PetDemo();
        demo.mapRequest(p);
        String requestBody = util.generateStringFromResource("target/pet.json");
        JsonPath res = APIcaller.post("/pet", ContentType.JSON, requestBody );
        String petName2 = res.getString("name");

        if(petName2.equals(petName))
            reportStep("PASSED: Pet name returned is "+petName, "PASS",driver);
        else
            reportStep("FAILED: Pet name returned is not "+petName, "FAIL_FAIL",driver);

        reportStep("Execution COMPLETED for the test: verifyGettingPetTest", "INFO");
    }

    @Description("Verify getting pet name with id...")
    @Epic("Pet")
    @Feature("Get call")
    @Test()
    public void verifyGettingPetTest() throws Exception {
        reportStep("Execution STARTED for the test: verifyGettingPetTest", "INFO");
        JsonPath res = APIcaller.getWithParameters("/pet/", "application/json","129" );
        String petName = res.getString("name");

        if(petName.equals("Ishu"))
            reportStep("PASSED: Pet name returned is Ishu ", "PASS",driver);
        else
            reportStep("FAILED: Pet name returned is not Ishu ", "FAIL_FAIL",driver);

        reportStep("Execution COMPLETED for the test: verifyGettingPetTest", "INFO");
    }

}
