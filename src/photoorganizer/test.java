package photoorganizer;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;


public class test{
    public static void main (String args[]) throws SAXException, IOException, ParserConfigurationException, TransformerException{
        FavEditor fe = new FavEditor();
        fe.addElement("NEW TEST");
        System.out.println(fe.getAllImages().toString());
        
    }
}