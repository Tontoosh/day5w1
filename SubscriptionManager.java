// SubscriptionManager.java

import java.util.*;

abstract class Subscription {
    String id, customer, phone, nextDate, recurring, plan, status;

    public Subscription(String id, String customer, String phone, String nextDate, String recurring, String plan, String status) {
        this.id = id;
        this.customer = customer;
        this.phone = phone;
        this.nextDate = nextDate;
        this.recurring = recurring;
        this.plan = plan;
        this.status = status;
    }

    public abstract String getType();

    public Object[] toRow() {
        return new Object[]{id, customer, phone, nextDate, recurring, plan, status, getType()};
    }
}

class ProductSubscription extends Subscription {
    public ProductSubscription(String id, String customer, String phone, String nextDate, String recurring, String plan, String status) {
        super(id, customer, phone, nextDate, recurring, plan, status);
    }

    @Override
    public String getType() {
        return "Product";
    }
}

class ServiceSubscription extends Subscription {
    public ServiceSubscription(String id, String customer, String phone, String nextDate, String recurring, String plan, String status) {
        super(id, customer, phone, nextDate, recurring, plan, status);
    }

    @Override
    public String getType() {
        return "Service";
    }
}

public class SubscriptionManager {
    private List<Subscription> subscriptions = new ArrayList<>();

    public SubscriptionManager(List<Subscription> initialData) {
        subscriptions.addAll(initialData);
    }

    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public void addSubscription(String customer, String phone, String nextDate, String recurring, String plan, String status, String type) {
        String id = generateNewId();
        Subscription sub = type.equalsIgnoreCase("Product") ?
                new ProductSubscription(id, customer, phone, nextDate, recurring, plan, status) :
                new ServiceSubscription(id, customer, phone, nextDate, recurring, plan, status);
        subscriptions.add(sub);
    }

    public String generateNewId() {
        return "S" + String.format("%04d", subscriptions.size() + 1);
    }

    public Subscription findById(String id) {
        return subscriptions.stream().filter(s -> s.id.equals(id)).findFirst().orElse(null);
    }

    public void editSubscription(String id, String customer, String phone, String nextDate, String recurring, String plan, String status, String type) {
        Subscription old = findById(id);
        if (old != null) {
            subscriptions.remove(old);
            Subscription updated = type.equalsIgnoreCase("Product") ?
                    new ProductSubscription(id, customer, phone, nextDate, recurring, plan, status) :
                    new ServiceSubscription(id, customer, phone, nextDate, recurring, plan, status);
            subscriptions.add(updated);
        }
    }

    public void removeSubscription(String id) {
        subscriptions.removeIf(s -> s.id.equals(id));
    }

    public List<Subscription> filter(String keyword) {
        keyword = keyword.toLowerCase();
        List<Subscription> result = new ArrayList<>();
        for (Subscription s : subscriptions) {
            if (s.id.toLowerCase().contains(keyword) ||
                s.customer.toLowerCase().contains(keyword) ||
                s.phone.contains(keyword) ||
                s.plan.toLowerCase().contains(keyword) ||
                s.status.toLowerCase().contains(keyword)) {
                result.add(s);
            }
        }
        return result;
    }
}
