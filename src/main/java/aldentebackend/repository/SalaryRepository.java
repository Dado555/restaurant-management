package aldentebackend.repository;

import aldentebackend.model.Salary;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface SalaryRepository extends JpaRepository<Salary, Long> {

    Salary findFirstByUserIdAndDateLessThanEqualOrderByDateDesc(Long userId, Long epoch);

    @Query(value = "SELECT DISTINCT ON (salary.user_id) *  FROM salary WHERE salary.date <= ?1 ORDER BY user_id ASC, salary.date DESC;", nativeQuery = true)
    Collection<Salary> getUsersSalariesAt(Long epoch);
}
