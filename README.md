# Zuul based service gateway

## Build project
`mvn clean install`

## Run locally
`mvn spring-boot:run`

## Test manually
`curl localhost:5555/routes`

## Build docker image
`mvn clean package docker:build`


## Copied from
`https://github.com/carnellj/spmia-chapter6`