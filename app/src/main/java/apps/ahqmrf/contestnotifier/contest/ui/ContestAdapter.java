package apps.ahqmrf.contestnotifier.contest.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import apps.ahqmrf.contestnotifier.R;
import apps.ahqmrf.contestnotifier.admin.model.Contest;
import apps.ahqmrf.contestnotifier.utils.ImageConfig;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by bsse0 on 7/4/2017.
 */

public class ContestAdapter extends RecyclerView.Adapter {

    private List<Contest> items;

    public ContestAdapter(List<Contest> items) {
        addItems(items);
    }

    public void addItems(List<Contest> items) {
        this.items = new ArrayList<>();
        this.items.addAll(items);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contest, parent, false);
        return new ContestHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ContestHolder) holder).bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ContestHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.contest_name)  TextView  contestName;
        @BindView(R.id.platform_name) TextView  platformName;
        @BindView(R.id.division)      TextView  division;
        @BindView(R.id.duration)      TextView  duration;
        @BindView(R.id.time)          TextView  time;
        @BindView(R.id.logo)          ImageView logo;

        ContestHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(Contest contest) {
            platformName.setText(contest.getPlatform());
            contestName.setText(contest.getName());
            division.setText(contest.getDivision());
            duration.setText(contest.getParsedDuration());
            time.setText(contest.getTime() + ", " + contest.getDate());
            ImageLoader.getInstance().displayImage(contest.getLogo(), logo, ImageConfig.getInstance().getImageConfig());
        }
    }
}
