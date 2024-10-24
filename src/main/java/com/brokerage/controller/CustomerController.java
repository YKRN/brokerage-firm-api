package com.brokerage.controller;

import com.brokerage.model.Asset;
import com.brokerage.service.AssetService;
import io.github.bucket4j.Bucket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class CustomerController {

    @Autowired
    private AssetService assetService;

    @Autowired
    private Bucket rateLimitBucket;

    @GetMapping("/{customerId}/assets")
    public ResponseEntity<List<Asset>> getAssets(@PathVariable Long customerId) {
        if (rateLimitBucket.tryConsume(1)) {
            return ResponseEntity.ok(assetService.getAssetsByCustomerId(customerId));
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }

    @PostMapping("/{customerId}/deposit")
    public ResponseEntity<String> depositMoney(@PathVariable Long customerId, @RequestParam double amount) {
        Asset tryAsset = assetService.getTRYAsset(customerId);
        tryAsset.setUsableSize(tryAsset.getUsableSize() + amount);
        assetService.saveAsset(tryAsset);
        return ResponseEntity.ok("Deposit successful");
    }

    @PostMapping("/{customerId}/withdraw")
    public ResponseEntity<String> withdrawMoney(@PathVariable Long customerId, @RequestParam double amount, @RequestParam String iban) {
        Asset tryAsset = assetService.getTRYAsset(customerId);
        if (tryAsset.getUsableSize() < amount) {
            return ResponseEntity.badRequest().body("Insufficient funds");
        }

        tryAsset.setUsableSize(tryAsset.getUsableSize() - amount);
        assetService.saveAsset(tryAsset);
        return ResponseEntity.ok("Withdrawal successful");
    }
}
