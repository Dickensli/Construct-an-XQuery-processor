// Generated from C:/Users/Dickensli/Desktop/cse232/CSE232b_proj/src/proj\XPath.g4 by ANTLR 4.7
package XPath;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link XPathParser}.
 */
public interface XPathListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link XPathParser#doc}.
	 * @param ctx the parse tree
	 */
	void enterDoc(XPathParser.DocContext ctx);
	/**
	 * Exit a parse tree produced by {@link XPathParser#doc}.
	 * @param ctx the parse tree
	 */
	void exitDoc(XPathParser.DocContext ctx);
	/**
	 * Enter a parse tree produced by the {@code singleAP}
	 * labeled alternative in {@link XPathParser#ap}.
	 * @param ctx the parse tree
	 */
	void enterSingleAP(XPathParser.SingleAPContext ctx);
	/**
	 * Exit a parse tree produced by the {@code singleAP}
	 * labeled alternative in {@link XPathParser#ap}.
	 * @param ctx the parse tree
	 */
	void exitSingleAP(XPathParser.SingleAPContext ctx);
	/**
	 * Enter a parse tree produced by the {@code doubleAP}
	 * labeled alternative in {@link XPathParser#ap}.
	 * @param ctx the parse tree
	 */
	void enterDoubleAP(XPathParser.DoubleAPContext ctx);
	/**
	 * Exit a parse tree produced by the {@code doubleAP}
	 * labeled alternative in {@link XPathParser#ap}.
	 * @param ctx the parse tree
	 */
	void exitDoubleAP(XPathParser.DoubleAPContext ctx);
	/**
	 * Enter a parse tree produced by the {@code braceRP}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterBraceRP(XPathParser.BraceRPContext ctx);
	/**
	 * Exit a parse tree produced by the {@code braceRP}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitBraceRP(XPathParser.BraceRPContext ctx);
	/**
	 * Enter a parse tree produced by the {@code textRP}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterTextRP(XPathParser.TextRPContext ctx);
	/**
	 * Exit a parse tree produced by the {@code textRP}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitTextRP(XPathParser.TextRPContext ctx);
	/**
	 * Enter a parse tree produced by the {@code attRP}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterAttRP(XPathParser.AttRPContext ctx);
	/**
	 * Exit a parse tree produced by the {@code attRP}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitAttRP(XPathParser.AttRPContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parentRP}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterParentRP(XPathParser.ParentRPContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parentRP}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitParentRP(XPathParser.ParentRPContext ctx);
	/**
	 * Enter a parse tree produced by the {@code selfRP}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterSelfRP(XPathParser.SelfRPContext ctx);
	/**
	 * Exit a parse tree produced by the {@code selfRP}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitSelfRP(XPathParser.SelfRPContext ctx);
	/**
	 * Enter a parse tree produced by the {@code filterRP}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterFilterRP(XPathParser.FilterRPContext ctx);
	/**
	 * Exit a parse tree produced by the {@code filterRP}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitFilterRP(XPathParser.FilterRPContext ctx);
	/**
	 * Enter a parse tree produced by the {@code commaRP}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterCommaRP(XPathParser.CommaRPContext ctx);
	/**
	 * Exit a parse tree produced by the {@code commaRP}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitCommaRP(XPathParser.CommaRPContext ctx);
	/**
	 * Enter a parse tree produced by the {@code childrenRP}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterChildrenRP(XPathParser.ChildrenRPContext ctx);
	/**
	 * Exit a parse tree produced by the {@code childrenRP}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitChildrenRP(XPathParser.ChildrenRPContext ctx);
	/**
	 * Enter a parse tree produced by the {@code tagRP}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterTagRP(XPathParser.TagRPContext ctx);
	/**
	 * Exit a parse tree produced by the {@code tagRP}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitTagRP(XPathParser.TagRPContext ctx);
	/**
	 * Enter a parse tree produced by the {@code doubleRP}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterDoubleRP(XPathParser.DoubleRPContext ctx);
	/**
	 * Exit a parse tree produced by the {@code doubleRP}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitDoubleRP(XPathParser.DoubleRPContext ctx);
	/**
	 * Enter a parse tree produced by the {@code singleRP}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterSingleRP(XPathParser.SingleRPContext ctx);
	/**
	 * Exit a parse tree produced by the {@code singleRP}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitSingleRP(XPathParser.SingleRPContext ctx);
	/**
	 * Enter a parse tree produced by the {@code notFilter}
	 * labeled alternative in {@link XPathParser#f}.
	 * @param ctx the parse tree
	 */
	void enterNotFilter(XPathParser.NotFilterContext ctx);
	/**
	 * Exit a parse tree produced by the {@code notFilter}
	 * labeled alternative in {@link XPathParser#f}.
	 * @param ctx the parse tree
	 */
	void exitNotFilter(XPathParser.NotFilterContext ctx);
	/**
	 * Enter a parse tree produced by the {@code andFilter}
	 * labeled alternative in {@link XPathParser#f}.
	 * @param ctx the parse tree
	 */
	void enterAndFilter(XPathParser.AndFilterContext ctx);
	/**
	 * Exit a parse tree produced by the {@code andFilter}
	 * labeled alternative in {@link XPathParser#f}.
	 * @param ctx the parse tree
	 */
	void exitAndFilter(XPathParser.AndFilterContext ctx);
	/**
	 * Enter a parse tree produced by the {@code rpFilter}
	 * labeled alternative in {@link XPathParser#f}.
	 * @param ctx the parse tree
	 */
	void enterRpFilter(XPathParser.RpFilterContext ctx);
	/**
	 * Exit a parse tree produced by the {@code rpFilter}
	 * labeled alternative in {@link XPathParser#f}.
	 * @param ctx the parse tree
	 */
	void exitRpFilter(XPathParser.RpFilterContext ctx);
	/**
	 * Enter a parse tree produced by the {@code valueEQFilter}
	 * labeled alternative in {@link XPathParser#f}.
	 * @param ctx the parse tree
	 */
	void enterValueEQFilter(XPathParser.ValueEQFilterContext ctx);
	/**
	 * Exit a parse tree produced by the {@code valueEQFilter}
	 * labeled alternative in {@link XPathParser#f}.
	 * @param ctx the parse tree
	 */
	void exitValueEQFilter(XPathParser.ValueEQFilterContext ctx);
	/**
	 * Enter a parse tree produced by the {@code idEQFilter}
	 * labeled alternative in {@link XPathParser#f}.
	 * @param ctx the parse tree
	 */
	void enterIdEQFilter(XPathParser.IdEQFilterContext ctx);
	/**
	 * Exit a parse tree produced by the {@code idEQFilter}
	 * labeled alternative in {@link XPathParser#f}.
	 * @param ctx the parse tree
	 */
	void exitIdEQFilter(XPathParser.IdEQFilterContext ctx);
	/**
	 * Enter a parse tree produced by the {@code braceFilter}
	 * labeled alternative in {@link XPathParser#f}.
	 * @param ctx the parse tree
	 */
	void enterBraceFilter(XPathParser.BraceFilterContext ctx);
	/**
	 * Exit a parse tree produced by the {@code braceFilter}
	 * labeled alternative in {@link XPathParser#f}.
	 * @param ctx the parse tree
	 */
	void exitBraceFilter(XPathParser.BraceFilterContext ctx);
	/**
	 * Enter a parse tree produced by the {@code orFilter}
	 * labeled alternative in {@link XPathParser#f}.
	 * @param ctx the parse tree
	 */
	void enterOrFilter(XPathParser.OrFilterContext ctx);
	/**
	 * Exit a parse tree produced by the {@code orFilter}
	 * labeled alternative in {@link XPathParser#f}.
	 * @param ctx the parse tree
	 */
	void exitOrFilter(XPathParser.OrFilterContext ctx);
	/**
	 * Enter a parse tree produced by {@link XPathParser#tagName}.
	 * @param ctx the parse tree
	 */
	void enterTagName(XPathParser.TagNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link XPathParser#tagName}.
	 * @param ctx the parse tree
	 */
	void exitTagName(XPathParser.TagNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link XPathParser#attName}.
	 * @param ctx the parse tree
	 */
	void enterAttName(XPathParser.AttNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link XPathParser#attName}.
	 * @param ctx the parse tree
	 */
	void exitAttName(XPathParser.AttNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link XPathParser#filename}.
	 * @param ctx the parse tree
	 */
	void enterFilename(XPathParser.FilenameContext ctx);
	/**
	 * Exit a parse tree produced by {@link XPathParser#filename}.
	 * @param ctx the parse tree
	 */
	void exitFilename(XPathParser.FilenameContext ctx);
}