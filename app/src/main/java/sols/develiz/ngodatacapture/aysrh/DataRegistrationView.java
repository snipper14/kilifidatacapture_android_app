package sols.develiz.ngodatacapture.aysrh;

public interface DataRegistrationView {

    void showProgressBar(String s);

    void hideProgress();



    void displayVolleyError(String s, int statusCode);

    void registrationApiResponse(String response);

 //   void organizationSpinnerApiResponse(String response);

}
