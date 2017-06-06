PROJECT TITLE: FAOPS: Operations with Finite Automata
 
 
GROUP: G24
Diogo Campos - 201403468 
Grade: 20, Contribution: 25%
Fábio Caramelo - 201404783
Grade: 20, Contribution: 25%
Manuel Gomes - 201402679
Grade: 20, Contribution: 25%
José Oliveira - 201406208
Grade: 20, Contribution: 25%
 
 
 
SUMMARY:
This tool allows users to execute 5 operations over automata: multiply, concatenate, complement, reverse, intersection and union. We have specified a DSL similar to the one given on the example. Users must define the automata on .dot files.
 
 
EXECUTE: 
Compile the program by running the compile.sh script under the src folder:
sh compile.sh
 
Make sure you have the FAOPS.jar file, the gs-core-1.3.jar (available in the examples folder) file, the desired faops file and every .dot file you need under the same folder and run:
 
java -jar FAOPS.jar ex1.faops
 
 
 
DEALING WITH SYNTACTIC ERRORS: 
We have defined the following CFG, with FANAME being the variable name and FILENAME the DSL code file name.
 
start -> (assignment | dump)+
assignment -> ‘FA’ FANAME ‘ = ‘ stmt
stmt -> ‘new(‘ ‘“‘ FILENAME ‘“‘ ‘)’
stmt -> expr
stmt -> expr op1 expr
expr -> op2 stmt ‘)’
expr -> FANAME
expr -> ‘(‘ stmt ‘)’
dump -> FANAME ‘.dump(‘ FILENAME ‘)’ 
op1 -> ‘x’
op1 -> ‘.’
op1 -> ‘int’
op1 -> ‘+’
op2 -> ‘not(‘
op2 -> ‘rev(‘
FANAME -> ([“A”-”Z”])+
FILENAME -> ([“a”-”z”]|[“A”-”Z”]|”-”|”_”|[“0”-”9”]|”.”)+ “.dot”)
 
When the file has an error the program will abort, throwing a parsing exception.
 
 
 
SEMANTIC ANALYSIS:
There is no operator precedence, therefore the precedence should be given with parenthesis.
If a variable is reassigned after the first assignment, the program will overwrite the automata previously stored.
 
 
OVERVIEW: 
To begin we have decided to define our DSL, similar to the one given on the example. Users declare FA variables assigning them filenames where the automata is defined. Then they can operate over the automata and dump the resulting automate into a dot file.
We have used a library, GraphStream, in order to read the dot files and operate with the nodes and edges of the automata, as well as export the desired automatas. 
 
 
TESTSUITE AND TEST INFRASTRUCTURE: 
We have decided to make a testsuite with 7 tests, 6 of them regarding each of the operations and the last one a combination of these operations.
 
 
TASK DISTRIBUTION: 
Diogo Campos - responsible for the grammar and parser
Fábio Caramelo - responsible for reverse, concatenation and union 
Manuel Gomes - responsible for union, intersection and examples
José Oliveira - responsible for complement, NFA to DFA translator, multiply and testsuite
 
Despite the distribution previously mentioned, everybody collaborated in every part of the project, while helping others to fix bugs and find solutions.
 
 
 
PROS: 
The ability to easily perform operations on finite automata using a DSL with simple rules allows any Computer Scientist to quickly export the results to a widely supported DOT-format.
Although it isn’t used, we have implemented a NFA to DFA translator. 
 
 
CONS: 
Due to a limitation in the used library (Graphstream) it is not possible to have edges with the same source and the same destination but with different labels; the library does not accept nodes with the same id. The program could have a user interface to write the file to read.

