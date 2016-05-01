/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.calebdavenport.project7;

import javafx.collections.ObservableList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import project7.Entry;
import project7.EntryReport;
import project7.InventoryManagement;
import static org.junit.Assert.*;

/**
 *
 * @author rmartin-
 */
public class InventoryManagementTest {
    
    public InventoryManagementTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of entryIndex method, of class InventoryManagement.
     */
    @Test
    public void testFindEntry() {
        System.out.println("findEntry");
        String name = "";
        int expResult = 0;
        int result = InventoryManagement.entryIndex(name);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of checkAddEntry method, of class InventoryManagement.
     */
    @Test
    public void testCheckAddEntry() {
        System.out.println("checkAddEntry");
        String name = "";
        String number = "";
        String notes = "";
        EntryReport expResult = null;
        EntryReport result
                = InventoryManagement.checkAddEntry(name, number, notes);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addEntry method, of class InventoryManagement.
     */
    @Test
    public void testAddEntry() {
        System.out.println("addEntry");
        String name = "";
        String number = "";
        String notes = "";
        InventoryManagement.addEntry(name, number, notes);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of editEntry method, of class InventoryManagement.
     */
    @Test
    public void testEditEntry() {
        System.out.println("editEntry");
        Entry replacedEntry = null;
        String name = "";
        String number = "";
        String notes = "";
        InventoryManagement.editEntry(replacedEntry, name, number, notes);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteEntry method, of class InventoryManagement.
     */
    @Test
    public void testDeleteEntry_String() {
        System.out.println("deleteEntry");
        String name = "";
        String expResult = "";
        String result = InventoryManagement.deleteEntry(name);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteEntry method, of class InventoryManagement.
     */
    @Test
    public void testDeleteEntry_int() {
        System.out.println("deleteEntry");
        int index = 0;
        String expResult = "";
        String result = InventoryManagement.deleteEntry(index);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteEntry method, of class InventoryManagement.
     */
    @Test
    public void testDeleteEntry_Entry() {
        System.out.println("deleteEntry");
        Entry entry = null;
        String expResult = "";
        String result = InventoryManagement.deleteEntry(entry);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loadInventory method, of class InventoryManagement.
     */
    @Test
    public void testLoadInventory() {
        System.out.println("loadInventory");
        String pathStr = "";
        String expResult = "";
        String result = InventoryManagement.loadInventory(pathStr);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of saveInventory method, of class InventoryManagement.
     */
    @Test
    public void testSaveInventory() {
        System.out.println("saveInventory");
        String pathStr = "";
        String expResult = "";
        String result = InventoryManagement.saveInventory(pathStr);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of clearInventory method, of class InventoryManagement.
     */
    @Test
    public void testClearInventory() {
        System.out.println("clearInventory");
        String expResult = "";
        String result = InventoryManagement.clearInventory();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of checkNameValidity method, of class InventoryManagement.
     */
    @Test
    public void testCheckNameValidity() {
        System.out.println("checkNameValidity");
        String name = "";
        String expResult = "";
        String result = InventoryManagement.checkNameValidity(name);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of checkNumberValidty method, of class InventoryManagement.
     */
    @Test
    public void testCheckNumberValidty() {
        System.out.println("checkNumberValidty");
        String number = "";
        String expResult = "";
        String result = InventoryManagement.checkNumberValidty(number);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of customEquals method, of class InventoryManagement.
     */
    @Test
    public void testCustomEquals() {
        System.out.println("customEquals");
        String name1 = "";
        String name2 = "";
        boolean expResult = false;
        boolean result = InventoryManagement.customEquals(name1, name2);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of customContains method, of class InventoryManagement.
     */
    @Test
    public void testCustomContains() {
        System.out.println("customContains");
        String testStr = "";
        String filterStr = "";
        boolean expResult = false;
        boolean result = InventoryManagement.customContains(testStr, filterStr);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of filteredEntries method, of class InventoryManagement.
     */
    @Test
    public void testFilteredEntries() {
        System.out.println("filteredEntries");
        String filtStr = "";
        ObservableList<Entry> expResult = null;
        ObservableList<Entry> result
                = InventoryManagement.filteredEntries(filtStr);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFilterCriterion method, of class InventoryManagement.
     */
    @Test
    public void testGetFilterCriterion() {
        System.out.println("getFilterCriterion");
        String expResult = "";
        String result = InventoryManagement.getFilterCriterion();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setFilterCriterion method, of class InventoryManagement.
     */
    @Test
    public void testSetFilterCriterion() {
        System.out.println("setFilterCriterion");
        String filterCriterion = "";
        InventoryManagement.setFilterCriterion(filterCriterion);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getInvLoc method, of class InventoryManagement.
     */
    @Test
    public void testGetInvLoc() {
        System.out.println("getInvLoc");
        String expResult = "";
        String result = InventoryManagement.getInvLoc();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
