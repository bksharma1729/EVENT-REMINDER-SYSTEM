import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;

public class EventReminderFX extends Application {
    private final ObservableList<Event> events = FXCollections.observableArrayList();
    private final EventStorage storage = new EventStorage("events.txt");

    private ListView<Event> eventListView;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        storage.loadFromFile(events);

        stage.setTitle("Event Reminder Management System");

        // List view
        eventListView = new ListView<>(events);

        // Buttons
        Button addBtn = new Button("Add Event");
        Button removeBtn = new Button("Remove Event");
        Button markBtn = new Button("Toggle Status");
        Button searchBtn = new Button("Search Event");
        Button viewCompletedBtn = new Button("View Completed");
        Button resetBtn = new Button("Show All");

        // Layout
        HBox buttons = new HBox(10, addBtn, removeBtn, markBtn, searchBtn, viewCompletedBtn, resetBtn);
        buttons.setPadding(new Insets(10));

        VBox root = new VBox(10, eventListView, buttons);
        root.setPadding(new Insets(10));

        // Button Actions
        addBtn.setOnAction(e -> addEvent());
        removeBtn.setOnAction(e -> removeEvent());
        markBtn.setOnAction(e -> toggleStatus());
        searchBtn.setOnAction(e -> searchEvent());
        viewCompletedBtn.setOnAction(e -> showCompleted());
        resetBtn.setOnAction(e -> eventListView.setItems(events));

        stage.setScene(new Scene(root, 600, 400));
        stage.setOnCloseRequest(e -> storage.saveToFile(events));
        stage.show();
    }

    private void addEvent() {
        Dialog<Event> dialog = new Dialog<>();
        dialog.setTitle("Add Event");

        Label titleLabel = new Label("Title:");
        Label descLabel = new Label("Description:");
        Label dateLabel = new Label("Date (dd-mm-yy):");

        TextField titleField = new TextField();
        TextField descField = new TextField();
        TextField dateField = new TextField();

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(titleLabel, 0, 0);
        grid.add(titleField, 1, 0);
        grid.add(descLabel, 0, 1);
        grid.add(descField, 1, 1);
        grid.add(dateLabel, 0, 2);
        grid.add(dateField, 1, 2);

        dialog.getDialogPane().setContent(grid);

        ButtonType addType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addType, ButtonType.CANCEL);

        dialog.setResultConverter(bt -> {
            if (bt == addType) {
                String title = titleField.getText().trim();
                String desc = descField.getText().trim();
                String date = dateField.getText().trim();

                if (title.isEmpty() || date.isEmpty()) {
                    alert("Title and Date cannot be empty!");
                    return null;
                }
                return new Event(title, desc, date);
            }
            return null;
        });

        dialog.showAndWait().ifPresent(events::add);
    }

    private void removeEvent() {
        Event selected = eventListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            events.remove(selected);
        } else {
            alert("Please select an event to remove!");
        }
    }

    private void toggleStatus() {
        Event selected = eventListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            selected.toggleStatus();
            eventListView.refresh();
        } else {
            alert("Please select an event to toggle status!");
        }
    }

    private void searchEvent() {
        TextInputDialog input = new TextInputDialog();
        input.setHeaderText("Enter title/date to search:");
        input.showAndWait().ifPresent(key -> {
            String searchKey = key.trim().toLowerCase();
            List<Event> filtered = events.stream()
                    .filter(e -> e.getTitle().toLowerCase().contains(searchKey) ||
                                 e.getDate().toLowerCase().contains(searchKey))
                    .collect(Collectors.toList());
            if (filtered.isEmpty()) {
                alert("No events found!");
            } else {
                eventListView.setItems(FXCollections.observableArrayList(filtered));
            }
        });
    }

    private void showCompleted() {
        List<Event> completed = events.stream().filter(Event::isCompleted).collect(Collectors.toList());
        if (completed.isEmpty()) {
            alert("No completed events!");
        } else {
            eventListView.setItems(FXCollections.observableArrayList(completed));
        }
    }

    private void alert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
