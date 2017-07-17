package javafx.view.image;

import javafx.beans.NamedArg;
import javafx.scene.image.Image;

import java.io.File;

/**
 * Created by nikitap4.92@gmail.com
 * 22.04.17.
 */
public abstract class AbstractImage extends Image {

    private static final String ABSOLUTE_PATH = "file:///" + System.getProperty("user.dir") + File.separator + "pic" + File.separator ;

    AbstractImage(String url) {
        super(ABSOLUTE_PATH + url + ".png");
    }
}
