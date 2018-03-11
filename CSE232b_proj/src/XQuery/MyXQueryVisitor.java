package XQuery;

import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.dom4j.util.NodeComparator;

import java.lang.reflect.Array;
import java.util.*;

public class MyXQueryVisitor extends XQueryBaseVisitor<ArrayList> {
    public ArrayList<Node> curState= new ArrayList();
    private LinkedHashMap<String, ArrayList<Node>> curCtx = new LinkedHashMap<>();
    public Stack<LinkedHashMap<String, ArrayList<Node>>> stack = new Stack();
    Boolean needRewrite = true;

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
        if(this.needRewrite) {
            String rewrited = this.rewrite(ctx);
            if (rewrited.equals(""))
                return this.FlwrHelper(ctx);
            else {
                return Main.evalRewrited(rewrited);
            }
        }
        else{
            return this.FlwrHelper(ctx);
        }
    }

    public ArrayList FlwrHelper(XQueryParser.FlwrXQContext ctx){
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
//            System.out.println("this is testing var");
//            for(String str: this.curCtx.keySet()){
//                System.out.println(str);
//                for(Node n:this.curCtx.get(str)){
//                    try{
//                        OutputFormat format = OutputFormat.createPrettyPrint();
//                        XMLWriter writer = new XMLWriter(System.out, format);
//                        writer.write(n);
//                    } catch (Exception e){
//                        System.out.println("lalalalaa");
//                    }
//                }
//            }
//            System.out.println("this is testing var");

            ArrayList<Node> nnn = new ArrayList<>(this.visit(ctx.returnClause().xq()));
//            System.out.println("Flwr output start");
//            for(Node n: nnn){
//                try{
//                    OutputFormat format = OutputFormat.createPrettyPrint();
//                    XMLWriter writer = new XMLWriter(System.out, format);
//                    writer.write(n);
//                } catch (Exception e){
//                    System.out.println("lalalalaa");
//                }
//            }
//            System.out.println("Flwr output end");
            returnList.addAll(nnn);
        }
        this.curCtx = this.stack.pop();
        this.curState = new ArrayList<Node>(prev);
        this.curState.addAll(returnList);
        return this.curState;
    }

    @Override
    public ArrayList visitApXQ(XQueryParser.ApXQContext ctx) {
        ArrayList<Node> prev = new ArrayList<>(this.curState);
        ArrayList<Node> res = this.visit(ctx.ap());
        this.curState = new ArrayList<>(prev);
        return res;
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
        ArrayList<Node> prev = new ArrayList<>(this.curState);

        System.out.println("this is singleXQ start start");
        System.out.println(this.curState.size());
        System.out.println("this is singleXQ start end");
        System.out.println();

        ArrayList vv = this.visit(ctx.xq());
        this.curState = new ArrayList<>(vv);

        System.out.println("this is after $x/$y start");
        System.out.println(((Node)vv.get(0)).getUniquePath());
        System.out.println("this is after $x/$y end");
        System.out.println();

        this.curState = this.visit(ctx.rp());

        System.out.println("this is after rp start");
        System.out.println(this.curState.get(0).getUniquePath());
        System.out.println("this is after rp end");
        System.out.println();

        System.out.println();
        LinkedHashSet<Node> tmp = new LinkedHashSet<Node>(this.curState);
        this.curState = new ArrayList<>(prev);
//        System.out.println("SingleXQ output start");
//        for(Node n:tmp) {
//            try {
//                OutputFormat format = OutputFormat.createPrettyPrint();
//                XMLWriter writer = new XMLWriter(System.out, format);
//                writer.write(n);
//            } catch (Exception e) {
//                System.out.println("lalalalaa");
//            }
//        }
//        System.out.println("SingleXQ output end");
        return new ArrayList<Node>(tmp);
    }

    @Override
    public ArrayList visitCommaXQ(XQueryParser.CommaXQContext ctx) {
//        this.curState = this.visit(ctx.xq(0));
//        if (this.curState.isEmpty()){
//            this.curState = this.visit(ctx.xq(1));
//        }
//        return this.curState;
        ArrayList<Node> prev = new ArrayList<Node>(this.curState);

        System.out.println("In comma curState begin");
        System.out.println(this.curState.size());
        System.out.println("In comma curState begin");
        System.out.println();

        ArrayList<Node> first = this.visit(ctx.xq(0));
        this.curState = new ArrayList<Node>(prev);

        System.out.println("this is after comma");
        System.out.println(this.curState.size());
        System.out.println("this is after comma");
        System.out.println();

        ArrayList<Node> second = this.visit(ctx.xq(1));
        ArrayList<Node> total = new ArrayList<>();
        total.addAll(first);
        total.addAll(second);
        this.curState = new ArrayList<Node>(prev);
//        System.out.println("Comma output start");
//        for(Node n:total) {
//            try {
//                OutputFormat format = OutputFormat.createPrettyPrint();
//                XMLWriter writer = new XMLWriter(System.out, format);
//                writer.write(n);
//            } catch (Exception e) {
//                System.out.println("lalalalaa");
//            }
//        }
//        System.out.println("Comma output end");
        return total;
    }

    @Override
    public ArrayList visitVarXQ(XQueryParser.VarXQContext ctx) {
        String varName = ctx.var().ID().getText();
        return new ArrayList<Node>(this.curCtx.get(varName));
    }

    @Override
    public ArrayList visitBraceXQ(XQueryParser.BraceXQContext ctx) {
        ArrayList<Node> res = this.visit(ctx.xq());
        System.out.println("In brace!! start");
        for(Node n : res){
            try {
                OutputFormat format = OutputFormat.createPrettyPrint();
                XMLWriter writer = new XMLWriter(System.out, format);
                writer.write(n);
            } catch (Exception e) {
                System.out.println("lalalalaa");
            }
        }
        System.out.println("In brace!! end");
        return res;
    }

    @Override
    public ArrayList visitJoinXQ(XQueryParser.JoinXQContext ctx) {
        LinkedHashMap<ListKeys, ArrayList<Node>> joinHash = new LinkedHashMap<>();
        ArrayList<Node> res = new ArrayList<>();
        ArrayList<Node> prevState = new ArrayList(this.curState);
        ArrayList<Node> left = new ArrayList<Node>(this.visit(ctx.xq(0)));
        this.curState = new ArrayList<>(prevState);
        ArrayList<Node> right = new ArrayList<Node>(this.visit(ctx.xq(1)));
        ArrayList<String> leftatt = new ArrayList<String>(this.visit(ctx.attrs(0)));
        ArrayList<String> rightatt = new ArrayList<String>(this.visit(ctx.attrs(1)));
        ArrayList<Node> small = left.size() <= right.size() ? left : right,
                large = left.size() > right.size() ? left : right;
        ArrayList<String> smatt = left.size() <= right.size() ? leftatt : rightatt,
                latt = left.size() > right.size() ? leftatt : rightatt;
        ArrayList<Node> prev = new ArrayList<Node>(this.curState);

        // Add small to hash map
        for(Node s : small) {
            ArrayList<Node> leftNodes = new ArrayList<>();
            for (String att : smatt) {
                //todo: what if two atts have the same name
                leftNodes.add(s.selectNodes(att.concat("/*")).get(0));
            }
            ListKeys leftKeys = new ListKeys(leftNodes);
            if (joinHash.get(leftKeys) == null)
                joinHash.put(leftKeys, new ArrayList<Node>());
            joinHash.get(leftKeys).add(s);
        }
        // find key in hashmap and pair up to form a new tuple
        for(Node s : large){
            ArrayList<Node> rightNodes = new ArrayList<>();
            for (String att : latt) {
                //todo: what if two atts have the same name
                rightNodes.add(s.selectNodes(att.concat("/*")).get(0));
            }
            ListKeys rightKeys = new ListKeys(rightNodes);
            if(!joinHash.containsKey(rightKeys))
                continue;
            for(Node node : joinHash.get(rightKeys)){
                res.add(pairUp(node, s));
            }
        }
        return res;
    }

    private Node pairUp(Node n1, Node n2){
        Element res = (Element)n1.clone();
        for(Node child_ : n2.selectNodes("*")){
            Element child = (Element)child_.clone();
            res.add(child);
        }
        return res;
    }

    @Override
    public ArrayList visitDoubleXQ(XQueryParser.DoubleXQContext ctx) {
        ArrayList<Node> prev = new ArrayList<>(this.curState);
        this.curState = this.visit(ctx.xq());
        ArrayList<Node> res = new ArrayList<>();
        for (Node node : this.curState)
            doubleHelper(res, node);
        this.curState = res;
        this.curState = this.visit(ctx.rp());
        LinkedHashSet<Node> tmp = new LinkedHashSet<Node>(this.curState);
        this.curState = new ArrayList<>(prev);
        return new ArrayList<Node>(tmp);
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
                else if (obj1 instanceof Node && obj2 instanceof String){
                    Node node1 = (Node)obj1;
                    String str2 = (String)obj2;
                    if (node1.getText().equals(str2)){
                        res.add(true);
                        return res;
                    }
                }
                else if (obj2 instanceof Node && obj1 instanceof String){
                    Node node2 = (Node)obj2;
                    String str1 = (String)obj1;
                    if (node2.getText().equals(str1)){
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
    public ArrayList visitAttrs(XQueryParser.AttrsContext ctx) {
        ArrayList<String> res = new ArrayList<>();
        for(int i=0 ; i<ctx.ID().size(); i++){
            res.add(ctx.ID(i).getText());
        }
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
        ArrayList<Node> res = new ArrayList<>();
        for(Node node : this.curState){
            res.add(node.selectNodes("text()").get(0));
        }
        this.curState = new ArrayList(res);
        return this.curState;
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
        this.curState = new ArrayList<>(prev);
        ArrayList<Node> second = this.visit(ctx.rp(1));
        ArrayList<Node> total = new ArrayList<>();
        total.addAll(first);
        total.addAll(second);
        this.curState = new ArrayList<>(prev);
        return total;
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
            if(first_list.size() > 0 && second_list.size() > 0)
                res.add(this.IdCompare(first_list, second_list));
            else
                res.add(false);
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


    public String rewrite(XQueryParser.FlwrXQContext ctx) {
        int numVars = ctx.forClause().var().size();
        LinkedHashMap<String, LinkedHashSet<String>> blocks = new LinkedHashMap<>();
        LinkedHashMap<String, String> varPaths = new LinkedHashMap<>();
        // blocks: root -> [vars including root]

        for (int i = 0; i < numVars; i++) {
            String var = ctx.forClause().var(i).getText();
            String root = ctx.forClause().xq(i).getText().split("/")[0];
            String path = ctx.forClause().xq(i).getText();
            varPaths.put(var, path);
            boolean existed = false;

            Set<String> keys = blocks.keySet();
            for (String key: keys) {
                LinkedHashSet<String> curBlock = blocks.get(key);
                if (curBlock.contains(root)) {
                    existed = true;
                    curBlock.add(var);
                    break;
                }
            }

            if (!existed) {
                LinkedHashSet<String> newBlock = new LinkedHashSet<>();
                newBlock.add(var);
                blocks.put(var, newBlock);
            }
        }

        if (blocks.size() == 1){
            return "";
        }

//        whereClause
        String[] cond = ctx.whereClause().cond().getText().split("and");
        String[][] whereList = new String[cond.length][2];
        for (int i = 0; i < cond.length; i++) {
            whereList[i][0] = cond[i].split("eq|=")[0];
            whereList[i][1] = cond[i].split("eq|=")[1];
        }

        LinkedHashMap<ArrayList<String>,ArrayList<ArrayList<String>>> mapping = new LinkedHashMap<>();
        LinkedHashMap<String, String> stringMapping = new LinkedHashMap<>();

        for (int i = 0; i < whereList.length; i++) {
            String var1 = whereList[i][0];
            String var2 = whereList[i][1];
            // check if pattern is $a = "John"
            if (!var1.startsWith("$") || !var2.startsWith("$")) {
                stringMapping.put(var1, var2);
                continue;
            }

            String root1 = "";
            String root2 = "";

            // create mapping [b1, b2] -> [b11, b12], [b21, b22]
            Set<String> keys = blocks.keySet();
            for (String key: keys) {
                if (blocks.get(key).contains(var1)) {
                    root1 = key;
                }
                if (blocks.get(key).contains(var2)) {
                    root2 = key;
                }
            }


            ArrayList<String> rootKeys = new ArrayList<>(Arrays.asList(root1, root2));
            Collections.sort(rootKeys);
            ArrayList<ArrayList<String>> varValues = mapping.get(rootKeys);
//            ArrayList<ArrayList<String>> varValues = new ArrayList<ArrayList<String>>(mapping.get(rootKeys));

            if (varValues != null) {
                if (rootKeys.get(0).equals(root1)){
                    varValues.get(0).add(var1.substring(1));
                    varValues.get(1).add(var2.substring(1));
                } else {
                    varValues.get(0).add(var2.substring(1));
                    varValues.get(1).add(var1.substring(1));
                }

            } else {
                varValues = new ArrayList<ArrayList<String>>();
//                ArrayList<String> var1List = new ArrayList<>(Arrays.asList(var1));
                ArrayList<String> var1List = new ArrayList<>();
                var1List.add(var1.substring(1));
//                ArrayList<String> var2List = new ArrayList<>(Arrays.asList(var2));
                ArrayList<String> var2List = new ArrayList<>();
                var2List.add(var2.substring(1));


                if (rootKeys.get(0).equals(root1)){
                    varValues.add(var1List);
                    varValues.add(var2List);
                } else {
                    varValues.add(var2List);
                    varValues.add(var1List);
                }
                mapping.put(rootKeys, varValues);
            }
        }

        // create output

        String output = "for $tuple in";

        for (int i = 0; i < mapping.size(); i++) {
            output += " join (";
        }


        HashSet<String> visited = new HashSet<>();
        Set<ArrayList<String>> rootPairSet = mapping.keySet();

        int index = 0;
        int size = mapping.size();
        for (ArrayList<String> rootPair: mapping.keySet()) {
            String root1 = rootPair.get(0);
            String root2 = rootPair.get(1);
            ArrayList<ArrayList<String>> varValues = mapping.get(rootPair);

            if (index == 0) {
                output += printBlock(blocks.get(root1), varPaths, stringMapping) + ",";
                output += printBlock(blocks.get(root2), varPaths, stringMapping) + ",";

                output += "[" + String.join(",", varValues.get(0)) + "], ";
                output += "[" + String.join(",", varValues.get(1)) + "])";
            }
            else{
                if (visited.contains(root1)) {
                    output += printBlock(blocks.get(root2), varPaths, stringMapping) + ",";
                    output += "[" + String.join(",", varValues.get(0)) + "], ";
                    output += "[" + String.join(",", varValues.get(1)) + "])";
                } else if (visited.contains(root2)){
                    output += printBlock(blocks.get(root1), varPaths, stringMapping) + ",";
                    output += "[" + String.join(",", varValues.get(1)) + "], ";
                    output += "[" + String.join(",", varValues.get(0)) + "])";
                } else {
                    System.out.println("Not joining to the others!");
                }
            }
            if (index < size-1) {
                output += ",";
            }

            visited.add(root1);
            visited.add(root2);
            index++;

        }

        // returnClause
        String retString = ctx.returnClause().xq().getText();
        String retResult = " return ";
        int i = 0;
        while (i < retString.length()) {
            int dollar = retString.indexOf("$", i);

            if (dollar != -1) {
                retResult = retResult + retString.substring(i, dollar);

                int comma = retString.indexOf(",", dollar);
                if (comma == -1) comma = retString.length();
                int slash = retString.indexOf("/", dollar);
                if (slash == -1) slash = retString.length();
                int bracket = retString.indexOf("}",dollar);
                if (bracket == -1) bracket = retString.length();
                int j = Math.min(Math.min(comma, slash), bracket);

                String variable = retString.substring(dollar, j);

                String replacedVar = "$tuple/" + variable.substring(1) + "/*";
                retResult = retResult + replacedVar;
                i = j;
            } else {
                retResult = retResult + retString.substring(i);
                i = retString.length();
            }
        }
        output += retResult;

        System.out.println(output);

        return output;
    }



    public String printBlock(LinkedHashSet<String> vars1,
                             LinkedHashMap<String, String> varPaths,
                             LinkedHashMap<String, String> stringMapping) {
        // for
        String res = "for ";
        int index = 0;
        int size1 = vars1.size();
        for (String var1: vars1) {
            if (index == size1 - 1) {
                res += var1 + " in " + varPaths.get(var1) + " ";
            }
            else {
                res += var1 + " in " + varPaths.get(var1) + ", ";
            }
            index++;
        }

        // where
        boolean contains = false;

        if (!stringMapping.isEmpty()){
            for (String var1: vars1) {
                if (stringMapping.keySet().contains(var1)){
                    if (!contains){
                        contains = true;
                        res += " where " + var1 + "=" + stringMapping.get(var1);
                    }
                    else {
                        res += " and " + var1 + "=" + stringMapping.get(var1);
                    }
                }
            }
        }

        // return
        res += " return <tuple> {";
        index = 0;
        size1 = vars1.size();
        for (String var: vars1) {
            if (index == size1 - 1) {
                res += "<" + var.substring(1) + "> {" + var + "} </" + var.substring(1) + ">";
            }
            else {
                res += "<" + var.substring(1) + "> {" + var + "} </" + var.substring(1) + ">, ";
            }
            index++;
        }
        res += "} </tuple>";

        return res;

    }
}

final class ListKeys{
    private final ArrayList<Node> listNodes;

    public ListKeys(ArrayList<Node> listNodes){
        this.listNodes = new ArrayList<>(listNodes);
    }

    public ArrayList<Node> getNodeList(){
        return new ArrayList<Node>(this.listNodes);
    }

    @Override
    public int hashCode(){
        int hash = 1;
        for(Node node : this.listNodes){
            hash ^= node.asXML().hashCode();
        }
        return hash;
    }

    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof ListKeys) || ((ListKeys)obj).listNodes.size() != this.listNodes.size())
            return false;
        Iterator<Node> selfKey;
        Iterator<Node> objKey;
        NodeComparator v = new NodeComparator();
        for(selfKey = this.listNodes.iterator(), objKey = ((ListKeys)obj).listNodes.iterator();
                selfKey.hasNext();){
            if(v.compare(selfKey.next(),objKey.next()) != 0)
                return false;
        }
        return true;
    }
}
