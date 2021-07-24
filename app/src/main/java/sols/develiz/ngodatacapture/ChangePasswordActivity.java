package sols.develiz.ngodatacapture;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;
import com.thekhaeng.pushdownanim.PushDownAnim;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import sols.develiz.ngodatacapture.base.BaseActivity;
import sols.develiz.ngodatacapture.consts.Const;
import sols.develiz.ngodatacapture.helper.SharedPreferenceUtils;
import sols.develiz.ngodatacapture.presenter.ChangePasswordPresenter;
import sols.develiz.ngodatacapture.view.ChangePassword;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;
import static sols.develiz.ngodatacapture.helper.IsDeviceConnected.isConnected;
import static sols.develiz.ngodatacapture.widgets.ShowAlert.showAlert;

public class ChangePasswordActivity extends BaseActivity implements View.OnClickListener, ChangePassword {

    private TextInputEditText tvOldPassword, tvNewPassword, tvConfirmPassword;
    private ChangePasswordPresenter changePasswordPresenter;
    private ProgressDialog progressDialog;
    @Override
    public void initActivity(Bundle b) {
        Toolbar toolbar = findViewById(R.id.tool_bar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Change Password");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChangePasswordActivity.this, HomeActivity.class);

                startActivity(intent);

                finish();
            }
        });
        changePasswordPresenter = new ChangePasswordPresenter(this, this);
        PushDownAnim.setPushDownAnimTo(findViewById(R.id.btnChangePassword)).setScale(MODE_SCALE, 0.89f).setOnClickListener(this);
        initUiElement();
    }

    private void initUiElement() {
        tvOldPassword = findViewById(R.id.tvOldPassword);
        tvNewPassword = findViewById(R.id.tvNewPassword);
        tvConfirmPassword = findViewById(R.id.tvConfirmPassword);

    }

    @Override
    public int initView() {
        return R.layout.activity_change_password;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnChangePassword:
                if (isConnected(this)) {
                    validateAndSendData();
                } else {
                    showAlert(ChangePasswordActivity.this, "No Internet Connection");

                }

                break;
        }
    }

    private void validateAndSendData() {
        String old_password=tvOldPassword.getText().toString();
        String new_password=tvNewPassword.getText().toString();
        String password_confirmation=tvConfirmPassword.getText().toString();

        int count = 0;
        if (TextUtils.isEmpty(old_password)) {
            tvOldPassword.setError("Required");
            count++;
        }
        if (TextUtils.isEmpty(new_password)) {
            tvNewPassword.setError("Required");
            count++;
        }

        if (!password_confirmation.equals(new_password)) {
            tvConfirmPassword.setError("Confirm Password Is Incorrect");
            count++;
        }

        if (count == 0) {
            final String id=new SharedPreferenceUtils(this).getSharedPrefStringData(Const.AGENT_ID);
            Map<String, String> map = new HashMap<>();
            map.put("new_password", new_password);
            map.put("old_password", old_password);
            map.put("password_confirmation", password_confirmation);
            map.put("id", id);
            changePasswordPresenter.changePassApiCall(map);

        }
    }

    @Override
    public void showProgressBar(String s) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(s);
        progressDialog.show();
        progressDialog.setCancelable(false);
    }

    @Override
    public void hideProgress() {
        try {
            progressDialog.dismiss();
        } catch (Exception e) {
        }
    }

    @Override
    public void displayVolleyError(String s, int statusCode) {
        progressDialog.dismiss();
        if(statusCode==422){
            showSnack(tvConfirmPassword,"Error occurred please check you fields");
            try {
                JSONObject object=new JSONObject(s);
                if(object.has("new_password")){
                    tvNewPassword.setError(object.getString("new_password").replace("[","").replace("]",""));
                }
                if(object.has("password_confirmation")){
                    tvConfirmPassword.setError(object.getString("password_confirmation").replace("[","").replace("]",""));
                }
                if(object.has("old_password")){
                    tvOldPassword.setError(object.getString("old_password").replace("[","").replace("]",""));
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }else if(statusCode==500){
            showAlert(ChangePasswordActivity.this, "Server responded with error 500");
        }else if(statusCode==419){
            showAlert(ChangePasswordActivity.this, "Server responded with error 419");
        }else {
            showAlert(ChangePasswordActivity.this, "Unknown Server Error!!!");
        }
    }

    @Override
    public void passwordApiResponse(String response) {
        try {
            JSONObject jsonObject=new JSONObject(response);

            if(jsonObject.getString("success").equals("false")){
                showAlert(ChangePasswordActivity.this,jsonObject.getString("message"));
            }else{
                showToast("Successfully updated password");
                    new SharedPreferenceUtils(ChangePasswordActivity.this).clear();
                startActivity(new Intent(ChangePasswordActivity.this,LoginActivity.class));
                finish();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}