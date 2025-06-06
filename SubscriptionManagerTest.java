import org.junit.jupiter.api.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SubscriptionManagerTest {

    SubscriptionManager manager;

    @BeforeEach
    public void setup() {
        manager = new SubscriptionManager(List.of(
            new ProductSubscription("S0001", "Alice", "99001122", "2025-06-10", "$10", "Monthly", "In Progress")
        ));
    }

    @Test
    public void testAddSubscription() {
        manager.addSubscription("Bob", "99112233", "2025-07-01", "$20", "Yearly", "Quotation", "Service");
        List<Subscription> list = manager.getSubscriptions();
        assertEquals(2, list.size());
        assertEquals("Bob", list.get(1).customer);
        assertEquals("Service", list.get(1).getType());
    }

    @Test
    public void testRemoveSubscription() {
        manager.removeSubscription("S0001");
        List<Subscription> list = manager.getSubscriptions();
        assertTrue(list.isEmpty());
    }

    @Test
    public void testFindById() {
        Subscription sub = manager.findById("S0001");
        assertNotNull(sub);
        assertEquals("Alice", sub.customer);
    }

    @Test
    public void testEditSubscription() {
        manager.editSubscription("S0001", "Alice Updated", "99009900", "2025-08-01", "$15", "Monthly", "Closed", "Product");
        Subscription sub = manager.findById("S0001");
        assertNotNull(sub);
        assertEquals("Alice Updated", sub.customer);
        assertEquals("Closed", sub.status);
    }

    @Test
    public void testFilter() {
        manager.addSubscription("Charlie", "99000011", "2025-09-01", "$25", "Monthly", "In Progress", "Service");
        List<Subscription> result = manager.filter("char");
        assertEquals(1, result.size());
        assertEquals("Charlie", result.get(0).customer);
    }
}
