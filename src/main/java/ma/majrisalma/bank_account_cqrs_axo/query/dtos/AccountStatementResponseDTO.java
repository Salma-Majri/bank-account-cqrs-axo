package ma.majrisalma.bank_account_cqrs_axo.query.dtos;

import ma.majrisalma.bank_account_cqrs_axo.query.entities.Account;
import ma.majrisalma.bank_account_cqrs_axo.query.entities.AccountOperation;

import java.util.List;

public record AccountStatementResponseDTO(
        Account account, List<AccountOperation> operations
) {
}