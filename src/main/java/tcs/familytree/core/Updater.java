package tcs.familytree.core;

import tcs.familytree.core.person.Person;
import tcs.familytree.core.place.Place;
import tcs.familytree.core.place.PlaceType;
import tcs.familytree.core.relation.Relation;
import tcs.familytree.core.relationtype.RelationType;

public class Updater {
    SingleUpdater<Person> personUpdater = new SingleUpdater<>();
    SingleUpdater<Place> placeUpdater = new SingleUpdater<>();
    SingleUpdater<Relation> relationUpdater = new SingleUpdater<>();
    SingleUpdater<RelationType> relationTypeUpdater = new SingleUpdater<>();
    SingleUpdater<PlaceType> placeTypeUpdater = new SingleUpdater<>();

    public void updatePerson(Person person){
        personUpdater.update(person);
    }
    public void updatePlace(Place place){
        placeUpdater.update(place);
    }
    public void updatePlaceType(PlaceType placeType){
        placeTypeUpdater.update(placeType);
    }
    public void updateRelation(Relation relation){
        relationUpdater.update(relation);
    }
    public void updateRelationType(RelationType relationType){
        relationTypeUpdater.update(relationType);
    }

    public void updatePerson(int id){
        personUpdater.update(id);
    }
    public void updatePlace(int id){
        placeUpdater.update(id);
    }
    public void updatePlaceType(int id){
        placeTypeUpdater.update(id);
    }
    public void updateRelation(int id){
        relationUpdater.update(id);
    }
    public void updateRelationType(int id){
        relationTypeUpdater.update(id);
    }

    public void registerPerson(ConnectionData<Person> data){
        personUpdater.register(data);
    }
    public void registerPlace(ConnectionData<Place> data){
        placeUpdater.register(data);
    }
    public void registerPlaceType(ConnectionData<PlaceType> data){
        placeTypeUpdater.register(data);
    }
    public void registerRelation(ConnectionData<Relation> data){
        relationUpdater.register(data);
    }
    public void registerRelationType(ConnectionData<RelationType> data){
        relationTypeUpdater.register(data);
    }

    public void unregisterPerson(ConnectionData<Person> data){
        personUpdater.unregister(data);
    }
    public void unregisterPlace(ConnectionData<Place> data){
        placeUpdater.unregister(data);
    }
    public void unregisterPlaceType(ConnectionData<PlaceType> data){
        placeTypeUpdater.unregister(data);
    }
    public void unregisterRelation(ConnectionData<Relation> data){
        relationUpdater.unregister(data);
    }
    public void unregisterRelationType(ConnectionData<RelationType> data){
        relationTypeUpdater.unregister(data);
    }
}
