package aldentebackend.service;

import aldentebackend.model.Salary;

import java.util.Collection;

public interface SalaryService extends JPAService<Salary> {

    Salary getUserCurrentSalary(Long userId);
    Salary getUserSalaryAt(Long userId, Long epoch);
    Collection<Salary> getAllCurrentSalaries();
    Collection<Salary> getAllSalariesAt(Long epoch);
}
