grammar XPath;

doc: 'doc("' filename '")';


//absolute path
ap : doc  '/' rp     #singleAP
   | doc  '//' rp    #doubleAP
   ;


//relative path
rp : tagName                   #tagRP
   | '*'                       #childrenRP
   | '.'                       #selfRP
   | '..'                      #parentRP
   | 'text()'                  #textRP
   | '@' attName               #attRP
   | '(' rp ')'                #braceRP
   | rp '/' rp                 #singleRP
   | rp '//' rp                #doubleRP
   | rp '[' f ']'              #filterRP
   | rp ',' rp                 #commaRP
   ;

//path filter
f : rp                         #rpFilter
  | rp ValueEQ rp              #valueEQFilter
  | rp IdEQ rp                 #idEQFilter
  | '(' f ')'                  #braceFilter
  | f 'and' f                  #andFilter
  | f 'or' f                   #orFilter
  | 'not' f                    #notFilter
  ;

tagName:  ID;
attName:  ID;

ValueEQ: '=' | 'eq';
IdEQ: '==' | 'is';
ID: ['"a-zA-Z0-9_-]+ ;

filename: FILENAME;
FILENAME: [a-zA-Z0-9._]+;

WHITESPACE:[ \t\n\r]+ -> skip;