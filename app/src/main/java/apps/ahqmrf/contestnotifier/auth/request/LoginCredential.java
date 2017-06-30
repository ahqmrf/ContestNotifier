package apps.ahqmrf.contestnotifier.auth.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by bsse0 on 6/28/2017.
 */

public class LoginCredential {
    @SerializedName("username") private String username;
    @SerializedName("password") private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
