package tests;

import config.APIConfig;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;

public class BaseTest {

    public static final String DATA_PATH = System.getProperty("user.dir") + "/src/test/resources/testdata/";
    protected static Logger logger = LoggerFactory.getLogger(BaseTest.class);

    @BeforeClass(alwaysRun = true)
    @Step("Base setup for api")
    public void apiSetUp() throws Exception {

        RestAssured.baseURI = APIConfig.getBaseUrl();
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.port = Integer.parseInt(APIConfig.getPort());

    }

    @AfterClass(alwaysRun = true)
    @Step("clean up all the connections")
    public void apiCleanUp() {

    }


    @BeforeMethod(alwaysRun = true)
    public void testStarted(Method method) {
        String testName = method.getName();
        logger.info("------------ Test Started : {} ------------------", testName);

    }

    @AfterMethod(alwaysRun = true)
    public void testDone(Method method) {
        String testName = method.getName();
        logger.info("------------ Test End : {} ------------------", testName);
        logger.info("                                             ");

    }

}
