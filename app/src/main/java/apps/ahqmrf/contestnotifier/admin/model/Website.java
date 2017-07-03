package apps.ahqmrf.contestnotifier.admin.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by bsse0 on 7/2/2017.
 */

public class Website {
    @SerializedName("id") private long id;
    @SerializedName("name") private String name;
    @SerializedName("url") private String url;
    @SerializedName("logo") private String logo;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    @Override
    public String toString() {
        return name;
    }
}
