package apps.ahqmrf.contestnotifier.admin.service;

import java.util.List;

import apps.ahqmrf.contestnotifier.admin.model.Platform;
import apps.ahqmrf.contestnotifier.base.DataCatchListener;

/**
 * Created by bsse0 on 7/3/2017.
 */

public interface PlatformRetrieveListener extends DataCatchListener {

    void onWebsiteListLoaded(List<Platform> platforms);
}
