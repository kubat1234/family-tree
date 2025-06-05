package tcs.familytree.viewmodels;

public class Refresher {
    GraphViewModel graphViewModel;
    public Refresher(GraphViewModel graphViewModel){
        this.graphViewModel =  graphViewModel;
    }

    public void refreshGraph(){
        if(graphViewModel.getMainController() != null){
            graphViewModel.getMainController().refreshGraph();
        }
    }

    private void fullRefreshOfSimplePersonDescription(){
        if(graphViewModel.getSimplePersonDescription() != null){
            graphViewModel.getSimplePersonDescription().hardRefresh();
        }
    }

    private void partialRefreshOfSimplePersonDescription(){
        if(graphViewModel.getSimplePersonDescription() != null){
            graphViewModel.getSimplePersonDescription().lightRefresh();
        }
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
