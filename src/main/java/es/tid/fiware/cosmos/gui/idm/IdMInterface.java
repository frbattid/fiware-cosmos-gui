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

import org.apache.commons.codec.binary.Base64;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;

/**
 *
 * @author Francisco Romero Bueno frb@tid.es
 */
public class IdMInterface {
    
    private Logger logger;
    private String host;
    private String port;
    private ConnectionsManager connectionsManager;
    
    /**
     * Constructor.
     * @param host Host where the IdM runs.
     * @param port Port where the IdM listens for incomming connections.
     * @param connectionsManager Connections Manager object containing a predefined pool of http connections.
     */
    public IdMInterface(String host, String port, ConnectionsManager connectionsManager) {
        logger = Logger.getLogger(IdMInterface.class);
        this.host = host;
        this.port = port;
        this.connectionsManager = connectionsManager;
    } // IdMInterface
    
    /**
     * Obtains from the IdM an OAuth2 access token for the given credentials.
     * @param user User of the IdM requesting the token.
     * @param password Password of the user in the IdM.
     * @param clientId OAuth2 client identifier.
     * @param clientSecret OAuth2 client secret.
     * @return
     */
    public TokenInformation getAccessToken(String user, String password, String clientId,
            String clientSecret) {
        logger.info("Getting an access token from the IdM, user=" + user + ", clientId=" + clientId);
        DefaultHttpClient httpClient = connectionsManager.getHttpClient(true);
        HttpPost httpPost = new HttpPost("https://" + host + ":" + port + "/oauth2/token");
        logger.info(">>> " + httpPost.toString());
        TokenInformation tokenInformation = null;
            
        try {
            httpPost.setHeader("Host", host);
            logger.info(">>> Host: " + host);
            String authHeader = new String(Base64.encodeBase64((clientId + ":" + clientSecret).getBytes()));
            httpPost.setHeader("Authorization", "Basic " + authHeader);
            logger.info(">>> Authorization: Basic " + authHeader);
            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
            logger.info(">>> Content-Type: application/x-www-form-urlencoded");
            httpPost.setEntity(new StringEntity("grant_type=password&username=" + user + "&password=" + password));
            logger.info(">>> [content not shown]");
            HttpResponse response = httpClient.execute(httpPost);
            logger.info("<<< " + response.toString());
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String res = reader.readLine();
            logger.info("<<< " + res);
            tokenInformation = createTokenInformation(res);
            reader.close();
        } catch (java.io.IOException e) {
            logger.error("Cannot get the access token, user=" + user + ", clientId=" + clientId);
            logger.error(e.getMessage());
        } finally {
            httpPost.releaseConnection();
            return tokenInformation;
        } // try catch finally
    } // getAccessToken
    
    /**
     * Obtains the information related to a client given its OAuth2 token.
     * @param token OAuth2 token.
     * @return
     */
    public ClientInformation getClientInformation(String token) {
        logger.info("Getting client information from the IdM, token=" + token);
        DefaultHttpClient httpClient = connectionsManager.getHttpClient(false);
        HttpGet httpGet = new HttpGet("https://" + host + ":" + port + "/user?access_token=" + token);
        logger.info(">>> " + httpGet.toString());
        ClientInformation clientInformation = null;

        try {
            HttpResponse response = httpClient.execute(httpGet);
            logger.info("<<< " + response.toString());
            BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String res = in.readLine();
            logger.info("<<< " + res);
            clientInformation = createClientInformation(res);
        } catch (IOException e) {
            logger.error("Cannot get client information, token=" + token);
            logger.error(e.getMessage());
            return null;
        } finally {
            httpGet.releaseConnection();
            return clientInformation;
        } // try catch finally
    } // getClientInformation

    /**
     * Creates an OAuth2 token information object given its json representation.
     * @param json The json representation of the OAuth2 token.
     * @return
     */
    private TokenInformation createTokenInformation(String json) {
        if (!json.contains("access_token")) {
            return null;
        } // if
        
        TokenInformation ti = new TokenInformation();
        String[] pairs = json.replaceAll("\\{", "").replaceAll("\\}", "").replaceAll("\"", "").split(",");
        ti.setAccessToken(pairs[0].split(":")[1]);
        ti.setRefreshToken(pairs[1].split(":")[1]);
        ti.setType(pairs[2].split(":")[1]);
        ti.setExpirationTime(new Long(pairs[3].split(":")[1]).longValue());
        return ti;
    } // createTokenInformation

    /**
     * 
     * Creates a client information object given its json representation.
     * @param json The json representation of the client information.
     */
    private ClientInformation createClientInformation(String json) {
        ClientInformation ci = new ClientInformation();
        String s1 = json.substring(1, json.length() - 1);
        String[] pairs1 = s1.replaceAll("\"", "").split(",", 6);
        ci.setId(pairs1[0].split(":")[1]);
        ci.setActorId(pairs1[1].split(":")[1]);
        ci.setNickName(pairs1[2].split(":")[1]);
        ci.setDisplayName(pairs1[3].split(":")[1]);
        ci.setEmail(pairs1[4].split(":")[1]);
        String s2 = pairs1[5].split(":", 2)[1];
        String[] pairs2 = s2.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\{", "").
                replaceAll("\\}", "").split(",");
        
        for (int i = 0; i < pairs2.length; i += 2) {
            ci.setRole(pairs2[i].split(":")[1], pairs2[i + 1].split(":")[1]);
        } // for
        
        return ci;
    } // createClientInformation
    
} // IdMInterface
