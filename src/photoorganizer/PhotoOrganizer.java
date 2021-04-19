/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package photoorganizer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author LJChao-PC
 */
public class PhotoOrganizer extends Application {

    public void start(Stage primaryStage) throws IOException {
        UI ui = new UI("C:/Users/LJChao-PC/Desktop/Pack 1/img", primaryStage);
        ui.load();
    }



    public static void main(String[] args) {
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

class UI{
    private File filePath;
    private Stage primaryStage;
    
    public UI(String filePath, Stage primaryStage){
        this.filePath = new File(filePath);
        this.primaryStage = primaryStage;
    }
    
    public void load() throws IOException{
        createAppUI();
    }

    public File getFilePath() {
        return filePath;
    }

    public void setFilePath(File filePath) {
        this.filePath = filePath;
    }
    
    public void createAppUI() throws IOException {
        BorderPane bPane = new BorderPane();
        VBox vb;
        HBox hb;
        Text txt;

        //Top
        hb = new HBox();
        Button button;
        button = new Button("<");
        hb.getChildren().add(button);
        button = new Button(">");
        hb.getChildren().add(button);
        button = new Button("^");
        hb.getChildren().add(button);
        TextField txtf = new TextField(filePath.getPath());
        HBox.setHgrow(txtf, Priority.ALWAYS);
        hb.getChildren().add(txtf);
        hb.setSpacing(10);
        bPane.setTop(hb);

        //Center
        hb = new HBox();
        for (int i = 0; i < filePath.listFiles().length; i++) {
            Image image;
            ImageView imageView = new ImageView();
            vb = new VBox();
            if (filePath.listFiles()[i].getName().endsWith(".jpg")) {
                vb = new VBox();
                //Imagen
                image = new Image(new FileInputStream(filePath.listFiles()[i]));
                imageView = new ImageView(image);
                imageView.setPreserveRatio(true);
                imageView.setFitWidth(200);
                
                
                //Nombre Imagen
                txt = new Text(filePath.listFiles()[i].getName());
                vb.getChildren().addAll(imageView,txt);
                vb.setAlignment(Pos.BASELINE_CENTER);
                
                //txt = new Text("File: " + f.listFiles()[i].getName() + " Last Time Modified: " + date.format(f.listFiles()[i].lastModified()));
            }//else if(f.listFiles()[i].isDirectory()){
            //txt = new Text("Directory: " + f.listFiles()[i].getName());
            //}
            //vb.getChildren().add(txt);
            hb.getChildren().add(vb);
        }
        
        hb.setSpacing(50);
        bPane.setCenter(hb);

        vb = new VBox();
        txt = new Text("Tree");
        vb.getChildren().add(txt);
        bPane.setLeft(vb);

        //End
        Scene scene = new Scene(bPane, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}