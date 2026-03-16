package com.civica.splitthebill.infraestructure.adapter.out.persistence.entity;

import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany(mappedBy = "members")
    private Set<GroupEntity> groups = new HashSet<>();

    @OneToMany(mappedBy = "payer")
    private Set<ExpenseEntity> expenses = new HashSet<>();

    public UserEntity(Long id, String name, Set<GroupEntity> groups, Set<ExpenseEntity> expenses) {
        this.id = id;
        this.name = name;
        this.groups = groups;
        this.expenses = expenses;
    }

    public UserEntity() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<GroupEntity> getGroups() {
        return groups;
    }

    public void setGroups(Set<GroupEntity> groups) {
        this.groups = groups;
    }

    public Set<ExpenseEntity> getExpenses() {
        return expenses;
    }

    public void setExpenses(Set<ExpenseEntity> expenses) {
        this.expenses = expenses;
    }
}
