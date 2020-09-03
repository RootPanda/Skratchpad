call gradlew build
docker build -t skratchpad:1 .
docker network create -d bridge local
docker-compose up -d
