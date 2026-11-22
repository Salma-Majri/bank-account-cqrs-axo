package ma.majrisalma.bank_account_cqrs_axo.dtos;

import ma.majrisalma.bank_account_cqrs_axo.enums.AccountStatus;

public record UpdateAccountStatusRequestDTO(
        String accountId,
        AccountStatus status
) {
}