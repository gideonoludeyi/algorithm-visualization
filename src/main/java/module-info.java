module com.algovisualizer.demo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.algovisualizer.demo to javafx.fxml;
    exports com.algovisualizer.demo;
}