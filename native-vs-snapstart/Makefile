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

deploy_snapstart:
	./mvnw clean package -DskipTests
	sam deploy \
		--no-confirm-changeset \
		--no-fail-on-empty-changeset \
		-t aws/sam.snapstart.yaml \
		--stack-name case-studies-native-vs-snapstart-snapstart \
		--s3-bucket artifacts-gamma \
		--capabilities CAPABILITY_IAM

deploy_jar:
	./mvnw clean package -DskipTests
	sam deploy \
		--no-confirm-changeset \
		--no-fail-on-empty-changeset \
		-t aws/sam.jar.yaml \
		--stack-name case-studies-native-vs-snapstart-jar \
		--s3-bucket artifacts-gamma \
		--capabilities CAPABILITY_IAM

monitor:
	npx lumigo-cli analyze-lambda-cold-starts -m 20 --name=native-vs-snapstart-jar --region=eu-west-1
	npx lumigo-cli analyze-lambda-cold-starts -m 20 --name=native-vs-snapstart-native --region=eu-west-1
	npx lumigo-cli analyze-lambda-cold-starts -m 20 --name=native-vs-snapstart-snapstart --region=eu-west-1

call:
	ab -k -c 15 -n 1000 -p benchmark/post.json -T 'application/json' https://s1mcgjq43e.execute-api.eu-west-1.amazonaws.com/fruits
	ab -k -c 15 -n 1000 -p benchmark/post.json -T 'application/json' https://s9ed527d11.execute-api.eu-west-1.amazonaws.com/fruits
	ab -k -c 15 -n 1000 -p benchmark/post.json -T 'application/json' https://jny9qejsjf.execute-api.eu-west-1.amazonaws.com/fruits
