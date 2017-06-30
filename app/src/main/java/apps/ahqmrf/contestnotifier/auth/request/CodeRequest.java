package apps.ahqmrf.contestnotifier.auth.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by bsse0 on 6/30/2017.
 */

public class CodeRequest {
    @SerializedName("email") private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
