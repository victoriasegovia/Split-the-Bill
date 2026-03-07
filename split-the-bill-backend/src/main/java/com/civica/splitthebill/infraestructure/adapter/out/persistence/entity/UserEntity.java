package com.civica.splitthebill.infraestructure.adapter.out.persistence.entity;

import java.util.List;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany(mappedBy = "members")
    private List<GroupEntity> groups;

    public UserEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
