package faops;

public class Concatenate {
    private MultiGraph concatenatedGraph;

    private MultiGraph graphA;
    private MultiGraph graphB;

    public Concatenate(MultiGraph graphA, MultiGraph graphB)
    {
        this.graphA = graphA;
        this.graphB = graphB;

        concatenatedGraph = new MultiGraph("concatenated");
    }

    public MultiGraph getGraph()
    {
        return concatenatedGraph;
    }

}
