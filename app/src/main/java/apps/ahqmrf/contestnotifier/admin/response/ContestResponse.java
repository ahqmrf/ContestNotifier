package apps.ahqmrf.contestnotifier.admin.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import apps.ahqmrf.contestnotifier.admin.model.Contest;
import apps.ahqmrf.contestnotifier.base.BaseResponse;

/**
 * Created by bsse0 on 7/3/2017.
 */

public class ContestResponse extends BaseResponse {
    @SerializedName("data") private List<Contest> contests;

    public List<Contest> getContests() {
        return contests;
    }
}
