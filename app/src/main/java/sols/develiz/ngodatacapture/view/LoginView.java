package sols.develiz.ngodatacapture.view;

public interface LoginView {

    void showProgressBar(String s);

    void hideProgress();



    void displayVolleyError(String s, int statusCode);

    void loginApiResponse(String response);
}
