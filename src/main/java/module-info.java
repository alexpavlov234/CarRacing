module hristov.mihail.carracing {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens hristov.mihail.carracing to javafx.fxml;
    exports hristov.mihail.carracing;
    opens hristov.mihail.carracing.controllers;
    exports hristov.mihail.carracing.controllers;

    opens hristov.mihail.carracing.models to javafx.fxml;
    exports hristov.mihail.carracing.models;
    opens hristov.mihail.carracing.services to javafx.fxml;
    exports hristov.mihail.carracing.services;

    opens hristov.mihail.carracing.data to javafx.fxml;
    exports hristov.mihail.carracing.data;


}