/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package photoorganizer;

import javax.xml.parsers.DocumentBuilderFactory;  
import javax.xml.parsers.DocumentBuilder;  
import org.w3c.dom.Document;  
import org.w3c.dom.NodeList;  
import org.w3c.dom.Node;  
import org.w3c.dom.Element;  
import java.io.File;  
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.xml.sax.SAXException;

/**
 *
 * @author LJ
 */
public class FavEditor {
    File file;
    DocumentBuilderFactory dbf; 
    DocumentBuilder db;
    Document doc;
    NodeList nodeList;  
    Element root;
    ArrayList<String> allImages;

    public FavEditor() throws SAXException, IOException, ParserConfigurationException {
        this.file = new File("./src/photoorganizer/fav.xml");
        dbf = DocumentBuilderFactory.newInstance();
        db = dbf.newDocumentBuilder();  
        doc = db.parse(file); 
        root = doc.getDocumentElement();
        doc.getDocumentElement().normalize();
        nodeList = doc.getElementsByTagName("imagen");
        reloadArray();
    }
    
    public void reloadArray(){
        nodeList = doc.getElementsByTagName("imagen");
        allImages = new ArrayList<String>();
        for(int i = 0; i < nodeList.getLength(); i++){
            Node node = nodeList.item(i);
            Element e = (Element) node;  
            String path = e.getElementsByTagName("path").item(0).getTextContent().replaceAll("\\s", "");
            allImages.add(path);
            System.out.println(path);
        }
    }
    
    public ArrayList<String> getAllImages(){
        return allImages;
    }
    
    public void addElement(String newImage) throws TransformerException{
        Element newImagen = doc.createElement("imagen");
        Element newPath = doc.createElement("path");
        newPath.setTextContent(newImage);
        newImagen.appendChild(newPath);
        root.appendChild(newImagen);
        saveFile();
        reloadArray();
    }
    public void deleteElement(){
        
        reloadArray();
    }
    public boolean contains(String check){
        return allImages.contains(check);
    }
    
    public void saveFile() throws TransformerConfigurationException, TransformerException{
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        Result output = new StreamResult(file); // xml is a object of File i.e. File xml = new File(filePath);
        Source input = new DOMSource(doc);
        transformer.transform(input, output);
    }
}
