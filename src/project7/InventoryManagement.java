/*
*
* Project 6: Inventory Management
* Caleb Davenport
* EECS 1510-091: Dr. Ledgard
*
* Description:
* Main class that handles user input
* and provides the functions based on the input.
*
*/

package inventorymanagement;

import java.io.*;
import java.util.*;

public class InventoryManagement {
    public static final int MAX_ENTRIES = 200;
    public static final String INV_LOCATION = "Inventory\\Inventory.txt";
    static Entry[] entryList = new Entry[MAX_ENTRIES];
    static Scanner input = new Scanner(System.in);
    
    public static void main(String[] args) throws Exception {
        File f = new File(INV_LOCATION);
        Scanner fileInput = new Scanner(f);
        System.out.println("Codes are entered as 1 to 8 characters.");
        for (int i = 0; i < MAX_ENTRIES; ++i) {
            try {
                String nextLine = fileInput.nextLine();
                entryList[i] = new Entry(nextLine);
            } catch (Exception e) {
                entryList[i] = new Entry();
            }
        }
        while (true) {
            System.out.println("Use \"e\" for enter, \"f\" for find, "
                    + "\"l\" for list, \"q\" for quit.");
            System.out.print("Command: ");
            switch (input.next()) {
                case "e":
                    enter();
                    break;
                case "f":
                    find();
                    break;
                case "l":
                    list();
                    break;
                case "q":
                    System.exit(0);
                default:
                    break;
            }
        }
    }
    private static void list() {
        for (int i = 0; i < MAX_ENTRIES; ++i) {
            if (entryList[i].exists()) System.out.println(entryList[i]);
        }
    }
    private static void find() {
        boolean found = false;
        System.out.println("Enter the code that you are looking for: ");
        String n = input.next();
        for (int i = 0; i < MAX_ENTRIES; ++i) {
            if (entryList[i].name().toLowerCase().contains(n.toLowerCase())) {
                found = true;
                System.out.println(entryList[i]);
            }
        }
        if (!found) System.out.println("No entry with code: " + n);
    }
    public static void enter() throws Exception {
        String name, number, notes;
        System.out.print("Enter name: ");
        name = input.next();
        System.out.print("Enter quantity: ");
        number = input.next();
        System.out.print("Enter notes: ");
        notes = input.next();
        for (int i = 0; i < MAX_ENTRIES; ++i) {
            if (!entryList[i].exists()){
                entryList[i] = new Entry(name, number, notes);
                break;
            }
        }
        try (PrintStream P = new PrintStream(INV_LOCATION)) {
            for (int i=0; i < MAX_ENTRIES; i++) {
                if (entryList[i].exists()) {
                    P.println(entryList[i].name() + "\t" +
                            entryList[i].number() + "\t" +
                            entryList[i].notes());
                }
            }
            P.close();
            System.out.println("Inventory stored.");
        }
    }
}
