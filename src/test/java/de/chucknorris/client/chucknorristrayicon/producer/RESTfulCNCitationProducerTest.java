package de.chucknorris.client.chucknorristrayicon.producer;

import com.google.gson.Gson;
import de.chucknorris.client.chucknorristrayicon.model.IcndbResponse;
import de.chucknorris.client.chucknorristrayicon.model.IcndbValue;
import de.chucknorris.client.chucknorristrayicon.settings.Constants;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Alexander Friedrichs
 */
public class RESTfulCNCitationProducerTest {
    
    private static final Logger LOG = LoggerFactory.getLogger(RESTfulCNCitationProducerTest.class);
    private static final String URL = "http://api.icndb.com/jokes/random";
    
    private RESTfulCNCitationProducer citationProducer;
    
    @Before
    public void setUp(){
        this.citationProducer = new RESTfulCNCitationProducer();
        System.setProperty(Constants.SETTINGS_PROXY_HOST, "");
        System.setProperty(Constants.SETTINGS_PROXY_PORT, "");
    }
    
    @Test
    public void testServerCall(){
        String response = this.citationProducer.serverCall(URL);
        LOG.info(response);
        assertNotNull(response);
        
        Gson gsonParser = new Gson();
        IcndbResponse icndbResponse = gsonParser.fromJson(response, IcndbResponse.class);
        
        assertNotNull(icndbResponse);
        assertTrue(icndbResponse.getValue() instanceof IcndbValue);
    }
    
//    @Test
//    public void testProxyAuth(){
//        System.setProperty(Constants.SETTINGS_PROXY_HOST, "google.com");
//        System.setProperty(Constants.SETTINGS_PROXY_PORT, "80");
//        System.setProperty(Constants.SETTINGS_PROXY_USER, "user");
//        System.setProperty(Constants.SETTINGS_PROXY_PASS, "pass");
//        String response = this.citationProducer.serverCall(URL);
//        LOG.info(response);
//        assertTrue(!response.isEmpty());
//    }

    @Test
    public void testGetCitation(){
        Object citation = this.citationProducer.getCitation();
        LOG.info("Citation to show: {}", citation);
        assertNotNull(citation);
    }
    
    @Test
    public void testCreateErrorResponse(){
        String createErrorResponse = this.citationProducer.createErrorResponse("Test Error");
        assertTrue(createErrorResponse != null && !createErrorResponse.isEmpty());
        assertEquals("{\"type\": \"error\", \"value\": {\"id\": -1, \"joke\": \"Test Error\"}}", createErrorResponse);
    }
}
