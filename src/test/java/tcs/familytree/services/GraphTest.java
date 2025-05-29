package tcs.familytree.services;

import org.jooq.meta.derby.sys.Sys;
import org.junit.jupiter.api.Test;
import tcs.familytree.core.person.Person;

import static org.junit.jupiter.api.Assertions.*;

public class GraphTest {
    TemporaryDataProvider2 provider = new TemporaryDataProvider2();
    {
        provider.updateGraph();
    }
    FamilyGraph graph = provider.getGraphProperty().get();

    @Test
    public void connection() {
        assertNotNull(graph);
        assertNotEquals(0, graph.getSize());
    }

    @Test
    public void widths() {
        for(Person p : graph.getAllPersons()) {
            System.out.println(p.getId() + ": " + graph.getWidthDown(p.getId()));
        }
    }
}
