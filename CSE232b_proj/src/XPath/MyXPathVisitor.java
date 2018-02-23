package XPath;

import org.dom4j.*;
import org.dom4j.util.NodeComparator;

import java.util.*;

public class MyXPathVisitor extends XPathBaseVisitor<ArrayList>{
    public ArrayList<Node> curState= new ArrayList();

    @Override
    public ArrayList visitDoc(XPathParser.DocContext ctx) {
        this.curState = this.visit(ctx.filename());
        return this.curState;
    }

    @Override
    public ArrayList visitSingleAP(XPathParser.SingleAPContext ctx) {
        this.curState = this.visit(ctx.doc());
        this.curState = this.visit(ctx.rp());
        return this.curState;
    }

    @Override
    public ArrayList visitDoubleAP(XPathParser.DoubleAPContext ctx) {
        this.curState = this.visit(ctx.doc());
        ArrayList<Node> res = new ArrayList<>();
        for (Node node : this.curState)
            doubleHelper(res, node);
        this.curState = res;
        this.curState = this.visit(ctx.rp());
        return this.curState;
    }

    public void doubleHelper(ArrayList<Node> res, Node cur){
        if(cur == null) return;
        res.add(cur);
        ArrayList<Node> list = (ArrayList<Node>)cur.selectNodes("*");
        for(Node node : list){
            doubleHelper(res, node);
        }
        return;
    }

    @Override
    public ArrayList visitBraceRP(XPathParser.BraceRPContext ctx) {
        this.curState = this.visit(ctx.rp());
        return this.curState;
    }

    @Override
    public ArrayList visitTextRP(XPathParser.TextRPContext ctx) {
        ArrayList<String> textList = new ArrayList<>();
        for(Node node : this.curState){
            textList.add(node.getText());
        }
        return textList;
    }

    @Override
    public ArrayList visitAttRP(XPathParser.AttRPContext ctx) {
        this.curState = this.visit(ctx.attName());
        return this.curState;
    }

    @Override
    public ArrayList visitParentRP(XPathParser.ParentRPContext ctx) {
        ArrayList<Node> parents = new ArrayList<>();
        for(Node node : this.curState){
            Element parent = node.getParent();
            if(!parents.contains(parent)){
                parents.add(parent);
            }
        }
        this.curState = parents;
        return this.curState;
    }

    @Override
    public ArrayList visitSelfRP(XPathParser.SelfRPContext ctx) {
        return this.curState;
    }

    @Override
    public ArrayList visitFilterRP(XPathParser.FilterRPContext ctx) {
        ArrayList<Node> oldPointer = this.visit(ctx.rp());
        ArrayList<Boolean> filterPointer = this.visit(ctx.f());
        ArrayList<Node> res = new ArrayList<>();
        Iterator<Node> oldIt = oldPointer.iterator();
        Iterator<Boolean> filIt = filterPointer.iterator();
        while(filIt.hasNext()&& oldIt.hasNext()){
            if(filIt.next()) {
                res.add(oldIt.next());
            }
            else oldIt.next();
        }
        this.curState = res;
        return this.curState;
    }

    @Override
    public ArrayList visitCommaRP(XPathParser.CommaRPContext ctx) {
        ArrayList<Node> prev = new ArrayList<Node>(this.curState);
        ArrayList<Node> first = this.visit(ctx.rp(0));
        this.curState = prev;
        ArrayList<Node> second = this.visit(ctx.rp(1));
        ArrayList<Node> total = new ArrayList<>();
        total.addAll(first);
        total.addAll(second);
        this.curState = total;
        return this.curState;
    }

    @Override
    public ArrayList visitChildrenRP(XPathParser.ChildrenRPContext ctx) {
        ArrayList<Node> children_next = new ArrayList<>();
        for (Node n : this.curState){
            children_next.addAll(n.selectNodes("*"));
        }
        this.curState = children_next;
        return this.curState;
    }

    @Override
    public ArrayList visitTagRP(XPathParser.TagRPContext ctx) {
        this.curState = this.visit(ctx.tagName());
        return this.curState;
    }

    @Override
    public ArrayList visitDoubleRP(XPathParser.DoubleRPContext ctx) {
        this.curState = this.visit(ctx.rp(0));
        ArrayList<Node> res = new ArrayList<>();
        for (Node node : this.curState)
            doubleHelper(res, node);
        this.curState = res;
        this.curState = this.visit(ctx.rp(1));
        LinkedHashSet<Node> tmp = new LinkedHashSet<Node>(this.curState);
        this.curState = new ArrayList<Node>(tmp);
        return this.curState;
    }

    @Override
    public ArrayList visitSingleRP(XPathParser.SingleRPContext ctx) {
        this.curState = this.visit(ctx.rp(0));
        this.curState = this.visit(ctx.rp(1));
        LinkedHashSet<Node> tmp = new LinkedHashSet<Node>(this.curState);
        this.curState = new ArrayList<Node>(tmp);
        return this.curState;
    }

    @Override
    public ArrayList visitNotFilter(XPathParser.NotFilterContext ctx) {
        ArrayList<Boolean> res = this.visit(ctx.f());
        for(int i=0 ; i < res.size() ; i++) {
            res.set(i, !res.get(i));
        }
        return res;
    }

    @Override
    public ArrayList visitAndFilter(XPathParser.AndFilterContext ctx) {
        ArrayList<Node> prev = new ArrayList<Node>(this.curState);
        ArrayList<Boolean> fil1List = this.visit(ctx.f(0));
        this.curState = prev;
        ArrayList<Boolean> fil2List = this.visit(ctx.f(1));
        Iterator<Boolean> fil1;
        Iterator<Boolean> fil2;
        ArrayList<Boolean> join = new ArrayList<>();
        for(fil1 = fil1List.iterator(), fil2 = fil2List.iterator() ;
            fil1.hasNext();){
            join.add(fil1.next() & fil2.next());
        }
        return join;
    }

    @Override
    public ArrayList visitRpFilter(XPathParser.RpFilterContext ctx) {
        ArrayList<Boolean> res = new ArrayList<>();
        ArrayList<Node> prevState = new ArrayList<Node>(this.curState);
        for(Node prevNode : prevState){
            this.curState = new ArrayList<Node>();
            this.curState.add(prevNode);
            if(!this.visit(ctx.rp()).isEmpty())
                res.add(true);
            else res.add(false);
        }
        return res;
    }

    @Override
    public ArrayList visitValueEQFilter(XPathParser.ValueEQFilterContext ctx) {
        ArrayList<Boolean> res = new ArrayList<Boolean>();
        ArrayList<Node> prev = new ArrayList<Node>(this.curState);
        ArrayList<Node> second_list = this.visit(ctx.rp(1));

        for(Node parent : prev){
            this.curState = new ArrayList<Node>(Arrays.asList(parent));
            ArrayList<Node> first_list = this.visit(ctx.rp(0));
            res.add(this.valueCompare(first_list, second_list));
        }
        return res;
    }

    private Boolean valueCompare(ArrayList<Node> list1, ArrayList<Node> list2){
        NodeComparator v = new NodeComparator();
        for(Node node1 : list1) {
            for (Node node2 : list2) {
                if (v.compare(node1, node2) == 0)
                    return true;
            }
        }
        return false;
    }

    @Override
    public ArrayList visitIdEQFilter(XPathParser.IdEQFilterContext ctx) {
        ArrayList<Boolean> res = new ArrayList<Boolean>();
        ArrayList<Node> prev = new ArrayList<Node>(this.curState);
        ArrayList<Node> second_list = this.visit(ctx.rp(1));

        for(Node parent : prev){
            this.curState = new ArrayList<Node>(Arrays.asList(parent));
            ArrayList<Node> first_list = this.visit(ctx.rp(0));
            res.add(this.IdCompare(first_list, second_list));
        }
        return res;
    }

    private Boolean IdCompare(ArrayList<Node> list1, ArrayList<Node> list2){
        for(Node node1 : list1) {
            for (Node node2 : list2) {
                if (node1.getUniquePath().equals(node2.getUniquePath()))
                    return true;
            }
        }
        return false;
    }

    @Override
    public ArrayList visitBraceFilter(XPathParser.BraceFilterContext ctx) {
        ArrayList<Boolean> res = this.visit(ctx.f());
        return res;
    }

    @Override
    public ArrayList visitOrFilter(XPathParser.OrFilterContext ctx) {
        ArrayList<Node> prev = new ArrayList<Node>(this.curState);
        ArrayList<Boolean> fil1List = this.visit(ctx.f(0));
        this.curState = prev;
        ArrayList<Boolean> fil2List = this.visit(ctx.f(1));
        Iterator<Boolean> fil1;
        Iterator<Boolean> fil2;
        ArrayList<Boolean> join = new ArrayList<>();
        for(fil1 = fil1List.iterator(), fil2 = fil2List.iterator() ;
            fil1.hasNext();){
            join.add(fil1.next() | fil2.next());
        }
        return join;
    }

    @Override
    public ArrayList visitTagName(XPathParser.TagNameContext ctx) {
        String tag = ctx.ID().getText();
        ArrayList<Node> children = new ArrayList<>();
        for(Node node : this.curState){
            for(Node child : node.selectNodes("*")){
                if(tag.equals(child.getName()))
                    children.add(child);
            }
        }
        this.curState = children;
        return this.curState;
    }

    @Override
    public ArrayList visitAttName(XPathParser.AttNameContext ctx) {
        String att = ctx.ID().getText();
        ArrayList<Node> children = new ArrayList<>();
        for(Node node : this.curState){
            Element element = (Element) node;
            for(Attribute child : element.attributes()){
                if(att.equals(child.getName()))
                    children.add(child);
            }
        }
        this.curState = children;
        return this.curState;
    }

    @Override
    public ArrayList visitFilename(XPathParser.FilenameContext ctx) {
        ArrayList<Node> eleList = new ArrayList<>();
        try{
            Dom4j dom = new Dom4j();
            Document doc = dom.parse(ctx.FILENAME().getText());
            Element root = doc.getRootElement();
            Element entry = DocumentHelper.createElement("document");
            entry.add(root);
            eleList = new ArrayList(Arrays.asList(entry));
        }catch (DocumentException e) {
            System.out.println(e);
        }
        return eleList;
    }
}