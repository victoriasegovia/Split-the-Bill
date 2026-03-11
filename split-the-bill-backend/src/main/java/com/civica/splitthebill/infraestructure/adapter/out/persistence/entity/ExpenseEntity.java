package com.civica.splitthebill.infraestructure.adapter.out.persistence.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "expenses")
public class ExpenseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "total_amount", nullable = false)
    private double totalAmount;

    @ManyToOne
    @JoinColumn(name = "payer_id", nullable = false)
    private UserEntity payer;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private GroupEntity group;

    public ExpenseEntity() {}

    public ExpenseEntity(Long id, String title, UserEntity payer, GroupEntity group, double totalAmount) {
        this.id = id;
        this.title = title;
        this.payer = payer;
        this.group = group;
        this.totalAmount = totalAmount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public UserEntity getPayer() {
        return payer;
    }

    public void setPayer(UserEntity payer) {
        this.payer = payer;
    }

    public GroupEntity getGroup() {
        return group;
    }

    public void setGroup(GroupEntity group) {
        this.group = group;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

}


