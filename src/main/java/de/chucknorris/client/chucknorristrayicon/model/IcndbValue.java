package de.chucknorris.client.chucknorristrayicon.model;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Alexander Friedrichs <friedrichs.alexander@gmail.com>
 */
public class IcndbValue implements Serializable {

    private static final long serialVersionUID = -7435034249914034153L;

    private long id;
    private String joke;
    private List<String> categories;

    public IcndbValue() {
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("IcndbValue{ ");
        builder.append("id : ").append(id).append(", ");
        builder.append("joke : ").append(joke).append(", ");
        builder.append("categories : [");
        boolean first = true;
        for (String string : categories) {
            if (first) {
                builder.append(string);
                first = false;
            } else {
                builder.append(",").append(string);
            }
        }
        builder.append("]").append("}");
        return builder.toString();
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the joke
     */
    public String getJoke() {
        return joke;
    }

    /**
     * @param joke the joke to set
     */
    public void setJoke(String joke) {
        this.joke = joke;
    }

    /**
     * @return the categories
     */
    public List<String> getCategories() {
        return categories;
    }

    /**
     * @param categories the categories to set
     */
    public void setCategories(List<String> categories) {
        this.categories = categories;
    }
}
