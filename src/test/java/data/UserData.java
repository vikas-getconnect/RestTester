package data;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserData {
    private int id;
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String phone;
    private int userstatus;
}
