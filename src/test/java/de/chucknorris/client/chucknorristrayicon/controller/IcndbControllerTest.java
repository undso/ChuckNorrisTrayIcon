package de.chucknorris.client.chucknorristrayicon.controller;

import de.chucknorris.client.chucknorristrayicon.model.IcndbModel;
import de.chucknorris.client.chucknorristrayicon.model.IcndbResponse;
import java.util.ArrayList;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Alexander Friedrichs
 */
public class IcndbControllerTest {
    
    private static final Logger LOG = LoggerFactory.getLogger(IcndbControllerTest.class);
    private static final String JSON_TEST = 
            "{ \"type\": \"success\", \"value\": { \"id\": 452, \"joke\": \"Chuck Norris can't test for equality because he has no equal.\", \"categories\": [\"nerdy\", \"chuck norris\"] } }";
    private static final String JOKE = "Chuck Norris can't test for equality because he has no equal.";
    private static final String GSON_TEST = 
            "{ \"_id\": \"0815\", \"type\": \"success\", \"value\": { \"id\": 557, \"joke\": \"Chuck Norris can read from an input stream.\", \"categories\": [\"nerdy\"] } }";
    private IcndbModel model;
    private IcndbController controller;
    
    @Before
    public void setUp(){
        this.controller = new IcndbController();
        ArrayList<String> list = new ArrayList<String>();
        list.add("nerdy");
        list.add("chuck norris");
        this.model = new IcndbModel(452l, IcndbModel.SUCCESS, JOKE, list);
    }
    
//    @Test
//    public void testCreateIcndbObject(){
//        IcndbModel createIcndbObject = null;
//        try{
//            createIcndbObject = this.controller.createIcndbObject(JSON_TEST);
//        }catch(ParseException pe){
//            fail(pe.getMessage());
//        }
//        assertNotNull(createIcndbObject);
//        assertEquals(JOKE, createIcndbObject.toString());
//        List<String> categories = createIcndbObject.getCategories();        
//        assertTrue(categories.size() == 2);
//        
//    }
    
    @Test
    public void testCreateIcndbResponse(){
        IcndbResponse createIcndbResponse = this.controller.createIcndbResponse(GSON_TEST);
        
        assertNotNull(createIcndbResponse);
        assertTrue(createIcndbResponse.isSuccess());
        LOG.info("ListType: {}", createIcndbResponse.getValue().getCategories().getClass().getName());
        LOG.info("Result: {}", createIcndbResponse);
    }
    
//    @Test
//    public void testCreateJSONObject() throws ParseException{
//        JSONObject jSONObject = this.controller.createJSONObject(model);
//        LOG.info(jSONObject.toJSONString());
//        JSONObject jSONObject1 = (JSONObject) new JSONParser().parse(JSON_TEST);
//        assertEquals(jSONObject, jSONObject1);
//    }
}
