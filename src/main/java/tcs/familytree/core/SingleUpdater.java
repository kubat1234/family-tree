package tcs.familytree.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SingleUpdater<T extends ConnectionData<? extends Identifiable>> {
    Map<Integer, List<T>> data = new HashMap<>();

    public void update(T data){
        if(this.data.containsKey(data.getId())){
            this.data.get(data.getId()).add(data);
        }else{
            this.data.put(data.getId(),List.of(data));
        }
    }

//    public void register()
}
