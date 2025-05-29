//package tcs.familytree;
//
//import javafx.application.Application;
//import javafx.stage.Stage;
//import javafx.application.Application;
//import javafx.scene.Scene;
//import javafx.scene.input.MouseEvent;
//import javafx.scene.layout.Pane;
//import javafx.scene.paint.Color;
//import javafx.scene.shape.Circle;
//import javafx.scene.shape.CubicCurve;
//import javafx.stage.Stage;
//
//
//public class CubicCurveTest extends Application {
//    private Circle makeDraggablePoint(double x, double y, Color color) {
//        Circle point = new Circle(x, y, 6, color);
//        point.setStroke(Color.BLACK);
//        point.setStrokeWidth(1.5);
//
//        point.setOnMousePressed(e -> point.setUserData(new double[]{e.getX(), e.getY()}));
//
//        point.setOnMouseDragged(e -> {
//            point.setCenterX(e.getX());
//            point.setCenterY(e.getY());
//        });
//
//        return point;
//    }
//
//    @Override
//    public void start(Stage stage) {
//        Pane pane = new Pane();
//
//        // Tworzymy punkty
//        Circle start = makeDraggablePoint(50, 200, Color.RED);
//        Circle control1 = makeDraggablePoint(150, 50, Color.ORANGE);
//        Circle control2 = makeDraggablePoint(250, 350, Color.ORANGE);
//        Circle end = makeDraggablePoint(350, 200, Color.BLUE);
//
//        // Krzywa
//        CubicCurve curve = new CubicCurve();
//        curve.setStroke(Color.DARKGREEN);
//        curve.setStrokeWidth(2);
//        curve.setFill(null);
//
//        // Bindujemy pozycje krzywej do punktów
//        curve.startXProperty().bind(start.centerXProperty());
//        curve.startYProperty().bind(start.centerYProperty());
//        curve.controlX1Property().bind(control1.centerXProperty());
//        curve.controlY1Property().bind(control1.centerYProperty());
//        curve.controlX2Property().bind(control2.centerXProperty());
//        curve.controlY2Property().bind(control2.centerYProperty());
//        curve.endXProperty().bind(end.centerXProperty());
//        curve.endYProperty().bind(end.centerYProperty());
//
//        // Dodajemy do sceny
//        pane.getChildren().addAll(curve, start, control1, control2, end);
//
//        Scene scene = new Scene(pane, 500, 400);
//        stage.setTitle("Interaktywna krzywa (CubicCurve)");
//        stage.setScene(scene);
//        stage.show();
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//
//}
