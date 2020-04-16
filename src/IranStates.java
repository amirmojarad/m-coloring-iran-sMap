import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

import java.awt.*;
import java.util.ArrayList;

public class IranStates {
    private Integer graph_number;
    private String title;
    private String id;
    private Colors colors;
    private ArrayList<Integer> neighbours;
    private SVGPath svg;
    //Constructors
    public IranStates(Integer graph_number, String title, String id, String contentString) {
        this.graph_number = graph_number;
        this.title = title;
        this.id = id;
        this.colors = Colors.WHITE;
        this.neighbours = new ArrayList<>();
        this.svg = new SVGPath();
        this.svg.setContent(contentString);
    }
    //Setter and Getter
    public ArrayList<Integer> getNeighbours() {
        return neighbours;
    }

    public Colors getColors() {
        return colors;
    }

    public void setColors(Colors colors) {
        this.colors = colors;
    }

    public String getTitle() {
        return title;
    }
    //Methods

    public Integer getGraph_number() {
        return graph_number;
    }

    public SVGPath getSVG(){
        this.svg.setFill(convertColor());
        this.svg.setStroke(Color.BLACK);
        return this.svg;
    }

    void backToDefault(){
        this.colors = Colors.WHITE;
    }

    private Color convertColor(){
        switch (this.colors){
            case BLUE: return Color.BLUE;
            case RED: return Color.RED;
            case YELLOW: return Color.YELLOW;
            case GREEN: return Color.GREEN;
        }
        return Color.WHITE;
    }


    @Override
    public String toString() {
        return String.format("%s", this.title);
    }
}
