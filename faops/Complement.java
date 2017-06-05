package faops;

import java.util.Collection;
import java.util.Iterator;
import java.util.ArrayList;
import org.graphstream.graph.*;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.*;
import org.graphstream.graph.implementations.AbstractEdge;
import org.graphstream.graph.implementations.DefaultGraph;

public class Complement {
private MultiGraph newGraph;

private MultiGraph initGraph;

public Complement(MultiGraph initGraph)
{
        this.initGraph = initGraph;

        newGraph = new MultiGraph("complement");
        complement();
}

public MultiGraph getGraph(){
        return newGraph;
}

public void complement(){
        createNodes();
        createEdges();
}

private void createNodes(){
        Iterator<AbstractNode> tempIterator = initGraph.getNodeIterator();

        while(tempIterator.hasNext()) {
                AbstractNode tempNode = tempIterator.next();

                String tempShape = tempNode.getAttribute("shape").toString();
                String tempID = tempNode.getId();



                if (tempShape.equals("point")) {
                        MultiNode newNode = new MultiNode(this.newGraph, tempID);
                        newGraph.addNode(tempID);
                        newGraph.getNode(tempID).setAttribute("shape", "point");
                } else if (tempShape.equals("circle")) {
                        MultiNode newNode = new MultiNode(this.newGraph, tempID);
                        newGraph.addNode(tempID);
                        newGraph.getNode(tempID).setAttribute("shape", "doublecircle");
                } else if (tempShape.equals("doublecircle")) {
                        MultiNode newNode = new MultiNode(this.newGraph, tempID);
                        newGraph.addNode(tempID);
                        newGraph.getNode(tempID).setAttribute("shape", "circle");
                }
        }
}

private void createEdges(){
        Iterator<AbstractEdge> tempIterator = initGraph.getEdgeIterator();

        int id = 2210;
        while(tempIterator.hasNext()) {
                AbstractEdge tempEdge = tempIterator.next();

                String tempSource = tempEdge.getNode0().getId();
                String tempDest = tempEdge.getNode1().getId();

                newGraph.addEdge(String.valueOf(id),tempSource,tempDest,true);

                if(tempEdge.hasAttribute("label")){
                  String tempInput = tempEdge.getAttribute("label").toString();
                  newGraph.getEdge(String.valueOf(id)).addAttribute("label",tempInput);
                }

                id++;
        }
}

//aux functions
private boolean isPoint(AbstractNode node){
        if(node.hasAttribute("shape")) {
                String shape = node.getAttribute("shape").toString();
                return shape.equals("point");
        }
        return false;
}

private boolean isFinal(AbstractNode node){
        if(node.hasAttribute("shape")) {
                String shape = node.getAttribute("shape").toString();
                return shape.equals("doublecircle");
        }
        return false;
}

private String getNodeType(AbstractNode tempNode, AbstractNode nodeB){
        if(tempNode.hasAttribute("shape") && nodeB.hasAttribute("shape")) {
                String shapeA = tempNode.getAttribute("shape").toString();
                String shapeB = nodeB.getAttribute("shape").toString();

                if(shapeA.equals("doublecircle") && shapeB.equals("doublecircle"))
                        return "doublecircle";
        }
        return "circle";
}


}
