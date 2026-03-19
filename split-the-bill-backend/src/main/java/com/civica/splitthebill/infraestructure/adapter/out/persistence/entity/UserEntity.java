package com.civica.splitthebill.infraestructure.adapter.out.persistence.entity;

import java.util.ArrayList;
import java.util.Objects;
import java.util.List;
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
    private List<GroupEntity> groups = new ArrayList<>();

    @OneToMany(mappedBy = "payer")
    private List<ExpenseEntity> expenses = new ArrayList<>();

    public UserEntity(Long id, String name, List<GroupEntity> groups, List<ExpenseEntity> expenses) {
        this.id = id;
        this.name = name;
        this.groups = groups;
        this.expenses = expenses;
    }

    protected UserEntity() {}

    private UserEntity(Long id) { this.id = id; }

    public static UserEntity createProxy(Long id) {
        Objects.requireNonNull(id, "Id cannot be null");
        return new UserEntity(id);
    }

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

    public List<GroupEntity> getGroups() {
        return groups;
    }

    public void setGroups(List<GroupEntity> groups) {
        this.groups = groups;
    }

    public List<ExpenseEntity> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<ExpenseEntity> expenses) {
        this.expenses = expenses;
    }
}
