package practice_8;

import javafx.scene.image.Image;

import java.io.Serializable;

/**
 * Created by swanta on 27.08.16.
 */
public class User implements Serializable{
    String Name;
    String pathToPhoto;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPathToPhoto() {
        return pathToPhoto;
    }

    public void setPathToPhoto(String pathToPhoto) {
        this.pathToPhoto = pathToPhoto;
    }
}
