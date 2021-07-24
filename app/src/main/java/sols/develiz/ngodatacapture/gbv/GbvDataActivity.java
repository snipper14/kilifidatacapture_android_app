package sols.develiz.ngodatacapture.gbv;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.thekhaeng.pushdownanim.PushDownAnim;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import sols.develiz.ngodatacapture.R;
import sols.develiz.ngodatacapture.base.BaseActivity;
import sols.develiz.ngodatacapture.consts.Const;
import sols.develiz.ngodatacapture.gbv.presenters.GBVDataPresenter;
import sols.develiz.ngodatacapture.gbv.views.GBVDataView;
import sols.develiz.ngodatacapture.helper.SharedPreferenceUtils;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;
import static sols.develiz.ngodatacapture.helper.IsDeviceConnected.isConnected;
import static sols.develiz.ngodatacapture.widgets.ShowAlert.showAlert;

public class GbvDataActivity extends BaseActivity implements View.OnClickListener, GBVDataView {

    private EditText datepicker_activitydate;
    private String gbv_healthcare, gbvViolationType, gbvAgeViolation14, gbvAgeViolation17;
    private EditText etCounty, etSubCounty, etWard, etLocation, dp_activity_date, et_gbv_male_10_14, et_gbv_female_10_14, et_gbv_male_15_17, et_gbv_female_15_17, et_police_reported,
            et_hosp_reported, et_male_start_psycho_help, et_female_start_psycho_help, et_male_complete_psycho_help, et_female_complete_psycho_help,
            etSubmittedBy, etOrganization;
    private String report_entity,
            violation,
            county,
            sub_county,
            ward,
            location,
            activity_date,
            defilement_male_age_10_14,
            defilement_female_age_10_14,
            defilement_male_age_15_17,
            defilement_female_age_15_17,


    sodomy_male_age_10_14,
            sodomy_female_age_10_14,
            sodomy_male_age_15_17,
            sodomy_female_age_15_17,

    sgbv_male_age_18_24,
            sgbv_female_age_18_24,
            sgbv_male_age_above_24,
            sgbv_female_age_above_24,


    rape_female_age_15_19,
            rape_female_age_20_24,
            rape_female_age_24,

    total_refered_police,
            total_refered_hospital,
            total_male_start_psychological_support,
            total_female_start_psychological_support,
            total_male_complete_psychological_support,
            total_female_complete_psychological_support,

    police_post,
            court,
            agent_id,
            submitted_by,
            organization;
    private Calendar myCalendar;
    private ProgressDialog progressDialog;
    private SharedPreferenceUtils sharedPreferenceUtils;
    private GBVDataPresenter gbvDataPresenter;

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
                Intent intent = new Intent(GbvDataActivity.this, ViolationTypeActivity.class);

                startActivity(intent);

                finish();
            }
        });
        gbvDataPresenter = new GBVDataPresenter(this, this);

        sharedPreferenceUtils = new SharedPreferenceUtils(this);
        gbv_healthcare = sharedPreferenceUtils.getSharedPrefStringData(Const.GBV_CATEGORY);
        report_entity = gbv_healthcare;
        gbvViolationType = sharedPreferenceUtils.getSharedPrefStringData(Const.GBV_VIOLATION);
        violation = gbvViolationType;
        gbvAgeViolation14 = sharedPreferenceUtils.getSharedPrefStringData(Const.GBV_VIOLATION_AGE_14);
        gbvAgeViolation17 = sharedPreferenceUtils.getSharedPrefStringData(Const.GBV_VIOLATION_AGE_17);

        TextView gbv_category = findViewById(R.id.tv_outcome3);
        gbv_category.setText(gbv_healthcare);
        TextView tv_violation = findViewById(R.id.tv_violation);
        tv_violation.setText(gbvViolationType);

        TextView tv_violation_age_10_14 = findViewById(R.id.tv_violation_age_10_14);
        tv_violation_age_10_14.setText(gbvAgeViolation14);

        TextView tv_violation_age_15_17 = findViewById(R.id.tv_violation_age_15_17);
        tv_violation_age_15_17.setText(gbvAgeViolation17);
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
        et_gbv_male_10_14 = findViewById(R.id.et_gbv_male_10_14);
        et_gbv_female_10_14 = findViewById(R.id.et_gbv_female_10_14);
        et_gbv_male_15_17 = findViewById(R.id.et_gbv_male_15_17);
        et_gbv_female_15_17 = findViewById(R.id.et_gbv_female_15_17);
        et_police_reported = findViewById(R.id.et_police_reported);
        et_hosp_reported = findViewById(R.id.et_hosp_reported);
        et_male_start_psycho_help = findViewById(R.id.et_male_start_psycho_help);
        et_female_start_psycho_help = findViewById(R.id.et_female_start_psycho_help);
        et_male_complete_psycho_help = findViewById(R.id.et_male_complete_psycho_help);
        et_female_complete_psycho_help = findViewById(R.id.et_female_complete_psycho_help);

        etSubmittedBy = findViewById(R.id.etSubmittedBy);
        etOrganization = findViewById(R.id.etOrganization);
    }

    @Override
    public int initView() {
        return R.layout.activity_gbv_data;
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
                new DatePickerDialog(GbvDataActivity.this, date, myCalendar
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit_info:
                if (isConnected(this)) {
                    validateAndSendData();
                } else {
                    showAlert(GbvDataActivity.this, "No Internet Connection");

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
        submitted_by = etSubmittedBy.getText().toString();
        organization = etOrganization.getText().toString();
        if (violation.equals(getResources().getString(R.string.gbv_defilement))) {
            defilement_male_age_10_14 = et_gbv_male_10_14.getText().toString();
            defilement_female_age_10_14 = et_gbv_female_10_14.getText().toString();
            defilement_male_age_15_17 = et_gbv_male_15_17.getText().toString();
            defilement_female_age_15_17 = et_gbv_female_15_17.getText().toString();
        } else if (violation.equals(getResources().getString(R.string.gbv_sodomy))) {
            sodomy_male_age_10_14 = et_gbv_male_10_14.getText().toString();
            sodomy_female_age_10_14 = et_gbv_female_10_14.getText().toString();
            sodomy_male_age_15_17 = et_gbv_male_15_17.getText().toString();
            sodomy_female_age_15_17 = et_gbv_female_15_17.getText().toString();
        } else if (violation.equals(getResources().getString(R.string.gbv_sgbv))) {
            sgbv_male_age_18_24 = et_gbv_male_10_14.getText().toString();
            sgbv_female_age_18_24 = et_gbv_female_10_14.getText().toString();
            sgbv_male_age_above_24 = et_gbv_male_15_17.getText().toString();
            sgbv_female_age_above_24 = et_gbv_female_15_17.getText().toString();
        }

        total_refered_police = et_police_reported.getText().toString();
        total_refered_hospital = et_hosp_reported.getText().toString();
        total_male_start_psychological_support = et_male_start_psycho_help.getText().toString();
        total_female_start_psychological_support = et_female_start_psycho_help.getText().toString();
        total_male_complete_psychological_support = et_male_complete_psycho_help.getText().toString();
        total_female_complete_psychological_support = et_female_complete_psycho_help.getText().toString();
        police_post = et_police_reported.getText().toString();

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
            showSnack(etWard, "Error  Organization is required !!!");

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
            map.put("organization", organization);
            map.put("submitted_by", submitted_by);
            if (violation.equals(getResources().getString(R.string.gbv_defilement))) {
                map.put("defilement_male_age_10_14", defilement_male_age_10_14);
                map.put("defilement_female_age_10_14", defilement_female_age_10_14);
                map.put("defilement_male_age_15_17", defilement_male_age_15_17);
                map.put("defilement_female_age_15_17", defilement_female_age_15_17);
            }
             if (violation.equals(getResources().getString(R.string.gbv_sodomy))) {
                 map.put("sodomy_male_age_10_14", sodomy_male_age_10_14);
                 map.put("sodomy_female_age_10_14", sodomy_female_age_10_14);
                 map.put("sodomy_male_age_15_17", sodomy_male_age_15_17);
                 map.put("sodomy_female_age_15_17", sodomy_female_age_15_17);
             }

            if (violation.equals(getResources().getString(R.string.gbv_sgbv))) {
                map.put("sgbv_male_age_18_24", sgbv_male_age_18_24);
                map.put("sgbv_female_age_18_24", sgbv_female_age_18_24);
                map.put("sgbv_male_age_above_24", sgbv_male_age_above_24);
                map.put("sgbv_female_age_above_24", sgbv_female_age_above_24);
            }
          map.put("total_refered_police", total_refered_police);
            map.put("total_refered_hospital", total_refered_hospital);
            map.put("total_male_start_psychological_support", total_male_start_psychological_support);
            map.put("total_female_start_psychological_support", total_female_start_psychological_support);
            map.put("total_male_complete_psychological_support", total_male_complete_psychological_support);
            map.put("total_female_complete_psychological_support", total_female_complete_psychological_support);
            map.put("report_entity", report_entity);
            map.put("violation", violation);

            gbvDataPresenter.registrationApiCall(map);

        }
    }

    @Override
    public void onBackPressed() {

        GbvDataActivity.super.onBackPressed();
        Intent intent = new Intent(GbvDataActivity.this, ViolationTypeActivity.class);

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
            showAlert(GbvDataActivity.this, "Server responded with error 500");
        } else if (statusCode == 419) {
            showAlert(GbvDataActivity.this, "Server responded with error 419");
        } else {
            showAlert(GbvDataActivity.this, "Unknown Server Error!!!");
        }
    }

    @Override
    public void registrationApiResponse(String response) {
        Log.e("Success response", response);
        showToast("Data Successfully Recorded");
        startActivity(new Intent(GbvDataActivity.this, ViolationTypeActivity.class));
        finish();
    }
}