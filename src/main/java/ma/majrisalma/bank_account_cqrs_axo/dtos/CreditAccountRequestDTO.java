package ma.majrisalma.bank_account_cqrs_axo.dtos;

public record CreditAccountRequestDTO(
        String accountId,
        double amount,
        String currency
) {}