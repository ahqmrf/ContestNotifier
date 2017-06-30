package apps.ahqmrf.contestnotifier.auth.service;

import apps.ahqmrf.contestnotifier.network.Client;

/**
 * Created by bsse0 on 6/28/2017.
 */

public class AuthClient extends Client<AuthService> {
    @Override
    public AuthService createService() {
        return getRetrofit().create(AuthService.class);
    }
}
