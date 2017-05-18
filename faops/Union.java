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
        while(aIterator.hasNext()){
            AbstractNode nodeA = aIterator.next();

            if(isPoint(nodeA))
                continue;

            String nA = nodeA.getId();

            while(bIterator.hasNext()){
                AbstractNode nodeB = bIterator.next();

                if(isPoint(nodeB))
                    continue;

                String nB = nodeB.getId();

                String nodeId = nA + nB;

                MultiNode newNode = new MultiNode(newGraph, nodeId);
                newGraph.addNode(nodeId);
                String nodeType = getNodeType(nodeA, nodeB);
                newGraph.getNode(nodeId).setAttribute("shape", nodeType);
            }
            bIterator = graphB.getNodeIterator();
        }

        /*Iterator<AbstractNode> it = newGraph.getNodeIterator();
        while(it.hasNext()){
            System.out.println(it.next().getId());
        }*/
    }

    private void createEdges(){
        Iterator<AbstractEdge> aIterator = graphA.getEdgeIterator();
        Iterator<AbstractEdge> bIterator = graphB.getEdgeIterator();

        int id = 9871;
        while(aIterator.hasNext()){
            AbstractEdge aEdge = aIterator.next();

            if(!aEdge.hasAttribute("label"))
                continue;

            String inputA = aEdge.getAttribute("label").toString();
            String sourceA = aEdge.getNode0().getId();
            String destA = aEdge.getNode1().getId();

            while(bIterator.hasNext()){
                AbstractEdge bEdge = bIterator.next();

                if(!bEdge.hasAttribute("label"))
                    continue;

                String inputB = bEdge.getAttribute("label").toString();
                String sourceB = bEdge.getNode0().getId();
                String destB = bEdge.getNode1().getId();

                if(inputA.equals(inputB)){
                    String source = sourceA + sourceB;
                    String dest = destA + destB;

                    newGraph.addEdge(String.valueOf(id), source, dest, true);
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

            if(shapeA.equals("doublecircle") && shapeB.equals("doublecircle"))
                return "doublecircle";
        }
        return "circle";
    }

/*    private String getEdgeDestination(Iterator iterator, String source, String input){
        while(iterator.hasNext()){
            AbstractEdge edge = iterator.next();
        }

    }*/


}
