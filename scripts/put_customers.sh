#!/usr/bin/env bash

CUSTOMERS_ENDPOINT="${CUSTOMERS_ENDPOINT:-localhost:8085/customers/}"

curl -XPOST "$CUSTOMERS_ENDPOINT" \
  -H 'Content-Type: application/json' \
  -d '{"firstName": "John", "lastName": "Smith", "phoneNumber": "+12345678", "address": "12345 Green Street"}'

curl -XPOST "$CUSTOMERS_ENDPOINT" \
  -H 'Content-Type: application/json' \
  -d '{"firstName": "Anna", "lastName": "Lavelace", "phoneNumber": "+8128132022", "address": "5432 Downing Street"}'
