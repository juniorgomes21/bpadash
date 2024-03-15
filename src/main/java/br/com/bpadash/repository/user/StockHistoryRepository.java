package br.com.bpadash.repository.user;

import br.com.bpadash.model.user.Employee;
import br.com.bpadash.model.user.StockHistory;
import br.com.bpadash.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockHistoryRepository extends JpaRepository<StockHistory, Long> {
    List<StockHistory> findByEmployee(Employee employee);

    Page<StockHistory> findByUser(User user, Pageable pageable);
}
