package com.brokerage.service;

import com.brokerage.model.Asset;
import com.brokerage.model.Order;
import com.brokerage.repository.AssetRepository;
import com.brokerage.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private AssetRepository assetRepository;

    @Transactional
    public Order createOrder(Long customerId, String assetName, String orderSide, Double size, Double price) {

        if ("BUY".equalsIgnoreCase(orderSide)) {
            Asset tryAsset = assetRepository.findByCustomerIdAndAssetName(customerId, "TRY");
            double requiredAmount = size * price;

            if (tryAsset == null || tryAsset.getUsableSize() < requiredAmount) {
                throw new IllegalStateException("Not enough TRY asset to place the BUY order.");
            }


            tryAsset.setUsableSize(tryAsset.getUsableSize() - requiredAmount);
            assetRepository.save(tryAsset);
        } else if ("SELL".equalsIgnoreCase(orderSide)) {
            Asset sellAsset = assetRepository.findByCustomerIdAndAssetName(customerId, assetName);

            if (sellAsset == null || sellAsset.getUsableSize() < size) {
                throw new IllegalStateException("Not enough asset to place the SELL order.");
            }


            sellAsset.setUsableSize(sellAsset.getUsableSize() - size);
            assetRepository.save(sellAsset);
        }


        Order order = new Order();
        order.setCustomerId(customerId);
        order.setAssetName(assetName);
        order.setOrderSide(orderSide);
        order.setSize(size);
        order.setPrice(price);
        order.setStatus("PENDING");
        order.setCreateDate(LocalDateTime.now());

        return orderRepository.save(order);
    }

    @Transactional(readOnly = true)
    public List<Order> getOrdersByCustomerIdAndDateRange(Long customerId, LocalDateTime startDate, LocalDateTime endDate) {

        return orderRepository.findByCustomerIdAndCreateDateBetween(customerId, startDate, endDate);
    }

    @Transactional(readOnly = true)
    public List<Order> getAllOrders() {
        // Tüm emirleri listeleme
        return orderRepository.findAll();
    }

    @Transactional
    public void cancelOrder(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + orderId));

        if (!"PENDING".equals(order.getStatus())) {
            throw new IllegalStateException("Only PENDING orders can be canceled.");
        }


        if ("BUY".equalsIgnoreCase(order.getOrderSide())) {
            Asset tryAsset = assetRepository.findByCustomerIdAndAssetName(order.getCustomerId(), "TRY");
            double refundAmount = order.getSize() * order.getPrice();
            tryAsset.setUsableSize(tryAsset.getUsableSize() + refundAmount);
            assetRepository.save(tryAsset);
        } else if ("SELL".equalsIgnoreCase(order.getOrderSide())) {
            Asset sellAsset = assetRepository.findByCustomerIdAndAssetName(order.getCustomerId(), order.getAssetName());
            sellAsset.setUsableSize(sellAsset.getUsableSize() + order.getSize());
            assetRepository.save(sellAsset);
        }

        order.setStatus("CANCELED");
        orderRepository.save(order);
    }

    @Transactional
    public void matchPendingOrders() {

        List<Order> pendingOrders = orderRepository.findByStatus("PENDING");

        for (Order order : pendingOrders) {

            order.setStatus("MATCHED");
            orderRepository.save(order);
        }
    }
}
