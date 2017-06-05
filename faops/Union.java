package faops;

import java.util.Iterator;
import org.graphstream.graph.*;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.*;

public class Union {
    private MultiGraph newGraph;

    private MultiGraph graphA;
    private MultiGraph graphB;


    public Union(MultiGraph graphA, MultiGraph graphB)
    {
        this.graphA = graphA;
        this.graphB = graphB;

        newGraph = new MultiGraph("union");
        union();
    }

    public MultiGraph getGraph()
    {
        return newGraph;
    }

    // source: http://stackoverflow.com/questions/4449950/how-do-you-construct-the-union-of-two-dfas
    private void union(){
        createNodes();
        createEdges();

        DumpDot dump = new DumpDot(newGraph);
        dump.dumpFile("exitTest.dot");
    }

    private void createNodes(){
        Iterator<AbstractNode> aIterator = graphA.getNodeIterator();
        Iterator<AbstractNode> bIterator = graphB.getNodeIterator();
        boolean startingDefined = false;

        while(aIterator.hasNext()){            
            AbstractNode nodeA = aIterator.next();

            String nA = nodeA.getId();

            while(bIterator.hasNext()){
                AbstractNode nodeB = bIterator.next();

                if(isPoint(nodeB)){
                    if(isPoint(nodeA) && !startingDefined){
                        String nB = nodeB.getId();
                        String nodeId = nA + nB;

                        MultiNode newNode = new MultiNode(newGraph, nodeId);
                        newGraph.addNode(nodeId);
                        String nodeType = getNodeType(nodeA, nodeB);
                        newGraph.getNode(nodeId).setAttribute("shape", "point");
                        startingDefined = true;
                    } else {
                        continue;
                    }                    
                } else {
                    if(!isPoint(nodeA)){
                        String nB = nodeB.getId();
                        String nodeId = nA + nB;

                        MultiNode newNode = new MultiNode(newGraph, nodeId);
                        newGraph.addNode(nodeId);
                        String nodeType = getNodeType(nodeA, nodeB);
                        newGraph.getNode(nodeId).setAttribute("shape", nodeType);
                    }                    
                }                  
            }

            bIterator = graphB.getNodeIterator();
        }
    }

    private void createEdges(){
        Iterator<AbstractEdge> aIterator = graphA.getEdgeIterator();
        Iterator<AbstractEdge> bIterator = graphB.getEdgeIterator();

        int id = 9871;
        while(aIterator.hasNext()){
            AbstractEdge aEdge = aIterator.next();
            String inputA = "";

            if(aEdge.hasAttribute("label"))
                inputA = aEdge.getAttribute("label").toString();
            
            String sourceA = aEdge.getNode0().getId();
            String destA = aEdge.getNode1().getId();

            while(bIterator.hasNext()){
                AbstractEdge bEdge = bIterator.next();
                String inputB = "";

                if(bEdge.hasAttribute("label"))
                    inputB = bEdge.getAttribute("label").toString();

                String sourceB = bEdge.getNode0().getId();
                String destB = bEdge.getNode1().getId();

                if(inputA.equals(inputB)){
                    String source = sourceA + sourceB;
                    String dest = destA + destB;

                    newGraph.addEdge(String.valueOf(id), source, dest, true);

                    if(bEdge.hasAttribute("label"))
                        newGraph.getEdge(String.valueOf(id)).addAttribute("label", bEdge.getAttribute("label").toString());

                    id++;
                }
            }

            bIterator = graphB.getEdgeIterator();
        }

    }

    private boolean isPoint(AbstractNode node){
        if(node.hasAttribute("shape")){
            String shape = node.getAttribute("shape").toString();

            return shape.equals("point");
        }

        return false;
    }

    private boolean isFinal(AbstractNode node){
        if(node.hasAttribute("shape")){
            String shape = node.getAttribute("shape").toString();
            return shape.equals("doublecircle");
        }
        return false;
    }

    private String getNodeType(AbstractNode nodeA, AbstractNode nodeB){
        if(nodeA.hasAttribute("shape") && nodeB.hasAttribute("shape")){
            String shapeA = nodeA.getAttribute("shape").toString();
            String shapeB = nodeB.getAttribute("shape").toString();

            if((shapeA.equals("doublecircle") && shapeB.equals("doublecircle")) ||
            (shapeA.equals("doublecircle") || shapeB.equals("doublecircle")))
                return "doublecircle";
        }

        return "circle";
    }
}
