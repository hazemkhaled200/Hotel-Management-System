package hellofx;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginValidatorProxy implements ILoginValidator {
    private final ILoginValidator realValidator;
    private String title;

    public LoginValidatorProxy(ILoginValidator realValidator) {
        this.realValidator = realValidator;
    }

    DatabaseConnection connectNow = DatabaseConnection.getObject();
    Connection connectDB = connectNow.getConnection();

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int validateLogin(String username, String password) {

        String getTitle = "SELECT job_title FROM workers WHERE w_username = '" + username
                + "' AND w_password = '" + password + "'";
        try (PreparedStatement pst = connectDB.prepareStatement(getTitle)) {

            ResultSet rs = pst.executeQuery();
            if (rs.next() == true) {

                if ((rs.getString("job_title")).equals("Manager")) {

                    setTitle("Manager");

                } else if ((rs.getString("job_title")).equals("Reciptioninst")) {
                    setTitle("Reciptioninst");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            e.getCause();
        }

        return realValidator.validateLogin(username, password);
    }
}