package com.orangehrm.test;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.orangehrm.utilities.APIUtility;
import com.orangehrm.utilities.ExtentManager;

import io.restassured.response.Response;

public class APITest {
	
	@Test
	public void verifyGetUserAPI() {
		
		SoftAssert softAssert = new SoftAssert();

		// step-1: Define API endPOint
		String endPoint = "https://jsonplaceholder.typicode.com/users/1";
		ExtentManager.logStep("API endPoint: " + endPoint);

		// S-2: Send GET req
		ExtentManager.logStep("Sending GET req to the API");
		Response response = APIUtility.sendGetRequest(endPoint);

		// s-3:Validate status code
		ExtentManager.logStep("Validating API response status code");
		boolean isStatusCodeValid = APIUtility.validateStatusCode(response, 200);

		softAssert.assertTrue(isStatusCodeValid, "Status code is not as Expected");

		if (isStatusCodeValid) {
			ExtentManager.logStepValidationAPI("Status Code validation Passed");

		} else {
			ExtentManager.logFailureAPI("Status code Validation failed");
		}

		// s-4: validate username
		ExtentManager.logStep("Validating response body for username");
		String userName = APIUtility.getJSONValue(response, "username");
		boolean isUserNameValid = "Bret".equals(userName);
		softAssert.assertTrue(isUserNameValid, "username is not valid");

		if (isUserNameValid) {
			ExtentManager.logStepValidationAPI("username validation passed!");
		} else {
			ExtentManager.logFailureAPI("username validation failed!");
		}

		// s-4: validate email
		ExtentManager.logStep("Validating response body for email");
		String userEmail = APIUtility.getJSONValue(response, "email");
		boolean isEmailValid = "Bret".equals(userName);
		softAssert.assertTrue(isEmailValid, "email is not valid");

		if (isUserNameValid) {
			ExtentManager.logStepValidationAPI("email validation passed!");
		} else {
			ExtentManager.logFailureAPI("email validation failed!");
		}
		
		softAssert.assertAll();
	}
}
