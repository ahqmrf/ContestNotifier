package apps.ahqmrf.contestnotifier.contest.service;

import apps.ahqmrf.contestnotifier.network.Client;

/**
 * Created by bsse0 on 7/4/2017.
 */

public class ContestClient extends Client<ContestService> {
    @Override
    public ContestService createService() {
        return getRetrofit().create(ContestService.class);
    }
}
