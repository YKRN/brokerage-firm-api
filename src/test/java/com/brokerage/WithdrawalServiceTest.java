package com.brokerage;

import com.brokerage.model.Asset;
import com.brokerage.model.Withdrawal;
import com.brokerage.repository.AssetRepository;
import com.brokerage.repository.WithdrawalRepository;
import com.brokerage.service.WithdrawalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class WithdrawalServiceTest {

    @Mock
    private AssetRepository assetRepository;

    @Mock
    private WithdrawalRepository withdrawalRepository;

    @InjectMocks
    private WithdrawalService withdrawalService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testWithdrawMoney_Success() {
        // Mock varlık
        Asset mockAsset = new Asset();
        mockAsset.setCustomerId(1L);
        mockAsset.setAssetName("TRY");
        mockAsset.setUsableSize(1000.0);

        // Mock işlemi
        when(assetRepository.findByCustomerIdAndAssetName(1L, "TRY")).thenReturn(mockAsset);

        // Çekim işlemini test et
        Withdrawal withdrawal = withdrawalService.withdrawMoney(1L, "TR123456789012345678901234", 100.0);

        // Sonuçları kontrol et
        assertNotNull(withdrawal);
        assertEquals(1L, withdrawal.getCustomerId());
        assertEquals("TR123456789012345678901234", withdrawal.getIban());
        assertEquals(100.0, withdrawal.getAmount());

        // Mock doğrulama
        verify(assetRepository, times(1)).save(mockAsset);
        verify(withdrawalRepository, times(1)).save(withdrawal);
    }

    @Test
    public void testWithdrawMoney_InsufficientFunds() {
        // Mock varlık
        Asset mockAsset = new Asset();
        mockAsset.setCustomerId(1L);
        mockAsset.setAssetName("TRY");
        mockAsset.setUsableSize(50.0); // Yeterli TRY yok

        // Mock işlemi
        when(assetRepository.findByCustomerIdAndAssetName(1L, "TRY")).thenReturn(mockAsset);

        // Yetersiz fon senaryosu için exception kontrolü
        assertThrows(IllegalStateException.class, () -> {
            withdrawalService.withdrawMoney(1L, "TR123456789012345678901234", 100.0);
        });

        // Mock doğrulama
        verify(assetRepository, never()).save(mockAsset);
        verify(withdrawalRepository, never()).save(any(Withdrawal.class));
    }
}
