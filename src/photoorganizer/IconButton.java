/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package photoorganizer;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author LJChao
 */
public class IconButton extends Button{
    public IconButton(Image iv) {
        ImageView image = new ImageView(iv);
        image.setFitHeight(20);
        image.setFitHeight(20);
        image.setPreserveRatio(true);
        setGraphic(image);
    }
}
