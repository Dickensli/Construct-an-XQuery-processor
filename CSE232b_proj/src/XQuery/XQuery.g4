grammar XQuery;

import XPath;


xq  : var													    # varXQ
	| StringConstant											# stringXQ
	| ap														# apXQ
	| '(' xq ')'												# braceXQ
	| xq ',' xq 											    # commaXQ
	| xq '/' rp													# singleXQ
	| xq '//' rp 												# doubleXQ
	| '<' ID '>' '{' xq '}' '<' '/' ID '>'					# constructXQ
	| forClause letClause? whereClause? returnClause    		# flwrXQ
	| letClause xq 												# letXQ
	| 'join''(' xq ',' xq ',' attrs ',' attrs ')'            # joinXQ
	;


var : '$' ID
	;


forClause   : 'for' var 'in' xq (',' var 'in' xq)*
	        ;


letClause   : 'let' var ':=' xq (',' var ':=' xq)*
	        ;


whereClause : 'where' cond
	        ;


returnClause: 'return' xq
	        ;

cond: xq ValueEQ xq 									     # valueEQCond
	| xq IdEQ xq 											 # idEQCond
	| 'empty' '(' xq ')' 		 							 # emptyCond
	| 'some' var 'in' xq (',' var 'in' xq)* 'satisfies' cond # someCond
	| '(' cond ')' 											 # braceCond
	| cond 'and' cond 										 # andCond
	| cond 'or' cond 										 # orCond
	| 'not' cond 											 # notCond
	;


StringConstant: '"'+[a-zA-Z0-9,.!?; '"-]+'"';
attrs: '[' ID (',' ID)* ']';