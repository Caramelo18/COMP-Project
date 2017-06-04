package faops;

import java.util.Iterator;
import org.graphstream.graph.*;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.*;

public class Product {
  private MultiGraph newGraph;

  private MultiGraph graphA;
  private MultiGraph graphB;

  public Product(MultiGraph graphA, MultiGraph graphB)
  {
      this.graphA = graphA;
      this.graphB = graphB;

      newGraph = new MultiGraph("product");
      product();
  }

  public MultiGraph getGraph()
  {
      return newGraph;
  }

// TODO - FALTA TERMINAR
  private void product(){


      DumpDot dump = new DumpDot(newGraph);
      dump.dumpFile("exitTestProd.dot");
  }
}
