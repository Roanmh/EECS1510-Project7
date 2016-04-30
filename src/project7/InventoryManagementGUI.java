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
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

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
                addEntryHandler();
            });

            MenuItem editEntry = new MenuItem("Edit Entry");
            editEntry.setOnAction((ActionEvent e) -> {
                editEntryHandler();
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
        TableColumn<Entry, String> numberCol = new TableColumn("Number");
        numberCol.setCellValueFactory(new PropertyValueFactory<>("number"));
        TableColumn notesCol = new TableColumn("Notes");
        notesCol.setCellValueFactory(new PropertyValueFactory<>("notes"));
        
        TABLE.setItems(InventoryManagement.filteredEntries(filterText));
        TABLE.getColumns().addAll(nameCol, numberCol, notesCol);
        TABLE.setPlaceholder(new Label("No entries found"));
    }
    
    /**
     * 
     */
    private void setupSidePanel() {
        Button addEntry = new Button();
        addEntry.setOnAction((ActionEvent e) -> {
            addEntryHandler();
        });

        Button editEntry = new Button();
        editEntry.setOnAction((ActionEvent e) -> {
            editEntryHandler();
        });

        Button deleteEntry = new Button();
        deleteEntry.setOnAction((ActionEvent e) -> {
            deleteEntryHandler();
        });
        
        addEntry.setMaxWidth(Double.MAX_VALUE);
        editEntry.setMaxWidth(Double.MAX_VALUE);
        deleteEntry.setMaxWidth(Double.MAX_VALUE);
        
        Image imgAdd = new Image("file:img/green_plus.png");
        addEntry.setGraphic(new ImageView(imgAdd));
        Image imgEdit = new Image("file:img/pencil.png");
        editEntry.setGraphic(new ImageView(imgEdit));
        Image imgDel = new Image("file:img/red_x.png");
        deleteEntry.setGraphic(new ImageView(imgDel));
        
        VBox vBoxButtons = new VBox();
        vBoxButtons.setSpacing(5);
        vBoxButtons.getChildren().addAll(addEntry, editEntry, deleteEntry);
        
        RIGHT_BOX.getChildren().add(vBoxButtons);
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
        BorderPane.setMargin(BOTTOM_BOX, new Insets(5, 0, 5, 10));
    }
    
    /**
     * 
     */
    private void addEntryHandler() {
        EntryReport report;
        
        System.out.println("addEntry");
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
            errText1.setText(lastReport.getNAME_ERROR_MSSG());
            errText2.setText(lastReport.getNUMBER_ERROR_MSSG());
            result = dialog.showAndWait();
            System.out.println(result.get());
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
        
        System.out.println("editEntry");
        
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
        System.out.println(result.get());
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
        System.out.println("deleteEntry");
        
        InventoryManagement.deleteEntry(TABLE.getSelectionModel().
                                        getSelectedItem());
        updateTable();
    }
    
    /**
     * 
     * @param s 
     */
    private void filterHandler(String s) {
        System.out.println("FILTER!");
        filterText = s;
        updateTable();
    }
    
    /**
     * 
     * @param type 
     */
    private void filterChoiceHandler(String type) {
        System.out.println("CHANGED");
        
        InventoryManagement.setFilterCriterion(type);
        updateTable();
        
    }
    
    /**
     * 
     */
    private void newListHandler() {
        System.out.println("newList");
                
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
        System.out.println("openList");
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
        primaryStage.setTitle("Invetory Management - " + file.getName());
    }
    
    /**
     * 
     */
    private void saveListHandler() {
        System.out.println("saveList");
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
        primaryStage.setTitle("Invetory Management - " + file.getName());
    }
    
    /**
     * 
     */
    private void aboutHandler() {
        System.out.println("About");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Project 7");
        alert.setHeaderText("Inventory Management");
        alert.setContentText("Created by Caleb Davenport "
                + "and Roan Martin-Hayden\n"
                + "Spring 2016");

        alert.showAndWait();
    }
    
    /**
     * 
     * @param args 
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    private static void duplicateDialogHandler() {
        Dialog dialog = new Dialog();
        dialog.setTitle("Add Entry");
        dialog.setHeaderText("Add Entry");
        ButtonType loginButtonType = new ButtonType("Add", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

    }
}
