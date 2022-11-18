package requests;

import io.restassured.response.Response;

import static utils.RestUtil.*;

public class UserRequests {

    public Response createWithArray(int responseCode, String data) {
        String url = "user/createWithArray";
        Response res = POST(responseCode, data, url);
        return res;
    }

    public Response updateUserByName(int responseCode, String userName, String data) {
        String url = "user/" + userName;
        Response res = PUT(responseCode, data, url);
        return res;
    }

    public Response getUserByName(int responseCode, String userName) {
        String url = "user/" + userName;
        Response res = GET(responseCode, url);
        return res;
    }
}
