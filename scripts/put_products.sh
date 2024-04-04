#!/usr/bin/env bash
#
# Script for add hoc pushing of data to the API
#
# Integration tests already cover the functionality.
#
# The script may be used if manual interaction in local is needed

PRODUCTS_ENDPOINT_URL="${PRODUCTS_ENDPOINT_URL:-localhost:8080/products}"

curl -XPOST "$PRODUCTS_ENDPOINT_URL" \
  -H 'Content-Type: application/json' \
  -d '{"name": "Lenovo ThinkPad", "vendor": "Lenovo", "description": "Durable laptop"}'

curl -XPOST "$PRODUCTS_ENDPOINT_URL" \
  -H 'Content-Type: application/json' \
  -d '{"name": "Apple M32 1TB", "vendor": "Apple", "description": "Expensive laptop"}'
