package com.brokerage.strategy;

import com.brokerage.model.Asset;

public interface AssetDepositStrategy {
    void deposit(Asset asset, Double amount);
}