package apps.ahqmrf.contestnotifier.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;

import java.lang.reflect.Field;

import apps.ahqmrf.contestnotifier.R;
import apps.ahqmrf.contestnotifier.auth.ui.LoginActivity;
import apps.ahqmrf.contestnotifier.utils.Utility;

/**
 * Created by bsse0 on 7/1/2017.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getOverflowMenu();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        onViewCreated();
    }

    public abstract void onViewCreated();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.overflow, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.settings) {
            // TODO
        } else if(id == R.id.logout) {
            logout();
        }
        return super.onOptionsItemSelected(item);
    }

    protected void logout() {
        Utility.endSession();
        finishAffinity();
        openActivity(LoginActivity.class);
    }

    protected void openActivity(Class<?> activity) {
        openActivity(new Intent(this, activity));
    }

    private void openActivity(Intent intent) {
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    protected void getOverflowMenu() {
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
