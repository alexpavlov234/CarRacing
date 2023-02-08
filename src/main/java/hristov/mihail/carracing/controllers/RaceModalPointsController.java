package hristov.mihail.carracing.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class RaceModalPointsController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<?, ?> firstName;

    @FXML
    private TableColumn<?, ?> lastName;

    @FXML
    private TableColumn<?, ?> nationality;

    @FXML
    private TableColumn<?, ?> points;

    @FXML
    private TableView<?> table;

    @FXML
    void initialize() {
        assert firstName != null : "fx:id=\"firstName\" was not injected: check your FXML file 'race-points-modal.fxml'.";
        assert lastName != null : "fx:id=\"lastName\" was not injected: check your FXML file 'race-points-modal.fxml'.";
        assert nationality != null : "fx:id=\"nationality\" was not injected: check your FXML file 'race-points-modal.fxml'.";
        assert points != null : "fx:id=\"points\" was not injected: check your FXML file 'race-points-modal.fxml'.";
        assert table != null : "fx:id=\"table\" was not injected: check your FXML file 'race-points-modal.fxml'.";

    }

}
