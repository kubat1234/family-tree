package tcs.familytree.views;

import tcs.familytree.core.Person;
import tcs.familytree.services.FamilyGraph;
import tcs.familytree.services.SimpleGraph;
import tcs.familytree.viewmodels.SingleTreeViewModel;

import java.util.Collection;
import java.util.Iterator;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Basic CLI view. It's very stupid.
 */
public class StupidCLIView {
    final SingleTreeViewModel viewModel;
    public StupidCLIView(SingleTreeViewModel viewModel) {
        this.viewModel = viewModel;
        new Thread() {
            public void run() {
                StupidCLIView.this.run();
            }
        }.start();
    }

    private void run() {
        System.out.println(" -=-=- Family Tree -=-=-");
        System.out.println(" -=-=- Program do drzew genealogicznych -=-=-");
        Scanner scanner = new Scanner(System.in);
        String helpMessage = "1: wypisz wszystkie nazwiska; 2: wypisz wszystkie dzieci; 3: odśwież dane; 4: zabij widok CLI";
        System.out.println(helpMessage);
        outer:
        while(scanner.hasNext()) {
            switch (scanner.nextLine())
            {
                case "1":
                    System.out.println("Lista osób: ");
                    for(Person person : viewModel.getGraphProperty().get().getAllPersons()) {
                        System.out.println(person);
                    }
                    break;
                case "2": {
                    System.out.println("Osoby z dziećmi: ");
                    FamilyGraph graph = viewModel.getGraphProperty().get();
                    for (Person p : graph.getAllPersons()) {
                        Collection<Person> children = graph.getChildren(p);
                        if(!children.isEmpty()) {
                            System.out.println(p.toString() + ": " + graph.getChildren(p).stream().map(Object::toString).collect(Collectors.joining(", ")));
                        }
                    }
                    break;
                }
                case "3": {
                    System.out.println("Pobieranie danych");
                    viewModel.updateGraph();
                    break;
                }
                case "4": {
                    System.out.println("Do widzenia");
                    break outer;
                }
                default:
                    System.out.println("nie jedynka");
            }
        }
    }
}
