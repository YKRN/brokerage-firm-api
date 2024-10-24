package com.brokerage.indicator;

import com.brokerage.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class OrderCountHealthIndicator implements HealthIndicator {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Health health() {
        long orderCount = orderRepository.count();

        if (orderCount < 10) {
            return Health.down()
                    .withDetail("Order Count", orderCount)
                    .withDetail("Message", "Orders are below the threshold!")
                    .build();
        }

        return Health.up()
                .withDetail("Order Count", orderCount)
                .withDetail("Message", "Order count is within a healthy range.")
                .build();
    }
}
