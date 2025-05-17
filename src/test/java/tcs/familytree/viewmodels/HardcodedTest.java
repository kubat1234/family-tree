package tcs.familytree.viewmodels;

import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import tcs.familytree.core.person.Person;
import tcs.familytree.services.FamilyGraph;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

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

    @Nested
    public class ModificationTests {
        SingleTreeViewModel model;
        ReadOnlyProperty<FamilyGraph> property;
        FamilyGraph graph;

        @BeforeEach
        public void before() {
            model = HardcodedSingleTreeViewModel.getModel();
            model.updateGraph();
            property = model.getGraphProperty();
            graph = property.getValue();
        }

        @Test
        public void StaticDataWorks() {
            assertNotEquals(0, graph.getAllPersons().size());
            assertNotNull(graph.getAllPersons().iterator().next());
            assertNotNull(graph.getAllPersons().iterator().next().getName());
        }

        @Test
        public void BasicModifications() {
            Person person = graph.getAllPersons().iterator().next();
            final String newName = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                    "Morbi sed lectus lacinia, sagittis libero ut, lobortis dui. " +
                    "Nam quis est sed lectus eleifend pellentesque et.";
            person.setName(newName);
            assertEquals(newName, person.getName());
            person = graph.getAllPersons().iterator().next();
            assertEquals(newName, person.getName());
        }

        @Test
        public void InDatabaseModification() {
            Person person = graph.getAllPersons().iterator().next();
            final String newName = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                    "Morbi sed lectus lacinia, sagittis libero ut, lobortis dui. " +
                    "Nam quis est sed lectus eleifend pellentesque et.";
            person.setName(newName);
            assertEquals(newName, person.getName());
            model.updateGraph();
            person = model.getGraphProperty().get().getAllPersons().iterator().next();
            assertEquals(newName, person.getName());
        }

        @Test
        public void BasicListeners() throws InterruptedException {
            final AtomicInteger counter = new AtomicInteger(0);
            ChangeListener<FamilyGraph> listener = new ChangeListener<FamilyGraph>() {
                @Override
                public void changed(ObservableValue<? extends FamilyGraph> observableValue, FamilyGraph graph, FamilyGraph t1) {
                    counter.incrementAndGet();
                }
            };
            model.getGraphProperty().addListener(listener);
            assertEquals(0, counter.get());
            model.updateGraph();
            Thread.sleep(100);
            assertEquals(1, counter.get());
        }

        @Test
        public void PersonChangedListeners() throws InterruptedException {
            Person person = graph.getAllPersons().iterator().next();
            final String newName = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                    "Morbi sed lectus lacinia, sagittis libero ut, lobortis dui. " +
                    "Nam quis est sed lectus eleifend pellentesque et.";
            final AtomicInteger counter = new AtomicInteger(0);
            ChangeListener<FamilyGraph> listener = new ChangeListener<FamilyGraph>() {
                @Override
                public void changed(ObservableValue<? extends FamilyGraph> observableValue, FamilyGraph graph, FamilyGraph t1) {
                    counter.incrementAndGet();
                }
            };
            model.getGraphProperty().addListener(listener);
            assertEquals(0, counter.get());
            person.setName(newName);

            Thread.sleep(100);
            assertEquals(1, counter.get());
        }

        @Test
        public void PersonChangedListenersWorkaround() throws InterruptedException {
            Person person = graph.getAllPersons().iterator().next();
            final String newName = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                    "Morbi sed lectus lacinia, sagittis libero ut, lobortis dui. " +
                    "Nam quis est sed lectus eleifend pellentesque et.";
            final AtomicInteger counter = new AtomicInteger(0);
            ChangeListener<FamilyGraph> listener = new ChangeListener<FamilyGraph>() {
                @Override
                public void changed(ObservableValue<? extends FamilyGraph> observableValue, FamilyGraph graph, FamilyGraph t1) {
                    counter.incrementAndGet();
                }
            };
            model.getGraphProperty().addListener(listener);
            assertEquals(0, counter.get());

            person.setName(newName);
            model.updateGraph();

            Thread.sleep(100);
            assertEquals(1, counter.get());
        }
    }
}
