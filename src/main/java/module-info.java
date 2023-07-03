module com.example.projekt_gra {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;


    opens com.werka.gra to javafx.fxml;
    exports com.werka.gra;
    exports com.werka.gra.objects;
    opens com.werka.gra.objects to javafx.fxml;
}