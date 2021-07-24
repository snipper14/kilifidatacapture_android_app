package sols.develiz.ngodatacapture.model.aysrh;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Strategy {

    public static JSONArray getStrategy11() {

        JSONArray strategies_arr11=new JSONArray();
        JSONObject strategies_obj11=new JSONObject();
        JSONObject strategies_obj12=new JSONObject();
        JSONObject strategies_obj13=new JSONObject();
        try {
            strategies_obj11.put("strategy","Community-based information delivery platforms");

            strategies_obj12.put("strategy","Schoolbased health programs");
            strategies_obj13.put("strategy","Digitalbased platforms for delivery of AYSRHR and HIV information for AYP");
            strategies_arr11.put(strategies_obj11);
            strategies_arr11.put(strategies_obj12);
            strategies_arr11.put(strategies_obj13);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return strategies_arr11;

    }

    public static JSONArray getStrategy12() {

        JSONArray strategies_arr11=new JSONArray();
        JSONObject strategies_obj=new JSONObject();
      try{
          strategies_obj.put("strategy","Health Systems strengthening");


            strategies_arr11.put(strategies_obj);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return strategies_arr11;

    }

    public static JSONArray getStrategy13() {

        JSONArray strategies_arr11=new JSONArray();
        JSONObject strategies_obj=new JSONObject();
        try{
            strategies_obj.put("strategy","Integration");


            strategies_arr11.put(strategies_obj);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return strategies_arr11;

    }


    public static JSONArray getStrategy21(){
        JSONArray strategies_arr=new JSONArray();
        JSONObject strategies_obj=new JSONObject();
        try{
            strategies_obj.put("strategy","AYP and community members involvement");
            strategies_arr.put(strategies_obj);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return strategies_arr;
    }

    public static JSONArray getStrategy22(){
        JSONArray strategies_arr=new JSONArray();
        JSONObject strategies_obj=new JSONObject();
        try{
            strategies_obj.put("strategy","Male Engagement");
            strategies_arr.put(strategies_obj);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return strategies_arr;
    }

    public static JSONArray getStrategy23(){
        JSONArray strategies_arr=new JSONArray();
        JSONObject strategies_obj=new JSONObject();
        try{
            strategies_obj.put("strategy","Socio economic empowerment for AYP");
            strategies_arr.put(strategies_obj);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return strategies_arr;
    }

    public static JSONArray getStrategy31(){
        JSONArray strategies_arr=new JSONArray();
        JSONObject strategies_obj=new JSONObject();
        JSONObject strategies_obj1=new JSONObject();
        JSONObject strategies_obj2=new JSONObject();
        try{
            strategies_obj.put("strategy","Conduct training needs assessment on AYSRH/HIV for all departments");
            strategies_obj1.put("strategy","Convene quarterly meetings with multisectorial AYSRHR TWGs at county level to coordinate teenage pregnancy prevention efforts");
            strategies_obj2.put("strategy","Advocacy for AYSRH/HIV");
            strategies_arr.put(strategies_obj);
            strategies_arr.put(strategies_obj1);
            strategies_arr.put(strategies_obj2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return strategies_arr;
    }

    public static JSONArray getStrategy32(){
        JSONArray strategies_arr=new JSONArray();
        JSONObject strategies_obj=new JSONObject();
        try{
            strategies_obj.put("strategy","Establish a PPP framework for the county");
            strategies_arr.put(strategies_obj);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return strategies_arr;
    }

    public static JSONArray getStrategy33(){
        JSONArray strategies_arr=new JSONArray();
        JSONObject strategies_obj=new JSONObject();
        try{
            strategies_obj.put("strategy","Monitoring implementation of the return to school policy");
            strategies_arr.put(strategies_obj);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return strategies_arr;
    }

    public static JSONArray getStrategy41(){
        JSONArray strategies_arr=new JSONArray();
        JSONObject strategies_obj=new JSONObject();
        JSONObject strategies_obj1=new JSONObject();
        JSONObject strategies_obj2=new JSONObject();
        try{
            strategies_obj.put("strategy","AYSRHR/ HIV budget allocation (Budget Advocacy meetings targeting Policy maker, development partners and implementati on partners).");
            strategies_obj1.put("strategy","Engagement with AYP in development of ( ADP,CIDP)");
            strategies_obj2.put("strategy","Train and engage AYP on County Budget process");
            strategies_arr.put(strategies_obj);
            strategies_arr.put(strategies_obj1);
            strategies_arr.put(strategies_obj2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return strategies_arr;
    }

    public static JSONArray getStrategy42(){
        JSONArray strategies_arr=new JSONArray();
        JSONObject strategies_obj=new JSONObject();
        try{
            strategies_obj.put("strategy","Resource mobilization for AYSRHR/HIV programs");
            strategies_arr.put(strategies_obj);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return strategies_arr;
    }
    }
