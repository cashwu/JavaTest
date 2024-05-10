package com.cashwu.javatest;

import com.cashwu.javatest.model.Account;
import com.cashwu.javatest.repositories.AccountRepository;
import com.cashwu.javatest.services.TransferService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * @author cash.wu
 * @since 2024/05/10
 */
public class TransferServiceUnitTests {

    @Test
    public void moneyTransferHappyFlow() {

        AccountRepository accountRepository = mock(AccountRepository.class);

        TransferService transferService = new TransferService(accountRepository);

        Account sender = new Account();
        sender.setId(1);
        sender.setAmount(new BigDecimal("100.00"));

        Account reciever = new Account();
        reciever.setId(2);
        reciever.setAmount(new BigDecimal("100.00"));

        given(accountRepository.findById(sender.getId()))
                .willReturn(Optional.of(sender));

        given(accountRepository.findById(reciever.getId()))
                .willReturn(Optional.of(reciever));

        transferService.transferMoney(sender.getId(), reciever.getId(), new BigDecimal(10));

        verify(accountRepository).changeAmount(1, new BigDecimal("90.00"));

        verify(accountRepository).changeAmount(2, new BigDecimal("110.00"));
    }
}
