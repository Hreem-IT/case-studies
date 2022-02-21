traffic:
	while true; do \
		ab -p create-order-payload.json \
		-c 500 -n 500 -s 120 -T 'application/json' localhost:8080/orders | grep Failed; \
		sleep 1; \
	done

monitor-beans:
	while true; do \
		BEANS=`curl localhost:8080/beans -H "Content-Type: application/json"`; \
		if [ -z "$$BEANS" ]; then \
			echo "Beans service not available at the moment, check back later!"; \
			else \
			echo "☕️ beans: $$BEANS grams"; \
		fi; \
		sleep 1; \
		clear; \
	done

monitor-orders:
	while true; do \
		curl localhost:8080/orders -H "Content-Type: application/json" | jq; \
		sleep 1; \
		clear; \
	done
