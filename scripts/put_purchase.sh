STOCK_ITEMS_ENDPOINT_URL="${STOCK_ITEMS_ENDPOINT_URL:-localhost:8080/stock-items/}"

curl -XPUT "${STOCK_ITEMS_ENDPOINT_URL}purchases" \
  -H 'Content-Type: application/json' \
  -d '{"productId": 1, "batchNumber": 1, "quantity": 10}'

curl -XPUT "${STOCK_ITEMS_ENDPOINT_URL}purchases" \
  -H 'Content-Type: application/json' \
  -d '{"productId": 2, "batchNumber": 1, "quantity": 20}'