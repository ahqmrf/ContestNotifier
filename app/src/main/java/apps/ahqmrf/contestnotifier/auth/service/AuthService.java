package apps.ahqmrf.contestnotifier.auth.service;

import apps.ahqmrf.contestnotifier.auth.request.CodeRequest;
import apps.ahqmrf.contestnotifier.auth.request.LoginCredential;
import apps.ahqmrf.contestnotifier.auth.request.RegRequest;
import apps.ahqmrf.contestnotifier.auth.response.CodeResponse;
import apps.ahqmrf.contestnotifier.auth.response.LoginResponse;
import apps.ahqmrf.contestnotifier.auth.response.RegResponse;
import apps.ahqmrf.contestnotifier.base.BaseResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by bsse0 on 6/28/2017.
 */

public interface AuthService {

    @POST("register.php") Call<RegResponse> register(@Body RegRequest request);

    @POST("login.php") Call<LoginResponse> login(@Body LoginCredential credential);

    @POST("confirmationCode.php")  Call<CodeResponse> sendConfirmationCode(@Body CodeRequest request);

    @POST("activateAccount.php") Call<BaseResponse> activateAccount(@Body CodeRequest request);
}
