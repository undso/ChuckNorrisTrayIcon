package de.chucknorris.client.chucknorristrayicon.producer;

import de.chucknorris.client.chucknorristrayicon.callbackhandler.CitationCallBackHandler;

/**
 *
 * @author Alexander Friedrichs
 */
public interface CitationProducer extends Runnable{
    public Object getCitation();
    public void setCallBackHandler(CitationCallBackHandler callBackHandler);
}
