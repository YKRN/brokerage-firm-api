package com.brokerage;

import com.brokerage.model.Asset;
import com.brokerage.strategy.TryAssetDepositStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TryAssetDepositStrategyTest {

    private TryAssetDepositStrategy tryAssetDepositStrategy;

    @BeforeEach
    public void setUp() {
        tryAssetDepositStrategy = new TryAssetDepositStrategy();
    }

    @Test
    public void testDeposit() {
        Asset asset = new Asset();
        asset.setSize(100.0);
        asset.setUsableSize(50.0);

        tryAssetDepositStrategy.deposit(asset, 25.0);

        assertEquals(125.0, asset.getSize());
        assertEquals(75.0, asset.getUsableSize());
    }
}