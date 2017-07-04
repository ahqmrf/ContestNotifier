package apps.ahqmrf.contestnotifier.contest.service;

import apps.ahqmrf.contestnotifier.admin.response.ContestResponse;
import apps.ahqmrf.contestnotifier.base.DataCatchListener;
import apps.ahqmrf.contestnotifier.base.ServiceCallback;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by bsse0 on 7/4/2017.
 */

public class ContestConnector {

    private DataCatchListener listener;
    private ContestService service;

    public ContestConnector(DataCatchListener listener) {
        this.listener = listener;
        service = new ContestClient().createService();
    }

    public void getContests() {
        if(listener != null) {
            listener.showLoader();
            Call<ContestResponse> call = service.getContests();
            call.enqueue(new ServiceCallback<ContestResponse>(listener) {
                @Override
                public void onResponse(Response<ContestResponse> response) {
                    ContestResponse data = response.body();
                    if(data != null) {
                        ((ContestServiceListener)listener).onContestsLoaded(data.getContests());
                    }
                }
            });
        }
    }
}
