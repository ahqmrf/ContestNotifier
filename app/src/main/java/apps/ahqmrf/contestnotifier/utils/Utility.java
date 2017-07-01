package apps.ahqmrf.contestnotifier.utils;

import android.os.Build;
import android.support.annotation.NonNull;
import android.text.Html;
import android.text.Spanned;
import android.widget.Toast;

import com.binjar.prefsdroid.Preference;

import apps.ahqmrf.contestnotifier.App;
import apps.ahqmrf.contestnotifier.auth.response.LoginResponse;

/**
 * Created by bsse0 on 6/29/2017.
 */

public final class Utility {
    public static void showToast(String message) {
        Toast.makeText(App.getContext(), message, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(int resId) {
        showToast(getString(resId));
    }

    public static String getString(int resId) {
        return App.getContext().getResources().getString(resId);
    }

    public static void startSession(LoginResponse data) {
        Preference.putString(PrefKeys.TOKEN, data.getToken());
        Preference.putLong(PrefKeys.USER_ID, data.getId());
    }

    @NonNull
    public static Spanned fromHtml(@NonNull String source) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(source);
        }
    }

    public static void endSession() {
        Preference.remove(PrefKeys.TOKEN);
        Preference.remove(PrefKeys.USER_ID);
    }
}
