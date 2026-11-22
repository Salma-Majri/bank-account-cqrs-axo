package ma.majrisalma.bank_account_cqrs_axo.query.handlers;

import ma.majrisalma.bank_account_cqrs_axo.query.entities.Account;
import ma.majrisalma.bank_account_cqrs_axo.query.queries.GetAllAccountsQuery;
import ma.majrisalma.bank_account_cqrs_axo.query.repository.AccountRepository;
import ma.majrisalma.bank_account_cqrs_axo.query.repository.OperationRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountQueryHandler {
    private AccountRepository accountRepository;
    private OperationRepository operationRepository;

    public AccountQueryHandler(AccountRepository accountRepository, OperationRepository operationRepository) {
        this.accountRepository = accountRepository;
        this.operationRepository = operationRepository;
    }

    @QueryHandler
    public List<Account> on(GetAllAccountsQuery query){
        return accountRepository.findAll();
    }
}