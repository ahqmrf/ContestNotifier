package apps.ahqmrf.contestnotifier.contest.service;

import apps.ahqmrf.contestnotifier.admin.response.ContestResponse;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by bsse0 on 7/4/2017.
 */

public interface ContestService {
    @GET("getContests.php") Call<ContestResponse> getContests();
}
