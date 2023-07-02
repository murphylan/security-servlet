#!/bin/sh

docker build -t murphylan/securityservlet .  
docker run -d --name securityservlet -p 8080:8080 murphylan/securityservlet