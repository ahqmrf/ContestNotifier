package apps.ahqmrf.contestnotifier.auth.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.rey.material.widget.ProgressView;

import apps.ahqmrf.contestnotifier.R;
import apps.ahqmrf.contestnotifier.auth.response.LoginResponse;
import apps.ahqmrf.contestnotifier.auth.service.AuthConnector;
import apps.ahqmrf.contestnotifier.auth.service.LoginListener;
import apps.ahqmrf.contestnotifier.base.BaseActivity;
import apps.ahqmrf.contestnotifier.contest.ui.HomeActivity;
import apps.ahqmrf.contestnotifier.utils.Utility;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginListener, View.OnFocusChangeListener {

    @BindView(R.id.input_username)  EditText     usernameView;
    @BindView(R.id.input_password)  EditText     passwordView;
    @BindView(R.id.progress_layout) ProgressView progressView;
    @BindView(R.id.button_register) TextView     registerView;
    @BindView(R.id.app_toolbar)     Toolbar      toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @Override
    public void onViewCreated() {
        setSupportActionBar(toolbar);
        setTitle(Utility.getString(R.string.button_log_in));

        usernameView.setOnFocusChangeListener(this);
        passwordView.setOnFocusChangeListener(this);

        String text = "Don't have an account yet? <font color='blue'>Click here</font> to register.";
        registerView.setText(Utility.fromHtml(text), TextView.BufferType.SPANNABLE);
    }

    @OnClick(R.id.button_login)
    void onLoginClick() {
        String username = usernameView.getText().toString().trim();
        String password = passwordView.getText().toString();

        if (TextUtils.isEmpty(username)) {
            Utility.showToast(R.string.error_empty_username);
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Utility.showToast(R.string.error_password_required);
            return;
        }

        new AuthConnector(this).login(username, password);
    }

    @OnClick(R.id.button_register) void onRegisterClick() {
        openActivity(RegistrationActivity.class);
    }

    @Override
    public void onRetrieved(LoginResponse data) {
        if(data.isActivated()) {
            Utility.startSession(data);
            openHomepage();
        } else {
            openConfirmCodePage(data.getEmail(), data.getCode());
        }
    }

    private void openHomepage() {
        openActivity(HomeActivity.class);
        finish();
    }

    private void openConfirmCodePage(String email, String confirmationCode) {
        CodeConfirmFragment fragment = CodeConfirmFragment.getInstance(email, confirmationCode);
        fragment.show(getFragmentManager(), "CodeConfirm");
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
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) v.setBackgroundResource(R.drawable.edittext_focused);
        else v.setBackgroundResource(R.drawable.edittext);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
