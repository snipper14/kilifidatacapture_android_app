package sols.develiz.ngodatacapture.aysrh.presenter;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;

import java.util.Map;

import sols.develiz.ngodatacapture.api.API;
import sols.develiz.ngodatacapture.aysrh.DataRegistrationView;
import sols.develiz.ngodatacapture.helper.VolleyRequestHelper;


public class DataRegistrationPresenter {
    private static final String AYSRH_DATA_REGISTRATION_SERVICE = "4332";
    private DataRegistrationView activityView;
    private Context context;
    private VolleyRequestHelper volleyRequestHelper;

    public DataRegistrationPresenter(DataRegistrationView activityView, Context context) {
        this.activityView = activityView;
        this.context = context;
        volleyRequestHelper = new VolleyRequestHelper(context, requestCompletedListener);
    }

    public void registrationApiCall(Map map) {
        activityView.showProgressBar("Sending.. please wait");

        volleyRequestHelper.requestString(AYSRH_DATA_REGISTRATION_SERVICE, API.BASE_URL + API.AYSRH_API_SERVICE, map, com.android.volley.Request.Method.POST, false);
    }
    private VolleyRequestHelper.OnRequestCompletedListener requestCompletedListener = new VolleyRequestHelper.OnRequestCompletedListener() {
        @Override
        public void onRequestCompleted(String requestName, boolean status, String response, VolleyError errorMessage, int statusCode) {

            //   Log.e("ERROR REPOS","Status=>"+status+" =>reponse"+response+"  error msq=>"+errorMessage.getMessage());

            if (status) {


                if(requestName==AYSRH_DATA_REGISTRATION_SERVICE){
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
