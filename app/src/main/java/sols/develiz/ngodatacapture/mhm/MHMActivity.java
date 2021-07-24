package sols.develiz.ngodatacapture.mhm;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.widget.Toolbar;

import com.thekhaeng.pushdownanim.PushDownAnim;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import sols.develiz.ngodatacapture.HomeActivity;
import sols.develiz.ngodatacapture.R;
import sols.develiz.ngodatacapture.base.BaseActivity;
import sols.develiz.ngodatacapture.consts.Const;
import sols.develiz.ngodatacapture.gbv.GBVDataCaptureInc;
import sols.develiz.ngodatacapture.gbv.GbvReportingActivity;
import sols.develiz.ngodatacapture.helper.SharedPreferenceUtils;
import sols.develiz.ngodatacapture.mhm.presenters.MHMPresenter;
import sols.develiz.ngodatacapture.mhm.view.MHMView;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;
import static sols.develiz.ngodatacapture.helper.IsDeviceConnected.isConnected;
import static sols.develiz.ngodatacapture.widgets.ShowAlert.showAlert;

public class MHMActivity extends BaseActivity implements View.OnClickListener, MHMView {


    private EditText datepicker_activitydate;
    private EditText etCounty, etSubCounty, etWard, etLocation, dp_activity_date,
            et_total_male_reached, et_total_female_reached, et_total_female_reached_10_14,
            et_total_female_reached_15_19, et_total_female_reached_20_24, et_total_sanitary_pads_reusable,
            et_total_sanitary_pads_disposable, et_submitted_by, et_organization;

    private String report_entity,

    county,
            sub_county,
            ward,
            location,
            activity_date,
            total_male_reached,
            total_female_reached,
            total_female_reached_with_products_10_14,
            total_female_reached_with_products_15_19,
            total_female_reached_with_products_20_14,
            total_sanitary_distributed_reusable,
            total_sanitary_distributed_disposable,
            agent_id,
            submitted_by,
            organization;
    private Calendar myCalendar;
    private ProgressDialog progressDialog;
    private SharedPreferenceUtils sharedPreferenceUtils;
    private MHMPresenter mhmPresenter;

    @Override
    public void initActivity(Bundle b) {
        Toolbar toolbar = findViewById(R.id.tool_bar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("GBV Data Reports");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MHMActivity.this, HomeActivity.class);

                startActivity(intent);

                finish();
            }
        });
        mhmPresenter = new MHMPresenter(this, this);
        report_entity = getResources().getString(R.string.mhm_reporting);
        sharedPreferenceUtils = new SharedPreferenceUtils(MHMActivity.this);
        PushDownAnim.setPushDownAnimTo(findViewById(R.id.btn_submit_info)).setScale(MODE_SCALE, 0.89f).setOnClickListener(this);
        initElement();

        activityDate();

    }

    private void initElement() {
        etCounty = findViewById(R.id.etCounty);
        etSubCounty = findViewById(R.id.etSubCounty);
        etWard = findViewById(R.id.etWard);
        etLocation = findViewById(R.id.etLocation);
        dp_activity_date = findViewById(R.id.dp_activity_date);
        et_total_male_reached = findViewById(R.id.et_total_male_reached);

        et_total_female_reached = findViewById(R.id.et_total_female_reached);
        et_total_female_reached_10_14 = findViewById(R.id.et_total_female_reached_10_14);
        et_total_female_reached_15_19 = findViewById(R.id.et_total_female_reached_15_19);
        et_total_female_reached_20_24 = findViewById(R.id.et_total_female_reached_20_24);
        et_total_sanitary_pads_reusable = findViewById(R.id.et_total_sanitary_pads_reusable);
        et_total_sanitary_pads_disposable = findViewById(R.id.et_total_sanitary_pads_disposable);
        et_submitted_by = findViewById(R.id.et_submitted_by);
        et_organization = findViewById(R.id.et_organization);
    }

    private void activityDate() {
        myCalendar = Calendar.getInstance();

        datepicker_activitydate = (EditText) findViewById(R.id.dp_activity_date);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        datepicker_activitydate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(MHMActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

        datepicker_activitydate.setText(sdf.format(myCalendar.getTime()));
    }


    @Override
    public int initView() {
        return R.layout.activity_m_h_m;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit_info:
                if (isConnected(this)) {
                    validateAndSendData();
                } else {
                    showAlert(MHMActivity.this, "No Internet Connection");

                }
//                startActivity(new Intent(DataRegistrationActivity.this,MainActivity.class));
//                finish();
                break;
        }
    }

    private void validateAndSendData() {
        county = etCounty.getText().toString();
        sub_county = etSubCounty.getText().toString();
        ward = etWard.getText().toString();
        location = etLocation.getText().toString();
        activity_date = dp_activity_date.getText().toString();
        submitted_by = et_submitted_by.getText().toString();


        total_male_reached = et_total_male_reached.getText().toString();
        total_female_reached = et_total_female_reached.getText().toString();
        total_female_reached_with_products_10_14 = et_total_female_reached_10_14.getText().toString();
        total_female_reached_with_products_15_19 = et_total_female_reached_15_19.getText().toString();
        total_female_reached_with_products_20_14 = et_total_female_reached_20_24.getText().toString();
        total_sanitary_distributed_reusable = et_total_sanitary_pads_reusable.getText().toString();
        total_sanitary_distributed_disposable = et_total_sanitary_pads_disposable.getText().toString();

        organization = et_organization.getText().toString();

        agent_id = sharedPreferenceUtils.getSharedPrefStringData(Const.AGENT_ID);
        int count = 0;

        if (TextUtils.isEmpty(county)) {
            showSnack(etWard, "Error county is required !!!");

            count++;
            return;
        }
        if (TextUtils.isEmpty(sub_county)) {
            showSnack(etWard, "Error Sub County is required !!!");

            count++;
            return;
        }
        if (TextUtils.isEmpty(ward)) {
            showSnack(etWard, "Error Ward is required !!!");

            count++;
            return;
        }
        if (TextUtils.isEmpty(location)) {
            showSnack(etWard, "Error Location is required !!!");

            count++;
            return;
        }

        if (TextUtils.isEmpty(activity_date)) {
            showSnack(etWard, "Error Activity Date is required !!!");

            count++;
            return;
        }
        if (TextUtils.isEmpty(submitted_by)) {
            showSnack(etWard, "Error Reported By is required !!!");

            count++;
            return;
        }
        if (TextUtils.isEmpty(organization)) {
            showSnack(etWard, "Error  organization name is required !!!");

            count++;
            return;
        }


        if (count == 0) {
            Map<String, String> map = new HashMap<>();
            map.put("county", county);
            map.put("sub_county", sub_county);
            map.put("ward", ward);
            map.put("agent_id", agent_id);
            map.put("location", location);
            map.put("activity_date", activity_date);
            map.put("report_entity", report_entity);
            map.put("submitted_by", submitted_by);
            map.put("total_male_reached", total_male_reached);
            map.put("total_female_reached", total_female_reached);
            map.put("total_female_reached_with_products_10_14", total_female_reached_with_products_10_14);
            map.put("total_female_reached_with_products_15_19", total_female_reached_with_products_15_19);
            map.put("total_female_reached_with_products_20_14", total_female_reached_with_products_20_14);
            map.put("total_sanitary_distributed_reusable", total_sanitary_distributed_reusable);
            map.put("total_sanitary_distributed_disposable", total_sanitary_distributed_disposable);
            map.put("organization", organization);


            mhmPresenter.registrationApiCall(map);

        }
    }

    @Override
    public void onBackPressed() {

        MHMActivity.super.onBackPressed();
        Intent intent = new Intent(this, HomeActivity.class);

        startActivity(intent);

        finish();

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
        if (statusCode == 422) {
            showSnack(etWard, "Error occurred please check you fields if they are empty");


        } else if (statusCode == 500) {
            showAlert(MHMActivity.this, "Server responded with error 500");
        } else if (statusCode == 419) {
            showAlert(MHMActivity.this, "Server responded with error 419");
        } else {
            showAlert(MHMActivity.this, "Unknown Server Error!!!");
        }
    }

    @Override
    public void registrationApiResponse(String response) {
        showToast("Data Successfully Recorded");
        startActivity(new Intent(MHMActivity.this, HomeActivity.class));
        finish();
    }
}