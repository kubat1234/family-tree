package tcs.familytree.views.painter;

import tcs.familytree.core.person.Person;
import tcs.familytree.services.FamilyGraph;
import tcs.familytree.views.plane.*;

import java.util.ArrayList;
import java.util.List;

public class ObsoletePainterBackEnd implements PainterBackEnd {

    FamilyGraph familyGraph;
    Person centralPerson;
    int max_depth = 20;
    int slot_size = 150;
    int depth_size = 100;
    public ObsoletePainterBackEnd(FamilyGraph familyGraph, Person person) {
        if(familyGraph == null || person == null) {
            throw new NullPointerException();
        }
        this.familyGraph = familyGraph;
        this.centralPerson = person;
        if(!familyGraph.getAllPersons().contains(person)) {
            throw new IllegalStateException();
        }
    }

    private int recursiveGraphDown(List<PersonOnPlane> personOnPlaneList, List<LineOnPlane> linesList, Person person, int x, int y, int depth){
        System.out.println(person.toString());
        if(depth > max_depth){
            return x;
        }
        int old_x = x;

        List<Integer> downId = new ArrayList<>();
        for(Person p: familyGraph.getChildren(person)){

            x = recursiveGraphDown(personOnPlaneList, linesList, p, x, y, depth + 1);
            downId.add(personOnPlaneList.size() - 1);
        }
        System.out.println(person.getName() + ": " + old_x + "; " + x);
        int my_slot = (1 + person.getPartners().size()) * slot_size;
        x = Math.max(x, old_x + my_slot);
        PersonOnPlane personOnPlane = new SimplePersonOnPlane((old_x + x - slot_size) / 2, y + depth * depth_size, person);
        for(Person p : person.getPartners()) {
            personOnPlaneList.add(new SimplePersonOnPlane((old_x + x + slot_size) / 2, y + depth * depth_size, p));
            linesList.add(new SimpleLineOnPlane(personOnPlane, personOnPlaneList.getLast()));
        }
        personOnPlaneList.add(personOnPlane);
        for(int i = 0; i < familyGraph.getChildren(person).size(); i++){
            linesList.add(new SimpleLineOnPlane(personOnPlaneList.getLast(), personOnPlaneList.get(downId.get(i))));
        }
        return x;
    }

    private int recursiveGraphUp(List<PersonOnPlane> list, List<LineOnPlane> parentList, Person person, int x, int y, int depth){
        System.out.println(person.toString());
        PersonOnPlane first = list.getLast();
        if(depth > max_depth){
            return x;
        }
        int old_x = x;

        boolean alone = true;
        List<Integer> downId = new ArrayList<>();
        for(Person p: familyGraph.getParents(person)){

            x = recursiveGraphUp(list, parentList, p, x, y, depth + 1);
            downId.add(list.size() - 1);
            alone = false;
        }
        if(alone){
            x += slot_size;
        }
        if(depth != 0){
            list.add(new SimplePersonOnPlane((old_x+x-slot_size)/2, y - depth * depth_size, person));
        }
        for(int i = 0; i < familyGraph.getParents(person).size(); i++){
            if(depth != 0){
                parentList.add(new SimpleLineOnPlane(list.getLast(), list.get(downId.get(i))));
            }else{
                parentList.add(new SimpleLineOnPlane(first, list.get(downId.get(i))));
            }
        }
        return x;
    }

    @Override
    public GraphOnPlane build() {
        List<PersonOnPlane> persons = new ArrayList<>();
        List<LineOnPlane> lines = new ArrayList<>();

        int x = 450, y = 250;
        //persons.add(new SimplePersonOnPlane(x, y, centralPerson));


        System.out.println("SimplePainterBackEnd - x");
        recursiveGraphDown(persons , lines, centralPerson, x, y, 0);
//        recursiveGraphUp(persons , lines, centralPerson, x, y, 0);

        return new SimpleGraphWithLineOnPlane(persons, lines);
    }
}
