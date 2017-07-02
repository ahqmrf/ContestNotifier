package apps.ahqmrf.contestnotifier.admin.service;

import apps.ahqmrf.contestnotifier.network.Client;

/**
 * Created by bsse0 on 7/2/2017.
 */

public class AdminClient extends Client<AdminService> {
    @Override
    public AdminService createService() {
        return getRetrofit().create(AdminService.class);
    }
}
