package faops;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.stream.file.FileSourceDOT;

import java.io.IOException;

public class ParseDot {
    public static MultiGraph graph;

    public static MultiGraph parseFile(String path) {
        graph = new MultiGraph(path) ;
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

        return graph;
    }

    public MultiGraph getGraph() {
        return graph;
    }
}
