docker-compose down
docker rm lab5-server
docker rm lab5-web-site
docker rm lab5-db
docker image rm src-lab5-web-site
docker image rm src-lab5-server
docker volume rm src_psql
docker-compose up  --force-recreate -d