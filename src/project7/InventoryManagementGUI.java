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

import javafx.application.Application;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class InventoryManagementGUI extends Application {
    private final static TableView<Entry> table = new TableView<Entry>();
    private final static MenuBar menuBar = new MenuBar();
    private final static HBox bottomBox = new HBox();
    private final static VBox rightBox = new VBox();
    private final static BorderPane root = new BorderPane();
    
    @Override
    public void start(Stage primaryStage) {
        InventoryManagement.entryList.add(new Entry());
        InventoryManagement.entryList.add(new Entry("ni", "6", "ksjhdkas"));
        
        addMenus();
        initializeTable();
        setupSidePanel();
        setupBottomFilter();
        setupMargins();
        
        //table.setPadding(new Insets(10));
        BorderPane.setMargin(table, new Insets(0, 10, 0, 10));
        BorderPane.setMargin(menuBar, new Insets(0, 0, 10, 0));
        root.setTop(menuBar);
        root.setCenter(table);
        root.setRight(rightBox);
        root.setBottom(bottomBox);
        
        Scene scene = new Scene(root, 800, 400);
        
        primaryStage.setTitle("Inventory Management");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private void addMenus() {
        Menu menuFile = new Menu("File");
        {
            MenuItem newList = new MenuItem("New List");
            newList.setAccelerator(KeyCombination.keyCombination("Ctrl+N"));

            MenuItem openList = new MenuItem("Open List");
            openList.setAccelerator(KeyCombination.keyCombination("Ctrl+O"));

            MenuItem saveList = new MenuItem("Save List");
            saveList.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));

            MenuItem exit = new MenuItem("Exit");
            exit.setAccelerator(KeyCombination.keyCombination("Ctrl+Q"));

            menuFile.getItems().addAll(newList, openList, saveList, new SeparatorMenuItem(), exit);
        }
        
        Menu menuEdit = new Menu("Edit");
        {
            MenuItem addEntry = new MenuItem("Add Entry");

            MenuItem editEntry = new MenuItem("Edit Entry");

            MenuItem deleteEntry = new MenuItem("Delete Entry");

            menuEdit.getItems().addAll(addEntry, editEntry, deleteEntry);
        }
        
        Menu menuHelp = new Menu("Help");
        {
            MenuItem about = new MenuItem("About");

            menuHelp.getItems().addAll(about);
        }
        
        menuBar.getMenus().addAll(menuFile, menuEdit, menuHelp);
    }
    private void initializeTable() {
        TableColumn nameCol = new TableColumn("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Entry, String> numberCol = new TableColumn("Number");
        numberCol.setCellValueFactory(new PropertyValueFactory<>("number"));
        TableColumn notesCol = new TableColumn("Notes");
        notesCol.setCellValueFactory(new PropertyValueFactory<>("notes"));
        
        table.setItems(InventoryManagement.entryList);
        table.getColumns().addAll(nameCol, numberCol, notesCol);  
    }
    private void setupSidePanel() {
        rightBox.getChildren().addAll(new Button("Add Entry"), new Button("Edit Entry"), new Button("Delete Entry"));
    }
    private void setupBottomFilter() {
        TextField t = new TextField();
        t.setPromptText("Filter");
        bottomBox.getChildren().addAll(t);
    }
    private void setupMargins() {
        BorderPane.setMargin(table, new Insets(0, 10, 10, 0));
        BorderPane.setMargin(menuBar, new Insets(0, 0, 10, 0));
        BorderPane.setMargin(rightBox, new Insets(0, 10, 0, 0));
        BorderPane.setMargin(bottomBox, new Insets(5, 10, 5, 10));
    }
    public static void main(String[] args) {
        launch(args);
    }
    
}
