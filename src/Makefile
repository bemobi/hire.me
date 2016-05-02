build:
    #container do redis [Required by testes]
    docker run -d --name redis -p 6379:6379 redis
    docker start redis
    #conseguindo ip do redis (:
    export REDIS_PORT_6379_TCP_ADDR=$(docker inspect --format '{{ .NetworkSettings.IPAddress }}' redis)
    #Testes & docker img
    mvn package docker:build
    #container da aplicacao no ar
    docker run -d --link redis:redis -p 80:8080 shortener/shortener

run:
    docker run -d --link redis:redis -p 80:8080 shortener/shortener --name app

stop:
    docker stop redis
    docker stop app

test:
    mvn test
