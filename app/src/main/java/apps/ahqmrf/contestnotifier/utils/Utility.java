package apps.ahqmrf.contestnotifier.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
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
        Preference.putBoolean(PrefKeys.IS_ADMIN, data.isAdmin());
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

    public static boolean isLoggedIn() {
        return Preference.getLong(PrefKeys.USER_ID) != 0 && Preference.getString(PrefKeys.TOKEN) != null;
    }

    public static boolean isAdmin() {
        return Preference.getBoolean(PrefKeys.IS_ADMIN, false);
    }

    public static String getFileUrl(Uri contentUri) {
        Context context = App.getContext();
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}
