package apps.ahqmrf.contestnotifier.auth.response;

import com.google.gson.annotations.SerializedName;

import apps.ahqmrf.contestnotifier.base.BaseResponse;

/**
 * Created by bsse0 on 6/30/2017.
 */

public class RegResponse extends BaseResponse {
    @SerializedName("confirmationCode") private String confirmationCode;

    public String getConfirmationCode() {
        return confirmationCode;
    }
}
