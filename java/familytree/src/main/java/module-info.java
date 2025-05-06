module tcs.familytree {
    requires javafx.controls;
    requires javafx.fxml;


    opens tcs.familytree to javafx.fxml;
    exports tcs.familytree;
}