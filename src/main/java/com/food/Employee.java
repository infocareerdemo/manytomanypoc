package com.food;

import java.util.HashSet;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;


@Data
@Entity
@Table(name = "employees")
@JsonIgnoreProperties({"projects"}) // Prevents projects from being serialized
public class Employee {

	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "ID")
	    private Long id;

	    @Column(name = "name")
	    private String name;

	    @Column(name = "email")
	    private String email;

	    @Column(name = "technicalSkill")
	    private String technicalSkill;


	    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	    @JoinTable(
	        name = "employee_project_mapping", // Join table
	        joinColumns = @JoinColumn(name = "employee_id"), // Foreign key for Employee
	        inverseJoinColumns = @JoinColumn(name = "project_id") // Foreign key for Project
	    )
	    private Set<Project> projects = new HashSet<>();
	    
	    
	    
	    @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (o == null || getClass() != o.getClass()) return false;
	        Employee employee = (Employee) o;
	        return Objects.equals(id, employee.id);
	    }

	    @Override
	    public int hashCode() {
	        return Objects.hash(id);
	    }
}