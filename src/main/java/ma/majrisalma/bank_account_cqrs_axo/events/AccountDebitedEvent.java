package ma.majrisalma.bank_account_cqrs_axo.events;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AccountDebitedEvent {
    private String accountId;
    private double amount;
    private String currency;
}