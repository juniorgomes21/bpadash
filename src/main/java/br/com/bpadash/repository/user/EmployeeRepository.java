package br.com.bpadash.repository.user;

import br.com.bpadash.model.user.Employee;
import br.com.bpadash.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByIdAndUser(Long id , User user);
}
