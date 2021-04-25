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
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
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
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;

/**
 *
 * @author LJChao-PC
 */
public class PhotoOrganizer extends Application {

    public void start(Stage primaryStage) throws IOException, SAXException, ParserConfigurationException{
        Path path = Paths.get("./src/photoorganizer/fotoTest");
        File directory = new File(path.toAbsolutePath().normalize().toString());
        UI ui = new UI(directory.getAbsolutePath(), primaryStage, 1000, 500);
        ui.load();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}

class UI{
    private int altura;
    private int anchura;
    private File filePath; //Directorio actual
    private Stage primaryStage; //STAGE
    private ArrayList<File> directories = new ArrayList<File>(); //Guarda el "historial" de navegacion, sirve para los botones de BAKC y FORWARD
    private int directoriesIndex = -1; //INDEX de indica en que posición del ArrayList estás
    private TextField field; //coge el campo TextField, que se usa para navegar
    private ImageInfo currentImgInfo;
    private BorderPane infoImagen; //Box que muestra toda la informacion de la imagen
    private static final double scale = 0.7; //escala de imagen por defecto
    
    private FavEditor fe;
    
    public UI(String filePath, Stage primaryStage, int altura, int anchura) throws IOException, SAXException, ParserConfigurationException{
        this.filePath = new File(filePath);
        this.primaryStage = primaryStage;
        this.altura = altura;
        this.anchura = anchura;
        fe = new FavEditor();
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public int getAnchura() {
        return anchura;
    }

    public void setAnchura(int anchura) {
        this.anchura = anchura;
    }
    
    public ImageInfo getCurrentImgInfo() {
        return currentImgInfo;
    }

    public void setCurrentImgInfo(ImageInfo currentImgInfo) {
        this.currentImgInfo = currentImgInfo;
    }

    public TextField getField() {
        return field;
    }

    public void setField(TextField field) {
        this.field = field;
    }
    
    public void load() throws IOException, SAXException, ParserConfigurationException{
        createAppUI();
    }

    public File getFilePath() {
        return filePath;
    }
    public void setFilePath(File filePath){
        this.filePath = filePath;
    }
    
    public void createAppUI() throws IOException, SAXException, ParserConfigurationException {
        BorderPane bPane = new BorderPane();
        VBox vb;
        HBox hb;
        Text txt;
        Image image;

        //Top
        hb = new HBox();
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
                        } catch (SAXException ex) {
                            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ParserConfigurationException ex) {
                            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }else if(directoriesIndex > -1){
                        setFilePath(directories.get(directoriesIndex));
                        directoriesIndex--;
                        try {
                            load();
                        } catch (IOException ex) {
                            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (SAXException ex) {
                            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ParserConfigurationException ex) {
                            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } 
                }
                for(int i = 0; i < directories.size(); i++){
                    System.out.println("I: " + i + " = " + directories.get(i));
                }
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
                    } catch (SAXException ex) {
                        Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ParserConfigurationException ex) {
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
                        directories.subList(directoriesIndex+1, directories.size()).clear();
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
                    } catch (SAXException ex) {
                        Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ParserConfigurationException ex) {
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
                    directories.subList(directoriesIndex+1, directories.size()).clear();
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
                } catch (SAXException ex) {
                    Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ParserConfigurationException ex) {
                    Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        hb.getChildren().add(iButton);
        hb.setSpacing(10);
        bPane.setTop(hb);

        //Center
        //Mostrar los contenidos
        //Mostrar las imagenes
        if (Files.exists(this.filePath.toPath())) {
            ScrollPane scroll = new ScrollPane();
            FlowPane fp = new FlowPane();
            for (int i = 0; i < this.filePath.listFiles().length; i++) {
                ImageInfo imageInfo;
                DirectoryView directoryView;
                vb = new VBox();
                if (this.filePath.listFiles()[i].getName().endsWith(".jpg")) {
                    
                    //Imagen
                    imageInfo = new ImageInfo(this.filePath.listFiles()[i]);
                    imageInfo.setPreserveRatio(true);
                    imageInfo.setFitWidth(100);
                    imageInfo.setFitHeight(100);
                    
                    //Button de imagen. Al dar doble click aparece la imagen al lado
                    imageInfo.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            if(event.getButton().equals(MouseButton.PRIMARY)){
                                if(event.getClickCount() == 2){
                                    infoImagen = new BorderPane();
                                    currentImgInfo = (ImageInfo)event.getSource();
                                    try {
                                        VBox infoImagenBot = new VBox();
                                        HBox row;
                                        VBox column;
                                        //Mostrar Imagen
                                        column = new VBox();
                                        
                                        CheckBox cb = new CheckBox("Favorito");
                                        if(fe.contains(currentImgInfo.getFile().getAbsolutePath())){
                                            cb.setSelected(true);
                                        }else{
                                            cb.setSelected(false);
                                        }
                                        cb.selectedProperty().addListener(new ChangeListener<Boolean>() {
                                            @Override
                                            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                                                if(newValue == true){
                                                    System.out.println("Check");
                                                    try {
                                                        fe.addElement(currentImgInfo.getFile().toString());
                                                    } catch (TransformerException ex) {
                                                        Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
                                                    }
                                                }else{
                                                    System.out.println("Uncheck");
                                                    try {
                                                        fe.deleteElement(currentImgInfo.getFile().toString());
                                                    } catch (TransformerException ex) {
                                                        Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
                                                    }
                                                }
                                                try {
                                                    load();
                                                } catch (IOException ex) {
                                                    Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
                                                } catch (SAXException ex) {
                                                    Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
                                                } catch (ParserConfigurationException ex) {
                                                    Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
                                                }
                                            }
                                        });
                                        
                                        ScrollPane imageScroll = new ScrollPane();
                                        //imageI = new ImageInfo(imageSelected.getFile());
                                        currentImgInfo.setPreserveRatio(true);
                                        currentImgInfo.setFitWidth(getAnchura()*0.7);
                                        currentImgInfo.setFitHeight(getAltura()*0.7);
                                        imageScroll.setContent(currentImgInfo);
                                        imageScroll.setFitToHeight(true);
                                        imageScroll.setFitToWidth(true);
                                        imageScroll.setPrefViewportWidth(getAnchura()*0.7);
                                        //imageScroll.setPrefViewportHeight(getAnchura()/2);
                                        
                                        Slider slider = new Slider();
                                        slider.setMax(2);
                                        slider.setMin(0.5);
                                        slider.setOnMouseReleased(evento -> {
                                            currentImgInfo.setFitWidth(getAnchura()*scale*slider.getValue());
                                            currentImgInfo.setFitHeight(getAltura()*scale*slider.getValue());
                                            try {
                                                load();
                                            } catch (IOException ex) {
                                                Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
                                            } catch (SAXException ex) {
                                                Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
                                            } catch (ParserConfigurationException ex) {
                                                Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                                        });
                                        column.getChildren().addAll(cb, imageScroll, slider);
                                        
                                        infoImagen.setCenter(column);
                                        
                                        //Mostrar el nombre de la imagen
                                        row = new HBox();
                                        Text imageNombreTitulo = new Text("Fichero: ");
                                        imageNombreTitulo.setStyle("-fx-font-weight: bold;");
                                        Text imageNombre = new Text(currentImgInfo.getFile().getName());
                                        row.getChildren().addAll(imageNombreTitulo, imageNombre);
                                        infoImagenBot.getChildren().add(row);
                                        
                                        //Mostrar última modificacion
                                        row = new HBox();
                                        Text imageLastModTitulo = new Text("Última modificación: ");
                                        imageLastModTitulo.setStyle("-fx-font-weight: bold");
                                        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                                        Text imageLastMod = new Text(df.format(currentImgInfo.getFile().lastModified()));
                                        row.getChildren().addAll(imageLastModTitulo, imageLastMod);
                                        infoImagenBot.getChildren().add(row);
                                        
                                       
                                        infoImagen.setBottom(infoImagenBot);
                                        load();
                                    } catch (FileNotFoundException ex) {
                                        Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
                                    } catch (IOException ex) {
                                        Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
                                    } catch (SAXException ex) {
                                        Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
                                    } catch (ParserConfigurationException ex) {
                                        Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    
                                }
                            }
                        }
                    });

                    //Nombre Imagen
                    txt = new Text(filePath.listFiles()[i].getName());
                    vb.getChildren().addAll(imageInfo,txt);
                    vb.setAlignment(Pos.BASELINE_CENTER);
                    fp.getChildren().add(vb);
                //Mostrar los directorios
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
                                    } catch (SAXException ex) {
                                        Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
                                    } catch (ParserConfigurationException ex) {
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
        
        //Lateral izquierdo
        //Titulo
        vb = new VBox();
        hb = new HBox();
        ImageView starIcon = new ImageView(new Image(new FileInputStream("./src/icons/star.png")));
        starIcon.setFitHeight(18);
        starIcon.setFitHeight(18);
        starIcon.setPreserveRatio(true);
        txt = new Text("Favoritos");
        txt.setStyle("-fx-font-weight: bold; -fx-underline: true ;");
        hb.getChildren().addAll(starIcon, txt);
        vb.getChildren().add(hb);
        
        
        //Mostrar todos los favoritos
        ScrollPane sp = new ScrollPane();
        for(int i = 0; i < fe.getAllImages().size(); i++){
            Path path = Paths.get(fe.getAllImages().get(i));
            if (Files.exists(path)) {
                System.out.println(path.toAbsolutePath());
                hb = new HBox();
                ImageInfo imageIcon = new ImageInfo(path.toFile());
                imageIcon.setFitHeight(18);
                imageIcon.setFitHeight(18);
                imageIcon.setPreserveRatio(true);
                imageIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            if(event.getButton().equals(MouseButton.PRIMARY)){
                                if(event.getClickCount() == 2){
                                    infoImagen = new BorderPane();
                                    currentImgInfo = (ImageInfo)event.getSource();
                                    try {
                                        VBox infoImagenBot = new VBox();
                                        HBox row;
                                        VBox column;
                                        //Mostrar Imagen
                                        column = new VBox();
                                        
                                        CheckBox cb = new CheckBox("Favorito");
                                        if(fe.contains(currentImgInfo.getFile().getAbsolutePath())){
                                            cb.setSelected(true);
                                        }else{
                                            cb.setSelected(false);
                                        }
                                        
                                        cb.selectedProperty().addListener(new ChangeListener<Boolean>() {
                                            @Override
                                            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                                                if(newValue == true){
                                                    System.out.println("Check");
                                                    try {
                                                        fe.addElement(currentImgInfo.getFile().toString());
                                                    } catch (TransformerException ex) {
                                                        Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
                                                    }
                                                }else{
                                                    System.out.println("Uncheck");
                                                    try {
                                                        fe.deleteElement(currentImgInfo.getFile().toString());
                                                    } catch (TransformerException ex) {
                                                        Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
                                                    }
                                                }
                                                try {
                                                    load();
                                                } catch (IOException ex) {
                                                    Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
                                                } catch (SAXException ex) {
                                                    Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
                                                } catch (ParserConfigurationException ex) {
                                                    Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
                                                }
                                            }
                                        });
                                        
                                        ScrollPane imageScroll = new ScrollPane();
                                        //imageI = new ImageInfo(imageSelected.getFile());
                                        currentImgInfo.setPreserveRatio(true);
                                        imageScroll.setContent(currentImgInfo);
                                        imageScroll.setFitToHeight(true);
                                        imageScroll.setFitToWidth(true);
                                        imageScroll.setPrefViewportWidth(getAnchura()*scale);
                                        //imageScroll.setPrefViewportHeight(getAnchura()/2);
                                        
                                        Slider slider = new Slider();
                                        slider.setMax(2);
                                        slider.setMin(0.5);
                                        slider.setOnMouseReleased(evento -> {
                                            currentImgInfo.setFitWidth(getAnchura()*scale*slider.getValue());
                                            currentImgInfo.setFitHeight(getAltura()*scale*slider.getValue());
                                            try {
                                                load();
                                            } catch (IOException ex) {
                                                Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
                                            } catch (SAXException ex) {
                                                Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
                                            } catch (ParserConfigurationException ex) {
                                                Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                                        });
                                        column.getChildren().addAll(cb, imageScroll, slider);
                                        
                                        infoImagen.setCenter(column);
                                        
                                        //Mostrar el nombre de la imagen
                                        row = new HBox();
                                        Text imageNombreTitulo = new Text("Fichero: ");
                                        imageNombreTitulo.setStyle("-fx-font-weight: bold;");
                                        Text imageNombre = new Text(currentImgInfo.getFile().getName());
                                        row.getChildren().addAll(imageNombreTitulo, imageNombre);
                                        infoImagenBot.getChildren().add(row);
                                        
                                        //Mostrar última modificacion
                                        row = new HBox();
                                        Text imageLastModTitulo = new Text("Última modificación: ");
                                        imageLastModTitulo.setStyle("-fx-font-weight: bold");
                                        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                                        Text imageLastMod = new Text(df.format(currentImgInfo.getFile().lastModified()));
                                        row.getChildren().addAll(imageLastModTitulo, imageLastMod);
                                        infoImagenBot.getChildren().add(row);
                                        
                                       
                                        infoImagen.setBottom(infoImagenBot);
                                        load();
                                    } catch (FileNotFoundException ex) {
                                        Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
                                    } catch (IOException ex) {
                                        Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
                                    } catch (SAXException ex) {
                                        Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
                                    } catch (ParserConfigurationException ex) {
                                        Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    
                                }
                            }
                        }
                    });
                txt = new Text(path.getFileName().toString());
                hb.getChildren().addAll(imageIcon, txt);
                vb.getChildren().add(hb);
                sp.setContent(vb);
            }
        }
        
        bPane.setLeft(sp);
        
        //Lateral derecho
        bPane.setRight(infoImagen);
        
        //Abajo
        

        //End
        Scene scene = new Scene(bPane, getAltura(), getAnchura());
        
        primaryStage.getIcons().add(new Image(new FileInputStream("./src/icons/ThePolaroid.png")));
        primaryStage.setTitle("Photo Organizer - Longjie Chao");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}