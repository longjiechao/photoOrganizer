/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package photoorganizer;

import java.io.File;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author LJ
 */
//Classe ImageView pero que coge el Path del directorio.

public class DirectoryView extends ImageView{
    private File directoryPath;
    
    public DirectoryView(Image image, File directoryPath) {
        super(image);
        this.directoryPath = directoryPath;
    }

    public File getDirectoryPath() {
        return directoryPath;
    }

    public void setDirectoryPath(File directoryPath) {
        this.directoryPath = directoryPath;
    }
    
}
