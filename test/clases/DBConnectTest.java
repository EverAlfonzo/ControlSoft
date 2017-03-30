/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.sql.ResultSet;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Edgar
 */
public class DBConnectTest {
    
    public DBConnectTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of conectado method, of class DBConnect.
     */
    @Test
    public void testConectado() {
        System.out.println("conectado");
        String host = "";
        String Usuario = "";
        String Pass = "";
        String puerto = "";
        String database = "";
        DBConnect instance = new DBConnect();
        boolean expResult = false;
        boolean result = instance.conectado(host, Usuario, Pass, puerto, database);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of Listar method, of class DBConnect.
     */
    @Test
    public void testListar() {
        System.out.println("Listar");
        String campos = "";
        String tabla = "";
        String condicion = "";
        DBConnect instance = new DBConnect();
        ResultSet expResult = null;
        ResultSet result = instance.Listar(campos, tabla, condicion);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
