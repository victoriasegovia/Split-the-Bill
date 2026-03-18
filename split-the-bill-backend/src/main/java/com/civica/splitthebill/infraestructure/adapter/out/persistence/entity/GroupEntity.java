package com.civica.splitthebill.infraestructure.adapter.out.persistence.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;

@Entity
@Table(name = "groups")
public class GroupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Id
    @Column(name = "groupId", unique = true)
    private Long groupId;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(
        name = "group_users",
        joinColumns = @JoinColumn(name = "group_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<UserEntity> members = new HashSet<>();

    @OneToMany(mappedBy = "group")
    private Set<ExpenseEntity> expenses = new HashSet<>();

    public GroupEntity() {}

    public GroupEntity(Long id, Long groupId, String name, Set<UserEntity> members, Set<ExpenseEntity> expenses) {
        this.id = id;
        this.groupId = id;
        this.name = name;
        this.members = members;
        this.expenses = expenses;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<UserEntity> getMembers() {
        return members;
    }

    public void setMembers(Set<UserEntity> members) {
        this.members = members;
    }

    public Set<ExpenseEntity> getExpenses() {
        return expenses;
    }

    public void setExpenses(Set<ExpenseEntity> expenses) {
        this.expenses = expenses;
    }

}