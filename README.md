# Construct-an-XQuery-processor
1. Design a straightforward query execution engine receives the simplified XQuery and an input XML file.
2. Evaluate the query using a recursive evaluation routine which, given an XQuery expression (path, concatenation, element creation, etc) and a list of input nodes, produces a list of output nodes.
3. Use ANTLR4 as XQuery Parser and dom4j as XML document parser. 
4. Optimize the query engine by detecting the fact that the FOR and WHERE clause computation can be implemented using the join operator.
---
- ANTLR4 output Java classes for each Xquery rule under `gen`
- Main query engine was written under `src`
