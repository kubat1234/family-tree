package tcs.familytree;

import tcs.familytree.viewmodels.SingleDatabaseViewModel;
import tcs.familytree.views.cli.CLIView;

/**
 * Starts the CLI.
 * Uncomment to start the old GUI
 */
public class Main /* extends Application */ {

    /*
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = new FXMLLoader(Main.class.getResource("main.fxml")).load();
        Scene scene = new Scene(root, 1000, 700);
        stage.setScene(scene);
        stage.show();
    }
     */

    public static void main(String[] args) {
//        launch();
        CLIView cliView = new CLIView(new SingleDatabaseViewModel());
    }
}