package aldentebackend.service.impl;

import aldentebackend.exception.BadRequestException;
import aldentebackend.model.Salary;
import aldentebackend.repository.SalaryRepository;
import aldentebackend.service.SalaryService;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collection;

@AllArgsConstructor
@Service
public class SalaryServiceImpl extends JPAServiceImpl<Salary> implements SalaryService {

    SalaryRepository salaryRepository;

    @Override
    protected JpaRepository<Salary, Long> getEntityRepository() {
        return salaryRepository;
    }

    @Override
    public Salary getUserCurrentSalary(Long userId) {
        Long epoch = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        Salary salary = salaryRepository.findFirstByUserIdAndDateLessThanEqualOrderByDateDesc(userId, epoch);
        if(salary == null)
            throw new NullPointerException("Salary object not found");
        if(salary.getUser() == null)
            throw new BadRequestException("User ID is not valid");
        return salary;
    }

    @Override
    public Salary getUserSalaryAt(Long userId, Long epoch) {
        if(epoch >= LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)*1000)
            throw new NullPointerException("Future time given");
        Salary salary = salaryRepository.findFirstByUserIdAndDateLessThanEqualOrderByDateDesc(userId, epoch);
        if(salary == null)
            throw new NullPointerException("Salary object not found");
        if(salary.getUser() == null)
            throw new BadRequestException("User ID is not valid");
        return salary;
    }

    @Override
    public Collection<Salary> getAllCurrentSalaries() {
        Long epoch = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        return salaryRepository.getUsersSalariesAt(epoch);
    }

    @Override
    public Collection<Salary> getAllSalariesAt(Long epoch) {
        return salaryRepository.getUsersSalariesAt(epoch);
    }
}
