package sols.develiz.ngodatacapture.gbv;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.thekhaeng.pushdownanim.PushDownAnim;

import sols.develiz.ngodatacapture.HomeActivity;
import sols.develiz.ngodatacapture.R;
import sols.develiz.ngodatacapture.aysrh.MainActivity;
import sols.develiz.ngodatacapture.base.BaseActivity;
import sols.develiz.ngodatacapture.consts.Const;
import sols.develiz.ngodatacapture.helper.SharedPreferenceUtils;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class ViolationTypeActivity extends BaseActivity implements View.OnClickListener {

private String gbv_healthcare;

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

                startActivity(new Intent(ViolationTypeActivity.this, GbvReportingActivity.class));

                finish();
            }
        });

        gbv_healthcare= new SharedPreferenceUtils(ViolationTypeActivity.this).getSharedPrefStringData(Const.GBV_CATEGORY);
        TextView gbv_category=findViewById(R.id.gbv_category);
        gbv_category.setText(gbv_healthcare);
        PushDownAnim.setPushDownAnimTo(findViewById(R.id.ln_gbv_defilement)).setScale(MODE_SCALE, 0.89f).setOnClickListener(this);
        PushDownAnim.setPushDownAnimTo(findViewById(R.id.ln_gbv_sodomy)).setScale(MODE_SCALE, 0.89f).setOnClickListener(this);
        PushDownAnim.setPushDownAnimTo(findViewById(R.id.ln_sgbv)).setScale(MODE_SCALE, 0.89f).setOnClickListener(this);


    }

    @Override
    public int initView() {
        return R.layout.activity_violation_type;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ln_gbv_defilement:


                new SharedPreferenceUtils(this).
                        saveInputDataSharedPrefs(Const.GBV_VIOLATION,getResources().getString(R.string.gbv_defilement));
                new SharedPreferenceUtils(this).
                        saveInputDataSharedPrefs(Const.GBV_VIOLATION_AGE_14,getResources().getString(R.string.gbv_violation_age14));
                new SharedPreferenceUtils(this).
                        saveInputDataSharedPrefs(Const.GBV_VIOLATION_AGE_17,getResources().getString(R.string.gbv_violation_age17));
                startActivity(new Intent(this, GbvDataActivity.class));
                finish();
                break;

            case R.id.ln_gbv_sodomy:


                new SharedPreferenceUtils(this).
                        saveInputDataSharedPrefs(Const.GBV_VIOLATION,getResources().getString(R.string.gbv_sodomy));
                new SharedPreferenceUtils(this).
                        saveInputDataSharedPrefs(Const.GBV_VIOLATION_AGE_14,getResources().getString(R.string.gbv_violation_sodomy_age14));
                new SharedPreferenceUtils(this).
                        saveInputDataSharedPrefs(Const.GBV_VIOLATION_AGE_17,getResources().getString(R.string.gbv_violation_sodomy_age17));
                startActivity(new Intent(this, GbvDataActivity.class));
                finish();
                break;

            case R.id.ln_sgbv:


                new SharedPreferenceUtils(this).
                        saveInputDataSharedPrefs(Const.GBV_VIOLATION,getResources().getString(R.string.gbv_sgbv));
                new SharedPreferenceUtils(this).
                        saveInputDataSharedPrefs(Const.GBV_VIOLATION_AGE_14,getResources().getString(R.string.gbv_violation_sgbv_age14));
                new SharedPreferenceUtils(this).
                        saveInputDataSharedPrefs(Const.GBV_VIOLATION_AGE_17,getResources().getString(R.string.gbv_violation_sgbv_age17));
                startActivity(new Intent(this, GbvDataActivity.class));
                finish();
                break;

        }
    }

    @Override
    public void onBackPressed() {

        ViolationTypeActivity.super.onBackPressed();
        Intent intent=new Intent(ViolationTypeActivity.this, GbvReportingActivity.class);

        startActivity(intent);

        finish();

    }
}