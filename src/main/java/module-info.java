module com.example.ui {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jooq;
    requires java.desktop;
    requires jjwt;


    opens com.example.ui to javafx.fxml;
    exports com.example.ui;
}