public class State {
    private Integer graph_number;
    private String title;
    private String id;
    //Constructors
    public State() {
        this.graph_number = 0;
    }
    public State(Integer graph_number) {
        this.graph_number = graph_number;
    }

    public State(Integer graph_number, String title, String id) {
        this.graph_number = graph_number;
        this.title = title;
        this.id = id;
    }

    public State(String name) {
        this.title = name;
        this.graph_number = 0;
    }
    //Setter and Getter


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getGraph_number() {
        return graph_number;
    }

    public void setGraph_number(Integer graph_number) {
        this.graph_number = graph_number;
    }

    //Methods


    @Override
    public String toString() {
        return String.format("%s --- %s", this.title, this.id);
    }
}