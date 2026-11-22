package ma.majrisalma.bank_account_cqrs_axo.commands.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ma.majrisalma.bank_account_cqrs_axo.enums.AccountStatus;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@AllArgsConstructor
public class UpdateAccountStatusCommand {
    @TargetAggregateIdentifier
    private String id;
    private AccountStatus status;
}
