package com.civica.splitthebill.infraestructure.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.civica.splitthebill.domain.model.Group;
import com.civica.splitthebill.domain.port.out.GroupRepository;

@Component
public class DataSeeder implements CommandLineRunner {

    private final GroupRepository groupRepository;

    public DataSeeder(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public void run(String... args) {
        groupRepository.save(new Group(null, "Viaje a las Islas Canarias"));
        groupRepository.save(new Group(null, "Cena de navidad"));
        groupRepository.save(new Group(null, "Cumpleanos de Marta"));
    }
}