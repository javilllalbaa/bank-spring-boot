#!/bin/bash

aws --endpoint-url http://localhost:4566 --profile dev-localstack dynamodb create-table \
    --table-name Account \
    --attribute-definitions AttributeName=Cuenta,AttributeType=S AttributeName=Documento,AttributeType=S \
    --key-schema AttributeName=Cuenta,KeyType=HASH AttributeName=Documento,KeyType=RANGE \
    --provisioned-throughput ReadCapacityUnits=5,WriteCapacityUnits=5 \
    --tags Key=Owner,Value=blueTeam

aws --endpoint-url http://localhost:4566 --profile dev-localstack dynamodb create-table \
    --table-name Transactions \
    --attribute-definitions AttributeName=Cuenta,AttributeType=S AttributeName=Timestamp,AttributeType=S \
    --key-schema AttributeName=Cuenta,KeyType=HASH AttributeName=Timestamp,KeyType=RANGE \
    --provisioned-throughput ReadCapacityUnits=5,WriteCapacityUnits=5 \
    --tags Key=Owner,Value=blueTeam