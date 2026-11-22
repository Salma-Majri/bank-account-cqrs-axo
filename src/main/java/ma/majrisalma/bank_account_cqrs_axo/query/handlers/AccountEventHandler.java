package ma.majrisalma.bank_account_cqrs_axo.query.handlers;

import lombok.extern.slf4j.Slf4j;
import ma.majrisalma.bank_account_cqrs_axo.events.AccountActivatedEvent;
import ma.majrisalma.bank_account_cqrs_axo.events.AccountCreatedEvent;
import ma.majrisalma.bank_account_cqrs_axo.events.AccountDebitedEvent;
import ma.majrisalma.bank_account_cqrs_axo.events.AccountStatusUpdatedEvent;
import ma.majrisalma.bank_account_cqrs_axo.query.entities.Account;
import ma.majrisalma.bank_account_cqrs_axo.query.entities.AccountOperation;
import ma.majrisalma.bank_account_cqrs_axo.query.entities.OperationType;
import ma.majrisalma.bank_account_cqrs_axo.query.repository.AccountRepository;
import ma.majrisalma.bank_account_cqrs_axo.query.repository.OperationRepository;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.EventMessage;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AccountEventHandler {
    private final AccountRepository accountRepository;
    private final OperationRepository operationRepository;

    public AccountEventHandler(AccountRepository accountRepository, OperationRepository operationRepository) {
        this.accountRepository = accountRepository;
        this.operationRepository = operationRepository;
    }

    @EventHandler
    public void on(AccountCreatedEvent event, EventMessage eventMessage){
        log.info("============== Query Side AccountCreatedEvent Recived ==============");
        Account account = Account.builder()
                .id(event.getAccountId())
                .balance(event.getInitialBalance())
                .status(event.getStatus())
                .currency(event.getCurrency())
                .createdAt(eventMessage.getTimestamp())
                .build();
        accountRepository.save(account);
    }

    @EventHandler
    public void on(AccountActivatedEvent event){
        log.info("============ Query Side AccountCreatedEvent Recieved ============");
        Account account = accountRepository.findById(event.getAccountId()).get();
        account.setStatus(event.getStatus());
        accountRepository.save(account);
    }
    @EventHandler
    public void on(AccountStatusUpdatedEvent event){
        log.info("============ Query Side AccountStatusUpdatedEvent Recieved ============");
        Account account = accountRepository.findById(event.getAccountId()).get();
        account.setStatus(event.getStatus());
        accountRepository.save(account);
    }


    @EventHandler
    public void on(AccountDebitedEvent event, EventMessage eventMessage){
        log.info("============ Query Side AccountDebitedEvent Recieved ============");
        Account account = accountRepository.findById(event.getAccountId()).get();
        AccountOperation accountOperation = AccountOperation.builder()
                .amount(event.getAmount())
                .date(eventMessage.getTimestamp())
                .type(OperationType.DEBIT)
                .currency(event.getCurrency())
                .account(account)
                .build();
        operationRepository.save(accountOperation);
        account.setBalance(account.getBalance() - accountOperation.getAmount());
        accountRepository.save(account);
    }


}