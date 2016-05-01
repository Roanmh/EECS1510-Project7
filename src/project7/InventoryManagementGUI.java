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
* @(1.0)Inventory.java 1.0 4/30/2016 [Roan Martin-Hayden, Caleb Davenport]
*
* Copyright (c) 2016 Roan Martin-Hayden, Caleb Davenport. All Rights Reserved
*/
package project7;

import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class InventoryManagementGUI extends Application {

    private static final JFXTreeTableView<Entry> TABLE = new JFXTreeTableView<>();
    private static final MenuBar MENU_BAR = new MenuBar();
    private static final HBox BOTTOM_BOX = new HBox();
    private static final VBox RIGHT_BOX = new VBox();
    private static final BorderPane ROOT = new BorderPane();
    private static String filteredText = "";
    private static Stage primaryStage = null;
    private static EntryReport lastReport = new EntryReport();

    /**
     * Initializes the GUI
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) {
        InventoryManagementGUI.primaryStage = primaryStage;
        Scene scene;

        addMenus();
        updateTable();
        setupSidePanel();
        setupMargins();

        BorderPane.setMargin(TABLE, new Insets(0, 10, 0, 10));
        BorderPane.setMargin(MENU_BAR, new Insets(0, 0, 10, 0));
        ROOT.setTop(MENU_BAR);
        ROOT.setCenter(TABLE);
        ROOT.setRight(RIGHT_BOX);
        ROOT.setBottom(BOTTOM_BOX);

        scene = new Scene(ROOT, 800, 400);
        
        InventoryManagementGUI.primaryStage.setTitle("Inventory Management");
        InventoryManagementGUI.primaryStage.setScene(scene);
        InventoryManagementGUI.primaryStage.show();
        setupBottomFilter();
    }

    /**
     * Adds the menus to the menu bar
     */
    private void addMenus() {
        Menu menuFile;
        MenuItem menuItemNew;
        MenuItem menuItemOpen;
        MenuItem menuItemSave;
        MenuItem menuItemExit;
        Menu menuEdit;
        MenuItem menuItemAddEntry;
        MenuItem menuItemEditEntry;
        MenuItem menuItemDeleteEntry;
        KeyCodeCombination keyCodeDelete;
        Menu menuHelp;
        MenuItem menuItemAbout;

        menuFile = new Menu("File");
        menuItemNew = new MenuItem("New List");
        menuItemNew.setAccelerator(KeyCombination.keyCombination("Ctrl+N"));
        menuItemNew.setOnAction((ActionEvent e) -> {
            handleNewList();
        });
        menuItemOpen = new MenuItem("Open List");
        menuItemOpen.setAccelerator(KeyCombination.keyCombination("Ctrl+O"));
        menuItemOpen.setOnAction((ActionEvent e) -> {
            handleOpenList();
        });
        menuItemSave = new MenuItem("Save List");
        menuItemSave.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
        menuItemSave.setOnAction((ActionEvent e) -> {
            handleSaveList();
        });
        menuItemExit = new MenuItem("Exit");
        menuItemExit.setAccelerator(KeyCombination.keyCombination("Ctrl+Q"));
        menuItemExit.setOnAction((ActionEvent e) -> {
            System.exit(0);
        });
        menuFile.getItems().addAll(menuItemNew, menuItemOpen, menuItemSave,
                new SeparatorMenuItem(), menuItemExit);
        
        menuEdit = new Menu("Edit");
        menuItemAddEntry = new MenuItem("Add Entry");
        menuItemAddEntry.setOnAction((ActionEvent e) -> {
            handleAddEntry();
        });
        menuItemEditEntry = new MenuItem("Edit Entry");
        menuItemEditEntry.setOnAction((ActionEvent e) -> {
            handleEditEntry();
        });
        menuItemDeleteEntry = new MenuItem("Delete Entry");
        keyCodeDelete = new KeyCodeCombination(KeyCode.DELETE);
        menuItemDeleteEntry.setAccelerator(keyCodeDelete);
        menuItemDeleteEntry.setOnAction((ActionEvent e) -> {
            handleDeleteEntry();
        });
        menuEdit.getItems().addAll(menuItemAddEntry, menuItemEditEntry,
                                   menuItemDeleteEntry);
        
        menuHelp = new Menu("Help");
        menuItemAbout = new MenuItem("About");
        menuItemAbout.setOnAction((ActionEvent e) -> {
            handleAboutAlert();
        });
        menuHelp.getItems().addAll(menuItemAbout);

        MENU_BAR.getMenus().addAll(menuFile, menuEdit, menuHelp);
    }

    /**
     * Updates the table with the current list of entries
     */
    private void updateTable() {
        TreeTableColumn nameCol;
        TreeTableColumn numberCol;
        TreeTableColumn notesCol;
        
        TABLE.getColumns().clear();
        nameCol = new TreeTableColumn("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        numberCol = new TreeTableColumn("#");
        numberCol.setCellValueFactory(new PropertyValueFactory<>("number"));
        notesCol = new TreeTableColumn("Notes");
        notesCol.setCellValueFactory(new PropertyValueFactory<>("notes"));

        nameCol.minWidthProperty().set(80);
        nameCol.prefWidthProperty().bind(TABLE.widthProperty()
                .multiply(0.1));
        numberCol.prefWidthProperty().bind(nameCol.widthProperty()
                .multiply(0.5));
        notesCol.prefWidthProperty().bind(TABLE.widthProperty()
                .subtract(nameCol.widthProperty().multiply(1.5))
                .subtract(2));

        TABLE.setItems(Inventory.filteredEntries(filteredText));
        TABLE.getColumns().addAll(nameCol, numberCol, notesCol);
        TABLE.setPlaceholder(new Label("No entries found"));
    }

    /**
     * Initializes the panel on the right
     */
    private void setupSidePanel() {
        Button buttonAddEntry = new Button("Add");
        Button buttonEditEntry = new Button("Edit");
        Button buttonDeleteEntry = new Button("Delete");
        Image imgAdd = new Image("file:img/green_plus.png",
                                 16, 16, true, false);
        Image imgEdit = new Image("file:img/pencil.png",
                                  16, 16, true, true);
        Image imgDel = new Image("file:img/red_x.png",
                                 16, 16, true, false);

        buttonAddEntry.setOnAction((ActionEvent e) -> {
            handleAddEntry();
        });
        buttonEditEntry.setOnAction((ActionEvent e) -> {
            handleEditEntry();
        });
        buttonDeleteEntry.setOnAction((ActionEvent e) -> {
            handleDeleteEntry();
        });

        buttonAddEntry.maxWidthProperty().set(Double.MAX_VALUE);
        buttonEditEntry.maxWidthProperty().set(Double.MAX_VALUE);
        buttonDeleteEntry.maxWidthProperty().set(Double.MAX_VALUE);

        buttonAddEntry.setGraphic(new ImageView(imgAdd));
        buttonEditEntry.setGraphic(new ImageView(imgEdit));
        buttonDeleteEntry.setGraphic(new ImageView(imgDel));

        RIGHT_BOX.setSpacing(5);
        RIGHT_BOX.getChildren().addAll(buttonAddEntry, buttonEditEntry,
                                       buttonDeleteEntry);
    }

    /**
     * Initializes the filter on the bottom of the GUI
     */
    private void setupBottomFilter() {
        TextField textFieldFilter = new TextField();
        ComboBox comboBox = new ComboBox();
        
        textFieldFilter.setPromptText("Filter");
        textFieldFilter.prefWidthProperty().bind(TABLE.widthProperty());
        textFieldFilter.setOnKeyReleased((KeyEvent e) -> {
            handleFilter(textFieldFilter.getText());
        });

        comboBox.getItems().addAll("Name", "Notes");
        comboBox.setValue("Name");
        comboBox.setMaxWidth(Double.MAX_VALUE);
        comboBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String oldStr,
                                String newStr) {
                handleFilterCriteria(newStr);
            }
        });

        BOTTOM_BOX.setSpacing(5);
        BOTTOM_BOX.getChildren().addAll(textFieldFilter, comboBox);
    }

    /**
     * Sets up the margins between all the elements in the BorderPane
     */
    private void setupMargins() {
        BorderPane.setMargin(TABLE, new Insets(0, 10, 10, 0));
        BorderPane.setMargin(MENU_BAR, new Insets(0, 0, 10, 0));
        BorderPane.setMargin(RIGHT_BOX, new Insets(0, 10, 0, 0));
        BorderPane.setMargin(BOTTOM_BOX, new Insets(5, 10, 5, 10));
    }

    /**
     * Handles adding an entry
     */
    private void handleAddEntry() {
        handleEntryDialog(false);
    }

    /**
     * Handles editing an entry
     */
    private void handleEditEntry() {
        handleEntryDialog(true);
    }

    /**
     * Handles deleting an entry
     */
    private void handleDeleteEntry() {
        Inventory.deleteEntry(TABLE.getSelectionModel().
                getSelectedItem());
        updateTable();
    }

    /**
     * Handles a change to the filter box
     * @param s
     */
    private void handleFilter(String s) {
        filteredText = s;
        updateTable();
    }

    /**
     * Handles a change to the filter criteria
     * @param type
     */
    private void handleFilterCriteria(String type) {
        Inventory.setFilterCriterion(type);
        updateTable();
    }

    /**
     * Handles a new list request
     */
    private void handleNewList() {
        Alert confirmation;
        ButtonType confirmButtonType;
        Optional<ButtonType> result;

        confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmButtonType = new ButtonType("Proceed", ButtonData.YES);
        confirmation.setTitle("New List Confirmation");
        confirmation.setHeaderText("New List Confirmation");
        confirmation.setContentText("Current list will be "
                + "permanently deleted.");

        confirmation.getButtonTypes().set(0, confirmButtonType);

        result = confirmation.showAndWait();
        if (result.isPresent() && result.get() == confirmButtonType) {
            Inventory.clearInventory();
            updateTable();
        }
    }

    /**
     * Handles opening a file
     */
    private void handleOpenList() {
        Stage stage;
        FileChooser fileChooser;
        File file;
        
        stage = new Stage();
        fileChooser = new FileChooser();
        fileChooser.setTitle("Open an inventory list");
        fileChooser.setInitialDirectory(new File(".\\"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("*.inv", "*.inv"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        file = fileChooser.showOpenDialog(stage);
        if (file == null) return;
        Inventory.loadInventory(file.getPath());
        updateTable();
        primaryStage.setTitle("Inventory Management - " + file.getName());
    }

    /**
     * Handles saving a file
     */
    private void handleSaveList() {
        Stage stage;
        FileChooser fileChooser;
        File file;
        
        stage = new Stage();
        fileChooser = new FileChooser();
        fileChooser.setTitle("Save an inventory list");
        fileChooser.setInitialDirectory(new File(".\\"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("*.inv", "*.inv"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        file = fileChooser.showSaveDialog(stage);
        if (file == null) return;
        Inventory.saveInventory(file.getPath());
        primaryStage.setTitle("Inventory Management - " + file.getName());
    }

    /**
     * Handles the About alert
     */
    private void handleAboutAlert() {
        Alert alert;
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Project 7");
        alert.setHeaderText("Inventory Management");
        alert.setContentText("Created by:\n\t"
                             + "Caleb Davenport\t\t"
                             + "(github.com/caleb-davenport)\n\t"
                             + "Roan Martin-Hayden\t(github.com/roanmh)\n\n"
                             + "Spring 2016");
        alert.setGraphic(new ImageView(new Image("file:img/about.png",
                                                 100, 100, true, true)));
        alert.showAndWait();
    }
    
    /**
     * Give the user a choice when a duplicate entry is found
     * @param nameMatches
     * @param wholeMatches
     * @return The result of whether the user clicked Continue, Edit or Cancel
     */
    private static Optional<ButtonType> duplicateResult(
                                        ObservableList<Entry> nameMatches,
                                        ObservableList<Entry> wholeMatches) {

        Dialog dialog;
        BorderPane borderPane;
        TableView<Entry> duplicatesTable;
        ObservableList<Entry> combinedMatches;
        ButtonType confirmButton;
        ButtonType changeButton;
        ButtonType cancelButton;
        TableColumn nameCol;
        TableColumn numberCol;
        TableColumn notesCol;

        dialog = new Dialog();
        dialog.setTitle("Duplcates Found");
        dialog.setHeaderText("Duplicates Found");

        confirmButton = new ButtonType("Continue", ButtonData.OTHER);
        changeButton = new ButtonType("Edit", ButtonData.OTHER);
        cancelButton = new ButtonType("Cancel", ButtonData.OTHER);
        dialog.getDialogPane().getButtonTypes().addAll(cancelButton,
                                                       changeButton,
                                                       confirmButton);

        duplicatesTable = new TableView<>();
        duplicatesTable.getColumns().clear();

        nameCol = new TableColumn("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        numberCol = new TableColumn("Number");
        numberCol.setCellValueFactory(new PropertyValueFactory<>("number"));
        notesCol = new TableColumn("Notes");
        notesCol.setCellValueFactory(new PropertyValueFactory<>("notes"));

        combinedMatches = FXCollections.observableArrayList();
        combinedMatches.addAll(wholeMatches);
        combinedMatches.addAll(nameMatches);
        duplicatesTable.setItems(combinedMatches);
        duplicatesTable.getColumns().addAll(nameCol, numberCol, notesCol);
        duplicatesTable.maxHeightProperty().set(120);

        borderPane = new BorderPane();
        borderPane.setCenter(duplicatesTable);

        dialog.getDialogPane().setContent(borderPane);

        return dialog.showAndWait();
    }

    /**
     * Handles the dialog box for editing or adding an entry
     * @param isEdit Determines if the entry is for an edit or an addition
     */
    private void handleEntryDialog(boolean isEdit) {
        String actionString;
        Entry editableEntry;
        Dialog dialog;
        ButtonType confirmButtonType;
        GridPane grid;
        TextField name;
        TextField number;
        TextField notes;
        Label errTextName;
        Label errTextNumber;
        Optional<ButtonType> entryResult;
        Optional<ButtonType> confirmResult;

        actionString = isEdit ? "Edit" : "Add";
        dialog = new Dialog();
        dialog.setTitle(actionString + " Entry");
        dialog.setHeaderText(actionString + " Entry");
        confirmButtonType = new ButtonType(actionString, ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType,
                                                       ButtonType.CANCEL);

        grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        name = new TextField();
        name.setPromptText("Name");
        number = new TextField();
        number.setPromptText("Quantity");
        notes = new TextField();
        notes.setPromptText("Notes");

        if (isEdit) {
            editableEntry = TABLE.getSelectionModel().getSelectedItem();
            name.setText(editableEntry.name());
            number.setText(editableEntry.number());
            notes.setText(editableEntry.notes());
        } else {
            editableEntry = new Entry();
        }
        
        errTextName = new Label("");
        errTextName.setTextFill(Color.web("#F00"));
        errTextNumber = new Label("");
        errTextNumber.setTextFill(Color.web("#F00"));

        grid.add(new Label("Name:"), 0, 0);
        grid.add(name, 1, 0);
        grid.add(new Label("Quantity:"), 0, 1);
        grid.add(number, 1, 1);
        grid.add(new Label("Notes:"), 0, 2);
        grid.add(notes, 1, 2);
        grid.add(errTextName, 2, 0);
        grid.add(errTextNumber, 2, 1);
        
        dialog.getDialogPane().setContent(grid);
        Platform.runLater(() -> name.requestFocus());

        boolean isRetry = true;
        while (isRetry) {
            errTextName.setText(lastReport.nameErrorMessage());
            errTextNumber.setText(lastReport.numberErrorMessage());
            entryResult = dialog.showAndWait();
            if (entryResult.get().getButtonData() == ButtonData.OK_DONE) {
                lastReport = Inventory.addEntryReport(name.getText(),
                                                     number.getText(),
                                                     notes.getText());
                if (lastReport.okayStatus()) {
                    if (isEdit) {
                        Inventory.editEntry(editableEntry,
                                            name.getText(),
                                            number.getText(),
                                            notes.getText());
                    } else {
                        Inventory.addEntry(name.getText(),
                                           number.getText(),
                                           notes.getText());
                    }
                    isRetry = false;
                } else if (lastReport.errorFlag()) {
                    isRetry = true;
                } else if (lastReport.anyMatches()) {
                    confirmResult = duplicateResult(
                                            lastReport.matchesByName(),
                                            lastReport.matchesInWhole());
                    if (null != confirmResult.get().getText()) {
                        switch (confirmResult.get().getText()) {
                            case "Continue":
                                if (isEdit) {
                                    Inventory.editEntry(editableEntry,
                                                        name.getText(),
                                                        number.getText(),
                                                        notes.getText());
                                } else {
                                    Inventory.addEntry(name.getText(),
                                                       number.getText(),
                                                       notes.getText());
                                }
                                isRetry = false;
                                break;
                            case "Edit":
                                break;
                            case "Cancel":
                            default:
                                isRetry = false;
                                break;
                        }
                    }
                } else isRetry = false;
            } else isRetry = false;
        }
        lastReport = new EntryReport();
        updateTable();
    }

    /**
     * Entry point for running the program
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

}
