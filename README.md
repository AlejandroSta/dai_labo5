DAI Lab - HTTP infrastructure
=============================

The application and the HTTP API server
----------

Our app manages a database of users, the basic CRUD operations are presents, such as creating a new user, login (which reads from the database), updating one's password and deleting an user. The server was built using Java with the Javalin library.


Static Web site
-----------------------

We reused a website that Guillaume Gonin already created beforehand and put it in the ./src/web/site folder. A very basic nginx set up was used, we didn't bother creating a nginx.conf file since the default nginx configuration was enough for our needs.

The website use AJAX in order to send the requests to the API.


Docker compose
----------------------

The docker-compose.yml file can be found at ./src.

Regarding the website, we specify the path inside the docker container, set up the traefik config (access via localhost on port 80) and deploy five replicas.

The same configuration is made for the server and database with a few differences. Notably, the server sets a cookie in order to use sticky sessions, there is an "/api" prefix to it, as for the database there is no replicas as we don't want multiple instances of it.


Traefik
----------------------------------

We set up a reverse proxy using traefik. The configuration is to be found inside the docker-compose.yml file. For the basics, we first signal that traefik is enabled for the website, server and database. For each of them, we set up what route has to be taken to reach them. We also specify the port for the website and the database and a prefix for the api.

The website will use a cookie that allows to keep a sticky session, this is used in the API to tell to the user that they were the first to connect to the database from the specific instance of the server.

We previously talked about the replicas of the website and server, traefik will automatically handle those and direct the communications in the correct way.


HTTPS and TLS
-----------------------------------

We tried configuring the HTTPS connexion, sadly while we were indeed able to reach the correct ports and establish a connexion, it only lead us to a 404 error for unknow reasons. 

The attempt to set those things up can be found again in the docker-compose.yml file as well as in the traefik.yml file, we generated certificates using openssl that can be found under the ./cert folder.
