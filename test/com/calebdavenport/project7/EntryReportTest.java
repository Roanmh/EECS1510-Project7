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
import static org.junit.Assert.*;

/**
 *
 * @author rmartin-
 */
public class EntryReportTest {
    
    public EntryReportTest() {
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
     * Test of getATTEMPTED_ENTRY method, of class EntryReport.
     */
    @Test
    public void testGetATTEMPTED_ENTRY() {
        System.out.println("getATTEMPTED_ENTRY");
        EntryReport instance = new EntryReport();
        Entry expResult = null;
        Entry result = instance.getATTEMPTED_ENTRY();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNAME_MATCHES method, of class EntryReport.
     */
    @Test
    public void testGetNAME_MATCHES() {
        System.out.println("getNAME_MATCHES");
        EntryReport instance = new EntryReport();
        ObservableList<Entry> expResult = null;
        ObservableList<Entry> result = instance.getNAME_MATCHES();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getWHOLE_MATCHES method, of class EntryReport.
     */
    @Test
    public void testGetWHOLE_MATCHES() {
        System.out.println("getWHOLE_MATCHES");
        EntryReport instance = new EntryReport();
        ObservableList<Entry> expResult = null;
        ObservableList<Entry> result = instance.getWHOLE_MATCHES();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNAME_ERROR_MSG method, of class EntryReport.
     */
    @Test
    public void testGetNAME_ERROR_MSG() {
        System.out.println("getNAME_ERROR_MSG");
        EntryReport instance = new EntryReport();
        String expResult = "";
        String result = instance.getNAME_ERROR_MSG();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNUMBER_ERROR_MSG method, of class EntryReport.
     */
    @Test
    public void testGetNUMBER_ERROR_MSG() {
        System.out.println("getNUMBER_ERROR_MSG");
        EntryReport instance = new EntryReport();
        String expResult = "";
        String result = instance.getNUMBER_ERROR_MSG();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isERROR_FLAG method, of class EntryReport.
     */
    @Test
    public void testIsERROR_FLAG() {
        System.out.println("isERROR_FLAG");
        EntryReport instance = new EntryReport();
        boolean expResult = false;
        boolean result = instance.isERROR_FLAG();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isNAME_MATCHES_FLAG method, of class EntryReport.
     */
    @Test
    public void testIsNAME_MATCHES_FLAG() {
        System.out.println("isNAME_MATCHES_FLAG");
        EntryReport instance = new EntryReport();
        boolean expResult = false;
        boolean result = instance.isNAME_MATCHES_FLAG();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isWHOLE_MATCHES_FLAG method, of class EntryReport.
     */
    @Test
    public void testIsWHOLE_MATCHES_FLAG() {
        System.out.println("isWHOLE_MATCHES_FLAG");
        EntryReport instance = new EntryReport();
        boolean expResult = false;
        boolean result = instance.isWHOLE_MATCHES_FLAG();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isAnyMatches method, of class EntryReport.
     */
    @Test
    public void testIsAnyMatches() {
        System.out.println("isAnyMatches");
        EntryReport instance = new EntryReport();
        boolean expResult = false;
        boolean result = instance.isAnyMatches();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isOK method, of class EntryReport.
     */
    @Test
    public void testIsOK() {
        System.out.println("isOK");
        EntryReport instance = new EntryReport();
        boolean expResult = false;
        boolean result = instance.isOK();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
