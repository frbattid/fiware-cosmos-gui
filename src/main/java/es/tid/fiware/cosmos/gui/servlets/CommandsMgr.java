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

import es.tid.fiware.cosmos.gui.idm.ConnectionsManager;
import es.tid.fiware.cosmos.gui.idm.IdMInterface;
import es.tid.fiware.cosmos.gui.idm.TokenInformation;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

/**
 *
 * @author frb
 */
public class CommandsMgr extends HttpServlet {
    
    private Logger logger;
    
    /**
     * Constructor.
     */
    public CommandsMgr() {
        super();
        FileAppender fa = new FileAppender();
        fa.setName("FileLogger");
        fa.setFile("../logs/cosmos-gui.log");
        fa.setLayout(new PatternLayout("%d %-5p [%c{1}] %m%n"));
        fa.setThreshold(Level.DEBUG);
        fa.setAppend(true);
        fa.activateOptions();
        Logger.getRootLogger().addAppender(fa);
        logger = Logger.getLogger(CommandsMgr.class);
    } // CommandsMgr

    /**
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    } // doGet

    /**
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
                
        // get a writer from the response object in order to send back to the client plain text data
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
            
        // parameters that can be sent in the request
        String cmd = null; // command to be executed by this servlet
        String idmUsername = null; // IdM username
        String idmPassword = null; // password related to the IdM username
        
        // get all the request parameters
        Enumeration<String> keys = request.getParameterNames();
        
        while (keys.hasMoreElements()) {
            String key = (String) keys.nextElement();

            if (key.equalsIgnoreCase("cmd")) {
                cmd = request.getParameter(key);
            } else if (key.equalsIgnoreCase("idmUsername")) {
                idmUsername = request.getParameter("idmUsername");
            } else if (key.equalsIgnoreCase("idmPassword")) {
                idmPassword = request.getParameter("idmPassword");
            } // if else if
        } // while
        
        // process the command
        int loginStatus;
        
        if (cmd == null) {
            loginStatus = LoginStatus.UNKNOWN_ERROR;
        } else if (cmd.equalsIgnoreCase("login")) {
            loginStatus = doLogin(idmUsername, idmPassword);
        } else {
            loginStatus = LoginStatus.UNKNOWN_ERROR;
        } // if else if
        
        response.sendRedirect("loginResult.jsp?status=" + loginStatus + "&cosmosUsername=" + idmUsername.split("@")[0]);
    } // doPost

    /**
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    } // getServletInfo
    
    private int doLogin(String idmUsername, String idmPassword) {
        try {
            // try to get an access token, given the IdM username and its password
            IdMInterface idmInterface =
                    new IdMInterface("account.lab.fi-ware.org", "443", new ConnectionsManager(true));
            TokenInformation tokenInfo = idmInterface.getAccessToken(idmUsername, idmPassword, "748",
                    "0216b70dd74cb6d15d69a3e83d57b68cc5e77431616485206a1bbd3e43e0369f77c5ba22deee4a88c4b7b09d6532bdd3"
                    + "9124397f83892e1855fae1711ed52a08");
            
            // if the access token could not be retrieved, the user is not a valid IdM one
            if (tokenInfo == null) {
                logger.info("Access token = NULL, it is not a valid IdM user");
                return LoginStatus.INVALID_IDM_USER;
            } // if
            
            // reached this point, the user is a valid IdM one
            logger.info("Access token = " + tokenInfo.getAccessToken() + ", it is a valid IdM user");
            
            // get the Cosmos username from the IdM username
            String cosmosUsername = idmUsername.split("@")[0];
            
            // Unix commands will be executed using the Runtime exec functionality, that returns Process objects
            Process p = null;
                        
            // check if the Cosmos username is already registered
            p = Runtime.getRuntime().exec(new String[]{"bash", "-c", "cat /etc/passwd | grep ^" + cosmosUsername
                    + ":"});
            p.waitFor();
            
            if (p.exitValue() == 0) {
                logger.info("The user " + idmUsername + " is already registered");
                return LoginStatus.ALREADY_REGISTERED_HDFS_USER;
            } // if
            
            // create a Unix user
            p = Runtime.getRuntime().exec(new String[]{"bash", "-c", "useradd " + cosmosUsername});
            p.waitFor();
            logger.info("Executing \"useradd " + cosmosUsername + "\", exit value = " + p.exitValue());
            
            if (p.exitValue() != 0) {
                return LoginStatus.ERROR_CREATING_UNIX_USER;
            } // if
            
            // set the password for the Unix user
            p = Runtime.getRuntime().exec(new String[]{"bash", "-c", "echo " + idmPassword + " | passwd "
                    + cosmosUsername + " --stdin"});
            p.waitFor();
            logger.info("Executing \"passwd " + cosmosUsername + "\", exit value = " + p.exitValue());
            
            if (p.exitValue() != 0) {
                return LoginStatus.ERROR_SETTING_UNIX_PASSWORD;
            } // if
            
            // create the HDFS user space
            // NOTE: in order commands like "sudo -u hdfs ..." can be run without a terminal, run "visudo" and remove
            // the "Defaults requiretty" property
            p = Runtime.getRuntime().exec(new String[]{"bash", "-c",
                "sudo -u hdfs hadoop fs -mkdir /user/" + cosmosUsername});
            p.waitFor();
            logger.info("Executing \"sudo -u hdfs hadoop fs -mkdir /user/" + cosmosUsername
                    + "\", exit value = " + p.exitValue() + (p.exitValue() == 1 ? ", details = "
                    + new BufferedReader(new InputStreamReader(p.getErrorStream())).readLine() : ""));
            
            if (p.exitValue() != 0) {
                return LoginStatus.ERROR_CREATING_HDFS_USER;
            } // if
            
            // set the proper owner of the HDFS space
            p = Runtime.getRuntime().exec(new String[]{"bash", "-c",
                "sudo -u hdfs hadoop fs -chown -R " + cosmosUsername + ":" + cosmosUsername + " /user/"
                    + cosmosUsername});
            p.waitFor();
            logger.info("Executing \"sudo -u hdfs hadoop fs -chown -R " + cosmosUsername + ":" + cosmosUsername
                    + " /user/" + cosmosUsername + "\", exit value = " + p.exitValue()
                    + (p.exitValue() == 1 ? ", details = "
                    + new BufferedReader(new InputStreamReader(p.getErrorStream())).readLine() : ""));
            
            if (p.exitValue() != 0) {
                return LoginStatus.ERROR_SETTING_HDFS_OWNER;
            } // if
            
            // set the quota for the HDFS space
            p = Runtime.getRuntime().exec(new String[]{"bash", "-c",
                "sudo -u hdfs hadoop dfsadmin -setSpaceQuota 5g /user/" + cosmosUsername});
            p.waitFor();
            logger.info("Executing \"sudo -u hdfs hadoop dfsadmin -setSpaceQuota 5g /user/" + cosmosUsername
                    + "\", exit value = " + p.exitValue() + (p.exitValue() == 1 ? ", details = "
                    + new BufferedReader(new InputStreamReader(p.getErrorStream())).readLine() : ""));
            
            if (p.exitValue() != 0) {
                return LoginStatus.ERROR_SETTING_HDFS_QUOTA;
            } // if
            
            return LoginStatus.OK;
        } catch (IOException e) {
            logger.error(e.getMessage());
            return LoginStatus.UNKNOWN_ERROR;
        } catch (java.lang.InterruptedException e) {
            logger.error(e.getMessage());
            return LoginStatus.UNKNOWN_ERROR;
        } // try catch
    } // doLogin
    
} // CommandsMgr
