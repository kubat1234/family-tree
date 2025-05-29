package tcs.familytree.core;

import tcs.familytree.core.date.Date;
import tcs.familytree.core.person.Person;
import tcs.familytree.core.place.Place;
import tcs.familytree.core.relation.Relation;
import tcs.familytree.core.relationtype.RelationType;

public class Updater {
    SingleUpdater<Person> personUpdater = new SingleUpdater<>();
    SingleUpdater<Date> dateUpdater = new SingleUpdater<>();
    SingleUpdater<Place> placeUpdater = new SingleUpdater<>();
    SingleUpdater<Relation> relationUpdater = new SingleUpdater<>();
    SingleUpdater<RelationType> relationTypeUpdater = new SingleUpdater<>();

    public void update(Person person){
        personUpdater.update(person);
    }
    public void update(Date date){
        dateUpdater.update(date);
    }
    public void update(Place place){
        placeUpdater.update(place);
    }
    public void update(Relation relation){
        relationUpdater.update(relation);
    }
    public void update(RelationType relationType){
        relationTypeUpdater.update(relationType);
    }

    public void registerPerson(ConnectionData<Person> data){
        personUpdater.register(data);
    }
    public void registerDate(ConnectionData<Date> data){
        dateUpdater.register(data);
    }
    public void registerPlace(ConnectionData<Place> data){
        placeUpdater.register(data);
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
    public void unregisterDate(ConnectionData<Date> data){
        dateUpdater.unregister(data);
    }
    public void unregisterPlace(ConnectionData<Place> data){
        placeUpdater.unregister(data);
    }
    public void unregisterRelation(ConnectionData<Relation> data){
        relationUpdater.unregister(data);
    }
    public void unregisterRelationType(ConnectionData<RelationType> data){
        relationTypeUpdater.unregister(data);
    }
}
