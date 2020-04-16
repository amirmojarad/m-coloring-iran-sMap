import javafx.application.Platform;
import javafx.concurrent.Task;

public class ColoringWorker extends Task {
    Manager manager;
    int delay;
    private static int COLORS = 4;


    public ColoringWorker(Manager manager, int delay) {
        this.manager = manager;
        this.delay = delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

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


    /**
     *
     * @param state
     * @param colors
     * @return true when state's color not to be similar to evey state neighbours
     */


    boolean isSafe(IranStates state, Colors colors) {
        for (Integer neighbour_number : state.getNeighbours())
            if (manager.getGraph().getGraph()[state.getGraph_number()][neighbour_number] == 1
                    && colors.equals(manager.getStates().get(neighbour_number).getColors()))
                return false;
        return true;
    }

    /**
     * at first step check state graph number
     * for colors that have in color list(or number) step through and:
     *      1 - check safety of state with i-th color
     *      2 - set i-th color for state
     *      3 - stop thread and run javafx application thread and dong coloring with javafx
     *      4 - check state graphNumber (it must be be smaller than 31
     *      5 - call graphColoring for next state
     * @param state
     * @return
     */
    boolean graphColoring(IranStates state) {
        if (state.getGraph_number() == manager.getGraph().getSize()) return true;
        for (int c = 1; c <= COLORS; c++) {
            if (isSafe(state, getColor(c))) {
                state.setColors(getColor(c));
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                int finalC = c;
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        manager.pane.getChildren().clear();
                        Main.pane.getChildren().remove(0);
                        Main.pane.getChildren().add(0, manager.getMap());
                    }
                });
                int temp = state.getGraph_number() + 1;
                if (temp == 31) return true;
                if (graphColoring(manager.getStates().get(state.getGraph_number() + 1))) {
                    return true;
                }
                state.setColors(Colors.WHITE);
            }
        }
        return false;
    }

    /**
     * first back all states color to white
     * @return true when it possible to coloring every states in map
     */
    boolean coloring() {
        manager.setDefaultState();
        if (!graphColoring(manager.getStates().get(0))) {
            System.out.println("Solution does not exist");
            return false;
        }
        return true;
    }


    @Override
    protected Object call() throws Exception {
        coloring();
        return null;
    }
}
