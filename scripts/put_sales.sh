SALES_ENDPOINT="${SALES_ENDPOINT:-localhost:8085/sales/}"

curl -XPUT "${SALES_ENDPOINT}" \
  -H 'Content-Type: application/json' \
  -d '{"stockItemId": 1, "quantity": 1, "price": 20}' \
  jq .

curl -XPUT "${SALES_ENDPOINT}" \
  -H 'Content-Type: application/json' \
  -d '{"stockItemId": 2, "quantity": 1, "price": 30}' \
  jq .