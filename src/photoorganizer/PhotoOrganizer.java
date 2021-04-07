/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package photoorganizer;

import java.io.File;
import java.text.SimpleDateFormat;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;


/**
 *
 * @author LJChao-PC
 */
public class PhotoOrganizer extends Application{

    public void start(Stage primaryStage) {
        File f = new File("C:/Users/LJChao-PC/Desktop/Pack 1/img");
        createGUI(primaryStage, f);
    }
    
    public void createGUI(Stage primaryStage, File f){
        SimpleDateFormat date = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        BorderPane bPane = new BorderPane();
        
        //Top
        VBox vb = new VBox();
        TextField txtf = new TextField(f.getPath());
        vb.getChildren().add(txtf);
        bPane.setTop(vb);
        
        //Center
        vb = new VBox();
        for(int i = 0; i < f.listFiles().length; i++){
            Text txt = new Text("");
            if(f.listFiles()[i].getName().endsWith(".jpg")){
                txt = new Text("File: " + f.listFiles()[i].getName() + " Last Time Modified: " + date.format(f.listFiles()[i].lastModified()));
            }else if(f.listFiles()[i].isDirectory()){
                txt = new Text("Directory: " + f.listFiles()[i].getName());
            }
            vb.getChildren().add(txt);
        }
        
        
        bPane.setCenter(vb);
        
        Scene scene = new Scene(bPane, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void main(String[] args){
        launch(args);
        /*File f1 = new File("C:\\Users\\LJChao-PC\\Desktop\\Pack 1\\img");
        SimpleDateFormat date = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        System.out.println(f1.getPath());
        for(int i = 0; i < f1.listFiles().length; i++){
            if(f1.listFiles()[i].getName().endsWith(".jpg")){
                System.out.println("File: " + f1.listFiles()[i].getName() + " Last Time Modified: " + date.format(f1.listFiles()[i].lastModified()));
            }else if(f1.listFiles()[i].isDirectory()){
                System.out.println("Directory: " + f1.listFiles()[i].getName());
            }
            else{
                System.out.println("ey");
            }
        }*/
    }
}
