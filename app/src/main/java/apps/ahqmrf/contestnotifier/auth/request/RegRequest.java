package apps.ahqmrf.contestnotifier.auth.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by bsse0 on 6/28/2017.
 */

public class RegRequest extends LoginCredential {
    @SerializedName("name") private String name;
    @SerializedName("email") private String email;
    @SerializedName("workplace") private String workplace;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWorkplace() {
        return workplace;
    }

    public void setWorkplace(String workplace) {
        this.workplace = workplace;
    }
}
