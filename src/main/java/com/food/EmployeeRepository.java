package com.food;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // Custom query to fetch employee with projects eagerly
    @Query("SELECT e FROM Employee e LEFT JOIN FETCH e.projects WHERE e.id = :id")
    Optional<Employee> findByIdWithProjects(Long id);

}
