package tcs.familytree.views;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.text.TextFlow;
import org.jooq.Field;
import org.jooq.Result;
import tcs.familytree.viewmodels.GraphViewModel;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AdminPanel implements Initializable {
    public TextArea queryTextArea;
    public Button sendButton;
    public TableView<ObservableList<String>> dynamicTable;
    public TextArea errorArea;
    GraphViewModel viewModel;
    public void setViewModel(GraphViewModel viewModel) {
        this.viewModel = viewModel;
    }
    public void initialize(URL location, ResourceBundle resources) {
        errorArea.setEditable(false);
        errorArea.setStyle("-fx-text-fill: red;");
    }
    public void sendQuery(ActionEvent event) {
        errorArea.clear();
        try {
            Result<org.jooq.Record> result = viewModel.sendQuery(queryTextArea.getText());
            buildDynamicTable(dynamicTable, result);
            viewModel.updateAll();
            viewModel.refresh().hardRefresh();
        }catch(Exception e) {
            errorArea.setText(e.getMessage());
        }
    }

    private void buildDynamicTable(TableView<ObservableList<String>> table, Result<org.jooq.Record> result) {
        table.getColumns().clear();
        table.getItems().clear();

        if (result == null || result.isEmpty()) return;

        List<Field<?>> fields = List.of(result.fields());

        for (int colIndex = 0; colIndex < fields.size(); colIndex++) {
            final int index = colIndex;
            TableColumn<ObservableList<String>, String> column =
                    new TableColumn<>(fields.get(colIndex).getName());

            column.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData.getValue().get(index)));

            table.getColumns().add(column);
        }

        for (org.jooq.Record record : result) {
            ObservableList<String> row = FXCollections.observableArrayList();
            for (Field<?> field : fields) {
                Object value = record.get(field);
                row.add(value != null ? value.toString() : "null");
            }
            table.getItems().add(row);
        }
    }

    private TableView<ObservableList<String>> createDynamicTable(Result<org.jooq.Record> result) {
        TableView<ObservableList<String>> table = new TableView<>();

        if (result.isEmpty()) return table;

        List<Field<?>> fields = List.of(result.fields());

        // 1. Tworzymy kolumny dynamicznie
        for (int colIndex = 0; colIndex < fields.size(); colIndex++) {
            final int index = colIndex;
            TableColumn<ObservableList<String>, String> column = new TableColumn<>(fields.get(colIndex).getName());

            column.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData.getValue().get(index))
            );

            table.getColumns().add(column);
        }

        // 2. Tworzymy dane wierszy
        for (org.jooq.Record record : result) {
            ObservableList<String> row = FXCollections.observableArrayList();

            for (Field<?> field : fields) {
                Object value = record.get(field);
                row.add(value != null ? value.toString() : "null");
            }

            table.getItems().add(row);
        }

        return table;
    }

}
