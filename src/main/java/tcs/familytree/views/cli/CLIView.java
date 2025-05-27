package tcs.familytree.views.cli;

import tcs.familytree.core.person.Person;
import tcs.familytree.viewmodels.SingleTreeViewModel;

import java.util.List;
import java.util.NoSuchElementException;

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
            try {
                currentMode = currentMode.parseLine();
            }
            catch (Exception e) { //exception <=> stdin is closed
                break;
            }
        }
    }
}

abstract class Mode {
    SingleTreeViewModel viewModel;
    String prompt;
    String helpMessage = "//TODO " + this.getClass().getSimpleName() + " help message\n";
    abstract protected Mode _parseLine(List<String> line);
    protected Mode parseLine() {
        preParse();
        List<String> line;
        try {
            line = CommandParser.parseStdin();
        } catch (NoSuchElementException | IllegalStateException e) { //stdin is closed
            throw e;
        } catch (Exception e) {
            System.out.println("Niepoprawna komenda:\n\t" + e.getMessage());
            return this;
        }
        {
            Mode mode = preChecks(line);
            if(mode != null) {
                return mode;
            }
        }
        return _parseLine(line);
    }
    protected void preParse() {
        System.out.print(prompt + " >> ");
    }
    protected Mode preChecks(List<String> line) {
        if(line.isEmpty()) {
            return this;
        }
        if(line.getFirst().equalsIgnoreCase("help")) {
            System.out.println(helpMessage);
            return this;
        }
        return null;
    }
    protected void commandNotFound() {
        System.out.println("Nie znaleziono polecenia. Wpisz \"help\" aby uzyskać pomoc");
    }
}

abstract class ResourceMode extends Mode {
    Mode lastMode;

    ResourceMode(SingleTreeViewModel viewModel, Mode lastMode) {
        this.viewModel = viewModel;
        this.lastMode = lastMode;
    }

    @Override
    protected Mode preChecks(List<String> line) {
        {
            Mode mode = super.preChecks(line);
            if(mode != null) {
                return mode;
            }
        }
        switch (line.getFirst().toLowerCase()) {
            case "close":
                return new BasicMode(viewModel);
            case "back":
                return lastMode;
            default:
                return null;
        }
    }
}

class BasicMode extends Mode {
    SingleTreeViewModel viewModel;

    BasicMode(SingleTreeViewModel viewModel) {
        this.viewModel = viewModel;
        prompt = "familytree";
        helpMessage = new HelpMessageFactory().setIntro("Dostępne komendy: ")
                .addCommand("UPDATE", "pobiera dane osób z bazy")
                .addCommand("SELECT PERSON id", "wybiera osobę o określonym id",
                        List.of("id - id osoby"))
                .addCommand("PRINTALL", "wypisuje dane wszystkich osób")
                .generate();
    }

    @Override
    protected Mode _parseLine(List<String> line) {
        try {
            switch (line.getFirst().toLowerCase()) {
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
                        break;
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
                                return new PersonMode(viewModel, this, p);
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
                    commandNotFound();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }
}

class PersonMode extends ResourceMode {
    Person person;

    PersonMode(SingleTreeViewModel viewModel, Mode lastMode, Person person) {
        super(viewModel, lastMode);
        this.person = person;
        prompt = lastMode.prompt + "/P" + person.getId();
        helpMessage = HelpMessageFactory.resourceModeFactory()
                .addCommand("CHANGE NAME new_name", "zmienia imię osoby",
                        List.of("new_name - nowe imię"))
                .generate();
    }

    @Override
    protected Mode _parseLine(List<String> line) {
        try {
            switch (line.getFirst().toLowerCase()) {
                case "print":
                    System.out.println(person.getId() + ". " + person);
                    break;
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
                    commandNotFound();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }
}

/**
 * Helper class to make help messages of various modes consistent.
 * Pretty much self-explanatory.
 */

final class HelpMessageFactory {

    static HelpMessageFactory resourceModeFactory() {
        return new HelpMessageFactory().setIntro("Dostępne komendy:")
                .addCommand("BACK", "wraca do poprzedniego widoku")
                .addCommand("CLOSE", "wraca do podstawowego widoku");
    }
    String intro = null;
    StringBuilder body;
    HelpMessageFactory() {
        body = new StringBuilder();
        addCommand("HELP", "wyświetla tę listę");
    }
    public String generate() {
        if(intro == null) {
            throw new NullPointerException();
        }
        return intro + "\n" + body;
    }
    /**
     * @param usage Format: `COMMAND NAME arg1 [arg3]`
     * @param description Format: `single sentence, lower case`
     */
    public HelpMessageFactory addCommand(String usage, String description) {
        return addCommand(usage, description, null);
    }

    /**
     * @param usage Format: `COMMAND NAME arg1 [arg3]`
     * @param description Format: `single sentence, lower case`
     * @param args Format: `arg1 - name of the item` `arg2 (optional) - description of the item`
     */
    public HelpMessageFactory addCommand(String usage, String description, List<String> args) {
        body.append(usage).append(" - ").append(description).append("\n");
        if(args != null) {
            for(String s : args) {
                body.append("\t").append(s).append("\n");
            }
        }
        return this;
    }

    public HelpMessageFactory setIntro(String intro) {
        this.intro = intro;
        return this;
    }
}