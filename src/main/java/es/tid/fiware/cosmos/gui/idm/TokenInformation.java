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

package es.tid.fiware.cosmos.gui.idm;

/**
 *
 * @author Francisco Romero Bueno frb@tid.es
 */
public class TokenInformation {
    
    private String accessToken;
    private long expirationTime;
    private String refreshToken;
    private String type;
    
    /**
     * 
     */
    public TokenInformation() {
        accessToken = null;
        expirationTime = 0;
        refreshToken = null;
        type = null;
    } // TokenInformation
    
    /**
     * 
     * @param accessToken
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    } // setAccessToken
    
    /**
     * 
     * @param expirationTime
     */
    public void setExpirationTime(long expirationTime) {
        this.expirationTime = expirationTime;
    } // setAccessToken
    
    /**
     * 
     * @param refreshToken
     */
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    } // setAccessToken
    
    /**
     * 
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    } // setType
    
    /**
     * 
     * @return
     */
    public String getAccessToken() {
        return accessToken;
    } // getAccessToken
    
    /**
     * 
     * @return
     */
    public long getExpirationTime() {
        return expirationTime;
    } // getExpirationTime
    
    /**
     * 
     * @return
     */
    public String getRefreshToken() {
        return refreshToken;
    } // getRefreshToken
    
    /**
     * 
     * @return
     */
    public String getType() {
        return type;
    } // getType
    
} // TokenInformation
