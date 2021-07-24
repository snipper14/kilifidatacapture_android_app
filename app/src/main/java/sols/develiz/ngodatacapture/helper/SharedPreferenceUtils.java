package sols.develiz.ngodatacapture.helper;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import sols.develiz.ngodatacapture.consts.Const;

import static android.content.Context.MODE_PRIVATE;

public class SharedPreferenceUtils {
    private static SharedPreferenceUtils mSharedPreferenceUtils;
    private static SharedPreferences mSharedPref;
    protected Context mContext;

//    private SharedPreferences.Editor mSharedPreferencesEditor;

    public  SharedPreferenceUtils(Context context) {
        mContext = context;
        mSharedPref=context.getSharedPreferences(Const.PREF_NAME,MODE_PRIVATE);

    }

    public  void saveInputDataSharedPrefs(String key, String objName) {

        mSharedPref
                .edit()
                .putString(key, objName)
                .apply();
    }


    public String getSharedPrefStringData(String key) {

        String data = mSharedPref
                .getString(key,
                        "0");

        return data;
    }

    public  void saveIntergerSharedPrefs(String key, int objName) {

        mSharedPref
                .edit()
                .putInt(key, objName)
                .apply();
    }


    public  int getIntSharedPrefStringData(String key) {

        int data = mSharedPref
                .getInt(key,
                        0);

        return data;
    }

    public JSONObject getSharedPrefJSONObjectData(String key) {

        String json_array = mSharedPref.getString(key, null);
        JSONObject jsoArray = null;
        try {

            jsoArray = new JSONObject(json_array);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsoArray;
    }



    public static Boolean getBooleanValue(String keyFlag) {
        return mSharedPref.getBoolean(keyFlag,false);
    }

    public static void saveBoolean(String keytag,boolean value){
        mSharedPref.edit().putBoolean(keytag, value).apply();

    }
    /**
     * Removes key from preference
     *
     * @param key key of preference that is to be deleted
     */


    public static void clearPrefs(String key) {
        if (mSharedPref != null) {
            mSharedPref.edit().remove(key);
            mSharedPref.edit().commit();
        }
    }

    /**
     * Clears all the preferences stored
     */
    public void clear() {
        mSharedPref.edit().clear().commit();
    }
}
