package apps.ahqmrf.contestnotifier.auth.ui;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.rey.material.widget.ProgressView;

import apps.ahqmrf.contestnotifier.R;
import apps.ahqmrf.contestnotifier.auth.service.AuthConnector;
import apps.ahqmrf.contestnotifier.auth.service.CodeListener;
import apps.ahqmrf.contestnotifier.utils.Const;
import apps.ahqmrf.contestnotifier.utils.Utility;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by bsse0 on 6/30/2017.
 */

public class CodeConfirmFragment extends DialogFragment implements CodeListener {

    View rootView;
    private Unbinder unbinder;
    private String   email, code;

    @BindView(R.id.input_code)      EditText     codeView;
    @BindView(R.id.progress_layout) ProgressView progressView;

    public static CodeConfirmFragment getInstance(String email, String code) {
        Bundle args = new Bundle();
        args.putString(Const.EMAIL, email);
        args.putString(Const.CONFIRMATION_CODE, code);

        CodeConfirmFragment fragment = new CodeConfirmFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_code_confirm, container, false);

        unbinder = ButterKnife.bind(this, rootView);

        email = getArguments().getString(Const.EMAIL);
        code = getArguments().getString(Const.CONFIRMATION_CODE);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        codeView.setBackgroundResource(R.drawable.edittext_focused);
    }

    @OnClick(R.id.button_submit)
    void onSubmitClick() {
        String inputCode = codeView.getText().toString().trim();
        if (inputCode.equals(code)) {
            new AuthConnector(this).activateAccount(email);
        } else {
            onFailure(Utility.getString(R.string.error_code_mismatch));
        }
    }

    @OnClick(R.id.button_resend)
    void onResendClick() {
        new AuthConnector(this).sendConfirmationCode(email);
    }

    @Override
    public void onResume() {
        if (getDialog().getWindow() != null) {
            ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
            params.width = LinearLayout.LayoutParams.MATCH_PARENT;
            params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        }
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
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
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        dismiss();
    }

    @Override
    public void onCodeRetrieved(String code) {
        this.code = code;
    }
}
