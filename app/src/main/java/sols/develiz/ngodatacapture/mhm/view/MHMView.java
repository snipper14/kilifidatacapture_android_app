package sols.develiz.ngodatacapture.mhm.view;

public interface MHMView {
    void showProgressBar(String s);

    void hideProgress();



    void displayVolleyError(String s, int statusCode);

    void registrationApiResponse(String response);
}
