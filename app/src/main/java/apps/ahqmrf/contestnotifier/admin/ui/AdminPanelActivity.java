package apps.ahqmrf.contestnotifier.admin.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.rey.material.widget.ProgressView;

import java.io.File;

import apps.ahqmrf.contestnotifier.R;
import apps.ahqmrf.contestnotifier.admin.model.Website;
import apps.ahqmrf.contestnotifier.admin.service.AdminConnector;
import apps.ahqmrf.contestnotifier.admin.service.UploadListener;
import apps.ahqmrf.contestnotifier.base.BaseActivity;
import apps.ahqmrf.contestnotifier.utils.Const;
import apps.ahqmrf.contestnotifier.utils.Utility;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AdminPanelActivity extends BaseActivity implements UploadListener {

    @BindView(R.id.progress_layout)    ProgressView progressView;
    @BindView(R.id.input_name)         EditText     nameView;
    @BindView(R.id.input_website_url)  EditText     urlView;
    @BindView(R.id.input_logo)         EditText     logoView;
    @BindView(R.id.layout_add_website) View         addWebsiteLayout;
    @BindView(R.id.dropdown)           ImageView    dropdownView;

    private File   file;
    private String logoPath;
    private boolean addWebsiteVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        ButterKnife.bind(this);
    }

    @Override
    public void onViewCreated() {
        setTitle(Utility.getString(R.string.menu_admin_panel));
        setBackArrow();
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
            new AdminConnector(this).uploadFile(file);
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
        website.setLogo(logoPath);

        new AdminConnector(this).addWebsite(website);
    }

    @OnClick(R.id.add_website)
    void onAddWebsiteClick() {
        addWebsiteVisible = !addWebsiteVisible;
        if (addWebsiteVisible) {
            addWebsiteLayout.setVisibility(View.VISIBLE);
            dropdownView.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);

        } else {
            addWebsiteLayout.setVisibility(View.GONE);
            dropdownView.setImageResource(R.drawable.ic_arrow_drop_up_black_24dp);
        }
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
    }

    @Override
    public void onLogoUrlRetrieved(String logoUrl) {
        logoPath = logoUrl;
    }
}
