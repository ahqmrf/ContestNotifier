package apps.ahqmrf.contestnotifier.base;

import com.google.gson.annotations.SerializedName;

/**
 * Created by bsse0 on 6/28/2017.
 */

public class BaseResponse {
    @SerializedName("statusCode") private long statusCode;
    @SerializedName("error") private String error;
    @SerializedName("message") private String message;

    public long getStatusCode() {
        return statusCode;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}
