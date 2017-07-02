package apps.ahqmrf.contestnotifier.contest.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import apps.ahqmrf.contestnotifier.R;
import apps.ahqmrf.contestnotifier.admin.ui.AdminPanelActivity;
import apps.ahqmrf.contestnotifier.base.BaseActivity;
import apps.ahqmrf.contestnotifier.utils.Utility;

public class HomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    public void onViewCreated() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        if(Utility.isAdmin()) {
            MenuItem item = menu.findItem(R.id.admin_panel);
            item.setVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId() == R.id.admin_panel) {
            openActivity(AdminPanelActivity.class);
        }
        return true;
    }
}
