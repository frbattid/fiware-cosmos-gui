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

import java.util.ArrayList;

/**
 *
 * @author Francisco Romero Bueno frb@tid.es
 * 
 * Contains all the IdM clients information.
 */
public class ClientInformation {
    
    /**
     * Contains a role information, i.e. a (id, name) pair.
     */
    public class RoleInformation {
        
        private String id;
        private String name;
        
        /**
         * Constructor.
         * @param id
         * @param name
         */
        public RoleInformation(String id, String name) {
            this.id = id;
            this.name = name;
        } // RoleInformation
        
        /**
         * Returns the identifier part of the role.
         * @return The identifier part of the role.
         */
        public String getId() {
            return this.id;
        } // getId
        
        /**
         * Returns the name part of the role.
         * @return The name part of the role.
         */
        public String getName() {
            return this.name;
        } // getName
        
    } // RoleInformation
    
    private String id;
    private String actorId;
    private String nickName;
    private String displayName;
    private String email;
    private ArrayList<RoleInformation> roles;
    
    /**
     * Constructor.
     */
    public ClientInformation() {
        id = null;
        actorId = null;
        nickName = null;
        displayName = null;
        email = null;
        roles = new ArrayList<RoleInformation>();
    } // ClientInformation
    
    /**
     * 
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    } // setId
    
    /**
     * 
     * @param actorId
     */
    public void setActorId(String actorId) {
        this.actorId = actorId;
    } // setActorId
    
    /**
     * 
     * @param nickName
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    } // setNickName
    
    /**
     * 
     * @param displayName
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    } // setDisplayName
    
    /**
     * 
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    } // setEmail
    
    /**
     * 
     * @param id
     * @param name
     */
    public void setRole(String id, String name) {
        this.roles.add(new RoleInformation(id, name));
    } // setRole
    
    /**
     * 
     * @return
     */
    public String getId() {
        return this.id;
    } // getId
    
    /**
     * 
     * @return
     */
    public String getActorId() {
        return this.actorId;
    } // getActorId
    
    /**
     * 
     * @return
     */
    public String getNickName() {
        return this.nickName;
    } // getNickName
    
    /**
     * 
     * @return
     */
    public String getDisplayName() {
        return this.displayName;
    } // getDisplayName
    
    /**
     * 
     * @return
     */
    public String getEmail() {
        return this.email;
    } // getEmail
    
    /**
     * 
     * @return
     */
    public ArrayList<RoleInformation> getRoles() {
        return this.roles;
    } // getRoles
    
    /**
     * 
     * @return
     */
    public String getRolesStr() {
        if (roles == null) {
            return null;
        } // if

        String res = ((RoleInformation) roles.get(0)).getName();
        
        for (int i = 1; i < roles.size(); i++) {
            res += "," + ((RoleInformation) roles.get(i)).getName();
        } // for
        
        return res;
    } // getRolesStr
    
} // ClientInformation
