package apps.ahqmrf.contestnotifier.contest.service;

import java.util.List;

import apps.ahqmrf.contestnotifier.admin.model.Contest;
import apps.ahqmrf.contestnotifier.base.DataCatchListener;

/**
 * Created by bsse0 on 7/4/2017.
 */

public interface ContestServiceListener extends DataCatchListener {

    void onContestsLoaded(List<Contest> contests);
}
