package faops;

import java.util.Iterator;
import org.graphstream.graph.*;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.*;

public class Concatenate {
    private MultiGraph concatenatedGraph;

    private MultiGraph graphA;
    private MultiGraph graphB;

    private String lastNodeA = null;
    private String firstNodeB = null;

    public Concatenate(MultiGraph graphA, MultiGraph graphB)
    {
        this.graphA = graphA;
        this.graphB = graphB;

        concatenatedGraph = new MultiGraph("concatenated");

        concatenate();
    }

    public MultiGraph getGraph()
    {
        return concatenatedGraph;
    }

    private void concatenate()
    {
        joinNodes();
        joinEdges();

        DumpDot dump = new DumpDot(concatenatedGraph);
        dump.dumpFile("exitTest.dot");
    }

    private void joinNodes()
    {

        Iterator<AbstractNode> nodeIterator = graphA.getNodeIterator();
        while(nodeIterator.hasNext()){
            AbstractNode node = nodeIterator.next();
            concatenatedGraph.addNode(node.getId());

            if(node.hasAttribute("shape")){
                String shape = node.getAttribute("shape").toString();
                if(shape.equals("circle")){
                    concatenatedGraph.getNode(node.getId()).addAttribute("shape", "circle");
                }
                else if(shape.equals("doublecircle")){
                    concatenatedGraph.getNode(node.getId()).addAttribute("shape", "circle");
                    lastNodeA = node.getId();
                }
                else{
                    concatenatedGraph.getNode(node.getId()).addAttribute("shape", "point");
                }
            }

        }

        System.out.println("lastNodeA: " + lastNodeA);

        nodeIterator = graphB.getNodeIterator();
        while(nodeIterator.hasNext()){
            AbstractNode node = nodeIterator.next();
            concatenatedGraph.addNode(node.getId());

            if(node.hasAttribute("shape")){
                String shape = node.getAttribute("shape").toString();
                if(shape.equals("point")){
                    firstNodeB = node.getId();
                    concatenatedGraph.getNode(node.getId()).addAttribute("shape", "circle");
                    //concatenatedGraph.removeNode(node.getId());
                    continue;
                }

                concatenatedGraph.getNode(node.getId()).addAttribute("shape", shape);
            }
        }
        System.out.println("firstNodeB: " + firstNodeB);
    }

    private void joinEdges()
    {
        Iterator<AbstractEdge> iterator = graphA.getEdgeIterator();
        int i = 5656;
        while(iterator.hasNext()){
            AbstractEdge e = iterator.next();

            String source = e.getNode0().getId();
            String dest = e.getNode1().getId();
            concatenatedGraph.addEdge(String.valueOf(i), source, dest, true);

            if(e.hasAttribute("label"))
                concatenatedGraph.getEdge(String.valueOf(i)).addAttribute("label", e.getAttribute("label").toString());
            i++;
        }

        iterator = graphB.getEdgeIterator();
        while(iterator.hasNext()){
            AbstractEdge e = iterator.next();

            String source = e.getNode0().getId();
            String dest = e.getNode1().getId();
            concatenatedGraph.addEdge(String.valueOf(i), source, dest, true);

            if(e.hasAttribute("label"))
                concatenatedGraph.getEdge(String.valueOf(i)).addAttribute("label", e.getAttribute("label").toString());
            i++;
        }
        concatenatedGraph.addEdge(String.valueOf(i), lastNodeA, firstNodeB, true);
    }



}
