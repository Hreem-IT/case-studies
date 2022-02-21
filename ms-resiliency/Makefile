traffic:
	while true; do \
		ab -p create-order-payload.json \
		-c 30 -n 30 -s 120 -T 'application/json' localhost:8080/orders; \
		sleep 1; \
	done

monitor-beans:
	while true; do \
		BEANS=`curl localhost:8080/beans -H "Content-Type: application/json"`; \
		echo "☕️ beans: $$BEANS grams in stock"; \
		sleep 1; \
		clear; \
	done

monitor-orders:
	while true; do \
		curl localhost:8080/orders -H "Content-Type: application/json" | jq; \
		sleep 1; \
		clear; \
	done
