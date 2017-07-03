package apps.ahqmrf.contestnotifier.admin.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by bsse0 on 7/3/2017.
 */

public class Division {
    @SerializedName("id") private long id;
    @SerializedName("type") private String type;

    public long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
