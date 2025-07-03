# challenge serverless API

## Cómo ver la documentación en Swagger Editor

1. Ingresá a [https://editor.swagger.io](https://editor.swagger.io)
2. Hacé clic en `File > Import File`
3. Desde la carpeta raíz del proyecto, seleccioná `swagger.yaml`
4. ¡Listo! Verás la documentación completa de la API en modo visual.

## Pre-requisitos
* [AWS CLI](https://aws.amazon.com/cli/)
* [SAM CLI](https://github.com/awslabs/aws-sam-cli)
* [Gradle](https://gradle.org/)

## Pruebas locales con Docker + SAM CLI

Desde la carpeta raíz del proyecto - donde se encuentra el archivo `template.yml` - abre una nueva terminal y ejecuta los siguientes comandos uno por uno:

#### Paso 1: Iniciar los servicios Docker con Docker Compose
```bash

docker-compose up -d
```

#### Paso 2: Crear el topico en localstack
```bash

aws --endpoint-url http://localhost:4566 sns create-topic --name client-notifications
```


#### Paso 3: Compilar los módulos y generar los archivos ZIP de distribución
```bash

./gradlew clean
./gradlew :modules:entrypoint:web:build
./gradlew :modules:entrypoint:sns:build
```

#### Paso 4: Construir y desplegar la aplicación en entorno local con SAM
```bash

sam build
sam local start-api \
  --env-vars env.json \
  --docker-network pinapp_rquirpa_net \
  --parameter-overrides SpringProfile=local
```

Cuando se muestre este mensaje, podrás probar el API localmente.

```
* Running on http://127.0.0.1:3000
  2025-07-03 18:05:45 Press CTRL+C to quit
```

---

## Despliegue en AWS
Para desplegar la aplicación en tu cuenta de AWS, podés usar el proceso guiado de despliegue de SAM CLI y seguir las instrucciones que aparecen en pantalla.

#### Paso 1: Desplegar la infraestructura base (PostgreSQL y SNS) en AWS con SAM
```bash

sam build --config-env infra
sam deploy --guided --config-env infra
```

El comando `sam deploy --guided` realizará una serie de preguntas para configurar el entorno por primera vez. A continuación se muestran las respuestas recomendadas. En todos los casos, debés escribir la respuesta indicada (si corresponde) y presionar Enter.

| Pregunta                                   | Respuesta sugerida |
|--------------------------------------------|--------------------|
| Stack Name [pinapp-rquirpa-infra]          | `Enter`            |
| AWS Region [us-east-1]                     | `Enter`            |
| Parameter DBUsername [postgres]            | `Enter`            |
| Parameter DBPassword [s3cretp4ss]          | `Enter`            |
| Parameter DBName [postgres]                | `Enter`            |
| Parameter DBAllocatedStorage [20]          | `Enter`            |
| Confirm changes before deploy [y/N]        | **N** `Enter`      |
| Allow SAM CLI IAM role creation [Y/n]      | **Y** `Enter`      |
| Disable rollback [y/N]                     | **N** `Enter`      |
| Save arguments to configuration file [Y/n] | **N** `Enter`      |

Al iniciar el despliegue, verás un mensaje similar a:
```
2025-07-01 17:09:51 - Waiting for stack create/update to complete
```

Una vez finalizado el despliegue de la infraestructura base, la SAM CLI mostrará en pantalla los outputs (salidas) del stack:
```
...
CREATE_COMPLETE                    AWS::SNS::Topic                    ClientNotificationTopic            -                                
CREATE_COMPLETE                    AWS::RDS::DBInstance               PostgresDB                         -                                
CREATE_COMPLETE                    AWS::CloudFormation::Stack         pinapp-rquirpa-infra               - 
...
Successfully created/updated stack - pinapp-rquirpa-infra in us-east-1
```

#### Paso 2: Compilar los módulos y generar los archivos ZIP de distribución
```bash

./gradlew clean
./gradlew :modules:entrypoint:web:build
./gradlew :modules:entrypoint:sns:build
```

#### Paso 3: Construir y desplegar la aplicación en AWS con SAM
```bash

sam build
sam deploy --parameter-overrides SpringProfile=prod
```

Una vez finalizado el despliegue de applicación, la SAM CLI la URL de la applicación:
```
...
CREATE_COMPLETE                    AWS::ApiGateway::Stage             ServerlessRestApiProdStage         -                                
CREATE_COMPLETE                    AWS::CloudFormation::Stack         pinapp-rquirpa-app                 -                                
-----------------------------------------------------------------------------------------------------------------------------------------

CloudFormation outputs from deployed stack
-------------------------------------------------------------------------------------------------------------------------------------------
Outputs                                                                                                                                   
-------------------------------------------------------------------------------------------------------------------------------------------
Key                 ApiUrl                                                                                                                
Description         Application URL                                                                                                       
Value               https://2ujgjzhsmb.execute-api.us-east-1.amazonaws.com/Prod/                                                          

Key                 SwaggerUrl                                                                                                            
Description         Swagger URL                                                                                                           
Value               https://2ujgjzhsmb.execute-api.us-east-1.amazonaws.com/Prod/v3/api-docs.yaml  
-------------------------------------------------------------------------------------------------------------------------------------------


Successfully created/updated stack - pinapp-rquirpa-app in us-east-1
```

Copiá el `OutputValue` del ApiUrl y usalo en Postman o con curl para probar tu primera solicitud:

```bash

curl --location 'https://2ujgjzhsmb.execute-api.us-east-1.amazonaws.com/Prod/clients' \
--header 'Content-Type: application/json' \
--data '{
    "firstName": "roberto",
    "lastName": "quirpa",
    "sex": "MALE",
    "birthDate": "1988-05-04"
}'
```
