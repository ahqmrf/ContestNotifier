package apps.ahqmrf.contestnotifier.admin.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by bsse0 on 7/3/2017.
 */

public class Contest {
    @SerializedName("id") private long id;
    @SerializedName("name") private String name;
    @SerializedName("division") private String division;
    @SerializedName("time") private String time;
    @SerializedName("date") private String date;
    @SerializedName("platform") private String platform;
    @SerializedName("logo") private String logo;
    @SerializedName("platformUrl") private String platformUrl;
    @SerializedName("contestUrl") private String contestUrl;
    @SerializedName("duration") private String duration;

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

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

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getPlatformUrl() {
        return platformUrl;
    }

    public void setPlatformUrl(String platformUrl) {
        this.platformUrl = platformUrl;
    }

    public String getContestUrl() {
        return contestUrl;
    }

    public void setContestUrl(String contestUrl) {
        this.contestUrl = contestUrl;
    }

    public String getParsedDuration() {
        String tokens[] = duration.split(":");
        int day = Integer.valueOf(tokens[0]);
        int hrs = Integer.valueOf(tokens[1]);
        int min = Integer.valueOf(tokens[2]);

        String str = "";

        if(day > 0) {
            str = str + day + " day" + (day > 1? "s" : "");
        }

        if(hrs > 0) {
            str = str + " " + hrs + " hr" + (hrs > 1? "s" : "");
        }

        if(min > 0) {
            str = str + " " +  min + " min" + (min > 1? "s" : "");
        }
        return str.trim();
    }
}
