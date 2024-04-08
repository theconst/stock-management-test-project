package com.eclub.dto.request;

import com.eclub.dto.doc.Address;
import com.eclub.dto.doc.CustomerId;
import com.eclub.dto.doc.FirstName;
import com.eclub.dto.doc.LastName;
import com.eclub.dto.doc.PhoneNumber;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
public record CustomerRequest(
        @CustomerId Long id,
        @FirstName String firstName,
        @LastName String lastName,
        @PhoneNumber String phoneNumber,
        @Address String address) {
}
