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
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

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
            #login { height: 20%; width: 99%; position: absolute; top: 18% }
            #filab-link { height: 5%; width: 99%; position: absolute; top: 40% }
            #footer { height: 3%; width: 99%; position: absolute; top: 95%; vertical-align: middle }
            a { text-decoration: none; }
            table { border: 0px; width: 30% }
            #left-column { width: 20% }
            #rigth-column { width: 80% }
            #input-field { width: 100% }
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
        <div id="login">
            <h3>Create a Cosmos account by login with your FI-LAB user:</h3>
            <form method="POST" action="/cosmos-gui/CommandsMgr?cmd=login">
                <table>
                    <tr><td id="left-column">Email</td><td id="rigth-column"><input id="input-field" type="text" name="idmUsername"></td></tr>
                    <tr><td id="left-column">Password</td><td id="rigth-column"><input  id="input-field" type="password" name="idmPassword"></td></tr>
                </table>
                <input type="submit" value="Login">
            </form>
        </div>
        <div id="filab-link">
            <h3>If you still do not have a FI-LAB user, click <a href="https://account.lab.fi-ware.org/users/sign_up">here</a>.</h3>
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
