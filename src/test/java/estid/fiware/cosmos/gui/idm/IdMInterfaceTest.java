/**
 * Copyright 2014 Telefonica Investigación y Desarrollo, S.A.U
 *
 * This file is part of fiware-cosmos-gui.
 *
 * fiware-cosmos-gui is free software: you can redistribute it and/or modify it under the terms of the GNU Affero
 * General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * fiware-cosmos-gui is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Affero General Public License along with fiware-connectors. If not, see
 * http://www.gnu.org/licenses/.
 *
 * For those usages not covered by the GNU Affero General Public License please contact with Francisco Romero
 * frb@tid.es
 */
package estid.fiware.cosmos.gui.idm;

import es.tid.fiware.cosmos.gui.idm.IdMInterface;
import es.tid.fiware.cosmos.gui.idm.TokenInformation;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author frb
 */
public class IdMInterfaceTest {
    
    /**
     * Test of configure method, of class OrionHDFSSink.
     */
    @Test
    public void testConfigure() {
        System.out.println("Testing IdMInterface.createTokenInformation");
        IdMInterface idmIf = new IdMInterface(null, null, null);
        TokenInformation ti = idmIf.createTokenInformation(
                "{\"access_token\": \"H9VIhanka8tlsramhkjyO9Y0PEoWit\", \"token_type\": \"Bearer\", "
                + "\"expires_in\": 3600, \"refresh_token\": \"qkBAzYJNw4AMQWdnjRQMvIx5Are4xP\"}");
        assertEquals("H9VIhanka8tlsramhkjyO9Y0PEoWit", ti.getAccessToken());
        assertEquals("Bearer", ti.getType());
        assertEquals(3600, ti.getExpirationTime());
        assertEquals("qkBAzYJNw4AMQWdnjRQMvIx5Are4xP", ti.getRefreshToken());
    } // testConfigure
    
} // IdMInterfaceTest
