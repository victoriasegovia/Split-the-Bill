package com.civica.splitthebill.infraestructure.adapter.out.persistence.entity;

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
    private List<Long> groupIds;

    @OneToMany(mappedBy = "expenses")
    private List<Long> expenseIds;

    @OneToMany(mappedBy = "debts")
    private List<Long> debtIds;

    public UserEntity(Long id, String name) {
        this.id = id;
        this.name = name;
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

    public List<Long> getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(List<Long> groupIds) {
        this.groupIds = groupIds;
    }

    public List<Long> getExpenseIds() {
        return expenseIds;
    }

    public void setExpenseIds(List<Long> expenseIds) {
        this.expenseIds = expenseIds;
    }

    public List<Long> getDebtIds() {
        return debtIds;
    }

    public void setDebtIds(List<Long> debtIds) {
        this.debtIds = debtIds;
    }
}
