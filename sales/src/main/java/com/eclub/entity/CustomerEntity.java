package com.eclub.entity;

import lombok.Data;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("customer")
public class CustomerEntity {
    @Column("customer_id")
    Long customerId;
    @Column("first_name")
    String firstName;
    @Column("last_name")
    String lastName;
    @Column("phone_number")
    String phoneNumber;

    String address;
}
