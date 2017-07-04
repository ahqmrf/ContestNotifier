package apps.ahqmrf.contestnotifier.contest.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.rey.material.widget.ProgressView;

import java.util.ArrayList;
import java.util.List;

import apps.ahqmrf.contestnotifier.R;
import apps.ahqmrf.contestnotifier.admin.model.Contest;
import apps.ahqmrf.contestnotifier.admin.ui.AdminPanelActivity;
import apps.ahqmrf.contestnotifier.base.BaseActivity;
import apps.ahqmrf.contestnotifier.contest.service.ContestConnector;
import apps.ahqmrf.contestnotifier.contest.service.ContestServiceListener;
import apps.ahqmrf.contestnotifier.utils.Utility;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity implements ContestServiceListener {

    @BindView(R.id.progress_layout) ProgressView progressView;
    @BindView(R.id.contests)        RecyclerView contestsView;

    private ContestConnector connector;
    private ContestAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);
    }

    @Override
    public void onViewCreated() {
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        contestsView.setLayoutManager(manager);
        adapter = new ContestAdapter(new ArrayList<Contest>());
        contestsView.setAdapter(adapter);

        connector = new ContestConnector(this);
        refresh();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        if (Utility.isAdmin()) {
            MenuItem item = menu.findItem(R.id.admin_panel);
            item.setVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.admin_panel) {
            openActivity(AdminPanelActivity.class);
        }
        return true;
    }

    @Override
    public void onFailure(String message) {
        Utility.showToast(message);
    }

    @Override
    public void onSuccess(String message) {
        Utility.showToast(message);
    }

    @Override
    public void hideLoader() {
        progressView.setVisibility(View.GONE);
    }

    @Override
    public void showLoader() {
        progressView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onContestsLoaded(List<Contest> contests) {
        adapter.addItems(contests);
        adapter.notifyDataSetChanged();
    }

    @OnClick(R.id.btn_refresh) void refresh() {
        connector.getContests();
    }
}
