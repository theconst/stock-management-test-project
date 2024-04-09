package com.eclub.common;

import com.eclub.domain.Customer;

public class CustomerStubs {
    public static final Customer.CustomerId CUSTOMER_ID_1 = new Customer.CustomerId(1L);
    public static final Customer CUSTOMER_1 = Customer.builder()
            .id(CUSTOMER_ID_1)
            .address("Green Str.")
            .lastName("Smith")
            .firstName("John")
            .phoneNumber("+123456")
            .build();
    public static final Customer.CustomerId CUSTOMER_ID_2 = new Customer.CustomerId(2L);
    public static final Customer CUSTOMER_2 = Customer.builder()
            .id(CUSTOMER_ID_2)
            .address("White Str.")
            .lastName("White")
            .firstName("William")
            .phoneNumber("+567890")
            .build();
}
