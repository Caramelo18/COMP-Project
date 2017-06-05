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
  Vector<String> initNodeA;
  Vector<String> initNodeB;

  public Multiply(MultiGraph graphA, MultiGraph graphB){
      this.graphA = graphA;
      this.graphB = graphB;

      this.aNodes = new HashMap<String,Vector<String>>();
      this.bNodes = new HashMap<String,Vector<String>>();

      this.initNodeA = getInitialNodes(this.graphA);
      this.initNodeB = getInitialNodes(this.graphB);

      newGraph = new MultiGraph("multiply");
      multiply();
  }

  public MultiGraph getGraph(){
      return newGraph;
  }

  private void multiply(){
      createNodes();
      createEdges();
  }

  private void createNodes(){
      Iterator<AbstractNode> aIterator = graphA.getNodeIterator();
      Iterator<AbstractNode> bIterator = graphB.getNodeIterator();
      Vector<String> tempNodes = new Vector<String>();
      Vector<String> tempNodes2 = new Vector<String>();

      newGraph.addNode("Start");
      newGraph.getNode("Start").setAttribute("shape", "point");

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

  private void createEdges(){
    Iterator<AbstractEdge> aIterator = graphA.getEdgeIterator();
    Iterator<AbstractEdge> bIterator = graphB.getEdgeIterator();

    int id = 1096;

    for (String x : initNodeA) {
      for (String y : initNodeB) {
        newGraph.addEdge(String.valueOf(id),"Start", x + y,true);
        id++;
      }
    }

    while(aIterator.hasNext()){
        AbstractEdge aEdge = aIterator.next();

        String inputA;

        if (aEdge.hasAttribute("label")) {
          inputA = aEdge.getAttribute("label").toString();
          String sourceA = aEdge.getNode0().getId();
          String destA = aEdge.getNode1().getId();

          while(bIterator.hasNext()){
              AbstractEdge bEdge = bIterator.next();
              String inputB;

              if(bEdge.hasAttribute("label")){
                inputB = bEdge.getAttribute("label").toString();
                String sourceB = bEdge.getNode0().getId();
                String destB = bEdge.getNode1().getId();

                if (inputA.equals(inputB)) {
                  newGraph.addEdge(String.valueOf(id),sourceA + sourceB,destA + destB,true);
                  newGraph.getEdge(String.valueOf(id)).addAttribute("label",inputA);
                }

              }
              id++;
          }

          id++;
        }
        bIterator = graphB.getEdgeIterator();
    }
  }

  private Vector<String> getInitialNodes(MultiGraph graph){
    Vector<String> temp = new Vector<String>();
    Vector<String> ret = new Vector<String>();

    Iterator<AbstractNode> itNode = graph.getNodeIterator();
    Iterator<AbstractEdge> itEdge = graph.getEdgeIterator();

    while(itNode.hasNext()){
        AbstractNode node = itNode.next();

        if(node.getAttribute("shape").equals("point"))
          temp.add(node.getId());
    }

    while(itEdge.hasNext()){
        AbstractEdge edge = itEdge.next();

        String source = edge.getNode0().getId();
        String dest = edge.getNode1().getId();

        if (temp.contains(source)) {
          ret.add(dest);
        }
    }

    return ret;
  }
}
