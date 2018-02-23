package XPath;

import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Output {
    String filename;
    String inputStream;

    public Output(String filename, String XPath){
        this.filename = filename;
        this.inputStream = XPath;
    }

    public void printElement(XMLWriter writer, Element element) throws IOException{
        //System.out.println("Here is printElement!");
        try{
            writer.write(element);
        } catch (Exception e){
            System.out.println("error in printNode");
            throw e;
        }
    }

    public void printAtt(XMLWriter writer, Attribute att) throws IOException{
        //System.out.println("Here is printAtt!");
        try {
            System.out.print("Attribute=");
            System.out.println("\'" + att.getName() + "=" + att.getValue() + "\'");
        }
        catch (Exception e){
            System.out.println("error in printAtt");
            throw e;
        }
    }

    public void printText(String text) throws IOException{
        //System.out.println("Here is printText!");
        try {
            System.out.print("Text=");
            System.out.println(text);
        }
        catch (Exception e){
            System.out.println("error in printText");
            throw e;
        }
    }

    public void printXML(ArrayList res){
        try{
            OutputFormat format = OutputFormat.createPrettyPrint();
            XMLWriter writer = new XMLWriter(System.out, format);
            //writer = new XMLWriter(new FileWriter(this.filename));
            for(Object obj : res){
                if(obj instanceof String)
                    this.printText((String)obj);
                else if(obj instanceof Element)
                    this.printElement(writer, (Element)obj);
                else if(obj instanceof Attribute)
                    this.printAtt(writer, (Attribute)obj);
            }
            writer.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
}
