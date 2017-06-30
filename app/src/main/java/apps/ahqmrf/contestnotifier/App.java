package apps.ahqmrf.contestnotifier;

import android.app.Application;

import com.binjar.prefsdroid.Preference;

import apps.ahqmrf.contestnotifier.utils.PrefKeys;

/**
 * Created by bsse0 on 6/29/2017.
 */

public class App extends Application {

    public static App context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        Preference.load().using(this).with(PrefKeys.NAME).prepare();
    }

    public static App getContext() {
        return context;
    }
}
