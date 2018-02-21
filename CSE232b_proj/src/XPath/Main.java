package XPath;
import org.antlr.v4.runtime.*;

import java.util.ArrayList;
import java.io.*;

public class Main {
    public static void main (String[] args) throws IOException{
        //String XPath = "doc(\" test.txt \")/class/student[firstname == ../student/firstname]";
        String XPath = "doc(\"j_caesar.xml\")//ACT[(./TITLE)==(./TITLE)]/*/SPEECH/../TITLE";
        ANTLRInputStream in = new ANTLRInputStream(XPath);
        XPathLexer lexer = new XPathLexer(in);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        XPathParser parser = new XPathParser(tokens);
        MyXPathVisitor visitor = new MyXPathVisitor();
        ArrayList res = visitor.visit(parser.ap());
        Output out = new Output("output.txt", XPath);
        out.printXML(res);
    }
}
