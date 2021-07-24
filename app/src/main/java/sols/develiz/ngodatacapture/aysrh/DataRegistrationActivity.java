package sols.develiz.ngodatacapture.aysrh;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import sols.develiz.ngodatacapture.aysrh.presenter.DataRegistrationPresenter;
import sols.develiz.ngodatacapture.base.BaseActivity;
import sols.develiz.ngodatacapture.consts.Const;
import sols.develiz.ngodatacapture.helper.SharedPreferenceUtils;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;
import static sols.develiz.ngodatacapture.helper.IsDeviceConnected.isConnected;
import static sols.develiz.ngodatacapture.widgets.ShowAlert.showAlert;

public class DataRegistrationActivity extends BaseActivity implements View.OnClickListener, DataRegistrationView {

    private EditText datepicker_activitydate;
    private Calendar myCalendar;

    String strategic_objective, expected_outcome, strategies, activity_name,
            county, sub_county, ward, location, activity_date, total_male_10_14, total_female_10_14,
            total_male_15_17, total_female_15_17, total_male_18_24, total_female_18_24, total_male_25,
            total_female_25, total_male_female, activity_cost, no_iec, no_sanitary_pads, no_dignity_kits,
            no_panties_boxers, reported_by, organization;
    private EditText etCounty, etSubCounty, etWard, etLocation, etActivityName, dp_activity_date, etReachedMale14, etReachedFemale14,
            etReachedMale17, etReachedFemale17, etReachedMale24, etReachedFemale24, etReachedMale25, etReachedFemale25, etTotalMaleFemale,
            etActivityCost, etIEC, etNoSanitaryPads, etDignityKits, etPantiesBoxers, etSubmittedBy;
    private EditText etOrganization;
    private ProgressDialog progressDialog;
    private float total_attendees = 0;
    private DataRegistrationPresenter dataRegistrationPresenter;

    @Override
    public void initActivity(Bundle b) {
        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Data Collection Tool");
        Intent intent = getIntent();
        strategic_objective = intent.getStringExtra("NGO_OBJECTIVE");
        expected_outcome = intent.getStringExtra("NGO_OUTCOME");
        strategies = intent.getStringExtra("NGO_STRATEGY");

        TextView tv_objective3 = findViewById(R.id.tv_objective3);
        tv_objective3.setText(strategic_objective);

        TextView tv_outcome3 = findViewById(R.id.tv_outcome3);
        tv_outcome3.setText(expected_outcome);

        TextView tv_strategy3 = findViewById(R.id.tv_strategy3);
        tv_strategy3.setText(strategies);
        activityDate();
        dataRegistrationPresenter = new DataRegistrationPresenter(this, this);
        PushDownAnim.setPushDownAnimTo(findViewById(R.id.btn_submit_info)).setScale(MODE_SCALE, 0.89f).setOnClickListener(this);
        initElement();
        TextWatcher textWatcher = new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

                etTotalMaleFemale.setText( "");
                Log.e("TEXT CHED", ">>>" + s);
                try {
                    Float male14= TextUtils.isEmpty(etReachedMale14.getText().toString())?0:Float.parseFloat(etReachedMale14.getText().toString());
                    Float female14= TextUtils.isEmpty(etReachedFemale14.getText().toString())?0:Float.parseFloat(etReachedFemale14.getText().toString());
                    Float male17= TextUtils.isEmpty(etReachedMale17.getText().toString())?0:Float.parseFloat(etReachedMale17.getText().toString());
                    Float female17= TextUtils.isEmpty(etReachedFemale17.getText().toString())?0:Float.parseFloat(etReachedFemale17.getText().toString());
                    Float male24= TextUtils.isEmpty(etReachedMale24.getText().toString())?0:Float.parseFloat(etReachedMale24.getText().toString());
                    Float female24= TextUtils.isEmpty(etReachedFemale24.getText().toString())?0:Float.parseFloat(etReachedFemale24.getText().toString());
                    Float male25= TextUtils.isEmpty(etReachedMale25.getText().toString())?0:Float.parseFloat(etReachedMale25.getText().toString());
                    Float female25= TextUtils.isEmpty(etReachedFemale25.getText().toString())?0:Float.parseFloat(etReachedFemale25.getText().toString());
                  total_attendees = male14
                            + female14+male17+female17+male24+female24+male25+female25;

                    etTotalMaleFemale.setText(total_attendees + "");
                } catch (Exception e) {

                }

            }
        };
        etReachedFemale14.addTextChangedListener(textWatcher);
        etReachedMale14.addTextChangedListener(textWatcher);
        etReachedMale17.addTextChangedListener(textWatcher);
        etReachedFemale17.addTextChangedListener(textWatcher);

        etReachedMale24.addTextChangedListener(textWatcher);
        etReachedFemale24.addTextChangedListener(textWatcher);
        etReachedMale25.addTextChangedListener(textWatcher);
        etReachedFemale25.addTextChangedListener(textWatcher);


    }

    private void initElement() {
        etCounty = findViewById(R.id.etCounty);
        etSubCounty = findViewById(R.id.etSubCounty);
        etWard = findViewById(R.id.etWard);
        etLocation = findViewById(R.id.etLocation);
        etActivityName = findViewById(R.id.etActivityName);
        dp_activity_date = findViewById(R.id.dp_activity_date);
        etReachedMale14 = findViewById(R.id.etReachedMale14);
        etReachedFemale14 = findViewById(R.id.etReachedFemale14);
        etReachedMale17 = findViewById(R.id.etReachedMale17);
        etReachedFemale17 = findViewById(R.id.etReachedFemale17);
        etReachedMale24 = findViewById(R.id.etReachedMale24);
        etReachedFemale24 = findViewById(R.id.etReachedFemale24);
        etReachedMale25 = findViewById(R.id.etReachedMale25);
        etReachedFemale25 = findViewById(R.id.etReachedFemale25);
        etTotalMaleFemale = findViewById(R.id.etTotalMaleFemale);
        etActivityCost = findViewById(R.id.etActivityCost);
        etIEC = findViewById(R.id.etIEC);
        etNoSanitaryPads = findViewById(R.id.etNoSanitaryPads);
        etDignityKits = findViewById(R.id.etDignityKits);
        etPantiesBoxers = findViewById(R.id.etPantiesBoxers);
        etSubmittedBy = findViewById(R.id.etSubmittedBy);
        etOrganization = findViewById(R.id.etOrganization);


    }


    @Override
    public int initView() {
        return R.layout.activity_data_registration;
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
                new DatePickerDialog(DataRegistrationActivity.this, date, myCalendar
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
    public void onBackPressed() {

        DataRegistrationActivity.super.onBackPressed();
        startActivity(new Intent(DataRegistrationActivity.this, MainActivity.class));
        finish();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit_info:
                if (isConnected(this)) {
                    validateAndSendData();
                } else {
                    showAlert(DataRegistrationActivity.this, "No Internet Connection");

                }

                break;
        }
    }

    private void validateAndSendData() {


        county = etCounty.getText().toString();
        sub_county = etSubCounty.getText().toString();
        ward = etWard.getText().toString();
        location = etLocation.getText().toString();
        activity_date = dp_activity_date.getText().toString();
        activity_name = etActivityName.getText().toString();
        total_male_10_14 = etReachedMale14.getText().toString();
        total_female_10_14 = etReachedFemale14.getText().toString();
        total_male_15_17 = etReachedMale17.getText().toString();
        total_female_15_17 = etReachedFemale17.getText().toString();
        total_male_18_24 = etReachedMale24.getText().toString();
        total_female_18_24 = etReachedFemale24.getText().toString();
        total_male_25 = etReachedMale25.getText().toString();
        total_female_25 = etReachedFemale25.getText().toString();
        total_male_female = etTotalMaleFemale.getText().toString();
        activity_cost = etActivityCost.getText().toString();
        no_iec = etIEC.getText().toString();
        no_sanitary_pads = etNoSanitaryPads.getText().toString();
        no_dignity_kits = etDignityKits.getText().toString();
        no_panties_boxers = etPantiesBoxers.getText().toString();
        reported_by = etSubmittedBy.getText().toString();
        organization = etOrganization.getText().toString();

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
        if (TextUtils.isEmpty(activity_name)) {
            showSnack(etWard, "Error Activity Name is required !!!");

            count++;
            return;
        }
        if (TextUtils.isEmpty(activity_date)) {
            showSnack(etWard, "Error Activity Date is required !!!");

            count++;
            return;
        }
        if (TextUtils.isEmpty(reported_by)) {
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
            map.put("strategic_objective", strategic_objective);
            map.put("expected_outcome", expected_outcome);
            map.put("strategies", strategies);
            map.put("activity_name", activity_name);
            map.put("county", county);
            map.put("sub_county", sub_county);
            map.put("ward", ward);
            map.put("agent_id", new SharedPreferenceUtils(this).getSharedPrefStringData(Const.AGENT_ID));
            map.put("location", location);
            map.put("activity_date", activity_date);
            map.put("total_male_10_14", total_male_10_14);
            map.put("total_female_10_14", total_female_10_14);
            map.put("total_male_15_17", total_male_15_17);
            map.put("total_female_15_17", total_female_15_17);
            map.put("total_male_18_24", total_male_18_24);
            map.put("total_female_18_24", total_female_18_24);


            map.put("total_male_25", total_male_25);
            map.put("total_female_25", total_female_25);
            map.put("total_male_female", total_male_female);
            map.put("activity_cost", activity_cost);
            map.put("no_iec", no_iec);
            map.put("no_sanitary_pads", no_sanitary_pads);
            map.put("no_dignity_kits", no_dignity_kits);
            map.put("no_panties_boxers", no_panties_boxers);
            map.put("reported_by", reported_by);
            map.put("organization", organization);


            dataRegistrationPresenter.registrationApiCall(map);

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
            showAlert(DataRegistrationActivity.this, "Server responded with error 500");
        } else if (statusCode == 419) {
            showAlert(DataRegistrationActivity.this, "Server responded with error 419");
        } else {
            showAlert(DataRegistrationActivity.this, "Unknown Server Error!!!");
        }
    }

    @Override
    public void registrationApiResponse(String response) {
        showToast("Data Successfully Recorded");
        startActivity(new Intent(DataRegistrationActivity.this, MainActivity.class));
        finish();
    }


}