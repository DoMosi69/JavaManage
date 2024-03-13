package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class FxmlController implements Initializable {

    @FXML
    private Button btnLogin;

    @FXML
    private ImageView close;

    @FXML
    private TextField password;

    @FXML
    private TextField username;

    @FXML
    private Label dashboard;

    private final Database database;

    public FxmlController() {
        this.database = new Database();
    }

    @FXML
    public void onBtnLoginAction() throws IOException {
        String sql = "SELECT * FROM admin WHERE username = ? AND password = ?";

        try (Connection connection = database.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, username.getText());
            preparedStatement.setString(2, password.getText());

            try (ResultSet result = preparedStatement.executeQuery()) {
                Alert alert;

                if (result.next()) {
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Login Successful");
                    alert.setHeaderText(null);
                    alert.setContentText("Welcome, " + username.getText() + "!");
                    alert.showAndWait();


                    Stage currentStage = (Stage) btnLogin.getScene().getWindow();
                    currentStage.close();

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
                    Parent root = loader.load();

                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();

                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Login Failed");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid username or password. Please try again.");
                    alert.showAndWait();
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.err.println("Error during login:");
            System.err.println("SQL State: " + ex.getSQLState());
            System.err.println("Error Code: " + ex.getErrorCode());
            System.err.println("Message: " + ex.getMessage());
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        close();
    }

    private void close() {
        close.setOnMouseClicked(event -> {
            Stage stage = (Stage) close.getScene().getWindow();
            stage.close();
        });
    }
}
