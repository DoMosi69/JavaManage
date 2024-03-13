package sample;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class dashboardController implements Initializable {

        @FXML
        private TableColumn<Customerdb, Integer> add_Col_Id;

        @FXML
        private TableColumn<Customerdb, String> add_Col_FN;

        @FXML
        private TableColumn<Customerdb, String> add_Col_Sex;

        @FXML
        private TableColumn<Customerdb, String> add_Col_Tel;

        @FXML
        private TableColumn<Customerdb, String> add_Col_Gmail;

        @FXML
        private TableColumn<Customerdb, String> add_Col_RN;

        @FXML
        private TableColumn<Customerdb, LocalDate> add_Col_BD;

        @FXML
        private TableColumn<Customerdb, LocalDate> add_Col_InD;

        @FXML
        private TableColumn<Customerdb, LocalDate> add_Col_OD;

        @FXML
        private TableColumn<Customerdb, String> add_Col_RT;

        @FXML
        private TableView<Customerdb> tableView;

        @FXML
        private ChoiceBox<String> cbRT;

        @FXML
        private Button btnAdd;

        @FXML
        private Button btnCancel;

        @FXML
        private Button btnEdit;

        @FXML
        private Button btnRemove;

        @FXML
        private Button btnSave;

        @FXML
        private DatePicker dpBD;

        @FXML
        private DatePicker dpInD;

        @FXML
        private DatePicker dpOD;

        @FXML
        private ImageView close;

        @FXML
        private ImageView logout;

        @FXML
        private AnchorPane dashboard;

        @FXML
        private TextField txfGmail;

        @FXML
        private TextField txfName;

        @FXML
        private TextField txfRN;

        @FXML
        private TextField txfSearch;

        @FXML
        private TextField txfTel;

        @FXML
        private RadioButton radioFemale;

        @FXML
        private RadioButton radioMale;

        private final Database databaseConnection;

        public dashboardController() {
                // Default constructor
                this.databaseConnection = new Database(); // Initialize the database connection
        }

        private ObservableList<Customerdb> populateTableView() {
                ObservableList<Customerdb> listData = FXCollections.observableArrayList();
                String sql = "SELECT * FROM customer";

                try (Connection conn = databaseConnection.connect();
                     PreparedStatement stmt = conn.prepareStatement(sql);
                     ResultSet rs = stmt.executeQuery()) {
                        while (rs.next()) {
                                Customerdb customer = new Customerdb(
                                        rs.getInt("Id"),
                                        rs.getString("Name"),
                                        rs.getString("Sex"),
                                        rs.getString("Tel"),
                                        rs.getString("Gmail"),
                                        rs.getString("Room-type"),
                                        rs.getDate("Book-date").toLocalDate(),
                                        rs.getDate("In-date").toLocalDate(),
                                        rs.getDate("Out-date").toLocalDate(),
                                        rs.getString("Room")
                                );
                                listData.add(customer);
                        }
                        System.out.println("Data retrieved successfully."); // Add debug print statement
                } catch (SQLException e) {
                        e.printStackTrace();
                        System.err.println("Error retrieving data from the database."); // Add debug print statement
                }
                return listData;
        }

        @FXML
        private void customerShowlistData() {
                ObservableList<Customerdb> addCustomerList = populateTableView();

                add_Col_Id.setCellValueFactory(new PropertyValueFactory<>("id"));
                add_Col_FN.setCellValueFactory(new PropertyValueFactory<>("name"));
                add_Col_Sex.setCellValueFactory(new PropertyValueFactory<>("sex"));
                add_Col_Tel.setCellValueFactory(new PropertyValueFactory<>("tel"));
                add_Col_Gmail.setCellValueFactory(new PropertyValueFactory<>("gmail"));
                add_Col_RN.setCellValueFactory(new PropertyValueFactory<>("Room"));
                add_Col_BD.setCellValueFactory(new PropertyValueFactory<>("bookDate"));
                add_Col_InD.setCellValueFactory(new PropertyValueFactory<>("inDate"));
                add_Col_OD.setCellValueFactory(new PropertyValueFactory<>("outDate"));
                add_Col_RT.setCellValueFactory(new PropertyValueFactory<>("roomType"));

                tableView.setItems(addCustomerList);
        }


        public void addCustomer() {
                // Get the values from the text fields and date pickers
                String name = txfName.getText();
                String sex = getSelectedSexText();
                String tel = txfTel.getText();
                String gmail = txfGmail.getText();
                String roomType = cbRT.getValue();
                LocalDate bookDate = dpBD.getValue();
                LocalDate inDate = dpInD.getValue();
                LocalDate outDate = dpOD.getValue();
                String roomNumber = txfRN.getText();

                // Validate input fields
                if (name.isEmpty() || sex.isEmpty() || tel.isEmpty() || gmail.isEmpty() || roomType.isEmpty() || bookDate == null || inDate == null || outDate == null || roomNumber.isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText(null);
                        alert.setContentText("Please fill in all the fields.");
                        alert.showAndWait();
                        return;
                }

                String sql = "INSERT INTO customer (Name, Sex, Tel, Gmail, `Room-type`, `Book-date`, `In-date`, `Out-date`, Room) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

                try (Connection conn = databaseConnection.connect();
                     PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                        stmt.setString(1, name);
                        stmt.setString(2, sex);
                        stmt.setString(3, tel);
                        stmt.setString(4, gmail);
                        stmt.setString(5, roomType);
                        stmt.setDate(6, java.sql.Date.valueOf(bookDate));
                        stmt.setDate(7, java.sql.Date.valueOf(inDate));
                        stmt.setDate(8, java.sql.Date.valueOf(outDate));
                        stmt.setString(9, roomNumber);

                        int affectedRows = stmt.executeUpdate();

                        if (affectedRows > 0) {
                                // Retrieve the auto-generated id
                                ResultSet generatedKeys = stmt.getGeneratedKeys();
                                if (generatedKeys.next()) {
                                        int customerId = generatedKeys.getInt(1);
                                        // You can use customerId as needed, such as displaying it or performing further operations
                                        System.out.println("Generated Customer ID: " + customerId);
                                }

                                Alert alert = getAlert(affectedRows);
                                alert.showAndWait();

                                // Refresh the TableView after adding a new record
                                // Clear existing data and repopulate the TableView
                                customerShowlistData();
                        }
                } catch (SQLException e) {
                        e.printStackTrace();
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText(null);
                        alert.setContentText("An error occurred while adding the customer.");
                        alert.showAndWait();
                }
        }


        public void addCustomerSelect() {
                tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                        if (newSelection != null) {
                                Customerdb selectedCustomer = tableView.getSelectionModel().getSelectedItem();
                                txfName.setText(selectedCustomer.getName());
                                txfGmail.setText(selectedCustomer.getGmail());
                                txfTel.setText(selectedCustomer.getTel());
                                if ("Male".equals(selectedCustomer.getSex())) {
                                        radioMale.setSelected(true);
                                } else if ("Female".equals(selectedCustomer.getSex())) {
                                        radioFemale.setSelected(true);
                                }
                                txfRN.setText(selectedCustomer.getRoom());
                        }
                });
        }


        public void editCustomer() throws SQLException {
                // Get the selected customer from the TableView
                Customerdb selectedCustomer = tableView.getSelectionModel().getSelectedItem();

                // Check if a customer is selected
                if (selectedCustomer == null) {
                        // Display an error message if no customer is selected
                        showAlert(Alert.AlertType.ERROR, "Error", "No Customer Selected", "Please select a customer to edit.");
                        return;
                }

                // Get the values from the input fields
                String name = txfName.getText();
                String sex = getSelectedSexText();
                String tel = txfTel.getText();
                String gmail = txfGmail.getText();
                String roomType = cbRT.getValue();
                LocalDate bookDate = dpBD.getValue();
                LocalDate inDate = dpInD.getValue();
                LocalDate outDate = dpOD.getValue();
                String roomNumber = txfRN.getText();

                // Validate input fields
                if (name.isEmpty() || sex.isEmpty() || tel.isEmpty() || gmail.isEmpty() || roomType.isEmpty() || bookDate == null || inDate == null || outDate == null || roomNumber.isEmpty()) {
                        showAlert(Alert.AlertType.ERROR, "Error", "Missing Information", "Please fill in all the fields.");
                        return;
                }

                // Retrieve the ID of the selected customer from the database
                int customerId = getCustomerId(selectedCustomer.getName());

                // Update the customer in the TableView
                selectedCustomer.setName(name);
                selectedCustomer.setSex(sex);
                selectedCustomer.setTel(tel);
                selectedCustomer.setGmail(gmail);
                selectedCustomer.setRoomType(roomType);
                selectedCustomer.setBookDate(bookDate);
                selectedCustomer.setInDate(inDate);
                selectedCustomer.setOutDate(outDate);
                selectedCustomer.setRoom(roomNumber);

                tableView.refresh();

                // Update the customer in the database
                try (Connection conn = databaseConnection.connect()) {
                        String sql = "UPDATE customer SET Name = ?, Sex = ?, Tel = ?, Gmail = ?, `Room-type` = ?, `Book-date` = ?, `In-date` = ?, `Out-date` = ?, Room = ? WHERE id = ?";
                        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                                stmt.setString(1, name);
                                stmt.setString(2, sex);
                                stmt.setString(3, tel);
                                stmt.setString(4, gmail);
                                stmt.setString(5, roomType);
                                stmt.setDate(6, java.sql.Date.valueOf(bookDate));
                                stmt.setDate(7, java.sql.Date.valueOf(inDate));
                                stmt.setDate(8, java.sql.Date.valueOf(outDate));
                                stmt.setString(9, roomNumber);
                                stmt.setInt(10, customerId);

                                int affectedRows = stmt.executeUpdate();
                                if (affectedRows > 0) {
                                        showAlert(Alert.AlertType.INFORMATION, "Success", "Update Successful", "Customer details updated successfully in the database.");
                                } else {
                                        showAlert(Alert.AlertType.ERROR, "Error", "Update Failed", "Failed to update customer details in the database.");
                                }
                        }
                } catch (SQLException e) {
                        e.printStackTrace();
                        showAlert(Alert.AlertType.ERROR, "Error", "Database Error", "An error occurred while updating the customer details in the database.");
                }
        }

        private int getCustomerId(String customerName) throws SQLException {
                int customerId = -1;
                String sql = "SELECT id FROM customer WHERE Name = ?";
                try (Connection conn = databaseConnection.connect();
                     PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setString(1, customerName);
                        try (ResultSet rs = stmt.executeQuery()) {
                                if (rs.next()) {
                                        customerId = rs.getInt("id");
                                }
                        }
                }
                return customerId;
        }

        private void showAlert(Alert.AlertType type, String title, String header, String content) {
                Alert alert = new Alert(type);
                alert.setTitle(title);
                alert.setHeaderText(header);
                alert.setContentText(content);
                alert.showAndWait();
        }


        public void removeCustomer() {
                // Set action for the remove button
                btnRemove.setOnAction(event -> {
                        // Get the selected customer from the TableView
                        Customerdb selectedCustomer = tableView.getSelectionModel().getSelectedItem();

                        // Check if a customer is selected
                        if (selectedCustomer == null) {
                                // Display an error message if no customer is selected
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Error");
                                alert.setHeaderText(null);
                                alert.setContentText("Please select a customer to remove.");
                                alert.showAndWait();
                                return;
                        }

                        // Display a confirmation dialog before removing the customer
                        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
                        confirmation.setTitle("Confirmation");
                        confirmation.setHeaderText(null);
                        confirmation.setContentText("Are you sure you want to remove this customer?");
                        Optional<ButtonType> result = confirmation.showAndWait();

                        if (result.isPresent() && result.get() == ButtonType.OK) {
                                // Remove the customer from the TableView
                                tableView.getItems().remove(selectedCustomer);

                                // Remove the customer from the database
                                try (Connection conn = databaseConnection.connect()) {
                                        String sql = "DELETE FROM customer WHERE id = ?";
                                        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                                                stmt.setInt(1, selectedCustomer.getId());
                                                int affectedRows = stmt.executeUpdate();
                                                if (affectedRows > 0) {
                                                        // Display a success message if the customer is successfully removed from the database
                                                        Alert success = new Alert(Alert.AlertType.INFORMATION);
                                                        success.setTitle("Success");
                                                        success.setHeaderText(null);
                                                        success.setContentText("Customer removed successfully from the database.");
                                                        success.showAndWait();
                                                } else {
                                                        // Display an error message if the deletion operation fails
                                                        Alert error = new Alert(Alert.AlertType.ERROR);
                                                        error.setTitle("Error");
                                                        error.setHeaderText(null);
                                                        error.setContentText("Failed to remove customer from the database.");
                                                        error.showAndWait();
                                                }
                                        }
                                } catch (SQLException e) {
                                        e.printStackTrace();
                                        // Display an error message if an SQL exception occurs
                                        Alert sqlError = new Alert(Alert.AlertType.ERROR);
                                        sqlError.setTitle("Error");
                                        sqlError.setHeaderText(null);
                                        sqlError.setContentText("An error occurred while removing the customer from the database.");
                                        sqlError.showAndWait();
                                }
                        }
                });
        }


        @FXML
        private void searchByName(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)) {
                        String searchTerm = txfSearch.getText().trim();
                        ObservableList<Customerdb> originalList = populateTableView();
                        if (!searchTerm.isEmpty()) {
                                ObservableList<Customerdb> filteredList = originalList.filtered(customer ->
                                        customer.getName().toLowerCase().contains(searchTerm.toLowerCase())
                                );
                                tableView.setItems(filteredList);
                        } else {
                                // If the search field is empty, show all customers
                                tableView.setItems(originalList);
                        }
                }
        }

        public void clearTextFields() {
                txfName.clear();
                txfGmail.clear();
                txfTel.clear();
                txfRN.clear();
                dpBD.setValue(null);
                dpInD.setValue(null);
                dpOD.setValue(null);
                cbRT.getSelectionModel().clearSelection();
                radioMale.setSelected(false);
                radioFemale.setSelected(false);
        }

        private String getSelectedSexText() {
                if (radioMale.isSelected()) {
                        return radioMale.getText(); // Return the text of the male radio button
                } else if (radioFemale.isSelected()) {
                        return radioFemale.getText(); // Return the text of the female radio button
                } else {
                        return ""; // No selection, handle as needed
                }
        }

        private static Alert getAlert(int affectedRows) {
                Alert alert;
                if (affectedRows > 0) {
                        // Record updated successfully
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Success");
                        alert.setHeaderText(null);
                        alert.setContentText("Customer details updated successfully.");
                } else {
                        // No records updated
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText(null);
                        alert.setContentText("Failed to update customer details.");
                }
                return alert;
        }

        public void logout() {
                try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
                        Parent root = loader.load();

                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Confirmation Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Are you sure you want to logout?");
                        Optional<ButtonType> option = alert.showAndWait();

                        if (option.isPresent() && option.get().equals(ButtonType.OK)) {
                                Stage stage = (Stage) logout.getScene().getWindow();
                                stage.setScene(new Scene(root));
                        }
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }

        private void close() {
                close.setOnMouseClicked(event -> {
                        Stage stage = (Stage) close.getScene().getWindow();
                        stage.close();
                });
        }

        @Override
        public void initialize(URL location, ResourceBundle resources) {
                // Initialize choice box with values
                ObservableList<String> roomTypes = FXCollections.observableArrayList(
                        "Single Room", "Double Room", "Suite"
                );
                cbRT.setItems(roomTypes);
                cbRT.setValue("Single Room");

                // Add an event listener to the ChoiceBox to handle selection changes
                cbRT.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                        // Handle selection change here
                        System.out.println("Selected room type: " + newValue);
                });

                // Populate the TableView with initial data
                customerShowlistData();

                // Set action for the edit button
                btnEdit.setOnAction(event -> {
                        try {
                                editCustomer();
                        } catch (SQLException e) {
                                throw new RuntimeException(e);
                        }
                });
                addCustomerSelect();
                removeCustomer();
                close();
                txfSearch.setOnKeyPressed(this::searchByName);
                clearTextFields();
        }
}
