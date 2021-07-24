package sols.develiz.ngodatacapture.presenter;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;

import java.util.Map;

import sols.develiz.ngodatacapture.api.API;
import sols.develiz.ngodatacapture.helper.VolleyRequestHelper;
import sols.develiz.ngodatacapture.view.ChangePassword;
import sols.develiz.ngodatacapture.view.RegistrationView;

public class ChangePasswordPresenter {

    private static final String CHANGE_PASS_SERVICE = "3443";
    private ChangePassword activityView;
    private Context context;
    private VolleyRequestHelper volleyRequestHelper;

    public ChangePasswordPresenter(ChangePassword activityView, Context context) {
        this.activityView = activityView;
        this.context = context;
        volleyRequestHelper = new VolleyRequestHelper(context, requestCompletedListener);
    }

    public void changePassApiCall(Map map) {
        activityView.showProgressBar("Sending.. please wait");

        volleyRequestHelper.requestString(CHANGE_PASS_SERVICE, API.BASE_URL + API.CHANGE_PASSWORD, map, com.android.volley.Request.Method.POST, false);
    }
    private VolleyRequestHelper.OnRequestCompletedListener requestCompletedListener = new VolleyRequestHelper.OnRequestCompletedListener() {
        @Override
        public void onRequestCompleted(String requestName, boolean status, String response, VolleyError errorMessage, int statusCode) {


            if (status) {


                if(requestName==CHANGE_PASS_SERVICE){
                    activityView.hideProgress();

                    activityView.passwordApiResponse(response);
                }



            } else {
                activityView.hideProgress();
                activityView.displayVolleyError(errorMessage.getMessage(),statusCode);

                return;
            }

        }
    };
}
