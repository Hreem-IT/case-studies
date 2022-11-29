
build:
	./mvnw clean package -DskipTests -Pnative

build_lambdas:
	./mvnw clean package -DskipTests -Pnative,lambda -Dquarkus.profile=aws

# REGION=eu-west-1 make deploy
deploy:
	./mvnw clean package -DskipTests -Pnative -Dquarkus.native.container-build=true
	sam deploy \
		--no-confirm-changeset \
		--no-fail-on-empty-changeset \
		-t aws/sam.native.yaml \
		--stack-name case-studies-native-vs-snapstart-native \
		--s3-bucket artifacts-gamma \
		--capabilities CAPABILITY_IAM


deploy_jar:
	./mvnw clean package -DskipTests
	sam deploy \
		--no-confirm-changeset \
		--no-fail-on-empty-changeset \
		-t aws/sam.jvm.yaml \
		--stack-name case-studies-native-vs-snapstart-snapstart \
		--s3-bucket artifacts-gamma \
		--capabilities CAPABILITY_IAM

monitor:
	npx lumigo-cli analyze-lambda-cost -p ${AWS_PROFILE} -d ${DAYS}
	npx lumigo-cli analyze-lambda-cold-starts -p ${AWS_PROFILE} -d ${DAYS}
