package apps.ahqmrf.contestnotifier.admin.ui;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatSpinner;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.rey.material.widget.ProgressView;

import java.io.File;
import java.util.Calendar;
import java.util.List;

import apps.ahqmrf.contestnotifier.R;
import apps.ahqmrf.contestnotifier.admin.model.Contest;
import apps.ahqmrf.contestnotifier.admin.model.Division;
import apps.ahqmrf.contestnotifier.admin.model.Website;
import apps.ahqmrf.contestnotifier.admin.service.AdminConnector;
import apps.ahqmrf.contestnotifier.admin.service.GetDivisionListener;
import apps.ahqmrf.contestnotifier.admin.service.GetWebsiteListener;
import apps.ahqmrf.contestnotifier.admin.service.UploadListener;
import apps.ahqmrf.contestnotifier.base.BaseActivity;
import apps.ahqmrf.contestnotifier.utils.Const;
import apps.ahqmrf.contestnotifier.utils.Utility;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AdminPanelActivity extends BaseActivity implements UploadListener, View.OnFocusChangeListener, GetWebsiteListener, GetDivisionListener, AdapterView.OnItemSelectedListener {

    @BindView(R.id.progress_layout)    ProgressView     progressView;
    @BindView(R.id.input_name)         EditText         nameView;
    @BindView(R.id.input_website_url)  EditText         urlView;
    @BindView(R.id.input_logo)         EditText         logoView;
    @BindView(R.id.layout_add_website) View             addWebsiteLayout;
    @BindView(R.id.layout_add_contest) View             addContestLayout;
    @BindView(R.id.dropdownWebsite)    ImageView        dropdownView;
    @BindView(R.id.dropdownContest)    ImageView        dropdownContestView;
    @BindView(R.id.input_contest_url)  EditText         contestUrlView;
    @BindView(R.id.input_contest_name) EditText         contestNameView;
    @BindView(R.id.input_time)         EditText         timeView;
    @BindView(R.id.platformSpinner)    AppCompatSpinner platformView;
    @BindView(R.id.divisionSpinner)    AppCompatSpinner divisionView;
    @BindView(R.id.daySpinner)         AppCompatSpinner daySpinnerView;
    @BindView(R.id.hourSpinner)        AppCompatSpinner hourSpinnerView;
    @BindView(R.id.minuteSpinner)      AppCompatSpinner minuteSpinnerView;
    @BindView(R.id.text_duration)      TextView         durationView;

    private File   file;
    private String logoPath;
    private int    mYear, mMonth, mDay, mHour, mMinute;
    private String date, time;
    private Calendar c = Calendar.getInstance();
    private AdminConnector connector;
    private List<Division> divisions;
    private List<Website>  websites;
    private int selectedWebsite  = 0;
    private int selectedDivision = 0;
    private int dayIndex         = 0, hourIndex = 0, minuteIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        ButterKnife.bind(this);

        connector = new AdminConnector(this);
    }

    @Override
    public void onViewCreated() {
        setTitle(Utility.getString(R.string.menu_admin_panel));
        setBackArrow();
        nameView.setOnFocusChangeListener(this);
        urlView.setOnFocusChangeListener(this);
        contestNameView.setOnFocusChangeListener(this);
        contestUrlView.setOnFocusChangeListener(this);

        setTimeDate();

        connector.getWebsites();
        connector.getDivisions();

        setSpinnerListeners();
    }

    private void setSpinnerListeners() {
        daySpinnerView.setOnItemSelectedListener(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, getResources().getStringArray(R.array.days));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinnerView.setAdapter(adapter);
        adapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, getResources().getStringArray(R.array.hours));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hourSpinnerView.setAdapter(adapter);
        hourSpinnerView.setOnItemSelectedListener(this);
        adapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, getResources().getStringArray(R.array.minutes));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        minuteSpinnerView.setAdapter(adapter);
        minuteSpinnerView.setOnItemSelectedListener(this);
        divisionView.setOnItemSelectedListener(this);
        platformView.setOnItemSelectedListener(this);
    }

    private void setTimeDate() {
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        String str = "AM";
        if (mHour > 12) {
            mHour -= 12;
            str = "PM";
        }

        time = (mHour < 10 ? "0" : "") + mHour + ":" + (mMinute < 10 ? "0" : "") + mMinute + " " + str;

        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH) + 1;
        mDay = c.get(Calendar.DAY_OF_MONTH);

        date = mYear + "/" + (mMonth < 10? "0" : "") + mMonth + "/" + (mDay < 10? "0" : "") + mDay;

        timeView.setText(time + ", " + date);
    }

    @OnClick(R.id.btn_done)
    void onDoneClick() {
        String contestName = contestNameView.getText().toString().trim();
        String contestUrl = contestUrlView.getText().toString().trim();
        if (TextUtils.isEmpty(contestName)) {
            Utility.showToast(R.string.error_empty_contest_name);
            return;
        }
        if (selectedWebsite == 0) {
            Utility.showToast(R.string.error_website_selection);
            return;
        }
        if (selectedDivision == 0) {
            Utility.showToast(R.string.error_division_selection);
            return;
        }

        Website website = websites.get(selectedWebsite);
        Division division = divisions.get(selectedDivision);

        Contest contest = new Contest();
        contest.setName(contestName);
        contest.setContestUrl(contestUrl);
        contest.setTime(time);
        contest.setDate(date);
        contest.setPlatform(website.getName());
        contest.setPlatformUrl(website.getUrl());
        contest.setLogo(website.getLogo());
        contest.setDivision(division.getType());

        contest.setDuration(getSelectedTime());
        connector.addContest(contest);
    }


    String getSelectedTime() {
        if (dayIndex == 0) dayIndex = 1;
        if (hourIndex == 0) hourIndex = 1;
        if (minuteIndex == 0) minuteIndex = 1;
        String day = getResources().getStringArray(R.array.days)[dayIndex];
        String hrs = getResources().getStringArray(R.array.hours)[hourIndex];
        String mins = getResources().getStringArray(R.array.minutes)[minuteIndex];
        day = day.length() < 2 ? "0" + day : day;
        hrs = hrs.length() < 2 ? "0" + hrs : hrs;
        mins = mins.length() < 2 ? "0" + mins : mins;
        return day + ":" + hrs + ":" + mins;
    }

    @OnClick(R.id.btn_choose)
    void checkReadExternalStoragePermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {

                } else {

                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            Const.READ_EXTERNAL_STORAGE);
                }
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        Const.READ_EXTERNAL_STORAGE);
            }
        } else {
            openImageGallery();
        }
    }

    @OnClick(R.id.btn_upload)
    void upload() {
        if (file != null && file.exists()) {
            connector.uploadFile(file);
        } else {
            Utility.showToast(R.string.msg_error);
        }
    }

    @OnClick(R.id.btn_save)
    void submit() {
        String name = nameView.getText().toString().trim();
        String url = urlView.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            Utility.showToast(R.string.error_empty_name);
            return;
        }

        if (TextUtils.isEmpty(url)) {
            Utility.showToast(R.string.error_empty_url);
            return;
        }

        Website website = new Website();
        website.setName(name);
        website.setUrl(url);
        website.setLogo(logoPath == null ? "" : logoPath);

        connector.addWebsite(website);
        connector.getWebsites();
    }

    @OnClick({R.id.add_website, R.id.dropdownWebsite})
    void onAddWebsiteClick() {
        if (addWebsiteLayout.getVisibility() == View.VISIBLE) {
            addWebsiteLayout.setVisibility(View.GONE);
            dropdownView.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
        } else {
            addWebsiteLayout.setVisibility(View.VISIBLE);
            dropdownView.setImageResource(R.drawable.ic_arrow_drop_up_black_24dp);
        }
    }

    @OnClick({R.id.add_contest, R.id.dropdownContest})
    void onAddContestClick() {
        if (addContestLayout.getVisibility() == View.VISIBLE) {
            addContestLayout.setVisibility(View.GONE);
            dropdownContestView.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
        } else {
            addContestLayout.setVisibility(View.VISIBLE);
            dropdownContestView.setImageResource(R.drawable.ic_arrow_drop_up_black_24dp);
        }
    }

    @OnClick(R.id.btn_time)
    void onTimeClick() {
        c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        String str = "AM";
                        if (hourOfDay > 12) {
                            hourOfDay -= 12;
                            str = "PM";
                        }

                        time = (hourOfDay < 10 ? "0" : "") + hourOfDay + ":" + (minute < 10 ? "0" : "") + minute + " " + str;
                        openDatePicker();

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    void openDatePicker() {
        c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        monthOfYear++;
                        date = year + "/" + (monthOfYear < 10? "0" : "") + monthOfYear + "/" + (dayOfMonth < 10? "0" : "") +dayOfMonth;
                        timeView.setText(time + ", " + date);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void openImageGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        if (getPackageManager().resolveActivity(intent, 0) != null) {
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), Const.REQUEST_BROWSE_GALLERY);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case Const.READ_EXTERNAL_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openImageGallery();

                } else {
                    Utility.showToast(R.string.error_permission);
                }
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Const.REQUEST_BROWSE_GALLERY && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            if (null != uri) {
                file = new File(Utility.getFileUrl(uri));
                Utility.showToast(R.string.msg_file_chosen);
                logoView.setText(file.getName());
            }
        }
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
    public void onFailure(String message) {
        Utility.showToast(message);
    }

    @Override
    public void onSuccess(String message) {
        Utility.showToast(message);
        clearFields();
    }

    private void clearFields() {
        nameView.setText("");
        contestNameView.setText("");
        logoView.setText("");
        contestUrlView.setText("");
        urlView.setText("");
        setTimeDate();
    }

    @Override
    public void onLogoUrlRetrieved(String logoUrl) {
        logoPath = logoUrl;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) v.setBackgroundResource(R.drawable.edittext_focused);
        else v.setBackgroundResource(R.drawable.edittext);
    }

    @Override
    public void onWebsiteListLoaded(List<Website> websites) {
        if (websites != null) {
            Website website = new Website();
            website.setName("Select a platform");
            websites.add(0, website);
            this.websites = websites;

            ArrayAdapter<Website> adapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            adapter.addAll(websites);

            platformView.setAdapter(adapter);
        }
    }

    @Override
    public void onDivisionsLoaded(List<Division> divisions) {
        if (divisions != null) {
            Division division = new Division();
            division.setType("Select a division");
            divisions.add(0, division);
            this.divisions = divisions;

            ArrayAdapter<Division> adapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            adapter.addAll(divisions);

            divisionView.setAdapter(adapter);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int resId = parent.getId();
        if (resId == R.id.platformSpinner) {
            selectedWebsite = position;
        } else if (resId == R.id.divisionSpinner) {
            selectedDivision = position;
        } else if (resId == R.id.daySpinner) {
            dayIndex = position;
            durationView.setText(getSelectedTime());
        } else if (resId == R.id.hourSpinner) {
            hourIndex = position;
            durationView.setText(getSelectedTime());
        } else if (resId == R.id.minuteSpinner) {
            minuteIndex = position;
            durationView.setText(getSelectedTime());
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
