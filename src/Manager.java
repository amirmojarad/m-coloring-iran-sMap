import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Manager {
    //////Static Field
    //its unique for all states
    private static int GRAPH_NUMBER = 0;
    //number of colors that we used in project
    private static int COLORS = 4;
    //Attributes
    private Graph graph;
    //saves all states in this variable
    private ArrayList<State> states;
    //saves all data's that include in "iran.svg" file
    private List<String[]> csv_information;
    //Json file that stores every state and it's neighbours
    JSONObject jsonObject;

    //Constructors
    public Manager() {
        this.graph = new Graph();
        this.states = new ArrayList<>(this.graph.getSize());
        try {
            initialize();
            String JSON_PATH = "E:\\University Courses\\Term 4\\Algorithms\\Assigns\\05 - MiniProject02\\state-neighbours.json";
            this.jsonObject = (JSONObject) new JSONParser().parse(new FileReader(JSON_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        setAllStates();
        setNeighbours();
        coloring();
    }

    public Manager(int size) {
        this();
        this.graph = new Graph(size);
        this.states = new ArrayList<>(size);
    }
    //Setter and Getters

    public Graph getGraph() {
        return graph;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    public ArrayList<State> getStates() {
        return states;
    }

    public void setStates(ArrayList<State> states) {
        this.states = states;
    }

    //Methods

    /**
     * 1 --> Blue
     * 2 --> Green
     * 3 --> Yellow
     * 4 --> Red
     *
     * @param number_of_color assign a number for any colors that exists in colors enum
     * @return relative integer to color value
     */
    private Colors getColor(Integer number_of_color) {
        switch (number_of_color) {
            case 1:
                return Colors.BLUE;
            case 2:
                return Colors.GREEN;
            case 3:
                return Colors.YELLOW;
            case 4:
                return Colors.RED;
        }
        return Colors.WHITE;
    }

    private Integer getNumberOfColor(Colors colors) {
        switch (colors) {
            case RED:
                return 4;
            case YELLOW:
                return 3;
            case GREEN:
                return 2;
            case BLUE:
                return 1;
        }
        return 0;
    }

    private ArrayList<String[]> makeBag(int index) {
        ArrayList<String[]> bag = new ArrayList<>();
        for (int i = index; i < index + 4; i++)
            bag.add(this.csv_information.get(i));
        return bag;
    }

    private String splitter(String string) {
        return string.split("\"")[1];
    }

    private void setAllStates() {
        for (int i = 0; i < csv_information.size() - 4; i += 4) {
            ArrayList<String[]> bag = makeBag(i);
            String title = splitter(bag.get(2)[0]);
            String id = splitter(bag.get(3)[0]);
            State state = new State(GRAPH_NUMBER++, title, id);
            this.states.add(state);
        }
    }

    private void initialize() throws IOException, CsvException {
        String SVG_PATH = "E:\\University Courses\\Term 4\\Algorithms\\Assigns\\05 - MiniProject02\\iran.svg";
        FileReader file = new FileReader(SVG_PATH);
        //read data from "iran.svg" file and skip first 6 lines, because those are useless :)
        CSVReader reader = new CSVReaderBuilder(file).withSkipLines(6).build();
        this.csv_information = reader.readAll();
    }

    private State findInStates(String title) {
        for (State state : states)
            if (state.getTitle().equals(title)) return state;
        return null;
    }

    public void setNeighbours() {
        for (State state : this.states) {
            Integer state_number = state.getGraph_number();
            JSONArray neighbours = (JSONArray) jsonObject.get(state.getTitle());
            for (Object obj : neighbours) {
                String neighbour = (String) obj;
                Integer neighbour_number = Objects.requireNonNull(findInStates(neighbour)).getGraph_number();
                this.graph.add(state_number, neighbour_number);
                state.neighbours.add(neighbour_number);
            }
        }
    }

    boolean isSafe(State state, Colors colors) {
        for (Integer neighbour_number : state.getNeighbours())
            if (graph.getGraph()[state.getGraph_number()][neighbour_number] == 1
                    && colors.equals(states.get(neighbour_number).getColors()))
                return false;
        return true;
    }

    boolean graphColoring(State state) {
        if (state.getGraph_number() == graph.getSize() - 1) return true;
        for (int c = 1; c <= COLORS; c++) {
            if (isSafe(state, getColor(c))) {
                state.setColors(getColor(c));
                if (graphColoring(states.get(state.getGraph_number() + 1)))
                    return true;
                state.setColors(Colors.WHITE);
            }
        }
        return false;
    }

    boolean coloring() {
        if (!graphColoring(states.get(0))) {
            System.out.println("Solution does not exist");
            return false;
        }
//        states.get(30).setColors(Colors.RED);
        print();
        return true;
    }

    void print() {

//        for (State state : states){
//            if (state.getColors().equals(Colors.WHITE)) System.out.println(state);
//        }

        for (State state : states) {
            System.out.print(state + ":(" + state.getColors() + ") -->");
            for (Integer integer : state.getNeighbours())
                System.out.print(states.get(integer) + ":(" + states.get(integer).getColors() + ") ");
            System.out.println();
        }
    }
}
