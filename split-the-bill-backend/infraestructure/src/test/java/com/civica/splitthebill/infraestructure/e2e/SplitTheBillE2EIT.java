package com.civica.splitthebill.infraestructure.e2e;

import com.civica.splitthebill.application.dto.ExpenseDTO;
import com.civica.splitthebill.application.dto.GroupDTO;
import com.civica.splitthebill.application.dto.UserDTO;
import com.civica.splitthebill.infraestructure.AbstractIntegrationTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SplitTheBillE2EIT extends AbstractIntegrationTest {
  
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldExecuteFullWorkflowSuccessfully() {

        GroupDTO groupRequest = new GroupDTO(null, "Cena Granada");
        ResponseEntity<GroupDTO> groupResponse = restTemplate.postForEntity("/api/groups", groupRequest, GroupDTO.class);
        
        assertEquals(HttpStatus.CREATED, groupResponse.getStatusCode());
        Long groupId = groupResponse.getBody().groupId();
        assertNotNull(groupId);

        UserDTO userRequest = new UserDTO(null, "Victoria");
        ResponseEntity<UserDTO> userResponse = restTemplate.postForEntity("/api/groups/" + groupId + "/users", userRequest, UserDTO.class);
        
        assertEquals(HttpStatus.CREATED, userResponse.getStatusCode());
        Long userId = userResponse.getBody().userId();
        assertNotNull(userId);

        ExpenseDTO expenseRequest = new ExpenseDTO(null, "Tapas", userId, groupId, 25.50);
        ResponseEntity<ExpenseDTO> expenseResponse = restTemplate.postForEntity("/api/groups/" + groupId + "/expenses", expenseRequest, ExpenseDTO.class);

        assertAll(
            () -> assertEquals(HttpStatus.CREATED, expenseResponse.getStatusCode()),
            () -> assertNotNull(expenseResponse.getBody().expenseId()),
            () -> assertEquals(25.50, expenseResponse.getBody().totalAmount())
        );
    }
}