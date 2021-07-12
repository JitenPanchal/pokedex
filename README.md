# pokedex

Steps to run pokedex spring rest apis application

1. Download and install docker on your machine
2. Open command prompt 
3. Run command to pull docker image (https://hub.docker.com/r/jitenpanchal/pokemon-apis-app) from docker hub
   docker pull jitenpanchal/pokemon-apis-app
4. Run command to run docker container 
   docker run -d -p 9999:9999 jitenpanchal/pokemon-apis-app
5. Next use browser/postman to access apis.e.g. 
- http://localhost:9999/pokemon/wormadam
- http://localhost:9999/pokemon/mewtwo
- http://localhost:9999/pokemon/translated/wormadam
- http://localhost:9999/pokemon/translated/mewtwo
