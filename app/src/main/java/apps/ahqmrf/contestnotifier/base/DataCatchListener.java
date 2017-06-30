package apps.ahqmrf.contestnotifier.base;

/**
 * Created by bsse0 on 6/29/2017.
 */

public interface DataCatchListener {

    void hideLoader();

    void showLoader();

    void onFailure(String message);

    void onSuccess(String message);
}
