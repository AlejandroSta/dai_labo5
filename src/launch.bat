docker-compose down
docker rm lab5-server
docker rm lab5-web-site
docker rm lab5-db
docker image rm src-lab5-web-site
docker image rm src-lab5-server
docker-compose up  --force-recreate -d
docker exec -it lab5-db psql -U u_app -d db_app