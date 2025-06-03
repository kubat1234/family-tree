package tcs.familytree.viewmodels;

public class Refresher {
    GraphViewModel graphViewModel;
    public Refresher(GraphViewModel graphViewModel){
        this.graphViewModel =  graphViewModel;
    }

    public void refreshOld(){
        graphViewModel.getMainController().refresh();
    }


}
