import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import tcs.familytree.core.person.Person;
import tcs.familytree.services.FamilyGraph;
import tcs.familytree.services.GraphProvider;
import tcs.familytree.services.RealGraphProvider;

import static org.junit.jupiter.api.Assertions.*;

public class GraphTest {
    static GraphProvider provider2 = new RealGraphProvider();
    static FamilyGraph graph;

    @BeforeAll
    public static void before() throws InterruptedException {
        provider2.updateGraph();
        Thread.sleep(2000);
        System.out.println("Working: ");
        graph = provider2.getGraphProperty().get();
        Thread.sleep(2000);
    }

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
