package tcs.familytree.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SingleUpdater<T extends Identifiable> {
    Map<Integer, List<ConnectionData<T>>> elements = new HashMap<>();

    public void update(T data){
        if(elements.containsKey(data.getId())){
            for(ConnectionData<T> element : elements.get(data.getId())){
                element.load(data);
            }
        }
    }

    public void register(ConnectionData<T> data){
        if(!elements.containsKey(data.getId()))
            elements.put(data.getId(),new ArrayList<>());
        elements.get(data.getId()).add(data);
    }

    public void unregister(ConnectionData<T> data){
        if(elements.containsKey(data.getId())){
            elements.get(data.getId()).remove(data);
        }
    }
}
