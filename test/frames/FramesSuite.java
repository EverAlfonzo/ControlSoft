/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package frames;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author Edgar
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({frames.jDnventaTest.class, frames.jDVendedorTest.class, frames.jDProductosTest.class, frames.jDVentasTest.class, frames.jDClientesTest.class, frames.jDncompraTest.class, frames.jDComprasTest.class, frames.FramePrincipalTest.class, frames.FrmLogonTest.class})
public class FramesSuite {

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    
}
