package tcs.familytree;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;


public class Main extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader= new FXMLLoader(Main.class.getResource("main.fxml"));
        Parent root = loader.load();
//        ModuleLayer.Controller controller = loader.getController();
        MainController controller = loader.getController();
        controller.setStage(stage);

        Scene scene = new Scene(root, 1000, 700);
        scene.setOnKeyPressed(
                (KeyEvent event) -> {
                    System.out.println("Pressed: " + event.getCode() + ", target: " + event.getTarget());
                    switch (event.getCode()){
                        case A:
                            System.out.println("Pressed A");
                            controller.moveLeft();
                            break;
                        case W:
                            System.out.println("Pressed W");
                            controller.moveUp();
                            break;
                        case S:
                            System.out.println("Pressed S");
                            controller.moveDown();
                            break;
                        case D:
                            controller.moveRight();
                            System.out.println("Pressed D");
                            break;
                        case SPACE:
                            System.out.println("Pressed SPACE");
                            break;
                        default:
                            break;
                    }
                }
        );
        root.requestFocus();


        stage.setScene(scene);
        stage.show();
    }
}
