package XQuery;
import org.antlr.v4.runtime.*;
import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.dom4j.util.NodeComparator;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.io.*;

public class Main {
    public static void main (String[] args) throws IOException{
        try
        {
            String XQuery = new String ( Files.readAllBytes( Paths.get("input.txt") ) );
            ANTLRInputStream in = new ANTLRInputStream(XQuery);
            XQueryLexer lexer = new XQueryLexer(in);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            XQueryParser parser = new XQueryParser(tokens);
            MyXQueryVisitor visitor = new MyXQueryVisitor();
            ArrayList res = visitor.visit(parser.xq());
            Output out = new Output("output.txt", XQuery);
            out.printXML(res);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static ArrayList<Node> evalRewrited (String rewrited) {
        ArrayList<Node> res = new ArrayList<>();
        try {
            ANTLRInputStream in = new ANTLRInputStream(rewrited);
            XQueryLexer lexer = new XQueryLexer(in);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            XQueryParser parser = new XQueryParser(tokens);
            MyXQueryVisitor visitor = new MyXQueryVisitor();
            visitor.needRewrite = false;
            res = visitor.visit(parser.xq());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
}