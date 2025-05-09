package tcs.familytree.views;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DummyViewTest {

    @Nested
    class BasicTests {
        @Test
        public void testGetDummyDataDoesNotThrow() {
            DummyView dummyView = new DummyView();
            dummyView.getDummyData();
        }

        @Test
        public void testGetDummyDataReturnsNotNull() {
            assertNotNull(new DummyView().getDummyData());
        }
    }

    @Nested
    class SpecificTests {
        @Test
        public void testGetDummyDataIsNotEmpty() {
            assertNotEquals(0, new DummyView().getDummyData().length());
        }
        @Test
        public void testGetDummyDataReturnsCorrectValue() {
            assertEquals("Hello from Core! and Services! and ViewModels! and Views!", new DummyView().getDummyData());
        }
    }
}
