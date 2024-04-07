STOCK_ITEMS_ENDPOINT="${STOCK_ITEMS_ENDPOINT:-localhost:8080/stock-items/?page=0&size=1}"

curl -XGET "$STOCK_ITEMS_ENDPOINT" | jq .