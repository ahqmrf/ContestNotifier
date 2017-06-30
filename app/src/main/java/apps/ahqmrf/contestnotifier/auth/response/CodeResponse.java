package apps.ahqmrf.contestnotifier.auth.response;

import com.google.gson.annotations.SerializedName;

import apps.ahqmrf.contestnotifier.base.BaseResponse;

/**
 * Created by bsse0 on 6/30/2017.
 */

public class CodeResponse extends BaseResponse {
    @SerializedName("confirmationCode") private String code;

    public String getCode() {
        return code;
    }
}
