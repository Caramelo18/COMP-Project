/* Generated By:JJTree: Do not edit this line. Stmt.java Version 7.0 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
import org.graphstream.graph.implementations.MultiGraph;
import faops.ParseDot;

public
class Stmt extends SimpleNode {
  public Stmt(int id) {
    super(id);
  }

  public Stmt(FAOPS p, int id) {
    super(p, id);
  }

  public MultiGraph eval() {
      for (int i = 0; i < children.length; i++) {
          if (children[i] instanceof Path) {
              ParseDot dot = new ParseDot();
              dot.parseFile(((Path) children[i]).name);
              return dot.getGraph();
          } else if (children[i] instanceof Expr) {
              if (children.length == i + 1) {
                  return ((Expr) children[i]).eval();
              } else {
                  continue;
              }
          } else if (children[i] instanceof Op1) {
              MultiGraph leftOperand = ((Expr) children[i - 1]).eval();
              MultiGraph rightOperand = ((Expr) children[i + 1]).eval();

              return ((Op1) children[i]).eval(leftOperand, rightOperand);
          }
      }

      return null;
  }

}
/* JavaCC - OriginalChecksum=61898d872678e156edbf34f0a0eaea46 (do not edit this line) */
