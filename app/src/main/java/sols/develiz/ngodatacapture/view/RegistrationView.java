package sols.develiz.ngodatacapture.view;

public interface RegistrationView {

    void showProgressBar(String s);

    void hideProgress();



    void displayVolleyError(String s, int statusCode);

    void registrationApiResponse(String response);

}
