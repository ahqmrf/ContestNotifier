package apps.ahqmrf.contestnotifier.admin.service;

import apps.ahqmrf.contestnotifier.admin.response.UploadResponse;
import apps.ahqmrf.contestnotifier.base.DataCatchListener;

/**
 * Created by bsse0 on 7/2/2017.
 */

public interface UploadListener extends DataCatchListener {

    void onUploadSuccess(UploadResponse response);
}
