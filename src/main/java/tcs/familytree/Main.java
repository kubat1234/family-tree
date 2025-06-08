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
        controller.loaderGraphOrientedViewModel();
        controller.openLeftPanel(null);

        Scene scene = new Scene(root, 1000, 700);
        scene.setOnKeyPressed(
                (KeyEvent event) -> {
                    switch (event.getCode()){
                        case A:
                            controller.moveLeft();
                            break;
                        case W:
                            controller.moveUp();
                            break;
                        case S:
                            controller.moveDown();
                            break;
                        case D:
                            controller.moveRight();
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
