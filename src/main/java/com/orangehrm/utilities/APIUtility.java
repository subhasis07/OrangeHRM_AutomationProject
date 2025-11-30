package com.orangehrm.utilities;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class APIUtility {
	
	//Method to send GET req
	public static Response sendGetRequest(String endPoint) {
		return RestAssured.get(endPoint);
	}
	
	//Method to send the Response
	public static Response sendPostRequest(String endPoint, String payLoad) {
		return RestAssured.given().header("Content-Type","application/json")
								  .body(payLoad)
								  .post();
	}
	
	//Method to validate the response status
	public static boolean validateStatusCode(Response response, int statusCode) {
		return response.getStatusCode()==statusCode;
	}
	
	//Method to extract value from JSON response
	public static String getJSONValue(Response response, String value) {
		return response.jsonPath().getString(value);
	}
	

}
