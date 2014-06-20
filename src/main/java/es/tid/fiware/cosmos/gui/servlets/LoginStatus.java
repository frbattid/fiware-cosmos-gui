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


package es.tid.fiware.cosmos.gui.servlets;

/**
 *
 * @author frb
 */
public final class LoginStatus {
    
    /**
     * Constructor. It is private because utility classes should not have a public constructor.
     */
    private LoginStatus() {
    } // LoginStatus
    
    public static final int OK = 0;
    public static final int UNKNOWN_ERROR = 1;
    public static final int INVALID_IDM_USER = 2;
    public static final int ALREADY_REGISTERED_HDFS_USER = 3;
    public static final int ERROR_CREATING_UNIX_USER = 4;
    public static final int ERROR_SETTING_UNIX_PASSWORD = 5;
    public static final int ERROR_CREATING_HDFS_USER = 6;
    public static final int ERROR_SETTING_HDFS_OWNER = 7;
    public static final int ERROR_SETTING_HDFS_QUOTA = 8;
    
    
} // LoginStatus
