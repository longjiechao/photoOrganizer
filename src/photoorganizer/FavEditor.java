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
import javax.xml.parsers.ParserConfigurationException;
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

    public FavEditor() throws SAXException, IOException, ParserConfigurationException {
        this.file = new File("./src/photoorganizer/fav.xml");
        dbf = DocumentBuilderFactory.newInstance();
        db = dbf.newDocumentBuilder();  
        doc = db.parse(file);  
        doc.getDocumentElement().normalize(); 
        nodeList = doc.getElementsByTagName("imagen");
    }
    
    public void test(){
        for(int i = 0; i < nodeList.getLength(); i++){
            Node node = nodeList.item(i);
            Element e = (Element) node;  
            String path = e.getElementsByTagName("path").item(0).getTextContent().replaceAll("\\s", "");
            System.out.println("PATH: " + path); 
        }
        
        
    }
    
}
