package sols.develiz.ngodatacapture.base;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.util.GregorianCalendar;

public abstract class BaseActivity  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        GregorianCalendar expDate = new GregorianCalendar(2020, 9, 2); // midnight
        GregorianCalendar now = new GregorianCalendar();

        boolean isExpired = now.after(expDate);
        // if(isExpired){
//startActivity(new Intent(getApplicationContext(), ExpiryActivity.class));
//finish();
        ///      }else {
        setContentView(initView());

        initActivity(savedInstanceState);

        //   }

    }

    public abstract void initActivity(Bundle b);
    public abstract int initView();

    protected  void showSnack(View view, String message){
        Snackbar snackbar = Snackbar
                .make(view, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    protected void showToast(String message){

        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }

    protected int getImage(String imageName) {

        int drawableResourceId = getApplicationContext().getResources().getIdentifier(imageName, "drawable", getApplicationContext().getPackageName());

        return drawableResourceId;
    }

}
