#!/usr/bin/env bash

PRODUCTS_ENDPOINT_URL="${PRODUCTS_ENDPOINT_URL:-localhost:8080/products}"

curl -XPOST "$PRODUCTS_ENDPOINT_URL" \
  -H 'Content-Type: application/json' \
  -d '{"name": "Lenovo ThinkPad", "vendor": "Lenovo", "description": "Durable laptop"}' \
  | jq .

curl -XPOST "$PRODUCTS_ENDPOINT_URL" \
  -H 'Content-Type: application/json' \
  -d '{"name": "Apple M32 1TB", "vendor": "Apple", "description": "Expensive laptop"}' \
  | jq .
