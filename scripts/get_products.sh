PRODUCTS_ENDPOINT_URL="${PRODUCTS_ENDPOINT_URL:-localhost:8086/products/}"

curl -XGET "$PRODUCTS_ENDPOINT_URL" | jq .