package sols.develiz.ngodatacapture.gbv;

import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.thekhaeng.pushdownanim.PushDownAnim;

import sols.develiz.ngodatacapture.HomeActivity;
import sols.develiz.ngodatacapture.R;
import sols.develiz.ngodatacapture.base.BaseActivity;
import sols.develiz.ngodatacapture.consts.Const;
import sols.develiz.ngodatacapture.helper.SharedPreferenceUtils;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class GbvReportingActivity extends BaseActivity implements View.OnClickListener{


private SharedPreferenceUtils sharedPreferenceUtils;
    @Override
    public void initActivity(Bundle b) {
        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("GBV Reporting");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(GbvReportingActivity.this, HomeActivity.class));
                finish();
            }
        });
        sharedPreferenceUtils=new SharedPreferenceUtils(this);
        sharedPreferenceUtils.clearPrefs(Const.GBV_VIOLATION);
        PushDownAnim.setPushDownAnimTo(findViewById(R.id.ln_gbv_healthcare)).setScale(MODE_SCALE, 0.89f).setOnClickListener(this);
        PushDownAnim.setPushDownAnimTo(findViewById(R.id.ln_gbv_police_stations)).setScale(MODE_SCALE, 0.89f).setOnClickListener(this);
        PushDownAnim.setPushDownAnimTo(findViewById(R.id.ln_gbv_judiciary)).setScale(MODE_SCALE, 0.89f).setOnClickListener(this);

    }

    @Override
    public int initView() {
        return R.layout.activity_gbv_reporting;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ln_gbv_healthcare:
               Intent intent=new Intent(GbvReportingActivity.this, ViolationTypeActivity.class);
                String gbv_healthcare = getResources().getString(R.string.gbv_healthcare);
              sharedPreferenceUtils.saveInputDataSharedPrefs(Const.GBV_CATEGORY,gbv_healthcare);


                startActivity(intent);
                finish();
                break;

            case R.id.ln_gbv_police_stations:
              sharedPreferenceUtils.
                        saveInputDataSharedPrefs(Const.GBV_CATEGORY,getResources().getString(R.string.gbv_police_station));
                sharedPreferenceUtils.
                        saveInputDataSharedPrefs(Const.GBV_VIOLATION_AGE_14,getResources().getString(R.string.gbv_violation_age14));
                sharedPreferenceUtils.
                        saveInputDataSharedPrefs(Const.GBV_VIOLATION_AGE_17,getResources().getString(R.string.gbv_violation_age17));

                startActivity(new Intent(GbvReportingActivity.this, GBVDataCaptureInc.class));
                finish();
                break;

            case R.id.ln_gbv_judiciary:
                new SharedPreferenceUtils(GbvReportingActivity.this).
                        saveInputDataSharedPrefs(Const.GBV_CATEGORY,getResources().getString(R.string.gbv_judiciary));
                startActivity(new Intent(GbvReportingActivity.this, GbvDataCaptureJudiciary.class));
                finish();
                break;

        }
    }

    @Override
    public void onBackPressed() {

        GbvReportingActivity.super.onBackPressed();
        startActivity(new Intent(GbvReportingActivity.this, HomeActivity.class));
        finish();

    }
}