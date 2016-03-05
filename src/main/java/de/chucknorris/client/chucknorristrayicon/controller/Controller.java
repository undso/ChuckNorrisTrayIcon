package de.chucknorris.client.chucknorristrayicon.controller;

import de.chucknorris.client.chucknorristrayicon.callbackhandler.SettingsChangeCallBackHandler;
import de.chucknorris.client.chucknorristrayicon.callbackhandler.CitationCallBackHandler;
import de.chucknorris.client.chucknorristrayicon.model.IcndbResponse;
import de.chucknorris.client.chucknorristrayicon.producer.CitationProducer;
import de.chucknorris.client.chucknorristrayicon.settings.Constants;
import de.chucknorris.client.chucknorristrayicon.producer.RESTfulCNCitationProducer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import javax.swing.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Alexander Friedrichs
 */
public class Controller extends Observable implements ActionListener, CitationCallBackHandler, SettingsChangeCallBackHandler {

    private static final Logger LOG = LoggerFactory.getLogger(Controller.class);

    private final ConfigDialogController _configDialogController;
    private CitationProducer citations;
    private Timer timer;
    private String citation;

    public Controller(ConfigDialogController configDialogController) {
        this._configDialogController = configDialogController;
    }

    public void createTimer() {
        if (this.timer != null) {
            this.timer.stop();
        }
        this.timer = getNewTimer();
        LOG.debug("Timer created: {}", this.timer);
    }

    private Timer getNewTimer() {
        String delay = System.getProperty(Constants.DELAY, Constants.DEFAULT_DELAY);
        Timer t = new Timer(Integer.parseInt(delay), this);
        t.setInitialDelay(1);
        t.setRepeats(true);
        t.setActionCommand(Constants.TIMERACTIONCOMMAND);
        return t;
    }

    public void start() {
        this.timer.start();
        LOG.info("timer started {}", timer.getDelay());
    }

    public void showConfigScreen() {
        this._configDialogController.showConfigScreen(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        LOG.debug("actionPerformed -> Event: {}, Command: {}", new Object[]{e, e.getActionCommand()});

        if (e.getActionCommand() != null) {
            switch (e.getActionCommand()) {
                case Constants.TIMERACTIONCOMMAND:
                case Constants.NEWCITATION:
                    new Thread(getCitations()).start();
                    LOG.debug("Citation Thread stated.");
                    break;
                case Constants.CONFIGSCREEN:
                    this.showConfigScreen();
                    break;
                default:
                    LOG.error("Unknown ActionEvent");
                    break;
            }
        }

    }

    public void setTimerActive(boolean active) {
        String status = (active) ? "active" : "inactive";
        LOG.info("Set Timer to {}", status);
        if (active && !timer.isRunning()) {
            timer.start();
        } else if (!active && timer.isRunning()) {
            timer.stop();
        }
    }

    protected CitationProducer getCitations() {
        if (this.citations == null) {
            this.citations = new RESTfulCNCitationProducer();
            this.citations.setCallBackHandler(this);

            LOG.debug("CNCitationProducer created {}", this.citations);

        }
        return citations;
    }

    @Override
    public void callBackCitation(Object obj) {
        if (obj instanceof Exception) {
            // TODO Error Handling
            LOG.error("Exception while creating Citations", obj);
            setChanged();
            notifyObservers(obj);
        } else {
            this.citation = ((IcndbResponse) obj).getValue().getJoke();
            this.citation = this.citation.replace("&quot;", "\"");
            setChanged();
            notifyObservers(this.citation);
        }
    }

    @Override
    public void callBackSettingsChange() {
        this.createTimer();
        this.start();
        LOG.debug("New timer started.");
    }
}
