package com.brokerage.service;

import com.brokerage.model.Asset;
import com.brokerage.repository.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AssetService {

    @Autowired
    private AssetRepository assetRepository;

    @Transactional
    public void depositMoney(Long customerId, Double amount) {

        Asset asset = assetRepository.findByCustomerIdAndAssetName(customerId, "TRY");
        asset.setSize(asset.getSize() + amount);
        assetRepository.save(asset);
    }

    @Transactional
    public void withdrawMoney(Long customerId, Double amount) {

        Asset asset = assetRepository.findByCustomerIdAndAssetName(customerId, "TRY");
        asset.setSize(asset.getSize() - amount);
        assetRepository.save(asset);
    }

    @Transactional(readOnly = true)
    public List<Asset> getAssetsByCustomerId(Long customerId) {
        return assetRepository.findByCustomerId(customerId);
    }
}
