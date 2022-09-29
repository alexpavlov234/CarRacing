module hristov.mihail.carracing {
    requires javafx.controls;
    requires javafx.fxml;


    opens hristov.mihail.carracing to javafx.fxml;
    exports hristov.mihail.carracing;
}