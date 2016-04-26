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
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class InventoryManagementGUI extends Application {
    private TableView<Entry> table = new TableView<Entry>();
    private ObservableList<Entry> entryList = FXCollections.observableArrayList(
    new Entry("Test", "12", "Note Test"));
    
    @Override
    public void start(Stage primaryStage) {
        MenuBar menuBar = new MenuBar();
        Menu menuFile = new Menu("File");
        menuBar.getMenus().addAll(menuFile);
        
        TableColumn nameCol = new TableColumn("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<Entry, String>("name"));
        TableColumn<Entry, String> numberCol = new TableColumn("Number");
        numberCol.setCellValueFactory(new PropertyValueFactory<Entry, String>("number"));
        TableColumn notesCol = new TableColumn("Notes");
        notesCol.setCellValueFactory(new PropertyValueFactory<Entry, String>("notes"));
        
        table.setItems(entryList);
        table.getColumns().addAll(nameCol, numberCol, notesCol);
        
        VBox leftBox = new VBox();
        leftBox.getChildren().addAll(new Button("Hello!"), new Button("Greetings!"), new Button("Welcome to Earth!"));
        
        HBox bottomBox = new HBox();
        bottomBox.getChildren().addAll(new Button("Hello!"), new Button("Greetings!"), new Button("Welcome to Earth!"));
        
        VBox rightBox = new VBox();
        rightBox.getChildren().addAll(new Button("Hello!"), new Button("Greetings!"), new Button("Welcome to Earth!"));
        
        BorderPane root = new BorderPane();
        root.setTop(menuBar);
        root.setCenter(table);
        root.setLeft(leftBox);
        root.setRight(rightBox);
        root.setBottom(bottomBox);
        
        Scene scene = new Scene(root, 800, 400);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
    
}
