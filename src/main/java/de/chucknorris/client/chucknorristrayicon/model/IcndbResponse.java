package de.chucknorris.client.chucknorristrayicon.model;

import java.io.Serializable;

/**
 *
 * @author Alexander Friedrichs <friedrichs.alexander@gmail.com>
 */
public class IcndbResponse implements Serializable {

    private static final long serialVersionUID = -8276234988961548525L;
    private static final String SUCCESS_TYPE = "success";

    private String type;
    private IcndbValue value;

    public IcndbResponse() {
    }

    public boolean isSuccess() {
        return SUCCESS_TYPE.equals(type);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("IcndbResponse { ");
        builder.append("type : ").append(type).append(", ");
        builder.append("value : ").append(value).append(" }");

        return builder.toString();
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the value
     */
    public IcndbValue getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(IcndbValue value) {
        this.value = value;
    }

}
