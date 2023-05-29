module com.example.projekt_gra {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.werka.gra to javafx.fxml;
    exports com.werka.gra;
    exports com.werka.gra.objects;
    opens com.werka.gra.objects to javafx.fxml;
}