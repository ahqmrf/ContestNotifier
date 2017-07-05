package apps.ahqmrf.contestnotifier.admin.service;

import java.util.List;

import apps.ahqmrf.contestnotifier.admin.model.Division;
import apps.ahqmrf.contestnotifier.base.DataCatchListener;

/**
 * Created by bsse0 on 7/3/2017.
 */

public interface DivisionRetrieveListener extends DataCatchListener{

    void onDivisionsLoaded(List<Division> divisions);
}
