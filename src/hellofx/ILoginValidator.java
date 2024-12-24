package hellofx;

public interface ILoginValidator {
    String getTitle();

    void setTitle(String title);

    int validateLogin(String username, String password);
}
