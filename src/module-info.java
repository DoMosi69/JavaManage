module sample {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;
    requires java.net.http;

    opens sample to javafx.fxml;

    exports sample;
}
