import POJO.dataPOJO;
import POJO.pagesPOJO;
import api.automation.utils.RestUtils;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class FinalFrameworkTest {
    @Test(priority = 1)
    public void checkingStatusCode()
    {
        Response response = get("https://reqres.in/api/users?page=2");
        System.out.println(response.getStatusCode());
        System.out.println(response.getTime());
        System.out.println(response.getBody().asString());
        System.out.println(response.getStatusLine());
        System.out.println(response.getHeader("content-type"));

        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200);
    }
    @Test(priority = 2)
    public void FetchingData() {
        baseURI = "https://reqres.in/api";

        given().
                get("/users?page=2").
                then().assertThat().statusCode(200).
                body("data[1].id", equalTo(8))
                .log().all();
    }
    @Test(priority = 3)
    public void AssertionHamcrestValidation_1()
    {
        baseURI = "https://reqres.in/api";
        given().
                get("/users?page=2").
                then().assertThat().statusCode(200).
                body("data.id[1]", equalTo(8))
                .and().body("data[1].first_name", is(equalToIgnoringCase("lindsay")))
                .and().body("data[1].avatar", startsWith("https"))
                .and().body("data[1].avatar", endsWith(".jpg"))
                .and().body("data.avatar[1]", containsString("//reqres.in/")).log().all();
    }
    @Test(priority = 4)
    public void AssertionHamcrestValidation_2()
    {
        baseURI = "https://reqres.in/api";
        given().
                get("/users/?data=2").
                then().assertThat().statusCode(200)
                .body("data[0]", hasKey("last_name"))
                .and().body("data[1]", hasValue("Weaver"))
                .and().body("data[1]", hasEntry("last_name","Weaver"));
    }
    @Test(priority = 5)
    public void addUserByDataPOJO()
    {
        String url = "https://reqres.in/api/users?page=2";
        RestUtils utils = new RestUtils();
        //EmailAddressPOJO users = new EmailAddressPOJO();
        dataPOJO data = new dataPOJO();
        Map<String, String> testdata = new HashMap<String , String>();
//        testdata.put("page", "3");
//        testdata.put("per_page", "7");
//        testdata.put("total", "12");
//        testdata.put("total_pages","2");
        testdata.put("id","10");
        testdata.put("email", "nive@gmail.com");
        testdata.put("first_name","Niveditha");
        testdata.put("last_name", "H R");
        String payload = data.getUserPayload(testdata);
        Response response = utils.post(url, payload);
        response.prettyPrint();
    }
    @Test(priority = 6)
    public  void addPagesBYPagesPOJO() {
        String url = "https://reqres.in/api/users?page=2";
        RestUtils utils = new RestUtils();
        pagesPOJO page = new pagesPOJO();
        Map<String, String> testdata = new HashMap<String, String>();
        testdata.put("page", "3");
        testdata.put("per_page", "7");
        testdata.put("total", "12");
        testdata.put("total_pages", "2");
        testdata.put("id", "13");
        String payload = page.getUserPayload(testdata);
        Response response = utils.post(url, payload);
        response.prettyPrint();
    }
    @Test(priority = 7)
    public void getParticularUsersById()
    {
        String url = "https://reqres.in/api/users";
        String key = "id";
        int value = 10;
        RestUtils restUtils = new RestUtils();

        Map<String,Integer> map = new HashMap<String, Integer>();
        map.put(key,value);
        Response response = restUtils.getWithParams(url,map);
        //system.out.println(response.body().asString());
        response.prettyPrint();
    }
    @Test(priority = 8)
    public void updateUserByDataPOJO()
    {
        String url = "https://reqres.in/api/users/2";
        RestUtils Utils = new RestUtils();
         dataPOJO user = new dataPOJO();
        Map<String, String> testData = new HashMap<String, String>();
        testData.put("email", "Nive@123");
        testData.put("password", "Niv123");

        String payload = user.updateUserPayload(testData);
        Response response = Utils.post(url, payload);
        response.prettyPrint();
    }
    @Test(priority = 9)
    public void updateUserByPagesPOJO() {
        String url = "https://reqres.in/api/users/2";
        RestUtils Utils = new RestUtils();
        dataPOJO user = new dataPOJO();
        Map<String, String> testData = new HashMap<String, String>();
        testData.put("page", "7");
        testData.put("total_pages", "5");

        String payload = user.updateUserPayload(testData);
        Response response = Utils.post(url, payload);
        response.prettyPrint();
    }
    //1- REGISTER - SUCCESSFUL (Add assert for the response with status code - 200)
    @Test(priority = 10)
    public void RegisterSuccessful() {
        Map<String, Object> map = new HashMap<String, Object>();

        JSONObject request = new JSONObject(map);
        request.put("email", "eve.holt@reqres.in");
        request.put("password", "pistol");
        System.out.println(request.toJSONString());
        baseURI = "https://reqres.in/api";

        given().
                header("Content-Type", "application/json").
                contentType(ContentType.JSON).accept(ContentType.JSON).
                body(request.toJSONString()).
                when().post("/register").
                then().assertThat().statusCode(200).
                log().all();
    }
    //2- REGISTER - UNSUCCESSFUL (Add assert for error message and status code - 400)
    @Test(priority = 11)
    public void RegisterUnSuccessful() {
        Map<String, Object> map = new HashMap<String, Object>();

        JSONObject request = new JSONObject(map);
        request.put("email", "sydney@fife");
        request.put("password", "");
        System.out.println(request.toJSONString());
        baseURI = "https://reqres.in/api";

        given().
                header("Content-Type", "application/json").
                contentType(ContentType.JSON).accept(ContentType.JSON).
                body(request.toJSONString()).
                when().post("/register").
                then().assertThat().statusCode(400).
                log().all();
    }
    //3- LOGIN - SUCCESSFUL (Add assert for the response with status code - 200)
    @Test(priority = 12)
    public void LoginSuccessful() {
        Map<String, Object> map = new HashMap<String, Object>();

        JSONObject request = new JSONObject(map);
        request.put("email", "eve.holt@reqres.in");
        request.put("password", "cityslicka");
        System.out.println(request.toJSONString());
        baseURI = "https://reqres.in/api";

        given().
                header("Content-Type", "application/json").
                contentType(ContentType.JSON).accept(ContentType.JSON).
                body(request.toJSONString()).
                when().post("/login").
                then().assertThat().statusCode(200).
                log().all();
    }
    //4- LOGIN - UNSUCCESSFUL (Add assert for error message and status code - 400)
    @Test(priority = 13)
    public void LoginUnSuccessful() {
        Map<String, Object> map = new HashMap<String, Object>();

        JSONObject request = new JSONObject(map);
        request.put("email", "peter@klaven");
        request.put("password", "");
        System.out.println(request.toJSONString());
        baseURI = "https://reqres.in/api";

        given().
                header("Content-Type", "application/json").
                contentType(ContentType.JSON).accept(ContentType.JSON).
                body(request.toJSONString()).
                when().post("/login").
                then().assertThat().statusCode(400).
                log().all();
    }

}
