package XQuery;

import org.dom4j.*;
import org.dom4j.util.NodeComparator;

import java.util.*;

public class MyXQueryVisitor extends XQueryBaseVisitor<ArrayList> {
    public ArrayList<Node> curState= new ArrayList();
    private ArrayList<LinkedHashMap<String, ArrayList<Node>>> curCtx = new ArrayList<>();
    public Stack<ArrayList<LinkedHashMap<String, ArrayList<Node>>>> stack = new Stack();

    private void initCtx(){
        this.curCtx.add(new LinkedHashMap<String, ArrayList<Node>>());
        this.curCtx.add(new LinkedHashMap<String, ArrayList<Node>>());
    }

    private void initStack(){
        initCtx();
        this.stack.push(this.curCtx);
    }

    @Override
    public ArrayList visitConstructXQ(XQueryParser.ConstructXQContext ctx) {
        String tagName = ctx.ID(0).getText();
        if(!tagName.equals(ctx.ID(1).getText())) return this.curState;
        Element entry = DocumentHelper.createElement(tagName);
        this.curState = this.visit(ctx.xq());
        for(Node node : this.curState){
            entry.add(node);
        }
        this.curState = new ArrayList<>(Arrays.asList(entry));
        return this.curState;
    }

    @Override
    public ArrayList visitFlwrXQ(XQueryParser.FlwrXQContext ctx) {
        this.curState = this.visit(ctx.forClause());
        //Todo: if letClause doesn't exist
        this.curState = this.visit(ctx.letClause());
        this.curState = this.visit(ctx.whereClause());
        this.curState = this.visit(ctx.returnClause());
        return this.curState;
    }

    @Override
    public ArrayList visitApXQ(XQueryParser.ApXQContext ctx) {
        this.curState = this.visit(ctx.ap());
        return this.curState;
    }

    @Override
    public ArrayList visitLetXQ(XQueryParser.LetXQContext ctx) {
        this.curState = this.visit(ctx.letClause());
        this.curState = this.visit(ctx.xq());
        return this.curState;
    }

    @Override
    public ArrayList visitStringXQ(XQueryParser.StringXQContext ctx) {
        return super.visitStringXQ(ctx);
    }

    @Override
    public ArrayList visitSingleXQ(XQueryParser.SingleXQContext ctx) {
        this.curState = this.visit(ctx.xq());
        this.curState = this.visit(ctx.rp());
        return this.curState;
    }

    @Override
    public ArrayList visitCommaXQ(XQueryParser.CommaXQContext ctx) {
        this.curState = this.visit(ctx.xq(0));
        if (this.curState.isEmpty()){
            this.curState = this.visit(ctx.xq(1));
        }
        return this.curState;
    }

    @Override
    public ArrayList visitVarXQ(XQueryParser.VarXQContext ctx) {
        ArrayList<Node>
        return super.visitVarXQ(ctx);
    }

    @Override
    public ArrayList visitBraceXQ(XQueryParser.BraceXQContext ctx) {
        ArrayList<Boolean> res = this.visit(ctx.xq());
        return res;
    }

    @Override
    public ArrayList visitDoubleXQ(XQueryParser.DoubleXQContext ctx) {
        this.curState = this.visit(ctx.xq());
        ArrayList<Node> res = new ArrayList<>();
        for (Node node : this.curState)
            doubleHelper(res, node);
        this.curState = res;
        this.curState = this.visit(ctx.rp());
        return this.curState;
    }

    @Override
    public ArrayList visitVar(XQueryParser.VarContext ctx) {
        String tagName = ctx.ID().getText();
        ArrayList<String> res = new ArrayList<>();
        res.add(tagName);
        return res;
    }

    @Override
    public ArrayList visitForClause(XQueryParser.ForClauseContext ctx) {
        ArrayList<String> vars = new ArrayList<>();
        ArrayList<Node> prev = this.curState;
        for(int i=0 ; i<ctx.var().size() ; i++){
            //Todo: not use visitVar
            vars.add(ctx.var(i).ID().getText());
            this.curState = prev;
        }

        if(this.stack.isEmpty()) this.initStack();
        for(int i=0 ; i < vars.size() ; i++){
            this.curState = this.visit(ctx.xq(i));
            this.curCtx.get(0).put(vars.get(i), this.curState);
            this.curState = prev;
        }
        this.stack.push(this.curCtx);
        return this.curState;
    }

    @Override
    public ArrayList visitLetClause(XQueryParser.LetClauseContext ctx) {

        return super.visitLetClause(ctx);
    }

    @Override
    public ArrayList visitWhereClause(XQueryParser.WhereClauseContext ctx) {
        return super.visitWhereClause(ctx);
    }

    @Override
    public ArrayList visitReturnClause(XQueryParser.ReturnClauseContext ctx) {
        ArrayList<Node> prev = this.curState;

        return super.visitReturnClause(ctx);
    }

    @Override
    public ArrayList visitValueEQCond(XQueryParser.ValueEQCondContext ctx) {
        return super.visitValueEQCond(ctx);
    }

    @Override
    public ArrayList visitBraceCond(XQueryParser.BraceCondContext ctx) {
        return super.visitBraceCond(ctx);
    }

    @Override
    public ArrayList visitOrCond(XQueryParser.OrCondContext ctx) {
        return super.visitOrCond(ctx);
    }

    @Override
    public ArrayList visitIdEQCond(XQueryParser.IdEQCondContext ctx) {
        return super.visitIdEQCond(ctx);
    }

    @Override
    public ArrayList visitEmptyCond(XQueryParser.EmptyCondContext ctx) {
        return super.visitEmptyCond(ctx);
    }

    @Override
    public ArrayList visitAndCond(XQueryParser.AndCondContext ctx) {
        return super.visitAndCond(ctx);
    }

    @Override
    public ArrayList visitSomeCond(XQueryParser.SomeCondContext ctx) {
        return super.visitSomeCond(ctx);
    }

    @Override
    public ArrayList visitNotCond(XQueryParser.NotCondContext ctx) {
        return super.visitNotCond(ctx);
    }

    @Override
    public ArrayList visitDoc(XQueryParser.DocContext ctx) {
        this.curState = this.visit(ctx.filename());
        return this.curState;
    }

    @Override
    public ArrayList visitSingleAP(XQueryParser.SingleAPContext ctx) {
        this.curState = this.visit(ctx.doc());
        this.curState = this.visit(ctx.rp());
        return this.curState;
    }

    @Override
    public ArrayList visitDoubleAP(XQueryParser.DoubleAPContext ctx) {
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
    public ArrayList visitBraceRP(XQueryParser.BraceRPContext ctx) {
        this.curState = this.visit(ctx.rp());
        return this.curState;
    }

    @Override
    public ArrayList visitTextRP(XQueryParser.TextRPContext ctx) {
        ArrayList<String> textList = new ArrayList<>();
        for(Node node : this.curState){
            textList.add(node.getText());
        }
        return textList;
    }

    @Override
    public ArrayList visitAttRP(XQueryParser.AttRPContext ctx) {
        this.curState = this.visit(ctx.attName());
        return this.curState;
    }

    @Override
    public ArrayList visitParentRP(XQueryParser.ParentRPContext ctx) {
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
    public ArrayList visitSelfRP(XQueryParser.SelfRPContext ctx) {
        return this.curState;
    }

    @Override
    public ArrayList visitFilterRP(XQueryParser.FilterRPContext ctx) {
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
    public ArrayList visitCommaRP(XQueryParser.CommaRPContext ctx) {
        this.curState = this.visit(ctx.rp(0));
        if (this.curState.isEmpty()){
            this.curState = this.visit(ctx.rp(1));
        }
        return this.curState;
    }

    @Override
    public ArrayList visitChildrenRP(XQueryParser.ChildrenRPContext ctx) {
        ArrayList<Node> children_next = new ArrayList<>();
        for (Node n : this.curState){
            children_next.addAll(n.selectNodes("*"));
        }
        this.curState = children_next;
        return this.curState;
    }

    @Override
    public ArrayList visitTagRP(XQueryParser.TagRPContext ctx) {
        this.curState = this.visit(ctx.tagName());
        return this.curState;
    }

    @Override
    public ArrayList visitDoubleRP(XQueryParser.DoubleRPContext ctx) {
        this.curState = this.visit(ctx.rp(0));
        ArrayList<Node> res = new ArrayList<>();
        for (Node node : this.curState)
            doubleHelper(res, node);
        this.curState = res;
        this.curState = this.visit(ctx.rp(1));
        return this.curState;
    }

    @Override
    public ArrayList visitSingleRP(XQueryParser.SingleRPContext ctx) {
        this.curState = this.visit(ctx.rp(0));
        this.curState = this.visit(ctx.rp(1));
        return this.curState;
    }

    @Override
    public ArrayList visitNotFilter(XQueryParser.NotFilterContext ctx) {
        ArrayList<Boolean> res = this.visit(ctx.f());
        for(int i=0 ; i < res.size() ; i++) {
            res.set(i, !res.get(i));
        }
        System.out.println(res);
        return res;
    }

    @Override
    public ArrayList visitAndFilter(XQueryParser.AndFilterContext ctx) {
        ArrayList<Node> prev = this.curState;
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
    public ArrayList visitRpFilter(XQueryParser.RpFilterContext ctx) {
        ArrayList<Boolean> res = new ArrayList<>();
        ArrayList<Node> prevState = this.curState;
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
    public ArrayList visitValueEQFilter(XQueryParser.ValueEQFilterContext ctx) {
        ArrayList<Boolean> res = new ArrayList<Boolean>();
        ArrayList<Node> prev = this.curState;
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
    public ArrayList visitIdEQFilter(XQueryParser.IdEQFilterContext ctx) {
        ArrayList<Boolean> res = new ArrayList<Boolean>();
        ArrayList<Node> prev = this.curState;
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
    public ArrayList visitBraceFilter(XQueryParser.BraceFilterContext ctx) {
        ArrayList<Boolean> res = this.visit(ctx.f());
        return res;
    }

    @Override
    public ArrayList visitOrFilter(XQueryParser.OrFilterContext ctx) {
        ArrayList<Node> prev = this.curState;
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
    public ArrayList visitTagName(XQueryParser.TagNameContext ctx) {
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
    public ArrayList visitAttName(XQueryParser.AttNameContext ctx) {
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
    public ArrayList visitFilename(XQueryParser.FilenameContext ctx) {
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
