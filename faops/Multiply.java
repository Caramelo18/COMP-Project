package faops;

import java.util.Iterator;
import java.util.Vector;
import java.util.HashMap;
import org.graphstream.graph.*;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.*;

public class Multiply {
  private MultiGraph newGraph;

  private MultiGraph graphA;
  private MultiGraph graphB;

  private HashMap<String,Vector<String>> aNodes;
  private HashMap<String,Vector<String>> bNodes;

  public Multiply(MultiGraph graphA, MultiGraph graphB){
      this.graphA = graphA;
      this.graphB = graphB;

      this.aNodes = new HashMap<String,Vector<String>>();
      this.bNodes = new HashMap<String,Vector<String>>();

      newGraph = new MultiGraph("multiply");
      multiply();
  }

  public MultiGraph getGraph(){
      return newGraph;
  }

// TODO - FALTA TERMINAR
  private void multiply(){
      createNodes();
      //createEdges();

      DumpDot dump = new DumpDot(newGraph);
      dump.dumpFile("exitTest.dot");
  }

  private void createNodes(){
      Iterator<AbstractNode> aIterator = graphA.getNodeIterator();
      Iterator<AbstractNode> bIterator = graphB.getNodeIterator();
      Vector<String> tempNodes = new Vector<String>();
      Vector<String> tempNodes2 = new Vector<String>();

      while(aIterator.hasNext()){
          tempNodes = new Vector<String>();
          AbstractNode nodeA = aIterator.next();

          if(nodeA.getAttribute("shape").equals("point"))
            continue;

          String nA = nodeA.getId();

          while(bIterator.hasNext()){
              tempNodes2 = new Vector<String>();

              AbstractNode nodeB = bIterator.next();

              if(nodeB.getAttribute("shape").equals("point"))
                continue;

              String nB = nodeB.getId();

              String nodeId = nA + nB;

              tempNodes.add(nodeId);

              MultiNode newNode = new MultiNode(newGraph, nodeId);
              newGraph.addNode(nodeId);

              if (nodeA.getAttribute("shape").equals("doublecircle") && nodeB.getAttribute("shape").equals("doublecircle")) {
                newGraph.getNode(nodeId).setAttribute("shape", "doublecircle");
              }

              if (bNodes.containsKey(nB)) {
                tempNodes2 = bNodes.get(nB);
                tempNodes2.add(nodeId);
                bNodes.replace(nB,tempNodes2);
              } else {
                tempNodes2.add(nodeId);
                bNodes.put(nB,tempNodes2);
              }
          }

          bIterator = graphB.getNodeIterator();
          aNodes.put(nA,tempNodes);
      }
  }
}
