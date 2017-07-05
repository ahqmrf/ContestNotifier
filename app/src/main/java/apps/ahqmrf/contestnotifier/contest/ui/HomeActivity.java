package apps.ahqmrf.contestnotifier.contest.ui;

import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.rey.material.widget.ProgressView;

import java.util.ArrayList;
import java.util.List;

import apps.ahqmrf.contestnotifier.R;
import apps.ahqmrf.contestnotifier.admin.model.Contest;
import apps.ahqmrf.contestnotifier.admin.model.Division;
import apps.ahqmrf.contestnotifier.admin.model.Website;
import apps.ahqmrf.contestnotifier.admin.service.AdminConnector;
import apps.ahqmrf.contestnotifier.admin.service.GetDivisionListener;
import apps.ahqmrf.contestnotifier.admin.service.GetWebsiteListener;
import apps.ahqmrf.contestnotifier.admin.ui.AdminPanelActivity;
import apps.ahqmrf.contestnotifier.base.BaseActivity;
import apps.ahqmrf.contestnotifier.contest.service.ContestConnector;
import apps.ahqmrf.contestnotifier.contest.service.ContestServiceListener;
import apps.ahqmrf.contestnotifier.utils.Utility;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity implements ContestServiceListener, GetWebsiteListener {

    @BindView(R.id.progress_layout) ProgressView     progressView;
    @BindView(R.id.contests)        RecyclerView     contestsView;
    @BindView(R.id.platformSpinner) AppCompatSpinner platformsView;

    private ContestConnector connector;
    private ContestAdapter   adapter;
    private List<Website>    websites;
    private List<Contest>    contests;

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
        new AdminConnector(this).getWebsites();
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
        this.contests = new ArrayList<>();
        this.contests.addAll(contests);
        if (contests.isEmpty()) {
            Utility.showToast(R.string.emtpy_contests);
            return;
        }
        adapter.addItems(contests);
        adapter.notifyDataSetChanged();
        if(websites != null && websites.size() > 0) platformsView.setSelection(0);
    }

    @OnClick(R.id.btn_refresh)
    void refresh() {
        connector.getContests();
    }

    @Override
    public void onWebsiteListLoaded(final List<Website> websites) {
        if (websites != null) {
            Website website = new Website();
            website.setName("-All platforms-");
            websites.add(0, website);
            website = new Website();
            website.setName("Select a platform");
            websites.add(0, website);
            this.websites = websites;

            ArrayAdapter<Website> adapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            adapter.addAll(websites);

            platformsView.setAdapter(adapter);
            platformsView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    filterContestsByPlatform(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }

    private void filterContestsByPlatform(int position) {
        if (position < 2) {
            adapter.addItems(contests);
        } else {
            String selectedPlatform = websites.get(position).getName();
            List<Contest> filteredContests = new ArrayList<>();
            for (Contest contest : contests) {
                if (contest.getPlatform().equals(selectedPlatform)) {
                    filteredContests.add(contest);
                }
            }
            if (filteredContests.isEmpty()) {
                Utility.showToast(R.string.emtpy_contests);
                adapter.addItems(contests);
            } else {
                adapter.addItems(filteredContests);
            }
        }
        adapter.notifyDataSetChanged();
    }
}
