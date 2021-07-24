package sols.develiz.ngodatacapture.helper;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class VolleyRequestHelper {

    private static final String TAG ="VolleyRequestHelper";


    private Context context;
    private RequestQueue requestQueue;
    private OnRequestCompletedListener mRequestCompletedListener;

    public VolleyRequestHelper(Context context) {
        this.context = context;
    }


    /**
     * Used to call web service and get response as JSON using post method.
     *
     * @param context  - context of the activity.
     * @param callback - The callback reference.
     */
    public VolleyRequestHelper(Context context, OnRequestCompletedListener callback) {
        mRequestCompletedListener = callback;
        this.context = context;
    }

    /**
     * Request String response from the Web API.
     *
     * @param requestName   the String refers the request name
     * @param webserviceUrl the String refers the web service URL.
     * @param requestParams the list of parameters in byte array.
     * @param webMethod     the integer indicates the web method.
     * @param getCache      the boolean indicates whether cache can enable/disable
     */
    public void requestString(final String requestName,
                              final String webserviceUrl,
                              final Map<String,String> requestParams, final int webMethod,
                              final boolean getCache) {
        StringRequest stringRequest = new StringRequest(webMethod,
                webserviceUrl, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                mRequestCompletedListener.onRequestCompleted(
                        requestName, true, response, null, 0);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                String errorResponse = "";
                int statusCode=0;
                if (getCache) {
                    final com.android.volley.Cache cache = getRequestQueue().getCache();
                    final Cache.Entry entry = cache.get(webserviceUrl);
                    if (entry != null) {
                        try {
                            errorResponse = new String(entry.data, "UTF-8");
                             statusCode=error.networkResponse.statusCode;
                            mRequestCompletedListener
                                    .onRequestCompleted(requestName,
                                            true, errorResponse, null, statusCode);
                            return;
                        } catch (UnsupportedEncodingException e) {
                            Log.e(TAG, e+"");
                        }
                    } else {
                        Log.e(TAG, "requestName"
                                + " Cache does not exist");
                    }
                }
                VolleyError responseError = null;
                try {
                    responseError = new VolleyError(
                            new String(error.networkResponse.data));
                    statusCode=error.networkResponse.statusCode;
                    try {
                        final JSONObject responseJson = new JSONObject(responseError.getMessage());
                        // Show Alert Information
                        statusCode=error.networkResponse.statusCode;
                        errorResponse = responseJson.getString("VolleyErrorResponce");
                    } catch (Exception e) {
                        errorResponse = "Unknown";
                    }
                } catch (Exception e) {
                    Log.e(TAG, e+"");
                }
                Log.e("CUSTOM_ERROR ", responseError+"");

                mRequestCompletedListener.onRequestCompleted(
                        requestName, false, null,
                        responseError,statusCode);
            }
        }) {

//            @Override
//            protected Response<String> parseNetworkResponse(NetworkResponse response) {
//                int mStatusCode = response.statusCode;
//                Log.e("CODE>>>>",mStatusCode+"");
//                return super.parseNetworkResponse(response);
//            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> map=requestParams;

                return map;

            }
        /*    @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer " + new BearerToken(context).getBearerToken());
                return params;
            }*/
        };
        // Adding String request to request queue
        addToRequestQueue(stringRequest, requestName);
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context);
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        req.setRetryPolicy(new DefaultRetryPolicy(90 * 1000, 0, 1.0f));
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (requestQueue != null) {
            requestQueue.cancelAll(tag);
        }
    }

    /**
     * To get the ImageLoader class instance to load the network image in Image
     * view.
     *
     * @return ImageLoader instance.
     */



    /**
     * A callback interface indicates the status about the completion of HTTP
     * request.
     */
    public interface OnRequestCompletedListener {
        /**
         * Called when the String request has been completed.
         *  @param requestName  the String refers the request name
         * @param status       the status of the request either success or failure
         * @param response     the String response returns from the Webservice. It may be
 *                     null if request failed.
         * @param errorMessage the String refers the error message when request failed to
         * @param statusCode
         */
        void onRequestCompleted(String requestName, boolean status,
                                String response, VolleyError errorMessage, int statusCode);

    }
}
