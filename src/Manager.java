import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import javafx.scene.layout.Pane;
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
    private ArrayList<IranStates> states;
    //saves all data's that include in "iran.svg" file
    private List<String[]> csv_information;
    //Json file that stores every state and it's neighbours
    JSONObject jsonObject;
    /////////////////////
    Pane pane;

    //Constructors
    public Manager() {
        this.pane = new Pane();
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
    }
    //Setter and Getters

    public Graph getGraph() {
        return graph;
    }

    public ArrayList<IranStates> getStates() {
        return states;
    }

    /**
     * step through among csv_information and stores all information that stores in i, i+1,i+2,i+3
     *
     * @param index
     * @return
     */
    //Methods
    private ArrayList<String[]> makeBag(int index) {
        ArrayList<String[]> bag = new ArrayList<>();
        for (int i = index; i < index + 4; i++)
            bag.add(this.csv_information.get(i));
        return bag;
    }

    /**
     * just for better code! :)
     *
     * @param string
     * @return
     */
    private String splitter(String string) {
        return string.split("\"")[1];
    }

    /**
     * uses in setAllStates method
     * for find that point coordinate starts
     *
     * @param str
     * @return
     */

    private int subString(String str) {
        int counter = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) != 'm') counter++;
            else return counter;
        }
        return counter;
    }

    /**
     * step through among csv_information and create a information bag
     * every bag contains a state information like title name and id and coordinate
     */

    private void setAllStates() {
        for (int i = 0; i < csv_information.size() - 4; i += 4) {
            ArrayList<String[]> bag = makeBag(i);
            String title = splitter(bag.get(2)[0]);
            String id = splitter(bag.get(3)[0]);
            String svgCoordinate = bag.get(1)[0].substring(subString(bag.get(1)[0]));
            IranStates state = new IranStates(GRAPH_NUMBER++, title, id, svgCoordinate);
            this.states.add(state);
        }
    }

    /**
     * read all infos in svg file and stores in csv_information
     *
     * @throws IOException
     * @throws CsvException
     */
    private void initialize() throws IOException, CsvException {
        String SVG_PATH = "E:\\University Courses\\Term 4\\Algorithms\\Assigns\\05 - MiniProject02\\iran.svg";
        FileReader file = new FileReader(SVG_PATH);
        //read data from "iran.svg" file and skip first 6 lines, because those are useless :)
        CSVReader reader = new CSVReaderBuilder(file).withSkipLines(6).build();
        this.csv_information = reader.readAll();
    }

    /**
     * @param title find a state in states containers with title attribute
     * @return state that has "title"
     */

    private IranStates findInStates(String title) {
        for (IranStates state : states)
            if (state.getTitle().equals(title)) return state;
        return null;
    }

    /**
     *
     */
    public void setNeighbours() {
        for (IranStates state : this.states) {
            Integer state_number = state.getGraph_number();
            JSONArray neighbours = (JSONArray) jsonObject.get(state.getTitle());
            for (Object obj : neighbours) {
                String neighbour = (String) obj;
                Integer neighbour_number = Objects.requireNonNull(findInStates(neighbour)).getGraph_number();
                this.graph.add(state_number, neighbour_number);
                state.getNeighbours().add(neighbour_number);
            }
        }
    }

    /**
     * back all of state's colors to white for reuse
     */
    public void setDefaultState() {
        for (IranStates state : states) state.backToDefault();
    }

    /**
     * create and store all states in pane
     *
     * @return a pane that contains all states
     */
    public Pane getMap() {
        for (int i = 0; i < states.size(); i++) {
            pane.getChildren().add(states.get(i).getSVG());
        }
        return pane;
    }


}
