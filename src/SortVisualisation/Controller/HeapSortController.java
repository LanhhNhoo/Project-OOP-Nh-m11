package SortVisualisation.Controller;
import SortVisualisation.Model.ChartDataManager;
import SortVisualisation.Model.Sorting.HeapSort;
import javafx.event.ActionEvent;
public class HeapSortController extends AbstractSortController {

    public HeapSortController() {
    }
    public void initialize() {
        this.chartData = new ChartDataManager(barChart);
    }
    public void visualiseInput(ActionEvent actionEvent) {
        super.visualiseInput(actionEvent);
        sorter = null;
       sorter = new HeapSort(unsortedIntegers);
    }

}
