package tcs.familytree;

import tcs.familytree.viewmodels.SingleDatabaseViewModel;
import tcs.familytree.views.cli.CLIView;

/**
 * Starts the CLI.
 */
public class CLIMain {

    public static void main(String[] args) {
        CLIView cliView = new CLIView(new SingleDatabaseViewModel());
    }
}