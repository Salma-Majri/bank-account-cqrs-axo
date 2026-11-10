package ma.majrisalma.bank_account_cqrs_axo.commands.aggregates;

import lombok.extern.slf4j.Slf4j;
import ma.majrisalma.bank_account_cqrs_axo.commands.commands.AddAccountCommand;
import ma.majrisalma.bank_account_cqrs_axo.enums.AccountStatus;
import ma.majrisalma.bank_account_cqrs_axo.events.AccountCreatedEvent;
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
    }

    @EventSourcingHandler
    public void on(AccountCreatedEvent event){
        log.info("################### AccountCreatedEvent occured ###################");
        this.accountId = event.getAccountId();
        this.balance = event.getInitialBalance();
        this.status = event.getStatus();
    }
}