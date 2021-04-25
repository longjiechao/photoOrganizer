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
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
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
        Path path = Paths.get("./src/photoorganizer/fotoTest");
        File directory = new File(path.toAbsolutePath().normalize().toString());
        UI ui = new UI(directory.getAbsolutePath(), primaryStage);
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
    private boolean fav = false;
    private ArrayList<File> directories = new ArrayList<File>();
    private int directoriesIndex = -1;
    private TextField field;
    
    public UI(String filePath, Stage primaryStage){
        this.filePath = new File(filePath);
        this.primaryStage = primaryStage;
    }

    public TextField getField() {
        return field;
    }

    public void setField(TextField field) {
        this.field = field;
    }
    
    
    
    public void load() throws IOException{
        createAppUI();
    }

    public File getFilePath() {
        return filePath;
    }
    public void setFilePath(File filePath){
        this.filePath = filePath;
    }
    
    public void createAppUI() throws IOException {
        BorderPane bPane = new BorderPane();
        VBox vb;
        HBox hb;
        Text txt;
        Image image;

        //Top
        hb = new HBox();
        Button button;
        IconButton iButton;
        //BACK
        image = new Image(new FileInputStream("./src/icons/back.png"));
        iButton = new IconButton(image);
        hb.getChildren().add(iButton);
        iButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                System.out.println("INDEXAAA: " + directoriesIndex);
                System.out.println("SIZEAAA: " + directories.size());
                if(!directories.isEmpty()){
                    if(directoriesIndex == directories.size()-1){
                        System.out.println("EYYYYY");
                        directories.add(getFilePath());
                        setFilePath(directories.get(directoriesIndex));
                        directoriesIndex--;
                        try {
                            load();
                        } catch (IOException ex) {
                            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }else if(directoriesIndex > -1){
                        setFilePath(directories.get(directoriesIndex));
                        directoriesIndex--;
                        try {
                            load();
                        } catch (IOException ex) {
                            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } 
                }

                for(int i = 0; i < directories.size(); i++){
                    System.out.println("I: " + i + " = " + directories.get(i));
                }
                System.out.println("INDEX: " + directoriesIndex);
                System.out.println("SIZE: " + directories.size());
            }
        });
        //FORWARD
        image = new Image(new FileInputStream("./src/icons/forward.png"));
        iButton = new IconButton(image);
        iButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                if(directoriesIndex < directories.size()-1 && !directories.isEmpty()){
                    System.out.println("PATH: "+directories.get(directoriesIndex+1).getPath());
                    setFilePath(directories.get(directoriesIndex+1));
                    directoriesIndex++;
                    try {
                        load();
                    } catch (IOException ex) {
                        Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        hb.getChildren().add(iButton);
        //PARENT
        image = new Image(new FileInputStream("./src/icons/parent.png"));
        iButton = new IconButton(image);
        iButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                if(getFilePath().getParentFile() != null){
                    System.out.println(directoriesIndex);
                    if(directoriesIndex == directories.size()-1 && !directories.isEmpty()){
                        directories.add(getFilePath());
                        directoriesIndex++;
                    }else if(directoriesIndex < directories.size()-1){
                        directories.subList(directoriesIndex, directories.size()).clear();
                        directories.add(getFilePath());
                        directoriesIndex++;
                    }else{
                        directories.add(getFilePath());
                        directoriesIndex++;
                    }
                    setFilePath(new File(getFilePath().getParentFile().getAbsolutePath()));
                    try {
                        load();
                    } catch (IOException ex) {
                        Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else{
                    System.out.println("No puedes salirte más");
                }
            }
        });
        hb.getChildren().add(iButton);
        TextField txtf = new TextField(this.filePath.getPath());
        setField(txtf);
        HBox.setHgrow(txtf, Priority.ALWAYS);
        hb.getChildren().add(txtf);
        
        //RELOAD
        image = new Image(new FileInputStream("./src/icons/reload.png"));
        iButton = new IconButton(image);
        iButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                File reloadFile = new File(getField().getText());
                if(directoriesIndex == directories.size()-1 && !directories.isEmpty()){
                    directories.add(getFilePath());
                    directoriesIndex++;
                }else if(directoriesIndex < directories.size()-1){
                    directories.subList(directoriesIndex, directories.size()).clear();
                    directories.add(getFilePath());
                    directoriesIndex++;
                }else{
                    directories.add(getFilePath());
                    directoriesIndex++;
                }
                setFilePath(reloadFile);
                try {
                    load();
                } catch (IOException ex) {
                    Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        hb.getChildren().add(iButton);
        hb.setSpacing(10);
        bPane.setTop(hb);

        //Center
        if (Files.exists(this.filePath.toPath())) {
            ScrollPane scroll = new ScrollPane();
            FlowPane fp = new FlowPane();
            for (int i = 0; i < this.filePath.listFiles().length; i++) {
                ImageView imageView;
                DirectoryView directoryView;
                vb = new VBox();
                if (this.filePath.listFiles()[i].getName().endsWith(".jpg")) {
                    //Imagen
                    image = new Image(new FileInputStream(this.filePath.listFiles()[i]));
                    imageView = new ImageView(image);
                    imageView.setPreserveRatio(true);
                    imageView.setFitWidth(100);
                    imageView.setFitHeight(100);

                    imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            if(event.getButton().equals(MouseButton.PRIMARY)){
                                if(event.getClickCount() == 2){
                                    System.out.println("Double clicked");
                                }
                            }
                        }
                    });

                    //Nombre Imagen
                    txt = new Text(filePath.listFiles()[i].getName());
                    vb.getChildren().addAll(imageView,txt);
                    vb.setAlignment(Pos.BASELINE_CENTER);
                    fp.getChildren().add(vb);

                }else if(filePath.listFiles()[i].isDirectory()){
                    image = new Image(new FileInputStream("./src/icons/folder.png"));
                    directoryView = new DirectoryView(image, filePath.listFiles()[i]);
                    directoryView.setPreserveRatio(true);
                    directoryView.setFitWidth(100);
                    directoryView.setFitHeight(100);
                    String name = filePath.listFiles()[i].getName();
                    if(name.length() > 10){
                        name = name.substring(0, 10)+"...";
                    }
                    txt = new Text(name);
                    directoryView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            if(event.getButton().equals(MouseButton.PRIMARY)){
                                if(event.getClickCount() == 2){
                                    if(directoriesIndex == directories.size()-1 && !directories.isEmpty()){
                                        directories.add(getFilePath());
                                        directoriesIndex++;
                                    }else if(directoriesIndex < directories.size()-1){
                                        directories.subList(directoriesIndex+1, directories.size()).clear();
                                        directories.add(getFilePath());
                                        directoriesIndex++;
                                    }else{
                                        directories.add(getFilePath());
                                        directoriesIndex++;
                                    }
                                    
                                    DirectoryView dv = (DirectoryView)event.getSource();
                                    setFilePath(dv.getDirectoryPath());
                                    System.out.println(dv.getDirectoryPath());
                                    try {
                                        load();
                                    } catch (IOException ex) {
                                        Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            }
                        }
                    });

                    //Nombre Imagen
                    vb.getChildren().addAll(directoryView,txt);
                    vb.setAlignment(Pos.BASELINE_CENTER);
                    fp.getChildren().add(vb);
                }
            }

            fp.setHgap(10);
            fp.setVgap(10);
            scroll.setFitToHeight(true);
            scroll.setFitToWidth(true);
            scroll.setContent(fp);
            
            bPane.setCenter(scroll);
        }else{
            txt = new Text("No se ha encontrado la ruta");
            bPane.setCenter(txt);
        }
        

        vb = new VBox();
        txt = new Text("Tree");
        vb.getChildren().add(txt);
        bPane.setLeft(vb);

        //End
        Scene scene = new Scene(bPane, 800, 500);
        
        primaryStage.getIcons().add(new Image(new FileInputStream("./src/icons/ThePolaroid.png")));
        primaryStage.setTitle("Photo Organizer - Longjie Chao");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}