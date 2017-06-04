package faops;

import java.util.Iterator;
import org.graphstream.graph.*;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.*;

public class Multiply {
  private MultiGraph newGraph;

  private MultiGraph graphA;
  private MultiGraph graphB;

  public Multiply(MultiGraph graphA, MultiGraph graphB)
  {
      this.graphA = graphA;
      this.graphB = graphB;

      newGraph = new MultiGraph("multiply");
      multiply();
  }

  public MultiGraph getGraph()
  {
      return newGraph;
  }

// TODO - FALTA TERMINAR
  private void multiply(){
      

      DumpDot dump = new DumpDot(newGraph);
      dump.dumpFile("exitTest.dot");
  }
}
