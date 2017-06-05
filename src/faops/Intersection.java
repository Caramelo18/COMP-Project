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
      newGraph = new Complement(new Union(new Complement(this.graphA).getGraph(), new Complement(this.graphB).getGraph()).getGraph()).getGraph();
  }
}
