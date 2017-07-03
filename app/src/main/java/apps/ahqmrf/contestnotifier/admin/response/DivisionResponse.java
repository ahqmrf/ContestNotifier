package apps.ahqmrf.contestnotifier.admin.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import apps.ahqmrf.contestnotifier.admin.model.Division;
import apps.ahqmrf.contestnotifier.base.BaseResponse;

/**
 * Created by bsse0 on 7/3/2017.
 */

public class DivisionResponse extends BaseResponse {
    @SerializedName("data") private List<Division> divisions;

    public List<Division> getDivisions() {
        return divisions;
    }
}
