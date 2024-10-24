package com.brokerage.service;

import com.brokerage.annotation.LocalizedMessage;
import com.brokerage.model.Order;
import com.brokerage.model.Asset;
import com.brokerage.repository.OrderRepository;
import com.brokerage.repository.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private AssetRepository assetRepository;

    public Order createOrder(Order order) {

        Asset tryAsset = assetRepository.findByCustomerIdAndAssetName(order.getCustomerId(), "TRY");
        double requiredFunds = order.getPrice() * order.getSize();
        if (tryAsset.getUsableSize() < requiredFunds) {
            throw new RuntimeException("Insufficient funds in TRY");
        }


        tryAsset.setUsableSize(tryAsset.getUsableSize() - requiredFunds);
        assetRepository.save(tryAsset);

        order.setStatus("PENDING");
        return orderRepository.save(order);
    }

    @LocalizedMessage(key = "insufficient_funds")
    public String checkOrderFunds(Double availableFunds, Double requiredFunds) {
        if (availableFunds < requiredFunds) {
            return "Insufficient funds";
        }
        return "Sufficient funds";
    }

    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        if ("PENDING".equals(order.getStatus())) {

            if ("BUY".equals(order.getOrderSide())) {
                Asset tryAsset = assetRepository.findByCustomerIdAndAssetName(order.getCustomerId(), "TRY");
                double refundedAmount = order.getPrice() * order.getSize();
                tryAsset.setUsableSize(tryAsset.getUsableSize() + refundedAmount);
                assetRepository.save(tryAsset);
            }
            order.setStatus("CANCELED");
            orderRepository.save(order);
        }
    }

    public void matchPendingOrders() {
        List<Order> pendingOrders = orderRepository.findByStatus("PENDING");

        pendingOrders.stream().forEach(order -> {
            if (order.getOrderSide().equals("BUY")) {

                Asset asset = assetRepository.findByCustomerIdAndAssetName(order.getCustomerId(), order.getAssetName());
                asset.setSize(asset.getSize() + order.getSize());
                asset.setUsableSize(asset.getUsableSize() + order.getSize());
                assetRepository.save(asset);
            } else if (order.getOrderSide().equals("SELL")) {

                Asset tryAsset = assetRepository.findByCustomerIdAndAssetName(order.getCustomerId(), "TRY");
                double sellAmount = order.getPrice() * order.getSize();
                tryAsset.setUsableSize(tryAsset.getUsableSize() + sellAmount);
                assetRepository.save(tryAsset);
            }
            order.setStatus("MATCHED");
            orderRepository.save(order);
        });
    }

    public List<Order> getOrdersByCustomerId(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    public List<Order> getOrdersByCustomerIdAndDateRange(Long customerId, LocalDateTime startDate, LocalDateTime endDate) {
        return orderRepository.findByCustomerId(customerId).stream()
                .filter(order -> order.getCreateDate().isAfter(startDate) && order.getCreateDate().isBefore(endDate))
                .collect(Collectors.toList());
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
