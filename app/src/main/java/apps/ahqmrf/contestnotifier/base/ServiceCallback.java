package apps.ahqmrf.contestnotifier.base;

import android.support.annotation.NonNull;

import java.io.IOException;

import apps.ahqmrf.contestnotifier.R;
import apps.ahqmrf.contestnotifier.utils.Const;
import apps.ahqmrf.contestnotifier.utils.Utility;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by bsse0 on 6/29/2017.
 */

public abstract class ServiceCallback<Model> implements Callback<Model> {
    private DataCatchListener listener;

    public ServiceCallback(DataCatchListener listener) {
        this.listener = listener;
    }

    @Override
    public void onResponse(@NonNull Call<Model> call, @NonNull Response<Model> response) {
        if(listener != null) {

            listener.hideLoader();

            Model model = response.body();
            if(model == null) {
                listener.onFailure(Utility.getString(R.string.msg_error));
                return;
            }

            BaseResponse baseResponse = (BaseResponse) model;
            if(baseResponse.getStatusCode() != Const.OK) {
                listener.onFailure(baseResponse.getError());
                return;
            }

            onResponse(response);
        }
    }

    @Override
    public void onFailure(@NonNull Call<Model> call, @NonNull Throwable t) {
        if(listener != null) {
            listener.hideLoader();
            if (t instanceof IOException) {
                listener.onFailure(Utility.getString(R.string.msg_network_problem));
            }
        }
    }

    public abstract void onResponse(Response<Model> response);
}
