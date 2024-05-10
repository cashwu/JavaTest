package com.cashwu.javatest;

import com.cashwu.javatest.exceptions.AccountNotFoundException;
import com.cashwu.javatest.model.Account;
import com.cashwu.javatest.repositories.AccountRepository;
import com.cashwu.javatest.services.TransferService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

/**
 * @author cash.wu
 * @since 2024/05/10
 */
@ExtendWith(MockitoExtension.class)
public class TransferServiceWithAnnotationsUnitTests {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private TransferService transferService;

    @Test
    public void moneyTransferHappyFlow() {

        Account sender = new Account();
        sender.setId(1);
        sender.setAmount(new BigDecimal("100.00"));

        Account receiver = new Account();
        receiver.setId(2);
        receiver.setAmount(new BigDecimal("100.00"));

        given(accountRepository.findById(sender.getId())).willReturn(Optional.of(sender));

        given(accountRepository.findById(receiver.getId())).willReturn(Optional.of(receiver));

        transferService.transferMoney(sender.getId(), receiver.getId(), new BigDecimal("10.00"));

        verify(accountRepository).changeAmount(1, new BigDecimal("90.00"));

        verify(accountRepository).changeAmount(2, new BigDecimal("110.00"));
    }

    @Test
    public void moneyTransferReceiverAccountNotFoundFlow() {

        Account sender = new Account();
        sender.setId(1);
        sender.setAmount(new BigDecimal("100.00"));

        Account receiver = new Account();
        receiver.setId(2);
        receiver.setAmount(new BigDecimal("100.00"));

        given(accountRepository.findById(sender.getId())).willReturn(Optional.of(sender));

        given(accountRepository.findById(receiver.getId())).willReturn(Optional.empty());

        Assertions.assertThrows(AccountNotFoundException.class,
                                () -> transferService.transferMoney(sender.getId(),
                                                                    receiver.getId(),
                                                                    new BigDecimal("10.00")));

        verify(accountRepository, never()).changeAmount(anyInt(), any());
    }
}
