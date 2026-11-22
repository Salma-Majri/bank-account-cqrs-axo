package ma.majrisalma.bank_account_cqrs_axo.query.repository;

import ma.majrisalma.bank_account_cqrs_axo.query.entities.AccountOperation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OperationRepository extends JpaRepository<AccountOperation, Long> {
    List<AccountOperation> findByAccountId(String id);
}