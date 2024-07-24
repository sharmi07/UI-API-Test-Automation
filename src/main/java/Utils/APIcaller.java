package Utils;

import BaseFramework.GlobalStore;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;



import static io.restassured.RestAssured.given;

/**
 * API calls using rest assured
 * 
 * @author sharmidha
 *
 */
public class APIcaller {

	/**
	 * This method calls a get API 
	 *
	 * @param url
	 * @param content
	 * @return JSON object of response
	 */
	public static JsonPath getWithParameters( String url, String content, String parameter) {
		System.out.println("Calling API:"+GlobalStore.apiURL+url+parameter);
		Response res = given().contentType(content).when().get(GlobalStore.apiURL+url+parameter);
		return res.jsonPath();
	}

	/**
	 * This method calls a get API without Auth
	 *
	 * @param url
	 * @param json
	 * @return JSON object of response
	 */
	public static JsonPath getWithoutAuth(String url, ContentType json, String parameter) {
		Response res = given().header("Origin", GlobalStore.apiURL).contentType(json).when().get(url+parameter);
		return res.jsonPath();
	}

	/**
	 * This method calls a get API and returns status code
	 *
	 * @param url
	 * @param json
	 * @return status code returned from API response
	 */
	public static int getStatusCode(String url, ContentType json, String parameter) {
		Response res = given().header("Origin", GlobalStore.apiURL).contentType(json).when().get(url+parameter);
		return res.getStatusCode();
	}

	/**
	 * POST call with body
	 * 
	 * @param url
	 * @param json
	 * @param requestbody
	 * @return JSON object of response
	 */
	public static JsonPath post(String url, ContentType json, String requestbody) {
		Response res = given().header("Origin", GlobalStore.apiURL).contentType(json).body(requestbody).when().post(GlobalStore.apiURL+url);
		return res.jsonPath();
	}
	
	/**
	 * Delete call
	 * @param url
	 * @param json
	 * @param Auth
	 * @param UserId
	 * @return status code from delete API response
	 */
	public static int delete(String url, ContentType json, String Auth, String UserId) {
		Response res = given().header("Origin", GlobalStore.apiURL).contentType(json).headers("Authorization", Auth).when().delete(url + "/" + UserId);
		return res.getStatusCode();
	}


}
