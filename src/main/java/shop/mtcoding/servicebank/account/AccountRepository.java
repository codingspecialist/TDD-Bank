package shop.mtcoding.servicebank.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("select ac from Account ac where ac.number = :number")
    Optional<Account> findByNumber(@Param("number") Integer number);

    @Query("select ac from Account ac where ac.user.id = :userId")
    List<Account> findByUserId(Long userId);
}
