package tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import constant.ResponseCode;
import data.PetData;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import requests.PetRequests;
import utils.RestUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class PetTests extends BaseTest {
    protected static Logger logger = LoggerFactory.getLogger(PetTests.class);

    @DataProvider(name = "status")
    public Iterator<Object[]> status() {
        final List<Object[]> createData = new ArrayList<>();
        createData.add(new Object[]{"available"});
        createData.add(new Object[]{"sold"});
        createData.add(new Object[]{"pending"});
        return createData.iterator();
    }

    @Test(groups = {"pets"})
    public void createNewPetTest() throws JsonProcessingException {

        PetData petData = PetData.builder().id(1)
                .category(PetData.Category.builder().id(11).name("ddas").build())
                .name("tj")
                .photoUrls(Arrays.asList("https://sample.com"))
                .status("available")
                .tags(Arrays.asList(PetData.Tags.builder().id(1).name("tag_0").build()))
                .build();

        ObjectMapper mapper = new ObjectMapper();
        String js = mapper.writeValueAsString(petData);
        System.out.println(js);

        PetRequests request = new PetRequests();
        Response res = request.addNewPet(ResponseCode.OK, js);
        res.body().print();

        JsonPath jp = res.body().jsonPath();
        Assert.assertEquals(jp.get("category.name"), "ddas");
    }

    @Test(groups = {"pets"})
    public void updateExistingPetTest() throws JsonProcessingException {
        PetData petData = PetData.builder().id(1)
                .category(PetData.Category.builder().id(11).name("update").build())
                .name("tj")
                .photoUrls(Arrays.asList("https://sample.com"))
                .status("available")
                .tags(Arrays.asList(PetData.Tags.builder().id(1).name("tag_0").build()))
                .build();

        ObjectMapper mapper = new ObjectMapper();
        String js = mapper.writeValueAsString(petData);
        System.out.println(js);
        PetRequests request = new PetRequests();
        Response res = request.updateExistingPet(ResponseCode.OK, js);

        JsonPath jp = res.body().jsonPath();
        Assert.assertEquals(jp.get("category.name"), "update");
    }

    @Test(dataProvider = "status", groups = {"pets"})
    public void getPetByStatusTest(String status) throws JsonProcessingException, ParseException {
        PetRequests request = new PetRequests();
        Response res = request.findPetsByStatus(ResponseCode.OK, status);
        JsonPath jp = res.body().jsonPath();
        JSONArray ja = RestUtil.getJsonArray(res.asString());

        //Assert that all the data is of same status
        for (int i = 0; i < ja.size(); i++) {
            JSONObject obj = (JSONObject) ja.get(i);
            Assert.assertEquals(obj.get("status"), status);
        }

    }


}
