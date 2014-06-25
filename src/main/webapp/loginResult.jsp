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
        <title>Cosmos GUI</title>
        <style type="text/css">
            body { margin: 1%; background-image: url(imgs/cosmos.png); background-position: 50% 0%;
                   background-repeat: no-repeat; color: slategrey; font-family: Helvetica }
            #header-fiware { height: 5%; width: 5%; position: absolute; top: 1%; left: 1% }
            #header-filab { height: 5%; width: 10%; position: absolute; top: 1%; left: 6% }
            #header-title { height: 5%; width: 83%; position: absolute; top: 1%; right: 0% }
            h1 { vertical-align: middle }
            #hr-line { height: 1%; width: 99%; position: absolute; top: 8% }
            #logo { width: 100% }
            #result { height: 70%; width: 99%; position: absolute; top: 18% }
            #footer { height: 3%; width: 99%; position: absolute; top: 95%; vertical-align: middle }
            a { text-decoration: none; }
        </style>
    </head>
    <body>
        <div id="header-fiware">
            <img id="logo" src="imgs/fiware.png">
        </div>
        <div id="header-filab">
            <img id="logo" src="imgs/filab.png">
        </div>
        <div id="header-title">
            <h1>Cosmos GUI</h1>
        </div>
        <div id="hr-line">
            <hr>
        </div>
        <div id="result">
            <%
            int status = new Integer(request.getParameter("status"));
            String cosmosUsername = request.getParameter("cosmosUsername");

            switch (status) {
                case 0:
            %>
            <h3>Cosmos account created!</h3>
            Cosmos user: <%= cosmosUsername %><br>
            HDFS user space: <code>/user/<%= cosmosUsername %>/</code> (5 GB quota)<br>
            <br>
            <br>
            You are now allowed to access the FI-LAB Cosmos Head Node:<br>
            <code>$ ssh <%= cosmosUsername %>@cosmos.lab.fi-ware.org</code><br>
            <br>
            <br>
            Other services like Hive, Oozie or WebHDFS/HttpFS are remotely available from any FI-LAB virtual machine.
            <%
                    break;
                case 1:
            %>
            <h3>Error: unknown</h3>
            <%
                    break;
                 case 2:
            %>
            <h3>Error: invalid IdM user</h3>
            <%
                    break;
                case 3:
            %>
            <h3>Already registered user!</h3>
            Remember, cosmos user: <%= cosmosUsername %><br>
            HDFS user space: <code>/user/<%= cosmosUsername %>/</code> (5 GB quota)<br>
            <br>
            <br>
            You are already allowed to access the FI-LAB Cosmos Head Node:<br>
            <code>$ ssh <%= cosmosUsername %>@cosmos.lab.fi-ware.org</code><br>
            <br>
            <br>
            Other services like Hive, Oozie or WebHDFS/HttpFS are still remotely available from any FI-LAB virtual machine.
            <%
                    break;
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
            %>
            <h3>Error: internal error creating the account</h3>
            <%
                    break;            
            } // switch
            %>
        </div>
        <div id="footer">
            2014 
            © 
            <a href="http://fi-ware.org/">FI-WARE</a>. 
            The use of FI-LAB services is subject to the acceptance of the
            <a href="http://wiki.fi-ware.org/FI-LAB_Terms_and_Conditions"> 
            Terms and Conditions
            </a>
            and 
            <a href="http://forge.fi-ware.org/plugins/mediawiki/wiki/fiware/index.php/FI-LAB_Personal_Data_Protection_Policy">
            Personal Data Protection Policy
            </a>
        </div>
    </body>
</html>
