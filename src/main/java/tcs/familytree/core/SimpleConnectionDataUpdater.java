package tcs.familytree.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleConnectionDataUpdater implements ConnectionDataUpdater{
    Map<Class<? extends Identifiable>, Map<Integer, List<ConnectionData<? extends Identifiable>>>> connectionDataElements = new HashMap<>();

    @Override
    public void update(Identifiable data) {
        if(!connectionDataElements.containsKey(data.getClass())){
            if(connectionDataElements.get(data.getClass()).containsKey(data.getId())){
                connectionDataElements.get(data.getClass()).get(data.getId()).forEach(ConnectionData::load);
            }
        }
    }

    @Override
    public void register(ConnectionData<? extends Identifiable> data){
        if(!connectionDataElements.containsKey(data.getClass())){
            connectionDataElements.put(data.getDataClass(), new HashMap<>());
        }
        if(!connectionDataElements.get(data.getDataClass()).containsKey(data.getId())){
            connectionDataElements.get(data.getDataClass()).put(data.getId(), new ArrayList<>());
        }
        connectionDataElements.get(data.getDataClass()).get(data.getId()).add(data);
    }

    @Override
    public void unregister(ConnectionData<? extends Identifiable> data){
        if(connectionDataElements.containsKey(data.getDataClass())){
            if(connectionDataElements.get(data.getDataClass()).containsKey(data.getId())){
                connectionDataElements.get(data.getDataClass()).get(data.getId()).remove(data);
            }
        }
    }
}
