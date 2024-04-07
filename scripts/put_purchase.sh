PURCHASES_URL="${PURCHASES_URL:-localhost:8080/purchases/}"

curl -XPUT "${PURCHASES_URL}" \
  -H 'Content-Type: application/json' \
  -d '{"productId": 1, "batchNumber": 1, "quantity": 10}' \
  | jq .

curl -XPUT "${PURCHASES_URL}" \
  -H 'Content-Type: application/json' \
  -d '{"productId": 2, "batchNumber": 1, "quantity": 20}' \
  | jq .