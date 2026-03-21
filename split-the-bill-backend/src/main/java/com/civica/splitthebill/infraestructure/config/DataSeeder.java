package com.civica.splitthebill.infraestructure.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.civica.splitthebill.domain.model.Expense;
import com.civica.splitthebill.domain.model.Group;
import com.civica.splitthebill.domain.model.User;
import com.civica.splitthebill.domain.port.out.ExpensePortOut;
import com.civica.splitthebill.domain.port.out.GroupPortOut;
import com.civica.splitthebill.domain.port.out.UserPortOut;

@Component
public class DataSeeder implements CommandLineRunner {

    private final GroupPortOut groupRepository;
    private final UserPortOut userRepository;
    private final ExpensePortOut expenseRepository;

    public DataSeeder(GroupPortOut groupRepository, UserPortOut userRepository, ExpensePortOut expenseRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.expenseRepository = expenseRepository;
    }

    @Override
    public void run(String... args) {
        Set<Long> martaGroups = new HashSet<>();
        Set<Long> umbertoGroups = new HashSet<>();
        Set<Long> antonioGroups = new HashSet<>();

        Set<Long> canariasMembers = new HashSet<>();
        Set<Long> canariasExpenses = new HashSet<>();

        Set<Long> cenaMembers = new HashSet<>();
        Set<Long> cenaExpenses = new HashSet<>();

        Set<Long> cumpleMembers = new HashSet<>();
        Set<Long> cumpleExpenses = new HashSet<>();

        User marta = userRepository.save(new User(null, "Marta", martaGroups, new HashSet<>()));
        User umberto = userRepository.save(new User(null, "Umberto", umbertoGroups, new HashSet<>()));
        User antonio = userRepository.save(new User(null, "Antonio", antonioGroups, new HashSet<>()));

        Group groupCanarias = groupRepository.save(new Group(null, "Viaje a las Islas Canarias", canariasMembers, canariasExpenses));
        Group groupCena = groupRepository.save(new Group(null, "Cena de navidad", cenaMembers, cenaExpenses));
        Group groupCumple = groupRepository.save(new Group(null, "Cumpleaños de Marta", cumpleMembers, cumpleExpenses));

        Expense expenseCafe = expenseRepository.save(new Expense(null, "Café en la plaza mayor", marta.userId(), groupCena.groupId(), 2.50));
        Expense expenseSocarrat = expenseRepository.save(new Expense(null, "Socarrat", marta.userId(), groupCumple.groupId(), 33.50));
        Expense expenseSurf = expenseRepository.save(new Expense(null, "Surfing the turists", umberto.userId(), groupCumple.groupId(), 50.50));

        cenaMembers.add(marta.userId());
        cenaExpenses.add(expenseCafe.expenseId());
        martaGroups.add(groupCena.groupId());

        cumpleMembers.add(marta.userId());
        cumpleMembers.add(umberto.userId());
        cumpleMembers.add(antonio.userId());
        cumpleExpenses.add(expenseSocarrat.expenseId());
        cumpleExpenses.add(expenseSurf.expenseId());

        martaGroups.add(groupCumple.groupId());
        umbertoGroups.add(groupCumple.groupId());
        antonioGroups.add(groupCumple.groupId());

        userRepository.save(new User(marta.userId(), "Marta", martaGroups, new HashSet<>()));
        userRepository.save(new User(umberto.userId(), "Umberto", umbertoGroups, new HashSet<>()));
        userRepository.save(new User(antonio.userId(), "Antonio", antonioGroups, new HashSet<>()));

        groupRepository.save(new Group(groupCanarias.groupId(), "Viaje a las Islas Canarias", canariasMembers, canariasExpenses));
        groupRepository.save(new Group(groupCena.groupId(), "Cena de navidad", cenaMembers, cenaExpenses));
        groupRepository.save(new Group(groupCumple.groupId(), "Cumpleaños de Marta", cumpleMembers, cumpleExpenses));
    }
}
