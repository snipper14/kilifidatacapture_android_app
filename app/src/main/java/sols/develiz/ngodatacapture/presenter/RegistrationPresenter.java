package sols.develiz.ngodatacapture.presenter;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;

import java.util.Map;

import sols.develiz.ngodatacapture.api.API;
import sols.develiz.ngodatacapture.helper.VolleyRequestHelper;
import sols.develiz.ngodatacapture.view.RegistrationView;

public class RegistrationPresenter {
    private static final String REGISTER_SERVICE = "33";
    private RegistrationView activityView;
    private Context context;
    private VolleyRequestHelper volleyRequestHelper;

    public RegistrationPresenter(RegistrationView activityView, Context context) {
        this.activityView = activityView;
        this.context = context;
        volleyRequestHelper = new VolleyRequestHelper(context, requestCompletedListener);
    }

    public void agentRegApiCall(Map map) {
        activityView.showProgressBar("Sending.. please wait");

        volleyRequestHelper.requestString(REGISTER_SERVICE, API.BASE_URL + API.REGISTER_AGENTS, map, com.android.volley.Request.Method.POST, false);
    }
    private VolleyRequestHelper.OnRequestCompletedListener requestCompletedListener = new VolleyRequestHelper.OnRequestCompletedListener() {
        @Override
        public void onRequestCompleted(String requestName, boolean status, String response, VolleyError errorMessage, int statusCode) {

         //   Log.e("ERROR REPOS","Status=>"+status+" =>reponse"+response+"  error msq=>"+errorMessage.getMessage());

            if (status) {


                if(requestName==REGISTER_SERVICE){
                    activityView.hideProgress();

                    activityView.registrationApiResponse(response);
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
