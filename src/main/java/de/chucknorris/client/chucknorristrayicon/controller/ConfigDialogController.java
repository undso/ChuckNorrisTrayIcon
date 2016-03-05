package de.chucknorris.client.chucknorristrayicon.controller;

import de.chucknorris.client.chucknorristrayicon.callbackhandler.SettingsChangeCallBackHandler;
import de.chucknorris.client.chucknorristrayicon.settings.Constants;
import de.chucknorris.client.chucknorristrayicon.settings.Settings;
import de.chucknorris.client.chucknorristrayicon.view.ConfigDialog;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Alexander Friedrichs
 */
public class ConfigDialogController {
    
    private static final Logger LOG = LoggerFactory.getLogger(ConfigDialogController.class);
    
    private final ConfigDialog dialog;
    private final Settings settings;
    private SettingsChangeCallBackHandler callBackHandler;


    public ConfigDialogController(Settings settings) {
        this.dialog = new ConfigDialog(this, null, false);
        LOG.debug("ConfigDialog created: {}", this.dialog);
        this.settings = settings;
    }
    
    public void showConfigScreen(SettingsChangeCallBackHandler callBackHandler) {
        this.callBackHandler = callBackHandler;
        this.dialog.setLocationRelativeTo(null);
        String delay = System.getProperty(Constants.DELAY, Constants.DEFAULT_DELAY);
        int i = Integer.parseInt(delay);
        this.dialog.setDelay(Integer.parseInt(calculateDelay(i, true)));
        this.dialog.setProxyHost(System.getProperty(Constants.SETTINGS_PROXY_HOST));
        this.dialog.setProxyPort(System.getProperty(Constants.SETTINGS_PROXY_PORT));
        this.dialog.setProxyUser(System.getProperty(Constants.SETTINGS_PROXY_USER));
        this.dialog.setProxyPassword(System.getProperty(Constants.SETTINGS_PROXY_PASS));
        this.dialog.pack();
        this.dialog.setVisible(true);
    }

    public void save() {
        try{
            this.settings.setProperty(Constants.SETTINGS_PROXY_HOST, dialog.getProxyHost());
            this.settings.setProperty(Constants.SETTINGS_PROXY_PORT, dialog.getProxyPort());
            this.settings.setProperty(Constants.DELAY, calculateDelay(dialog.getDelay(), false));
            this.settings.setProperty(Constants.SETTINGS_PROXY_USER, dialog.getProxyUser());
            this.settings.setProperty(Constants.SETTINGS_PROXY_PASS, dialog.getProxyPassword());
            this.settings.saveProps();
        }catch (IOException ioe){
            LOG.error("Fehler beim Speichern der Einstellungen", ioe);
        }
        this.callBackHandler.callBackSettingsChange();
    }
    
    public String calculateDelay(int delayInMinutes, boolean fromMinutesToMillis){
        int i;
        if(fromMinutesToMillis){
            i = (delayInMinutes / 1000) / 60;
        }else{
            i = delayInMinutes * 1000 * 60;
        }
        return Integer.toString(i);
    }
}
