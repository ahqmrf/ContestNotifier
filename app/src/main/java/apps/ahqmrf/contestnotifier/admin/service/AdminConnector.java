package apps.ahqmrf.contestnotifier.admin.service;

import java.io.File;

import apps.ahqmrf.contestnotifier.admin.model.Contest;
import apps.ahqmrf.contestnotifier.admin.model.Platform;
import apps.ahqmrf.contestnotifier.admin.response.DivisionResponse;
import apps.ahqmrf.contestnotifier.admin.response.UploadResponse;
import apps.ahqmrf.contestnotifier.admin.response.PlatformResponse;
import apps.ahqmrf.contestnotifier.base.BaseResponse;
import apps.ahqmrf.contestnotifier.base.DataCatchListener;
import apps.ahqmrf.contestnotifier.base.ServiceCallback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by bsse0 on 7/2/2017.
 */

public class AdminConnector {
    private DataCatchListener listener;
    private AdminService service;

    public AdminConnector(DataCatchListener listener) {
        this.listener = listener;
        service = new AdminClient().createService();
    }

    public void uploadFile(File file) {
        if(listener != null) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
            MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
            RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());

            Call<UploadResponse> call = service.uploadFile(fileToUpload, filename);

            listener.showLoader();

            call.enqueue(new ServiceCallback<UploadResponse>(listener) {
                @Override
                public void onResponse(Response<UploadResponse> response) {
                    UploadResponse data = response.body();
                    if(data != null) {
                        ((UploadListener)listener).onUploadSuccess(data);
                    }
                }
            });
        }
    }

    public void addPlatform(Platform platform) {
        if(listener != null) {
            listener.showLoader();
            Call<BaseResponse> call = service.addPlatform(platform);
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

    public void getPlatforms() {
        if(listener != null) {
            listener.showLoader();
            Call<PlatformResponse> call = service.getPlatforms();
            call.enqueue(new ServiceCallback<PlatformResponse>(listener) {
                @Override
                public void onResponse(Response<PlatformResponse> response) {
                    PlatformResponse data = response.body();
                    if(data != null) {
                        ((PlatformRetrieveListener)listener).onWebsiteListLoaded(data.getPlatforms());
                    }
                }
            });
        }
    }

    public void getDivisions() {
        if(listener != null) {
            listener.showLoader();
            Call<DivisionResponse> call = service.getDivisions();
            call.enqueue(new ServiceCallback<DivisionResponse>(listener) {
                @Override
                public void onResponse(Response<DivisionResponse> response) {
                    DivisionResponse data = response.body();
                    if(data != null) {
                        ((DivisionRetrieveListener)listener).onDivisionsLoaded(data.getDivisions());
                    }
                }
            });
        }
    }

    public void addContest(Contest contest) {
        if(listener != null) {
            listener.showLoader();
            Call<BaseResponse> call = service.addContest(contest);
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
