package com.brokerage.strategy;
import com.brokerage.model.Asset;
import org.springframework.stereotype.Component;

@Component
public class TryAssetDepositStrategy implements AssetDepositStrategy {

    @Override
    public void deposit(Asset asset, Double amount) {
        asset.setSize(asset.getSize() + amount);
        asset.setUsableSize(asset.getUsableSize() + amount);
    }
}