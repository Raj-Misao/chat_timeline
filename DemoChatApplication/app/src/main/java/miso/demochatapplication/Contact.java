package miso.demochatapplication;

/**
 * Created by Sonu on 29-Jul-17.
 */

public class Contact {
    String id, name;

    public Contact(String id, String name)
    {
        this.name = name;
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
