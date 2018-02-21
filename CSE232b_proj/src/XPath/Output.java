package XPath;

import org.dom4j.Attribute;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Output {
    String filename;
    String XPath;

    public Output(String filename, String XPath){
        this.filename = filename;
        this.XPath = XPath;
    }

    public void printAtt(ArrayList<Attribute> res) throws IOException{
        System.out.println("Here is printAtt!");
        try {
            for (Attribute att : res) {
                System.out.print("Attribute=");
                System.out.println("\'" + att.getName() + "=" + att.getValue() + "\'");
            }
        }
        catch (Exception e){
            System.out.println("error in printAtt");
            throw e;
        }
    }

    public void printText(ArrayList<String> res) throws IOException{
        XMLWriter writer = new XMLWriter(new FileWriter(this.filename));
        System.out.println("Here is printText!");
        try {
            for (String text : res) {
                System.out.print("Text=");
                System.out.println(text);
            }
        }
        catch (Exception e){
            System.out.println("error in printText");
            throw e;
        }
    }

    public void printNode(ArrayList<Node> res) throws IOException{
        System.out.println("Here is printNode!");
        /*XMLWriter writer = new XMLWriter(new FileWriter(this.filename));
        try{
            for(Node node : res) {
                writer.write(node);
            }
            writer.close();
        } catch (Exception e){
            System.out.println(e);
        }*/
        // Pretty print the document to System.out
        XMLWriter writer;
        OutputFormat format = OutputFormat.createPrettyPrint();
        writer = new XMLWriter(System.out, format);
        try{
            for(Node node : res) {
                writer.write(node);
            }
            writer.close();
        } catch (Exception e){
            System.out.println("error in printNode");
            throw e;
        }
    }

    public void printXML(ArrayList res){
        try{
            if(XPath.length() >= 6 && XPath.substring(XPath.length()-6).equals("text()"))
                this.printText(res);
            else if(XPath.matches(".*\\[^\\[]@.*"))
                this.printAtt(res);
            else
                this.printNode(res);
        }
        catch(Exception e){
            System.out.println(e);
        }

    }
}
