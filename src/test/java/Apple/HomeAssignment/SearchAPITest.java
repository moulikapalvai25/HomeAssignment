package Apple.HomeAssignment;

import io.restassured.RestAssured;
import io.restassured.RestAssured.*;
import io.restassured.matcher.RestAssuredMatchers.*;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import org.hamcrest.Matchers.*;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SearchAPITest 
{   
	
	
	@DataProvider
	public Object[][] getDataSheet1() {
		return ReadDataFromExcel.readData("keyvalues");
	}
   
	@DataProvider
	public Object[][] getDataSheet2() {
		return ReadDataFromExcel.readData("multiplekeyvalues");
	}
	
	@DataProvider
	public Object[][] getDataSheet3() {
		return ReadDataFromExcel.readData("invalidkeyvalues");
	}
	
	//This test is to ensure whether all the valid key value pairs individually are able to hit the api and get the valid response 200
	@Test(dataProvider="getDataSheet1")
	public void searchAPITest(Object key,Object value) {
		 RestAssured.baseURI="https://itunes.apple.com/search";
		 RequestSpecification httpRequest = RestAssured.given();
		 Response response = httpRequest.queryParam(key.toString(), value.toString())
				             .when()
				             .get();
		 Assert.assertTrue((response.statusCode()==200),"Response status is "+response.statusCode());
	   }
	
	//Test to validate valid multiple key value pairs are able to successfully get the search response 
	@Test(dataProvider="getDataSheet2")
	public void searchAPITestMultiKeyValues(Object key1,Object value1,Object key2,Object value2,Object key3,Object value3,Object key4,Object value4,Object key5,Object value5) {
		
		RestAssured.baseURI="https://itunes.apple.com/search";
		RequestSpecification httpRequest = RestAssured.given();
	    Response response = httpRequest.queryParam(key1.toString(), value1.toString())
		       .queryParam(key2.toString(), value2.toString())
		       .queryParam(key3.toString(), value3.toString())
		       .queryParam(key4.toString(), value4.toString())
		       .queryParam(key5.toString(), value5.toString())
		       .when()
		       .get();
	    JsonPath jsonPathEvaluator = response.jsonPath();
	    int resultCount = jsonPathEvaluator.get("resultCount");
		Assert.assertTrue(resultCount>=0,"Result count is "+resultCount);
		Assert.assertTrue(response.statusCode()==200,"Response status is "+response.statusCode());
	   
	}
	
	//Test to ensure whether user is getting 400 error in the case of invalid key values
	@Test(dataProvider="getDataSheet3")
	public void searchAPITestInvalidValues(Object key,Object value,String expMessage) {
		
		 RestAssured.baseURI="https://itunes.apple.com/search";
		 RequestSpecification httpRequest = RestAssured.given();
		 Response response = httpRequest.queryParam(key.toString(), value.toString())
				             .when()
				             .get();
		 int statusCode = response.getStatusCode();
		 JsonPath jsonPathEvaluator = response.jsonPath();
		 String actMessage = jsonPathEvaluator.get("errorMessage");
		 Assert.assertEquals(actMessage, expMessage,"Message thrown is "+actMessage+" not as expected "+expMessage);
		 Assert.assertTrue(statusCode==400,"status code is not as expected which is "+statusCode);
		 
	}
	
}



	
	
	
	
	

