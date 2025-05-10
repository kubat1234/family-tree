package tcs.familytree.services;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import tcs.familytree.services.database.DatabaseConnection;
import tcs.familytree.services.database.DatabaseConnectionWithoutDatabase;
import tcs.familytree.services.database.DatabaseFactory;
import tcs.familytree.services.database.DatabaseFactoryPersonOnlyTest1;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseConnectionTest {

    @Nested
    class DatabaseFactoryTests{
        @Test
        public void DatabaseFactoryPersonOnlyTest(){
            DatabaseFactory df = new DatabaseFactoryPersonOnlyTest1();
            assertNotNull(df);
            assertNotNull(df.getPerson());
            assertEquals("Alice", df.getPerson().getName());
            assertEquals(3, df.getPerson().getId());
            try{
                df.getPerson();
                fail("method getPerson don't throw an exception");
            }catch(IllegalArgumentException ignored){
            }
        }

        @Test
        public void DatabaseBuildTest(){
            DatabaseFactory df = new DatabaseFactoryPersonOnlyTest1();
            assertNotNull(df.build());
        }

        @Test
        public void DatabaseBuildTestWithType(){
            DatabaseFactory df = new DatabaseFactoryPersonOnlyTest1();
            assertNotNull(df.changeType(DatabaseConnectionWithoutDatabase.class).build());
        }

        @Test
        public void DatabaseBuildTestWithIncorrectType(){
            DatabaseFactory df = new DatabaseFactoryPersonOnlyTest1();
            try{
                df.changeType(DatabaseConnection.class).build();
                fail();
            }catch (Exception ex){
                assertEquals("NOT IMPLEMENTED CLASS FACTORY: " + DatabaseConnection.class, ex.getMessage());
            }
        }
    }
}
