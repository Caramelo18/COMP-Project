package faops;

import java.util.Iterator;
import org.graphstream.graph.*;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.*;

public class Intersection {
  private MultiGraph newGraph;

  private MultiGraph graphA;
  private MultiGraph graphB;

  public Intersection(MultiGraph graphA, MultiGraph graphB)
  {
      this.graphA = graphA;
      this.graphB = graphB;

      newGraph = new MultiGraph("intersection");
      intersection();
  }

  public MultiGraph getGraph()
  {
      return newGraph;
  }

  private void intersection(){
      /*createNodes();
      createEdges();*/

      newGraph = complement(union(complement(this.graphA).getGraph(),complement(this.graphB).getGraph()).getGraph()).getGraph();

      DumpDot dump = new DumpDot(newGraph);
      dump.dumpFile("exitTest.dot");
  }
}
