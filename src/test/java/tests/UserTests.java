package tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import constant.ResponseCode;
import data.UserData;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import requests.UserRequests;

import java.util.ArrayList;
import java.util.List;

public class UserTests extends BaseTest {
    protected static Logger logger = LoggerFactory.getLogger(UserTests.class);


    @Test(groups = {"users"})
    @Description("Test to create multiple users")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Test to create multiple users")
    public void createMultipleUsersTest() throws JsonProcessingException {

        UserData userData = UserData.builder().id(1)
                .username("sadsd")
                .phone("1234")
                .email("a@b.c")
                .lastname("sdas")
                .firstname("sasda")
                .userstatus(0)
                .password("dasdasdsa")
                .build();
        List<UserData> users = new ArrayList<>();
        users.add(userData);

        ObjectMapper mapper = new ObjectMapper();
        String js = mapper.writeValueAsString(users);

        UserRequests request = new UserRequests();
        Response res = request.createWithArray(ResponseCode.OK, js);

        JsonPath jp = res.body().jsonPath();
        // Assertions for validations
        Assert.assertEquals(jp.get("message"), "ok");
        Assert.assertEquals(jp.get("type"), "unknown");
        Assert.assertEquals(jp.get("code").toString(), "200");
    }

    @Test(groups = {"users"})
    @Description("Test Update user by name")
    @Severity(SeverityLevel.MINOR)
    @Story("Test Update user by name")
    public void updateUserByUserNameTest() throws JsonProcessingException {
        UserData userData = UserData.builder().id(1)
                .username("sadsd")
                .phone("1234")
                .email("a@b.c")
                .lastname("sdas")
                .firstname("sasda")
                .userstatus(0)
                .password("dasdasdsa")
                .build();

        ObjectMapper mapper = new ObjectMapper();
        String js = mapper.writeValueAsString(userData);
        System.out.println(js);

        UserRequests request = new UserRequests();
        Response res = request.updateUserByName(ResponseCode.OK, "vikas", js);
        res.body().print();
        JsonPath jp = res.body().jsonPath();
        // Assertions for validations
        Assert.assertEquals(jp.get("message"), "1");
        Assert.assertEquals(jp.get("type"), "unknown");
        Assert.assertEquals(jp.get("code").toString(), "200");

    }

    @Test(groups = {"users"})
    @Description("Test get user info by name")
    @Severity(SeverityLevel.MINOR)
    @Story("User details info by name")
    public void getUserByNameTest() throws JsonProcessingException {

        UserData userData = UserData.builder().id(1)
                .username("mindtickle")
                .phone("1234")
                .email("a@b.c")
                .lastname("tickle")
                .firstname("mind")
                .userstatus(0)
                .password("mind@123")
                .build();
        List<UserData> users = new ArrayList<>();
        users.add(userData);

        ObjectMapper mapper = new ObjectMapper();
        String js = mapper.writeValueAsString(users);
        System.out.println(js);

        UserRequests request = new UserRequests();
        Response res = request.createWithArray(ResponseCode.OK, js);
        res.body().print();

        Response resGet = request.getUserByName(ResponseCode.OK, "mindtickle");
        JsonPath jp = resGet.body().jsonPath();
        // Assertions for validations
        Assert.assertEquals(jp.get("username"), "mindtickle");
    }

    @Test(groups = {"users"})
    @Description("Test get user info by invalid name")
    @Severity(SeverityLevel.MINOR)
    @Story("User details info by name")
    public void getUserByInvalidNameTest() throws JsonProcessingException {

        UserRequests request = new UserRequests();
        Response resGet = request.getUserByName(ResponseCode.NOT_FOUND, "ddsadwdw");
        JsonPath jp = resGet.body().jsonPath();
        // Assertions for validations
        Assert.assertEquals(jp.get("message"), "User not found");
    }


}
