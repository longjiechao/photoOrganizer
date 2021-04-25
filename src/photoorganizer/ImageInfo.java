/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package photoorganizer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author LJ
 */
//Classe que contiene toda la informacion de la imagen: la imagen, su nombre, fecha, etc
public class ImageInfo extends ImageView{
    private File filePath;
    public ImageInfo(File filePath) throws FileNotFoundException {
        super(new Image(new FileInputStream(filePath)));
        this.filePath = filePath;
    }
    
    public File getFile(){
       return this.filePath;
    }
    
}
