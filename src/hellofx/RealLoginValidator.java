package hellofx;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RealLoginValidator implements ILoginValidator {
    private final Connection connectDB;

    public RealLoginValidator(Connection connectDB) {
        this.connectDB = connectDB;
    }

    @Override
    public void setTitle(String title) {

    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public int validateLogin(String username, String password) {

        String query = "SELECT count(1) FROM workers WHERE w_username = ? AND w_password = ?";
        try (PreparedStatement statement = connectDB.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet queryResult = statement.executeQuery();
            if (queryResult.next() && queryResult.getInt(1) == 1) {
                return 1; // Valid login
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1; // Invalid login
    }
}