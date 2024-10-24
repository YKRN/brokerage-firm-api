package com.brokerage.controller;

import com.brokerage.model.Withdrawal;
import com.brokerage.service.WithdrawalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private WithdrawalService withdrawalService;
@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping("/withdraw")
    public Withdrawal withdrawMoney(
            @RequestParam Long customerId,
            @RequestParam String iban,
            @RequestParam Double amount) {

        return withdrawalService.withdrawMoney(customerId, iban, amount);
    }
}
