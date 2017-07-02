package apps.ahqmrf.contestnotifier.auth.response;

import com.google.gson.annotations.SerializedName;

import apps.ahqmrf.contestnotifier.base.BaseResponse;

/**
 * Created by bsse0 on 6/28/2017.
 */

public class LoginResponse extends BaseResponse {
    @SerializedName("id") private long id;
    @SerializedName("username") private String username;
    @SerializedName("name") private String name;
    @SerializedName("email") private String email;
    @SerializedName("workplace") private String workplace;
    @SerializedName("token") private String token;
    @SerializedName("isActivated") private boolean activated;
    @SerializedName("isAdmin") private boolean admin;
    @SerializedName("confirmationCode") private String code;

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getWorkplace() {
        return workplace;
    }

    public String getToken() {
        return token;
    }

    public boolean isActivated() {
        return activated;
    }

    public boolean isAdmin() {
        return admin;
    }

    public String getCode() {
        return code;
    }
}
