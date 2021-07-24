package sols.develiz.ngodatacapture.view;

public interface ChangePassword {
    void showProgressBar(String s);

    void hideProgress();



    void displayVolleyError(String s, int statusCode);

    void passwordApiResponse(String response);
}
