package apps.ahqmrf.contestnotifier.auth.service;

import apps.ahqmrf.contestnotifier.auth.request.CodeRequest;
import apps.ahqmrf.contestnotifier.auth.request.LoginCredential;
import apps.ahqmrf.contestnotifier.auth.request.RegRequest;
import apps.ahqmrf.contestnotifier.auth.response.CodeResponse;
import apps.ahqmrf.contestnotifier.auth.response.LoginResponse;
import apps.ahqmrf.contestnotifier.auth.response.RegResponse;
import apps.ahqmrf.contestnotifier.base.BaseResponse;
import apps.ahqmrf.contestnotifier.base.DataCatchListener;
import apps.ahqmrf.contestnotifier.base.ServiceCallback;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by bsse0 on 6/29/2017.
 */

public class AuthConnector {

    private DataCatchListener listener;
    private AuthService       service;

    public AuthConnector(DataCatchListener listener) {
        this.listener = listener;
        this.service = new AuthClient().createService();
    }

    public void login(String username, String password) {
        if (listener != null) {
            LoginCredential credential = new LoginCredential();
            credential.setUsername(username);
            credential.setPassword(password);

            Call<LoginResponse> call = service.login(credential);
            listener.showLoader();

            call.enqueue(new ServiceCallback<LoginResponse>(listener) {
                @Override
                public void onResponse(Response<LoginResponse> response) {
                    LoginResponse data = response.body();
                    if (data != null) {
                        ((LoginListener) listener).onRetrieved(data);
                        listener.onSuccess(data.getMessage());
                    }
                }
            });
        }
    }

    public void register(RegRequest request) {
        if (listener != null) {
            Call<RegResponse> call = service.register(request);
            listener.showLoader();
            call.enqueue(new ServiceCallback<RegResponse>(listener) {
                @Override
                public void onResponse(Response<RegResponse> response) {
                    RegResponse data = response.body();
                    if (data != null) {
                        ((CodeListener) listener).onCodeRetrieved(data.getConfirmationCode());
                        listener.onSuccess(data.getMessage());
                    }
                }
            });
        }
    }

    public void sendConfirmationCode(String email) {
        if (listener != null) {
            CodeRequest request = new CodeRequest();
            request.setEmail(email);
            Call<CodeResponse> call = service.sendConfirmationCode(request);
            listener.showLoader();
            call.enqueue(new ServiceCallback<CodeResponse>(listener) {
                @Override
                public void onResponse(Response<CodeResponse> response) {
                    CodeResponse data = response.body();
                    if (data != null) {
                        ((CodeListener) listener).onCodeRetrieved(data.getCode());
                        listener.onSuccess(data.getMessage());
                    }
                }
            });
        }
    }

    public void activateAccount(String email) {
        if(listener != null) {
            CodeRequest request = new CodeRequest();
            request.setEmail(email);
            Call<BaseResponse> call = service.activateAccount(request);
            listener.showLoader();
            call.enqueue(new ServiceCallback<BaseResponse>(listener) {
                @Override
                public void onResponse(Response<BaseResponse> response) {
                    BaseResponse data = response.body();
                    if(data != null) {
                        listener.onSuccess(data.getMessage());
                    }
                }
            });
        }
    }
}
