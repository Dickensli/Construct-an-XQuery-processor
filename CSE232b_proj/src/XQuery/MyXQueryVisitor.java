package XQuery;

import org.dom4j.*;
import org.dom4j.util.NodeComparator;

import java.util.*;

public class MyXQueryVisitor extends XQueryBaseVisitor<ArrayList> {
    public ArrayList<Node> curState= new ArrayList();
    private LinkedHashMap<String, ArrayList<Node>> curCtx = new LinkedHashMap<>();
    public Stack<LinkedHashMap<String, ArrayList<Node>>> stack = new Stack();

    @Override
    public ArrayList visitConstructXQ(XQueryParser.ConstructXQContext ctx) {
        String tagName = ctx.ID(0).getText();
        if(!tagName.equals(ctx.ID(1).getText())) return this.curState;
        Element entry = DocumentHelper.createElement(tagName);
        this.curState = this.visit(ctx.xq());
        for(Object obj : this.curState){
            if(obj instanceof Node){
                Node node = (Node)obj;
                entry.add((Node)node.clone());
            }
            if(obj instanceof String){
                String str = (String)obj;
                entry.addText(str);
            }
        }
        this.curState = new ArrayList<>(Arrays.asList(entry));
        return this.curState;
    }

    public void getHelper(XQueryParser.FlwrXQContext ctx,
                          ArrayList<String> vars,
                          ArrayList<Node> prev,
                          int i,
                          LinkedHashMap<String, ArrayList<Node>> map,
                          ArrayList<LinkedHashMap<String, ArrayList<Node>>> res){
        if(i == vars.size()) {
            res.add(new LinkedHashMap<String, ArrayList<Node>>(map));
            return;
        }
        ArrayList<Node> nodes = this.visit(ctx.forClause().xq(i));
        this.curState = new ArrayList<Node>(this.curState);
        for(Node node : nodes) {
            map.put(vars.get(i),
                    new ArrayList<Node>(Arrays.asList(node)));
            this.curCtx.put(vars.get(i),
                    new ArrayList<Node>(Arrays.asList(node)));
            getHelper(ctx, vars, prev, i+1, map, res);
            map.remove(vars.get(i));
            this.curCtx.remove(vars.get(i));
        }
    }

    @Override
    public ArrayList visitFlwrXQ(XQueryParser.FlwrXQContext ctx) {
        ArrayList<Node> prev = new ArrayList<Node>(this.curState);
        this.stack.push(this.curCtx);
        //forClause
        ArrayList<String> vars = new ArrayList<>();
        for(int i=0 ; i<ctx.forClause().var().size() ; i++){
            vars.add(ctx.forClause().var(i).ID().getText());
            this.curState = new ArrayList<Node>(prev);
        }

        ArrayList<LinkedHashMap<String, ArrayList<Node>>> res = new ArrayList<>();
        LinkedHashMap<String, ArrayList<Node>> map = new LinkedHashMap<>();
        getHelper(ctx, vars, prev, 0, map, res);

        LinkedHashMap<String, ArrayList<Node>> prevCtx = new LinkedHashMap<String, ArrayList<Node>>(this.curCtx);
        ArrayList<Node> returnList = new ArrayList<>();
        for(int k=0 ; k<res.size() ; k++) {
            this.curCtx = new LinkedHashMap<String, ArrayList<Node>>(prevCtx);
            this.curCtx.putAll(res.get(k));
            //letClause
            if (ctx.letClause() != null)
                this.visit(ctx.letClause());
            //whereClause
            if (ctx.whereClause() != null && !(Boolean)this.visit(ctx.whereClause().cond()).get(0))
                continue;
            //returnClause
            this.curState = new ArrayList<Node>(prev);
            returnList.addAll(this.visit(ctx.returnClause().xq()));
        }
        this.curCtx = this.stack.pop();
        this.curState = new ArrayList<Node>(prev);
        this.curState.addAll(returnList);
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
        String name = ctx.StringConstant().getText();
        return new ArrayList<String>(Arrays.asList(name.substring(1,name.length()-1)));
    }

    @Override
    public ArrayList visitSingleXQ(XQueryParser.SingleXQContext ctx) {
        this.curState = this.visit(ctx.xq());
        this.curState = this.visit(ctx.rp());
        LinkedHashSet<Node> tmp = new LinkedHashSet<Node>(this.curState);
        this.curState = new ArrayList<Node>(tmp);
        return this.curState;
    }

    @Override
    public ArrayList visitCommaXQ(XQueryParser.CommaXQContext ctx) {
//        this.curState = this.visit(ctx.xq(0));
//        if (this.curState.isEmpty()){
//            this.curState = this.visit(ctx.xq(1));
//        }
//        return this.curState;
        ArrayList<Node> prev = new ArrayList<Node>(this.curState);
        ArrayList<Node> first = this.visit(ctx.xq(0));
        this.curState = prev;
        ArrayList<Node> second = this.visit(ctx.xq(1));
        ArrayList<Node> total = new ArrayList<>();
        total.addAll(first);
        total.addAll(second);
        this.curState = total;
        return this.curState;
    }

    @Override
    public ArrayList visitVarXQ(XQueryParser.VarXQContext ctx) {
        String varName = ctx.var().ID().getText();
        this.curState = this.curCtx.get(varName);
        return this.curState;
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
        LinkedHashSet<Node> tmp = new LinkedHashSet<Node>(this.curState);
        this.curState = new ArrayList<Node>(tmp);
        return this.curState;
    }

    @Override
    public ArrayList visitVar(XQueryParser.VarContext ctx) {
        return this.curState;
    }

    @Override
    public ArrayList visitForClause(XQueryParser.ForClauseContext ctx) {
        return this.curState;
    }

    @Override
    public ArrayList visitLetClause(XQueryParser.LetClauseContext ctx) {
        ArrayList<Node> prev = new ArrayList<Node>(this.curState);
        ArrayList<String> vars = new ArrayList<>();
        for(int i=0 ; i<ctx.var().size() ; i++){
            vars.add(ctx.var(i).ID().getText());
            this.curState = new ArrayList<Node>(prev);
        }
        for(int i=0 ; i < ctx.xq().size() ; i++){
            this.curState = this.visit(ctx.xq(i));
            this.curCtx.put(vars.get(i), this.curState);
            this.curState = new ArrayList<Node>(prev);
        }
        return this.curState;
    }

    @Override
    public ArrayList visitWhereClause(XQueryParser.WhereClauseContext ctx) {
        return this.curState;
    }

    @Override
    public ArrayList visitReturnClause(XQueryParser.ReturnClauseContext ctx) {
        return this.curState;
    }

    @Override
    public ArrayList visitValueEQCond(XQueryParser.ValueEQCondContext ctx) {

        ArrayList<Boolean> res = new ArrayList<Boolean>();
        ArrayList<Node> prev = new ArrayList<Node>(this.curState);
        ArrayList<Object> first = this.visit(ctx.xq(0));
        this.curState = new ArrayList<Node>(prev);
        ArrayList<Object> second = this.visit(ctx.xq(1));

        NodeComparator v = new NodeComparator();

        for (Object obj1 : first){
            for (Object obj2: second){
                if (obj1 instanceof Node && obj2 instanceof Node){
                    Node node1 = (Node)obj1;
                    Node node2 = (Node)obj2;
                    if (v.compare(node1,node2)==0){
                        res.add(true);
                        return res;
                    }
                }
                if (obj1 instanceof String && obj2 instanceof String){
                    String str1 = (String)obj1;
                    String str2 = (String)obj2;
                    if (str1.equals(str2)){
                        res.add(true);
                        return res;
                    }
                }
            }
        }

        this.curState = new ArrayList<Node>(prev);
        res.add(false);
        return res;
    }

    @Override
    public ArrayList visitBraceCond(XQueryParser.BraceCondContext ctx) {
        ArrayList<Node> prev = new ArrayList<Node>(this.curState);
        ArrayList<Boolean> res = this.visit(ctx.cond());
        this.curState = prev;
        return res;
    }

    @Override
    public ArrayList visitOrCond(XQueryParser.OrCondContext ctx) {
        ArrayList<Node> prev = new ArrayList<Node>(this.curState);
        ArrayList<Boolean> fil1List = this.visit(ctx.cond(0));
        this.curState = new ArrayList<Node>(prev);
        ArrayList<Boolean> fil2List = this.visit(ctx.cond(1));

        assert fil1List.size() == 1;
        assert fil2List.size() == 1;

        this.curState = new ArrayList<Node>(prev);
        ArrayList<Boolean> res = new ArrayList<>();
        res.add(fil1List.get(0) | fil2List.get(0));
        return res;
    }

    @Override
    public ArrayList visitIdEQCond(XQueryParser.IdEQCondContext ctx) {
        ArrayList<Boolean> res = new ArrayList<Boolean>();
        ArrayList<Node> prev = new ArrayList<Node>(this.curState);
        ArrayList<Node> first = this.visit(ctx.xq(0));
        this.curState = new ArrayList<Node>(prev);
        ArrayList<Node> second = this.visit(ctx.xq(1));

        for (Node f : first){
            for (Node s: second){
                if (f.getUniquePath().equals(s.getUniquePath())){
                    res.add(true);
                    return res;
                }
            }
        }
        this.curState = new ArrayList<Node>(prev);
        res.add(false);
        return res;
    }

    @Override
    public ArrayList visitEmptyCond(XQueryParser.EmptyCondContext ctx) {
        ArrayList<Boolean> res = new ArrayList<>();
        ArrayList<Node> prev = new ArrayList<Node>(this.curState);
        ArrayList<Node> check = this.visit(ctx.xq());
        res.add(check.isEmpty());
        this.curState = prev;
        return res;
    }

    @Override
    public ArrayList visitAndCond(XQueryParser.AndCondContext ctx) {
        ArrayList<Node> prev = new ArrayList<Node>(this.curState);
        ArrayList<Boolean> fil1List = this.visit(ctx.cond(0));
        this.curState = new ArrayList<Node>(prev);
        ArrayList<Boolean> fil2List = this.visit(ctx.cond(1));

        assert fil1List.size() == 1;
        assert fil2List.size() == 1;

        this.curState = new ArrayList<Node>(prev);
        ArrayList<Boolean> res = new ArrayList<>();
        res.add(fil1List.get(0) & fil2List.get(0));
        return res;
    }

    @Override
    public ArrayList visitSomeCond(XQueryParser.SomeCondContext ctx) {
        ArrayList<Node> prev = new ArrayList<Node>(this.curState);
        LinkedHashMap<String, ArrayList<Node>> prevCtx = new LinkedHashMap<String, ArrayList<Node>>(this.curCtx);
        ArrayList<Boolean> ret = new ArrayList<>();

        ArrayList<String> vars = new ArrayList<>();
        for (int i=0; i<ctx.var().size(); i++){
            vars.add(ctx.var(i).ID().getText());
        }

        ArrayList<LinkedHashMap<String, ArrayList<Node>>> res = new ArrayList<>();
        LinkedHashMap<String, ArrayList<Node>> map = new LinkedHashMap<>();
        someHelper(ctx, vars, prev, 0, map, res);

        for (LinkedHashMap<String, ArrayList<Node>> m: res){
            LinkedHashMap<String, ArrayList<Node>> newCtx = new LinkedHashMap<>();
            newCtx.putAll(prevCtx);
            newCtx.putAll(m);
            this.curCtx = newCtx;
            this.curState = new ArrayList<Node>(prev);

            ArrayList<Boolean> ans = this.visit(ctx.cond());
            assert ans.size() == 1;

            if (ans.get(0)){
                ret.add(true);
                return ret;
            }
        }
        this.curState = new ArrayList<Node>(prev);
        this.curCtx = new LinkedHashMap<String, ArrayList<Node>>(prevCtx);

        ret.add(false);
        return ret;
    }

    public void someHelper(XQueryParser.SomeCondContext ctx,
                           ArrayList<String> vars,
                           ArrayList<Node> prev,
                           int i,
                           LinkedHashMap<String, ArrayList<Node>> map,
                           ArrayList<LinkedHashMap<String, ArrayList<Node>>> res){
        if(i == vars.size()) {
            res.add(new LinkedHashMap<String, ArrayList<Node>>(map));
            return;
        }
        this.curState = new ArrayList<Node>(prev);
        ArrayList<Node> nodes = this.visit(ctx.xq(i));
        for(Node node : nodes) {
            map.put(vars.get(i),
                    new ArrayList<Node>(Arrays.asList(node)));
            this.curCtx.put(vars.get(i),
                    new ArrayList<Node>(Arrays.asList(node)));
            someHelper(ctx, vars, prev, i+1, map, res);
            map.remove(vars.get(i));
            this.curCtx.remove(vars.get(i));
        }
    }

    @Override
    public ArrayList visitNotCond(XQueryParser.NotCondContext ctx) {
        ArrayList<Node> prev = new ArrayList<Node>(this.curState);
        ArrayList<Boolean> res = this.visit(ctx.cond());

        this.curState = prev;
        assert res.size() == 1;
        res.set(0, !res.get(0));
        return res;
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
        ArrayList<Node> prev = new ArrayList<Node>(this.curState);
        ArrayList<Node> first = this.visit(ctx.rp(0));
        this.curState = prev;
        ArrayList<Node> second = this.visit(ctx.rp(1));
        ArrayList<Node> total = new ArrayList<>();
        total.addAll(first);
        total.addAll(second);
        this.curState = total;
//        this.curState = this.visit(ctx.rp(0));
//        if (this.curState.isEmpty()){
//            this.curState = this.visit(ctx.rp(1));
//        }
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
        LinkedHashSet<Node> tmp = new LinkedHashSet<Node>(this.curState);
        this.curState = new ArrayList<Node>(tmp);
        return this.curState;
    }

    @Override
    public ArrayList visitSingleRP(XQueryParser.SingleRPContext ctx) {
        this.curState = this.visit(ctx.rp(0));
        this.curState = this.visit(ctx.rp(1));
        LinkedHashSet<Node> tmp = new LinkedHashSet<Node>(this.curState);
        this.curState = new ArrayList<Node>(tmp);
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
    public ArrayList visitRpFilter(XQueryParser.RpFilterContext ctx) {
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
    public ArrayList visitValueEQFilter(XQueryParser.ValueEQFilterContext ctx) {
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
    public ArrayList visitIdEQFilter(XQueryParser.IdEQFilterContext ctx) {
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
    public ArrayList visitBraceFilter(XQueryParser.BraceFilterContext ctx) {
        ArrayList<Boolean> res = this.visit(ctx.f());
        return res;
    }

    @Override
    public ArrayList visitOrFilter(XQueryParser.OrFilterContext ctx) {
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
