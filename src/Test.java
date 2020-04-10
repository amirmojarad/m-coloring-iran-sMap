import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Test {
    public static void main(String[] args) throws IOException, CsvException, ParseException {
        Manager manager = new Manager();
        manager.setNeighbours();
        manager.getGraph().display();
    }
}