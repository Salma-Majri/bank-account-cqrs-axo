package ma.majrisalma.bank_account_cqrs_axo.query.queries;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class WatchEventQuery {
    private String accountId;
}