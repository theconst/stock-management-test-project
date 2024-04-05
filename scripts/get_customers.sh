#!/usr/bin/env bash

CUSTOMERS_ENDPOINT="${CUSTOMERS_ENDPOINT:-localhost:8081/customers/}"

curl -XGET "$CUSTOMERS_ENDPOINT" | jq .