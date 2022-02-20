traffic:
	while true; do \
		NAME=$(curl https://api.namefake.com/ | jq .name); \
		ab -p create-order-payload.json \
		-c 3 -n 3 -T 'application/json' localhost:8081/orders; \
		sleep 10; \
	done

monitor-orders:
	while true; do \
		ORDERS=`curl localhost:8081/orders -H "Content-Type: application/json"`;\
		PENDING=`echo $$ORDERS | jq | grep 'PENDING' -i -c`; \
		PROCESSING=`echo $$ORDERS | jq | grep 'PROCESSING' -i -c`; \
		COMPLETED=`echo $$ORDERS | jq | grep 'COMPLETED' -i -c`; \
		echo ""; \
		echo "Orders PENDING ⏱ : $$PENDING"; \
		echo "Orders PROCESSING ☕️ : $$PROCESSING"; \
		echo "Orders COMPLETED ✅ : $$COMPLETED"; \
		sleep 1; \
		clear; \
	done
