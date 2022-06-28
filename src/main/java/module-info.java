module com.ruslan.gis {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.ruslan.gis to javafx.fxml;
    exports com.ruslan.gis;
    exports com.ruslan.gis.shapes;
    opens com.ruslan.gis.shapes to javafx.fxml;
    exports com.ruslan.gis.utils;
    opens com.ruslan.gis.utils to javafx.fxml;
}