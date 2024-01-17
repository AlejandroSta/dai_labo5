DAI Lab - HTTP infrastructure
=============================

The application and the HTTP API server
----------

Our app manages a database of users, the basic CRUD operations are presents, such as creating a new user, login (which reads from the database), updating one's password and deleting an user. The server was built using Java with the Javalin library.


Step 1: Static Web site
-----------------------

We reused a website that Guillaume Gonin already created beforehand and put it in the ./src/web/site folder. A very basic nginx set up was used.

### Acceptance criteria

- [x] You have created a separate folder in your respository for your static Web server.
- [x] You have a Dockerfile based on the Nginx image. The Dockerfile copies the static site content into the image.
- [ ] You have configured the `nginx.conf` configuration file to serve the static content on a port (normally 80).
- [ ] You are able to explain the content of the `nginx.conf` file.
- [x] You can run the image and access the static content from a browser.
- [ ] You have **documented** your configuration in your report.


Step 2: Docker compose
----------------------

The docker-compose.yml file can be found at ./src.

Regarding the website, we specify the path inside the docker container, set up the traefik config (access via localhost on port 80) and deploy five replicas.

The same configuration is made for the server and database with a few differences. Notably, the server sets a cookie in order to use sticky sessions, there is an "/api" prefix to it.

### Acceptance criteria

- [x] You have added a docker compose configuration file to your GitHub repo.
- [x] You can start and stop an infrastructure with a single static Web server using docker compose.
- [x] You can access the Web server on your local machine on the respective port.
- [x] You can rebuild the docker image with `docker compose build`
- [x] You have **documented** your configuration in your report.


Step 3: HTTP API server
-----------------------

As previously mentionned, we chose to create a user list manager. All CRUD operations were mentionned earlier.

### Acceptance criteria

- [x] Your API supports all CRUD operations.
- [x] You are able to explain your implementation and walk us through the code.
- [x] You can start and stop the API server using docker compose.
- [ ] You can access both the API and the static server from your browser.
- [x] You can rebuild the docker image with docker compose.
- [x] You can do demo where use an API testing tool to show that all CRUD operations work.
- [x] You have **documented** your implementation in your report.


Step 4: Reverse proxy with Traefik
----------------------------------

The goal of this step is to place a reverse proxy in front of the dynamic and static Web servers such that the reverse proxy receives all connections and relays them to the respective Web server. 

You will use [Traefik](https://traefik.io/traefik/) as a reverse proxy. Traefik interfaces directly with Docker to obtain the list of active backend servers. This means that it can dynamically adjust to the number of running server. Traefik has the particularity that it can be configured using labels in the docker compose file. This means that you do not need to write a configuration file for Traefik, but Traefik will read container configurations from the docker engine through the file `/var/run/docker.sock`.

The steps to follow for this section are thus:

- Add a new service "reverse_proxy" to your docker compose file using the Traefik docker image
- Read the [Traefik Quick Start](https://doc.traefik.io/traefik/getting-started/quick-start/) documentation to establish the basic configuration.
- Read the [Traefik & Docker](https://doc.traefik.io/traefik/routing/providers/docker/) documentation to learn how to configure Traefik to work with Docker.
- Then implement the reverse proxy:
  - relay the requests coming to "localhost" to the static HTTP server
  - relay the requests coming to "localhost/api" to the API server. See the [Traefik router documentation](https://doc.traefik.io/traefik/routing/routers/) for managing routes based on path prefixes. 
  - you will have to remove the `ports` configuration from the static and dynamic server in the docker compose file and replace them with `expose` configuration. Traefik will then be able to access the servers through the internal Docker network.
- You can use the [Traefik dashboard](https://doc.traefik.io/traefik/operations/dashboard/) to monitor the state of the reverse proxy.

### Acceptance criteria

- [ ] You can do a demo where you start from an "empty" Docker environment (no container running) and using docker compose you can start your infrastructure with 3 containers: static server, dynamic server and reverse proxy
- [ ] In the demo you can access each server from the browser in the demo. You can prove that the routing is done correctly through the reverse proxy.
- [ ] You are able to explain how you have implemented the solution and walk us through the configuration and the code.
- [ ] You are able to explain why a reverse proxy is useful to improve the security of the infrastructure.
- [x] You are able to explain how to access the dashboard of Traefik and how it works.
- [ ] You have **documented** your configuration in your report.


Step 5: Scalability and load balancing
--------------------------------------

The goal of this section is to allow Traefik to dynamically detect several instances of the (dynamic/static) Web servers. You may have already done this in the previous step 3.

Modify your docker compose file such that several instances of each server are started. Check that the reverse proxy distributes the connections between the different instances. Then, find a way to *dynamically* update the number of instances of each service with docker compose, without having to stop and restart the topology.

### Acceptance criteria

- [x] You can use docker compose to start the infrastructure with several instances of each server (static and dynamic).
- [ ] You can dynamically add and remove instances of each server.
- [ ] You can do a demo to show that Traefik performs load balancing among the instances.
- [ ] If you add or remove instances, you can show that the load balancer is dynamically updated to use the available instances.
- [ ] You have **documented** your configuration in your report.


Step 6: Load balancing with round-robin and sticky sessions
-----------------------------------------------------------

By default, Traefik uses round-robin to distribute the load among all available instances. However, if a service is stateful, it would be better to send requests of the same session always to the same instance. This is called sticky sessions.

The goal of this step is to change the configuration such that:

- Traefik uses sticky session for the dynamic server instances (API service).
- Traefik continues to use round robin for the static servers (no change required).

### Acceptance criteria

- [ ] You do a setup to demonstrate the notion of sticky session.
- [ ] You prove that your load balancer can distribute HTTP requests in a round-robin fashion to the static server nodes (because there is no state).
- [ ] You prove that your load balancer can handle sticky sessions when forwarding HTTP requests to the dynamic server nodes.
- [ ] You have **documented** your configuration and your validation procedure in your report.


Step 7: Securing Traefik with HTTPS
-----------------------------------

Any real-world web infrastructure must be secured with HTTPS instead of clear-text HTTP. The goal of this step is to configure Traefik to use HTTPS with the clients. The schema below shows the architecture.

```mermaid

graph LR
    subgraph Client
        B((Browser))
    end
    subgraph Server
        RP(Reverse\nProxy)
        SS(Static\nWeb server)
        DS(Dynamic\nAPI server)
    end
    B -. HTTPS .-> RP
    RP -- HTTP --> SS
    RP -- HTTP --> DS
```

This means that HTTPS is used for connection with clients, over the Internet. Inside the infrastructure, the connections between the reverse proxy and the servers are still done in clear-text HTTP.

### Certificate

To do this, you will first need to generate an encryption certificate. Since the system is not exposed to the Internet, you cannot use a public certificate such as Let's encrypt, but have to generate a self-signed certificate. You can [do this using openssl](https://stackoverflow.com/questions/10175812/how-to-create-a-self-signed-certificate-with-openssl#10176685).

Once you got the two files (certificate and key), you can place them into a folder, which has to be [mounted as a volume in the Traefik container](https://docs.docker.com/compose/compose-file/compose-file-v3/#short-syntax-3). You can mount the volume at any path in the container, for example `/etc/traefik/certificates`.

### Traefik configuration file

Up to now, you've configured Traefik through labels directely in the docker compose file. However, it is not possible to specify the location of the certificates to Traefik with labels. You have to create a configuration file `traefik.yaml`. 

Again, you have to mount this file into the Traefik container as a volume, at the location `/etc/traefik/traefik.yaml`.

The configuration file has to contain several sections:

- The [providers](https://doc.traefik.io/traefik/providers/docker/#configuration-examples) section to configure Traefik to read the configuration from Docker.
- The [entrypoints](https://doc.traefik.io/traefik/routing/entrypoints/#configuration-examples) section to configure two endpoints:  `http` and `https`.
- The [tls](https://doc.traefik.io/traefik/https/tls/#user-defined) section to configure the TLS certificates. Specify the location of the certificates as the location where you mounted the directory into the container (such as `/etc/traefik/certificates`).
- In order to make the dashboard accessible, you have to configure the [api](https://doc.traefik.io/traefik/operations/dashboard/#insecure-mode) section. You can remove the respective labels from the docker compose file.

### Activating the HTTPS entrypoint for the servers

Finally, you have to activate HTTPS for the static and dynamic servers. This is done in the docker compose file. You have to add two labels to each server:

- to activate the HTTPS entrypoint,
- to set TLS to true.

See the [Traefik documentation for Docker](https://doc.traefik.io/traefik/routing/providers/docker/#routers) for these two labels.

### Testing

After these configurations it should be possible to access the static and the dynamic servers through HTTPS. The browser will complain that the sites are not secure, since the certificate is self-signed. But you can ignore this warning.

If it does not work, go to the Traefik dashboard and check the configuration of the routers and the entrypoints.

### Acceptance criteria

- [ ] You can do a demo where you show that the static and dynamic servers are accessible through HTTPS.
- [ ] You have **documented** your configuration in your report.



Optional steps
==============

If you sucessfully complete all the steps above, you can reach a grade of 5.0. If you want to reach a higher grade, you can do one or more of the following optional steps. 

Optional step 1: Management UI
------------------------------

The goal of this step is to deploy or develop a Web app that can be used to monitor and update your Web infrastructure dynamically. You should be able to list running containers, start/stop them and add/remove instances.

- you use an existing solution (search on Google)
- for extra points, develop your own Web app. In this case, you can use the Dockerode npm module (or another Docker client library, in any of the supported languages) to access the docker API.

### Acceptance criteria

- [ ] You can do a demo to show the Management UI and manage the containers of your infrastructure.
- [ ] You have **documented** how to use your solution.
- [ ] You have **documented** your configuration in your report.


Optional step 2: Integration API - static Web site
--------------------------------------------------

This is a step into unknow territory. But you will figure it out.

The goal of this step is to change your static Web page to periodically make calls to your API server and show the results in the Web page. You will need JavaScript for this and this functionality is called AJAX.

Keep it simple! You can start by just making a GET request to the API server and display the result on the page. If you want, you can then you can add more features, but this is not obligatory.


The modern way to make such requests is to use the [JavaScript Fetch API](https://developer.mozilla.org/en-US/docs/Web/API/Fetch_API/Using_Fetch). But you can also use JQuery if you prefer.


### Acceptance criteria

- [ ] You have added JavaScript code to your static Web page to make at least a GET request to the API server.
- [ ] You can do a demo where you show that the API is called and the result is displayed on the page.
- [ ] You have **documented** your implementation in your report.