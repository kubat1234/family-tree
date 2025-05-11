package tcs.familytree.views;

import tcs.familytree.core.person.Person;
import tcs.familytree.services.FamilyGraph;
import tcs.familytree.viewmodels.SingleTreeViewModel;

import java.util.Collection;
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
        String helpMessage = "1: wypisz wszystkie nazwiska; 2: wypisz wszystkie dzieci; 3: odśwież dane; 4: zabij widok CLI\n" +
        "[5 \\n x]: wypisz wszystkie dane o osobie o id=x; [6 \\n x\\n y]: ustaw imię osoby o id=x na y";
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
                case "5": {
                    String id = scanner.nextLine();
                    try {
                        //Person p = viewModel.getDatabaseConnection().getPerson(Integer.parseInt(id));
                        Person p = viewModel.getGraphProperty().get().getAllPersons().stream().filter(q -> q.getId() == Integer.parseInt(id)).findFirst().get();
                        System.out.println(p.getName() + " " + String.join(" ", p.getAllSurnames()));
                    } catch (Exception e) {
                        System.out.println("Niepoprawne id");
                    }
                    break;
                }
                case "6": {
                    String id = scanner.nextLine();
                    try {
//                        Person p = viewModel.getDatabaseConnection().getPerson(Integer.parseInt(id));
                        Person p = viewModel.getGraphProperty().get().getAllPersons().stream().filter(q -> q.getId() == Integer.parseInt(id)).findFirst().get();
                        String newName = scanner.nextLine();
                        p.setName(newName);
                        System.out.println("Zmieniono na: " + p.getName() + " " + String.join(" ", p.getAllSurnames()));
                    } catch (Exception e) {
                        System.out.println("Wystąpił błąd");
                        e.printStackTrace();
                    }
                    break;
                }
                default:
                    System.out.println(helpMessage);
            }
        }
    }
}
