/*
*
* Project 7: Inventory Management
* Caleb Davenport & Roan Martin-Hayden
* EECS 1510-091: Dr. Ledgard
*
* Description:
* Entry class handles parsing data that's input
* via individual parameters or parsing the individual lines
* The data is then exported via public functions.
*
*/

package project7;

import java.io.File;
import java.util.Optional;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.control.ButtonBar.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.xml.ws.Response;

public class InventoryManagementGUI extends Application {
    private static final TableView<Entry> TABLE = new TableView<>();
    private static final MenuBar MENU_BAR = new MenuBar();
    private static final HBox BOTTOM_BOX = new HBox();
    private static final VBox RIGHT_BOX = new VBox();
    private static final BorderPane ROOT = new BorderPane();
    private static String filterText = "";
    private static Stage primaryStage = null;
    private static EntryReport lastReport = new EntryReport();
    
    /**
     * 
     * @param primaryStage 
     */
    @Override
    public void start(Stage primaryStage) {
        InventoryManagementGUI.primaryStage = primaryStage;
        
        InventoryManagement.addEntry("Nuts", "100", "Very Nutty");
        InventoryManagement.addEntry("Soup", "6", "Very Soupy");
        
        addMenus();
        updateTable();
        setupSidePanel();
        setupMargins();
        
        //table.setPadding(new Insets(10));
        BorderPane.setMargin(TABLE, new Insets(0, 10, 0, 10));
        BorderPane.setMargin(MENU_BAR, new Insets(0, 0, 10, 0));
        ROOT.setTop(MENU_BAR);
        ROOT.setCenter(TABLE);
        ROOT.setRight(RIGHT_BOX);
        ROOT.setBottom(BOTTOM_BOX);
        
        Scene scene = new Scene(ROOT, 800, 400);
        
        InventoryManagementGUI.primaryStage.setTitle("Inventory Management");
        InventoryManagementGUI.primaryStage.setScene(scene);
        InventoryManagementGUI.primaryStage.show();
        setupBottomFilter();
    }
    
    /**
     * 
     */
    private void addMenus() {
        Menu menuFile = new Menu("File");
        {
            MenuItem newList = new MenuItem("New List");
            newList.setAccelerator(KeyCombination.keyCombination("Ctrl+N"));
            newList.setOnAction((ActionEvent e) -> {
                newListHandler();
            });

            MenuItem openList = new MenuItem("Open List");
            openList.setAccelerator(KeyCombination.keyCombination("Ctrl+O"));
            openList.setOnAction((ActionEvent e) -> {
                openListHandler();
            });

            MenuItem saveList = new MenuItem("Save List");
            saveList.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
            saveList.setOnAction((ActionEvent e) -> {
                saveListHandler();
            });

            MenuItem exit = new MenuItem("Exit");
            exit.setAccelerator(KeyCombination.keyCombination("Ctrl+Q"));
            exit.setOnAction((ActionEvent e) -> {
                System.exit(0);
            });

            menuFile.getItems().addAll(newList, openList, saveList,
                                       new SeparatorMenuItem(), exit);
        }
        
        Menu menuEdit = new Menu("Edit");
        {
            MenuItem addEntry = new MenuItem("Add Entry");
            addEntry.setOnAction((ActionEvent e) -> {
                entryDialogHandler(false);
            });

            MenuItem editEntry = new MenuItem("Edit Entry");
            editEntry.setOnAction((ActionEvent e) -> {
                entryDialogHandler(true);
            });

            MenuItem deleteEntry = new MenuItem("Delete Entry");
            deleteEntry.setAccelerator(new KeyCodeCombination(KeyCode.DELETE));
            deleteEntry.setOnAction((ActionEvent e) -> {
                deleteEntryHandler();
            });

            menuEdit.getItems().addAll(addEntry, editEntry, deleteEntry);
        }
        
        Menu menuHelp = new Menu("Help");
        {
            MenuItem about = new MenuItem("About");
            about.setOnAction((ActionEvent e) -> {
                aboutHandler();
            });

            menuHelp.getItems().addAll(about);
        }
        
        MENU_BAR.getMenus().addAll(menuFile, menuEdit, menuHelp);
    }
    
    /**
     * 
     */
    private void updateTable() {
        TABLE.getColumns().clear();
        TableColumn nameCol = new TableColumn("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Entry, String> numberCol = new TableColumn("#");
        numberCol.setCellValueFactory(new PropertyValueFactory<>("number"));
        TableColumn notesCol = new TableColumn("Notes");
        notesCol.setCellValueFactory(new PropertyValueFactory<>("notes"));
        
        nameCol.prefWidthProperty().bind(TABLE.widthProperty().multiply(0.1));
        numberCol.prefWidthProperty().bind(TABLE.widthProperty().multiply(0.05));
        notesCol.prefWidthProperty().bind(TABLE.widthProperty().multiply(0.85));
        TABLE.setItems(InventoryManagement.filteredEntries(filterText));
        TABLE.getColumns().addAll(nameCol, numberCol, notesCol);
        TABLE.setPlaceholder(new Label("No entries found"));
    }
    
    /**
     * 
     */
    private void setupSidePanel() {
        Button addEntry = new Button("Add");
        addEntry.setOnAction((ActionEvent e) -> {
            entryDialogHandler(false);
        });

        Button editEntry = new Button("Edit");
        editEntry.setOnAction((ActionEvent e) -> {
            entryDialogHandler(true);
        });

        Button deleteEntry = new Button("Delete");
        deleteEntry.setOnAction((ActionEvent e) -> {
            deleteEntryHandler();
        });
        
        addEntry.setMaxWidth(Double.MAX_VALUE);
        editEntry.setMaxWidth(Double.MAX_VALUE);
        deleteEntry.setMaxWidth(Double.MAX_VALUE);
        
        Image imgAdd = new Image("file:img/green_plus.png", 16, 16, true, false);
        addEntry.setGraphic(new ImageView(imgAdd));
        Image imgEdit = new Image("file:img/pencil.png", 16, 16, true, true);
        editEntry.setGraphic(new ImageView(imgEdit));
        Image imgDel = new Image("file:img/red_x.png", 16, 16, true, false);
        deleteEntry.setGraphic(new ImageView(imgDel));
        
        RIGHT_BOX.setSpacing(5);
        RIGHT_BOX.getChildren().addAll(addEntry, editEntry, deleteEntry);
    }
    
    /**
     * 
     */
    private void setupBottomFilter() {
        TextField filter = new TextField();
        filter.setPromptText("Filter");
        filter.prefWidthProperty().bind(TABLE.widthProperty());
        filter.setOnKeyReleased((KeyEvent e) -> {
            filterHandler(filter.getText());
        });
        
        ComboBox criteria = new ComboBox();
        criteria.getItems().addAll(
                "Name",
                "Notes"
        );
        criteria.setValue("Name");
        criteria.setMaxWidth(Double.MAX_VALUE);
        criteria.valueProperty().addListener(new ChangeListener<String>() {
            @Override 
            public void changed(ObservableValue ov, String oldStr, String newStr) {                
                filterChoiceHandler(newStr);
            }
        });
        
        BOTTOM_BOX.setSpacing(10);
        BOTTOM_BOX.getChildren().addAll(filter, criteria);
    }
    
    /**
     * 
     */
    private void setupMargins() {
        BorderPane.setMargin(TABLE, new Insets(0, 10, 10, 0));
        BorderPane.setMargin(MENU_BAR, new Insets(0, 0, 10, 0));
        BorderPane.setMargin(RIGHT_BOX, new Insets(0, 10, 0, 0));
        BorderPane.setMargin(BOTTOM_BOX, new Insets(5, 10, 5, 10));
    }
    
    /**
     * 
     */
    private void addEntryHandler() {
        EntryReport report;
        
        Dialog dialog = new Dialog();
        dialog.setTitle("Add Entry");
        dialog.setHeaderText("Add Entry");
        ButtonType loginButtonType = new ButtonType("Add", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType,
                                                       ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField name = new TextField();
        name.setPromptText("Name");
        TextField number = new TextField();
        number.setPromptText("Quantity");
        TextField notes = new TextField();
        notes.setPromptText("Notes");

        grid.add(new Label("Name:"), 0, 0);
        grid.add(name, 1, 0);
        grid.add(new Label("Quantity:"), 0, 1);
        grid.add(number, 1, 1);
        grid.add(new Label("Notes:"), 0, 2);
        grid.add(notes, 1, 2);
        
        // Error Notifications
        Label errText1 = new Label("");
        errText1.setTextFill(Color.web("#F00"));
        Label errText2 = new Label("");
        errText2.setTextFill(Color.web("#F00"));
        Label errText3 = new Label("");
        errText3.setTextFill(Color.web("#F00"));
        
        grid.add(errText1, 2, 0);
        grid.add(errText2, 2, 1);
        grid.add(errText3, 2, 2);

        dialog.getDialogPane().setContent(grid);
        Platform.runLater(() -> name.requestFocus());
        
        Optional<ButtonType> result;
        boolean isRetry = true;
        while (isRetry) {
            errText1.setText(lastReport.getNAME_ERROR_MSG());
            errText2.setText(lastReport.getNUMBER_ERROR_MSG());
            result = dialog.showAndWait();
            if (result.get().getButtonData() == ButtonData.OK_DONE) {
                lastReport = InventoryManagement.checkAddEntry(name.getText(),
                                                               number.getText(),
                                                               number.
                                                               getText());
                if (lastReport.isOK()) {
                    InventoryManagement.addEntry(name.getText(), number.getText(),
                            notes.getText());
                    
                    isRetry = false;
                }
            } else {
                isRetry = false;
            }
        }
        lastReport = new EntryReport();
        updateTable();
    }
    
    /**
     * 
     */
    private void editEntryHandler() {
        Entry entry;
        
        Dialog dialog = new Dialog();
        dialog.setTitle("Edit Entry");
        dialog.setHeaderText("Edit Entry");
        ButtonType loginButtonType = new ButtonType("Edit", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType,
                                                       ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField name = new TextField();
        name.setPromptText("Name");
        TextField number = new TextField();
        number.setPromptText("Quantity");
        TextField notes = new TextField();
        notes.setPromptText("Notes");
        
        entry = TABLE.getSelectionModel().getSelectedItem();
        name.setText(entry.getName());
        number.setText(entry.getNumber());
        notes.setText(entry.getNotes());

        grid.add(new Label("Name:"), 0, 0);
        grid.add(name, 1, 0);
        grid.add(new Label("Quantity:"), 0, 1);
        grid.add(number, 1, 1);
        grid.add(new Label("Notes:"), 0, 2);
        grid.add(notes, 1, 2);

        dialog.getDialogPane().setContent(grid);
        Platform.runLater(() -> name.requestFocus());
        
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.get().getButtonData() == ButtonData.OK_DONE) {
            InventoryManagement.editEntry(entry, name.getText(),
                                          number.getText(), notes.getText());
        }
        updateTable();

    }
    
    /**
     * 
     */
    private void deleteEntryHandler() {
        InventoryManagement.deleteEntry(TABLE.getSelectionModel().
                                        getSelectedItem());
        updateTable();
    }
    
    /**
     * 
     * @param s 
     */
    private void filterHandler(String s) {
        filterText = s;
        updateTable();
    }
    
    /**
     * 
     * @param type 
     */
    private void filterChoiceHandler(String type) {
        InventoryManagement.setFilterCriterion(type);
        updateTable();
        
    }
    
    /**
     * 
     */
    private void newListHandler() {    
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        
        confirmation.setTitle("New List Confirmation");
        confirmation.setHeaderText("New List Confirmation");
        confirmation.setContentText("Current list will be " +
                                    "permanently deleted.");
        
        ButtonType confirmButtonType = new ButtonType("Proceed",
                                                      ButtonData.YES);
        confirmation.getButtonTypes().set(0, confirmButtonType);
        
        Optional<ButtonType> result = confirmation.showAndWait();
        if (result.isPresent() && result.get() == confirmButtonType) {
            InventoryManagement.clearInventory();
            updateTable();    
        }      
    }
    
    /**
     * 
     */
    private void openListHandler() {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open an inventory list");
        fileChooser.setInitialDirectory(new File(".\\"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("*.inv", "*.inv"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );
        File file = fileChooser.showOpenDialog(stage);
        if (file == null) return;
        InventoryManagement.loadInventory(file.getPath());
        updateTable();
        primaryStage.setTitle("Inventory Management - " + file.getName());
    }
    
    /**
     * 
     */
    private void saveListHandler() {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save an inventory list");
        fileChooser.setInitialDirectory(new File(".\\"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("*.inv", "*.inv"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );
        File file = fileChooser.showSaveDialog(stage);
        if (file == null) return;
        InventoryManagement.saveInventory(file.getPath());
        primaryStage.setTitle("Inventory Management - " + file.getName());
    }
    
    /**
     * 
     */
    private void aboutHandler() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Project 7");
        alert.setHeaderText("Inventory Management");
        alert.setContentText("Created by Caleb Davenport "
                + "and Roan Martin-Hayden\n\n"
                + "Spring 2016");
        alert.setGraphic(new ImageView(new Image("file:img/about.png", 100, 100, true, true)));

        alert.showAndWait();
    }
    
    /**
     * 
     * @param args 
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    private static Optional<ButtonType> duplicateDialogHandler(
            ObservableList<Entry> nameMatches,
            ObservableList<Entry> wholeMatches) {
        
        Dialog dialog;
        BorderPane borderPane;
        TableView<Entry> duplicatesTable;
        ObservableList<Entry> combinedMatches;
        
        dialog = new Dialog();
        dialog.setTitle("Duplcates Found");
        dialog.setHeaderText("Duplicates Found");
        
        ButtonType confirmButton = new ButtonType("Confirm", ButtonData.YES);
        ButtonType changeButton = new ButtonType("Change", ButtonData.BACK_PREVIOUS);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonData.SMALL_GAP);
        dialog.getDialogPane().getButtonTypes().addAll(cancelButton, changeButton, confirmButton);
        
        duplicatesTable = new TableView<>();
        duplicatesTable.getColumns().clear();

        // TODO: Add Identifier of Type (Requires wrapper class or more info...
//        TableColumn matchCol = new TableColumn("Match");
//        matchCol.setCellValueFactory(new PropertyValueFactory<>("match"));
        
        TableColumn nameCol = new TableColumn("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        
        TableColumn numberCol = new TableColumn("Number");
        numberCol.setCellValueFactory(new PropertyValueFactory<>("number"));
        
        TableColumn notesCol = new TableColumn("Notes");
        notesCol.setCellValueFactory(new PropertyValueFactory<>("notes"));
        
        combinedMatches = FXCollections.observableArrayList();
        combinedMatches.addAll(wholeMatches);
        combinedMatches.addAll(nameMatches);
        duplicatesTable.setItems(combinedMatches);
        duplicatesTable.getColumns().addAll(nameCol, numberCol, notesCol);
        duplicatesTable.setPlaceholder(new Label("No entries found"));
        duplicatesTable.maxHeightProperty().set(120);
        
        borderPane = new BorderPane();
        borderPane.setCenter(duplicatesTable);
        
        dialog.getDialogPane().setContent(borderPane);
        
        return dialog.showAndWait();
    }
    
    private void entryDialogHandler(boolean isEdit) {
        String actionStr;
        Entry editEntry;
        
        EntryReport report;
        
        actionStr = isEdit ? "Edit" : "Add";
        Dialog dialog = new Dialog();
        dialog.setTitle(actionStr + " Entry");
        dialog.setHeaderText(actionStr + " Entry");
        ButtonType loginButtonType = new ButtonType(actionStr, ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType,
                                                       ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField name = new TextField();
        name.setPromptText("Name");
        TextField number = new TextField();
        number.setPromptText("Quantity");
        TextField notes = new TextField();
        notes.setPromptText("Notes");

        if (isEdit) {
            editEntry = TABLE.getSelectionModel().getSelectedItem();
            name.setText(editEntry.getName());
            number.setText(editEntry.getNumber());
            notes.setText(editEntry.getNotes());
        } else {
            editEntry = new Entry();
        }
        
        grid.add(new Label("Name:"), 0, 0);
        grid.add(name, 1, 0);
        grid.add(new Label("Quantity:"), 0, 1);
        grid.add(number, 1, 1);
        grid.add(new Label("Notes:"), 0, 2);
        grid.add(notes, 1, 2);
        
        // Error Notifications
        Label errText1 = new Label("");
        errText1.setTextFill(Color.web("#F00"));
        Label errText2 = new Label("");
        errText2.setTextFill(Color.web("#F00"));
        Label errText3 = new Label("");
        errText3.setTextFill(Color.web("#F00"));
        
        grid.add(errText1, 2, 0);
        grid.add(errText2, 2, 1);
        grid.add(errText3, 2, 2);

        dialog.getDialogPane().setContent(grid);
        Platform.runLater(() -> name.requestFocus());
        
        Optional<ButtonType> entryResult;
        Optional<ButtonType> confirmResult;
        boolean isRetry = true;
        while (isRetry) {
            errText1.setText(lastReport.getNAME_ERROR_MSG());
            errText2.setText(lastReport.getNUMBER_ERROR_MSG());
            entryResult = dialog.showAndWait();
            System.out.println(entryResult.get());
            if (entryResult.get().getButtonData() == ButtonData.OK_DONE) {
                lastReport = InventoryManagement.checkAddEntry(name.getText(),
                                                               number.getText(),
                                                               number.
                                                               getText());
                if (lastReport.isOK()) {
                    if (isEdit) {
                        InventoryManagement.editEntry(editEntry, name.getText(),
                                                      number.getText(),
                                                      notes.getText());
                    } else {
                        InventoryManagement.addEntry(name.getText(),
                                                     number.getText(),
                                                     notes.getText());
                    }
                    isRetry = false;
                } else {
                    if (lastReport.isERROR_FLAG()) {
                        isRetry = true;
                    } else if (lastReport.isAnyMatches()) {
                        confirmResult = duplicateDialogHandler(lastReport.
                                getNAME_MATCHES(),
                                lastReport.getWHOLE_MATCHES());
                        if (null != confirmResult.get().getButtonData())
                            switch (confirmResult.get().getButtonData()) {
                            case YES:
                                if (isEdit) {
                                    InventoryManagement.editEntry(editEntry,
                                            name.getText(), number.getText(),
                                            notes.getText());
                                } else {
                                    InventoryManagement.addEntry(name.getText(),
                                            number.getText(),
                                            notes.getText());
                                }
                                isRetry = false;
                                break;
                            case BACK_PREVIOUS:
                                break;
                            case CANCEL_CLOSE:
                                isRetry = false;
                                break;
                            default:
                                isRetry = false;
                                break;
                        }
                    } else {
                        isRetry = false;
                    }
                }
            } else {
                isRetry = false;
            }
        }
        lastReport = new EntryReport();
        updateTable();
    }
}
