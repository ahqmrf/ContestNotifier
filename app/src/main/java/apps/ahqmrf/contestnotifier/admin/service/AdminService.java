package apps.ahqmrf.contestnotifier.admin.service;

import apps.ahqmrf.contestnotifier.admin.model.Contest;
import apps.ahqmrf.contestnotifier.admin.model.Website;
import apps.ahqmrf.contestnotifier.admin.response.DivisionResponse;
import apps.ahqmrf.contestnotifier.admin.response.UploadResponse;
import apps.ahqmrf.contestnotifier.admin.response.WebsiteResponse;
import apps.ahqmrf.contestnotifier.base.BaseResponse;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by bsse0 on 7/2/2017.
 */

public interface AdminService {

    @Multipart
    @POST("uploadLogo.php")
    Call<UploadResponse> uploadFile(@Part MultipartBody.Part file, @Part("file") RequestBody name);

    @POST("addWebsite.php")
    Call<BaseResponse> addWebsite(@Body Website website);

    @GET("getWebsites.php")
    Call<WebsiteResponse> getWebsites();

    @GET("getDivisions.php")
    Call<DivisionResponse> getDivisions();

    @POST("addContest.php")
    Call<BaseResponse> addContest(@Body Contest contest);
}
