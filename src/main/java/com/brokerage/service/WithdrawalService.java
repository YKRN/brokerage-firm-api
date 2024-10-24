package com.brokerage.service;

import com.brokerage.model.Asset;
import com.brokerage.model.Withdrawal;
import com.brokerage.repository.AssetRepository;
import com.brokerage.repository.WithdrawalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WithdrawalService {

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private WithdrawalRepository withdrawalRepository;

    @Transactional
    public Withdrawal withdrawMoney(Long customerId, String iban, Double amount) {
        // Müşteri kimliğine göre TRY varlığını kontrol et
        Asset tryAsset = assetRepository.findByCustomerIdAndAssetName(customerId, "TRY");

        if (tryAsset == null || tryAsset.getUsableSize() < amount) {
            throw new IllegalStateException("Not enough TRY asset for withdrawal.");
        }

        // TRY varlık boyutunu güncelle
        tryAsset.setUsableSize(tryAsset.getUsableSize() - amount);
        assetRepository.save(tryAsset);

        // Withdrawal tablosuna çekim işlemini kaydet
        Withdrawal withdrawal = new Withdrawal();
        withdrawal.setCustomerId(customerId);
        withdrawal.setIban(iban);
        withdrawal.setAmount(amount);
        return withdrawalRepository.save(withdrawal);
    }
}
