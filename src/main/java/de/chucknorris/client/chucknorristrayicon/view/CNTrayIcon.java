package de.chucknorris.client.chucknorristrayicon.view;

import de.chucknorris.client.chucknorristrayicon.controller.Controller;
import de.chucknorris.client.chucknorristrayicon.settings.Constants;
import java.awt.AWTException;
import java.awt.CheckboxMenuItem;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Alexander Friedrichs
 */
public class CNTrayIcon implements Observer, ActionListener, ItemListener {

    private static final Logger LOG = LoggerFactory.getLogger(CNTrayIcon.class);
    private String lastCitation;
    private TrayIcon trayIcon;
    private final Controller controller;
    private final ResourceBundle bundle;

    public CNTrayIcon(Controller controller) {
        this.controller = controller;
        this.bundle = ResourceBundle.getBundle(Constants.RESOURCEBUNDLE);
    }

    private PopupMenu createPopupMenu() {
        PopupMenu popupMenu = new PopupMenu();
        MenuItem citation = new MenuItem(bundle.getString(Constants.NEWCITATION));
        citation.addActionListener(this);
        citation.setActionCommand(Constants.NEWCITATION);
        popupMenu.add(citation);
        CheckboxMenuItem checkboxMenuItem = new CheckboxMenuItem(bundle.getString(Constants.PAUSE), false);
        checkboxMenuItem.addItemListener(this);
        checkboxMenuItem.setActionCommand(Constants.PAUSE);
        popupMenu.add(checkboxMenuItem);
        MenuItem settingsMenuItem = new MenuItem(bundle.getString(Constants.CONFIGSCREEN));
        settingsMenuItem.addActionListener(this);
        settingsMenuItem.setActionCommand(Constants.CONFIGSCREEN);
        popupMenu.add(settingsMenuItem);
        popupMenu.addSeparator();
        MenuItem item = new MenuItem(bundle.getString(Constants.QUIT));
        item.addActionListener(this);
        item.setActionCommand(Constants.QUIT);
        popupMenu.add(item);
        return popupMenu;
    }

    public void createSystemTray() {

        if (SystemTray.isSupported()) {
            SystemTray tray = SystemTray.getSystemTray();
            Image createImage = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/de/chucknorris/client/images/chuck_norris.png"));
            trayIcon = new TrayIcon(createImage, "Chuck Norris Citations", createPopupMenu());
            trayIcon.setImageAutoSize(true);
            trayIcon.addActionListener(this);
            trayIcon.setActionCommand(Constants.CITATION);

            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                LOG.error("TrayIcon could not be added.", e);
            }
        } else {
            JOptionPane.showMessageDialog(null,
                    this.bundle.getString("systemtray.notsupported.text"),
                    this.bundle.getString("systemtray.notsupported.title"),
                    JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg != null) {
            if (!(arg instanceof Exception)) {
                lastCitation = (String) arg;
                trayIcon.displayMessage(bundle.getString(Constants.TITLE), arg.toString(), TrayIcon.MessageType.NONE);
            } else {
                Exception e = (Exception) arg;
                trayIcon.displayMessage("Fehler!", e.getLocalizedMessage(), TrayIcon.MessageType.ERROR);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        LOG.debug("ActionEventCommand: {}", e.getActionCommand());
        LOG.debug("ActionEvent: {}", e);

        if (null != e.getActionCommand()) {
            switch (e.getActionCommand()) {
                case Constants.QUIT:
                    SystemTray.getSystemTray().remove(trayIcon);
                    System.exit(0);
                case Constants.CITATION:
                    this.copyCitationToClipboard();
                    break;
                default:
                    this.controller.actionPerformed(e);
                    break;
            }
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (Constants.PAUSE.equals(e.getItem())) {
            this.controller.setTimerActive(e.getStateChange() == ItemEvent.DESELECTED);
        }
    }

    private void copyCitationToClipboard() {
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(lastCitation), null);
    }
}
