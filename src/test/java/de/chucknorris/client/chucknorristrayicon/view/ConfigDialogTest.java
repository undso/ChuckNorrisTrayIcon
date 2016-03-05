package de.chucknorris.client.chucknorristrayicon.view;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Alexander Friedrichs <friedrichs.alexander@gmail.com>
 */
public class ConfigDialogTest {

    private ConfigDialog configDialog = new ConfigDialog();
          
    
    @Test
    public void testPasswordField(){
        String pass = "securePassword";        
        configDialog.setProxyPassword(pass);        
        assertEquals(pass, configDialog.getProxyPassword());
    }
}
