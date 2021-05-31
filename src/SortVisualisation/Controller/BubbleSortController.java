package SortVisualisation.Controller;
import SortVisualisation.Model.ChartDataManager;
import SortVisualisation.Model.Sorting.BubbleSort;
import javafx.event.ActionEvent;
public class BubbleSortController extends AbstractSortController {
    public BubbleSortController() {
    }
    public void initialize() {
        this.chartData = new ChartDataManager(barChart);
    }
    public void visualiseInput(ActionEvent actionEvent) {
        super.visualiseInput(actionEvent);
        sorter = null;
        sorter = new BubbleSort(unsortedIntegers);
        chartData.selectNode(0);
        chartData.selectNode(1);
    }

}
