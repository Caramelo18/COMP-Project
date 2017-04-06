package faops;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.stream.file.FileSourceDOT;

import java.io.IOException;

public class ParseDot {
    DefaultGraph graph;

    public void parseFile(String path) {
        graph = new DefaultGraph(path) ;
        FileSourceDOT fs = new FileSourceDOT();

        fs.addSink(graph);

        try {
        	fs.readAll(path);
        } catch (IOException e) {
        	e.printStackTrace();
        } finally {
        	fs.removeSink(graph);
        }

        System.out.println("FA " + path + " has " + graph.getEdgeCount() + " edges.");
        System.out.println("FA " + path + " has " + graph.getNodeCount() + " nodes.");
    }

    public Graph getGraph() {
        return graph;
    }
}
