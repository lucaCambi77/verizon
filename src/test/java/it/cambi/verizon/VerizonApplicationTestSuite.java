/**
 *
 */
package it.cambi.verizon;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;

/**
 * @author luca
 *
 */
@RunWith(JUnitPlatform.class)
@SelectClasses( { VerizonApplicationRestTest.class, VerizonApplicationServiceIntegrationTest.class } )
public class VerizonApplicationTestSuite
{

}
