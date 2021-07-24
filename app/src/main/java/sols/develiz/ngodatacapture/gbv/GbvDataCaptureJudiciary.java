package sols.develiz.ngodatacapture.gbv;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

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

public class GbvDataCaptureJudiciary extends BaseActivity implements  View.OnClickListener, GBVDataView {
    private EditText datepicker_activitydate;
    private String gbv_healthcare,gbvViolationType, gbvAgeViolation14,gbvAgeViolation17;
    private EditText etCounty, etSubCounty, etWard, etLocation, dp_activity_date,
            et_male_defilement_10_14,
            et_female_defilement_10_14,
            et_female_defilement_15_17,et_male_defilement_15_17,et_male_sodomy_10_14,et_female_sodomy_10_14,
            et_male_sodomy_15_17,et_female_sodomy_15_17,et_male_sgbv_18_24,et_female_sgbv_18_24,et_male_sgbv_24,
            et_female_sgbv_24,et_female_rape_15_19,et_female_rape_20_24,et_female_rape_24,et_reported_by,et_court,
            et_court_no;

    private String report_entity,

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


            court,
                    court_no,
            agent_id,
            submitted_by;
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
                Intent intent=new Intent(GbvDataCaptureJudiciary.this, GbvReportingActivity.class);

                startActivity(intent);

                finish();
            }
        });

        gbvDataPresenter=new GBVDataPresenter(this,this);
         sharedPreferenceUtils=new SharedPreferenceUtils(this);
        gbv_healthcare=sharedPreferenceUtils.getSharedPrefStringData(Const.GBV_CATEGORY);
        gbvViolationType= sharedPreferenceUtils.getSharedPrefStringData(Const.GBV_VIOLATION);

        TextView gbv_category=findViewById(R.id.tv_outcome3);
        gbv_category.setText(gbv_healthcare);
        report_entity = gbv_healthcare;

        PushDownAnim.setPushDownAnimTo(findViewById(R.id.btn_submit_info)).setScale(MODE_SCALE, 0.89f).setOnClickListener(this);
        initElement();

        activityDate();


    }

    private void initElement() {
        etCounty=findViewById(R.id.etCounty);
        etSubCounty=findViewById(R.id.etSubCounty);
        etWard=findViewById(R.id.etWard);
        etLocation=findViewById(R.id.etLocation);
        dp_activity_date=findViewById(R.id.dp_activity_date);

        et_male_defilement_10_14=findViewById(R.id.et_male_defilement_10_14);
        et_female_defilement_10_14=findViewById(R.id.et_female_defilement_10_14);
        et_male_defilement_15_17=findViewById(R.id.et_male_defilement_15_17);
        et_female_defilement_15_17=findViewById(R.id.et_female_defilement_15_17);

        et_male_sodomy_10_14=findViewById(R.id.et_male_sodomy_10_14);
        et_female_sodomy_10_14=findViewById(R.id.et_female_sodomy_10_14);

        et_male_sodomy_15_17=findViewById(R.id.et_male_sodomy_15_17);
        et_female_sodomy_15_17=findViewById(R.id.et_female_sodomy_15_17);
        et_male_sgbv_18_24=findViewById(R.id.et_male_sgbv_18_24);
        et_female_sgbv_18_24=findViewById(R.id.et_female_sgbv_18_24);
        et_female_sgbv_24=findViewById(R.id.et_female_sgbv_24);
        et_male_sgbv_24=findViewById(R.id.et_male_sgbv_24);

        et_reported_by=findViewById(R.id.et_reported_by);

        et_female_rape_15_19=findViewById(R.id.et_female_rape_15_19);
        et_female_rape_20_24=findViewById(R.id.et_female_rape_20_24);
        et_female_rape_24=findViewById(R.id.et_female_rape_24);
        et_reported_by=findViewById(R.id.et_reported_by);
        et_court=findViewById(R.id.et_court);
                et_court_no=findViewById(R.id.et_court_no);
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
                new DatePickerDialog(GbvDataCaptureJudiciary.this, date, myCalendar
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
        return R.layout.activity_gbv_data_capture_judiciary;
    }

    @Override
    public void onBackPressed() {

        GbvDataCaptureJudiciary.super.onBackPressed();
        Intent intent=new Intent(this, GbvReportingActivity.class);

        startActivity(intent);

        finish();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit_info:
                if (isConnected(this)) {
                    validateAndSendData();
                } else {
                    showAlert(GbvDataCaptureJudiciary.this, "No Internet Connection");

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
        submitted_by = et_reported_by.getText().toString();

        defilement_male_age_10_14 = et_male_defilement_10_14.getText().toString();
        defilement_female_age_10_14 = et_female_defilement_10_14.getText().toString();
        defilement_male_age_15_17 = et_male_defilement_15_17.getText().toString();
        defilement_female_age_15_17 = et_female_defilement_15_17.getText().toString();

        sodomy_male_age_10_14 = et_male_sodomy_10_14.getText().toString();
        sodomy_female_age_10_14 = et_female_sodomy_10_14.getText().toString();
        sodomy_male_age_15_17 = et_male_sodomy_15_17.getText().toString();
        sodomy_female_age_15_17 = et_female_sodomy_15_17.getText().toString();

        sgbv_male_age_18_24 = et_male_sgbv_18_24.getText().toString();
        sgbv_female_age_18_24 = et_female_sgbv_18_24.getText().toString();
        sgbv_male_age_above_24 = et_male_sgbv_24.getText().toString();
        sgbv_female_age_above_24 = et_female_sgbv_24.getText().toString();

                        rape_female_age_15_19=et_female_rape_15_19.getText().toString();
        rape_female_age_20_24=et_female_rape_20_24.getText().toString();
        rape_female_age_24=et_female_rape_24.getText().toString();
        et_reported_by=findViewById(R.id.et_reported_by);
        court=et_court.getText().toString();
        court_no=et_court_no.getText().toString();


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



        if (count == 0) {
            Map<String, String> map = new HashMap<>();
            map.put("county", county);
            map.put("sub_county", sub_county);
            map.put("ward", ward);
            map.put("agent_id", agent_id);
            map.put("location", location);
            map.put("activity_date", activity_date);
            map.put("submitted_by", submitted_by);

            map.put("defilement_male_age_10_14", defilement_male_age_10_14);
            map.put("defilement_female_age_10_14", defilement_female_age_10_14);
            map.put("defilement_male_age_15_17", defilement_male_age_15_17);
            map.put("defilement_female_age_15_17", defilement_female_age_15_17);


            map.put("sodomy_male_age_10_14", sodomy_male_age_10_14);
            map.put("sodomy_female_age_10_14", sodomy_female_age_10_14);
            map.put("sodomy_male_age_15_17", sodomy_male_age_15_17);
            map.put("sodomy_female_age_15_17", sodomy_female_age_15_17);


            map.put("sgbv_male_age_18_24", sgbv_male_age_18_24);
            map.put("sgbv_female_age_18_24", sgbv_female_age_18_24);
            map.put("sgbv_male_age_above_24", sgbv_male_age_above_24);
            map.put("sgbv_female_age_above_24", sgbv_female_age_above_24);


            map.put("report_entity", report_entity);
            map.put("rape_female_age_15_19", rape_female_age_15_19);
            map.put("rape_female_age_20_24", rape_female_age_20_24);
            map.put("rape_female_age_24", rape_female_age_24);
            map.put("court", court);
            map.put("court_no", court_no);

            gbvDataPresenter.registrationApiCall(map);

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
        if (statusCode == 422) {
            showSnack(etWard, "Error occurred please check you fields if they are empty");


        } else if (statusCode == 500) {
            showAlert(GbvDataCaptureJudiciary.this, "Server responded with error 500");
        } else if (statusCode == 419) {
            showAlert(GbvDataCaptureJudiciary.this, "Server responded with error 419");
        } else {
            showAlert(GbvDataCaptureJudiciary.this, "Unknown Server Error!!!");
        }
    }

    @Override
    public void registrationApiResponse(String response) {

        showToast("Data Successfully Recorded");
        startActivity(new Intent(GbvDataCaptureJudiciary.this, GbvReportingActivity.class));
        finish();
    }
}