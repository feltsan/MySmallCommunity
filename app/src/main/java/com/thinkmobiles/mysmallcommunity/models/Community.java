package com.thinkmobiles.mysmallcommunity.models;

/**
 * Created by dreamfire on 18.11.15.
 */
public class Community {
    private String id;
    private String idEmirates;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdEmirates() {
        return idEmirates;
    }

    public void setIdEmirates(String idEmirates) {
        this.idEmirates = idEmirates;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name.toString();
    }
}
