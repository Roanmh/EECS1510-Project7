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
    private final static TableView<Entry> TABLE = new TableView<>();
    private final static MenuBar MENU_BAR = new MenuBar();
    private final static HBox BOTTOM_BOX = new HBox();
    private final static VBox RIGHT_BOX = new VBox();
    private final static BorderPane ROOT = new BorderPane();
    
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
        BorderPane.setMargin(TABLE, new Insets(0, 10, 0, 10));
        BorderPane.setMargin(MENU_BAR, new Insets(0, 0, 10, 0));
        ROOT.setTop(MENU_BAR);
        ROOT.setCenter(TABLE);
        ROOT.setRight(RIGHT_BOX);
        ROOT.setBottom(BOTTOM_BOX);
        
        Scene scene = new Scene(ROOT, 800, 400);
        
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
        
        MENU_BAR.getMenus().addAll(menuFile, menuEdit, menuHelp);
    }
    private void initializeTable() {
        TableColumn nameCol = new TableColumn("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Entry, String> numberCol = new TableColumn("Number");
        numberCol.setCellValueFactory(new PropertyValueFactory<>("number"));
        TableColumn notesCol = new TableColumn("Notes");
        notesCol.setCellValueFactory(new PropertyValueFactory<>("notes"));
        
        TABLE.setItems(InventoryManagement.entryList);
        TABLE.getColumns().addAll(nameCol, numberCol, notesCol);
    }
    private void setupSidePanel() {
        RIGHT_BOX.getChildren().addAll(new Button("Add Entry"), new Button("Edit Entry"), new Button("Delete Entry"));
    }
    private void setupBottomFilter() {
        TextField t = new TextField();
        t.setPromptText("Filter");
        t.setPrefWidth(500); //TODO: CHANGE TO WIDTH OF TABLE
        ComboBox c = new ComboBox();
        c.getItems().addAll(
                "Name",
                "Notes"
        );
        c.setValue("Name");
        BOTTOM_BOX.getChildren().addAll(t, c);
    }
    private void setupMargins() {
        BorderPane.setMargin(TABLE, new Insets(0, 10, 10, 0));
        BorderPane.setMargin(MENU_BAR, new Insets(0, 0, 10, 0));
        BorderPane.setMargin(RIGHT_BOX, new Insets(0, 10, 0, 0));
        BorderPane.setMargin(BOTTOM_BOX, new Insets(5, 10, 5, 10));
    }
    
    private void addEntryHandler() {
        
    }
    private void editEntryHandler() {
        
    }
    private void deleteEntryHandler() {
        
    }
    private void filterHandler() {
        
    }
    private void newListHandler() {
        
    }
    private void openListHandler() {
        
    }
    private void saveListHandler() {
        
    }
    public static void main(String[] args) {
        launch(args);
    }
    
}
