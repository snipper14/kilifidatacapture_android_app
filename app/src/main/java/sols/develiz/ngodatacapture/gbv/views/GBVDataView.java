package sols.develiz.ngodatacapture.gbv.views;

public interface GBVDataView {
    void showProgressBar(String s);

    void hideProgress();



    void displayVolleyError(String s, int statusCode);

    void registrationApiResponse(String response);
}
