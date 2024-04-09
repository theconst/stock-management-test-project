package com.eclub.service;

import com.eclub.ServiceTest;
import com.eclub.domain.Customer;
import com.eclub.domain.Customer.CustomerId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ServiceTest
class CustomerServiceTest {
    static final CustomerId CUSTOMER_ID_1 = new CustomerId(1L);
    static final CustomerId CUSTOMER_ID_2 = new CustomerId(2L);
    static final Customer CUSTOMER_1 = Customer.builder()
            .id(CUSTOMER_ID_1)
            .address("Green Str.")
            .lastName("Smith")
            .firstName("John")
            .phoneNumber("+123456")
            .build();
    private static final Customer CUSTOMER_2 = Customer.builder()
            .id(CUSTOMER_ID_2)
            .address("White Str.")
            .lastName("White")
            .firstName("William")
            .phoneNumber("+567890")
            .build();

    @Autowired DatabaseClient databaseClient;
    @Autowired CustomerService customerService;

    @Test
    void shouldCreateCustomer() {
        customerService.upsert(CUSTOMER_1.toBuilder().id(null).build()).block();

        assertThat(getCustomers())
                .containsExactly(Map.of(
                        "CUSTOMER_ID", Long.valueOf(1L),
                        "ADDRESS", "Green Str.",
                        "LAST_NAME", "Smith",
                        "FIRST_NAME", "John",
                        "PHONE_NUMBER", "+123456"
                ));
    }

    @Test
    void shouldUpdateCustomer() {
        databaseClient.sql("""
                INSERT INTO customer (customer_id, first_name, last_name, address, phone_number)
                VALUES (1, 'William', 'Spock', 'White Str.', '+567890')
                """).then().block();

        Customer customer = customerService.upsert(CUSTOMER_1).block();

        assertThat(customer).isEqualTo(CUSTOMER_1);

        assertThat(getCustomers())
                .containsExactly(Map.of(
                        "CUSTOMER_ID", Long.valueOf(1L),
                        "ADDRESS", "Green Str.",
                        "LAST_NAME", "Smith",
                        "FIRST_NAME", "John",
                        "PHONE_NUMBER", "+123456"
                ));
    }

    @Test
    void shouldGetCustomerById() {
        databaseClient.sql("""
                INSERT INTO customer (customer_id, first_name, last_name, address, phone_number)
                VALUES (1, 'John', 'Smith', 'Green Str.', '+123456')
                """).then().block();

        Customer customer = customerService.findCustomerById(1L).block();

        assertThat(customer).isEqualTo(CUSTOMER_1);
    }

    @Test
    void shouldListCustomers() {
        Flux.just("""
                INSERT INTO customer (customer_id, first_name, last_name, address, phone_number)
                VALUES (1, 'John', 'Smith', 'Green Str.', '+123456')
                """,
                """
                INSERT INTO customer (customer_id, first_name, last_name, address, phone_number)
                VALUES (2, 'William', 'White', 'White Str.', '+567890')
                """)
                .map(databaseClient::sql)
                .flatMap(DatabaseClient.GenericExecuteSpec::then)
                .blockLast();

        var page1 = customerService.listCustomers(PageRequest.of(0, 1)).block();
        var page2 = customerService.listCustomers(PageRequest.of(1, 1)).block();

        assertThat(page1.getTotalPages()).isEqualTo(2);
        assertThat(page1).hasSize(1);
        assertThat(page1).containsExactly(CUSTOMER_1);

        assertThat(page1.getTotalPages()).isEqualTo(2);
        assertThat(page2).hasSize(1);
        assertThat(page2).containsExactly(CUSTOMER_2);
    }

    List<Map<String, Object>> getCustomers() {
        return databaseClient.sql("SELECT * FROM customer")
                .fetch()
                .all()
                .collectList()
                .block();
    }
}
