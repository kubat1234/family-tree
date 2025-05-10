package tcs.familytree.viewmodels;

import javafx.beans.property.ReadOnlyProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import tcs.familytree.core.person.Person;
import tcs.familytree.services.FamilyGraph;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HardcodedTest {
    @Nested
    public class BasicTests {
        SingleTreeViewModel model;
        ReadOnlyProperty<FamilyGraph> property;

        @BeforeEach
        public void before() {
            model = HardcodedSingleTreeViewModel.getModel();
            model.updateGraph();
            property = model.getGraphProperty();
        }

        @Test
        public void modelContainsPeople() {
            assertNotNull(property.getValue());
            FamilyGraph graph = property.getValue();
            assertNotNull(graph.getAllPersons());
            List<Person> persons = graph.getAllPersons().stream().toList();
            assertNotEquals(0, persons.size());
        }

        @Test
        public void anyoneHasChild() {
            assertNotNull(property.getValue());
            FamilyGraph graph = property.getValue();
            assertNotNull(graph.getAllPersons());
            List<Person> persons = graph.getAllPersons().stream().toList();
            assertNotEquals(0, persons.size());
            assertTrue(persons.stream().anyMatch(p -> p.getMother() != null || p.getFather() != null));
            assertTrue(persons.stream().anyMatch(p -> !graph.getChildren(p).isEmpty()));
        }
    }
}
