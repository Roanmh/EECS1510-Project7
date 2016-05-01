/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.calebdavenport.project7;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import project7.Entry;
import static org.junit.Assert.*;

/**
 *
 * @author rmartin-
 */
public class EntryTest {
    
    public EntryTest() {
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
     * Test of name method, of class Entry.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        Entry instance = new Entry();
        String expResult = "";
        String result = instance.name();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of number method, of class Entry.
     */
    @Test
    public void testGetNumber() {
        System.out.println("getNumber");
        Entry instance = new Entry();
        String expResult = "";
        String result = instance.number();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of notes method, of class Entry.
     */
    @Test
    public void testGetNotes() {
        System.out.println("getNotes");
        Entry instance = new Entry();
        String expResult = "";
        String result = instance.notes();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of exists method, of class Entry.
     */
    @Test
    public void testExists() {
        System.out.println("exists");
        Entry instance = new Entry();
        boolean expResult = false;
        boolean result = instance.exists();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class Entry.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Entry instance = new Entry();
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
