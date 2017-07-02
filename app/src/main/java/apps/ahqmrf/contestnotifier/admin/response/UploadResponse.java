package apps.ahqmrf.contestnotifier.admin.response;

import com.google.gson.annotations.SerializedName;

import apps.ahqmrf.contestnotifier.base.BaseResponse;

/**
 * Created by bsse0 on 7/2/2017.
 */

public class UploadResponse extends BaseResponse {
    @SerializedName("url") private String url;

    public String getUrl() {
        return url;
    }
}
