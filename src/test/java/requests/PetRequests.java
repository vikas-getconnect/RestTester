package requests;

import io.restassured.response.Response;

import static utils.RestUtil.*;

public class PetRequests {

    public Response addNewPet(int responseCode, String data) {
        String url = "pet";
        Response res = POST(responseCode, data, url);
        return res;
    }

    public Response updateExistingPet(int responseCode, String data) {
        String url = "pet";
        Response res = PUT(responseCode, data, url);
        return res;
    }

    public Response findPetsByStatus(int responseCode, String status) {
        String url = "pet/findByStatus?status=" + status;
        Response res = GET(responseCode, url);
        return res;
    }
}
