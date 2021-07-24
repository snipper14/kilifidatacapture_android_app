package sols.develiz.ngodatacapture;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.style.ReplacementSpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.thekhaeng.pushdownanim.PushDownAnim;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import sols.develiz.ngodatacapture.base.BaseActivity;
import sols.develiz.ngodatacapture.consts.Const;
import sols.develiz.ngodatacapture.helper.SharedPreferenceUtils;
import sols.develiz.ngodatacapture.presenter.LoginPresenter;
import sols.develiz.ngodatacapture.view.LoginView;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;
import static sols.develiz.ngodatacapture.helper.IsDeviceConnected.isConnected;
import static sols.develiz.ngodatacapture.widgets.ShowAlert.showAlert;

public class LoginActivity extends BaseActivity implements View.OnClickListener, LoginView {

    private ProgressDialog progressDialog;
    private LoginPresenter loginPresenter;
    private EditText etUsername, etPassword;
    private SharedPreferenceUtils sharedPreferenceUtils;

    @Override
    public void initActivity(Bundle b) {
        PushDownAnim.setPushDownAnimTo(findViewById(R.id.tv_registration)).setScale(MODE_SCALE, 0.89f).setOnClickListener(this);
        PushDownAnim.setPushDownAnimTo(findViewById(R.id.btnLogin)).setScale(MODE_SCALE, 0.89f).setOnClickListener(this);
        loginPresenter = new LoginPresenter(this, this);
        sharedPreferenceUtils=new SharedPreferenceUtils(LoginActivity.this);
        initElem();
    }

    private void initElem() {
        etPassword = findViewById(R.id.etPassword);
        etUsername = findViewById(R.id.etUsername);
    }

    @Override
    public int initView() {
        return R.layout.activity_login;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                if (isConnected(this)) {
                    validateAndSendData();
                } else {
                    showAlert(LoginActivity.this, "No Internet Connection");

                }
//                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
//                finish();
                break;
            case R.id.tv_registration:
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
                finish();
                break;
        }
    }

    private void validateAndSendData() {
        String email, password;
        email = etUsername.getText().toString();
        password = etPassword.getText().toString();
        int count = 0;
        if (TextUtils.isEmpty(email)) {
            showSnack(etUsername, "Username required");
            count++;
        }
        if (TextUtils.isEmpty(password)) {
            showSnack(etPassword, "Password required");
            count++;
        }


        if (count == 0) {
            Map<String, String> map = new HashMap<>();
            map.put("email", email);
            map.put("password", password);
            loginPresenter.loginApiCall(map);

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
    }

    @Override
    public void loginApiResponse(String response) {

        try {
            JSONObject jsonObject=new JSONObject(response);

            if(jsonObject.getString("success").equals("false")){
                showAlert(LoginActivity.this,jsonObject.getString("message"));
            }else{
                JSONObject dataObject=new JSONObject(jsonObject.getString("data"));
                String email=dataObject.getString("email");
                String id=dataObject.getString("id");
                String name=dataObject.getString("name");

                sharedPreferenceUtils.saveInputDataSharedPrefs(Const.AGENT_MAIL,email);
                sharedPreferenceUtils.saveInputDataSharedPrefs(Const.AGENT_ID,id);
                sharedPreferenceUtils.saveInputDataSharedPrefs(Const.AGENT_NAME,name);
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
               finish();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}