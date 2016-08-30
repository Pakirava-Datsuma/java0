package practice_8;

import java.io.Serializable;

/**
 * Created by swanta on 27.08.16.
 */
public class User implements Serializable{
    String name = "";
    String pathToPhoto = "";


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPathToPhoto() {
        return pathToPhoto;
    }

    public void setPathToPhoto(String pathToPhoto) {
        this.pathToPhoto = pathToPhoto;
    }
}
