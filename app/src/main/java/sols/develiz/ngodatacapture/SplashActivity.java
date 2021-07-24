package sols.develiz.ngodatacapture;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.Timer;
import java.util.TimerTask;

import sols.develiz.ngodatacapture.consts.Const;
import sols.develiz.ngodatacapture.helper.SharedPreferenceUtils;

public class SplashActivity extends AppCompatActivity {
    private static final int WELCOME_TIMEOUT =5000 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ImageView img=findViewById(R.id.imageView2);
        final String id=new SharedPreferenceUtils(this).getSharedPrefStringData(Const.AGENT_ID);
        Glide.with(this).load(getImage("splash_img")).into(img);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if(id.equals("0")) {
                    startActivity(new Intent(getBaseContext(), LoginActivity.class));
                }else{
                    startActivity(new Intent(getBaseContext(), HomeActivity.class));
                }
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        }, WELCOME_TIMEOUT);
    }

    private int getImage(String imageName) {

        int drawableResourceId = getApplicationContext().getResources().getIdentifier(imageName, "drawable", getApplicationContext().getPackageName());

        return drawableResourceId;
    }

}