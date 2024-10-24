package com.brokerage;

import com.brokerage.model.Asset;
import com.brokerage.model.Order;
import com.brokerage.repository.AssetRepository;
import com.brokerage.repository.OrderRepository;
import com.brokerage.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private AssetRepository assetRepository;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateOrder() {
        Order order = new Order();
        order.setCustomerId(1L);
        order.setPrice(10.0);
        order.setSize(5.0);

        Asset tryAsset = new Asset();
        tryAsset.setUsableSize(100.0);

        when(assetRepository.findByCustomerIdAndAssetName(1L, "TRY")).thenReturn(tryAsset);
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order createdOrder = orderService.createOrder(order);

        assertEquals("PENDING", createdOrder.getStatus());
        verify(assetRepository).save(tryAsset);
        verify(orderRepository).save(order);
    }

    @Test
    public void testCancelOrder() {
        Order order = new Order();
        order.setId(1L);
        order.setStatus("PENDING");
        order.setOrderSide("BUY");
        order.setCustomerId(1L);
        order.setPrice(10.0);
        order.setSize(5.0);

        Asset tryAsset = new Asset();
        tryAsset.setUsableSize(50.0);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(assetRepository.findByCustomerIdAndAssetName(1L, "TRY")).thenReturn(tryAsset);

        orderService.cancelOrder(1L);

        assertEquals("CANCELED", order.getStatus());
        verify(assetRepository).save(tryAsset);
        verify(orderRepository).save(order);
    }
}