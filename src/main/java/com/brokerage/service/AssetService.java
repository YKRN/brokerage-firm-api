package com.brokerage.service;

import com.brokerage.model.Asset;
import com.brokerage.repository.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetService {

    @Autowired
    private AssetRepository assetRepository;

    public List<Asset> getAssetsByCustomerId(Long customerId) {
        return assetRepository.findByCustomerId(customerId);
    }

    public Asset getTRYAsset(Long customerId) {
        return assetRepository.findByCustomerIdAndAssetName(customerId, "TRY");
    }

    public void saveAsset(Asset asset) {
        assetRepository.save(asset);
    }
}
