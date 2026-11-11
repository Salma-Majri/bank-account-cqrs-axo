package ma.majrisalma.bank_account_cqrs_axo.commands.aggregates;

import lombok.extern.slf4j.Slf4j;
import ma.majrisalma.bank_account_cqrs_axo.commands.commands.AddAccountCommand;
import ma.majrisalma.bank_account_cqrs_axo.commands.commands.CreditAccountCommand;
import ma.majrisalma.bank_account_cqrs_axo.enums.AccountStatus;
import ma.majrisalma.bank_account_cqrs_axo.events.AccountActivatedEvent;
import ma.majrisalma.bank_account_cqrs_axo.events.AccountCreatedEvent;
import ma.majrisalma.bank_account_cqrs_axo.events.AccountCreditedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@Slf4j
public class AccountAggregate {
    @AggregateIdentifier
    private String accountId;
    private double balance;
    private AccountStatus status;

    // Pour Axon
    public AccountAggregate() {}

    @CommandHandler
    public AccountAggregate(AddAccountCommand command){
        log.info("################### AddAccountCommand Received ###################");
        if (command.getInitialBalance() <= 0)
            throw new IllegalArgumentException("Balance must be greater than zero");

        AggregateLifecycle.apply(new AccountCreatedEvent(
                command.getId(),
                command.getInitialBalance(),
                AccountStatus.CREATED,
                command.getCurrency()
        ));
        AggregateLifecycle.apply(new AccountActivatedEvent(
                command.getId(),
                AccountStatus.ACTIVATED
        ));
    }

    @EventSourcingHandler
    public void on(AccountCreatedEvent event){
        log.info("################### AccountCreatedEvent occured ###################");
        this.accountId = event.getAccountId();
        this.balance = event.getInitialBalance();
        this.status = event.getStatus();
    }


    @CommandHandler
    public void handle(CreditAccountCommand command){
        log.info("############# CreditAccountCommand Received ###############");
        if(!status.equals(AccountStatus.ACTIVATED))
            throw new RuntimeException("The account " + command.getId() + " is not activated. Current status: " + status);
        if (command.getAmount() <= 0)
            throw new IllegalArgumentException("Amount must be positive");
        AggregateLifecycle.apply(new AccountCreditedEvent(
                command.getId(),
                command.getAmount(),
                command.getCurrency()
        ));
    }


    @EventSourcingHandler
    public void on(AccountCreditedEvent event){
        log.info("################ AccountCreditedEvent occured ###############");
        this.balance = this.balance + event.getAmount();
    }
}