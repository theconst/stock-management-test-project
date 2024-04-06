#!/usr/bin/env bash

CUSTOMERS_ENDPOINT="${CUSTOMERS_ENDPOINT:-localhost:8085/customers/}"

curl -XGET "$CUSTOMERS_ENDPOINT" | jq .