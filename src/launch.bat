docker-compose down
docker rm lab5-server
docker rm lab5-web-site
docker image rm src-lab5-web-site
docker image rm src-lab5-server
docker-compose up  --force-recreate -d