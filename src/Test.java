import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Test{
    public static void main(String[] args) {
        Manager manager = new Manager();

    }

    //Todo start graphic and showing svg files in fx

    Color getColor(Colors colors) {
        switch (colors) {
            case RED:
                return Color.RED;
            case WHITE:
                return Color.WHITE;
            case BLUE:
                return Color.BLUE;
            case YELLOW:
                return Color.YELLOW;
            case GREEN:
                return Color.GREEN;
        }
        return Color.WHITE;
    }
}