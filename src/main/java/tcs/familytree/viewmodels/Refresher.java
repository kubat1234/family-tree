package tcs.familytree.viewmodels;

public class Refresher {
    GraphViewModel graphViewModel;
    public Refresher(GraphViewModel graphViewModel){
        this.graphViewModel =  graphViewModel;
    }

    public void refreshGraph(){
        graphViewModel.getMainController().refreshGraph();
    }

    private void fullRefreshOfSimplePersonDescription(){
        graphViewModel.getSimplePersonDescription().hardRefresh();
    }

    private void partialRefreshOfSimplePersonDescription(){
        graphViewModel.getSimplePersonDescription().lightRefresh();
    }

    public void hardRefresh(){
        refreshGraph();
        fullRefreshOfSimplePersonDescription();
    }

    public void centeredPersonChange(){
        refreshGraph();
        partialRefreshOfSimplePersonDescription();
    }

    public void personListChange(){
        refreshGraph();
    }

}
