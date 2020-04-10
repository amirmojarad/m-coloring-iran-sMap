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

    public Graph(int size) {
        this.size = size;
        this.graph = new int[this.size][this.size];
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

    /**
     * @param row row of graph array
     * @param col col of graph array
     * @return a value that exist in graph[row][col]
     * @throws ArrayIndexOutOfBoundsException when has a request to access outer range of array
     */
    int get(int row, int col) throws ArrayIndexOutOfBoundsException {
        try {
            return this.graph[row][col];
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("Array index out of Bound");
        }
        return -1;
    }

    void display() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++)
                System.out.print(this.graph[i][j] + " ");
            System.out.println();
        }
    }
}
