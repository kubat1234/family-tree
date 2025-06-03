import org.jooq.meta.derby.sys.Sys;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import tcs.familytree.core.person.Person;
import tcs.familytree.core.relation.Relation;
import tcs.familytree.services.FamilyGraph;
import tcs.familytree.services.GraphProvider;
import tcs.familytree.services.RealGraphProvider;
import tcs.familytree.services.database.DatabaseConnection;

import static org.junit.jupiter.api.Assertions.*;

public class GraphTest {
    static GraphProvider provider2 = new RealGraphProvider();
    static FamilyGraph graph;

    @BeforeAll
    public static void before() throws InterruptedException {
        provider2.updateGraph();
        System.out.println("Working: ");
        graph = provider2.getGraphProperty().get();
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

    @Test
    public void Relations() {
        DatabaseConnection connection = ((RealGraphProvider)provider2).getDatabase("admin");
        for(Relation relation : connection.getAllRelations()) {
            System.out.println(relation.getId() + ". " + relation.getFirstPerson().getName() + " + " + relation.getSecondPerson().getName());
        }
    }
}
