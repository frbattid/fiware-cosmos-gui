<!--
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
-->

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Cosmos 0.9 GUI</h1>
        <%
        int status = new Integer(request.getParameter("status"));
        String cosmosUsername = request.getParameter("cosmosUsername");
        
        switch (status) {
            case 0:
        %>
        <h2>Cosmos account created!</h2>
        Cosmos user: <%= cosmosUsername %><br>
        HDFS user space: <code>/user/<%= cosmosUsername %>/</code> (5 GB quota)<br>
        <br>
        <br>
        You are now allowed to access the FI-LAB Cosmos Head Node:<br>
        <code>$ ssh <%= cosmosUsername %>@130.206.80.46</code><br>
        <br>
        <br>
        Other services like Hive, Oozie or WebHDFS/HttpFS are remotely available from any FI-LAB virtual machine.
        <%
                break;
            case 1:
        %>
        <h2>Error: unknown</h2>
        <%
                break;
             case 2:
        %>
        <h2>Error: invalid IdM user</h2>
        <%
                break;
            case 3:
        %>
        <h2>Error: already registered Cosmos user</h2>
        <%
                break;
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
        %>
        <h2>Error: internal error creating the account</h2>
        <%
                break;            
        } // switch
        %>
    </body>
</html>
