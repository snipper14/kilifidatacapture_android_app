package sols.develiz.ngodatacapture.model.aysrh;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Objective {
    public static JSONArray getData() {
        JSONArray jsonArray = new JSONArray();
        try {
            JSONObject obj = new JSONObject();


            obj.put("expected_outcome", Outcome.getOutcome11());
            obj.put("objective", "Strategic Objective 1: To improve equitable access to quality AYP friendly SRHR/HIV information and services");
            jsonArray.put(obj);



            JSONObject obj2 = new JSONObject();


            obj2.put("objective", "Strategic Objective 2: To strengthen meaningful engagement of communities in AYP programs and policy processes");
            obj2.put("expected_outcome", Outcome.getOutcome12());
            jsonArray.put(obj2);



            JSONObject obj3 = new JSONObject();

            obj3.put("objective", "Strategic Objective 3: To strengthen multi sectoral coordination on SRH/HIV health outcomes among AYP");
            obj3.put("expected_outcome", Outcome.getOutcome13());

            jsonArray.put(obj3);



            JSONObject obj4 = new JSONObject();


            obj4.put("objective", "Strategic Objective 4: To promote sustainable financing mechanism for AYSHR/HIV programs and services");
            obj4.put("expected_outcome", Outcome.getOutcome14());
            jsonArray.put(obj4);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

}
