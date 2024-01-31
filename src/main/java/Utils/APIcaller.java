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

	//private static String AdminEmail = "jenkins_availadmin@avail.io";
	//private static String AdminPassword = "Default123$";


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
	 * This method calls a get API
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
	 * @return
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
	 * @return
	 */
	public static int delete(String url, ContentType json, String Auth, String UserId) {
		Response res = given().header("Origin", GlobalStore.apiURL).contentType(json).headers("Authorization", Auth).when().delete(url + "/" + UserId);
		return res.getStatusCode();
	}



	/**
	 * Get UUID( unique token) for the user with user ID
	 * 
	 * @param url
	 * @param json
	 * @param Auth
	 * @return
	 */
	/*public static String getUUID(String url, ContentType json, String Auth) {
		//JsonPath res = get(url, json, Auth);
	//	String UUID = res.getString("content.uuid");

		//return UUID;
	}
	
	/**
	 * Delete User with User Id having specific Email Prefix
	 * 
	 * @param authUrl
	 * @param json
	 * @param AdminEmail
	 * @param AdminPassword
	 * @return
	 */
//	public boolean deleteUserWithUserId(String authUrl, String getUserIdUrl, String deleteUrl, ContentType json, String AdminEmail,
//			String AdminPassword, String emailPrefix, WebDriver driver) {
	//	boolean status = true;
	//	String token = postloginAndGetAccessToken(authUrl,json,AdminEmail,AdminPassword);
	//	JsonPath path = get(getUserIdUrl+emailPrefix, json,"Bearer " + token );//.getString("content.employee.id"); //res.jsonPath().getString("content.access_token");
	//	List<String> userIds = path.getList("content.employee.id");
	//	for (int i = 0; i < userIds.size(); i++) {
	//		status = getUserIdAndDeleteWithEmailPrefix(deleteUrl, json, "Bearer " + token, userIds.get(i), driver);
	//	}
	//	return status;
	//}*/


}
