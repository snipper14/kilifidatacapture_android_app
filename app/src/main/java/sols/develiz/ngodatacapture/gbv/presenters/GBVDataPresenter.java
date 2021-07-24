package sols.develiz.ngodatacapture.gbv.presenters;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;

import java.util.Map;

import sols.develiz.ngodatacapture.api.API;
import sols.develiz.ngodatacapture.aysrh.DataRegistrationView;
import sols.develiz.ngodatacapture.gbv.views.GBVDataView;
import sols.develiz.ngodatacapture.helper.VolleyRequestHelper;

public class GBVDataPresenter {

    private static final String GBV_DATA_REGISTRATION_SERVICE = "43352";
    private GBVDataView activityView;
    private Context context;
    private VolleyRequestHelper volleyRequestHelper;

    public GBVDataPresenter(GBVDataView activityView, Context context) {
        this.activityView = activityView;
        this.context = context;
        volleyRequestHelper = new VolleyRequestHelper(context, requestCompletedListener);
    }

    public void registrationApiCall(Map map) {
        activityView.showProgressBar("Sending.. please wait");
  Log.e("API DATA",map.toString());
        volleyRequestHelper.requestString(GBV_DATA_REGISTRATION_SERVICE, API.BASE_URL + API.GBV_API_SERVICE, map, com.android.volley.Request.Method.POST, false);
    }
    private VolleyRequestHelper.OnRequestCompletedListener requestCompletedListener = new VolleyRequestHelper.OnRequestCompletedListener() {
        @Override
        public void onRequestCompleted(String requestName, boolean status, String response, VolleyError errorMessage, int statusCode) {

            //   Log.e("ERROR REPOS","Status=>"+status+" =>reponse"+response+"  error msq=>"+errorMessage.getMessage());

            if (status) {


                if(requestName==GBV_DATA_REGISTRATION_SERVICE){
                    activityView.hideProgress();

                    activityView.registrationApiResponse(response);
                }



            } else {
                activityView.hideProgress();
                activityView.displayVolleyError(errorMessage.getMessage(),statusCode);

                return;
            }

        }
    };
}
