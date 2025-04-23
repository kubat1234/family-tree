module com.familytree.hello {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.familytree.hello to javafx.fxml;
    exports com.familytree.hello;
}