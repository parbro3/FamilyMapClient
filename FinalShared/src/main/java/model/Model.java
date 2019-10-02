package model;

import java.util.UUID;

/**
 * Created by Parker on 2/17/18.
 */

public class Model {

    private String ID;

    public String generateID()
    {
        return UUID.randomUUID().toString();
    }

    public String getID() { return ID; }

    public void setID(String ID) {
        this.ID = ID;
    }

}
