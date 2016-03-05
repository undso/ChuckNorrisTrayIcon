package de.chucknorris.client.chucknorristrayicon;

import de.chucknorris.client.chucknorristrayicon.controller.ConfigDialogController;
import de.chucknorris.client.chucknorristrayicon.controller.Controller;
import de.chucknorris.client.chucknorristrayicon.settings.Settings;
import de.chucknorris.client.chucknorristrayicon.view.CNTrayIcon;
import java.io.IOException;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 *  @author Alexander Friedrichs
 */
public class App 
{
    private static final Logger LOG = LoggerFactory.getLogger(App.class);
    private final Controller controller;
    private final ConfigDialogController configDialogController;
    private final CNTrayIcon trayIcon;
    private final Settings settings;
    

    public App() {
        this.settings = new Settings();
        LOG.debug("Settings created: {}", this.settings);
        
        this.configDialogController = new ConfigDialogController(this.settings);
        LOG.debug("ConfigDialogController created: {}", this.configDialogController);
        
        this.controller = new Controller(this.configDialogController);
        LOG.debug("Controller created: {}", this.controller);
        
        this.trayIcon = new CNTrayIcon(controller);
        LOG.debug("TrayIcon created: {}", this.trayIcon);
    }
    
    
    public static void main( String[] args )
    {
        try {
            UIManager.setLookAndFeel(
                UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            LOG.error("Fehler beim Laden des UIManager", ex);
        }
        
        App app = new App();
        try {
            app.start();
        } catch (IOException ex) {
            LOG.error("Fehler beim Starten der Applikation", ex);
            System.exit(1);
        }
    }

    private void start() throws IOException {
        this.settings.loadProps();
        this.trayIcon.createSystemTray();
        this.controller.addObserver(trayIcon);     
        this.controller.createTimer();
        this.controller.start();
    }
}
