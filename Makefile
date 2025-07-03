local-infra:
	docker-compose up -d

local-app:
	aws --endpoint-url http://localhost:4566 sns create-topic --name client-notifications
	./gradlew clean
	./gradlew :modules:entrypoint:web:build
	./gradlew :modules:entrypoint:sns:build
	sam build
	sam local start-api \
	  --env-vars env.json \
	  --docker-network pinapp_rquirpa_net \
	  --parameter-overrides SpringProfile=local

prod-infra:
	sam build --config-env infra
	sam deploy --guided --config-env infra

prod-app:
	./gradlew clean
	./gradlew :modules:entrypoint:web:build
	./gradlew :modules:entrypoint:sns:build
	sam build
	sam deploy --parameter-overrides SpringProfile=prod

test-sns:
	./gradlew clean
	./gradlew :modules:entrypoint:sns:build
	sam local invoke SnsConsumerFunction  \
	  --event input-sns.json  \
	  --env-vars env.json  \
	  --docker-network pinapp_rquirpa_net
