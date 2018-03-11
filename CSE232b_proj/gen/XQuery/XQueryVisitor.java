// Generated from C:/Users/Dickensli/Desktop/cse232/CSE232b_proj/src/XQuery\XQuery.g4 by ANTLR 4.7
package XQuery;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link XQueryParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface XQueryVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by the {@code constructXQ}
	 * labeled alternative in {@link XQueryParser#xq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstructXQ(XQueryParser.ConstructXQContext ctx);
	/**
	 * Visit a parse tree produced by the {@code flwrXQ}
	 * labeled alternative in {@link XQueryParser#xq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFlwrXQ(XQueryParser.FlwrXQContext ctx);
	/**
	 * Visit a parse tree produced by the {@code apXQ}
	 * labeled alternative in {@link XQueryParser#xq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitApXQ(XQueryParser.ApXQContext ctx);
	/**
	 * Visit a parse tree produced by the {@code letXQ}
	 * labeled alternative in {@link XQueryParser#xq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLetXQ(XQueryParser.LetXQContext ctx);
	/**
	 * Visit a parse tree produced by the {@code stringXQ}
	 * labeled alternative in {@link XQueryParser#xq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringXQ(XQueryParser.StringXQContext ctx);
	/**
	 * Visit a parse tree produced by the {@code singleXQ}
	 * labeled alternative in {@link XQueryParser#xq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSingleXQ(XQueryParser.SingleXQContext ctx);
	/**
	 * Visit a parse tree produced by the {@code commaXQ}
	 * labeled alternative in {@link XQueryParser#xq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommaXQ(XQueryParser.CommaXQContext ctx);
	/**
	 * Visit a parse tree produced by the {@code varXQ}
	 * labeled alternative in {@link XQueryParser#xq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarXQ(XQueryParser.VarXQContext ctx);
	/**
	 * Visit a parse tree produced by the {@code braceXQ}
	 * labeled alternative in {@link XQueryParser#xq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBraceXQ(XQueryParser.BraceXQContext ctx);
	/**
	 * Visit a parse tree produced by the {@code joinXQ}
	 * labeled alternative in {@link XQueryParser#xq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJoinXQ(XQueryParser.JoinXQContext ctx);
	/**
	 * Visit a parse tree produced by the {@code doubleXQ}
	 * labeled alternative in {@link XQueryParser#xq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDoubleXQ(XQueryParser.DoubleXQContext ctx);
	/**
	 * Visit a parse tree produced by {@link XQueryParser#var}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVar(XQueryParser.VarContext ctx);
	/**
	 * Visit a parse tree produced by {@link XQueryParser#forClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForClause(XQueryParser.ForClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link XQueryParser#letClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLetClause(XQueryParser.LetClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link XQueryParser#whereClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhereClause(XQueryParser.WhereClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link XQueryParser#returnClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnClause(XQueryParser.ReturnClauseContext ctx);
	/**
	 * Visit a parse tree produced by the {@code valueEQCond}
	 * labeled alternative in {@link XQueryParser#cond}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValueEQCond(XQueryParser.ValueEQCondContext ctx);
	/**
	 * Visit a parse tree produced by the {@code braceCond}
	 * labeled alternative in {@link XQueryParser#cond}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBraceCond(XQueryParser.BraceCondContext ctx);
	/**
	 * Visit a parse tree produced by the {@code orCond}
	 * labeled alternative in {@link XQueryParser#cond}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrCond(XQueryParser.OrCondContext ctx);
	/**
	 * Visit a parse tree produced by the {@code idEQCond}
	 * labeled alternative in {@link XQueryParser#cond}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdEQCond(XQueryParser.IdEQCondContext ctx);
	/**
	 * Visit a parse tree produced by the {@code emptyCond}
	 * labeled alternative in {@link XQueryParser#cond}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEmptyCond(XQueryParser.EmptyCondContext ctx);
	/**
	 * Visit a parse tree produced by the {@code andCond}
	 * labeled alternative in {@link XQueryParser#cond}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndCond(XQueryParser.AndCondContext ctx);
	/**
	 * Visit a parse tree produced by the {@code someCond}
	 * labeled alternative in {@link XQueryParser#cond}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSomeCond(XQueryParser.SomeCondContext ctx);
	/**
	 * Visit a parse tree produced by the {@code notCond}
	 * labeled alternative in {@link XQueryParser#cond}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNotCond(XQueryParser.NotCondContext ctx);
	/**
	 * Visit a parse tree produced by {@link XQueryParser#attrs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttrs(XQueryParser.AttrsContext ctx);
	/**
	 * Visit a parse tree produced by {@link XQueryParser#doc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDoc(XQueryParser.DocContext ctx);
	/**
	 * Visit a parse tree produced by the {@code singleAP}
	 * labeled alternative in {@link XQueryParser#ap}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSingleAP(XQueryParser.SingleAPContext ctx);
	/**
	 * Visit a parse tree produced by the {@code doubleAP}
	 * labeled alternative in {@link XQueryParser#ap}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDoubleAP(XQueryParser.DoubleAPContext ctx);
	/**
	 * Visit a parse tree produced by the {@code braceRP}
	 * labeled alternative in {@link XQueryParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBraceRP(XQueryParser.BraceRPContext ctx);
	/**
	 * Visit a parse tree produced by the {@code textRP}
	 * labeled alternative in {@link XQueryParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTextRP(XQueryParser.TextRPContext ctx);
	/**
	 * Visit a parse tree produced by the {@code attRP}
	 * labeled alternative in {@link XQueryParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttRP(XQueryParser.AttRPContext ctx);
	/**
	 * Visit a parse tree produced by the {@code parentRP}
	 * labeled alternative in {@link XQueryParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParentRP(XQueryParser.ParentRPContext ctx);
	/**
	 * Visit a parse tree produced by the {@code selfRP}
	 * labeled alternative in {@link XQueryParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelfRP(XQueryParser.SelfRPContext ctx);
	/**
	 * Visit a parse tree produced by the {@code filterRP}
	 * labeled alternative in {@link XQueryParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFilterRP(XQueryParser.FilterRPContext ctx);
	/**
	 * Visit a parse tree produced by the {@code commaRP}
	 * labeled alternative in {@link XQueryParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommaRP(XQueryParser.CommaRPContext ctx);
	/**
	 * Visit a parse tree produced by the {@code childrenRP}
	 * labeled alternative in {@link XQueryParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitChildrenRP(XQueryParser.ChildrenRPContext ctx);
	/**
	 * Visit a parse tree produced by the {@code tagRP}
	 * labeled alternative in {@link XQueryParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTagRP(XQueryParser.TagRPContext ctx);
	/**
	 * Visit a parse tree produced by the {@code doubleRP}
	 * labeled alternative in {@link XQueryParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDoubleRP(XQueryParser.DoubleRPContext ctx);
	/**
	 * Visit a parse tree produced by the {@code singleRP}
	 * labeled alternative in {@link XQueryParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSingleRP(XQueryParser.SingleRPContext ctx);
	/**
	 * Visit a parse tree produced by the {@code notFilter}
	 * labeled alternative in {@link XQueryParser#f}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNotFilter(XQueryParser.NotFilterContext ctx);
	/**
	 * Visit a parse tree produced by the {@code andFilter}
	 * labeled alternative in {@link XQueryParser#f}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndFilter(XQueryParser.AndFilterContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rpFilter}
	 * labeled alternative in {@link XQueryParser#f}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRpFilter(XQueryParser.RpFilterContext ctx);
	/**
	 * Visit a parse tree produced by the {@code valueEQFilter}
	 * labeled alternative in {@link XQueryParser#f}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValueEQFilter(XQueryParser.ValueEQFilterContext ctx);
	/**
	 * Visit a parse tree produced by the {@code idEQFilter}
	 * labeled alternative in {@link XQueryParser#f}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdEQFilter(XQueryParser.IdEQFilterContext ctx);
	/**
	 * Visit a parse tree produced by the {@code braceFilter}
	 * labeled alternative in {@link XQueryParser#f}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBraceFilter(XQueryParser.BraceFilterContext ctx);
	/**
	 * Visit a parse tree produced by the {@code orFilter}
	 * labeled alternative in {@link XQueryParser#f}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrFilter(XQueryParser.OrFilterContext ctx);
	/**
	 * Visit a parse tree produced by {@link XQueryParser#tagName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTagName(XQueryParser.TagNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link XQueryParser#attName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttName(XQueryParser.AttNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link XQueryParser#filename}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFilename(XQueryParser.FilenameContext ctx);
}