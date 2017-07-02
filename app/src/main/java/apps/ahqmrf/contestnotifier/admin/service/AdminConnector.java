package apps.ahqmrf.contestnotifier.admin.service;

import java.io.File;

import apps.ahqmrf.contestnotifier.admin.model.Website;
import apps.ahqmrf.contestnotifier.admin.response.UploadResponse;
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
                        listener.onSuccess(data.getMessage());
                        ((UploadListener)listener).onLogoUrlRetrieved(data.getUrl());
                    }
                }
            });
        }
    }

    public void addWebsite(Website website) {
        if(listener != null) {
            listener.showLoader();
            Call<BaseResponse> call = service.addWebsite(website);
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
