package apps.ahqmrf.contestnotifier;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.binjar.prefsdroid.Preference;

import apps.ahqmrf.contestnotifier.auth.ui.LoginActivity;
import apps.ahqmrf.contestnotifier.contest.ui.HomeActivity;
import apps.ahqmrf.contestnotifier.utils.Const;
import apps.ahqmrf.contestnotifier.utils.Utility;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(Utility.isLoggedIn()) startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                else startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        }, Const.SPLASH_SCREEN_DURATION * 1000);
    }
}
