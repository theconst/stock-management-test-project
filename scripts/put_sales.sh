SALES_ENDPOINT="${SALES_ENDPOINT:-localhost:8081/sales/}"

curl -XPUT "${SALES_ENDPOINT}" \
  -H 'Content-Type: application/json' \
  -d '{"productId": 1, "batchNumber": 1, "quantity": 1}'

curl -XPUT "${SALES_ENDPOINT}" \
  -H 'Content-Type: application/json' \
  -d '{"productId": 2, "batchNumber": 1, "quantity": 1}'