package de.chucknorris.client.chucknorristrayicon.producer;

import de.chucknorris.client.chucknorristrayicon.callbackhandler.CitationCallBackHandler;
import de.chucknorris.client.chucknorristrayicon.controller.IcndbController;
import de.chucknorris.client.chucknorristrayicon.settings.Constants;
import java.io.IOException;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Alexander Friedrichs
 */
public class RESTfulCNCitationProducer implements CitationProducer {

    private static final String URI = "http://api.icndb.com/jokes/random";
    private static final Logger LOG = LoggerFactory.getLogger(RESTfulCNCitationProducer.class);
    private static final String EMPTYSTRING = "";
    private static final String DEFAULTPORT = "80";
    private static final String ERROR_RESPONSE = "{\"type\": \"error\", \"value\": {\"id\": -1, \"joke\": \"%%ERROR_MESSAGE%%\"}}";
    private static final String ERROR_MESSAGE = "%%ERROR_MESSAGE%%";
    private final IcndbController controller;
    private CitationCallBackHandler callBackHandler;

    public RESTfulCNCitationProducer() {
        this.controller = new IcndbController();
    }

    @Override
    public Object getCitation() {
        String serverCall = serverCall(URI);
        
        return this.controller.createIcndbResponse(serverCall);
    }

    @Override
    public void run() {
        this.callBackHandler.callBackCitation(getCitation());
    }

    protected String serverCall(String uri) {
        String responseBody = null;
        String proxyHost = System.getProperty(Constants.SETTINGS_PROXY_HOST, EMPTYSTRING);
        String proxyPort = System.getProperty(Constants.SETTINGS_PROXY_PORT, DEFAULTPORT);
        String proxyUser = System.getProperty(Constants.SETTINGS_PROXY_USER, EMPTYSTRING);
        String proxyPass = System.getProperty(Constants.SETTINGS_PROXY_PASS, EMPTYSTRING);

        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpHost proxy = null;
        if (!proxyHost.isEmpty()) {
            LOG.debug("ProxyHost wird gesetzt.");
            int proxyProtInt = Integer.parseInt(proxyPort);
            proxy = new HttpHost(proxyHost, proxyProtInt);
            httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
        }

        if (!proxyHost.isEmpty() && !proxyUser.isEmpty() && !proxyPass.isEmpty()) {
            LOG.debug("Proxy Auth wird gesetzt.");
            httpClient.getCredentialsProvider().setCredentials(
                    new AuthScope(proxy.getHostName(), proxy.getPort()),
                    new UsernamePasswordCredentials(proxyUser, proxyPass));
        }
        HttpGet httpGet = new HttpGet(uri);

        // Create a response handler
        ResponseHandler<String> responseHandler = new BasicResponseHandler();

        try {
            responseBody = httpClient.execute(httpGet, responseHandler);
        } catch (IOException ioe) {
            LOG.error("Could not get data from URL '" + URI + "'", ioe);
            responseBody = "Could not get data from URL '" + URI + "'\nSee LOG for more information.";
            responseBody = createErrorResponse(responseBody);
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
        LOG.debug("Response: {}", responseBody);
        return responseBody;
    }
    
    protected String createErrorResponse(String message){
        return ERROR_RESPONSE.replace(ERROR_MESSAGE, message);
    }

    @Override
    public void setCallBackHandler(CitationCallBackHandler callBackHandler) {
        this.callBackHandler = callBackHandler;
    }
}
