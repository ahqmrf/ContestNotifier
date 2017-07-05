package apps.ahqmrf.contestnotifier.admin.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import apps.ahqmrf.contestnotifier.admin.model.Platform;
import apps.ahqmrf.contestnotifier.base.BaseResponse;

/**
 * Created by bsse0 on 7/2/2017.
 */

public class PlatformResponse extends BaseResponse {

    @SerializedName("data") private List<Platform> platforms;

    public List<Platform> getPlatforms() {
        return platforms;
    }
}
