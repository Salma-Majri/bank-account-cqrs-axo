package ma.majrisalma.bank_account_cqrs_axo.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ma.majrisalma.bank_account_cqrs_axo.enums.AccountStatus;

@Getter @AllArgsConstructor
public class AccountCreatedEvent {
    private String accountId;
    private double initialBalance;
    private AccountStatus status;
    private String currency;
}