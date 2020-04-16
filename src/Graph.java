public class Graph {
    //Attributes
    //save connection between states in this 2DArray
    private int graph[][];
    private int size;
    //Constructors
    public Graph() {
        //in this case we using iran's map and iran has 31 states
        this.size = 31;
        this.graph = new int[size][size];
    }

    //Setters and Getters
    public int[][] getGraph() {
        return graph;
    }

    public void setGraph(int[][] graph) {
        this.graph = graph;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
    //Methods
    /**
     * @param row        row of graph array
     * @param col        col of graph array
     */
    void add(int row, int col) {
        this.graph[row][col] = 1;
    }
}
