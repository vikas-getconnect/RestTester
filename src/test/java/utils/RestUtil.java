package utils;


import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.assertEquals;

public class RestUtil {
    static Logger logger = LoggerFactory.getLogger(RestUtil.class);
    static ConfigReader apiConfig = new ConfigReader();

    public static JsonPath getJson(Response res) {
        String json = res.asString();
        JsonPath jp = new JsonPath(json);
        return jp;
    }

    public static JSONArray getJsonArray(String json) throws ParseException {

        JSONParser parser = new JSONParser();
        Object obj = parser.parse(json);
        JSONArray array = (JSONArray) obj;
        return array;
    }

    public static Response GET(int responseCode, String url) {
        logger.info("GET " + url);
        Response res = get(url);

        if ((RestAssured.port != -1) && (RestAssured.port != 80))
            logger.info("GET " + RestAssured.baseURI + ":" + RestAssured.port
                    + url + " Expected Response " + responseCode
                    + " Observed Response " + res.getStatusCode());
        else
            logger.info("GET " + RestAssured.baseURI + url
                    + " Expected Response " + responseCode
                    + " Observed Response " + res.getStatusCode());
        assertEquals(res.getStatusCode(), responseCode);
        return res;
    }


    public static Response GET_Password(int responseCode, String username,
                                        String password, String url) {

        Response res = given().auth().basic(username, password).when().get(url);
        if ((RestAssured.port != -1) && (RestAssured.port != 80))
            logger.info("GET " + RestAssured.baseURI + ":" + RestAssured.port
                    + url + " Expected Response " + responseCode
                    + " Observed Response " + res.getStatusCode());
        else
            logger.info("GET " + RestAssured.baseURI + url
                    + " Expected Response " + responseCode
                    + " Observed Response " + res.getStatusCode());
        assertEquals(res.getStatusCode(), responseCode);
        return res;
    }


    public static Response POST_Without_Body(int responseCode, String url) {

        Response res = given().contentType("application/json").when().post(url);

        if ((port != -1) && (port != 80))
            logger.info("POST " + baseURI + ":" + port
                    + url + " Expected Response " + responseCode
                    + " Observed Response " + res.getStatusCode());
        else
            logger.info("POST " + baseURI + url
                    + " Expected Response " + responseCode
                    + " Observed Response " + res.getStatusCode());

        assertEquals(res.getStatusCode(), responseCode);
        return res;
    }

    public static Response cookieEnabledPOST_Without_Body(int responseCode,
                                                          String url, Map<String, String> cookies) {

        Response res = given().contentType("application/json").cookies(cookies)
                .when().post(url);

        if ((RestAssured.port != -1) && (RestAssured.port != 80))
            logger.info("POSTing when " + RestAssured.baseURI + ":"
                    + RestAssured.port + url + " Expected Response "
                    + responseCode + " Observed Response "
                    + res.getStatusCode());
        else
            logger.info("POSTing when " + RestAssured.baseURI + url
                    + " Expected Response " + responseCode
                    + " Observed Response " + res.getStatusCode());
        logger.info("---------------------cookie-------------- : " + cookies);
        assertEquals(res.getStatusCode(), responseCode);
        return res;
    }

    public static Response POST(int responseCode, String requestBody, String url) {

        logger.info(RestAssured.baseURI);
        // RestAssured.urlEncodingEnabled=false;
        Response res = given().contentType("application/json").and()
                .body(requestBody).when().post(url);

        if ((RestAssured.port != -1) && (RestAssured.port != 80))
            logger.info("POST " + RestAssured.baseURI + ":" + RestAssured.port
                    + url + requestBody + " Expected Response " + responseCode
                    + " Observed Response " + res.getStatusCode());
        else
            logger.info("POST " + RestAssured.baseURI + url + requestBody
                    + " Expected Response " + responseCode
                    + " Observed Response " + res.getStatusCode());

        assertEquals(res.getStatusCode(), responseCode);
        return res;
    }

    public static Response POST_with_header(int responseCode,
                                            String requestBody, String url, String headerName,
                                            String headerValue) {

        logger.info(RestAssured.baseURI);
        // RestAssured.urlEncodingEnabled=false;
        Response res = given().header(headerName, headerValue)
                .contentType("application/json").and().body(requestBody).when()
                .post(url);

        if ((RestAssured.port != -1) && (RestAssured.port != 80))
            logger.info("POST " + RestAssured.baseURI + ":" + RestAssured.port
                    + url + requestBody + " Expected Response " + responseCode
                    + " Observed Response " + res.getStatusCode());
        else
            logger.info("POST " + RestAssured.baseURI + url + requestBody
                    + " Expected Response " + responseCode
                    + " Observed Response " + res.getStatusCode());

        assertEquals(res.getStatusCode(), responseCode);
        return res;
    }


    public static Response PUT(int responseCode, String requestBody, String url) {

        Response res = given().contentType("application/json").and()
                .body(requestBody).when().put(url);
        if ((RestAssured.port != -1) && (RestAssured.port != 80))
            logger.info("PUT " + RestAssured.baseURI + ":" + RestAssured.port
                    + url + " " + requestBody + " Expected Response "
                    + responseCode + " Observed Response "
                    + res.getStatusCode());
        else
            logger.info("PUT " + RestAssured.baseURI + url + " " + requestBody
                    + " Expected Response " + responseCode
                    + " Observed Response " + res.getStatusCode());
        assertEquals(res.getStatusCode(), responseCode);
        return res;
    }

    public static Response PUT_WHITOUT_RESPONSE(String requestBody, String url) {

        Response res = given().contentType("application/json").and()
                .body(requestBody).when().put(url);
        if ((RestAssured.port != -1) && (RestAssured.port != 80))
            logger.info("PUT " + RestAssured.baseURI + ":" + RestAssured.port
                    + url + " " + requestBody + " Observed Response "
                    + res.getStatusCode());
        else
            logger.info("PUT " + RestAssured.baseURI + url + " " + requestBody
                    + " Observed Response " + res.getStatusCode());

        return res;
    }

    public static Response PUT(String url) {

        logger.info(RestAssured.baseURI);
        // RestAssured.urlEncodingEnabled=false;
        Response res = given().contentType("application/json").when().put(url);
        if ((RestAssured.port != -1) && (RestAssured.port != 80))
            logger.info("PUT " + RestAssured.baseURI + ":" + RestAssured.port
                    + url + " " + " Observed Response " + res.getStatusCode());
        else
            logger.info("PUT " + RestAssured.baseURI + url + " "
                    + " Observed Response " + res.getStatusCode());
        return res;
    }

    public static Response POST(int responseCode, String url) {

        logger.info(RestAssured.baseURI);
        // RestAssured.urlEncodingEnabled=false;
        Response res = given().contentType("application/json").when().post(url);
        if ((RestAssured.port != -1) && (RestAssured.port != 80))
            logger.info("POST " + RestAssured.baseURI + ":" + RestAssured.port
                    + url + " " + " Observed Response " + res.getStatusCode());
        else
            logger.info("POST " + RestAssured.baseURI + url + " "
                    + " Observed Response " + res.getStatusCode());
        assertEquals(res.getStatusCode(), responseCode);
        return res;
    }

    public static Response MultipartPOST(int responseCode, String filePath,
                                         String url) {

        logger.info(RestAssured.baseURI);
        Response res = given().contentType("multipart/form-data")
                .multiPart(new File(filePath)).post(url);
        if ((RestAssured.port != -1) && (RestAssured.port != 80))
            logger.info("POST " + RestAssured.baseURI + ":" + RestAssured.port
                    + url + " Expected Response " + responseCode
                    + " Observed Response " + res.getStatusCode());
        else
            logger.info("POST " + RestAssured.baseURI + url
                    + " Expected Response " + responseCode
                    + " Observed Response " + res.getStatusCode());
        assertEquals(res.getStatusCode(), responseCode);
        return res;
    }

    public static Response MultipartPOSTForFile(int responseCode,
                                                String filename, byte[] bytes, String url) {

        logger.info(RestAssured.baseURI);
        Response res = given().contentType("multipart/form-data")
                .multiPart("file", filename, bytes, "text/plain").post(url);

        if ((RestAssured.port != -1) && (RestAssured.port != 80))
            logger.info("POST " + RestAssured.baseURI + ":" + RestAssured.port
                    + url + " Expected Response " + responseCode
                    + " Observed Response " + res.getStatusCode());
        else
            logger.info("POST " + RestAssured.baseURI + url
                    + " Expected Response " + responseCode
                    + " Observed Response " + res.getStatusCode());

        assertEquals(res.getStatusCode(), responseCode);
        return res;
    }

    public static Response POST_flexible(int responseCode, String requestBody,
                                         String url, String error_msg) {

        Response res = given().contentType("application/json").and()
                .body(requestBody).when().post(url);
        if ((RestAssured.port != -1) && (RestAssured.port != 80))
            logger.info("POST " + RestAssured.baseURI + ":" + RestAssured.port
                    + url + " " + requestBody + " Expected Response "
                    + responseCode + " Observed Response "
                    + res.getStatusCode());
        else
            logger.info("POST " + RestAssured.baseURI + url + " " + requestBody
                    + " Expected Response " + responseCode
                    + " Observed Response " + res.getStatusCode());

        if (res.getStatusCode() != 200) {
            logger.info("Error!");
        } else {
            assertEquals(res.getStatusCode(), responseCode, error_msg);
        }
        return res;
    }

    public static Response POST_with_message(int responseCode,
                                             String requestBody, String url, String error_msg) {

        Response res = given().contentType("application/json").and()
                .body(requestBody).when().post(url);
        if ((RestAssured.port != -1) && (RestAssured.port != 80))
            logger.info("POST " + RestAssured.baseURI + ":" + RestAssured.port
                    + url + " " + requestBody + " Expected Response "
                    + responseCode + " Observed Response "
                    + res.getStatusCode());
        else
            logger.info("POST " + RestAssured.baseURI + url + " " + requestBody
                    + " Expected Response " + responseCode
                    + " Observed Response " + res.getStatusCode());
        assertEquals(res.getStatusCode(), responseCode, error_msg);
        return res;
    }

    public static Response POST_with_formParameter(int responseCode,
                                                   String username, String password, String url) {
        RestAssured.baseURI = url;
        logger.info(RestAssured.baseURI);
        Response res = given().urlEncodingEnabled(true)
                .formParam("username", username)
                .formParam("password", password).when().post();
        if ((RestAssured.port != -1) && (RestAssured.port != 80))
            logger.info("POST " + RestAssured.baseURI + ":" + RestAssured.port
                    + url + " user name" + username + " password" + password
                    + " Expected Response " + responseCode
                    + " Observed Response " + res.getStatusCode());
        else
            logger.info("POST " + RestAssured.baseURI + url + " user name"
                    + username + " password" + password + " Expected Response "
                    + responseCode + " Observed Response "
                    + res.getStatusCode());
        return res;
    }

    public static Response MultipartPUT(int responseCode, String filePath,
                                        String url) {

        logger.info(RestAssured.baseURI);
        Response res = given().contentType("multipart/form-data")
                .multiPart(new File(filePath)).put(url);
        if ((RestAssured.port != -1) && (RestAssured.port != 80))
            logger.info("PUT " + RestAssured.baseURI + ":" + RestAssured.port
                    + url + " Expected Response " + responseCode
                    + " Observed Response " + res.getStatusCode());
        else
            logger.info("PUT " + RestAssured.baseURI + url
                    + " Expected Response " + responseCode
                    + " Observed Response " + res.getStatusCode());
        assertEquals(res.getStatusCode(), responseCode);
        return res;
    }


    public static Response PUT_with_message(int responseCode,
                                            String requestBody, String url, String error_msg) {

        Response res = given().contentType("application/json").and()
                .body(requestBody).when().put(url);
        if ((RestAssured.port != -1) && (RestAssured.port != 80))
            logger.info("PUT " + RestAssured.baseURI + ":" + RestAssured.port
                    + url + " " + requestBody + " Expected Response "
                    + responseCode + " Observed Response "
                    + res.getStatusCode());
        else
            logger.info("PUT " + RestAssured.baseURI + url + " " + requestBody
                    + " Expected Response " + responseCode
                    + " Observed Response " + res.getStatusCode());
        assertEquals(res.getStatusCode(), responseCode, error_msg);
        return res;
    }

    public static Response PUT(String requestBody, String url) {

        Response res = given().contentType("application/json").and()
                .body(requestBody).when().put(url);
        if ((RestAssured.port != -1) && (RestAssured.port != 80))
            logger.info("PUT " + RestAssured.baseURI + ":" + RestAssured.port
                    + url + " " + requestBody + "  Observed Response "
                    + res.getStatusCode());

        else
            logger.info("PUT " + RestAssured.baseURI + url + " " + requestBody
                    + "  Observed Response " + res.getStatusCode());
        return res;
    }

    public static Response PUT_Without_Body(int responseCode, String url) {

        Response res = given().contentType("application/json").when().put(url);

        if ((RestAssured.port != -1) && (RestAssured.port != 80))
            logger.info("PUT " + RestAssured.baseURI + ":" + RestAssured.port
                    + url + " Expected Response " + responseCode
                    + " Observed Response " + res.getStatusCode());
        else
            logger.info("PUT " + RestAssured.baseURI + url
                    + " Expected Response " + responseCode
                    + " Observed Response " + res.getStatusCode());
        assertEquals(res.getStatusCode(), responseCode);
        return res;
    }

    public static Response PUT_Without_Body_NoResponse(String url) {

        Response res = given().contentType("application/json").when().put(url);

        if ((RestAssured.port != -1) && (RestAssured.port != 80))
            logger.info("PUT " + RestAssured.baseURI + ":" + RestAssured.port
                    + url + " Observed Response " + res.getStatusCode());
        else
            logger.info("PUT " + RestAssured.baseURI + url

                    + " Observed Response " + res.getStatusCode());

        return res;
    }


    public static Response DELETE(int responseCode, String url) {

        Response res = delete(url);
        if ((RestAssured.port != -1) && (RestAssured.port != 80))
            logger.info("DELETE " + RestAssured.baseURI + ":"
                    + RestAssured.port + url + " Expected Response "
                    + responseCode + " Observed Response "
                    + res.getStatusCode());
        else
            logger.info("DELETE " + RestAssured.baseURI + url
                    + " Expected Response " + responseCode
                    + " Observed Response " + res.getStatusCode());
        assertEquals(res.getStatusCode(), responseCode);
        return res;
    }

    public static Response DELETE_WithBody(int responseCode,
                                           String requestBody, String url) {

        Response res = given().contentType("application/json").and()
                .body(requestBody).when().delete(url);

        if ((RestAssured.port != -1) && (RestAssured.port != 80))
            logger.info("DELETE " + RestAssured.baseURI + ":"
                    + RestAssured.port + url + " Expected Response "
                    + responseCode + " Observed Response "
                    + res.getStatusCode());
        else
            logger.info("DELETE " + RestAssured.baseURI + url
                    + " Expected Response " + responseCode
                    + " Observed Response " + res.getStatusCode());
        assertEquals(res.getStatusCode(), responseCode);
        return res;
    }

    public static Response DELETE(String url) {

        Response res = delete(url);
        if ((RestAssured.port != -1) && (RestAssured.port != 80))
            logger.info("DELETE " + RestAssured.baseURI + ":"
                    + RestAssured.port + url + "  Observed Response "
                    + res.getStatusCode());

        else
            logger.info("DELETE " + RestAssured.baseURI + url
                    + "  Observed Response " + res.getStatusCode());
        return res;
    }

    public static Response DELETE_Billing(int responseCode, String requestBody,
                                          String url, String billingSession) {

        logger.info(RestAssured.baseURI);

        Response res = given().contentType("application/json")
                .header("Billing-Session", billingSession).and()
                .body(requestBody).when().delete(url);
        if ((RestAssured.port != -1) && (RestAssured.port != 80))
            logger.info("DELETE " + RestAssured.baseURI + ":"
                    + RestAssured.port + url + " " + requestBody
                    + " Expected Response " + responseCode
                    + " Observed Response " + res.getStatusCode());

        else
            logger.info("DELETE " + RestAssured.baseURI + url + " "
                    + requestBody + " Expected Response " + responseCode
                    + " Observed Response " + res.getStatusCode());

        assertEquals(res.getStatusCode(), responseCode);

        return res;
    }

    public static Response PUT_header(int responseCode, String requestBody,
                                      String url, Map<String, String> header) {

        logger.info(RestAssured.baseURI);

        Response res = given().contentType("application/json").headers(header)
                .and().body(requestBody).when().put(url);
        if ((RestAssured.port != -1) && (RestAssured.port != 80))
            logger.info("PUT " + RestAssured.baseURI + ":" + RestAssured.port
                    + url + " " + requestBody + " Expected Response "
                    + responseCode + " Observed Response "
                    + res.getStatusCode());

        else
            logger.info("PUT " + RestAssured.baseURI + url + " " + requestBody
                    + " Expected Response " + responseCode
                    + " Observed Response " + res.getStatusCode());

        assertEquals(res.getStatusCode(), responseCode);

        return res;

    }

    public static Response GET_header(int responseCode, String url,
                                      Map<String, String> header) {

        Response res = given().headers(header).get(url);

        if ((RestAssured.port != -1) && (RestAssured.port != 80))
            logger.info("GET " + RestAssured.baseURI + ":" + RestAssured.port
                    + url + " Expected Response " + responseCode
                    + " Observed Response " + res.getStatusCode());
        else
            logger.info("GET " + RestAssured.baseURI + url
                    + " Expected Response " + responseCode
                    + " Observed Response " + res.getStatusCode());
        assertEquals(res.getStatusCode(), responseCode);
        return res;
    }

    public static Response POST_header(int responseCode, String requestBody,
                                       String url, Map<String, String> header) {

        Response res = given().contentType("application/json").headers(header)
                .and().body(requestBody).when().post(url);

        if ((RestAssured.port != -1) && (RestAssured.port != 80))
            logger.info("POST " + RestAssured.baseURI + ":" + RestAssured.port
                    + url + " " + requestBody + " Expected Response "
                    + responseCode + " Observed Response "
                    + res.getStatusCode());
        else
            logger.info("POST " + RestAssured.baseURI + url + " " + requestBody
                    + " Expected Response " + responseCode
                    + " Observed Response " + res.getStatusCode());
        assertEquals(res.getStatusCode(), responseCode);
        return res;

    }

    public static Response cookienEnabledPOST(int responseCode,
                                              String requestBody, String url, Map<String, String> cookies) {

        Response res = given().contentType("application/json").cookies(cookies)
                .and().body(requestBody).when().post(url);

        if ((RestAssured.port != -1) && (RestAssured.port != 80))
            logger.info("POST " + RestAssured.baseURI + ":" + RestAssured.port
                    + url + " " + requestBody + " Expected Response "
                    + responseCode + " Observed Response "
                    + res.getStatusCode());
        else
            logger.info("POST " + RestAssured.baseURI + url + " " + requestBody
                    + " Expected Response " + responseCode
                    + " Observed Response " + res.getStatusCode());

        logger.info("cookie : " + cookies);
        assertEquals(res.getStatusCode(), responseCode);
        return res;

    }

    public static Response POSTWithUserNameAndPassword(int responseCode,
                                                       String url, String requestBody, String userName, String password) {
        logger.info("*** " + url + "**** " + userName + " " + password);
        Response res = given().contentType("application/json").auth()
                .basic(userName, password).and().body(requestBody).when()
                .post(url);
        if ((RestAssured.port != -1) && (RestAssured.port != 80))
            logger.info("POST " + RestAssured.baseURI + ":" + RestAssured.port
                    + url + " " + requestBody + " Expected Response "
                    + responseCode + " Observed Response "
                    + res.getStatusCode());
        else
            logger.info("POST " + RestAssured.baseURI + url + " " + requestBody
                    + " Expected Response " + responseCode
                    + " Observed Response " + res.getStatusCode());
        assertEquals(res.getStatusCode(), responseCode);
        return res;

    }

    public static Response GETBasicAuth(int responseCode, String url,
                                        String username, String password) {
        Response res = given().auth().basic(username, password).when().get(url);
        if ((RestAssured.port != -1) && (RestAssured.port != 80))
            logger.info("GET " + RestAssured.baseURI + ":" + RestAssured.port
                    + url + " Expected Response " + responseCode
                    + " Observed Response " + res.getStatusCode());
        else
            logger.info("GET " + RestAssured.baseURI + url
                    + " Expected Response " + responseCode
                    + " Observed Response " + res.getStatusCode());
        assertEquals(res.getStatusCode(), responseCode);
        return res;
    }

    public static Response POSTWithHeader(int responseCode, String requestBody,
                                          String url, Header serviceHeader) {

        logger.info("Header passed is " + serviceHeader.toString());
        Response res = given().contentType("application/json")
                .header(serviceHeader).body(requestBody).when().post(url);
        if ((RestAssured.port != -1) && (RestAssured.port != 80))
            logger.info("POST " + RestAssured.baseURI + ":" + RestAssured.port
                    + url + " " + requestBody + " Expected Response "
                    + responseCode + " Observed Response "
                    + res.getStatusCode());
        else
            logger.info("POST " + RestAssured.baseURI + url + " " + requestBody
                    + " Expected Response " + responseCode
                    + " Observed Response " + res.getStatusCode());

        assertEquals(res.getStatusCode(), responseCode);

        return res;

    }

    public static Response GETWithHeader(int responseCode, String url,
                                         Header serviceHeader) {

        logger.info("Header passed is " + serviceHeader.toString());
        Response res = given().header(serviceHeader).get(url);

        if ((RestAssured.port != -1) && (RestAssured.port != 80))
            logger.info("GET " + RestAssured.baseURI + ":" + RestAssured.port
                    + url + " Expected Response " + responseCode
                    + " Observed Response " + res.getStatusCode());
        else
            logger.info("GET " + RestAssured.baseURI + url
                    + " Expected Response " + responseCode
                    + " Observed Response " + res.getStatusCode());
        assertEquals(res.getStatusCode(), responseCode);
        return res;
    }
}
