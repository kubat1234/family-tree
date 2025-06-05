package tcs.familytree;

import tcs.familytree.viewmodels.SingleDatabaseViewModel;
import tcs.familytree.views.cli.CLIView;

/**
 * Starts the CLI.
 * Uncomment to start the old GUI
 */
public class CLIMain /* extends Application */ {

    public static void main(String[] args) {
//        launch();
        CLIView cliView = new CLIView(new SingleDatabaseViewModel());
    }
}