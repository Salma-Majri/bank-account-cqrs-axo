package ma.majrisalma.bank_account_cqrs_axo.query.repository;

import ma.majrisalma.bank_account_cqrs_axo.query.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
}