package apps.ahqmrf.contestnotifier.auth.service;

import apps.ahqmrf.contestnotifier.auth.response.LoginResponse;
import apps.ahqmrf.contestnotifier.base.DataCatchListener;

/**
 * Created by bsse0 on 6/29/2017.
 */

public interface LoginListener extends DataCatchListener{

    void onRetrieved(LoginResponse response);

}
