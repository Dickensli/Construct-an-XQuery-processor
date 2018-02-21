// Generated from C:/Users/Dickensli/Desktop/cse232/CSE232b_proj/src/proj\XQuery.g4 by ANTLR 4.7
package XQuery;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link XQueryParser}.
 */
public interface XQueryListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by the {@code constructXQ}
	 * labeled alternative in {@link XQueryParser#xq}.
	 * @param ctx the parse tree
	 */
	void enterConstructXQ(XQueryParser.ConstructXQContext ctx);
	/**
	 * Exit a parse tree produced by the {@code constructXQ}
	 * labeled alternative in {@link XQueryParser#xq}.
	 * @param ctx the parse tree
	 */
	void exitConstructXQ(XQueryParser.ConstructXQContext ctx);
	/**
	 * Enter a parse tree produced by the {@code flwrXQ}
	 * labeled alternative in {@link XQueryParser#xq}.
	 * @param ctx the parse tree
	 */
	void enterFlwrXQ(XQueryParser.FlwrXQContext ctx);
	/**
	 * Exit a parse tree produced by the {@code flwrXQ}
	 * labeled alternative in {@link XQueryParser#xq}.
	 * @param ctx the parse tree
	 */
	void exitFlwrXQ(XQueryParser.FlwrXQContext ctx);
	/**
	 * Enter a parse tree produced by the {@code apXQ}
	 * labeled alternative in {@link XQueryParser#xq}.
	 * @param ctx the parse tree
	 */
	void enterApXQ(XQueryParser.ApXQContext ctx);
	/**
	 * Exit a parse tree produced by the {@code apXQ}
	 * labeled alternative in {@link XQueryParser#xq}.
	 * @param ctx the parse tree
	 */
	void exitApXQ(XQueryParser.ApXQContext ctx);
	/**
	 * Enter a parse tree produced by the {@code letXQ}
	 * labeled alternative in {@link XQueryParser#xq}.
	 * @param ctx the parse tree
	 */
	void enterLetXQ(XQueryParser.LetXQContext ctx);
	/**
	 * Exit a parse tree produced by the {@code letXQ}
	 * labeled alternative in {@link XQueryParser#xq}.
	 * @param ctx the parse tree
	 */
	void exitLetXQ(XQueryParser.LetXQContext ctx);
	/**
	 * Enter a parse tree produced by the {@code stringXQ}
	 * labeled alternative in {@link XQueryParser#xq}.
	 * @param ctx the parse tree
	 */
	void enterStringXQ(XQueryParser.StringXQContext ctx);
	/**
	 * Exit a parse tree produced by the {@code stringXQ}
	 * labeled alternative in {@link XQueryParser#xq}.
	 * @param ctx the parse tree
	 */
	void exitStringXQ(XQueryParser.StringXQContext ctx);
	/**
	 * Enter a parse tree produced by the {@code singleXQ}
	 * labeled alternative in {@link XQueryParser#xq}.
	 * @param ctx the parse tree
	 */
	void enterSingleXQ(XQueryParser.SingleXQContext ctx);
	/**
	 * Exit a parse tree produced by the {@code singleXQ}
	 * labeled alternative in {@link XQueryParser#xq}.
	 * @param ctx the parse tree
	 */
	void exitSingleXQ(XQueryParser.SingleXQContext ctx);
	/**
	 * Enter a parse tree produced by the {@code commaXQ}
	 * labeled alternative in {@link XQueryParser#xq}.
	 * @param ctx the parse tree
	 */
	void enterCommaXQ(XQueryParser.CommaXQContext ctx);
	/**
	 * Exit a parse tree produced by the {@code commaXQ}
	 * labeled alternative in {@link XQueryParser#xq}.
	 * @param ctx the parse tree
	 */
	void exitCommaXQ(XQueryParser.CommaXQContext ctx);
	/**
	 * Enter a parse tree produced by the {@code varXQ}
	 * labeled alternative in {@link XQueryParser#xq}.
	 * @param ctx the parse tree
	 */
	void enterVarXQ(XQueryParser.VarXQContext ctx);
	/**
	 * Exit a parse tree produced by the {@code varXQ}
	 * labeled alternative in {@link XQueryParser#xq}.
	 * @param ctx the parse tree
	 */
	void exitVarXQ(XQueryParser.VarXQContext ctx);
	/**
	 * Enter a parse tree produced by the {@code braceXQ}
	 * labeled alternative in {@link XQueryParser#xq}.
	 * @param ctx the parse tree
	 */
	void enterBraceXQ(XQueryParser.BraceXQContext ctx);
	/**
	 * Exit a parse tree produced by the {@code braceXQ}
	 * labeled alternative in {@link XQueryParser#xq}.
	 * @param ctx the parse tree
	 */
	void exitBraceXQ(XQueryParser.BraceXQContext ctx);
	/**
	 * Enter a parse tree produced by the {@code doubleXQ}
	 * labeled alternative in {@link XQueryParser#xq}.
	 * @param ctx the parse tree
	 */
	void enterDoubleXQ(XQueryParser.DoubleXQContext ctx);
	/**
	 * Exit a parse tree produced by the {@code doubleXQ}
	 * labeled alternative in {@link XQueryParser#xq}.
	 * @param ctx the parse tree
	 */
	void exitDoubleXQ(XQueryParser.DoubleXQContext ctx);
	/**
	 * Enter a parse tree produced by {@link XQueryParser#var}.
	 * @param ctx the parse tree
	 */
	void enterVar(XQueryParser.VarContext ctx);
	/**
	 * Exit a parse tree produced by {@link XQueryParser#var}.
	 * @param ctx the parse tree
	 */
	void exitVar(XQueryParser.VarContext ctx);
	/**
	 * Enter a parse tree produced by {@link XQueryParser#forClause}.
	 * @param ctx the parse tree
	 */
	void enterForClause(XQueryParser.ForClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link XQueryParser#forClause}.
	 * @param ctx the parse tree
	 */
	void exitForClause(XQueryParser.ForClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link XQueryParser#letClause}.
	 * @param ctx the parse tree
	 */
	void enterLetClause(XQueryParser.LetClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link XQueryParser#letClause}.
	 * @param ctx the parse tree
	 */
	void exitLetClause(XQueryParser.LetClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link XQueryParser#whereClause}.
	 * @param ctx the parse tree
	 */
	void enterWhereClause(XQueryParser.WhereClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link XQueryParser#whereClause}.
	 * @param ctx the parse tree
	 */
	void exitWhereClause(XQueryParser.WhereClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link XQueryParser#returnClause}.
	 * @param ctx the parse tree
	 */
	void enterReturnClause(XQueryParser.ReturnClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link XQueryParser#returnClause}.
	 * @param ctx the parse tree
	 */
	void exitReturnClause(XQueryParser.ReturnClauseContext ctx);
	/**
	 * Enter a parse tree produced by the {@code valueEQCond}
	 * labeled alternative in {@link XQueryParser#cond}.
	 * @param ctx the parse tree
	 */
	void enterValueEQCond(XQueryParser.ValueEQCondContext ctx);
	/**
	 * Exit a parse tree produced by the {@code valueEQCond}
	 * labeled alternative in {@link XQueryParser#cond}.
	 * @param ctx the parse tree
	 */
	void exitValueEQCond(XQueryParser.ValueEQCondContext ctx);
	/**
	 * Enter a parse tree produced by the {@code braceCond}
	 * labeled alternative in {@link XQueryParser#cond}.
	 * @param ctx the parse tree
	 */
	void enterBraceCond(XQueryParser.BraceCondContext ctx);
	/**
	 * Exit a parse tree produced by the {@code braceCond}
	 * labeled alternative in {@link XQueryParser#cond}.
	 * @param ctx the parse tree
	 */
	void exitBraceCond(XQueryParser.BraceCondContext ctx);
	/**
	 * Enter a parse tree produced by the {@code orCond}
	 * labeled alternative in {@link XQueryParser#cond}.
	 * @param ctx the parse tree
	 */
	void enterOrCond(XQueryParser.OrCondContext ctx);
	/**
	 * Exit a parse tree produced by the {@code orCond}
	 * labeled alternative in {@link XQueryParser#cond}.
	 * @param ctx the parse tree
	 */
	void exitOrCond(XQueryParser.OrCondContext ctx);
	/**
	 * Enter a parse tree produced by the {@code idEQCond}
	 * labeled alternative in {@link XQueryParser#cond}.
	 * @param ctx the parse tree
	 */
	void enterIdEQCond(XQueryParser.IdEQCondContext ctx);
	/**
	 * Exit a parse tree produced by the {@code idEQCond}
	 * labeled alternative in {@link XQueryParser#cond}.
	 * @param ctx the parse tree
	 */
	void exitIdEQCond(XQueryParser.IdEQCondContext ctx);
	/**
	 * Enter a parse tree produced by the {@code emptyCond}
	 * labeled alternative in {@link XQueryParser#cond}.
	 * @param ctx the parse tree
	 */
	void enterEmptyCond(XQueryParser.EmptyCondContext ctx);
	/**
	 * Exit a parse tree produced by the {@code emptyCond}
	 * labeled alternative in {@link XQueryParser#cond}.
	 * @param ctx the parse tree
	 */
	void exitEmptyCond(XQueryParser.EmptyCondContext ctx);
	/**
	 * Enter a parse tree produced by the {@code andCond}
	 * labeled alternative in {@link XQueryParser#cond}.
	 * @param ctx the parse tree
	 */
	void enterAndCond(XQueryParser.AndCondContext ctx);
	/**
	 * Exit a parse tree produced by the {@code andCond}
	 * labeled alternative in {@link XQueryParser#cond}.
	 * @param ctx the parse tree
	 */
	void exitAndCond(XQueryParser.AndCondContext ctx);
	/**
	 * Enter a parse tree produced by the {@code someCond}
	 * labeled alternative in {@link XQueryParser#cond}.
	 * @param ctx the parse tree
	 */
	void enterSomeCond(XQueryParser.SomeCondContext ctx);
	/**
	 * Exit a parse tree produced by the {@code someCond}
	 * labeled alternative in {@link XQueryParser#cond}.
	 * @param ctx the parse tree
	 */
	void exitSomeCond(XQueryParser.SomeCondContext ctx);
	/**
	 * Enter a parse tree produced by the {@code notCond}
	 * labeled alternative in {@link XQueryParser#cond}.
	 * @param ctx the parse tree
	 */
	void enterNotCond(XQueryParser.NotCondContext ctx);
	/**
	 * Exit a parse tree produced by the {@code notCond}
	 * labeled alternative in {@link XQueryParser#cond}.
	 * @param ctx the parse tree
	 */
	void exitNotCond(XQueryParser.NotCondContext ctx);
	/**
	 * Enter a parse tree produced by {@link XQueryParser#doc}.
	 * @param ctx the parse tree
	 */
	void enterDoc(XQueryParser.DocContext ctx);
	/**
	 * Exit a parse tree produced by {@link XQueryParser#doc}.
	 * @param ctx the parse tree
	 */
	void exitDoc(XQueryParser.DocContext ctx);
	/**
	 * Enter a parse tree produced by the {@code singleAP}
	 * labeled alternative in {@link XQueryParser#ap}.
	 * @param ctx the parse tree
	 */
	void enterSingleAP(XQueryParser.SingleAPContext ctx);
	/**
	 * Exit a parse tree produced by the {@code singleAP}
	 * labeled alternative in {@link XQueryParser#ap}.
	 * @param ctx the parse tree
	 */
	void exitSingleAP(XQueryParser.SingleAPContext ctx);
	/**
	 * Enter a parse tree produced by the {@code doubleAP}
	 * labeled alternative in {@link XQueryParser#ap}.
	 * @param ctx the parse tree
	 */
	void enterDoubleAP(XQueryParser.DoubleAPContext ctx);
	/**
	 * Exit a parse tree produced by the {@code doubleAP}
	 * labeled alternative in {@link XQueryParser#ap}.
	 * @param ctx the parse tree
	 */
	void exitDoubleAP(XQueryParser.DoubleAPContext ctx);
	/**
	 * Enter a parse tree produced by the {@code braceRP}
	 * labeled alternative in {@link XQueryParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterBraceRP(XQueryParser.BraceRPContext ctx);
	/**
	 * Exit a parse tree produced by the {@code braceRP}
	 * labeled alternative in {@link XQueryParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitBraceRP(XQueryParser.BraceRPContext ctx);
	/**
	 * Enter a parse tree produced by the {@code textRP}
	 * labeled alternative in {@link XQueryParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterTextRP(XQueryParser.TextRPContext ctx);
	/**
	 * Exit a parse tree produced by the {@code textRP}
	 * labeled alternative in {@link XQueryParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitTextRP(XQueryParser.TextRPContext ctx);
	/**
	 * Enter a parse tree produced by the {@code attRP}
	 * labeled alternative in {@link XQueryParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterAttRP(XQueryParser.AttRPContext ctx);
	/**
	 * Exit a parse tree produced by the {@code attRP}
	 * labeled alternative in {@link XQueryParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitAttRP(XQueryParser.AttRPContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parentRP}
	 * labeled alternative in {@link XQueryParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterParentRP(XQueryParser.ParentRPContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parentRP}
	 * labeled alternative in {@link XQueryParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitParentRP(XQueryParser.ParentRPContext ctx);
	/**
	 * Enter a parse tree produced by the {@code selfRP}
	 * labeled alternative in {@link XQueryParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterSelfRP(XQueryParser.SelfRPContext ctx);
	/**
	 * Exit a parse tree produced by the {@code selfRP}
	 * labeled alternative in {@link XQueryParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitSelfRP(XQueryParser.SelfRPContext ctx);
	/**
	 * Enter a parse tree produced by the {@code filterRP}
	 * labeled alternative in {@link XQueryParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterFilterRP(XQueryParser.FilterRPContext ctx);
	/**
	 * Exit a parse tree produced by the {@code filterRP}
	 * labeled alternative in {@link XQueryParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitFilterRP(XQueryParser.FilterRPContext ctx);
	/**
	 * Enter a parse tree produced by the {@code commaRP}
	 * labeled alternative in {@link XQueryParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterCommaRP(XQueryParser.CommaRPContext ctx);
	/**
	 * Exit a parse tree produced by the {@code commaRP}
	 * labeled alternative in {@link XQueryParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitCommaRP(XQueryParser.CommaRPContext ctx);
	/**
	 * Enter a parse tree produced by the {@code childrenRP}
	 * labeled alternative in {@link XQueryParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterChildrenRP(XQueryParser.ChildrenRPContext ctx);
	/**
	 * Exit a parse tree produced by the {@code childrenRP}
	 * labeled alternative in {@link XQueryParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitChildrenRP(XQueryParser.ChildrenRPContext ctx);
	/**
	 * Enter a parse tree produced by the {@code tagRP}
	 * labeled alternative in {@link XQueryParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterTagRP(XQueryParser.TagRPContext ctx);
	/**
	 * Exit a parse tree produced by the {@code tagRP}
	 * labeled alternative in {@link XQueryParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitTagRP(XQueryParser.TagRPContext ctx);
	/**
	 * Enter a parse tree produced by the {@code doubleRP}
	 * labeled alternative in {@link XQueryParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterDoubleRP(XQueryParser.DoubleRPContext ctx);
	/**
	 * Exit a parse tree produced by the {@code doubleRP}
	 * labeled alternative in {@link XQueryParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitDoubleRP(XQueryParser.DoubleRPContext ctx);
	/**
	 * Enter a parse tree produced by the {@code singleRP}
	 * labeled alternative in {@link XQueryParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterSingleRP(XQueryParser.SingleRPContext ctx);
	/**
	 * Exit a parse tree produced by the {@code singleRP}
	 * labeled alternative in {@link XQueryParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitSingleRP(XQueryParser.SingleRPContext ctx);
	/**
	 * Enter a parse tree produced by the {@code notFilter}
	 * labeled alternative in {@link XQueryParser#f}.
	 * @param ctx the parse tree
	 */
	void enterNotFilter(XQueryParser.NotFilterContext ctx);
	/**
	 * Exit a parse tree produced by the {@code notFilter}
	 * labeled alternative in {@link XQueryParser#f}.
	 * @param ctx the parse tree
	 */
	void exitNotFilter(XQueryParser.NotFilterContext ctx);
	/**
	 * Enter a parse tree produced by the {@code andFilter}
	 * labeled alternative in {@link XQueryParser#f}.
	 * @param ctx the parse tree
	 */
	void enterAndFilter(XQueryParser.AndFilterContext ctx);
	/**
	 * Exit a parse tree produced by the {@code andFilter}
	 * labeled alternative in {@link XQueryParser#f}.
	 * @param ctx the parse tree
	 */
	void exitAndFilter(XQueryParser.AndFilterContext ctx);
	/**
	 * Enter a parse tree produced by the {@code rpFilter}
	 * labeled alternative in {@link XQueryParser#f}.
	 * @param ctx the parse tree
	 */
	void enterRpFilter(XQueryParser.RpFilterContext ctx);
	/**
	 * Exit a parse tree produced by the {@code rpFilter}
	 * labeled alternative in {@link XQueryParser#f}.
	 * @param ctx the parse tree
	 */
	void exitRpFilter(XQueryParser.RpFilterContext ctx);
	/**
	 * Enter a parse tree produced by the {@code valueEQFilter}
	 * labeled alternative in {@link XQueryParser#f}.
	 * @param ctx the parse tree
	 */
	void enterValueEQFilter(XQueryParser.ValueEQFilterContext ctx);
	/**
	 * Exit a parse tree produced by the {@code valueEQFilter}
	 * labeled alternative in {@link XQueryParser#f}.
	 * @param ctx the parse tree
	 */
	void exitValueEQFilter(XQueryParser.ValueEQFilterContext ctx);
	/**
	 * Enter a parse tree produced by the {@code idEQFilter}
	 * labeled alternative in {@link XQueryParser#f}.
	 * @param ctx the parse tree
	 */
	void enterIdEQFilter(XQueryParser.IdEQFilterContext ctx);
	/**
	 * Exit a parse tree produced by the {@code idEQFilter}
	 * labeled alternative in {@link XQueryParser#f}.
	 * @param ctx the parse tree
	 */
	void exitIdEQFilter(XQueryParser.IdEQFilterContext ctx);
	/**
	 * Enter a parse tree produced by the {@code braceFilter}
	 * labeled alternative in {@link XQueryParser#f}.
	 * @param ctx the parse tree
	 */
	void enterBraceFilter(XQueryParser.BraceFilterContext ctx);
	/**
	 * Exit a parse tree produced by the {@code braceFilter}
	 * labeled alternative in {@link XQueryParser#f}.
	 * @param ctx the parse tree
	 */
	void exitBraceFilter(XQueryParser.BraceFilterContext ctx);
	/**
	 * Enter a parse tree produced by the {@code orFilter}
	 * labeled alternative in {@link XQueryParser#f}.
	 * @param ctx the parse tree
	 */
	void enterOrFilter(XQueryParser.OrFilterContext ctx);
	/**
	 * Exit a parse tree produced by the {@code orFilter}
	 * labeled alternative in {@link XQueryParser#f}.
	 * @param ctx the parse tree
	 */
	void exitOrFilter(XQueryParser.OrFilterContext ctx);
	/**
	 * Enter a parse tree produced by {@link XQueryParser#tagName}.
	 * @param ctx the parse tree
	 */
	void enterTagName(XQueryParser.TagNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link XQueryParser#tagName}.
	 * @param ctx the parse tree
	 */
	void exitTagName(XQueryParser.TagNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link XQueryParser#attName}.
	 * @param ctx the parse tree
	 */
	void enterAttName(XQueryParser.AttNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link XQueryParser#attName}.
	 * @param ctx the parse tree
	 */
	void exitAttName(XQueryParser.AttNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link XQueryParser#filename}.
	 * @param ctx the parse tree
	 */
	void enterFilename(XQueryParser.FilenameContext ctx);
	/**
	 * Exit a parse tree produced by {@link XQueryParser#filename}.
	 * @param ctx the parse tree
	 */
	void exitFilename(XQueryParser.FilenameContext ctx);
}