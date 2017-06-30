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
    @SerializedName("isActivated") private int activated;
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

    public int isActivated() {
        return activated;
    }

    public String getCode() {
        return code;
    }
}
