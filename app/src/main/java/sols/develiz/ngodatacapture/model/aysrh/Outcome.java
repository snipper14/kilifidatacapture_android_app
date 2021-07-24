package sols.develiz.ngodatacapture.model.aysrh;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Outcome {

    public static JSONArray getOutcome11() {
        JSONArray expected_outcome_aray11 = new JSONArray();
        JSONObject expected_outcome_obj11 = new JSONObject();
        JSONObject expected_outcome_obj12 = new JSONObject();
        JSONObject expected_outcome_obj13 = new JSONObject();
        try {
            expected_outcome_obj11.put("outcome", "Increased knowledge on HIV and SRH among AYP and communities");
            expected_outcome_obj11.put("strategies", Strategy.getStrategy11());


            expected_outcome_obj12.put("outcome", "Strengthened health care system for delivery of AYSRH/HIV services");
            expected_outcome_obj12.put("strategies", Strategy.getStrategy12());

            expected_outcome_obj13.put("outcome", "Increased uptake of SRH/HIV services among AYP");
            expected_outcome_obj13.put("strategies", Strategy.getStrategy13());

            expected_outcome_aray11.put(expected_outcome_obj11);
            expected_outcome_aray11.put(expected_outcome_obj12);
            expected_outcome_aray11.put(expected_outcome_obj13);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return expected_outcome_aray11;
    }

    public static JSONArray getOutcome12() {
        JSONArray expected_outcome_aray21 = new JSONArray();
        JSONObject expected_outcome_obj21 = new JSONObject();
        JSONObject expected_outcome_obj22 = new JSONObject();
        JSONObject expected_outcome_obj23 = new JSONObject();
        JSONObject expected_outcome_obj24 = new JSONObject();
        try {
            expected_outcome_obj21.put("outcome", "Functional structures for involvement of AYP and communities in programs and policy advocacy");
            expected_outcome_obj21.put("strategies", Strategy.getStrategy21());


            expected_outcome_obj22.put("outcome", "Responsive communities to the needs of adolescents and young people");
            expected_outcome_obj22.put("strategies", Strategy.getStrategy22());

            expected_outcome_obj23.put("outcome", "Responsive policies and programs to the needs of Adolescents and Young people");
            expected_outcome_obj23.put("strategies", Strategy.getStrategy23());

            expected_outcome_aray21.put(expected_outcome_obj21);
            expected_outcome_aray21.put(expected_outcome_obj22);
            expected_outcome_aray21.put(expected_outcome_obj23);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return expected_outcome_aray21;
    }

    public static JSONArray getOutcome13() {

        JSONArray expected_outcome_aray31 = new JSONArray();
        JSONObject expected_outcome_obj31 = new JSONObject();
        JSONObject expected_outcome_obj32 = new JSONObject();
        JSONObject expected_outcome_obj33 = new JSONObject();
        try {
            expected_outcome_obj31.put("outcome", "Enhanced multi-sectoral participation in AYP programming ");
            expected_outcome_obj31.put("strategies", Strategy.getStrategy31());


            expected_outcome_obj32.put("outcome", "Increased accountability for AYP programs by different sectors");
            expected_outcome_obj32.put("strategies", Strategy.getStrategy32());

            expected_outcome_obj33.put("outcome", "Improved school retention, transition and completion rates among AYP");
            expected_outcome_obj33.put("strategies", Strategy.getStrategy33());


            expected_outcome_aray31.put(expected_outcome_obj31);
            expected_outcome_aray31.put(expected_outcome_obj32);
            expected_outcome_aray31.put(expected_outcome_obj33);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return expected_outcome_aray31;
    }

    public static JSONArray getOutcome14() {
        JSONArray expected_outcome_aray41 = new JSONArray();
        JSONObject expected_outcome_obj41 = new JSONObject();
        JSONObject expected_outcome_obj42 = new JSONObject();
        try {
            expected_outcome_obj41.put("outcome", "Increased funding allocation for AYP AYSRH/HIV programs in the county fiscal budget");
            expected_outcome_obj41.put("strategies", Strategy.getStrategy41());


            expected_outcome_obj42.put("outcome", "Diversified sources of funding for AYP programming");
            expected_outcome_obj42.put("strategies", Strategy.getStrategy42());

            expected_outcome_aray41.put(expected_outcome_obj41);
            expected_outcome_aray41.put(expected_outcome_obj42);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return expected_outcome_aray41;
    }
}
