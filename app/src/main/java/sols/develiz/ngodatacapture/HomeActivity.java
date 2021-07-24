package sols.develiz.ngodatacapture;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.thekhaeng.pushdownanim.PushDownAnim;

import sols.develiz.ngodatacapture.aysrh.MainActivity;
import sols.develiz.ngodatacapture.base.BaseActivity;
import sols.develiz.ngodatacapture.consts.Const;
import sols.develiz.ngodatacapture.gbv.GbvReportingActivity;
import sols.develiz.ngodatacapture.helper.SharedPreferenceUtils;
import sols.develiz.ngodatacapture.mhm.MHMActivity;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class HomeActivity extends BaseActivity implements View.OnClickListener {



    @Override
    public void initActivity(Bundle b) {
        Toolbar toolbar = findViewById(R.id.tool_bar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(new SharedPreferenceUtils(this).getSharedPrefStringData(Const.AGENT_NAME));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertExitApp();
            }
        });
        PushDownAnim.setPushDownAnimTo(findViewById(R.id.imgAYSRH)).setScale(MODE_SCALE, 0.89f).setOnClickListener(this);
        PushDownAnim.setPushDownAnimTo(findViewById(R.id.imgGBV)).setScale(MODE_SCALE, 0.89f).setOnClickListener(this);
        PushDownAnim.setPushDownAnimTo(findViewById(R.id.imgMHM)).setScale(MODE_SCALE, 0.89f).setOnClickListener(this);

    }

    @Override
    public int initView() {
        return R.layout.activity_home;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgAYSRH:
                startActivity(new Intent(HomeActivity.this, MainActivity.class));
                finish();
                break;
            case R.id.imgGBV:
                startActivity(new Intent(HomeActivity.this, GbvReportingActivity.class));
                finish();
                break;
            case R.id.imgMHM:
                startActivity(new Intent(HomeActivity.this, MHMActivity.class));
                finish();
                break;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
          int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_changepassword) {
            startActivity(new Intent(HomeActivity.this,ChangePasswordActivity.class));
            finish();
            return true;
        }else if(id==R.id.action_logout) {
             new SharedPreferenceUtils(HomeActivity.this).clear();
             startActivity(new Intent(HomeActivity.this,LoginActivity.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onBackPressed() {
        alertExitApp();
    }

    private void alertExitApp() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        HomeActivity.super.onBackPressed();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            finishAffinity();
                        }
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();

    }
}