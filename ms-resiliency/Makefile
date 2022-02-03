
package:
	mvn package

k8s-deploy:
	./mvnw clean package -Dquarkus.kubernetes.deploy=true

.PHONY: bff-service
local-dev-bff-service:
	cd coffee-service-bff \
	&& mvn quarkus:dev -Ddebug=5005 -Dquarkus.http.port=8080

.PHONY: order-service
local-dev-order-service:
	cd order-service \
	&& mvn quarkus:dev -Ddebug=5006 -Dquarkus.http.port=8081

.PHONY: beans-service
local-dev-beans-service:
	cd beans-service \
	&& mvn quarkus:dev -Ddebug=5007 -Dquarkus.http.port=8082

.PHONY: barista-service
local-dev-barista-service:
	cd barista-service \
	&& mvn quarkus:dev -Ddebug=5008 -Dquarkus.http.port=8083