package apps.ahqmrf.contestnotifier.auth.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.rey.material.widget.ProgressView;

import apps.ahqmrf.contestnotifier.R;
import apps.ahqmrf.contestnotifier.auth.request.RegRequest;
import apps.ahqmrf.contestnotifier.auth.service.AuthConnector;
import apps.ahqmrf.contestnotifier.auth.service.CodeListener;
import apps.ahqmrf.contestnotifier.utils.Const;
import apps.ahqmrf.contestnotifier.utils.Utility;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegistrationActivity extends AppCompatActivity implements CodeListener, View.OnFocusChangeListener {

    @BindView(R.id.input_email)            EditText     emailView;
    @BindView(R.id.input_username)         EditText     usernameView;
    @BindView(R.id.input_name)             EditText     nameView;
    @BindView(R.id.input_workplace)        EditText     workplaceView;
    @BindView(R.id.input_password)         EditText     passwordView;
    @BindView(R.id.input_confirm_password) EditText     confPassView;
    @BindView(R.id.progress_layout)        ProgressView progressView;

    private String email;
    private String username;
    private String name;
    private String workplace;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        ButterKnife.bind(this);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        setViews();
    }

    private void setViews() {
        setTitle(Utility.getString(R.string.button_register));

        emailView.setOnFocusChangeListener(this);
        usernameView.setOnFocusChangeListener(this);
        nameView.setOnFocusChangeListener(this);
        workplaceView.setOnFocusChangeListener(this);
        passwordView.setOnFocusChangeListener(this);
        confPassView.setOnFocusChangeListener(this);
    }

    @OnClick(R.id.button_register) void onRegisterClick() {
        if(isValidForm()) {
            RegRequest request = new RegRequest();
            request.setEmail(email);
            request.setUsername(username);
            request.setName(name);
            request.setWorkplace(workplace);
            request.setPassword(password);

            new AuthConnector(this).register(request);
        }
    }

    boolean isValidForm() {
        email = emailView.getText().toString().trim();
        username = usernameView.getText().toString().trim();
        name = nameView.getText().toString().trim();
        workplace = workplaceView.getText().toString().trim();
        password = passwordView.getText().toString();
        String confirmPassword = confPassView.getText().toString();

        if(TextUtils.isEmpty(email)) {
            Utility.showToast(R.string.error_empty_username);
            return false;
        }

        if(TextUtils.isEmpty(username)) {
            Utility.showToast(R.string.error_empty_username);
            return false;
        }

        if(TextUtils.isEmpty(name)) {
            Utility.showToast(R.string.error_empty_name);
            return false;
        }

        if(TextUtils.isEmpty(workplace)) {
            Utility.showToast(R.string.error_empty_workplace);
            return false;
        }

        if(TextUtils.isEmpty(password)) {
            Utility.showToast(R.string.error_password_required);
            return false;
        }

        if(TextUtils.isEmpty(confirmPassword)) {
            Utility.showToast(R.string.error_password_confirm);
            return false;
        }

        if(!password.equals(confirmPassword)) {
            Utility.showToast(R.string.error_password_mismatch);
            return false;
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
    public void onCodeRetrieved(String confirmationCode) {
        CodeConfirmFragment fragment = CodeConfirmFragment.getInstance(email, confirmationCode);
        fragment.show(getFragmentManager(), "CodeConfirm");
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) v.setBackgroundResource(R.drawable.edittext_focused);
        else v.setBackgroundResource(R.drawable.edittext);
    }
}
