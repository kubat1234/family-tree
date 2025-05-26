package tcs.familytree.views.cli;

import tcs.familytree.core.person.Person;
import tcs.familytree.services.FamilyGraph;
import tcs.familytree.viewmodels.SingleTreeViewModel;
import tcs.familytree.views.GraphView;

import java.util.List;

public class CLIView {
    final SingleTreeViewModel viewModel;
    public CLIView(SingleTreeViewModel viewModel) {
        this.viewModel = viewModel;
        new Thread(CLIView.this::run).start();
    }

    private void run() {
        System.out.println(" -=-=- Family Tree -=-=-");
        System.out.println(" -=-=- Program do drzew genealogicznych -=-=-");
        Mode currentMode = new BasicMode(viewModel);
        while (true) {
            currentMode = currentMode.parseLine();
        }
    }
}

interface Mode {
    Mode parseLine();
}

class BasicMode implements Mode {
    SingleTreeViewModel viewModel;

    BasicMode(SingleTreeViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public Mode parseLine() {
        try {
            System.out.print("basic mode >> ");
            List<String> line = CommandParser.parseStdin();
            if(line.isEmpty()) {
                return this;
            }
            switch (line.getFirst().toLowerCase()) {
                case "help":
                    System.out.println("TODO basicmode help message");
                    break;
                case "printall":
                    System.out.println("Lista osób: ");
                    for(Person person : viewModel.getGraphProperty().get().getAllPersons()) {
                        System.out.println(person.getId() + ". " + person);
                    }
                    break;
                case "update":
                    System.out.println("Pobieranie danych z bazy ...");
                    viewModel.updateGraph();
                    break;
                case "select":
                {
                    if(line.size() < 3) {
                        System.out.println("użyj SELECT PERSON id");
                    }
                    switch (line.get(1).toLowerCase()) {
                        case "person":
                            try {
                                int id = Integer.parseInt(line.get(2));
                                Person p = viewModel.getGraphProperty().get().getPerson(id);
                                if(p == null) {
                                    System.out.println("Nie znaleziono osoby o id " + id);
                                    break;
                                }
                                return new PersonMode(viewModel, p);
                            } catch (Exception e) {
                                System.out.println("\"" + line.get(2) + "\" nie jest liczbą");
                                break;
                            }
                        default:
                            System.out.println("użyj SELECT PERSON id");
                    }
                    break;
                }
                default:
                    System.out.println("Nie znaleziono polecenia. Wpisz \"help\" aby uzyskać pomoc");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }
}

class PersonMode implements Mode {
    SingleTreeViewModel viewModel;
    Person person;

    PersonMode(SingleTreeViewModel viewModel, Person person) {
        this.viewModel = viewModel;
        this.person = person;
    }

    @Override
    public Mode parseLine() {
        try {
            System.out.print("person no. " + person.getId() + " >> ");
            List<String> line = CommandParser.parseStdin();
            if (line.isEmpty()) {
                return this;
            }
            switch (line.getFirst().toLowerCase()) {
                case "help":
                    System.out.println("TODO personmode help message");
                    break;
                case "print":
                    System.out.println(person.getId() + ". " + person);
                    break;
                case "close":
                    return new BasicMode(viewModel);
                case "change":
                    if (line.size() < 3) {
                        System.out.println("użyj CHANGE NAME new_value");
                        break;
                    }
                    switch (line.get(1).toLowerCase()) {
                        case "name":
                            person.setName(line.get(2));
                            break;
                        default:
                            System.out.println("użyj CHANGE NAME new_value");
                    }
                    break;
                default:
                    System.out.println("Nie znaleziono polecenia. Wpisz \"help\" aby uzyskać pomoc");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }
}