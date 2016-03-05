package de.chucknorris.client.chucknorristrayicon.controller;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Alexander Friedrichs <friedrichs.alexander@gmail.com>
 */
public class ControllerTest {

    @Test
    public void testReplace(){
        
        String in = "Chuck Norris can't finish a &quot;color by numbers&quot; because his markers are filled with the blood of his victims. Unfortunately, all blood is dark red.";
        String exp = "Chuck Norris can't finish a \"color by numbers\" because his markers are filled with the blood of his victims. Unfortunately, all blood is dark red.";
    
        String out = in.replace("&quot;", "\"");
        
        assertEquals(exp, out);
    
    }
}
