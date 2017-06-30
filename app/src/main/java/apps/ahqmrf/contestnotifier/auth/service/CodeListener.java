package apps.ahqmrf.contestnotifier.auth.service;

import apps.ahqmrf.contestnotifier.base.DataCatchListener;

/**
 * Created by bsse0 on 6/30/2017.
 */

public interface CodeListener extends DataCatchListener {

    void onCodeRetrieved(String code);

}
