package sols.develiz.ngodatacapture;

import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputEditText;
import com.thekhaeng.pushdownanim.PushDownAnim;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sols.develiz.ngodatacapture.base.BaseActivity;
import sols.develiz.ngodatacapture.presenter.RegistrationPresenter;
import sols.develiz.ngodatacapture.view.RegistrationView;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;
import static sols.develiz.ngodatacapture.helper.IsDeviceConnected.isConnected;
import static sols.develiz.ngodatacapture.widgets.ShowAlert.showAlert;

public class RegistrationActivity extends BaseActivity implements RegistrationView,View.OnClickListener {

    private ProgressDialog progressDialog;
    private RegistrationPresenter registrationPresenter;
    private TextInputEditText tv_fullnames,tv_email,tv_password,tv_password_confirm,tv_phoneno,tv_id;
   private String gender,name,email,phone,id_no,password,password_confirmation;
    @Override
    public void initActivity(Bundle b) {
        Toolbar toolbar = findViewById(R.id.tool_bar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Agents Registration");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegistrationActivity.this, LoginActivity.class);

                startActivity(intent);

                finish();
            }
        });
        Spinner spinnerGender=findViewById(R.id.spnGender);
        final String[] genderArray = { "Male", "Female"};
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, genderArray );
        spinnerGender.setAdapter(adapter);
        spinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                gender = genderArray[position];


            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });
        PushDownAnim.setPushDownAnimTo(findViewById(R.id.btn_submit_info)).setScale(MODE_SCALE, 0.89f).setOnClickListener(this);
        registrationPresenter=new RegistrationPresenter(this,this);
        initViewElements();
    }

    @Override
    public int initView() {
        return R.layout.activity_registration;
    }

    private void initViewElements(){
        tv_fullnames=findViewById(R.id.tv_fullnames);
        tv_email=findViewById(R.id.tv_email);
        tv_password=findViewById(R.id.tv_password);
        tv_password_confirm=findViewById(R.id.tv_password_confirm);
        tv_phoneno=findViewById(R.id.tv_phoneno);
        tv_id=findViewById(R.id.tv_id);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_submit_info:
                if (isConnected(this)) {
                    validateAndSendData();
                } else {
                    showAlert(RegistrationActivity.this, "No Internet Connection");

                }

                break;
        }
    }


    private void validateAndSendData(){
        name=tv_fullnames.getText().toString();
        email=tv_email.getText().toString();
        phone=tv_phoneno.getText().toString();
        id_no=tv_id.getText().toString();
        password=tv_password.getText().toString();
        password_confirmation=tv_password_confirm.getText().toString();
        int count = 0;
        if (TextUtils.isEmpty(email)) {
            tv_email.setError("Required");
            count++;
        }
        if (TextUtils.isEmpty(name)) {
            tv_fullnames.setError("Required");
            count++;
        }
        if (TextUtils.isEmpty(phone)) {
            tv_phoneno.setError("Required");
            count++;
        }
        if (TextUtils.isEmpty(password)) {
            tv_password.setError("Required");
            count++;
        }
        if (TextUtils.isEmpty(password_confirmation)) {
            tv_password_confirm.setError("Required");
            count++;
        }
        if (!password_confirmation.equals(password)) {
            tv_password_confirm.setError("Confirm Password Is Incorrect");
            count++;
        }



        if (TextUtils.isEmpty(gender)) {
            showAlert(this, "Select Gender");
            count++;
        }

        if (count == 0) {
            Map<String, String> map = new HashMap<>();
            map.put("name", name);
            map.put("email", email);
            map.put("phone", phone);
            map.put("id_no", id_no);
            map.put("password", password);
            map.put("gender", gender);
            map.put("password_confirmation", password_confirmation);

            registrationPresenter.agentRegApiCall(map);

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
        Log.e("RESPONSE ERROR",s);
        progressDialog.dismiss();
        if(statusCode==422){
            showSnack(tv_email,"Error occurred please check you fields");
            try {
                JSONObject object=new JSONObject(s);
                if(object.has("email")){
                    tv_email.setError(object.getString("email").replace("[","").replace("]",""));
                }
                if(object.has("password_confirmation")){
                    tv_password_confirm.setError(object.getString("password_confirmation").replace("[","").replace("]",""));
                  }
                if(object.has("password")){
                    tv_password.setError(object.getString("password").replace("[","").replace("]",""));
                }
                if(object.has("phone")){
                    tv_phoneno.setError(object.getString("phone").replace("[","").replace("]",""));
                }
                if(object.has("id_no")){
                    tv_id.setError(object.getString("id_no").replace("[","").replace("]",""));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }else if(statusCode==500){
            showAlert(RegistrationActivity.this, "Server responded with error 500");
        }else if(statusCode==419){
            showAlert(RegistrationActivity.this, "Server responded with error 419");
        }else {
            showAlert(RegistrationActivity.this, "Unknown Server Error!!!");
        }
    }

    @Override
    public void registrationApiResponse(String response) {

        progressDialog.dismiss();
        try {
            JSONObject object =new JSONObject(response);
            if(object.getString("success").equals("true")){

                    showToast(object.getString("message"));
                    Intent intent=new Intent(RegistrationActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();

            }
        } catch (JSONException e) {
            showAlert(RegistrationActivity.this,"Unknown Server error occurred, try later");
            e.printStackTrace();
        }

    }
}