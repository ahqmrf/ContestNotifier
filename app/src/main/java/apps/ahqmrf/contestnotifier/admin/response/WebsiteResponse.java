package apps.ahqmrf.contestnotifier.admin.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import apps.ahqmrf.contestnotifier.admin.model.Website;
import apps.ahqmrf.contestnotifier.base.BaseResponse;

/**
 * Created by bsse0 on 7/2/2017.
 */

public class WebsiteResponse extends BaseResponse {

    @SerializedName("data") private List<Website> websites;
}
