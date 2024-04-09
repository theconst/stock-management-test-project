STOCK_ITEMS_ENDPOINT="${STOCK_ITEMS_ENDPOINT:-localhost:8080/stock-items/?pageNumber=0&pageSize=1}"

curl -XGET "$STOCK_ITEMS_ENDPOINT" | jq .