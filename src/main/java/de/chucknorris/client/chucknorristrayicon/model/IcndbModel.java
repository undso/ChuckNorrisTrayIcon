package de.chucknorris.client.chucknorristrayicon.model;

import java.util.List;

/**
 *
 * @author Alexander Friedrichs
 */
public class IcndbModel {

    public static final String ID = "id";
    public static final String CATEGORIES = "categories";
    public static final String JOKE = "joke";
    public static final String TYPE = "type";
    public static final String VALUE = "value";
    public static final String SUCCESS = "success";
    private long id;
    private String type;
    private String joke;
    private List<String> categories;

    public IcndbModel() {
    }

    public IcndbModel(long id, String type, String joke, List<String> categories) {
        this.id = id;
        this.type = type;
        this.joke = joke;
        this.categories = categories;
    }

    @Override
    public String toString() {
        if (this.type == null || this.type.isEmpty()) {
            return super.toString();
        } else if (!SUCCESS.equals(this.type)) {
            return this.type;
        } else {
            return this.joke;
        }
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof IcndbModel)
                ? (this.hashCode() == obj.hashCode())
                : false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 83 * hash + (this.type != null ? this.type.hashCode() : 0);
        hash = 83 * hash + (this.joke != null ? this.joke.hashCode() : 0);
        hash = 83 * hash + (this.categories != null ? this.categories.hashCode() : 0);
        return hash;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getJoke() {
        return joke;
    }

    public void setJoke(String joke) {
        this.joke = joke;
    }
}