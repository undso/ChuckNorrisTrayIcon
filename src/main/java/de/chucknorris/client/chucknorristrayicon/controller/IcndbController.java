package de.chucknorris.client.chucknorristrayicon.controller;

import com.google.gson.Gson;
import de.chucknorris.client.chucknorristrayicon.model.IcndbResponse;

/**
 *
 * @author Alexander Friedrichs
 */
public class IcndbController {

    private final Gson _gson;

    public IcndbController() {
        this._gson = new Gson();
    }

    public IcndbResponse createIcndbResponse(String jsonString) {
        return _gson.fromJson(jsonString, IcndbResponse.class);
    }

}
