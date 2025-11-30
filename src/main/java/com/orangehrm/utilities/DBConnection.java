package com.orangehrm.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class DBConnection {
	private static final String DB_URL = "jdbc:mysql://localhost:3306/orangeHRM";
	private static final String DB_USERNAME = "root";
	private static final String DB_PASSWORD = "";

	public static Connection getDBConnection() {

		try {
			System.out.println("Starting DB connection...");
			Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
			System.out.println("DB connection successful");
			return conn;
		} catch (SQLException e) {
			System.out.println("Error while establishing connection");
			e.printStackTrace();
		}
		return null;

	}

	public static Map<String, String> getEmployeeDetails(String emp_id) {
		String query = "SELECT emp_firstname, emp_middlename, emp_lastname FROM hs_hr_employee WHERE employee_id ="
				+ emp_id;
		Map<String, String> empDetails = new HashMap<String, String>();

		try (Connection conn = getDBConnection();
				Statement stmt = conn.createStatement();
				ResultSet res = stmt.executeQuery(query)) {
			System.out.println("Executing query ..." + query);
			if(res.next()) {
				String firstName=res.getString("emp_firstname");
				String middleName=res.getString("emp_middlename");
				String lastName=res.getString("emp_lastname");
				
				empDetails.put("firstName", firstName);
				empDetails.put("middleName", middleName!=null?middleName:"");
				empDetails.put("lastName", lastName);
				
				System.out.println("Query executed successfully");
				System.out.println("Employee data fetched: "+ empDetails);
			}else {
				System.out.println("Employee not found");
			}
		} catch (Exception e) {
			System.out.println("Error while fetching data");
			e.printStackTrace();
		}
		return empDetails;
	}
}
