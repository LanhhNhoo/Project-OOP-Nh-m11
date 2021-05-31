package SortVisualisation.Controller;
import SortVisualisation.Model.ChartDataManager;
import SortVisualisation.Model.Pointer;
import SortVisualisation.Model.RandomGen;
import SortVisualisation.Model.Sorting.AbstractSort;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
public abstract class AbstractSortController {
    @FXML
    protected Slider sldrDelay;
    @FXML
    protected TextField fldDelay;
    @FXML
    protected Button btnOneStep;
    @FXML
    protected Button btnStartPause;
    @FXML
    protected Button btnInput;
    @FXML
    protected BarChart barChart;
    @FXML
    protected LineChart lineChart;
    @FXML
    protected LineChart<String,Number> LineChart;
    @FXML
    protected TextField fldInput;
    protected ChartDataManager chartData;
    protected int[] unsortedIntegers;
    protected AbstractSort sorter;
    protected SortingThread sortingThread;
    protected int Data;
    protected int startRange = 0;
    protected int endRange   = 1000;
    protected int step       = 100;
    protected void visualiseInput(ActionEvent actionEvent) {
        // @TODO: fix proper error handling
        if (!textFieldHasValidInt(fldInput)) {
            System.out.println("Error: You have not entered a number for how many bars you would like to see.");
            return;
        }
        unsortedIntegers = RandomGen.generateRandomInts(Integer.parseInt(fldInput.getText()));
        updateBarChartData();
        sortingThread = null;
        btnStartPause.setText("Start");
        btnStartPause.disableProperty().setValue(false);
        btnOneStep.disableProperty().setValue(false);
        System.out.println(btnInput.getText() + " == Done");
    }
    @FXML
    public void updateDelaySlider(Event event) {
        if (textFieldHasValidInt(fldDelay)) {
            sldrDelay.setValue(Double.parseDouble(fldDelay.getText()));
        }
    }
    public void updateDelayField(Event event) {
        Double value = sldrDelay.getValue();
        fldDelay.setText(value.intValue() + "");
    }
    public void startOrPauseSorting(ActionEvent actionEvent) {
        if (btnStartPause.getText().equals("Start")) {
            btnStartPause.setText("Pause");
            if (sortingThread == null) {
                sortingThread = new SortingThread();
                sortingThread.start();
            } else {
                sortingThread.running = true;
            }
        } else {
            btnStartPause.setText("Start");
            sortingThread.running = false;
        }

        System.out.println(btnStartPause.getText() + " == Done");
    }
    public void visualiseOneSortingStep(ActionEvent actionEvent) {
        unsortedIntegers = sorter.sortOneStep();
        updateBarChartData();
        updateBarChartSelected();
        System.out.println(btnOneStep.getText() + " == Done");
    }

    protected int getDelayPerStep() {
        int delay = 100; // defaults to 100ms
        if (textFieldHasValidInt(fldDelay))
            delay = Integer.parseInt(fldDelay.getText());
        return delay;
    }
    protected boolean textFieldHasValidInt(TextField fldText) {
        try {
            int i = Integer.parseInt(fldText.getText());
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Input: " + fldText.getText() + " could not be parsed to an integer");
        }
        return false;
    }

    protected void updateBarChartData() {
        chartData.updateDataSet(unsortedIntegers);
        chartData.load();
        chartData.styleChartData("BarChart-default");
    }
    protected void updateBarChartSelected() {
        chartData.clearSelectedNodes();
        Pointer pointer = sorter.getPointer();
        if( pointer.getIndices()!=null) {
            pointer.getIndices().forEach(i ->{
                if(pointer.getIndices().size()==1)
                    chartData.selectNode2(i);
                else chartData.selectNode(i);
            });

        }
    }
    private class SortingThread extends Thread {
        private volatile boolean running = true;
        @Override
        public void run() {
            while (!sorter.isFinished()) {
                while (!running) {
                   Thread.yield();
                }
                unsortedIntegers = sorter.sortOneStep();
                Platform.runLater(() -> {
                    updateBarChartData();
                    updateBarChartSelected();
                });

                try {
                    Thread.sleep(getDelayPerStep());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
