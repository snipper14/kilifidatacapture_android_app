package sols.develiz.ngodatacapture.presenter;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;

import java.util.Map;

import sols.develiz.ngodatacapture.api.API;
import sols.develiz.ngodatacapture.helper.VolleyRequestHelper;
import sols.develiz.ngodatacapture.view.LoginView;
import sols.develiz.ngodatacapture.view.RegistrationView;

public class LoginPresenter {

    private static final String LOGIN_SERVICE = "332";
    private LoginView activityView;
    private Context context;
    private VolleyRequestHelper volleyRequestHelper;

    public LoginPresenter(LoginView activityView, Context context) {
        this.activityView = activityView;
        this.context = context;
        volleyRequestHelper = new VolleyRequestHelper(context, requestCompletedListener);
    }

    public void loginApiCall(Map map) {
        activityView.showProgressBar("Sending.. please wait");

        volleyRequestHelper.requestString(LOGIN_SERVICE, API.BASE_URL + API.LOGIN_AGENTS, map, com.android.volley.Request.Method.POST, false);
    }
    private VolleyRequestHelper.OnRequestCompletedListener requestCompletedListener = new VolleyRequestHelper.OnRequestCompletedListener() {
        @Override
        public void onRequestCompleted(String requestName, boolean status, String response, VolleyError errorMessage, int statusCode) {

            //   Log.e("ERROR REPOS","Status=>"+status+" =>reponse"+response+"  error msq=>"+errorMessage.getMessage());

            if (status) {


                if(requestName==LOGIN_SERVICE){
                    activityView.hideProgress();

                    activityView.loginApiResponse(response);
                }



            } else {
                activityView.hideProgress();
                activityView.displayVolleyError(errorMessage.getMessage(),statusCode);
                Log.e("STATUS CODE",statusCode+"");
                return;
            }

        }
    };
}
