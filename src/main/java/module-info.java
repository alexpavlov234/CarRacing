module hristov.mihail.carracing {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    requires org.jfxtras.styles.jmetro;
    opens hristov.mihail.carracing to javafx.fxml;
    exports hristov.mihail.carracing;
    opens hristov.mihail.carracing.controllers;
    exports hristov.mihail.carracing.controllers;


}