#!/bin/sh

docker build -t murphylan/jwt .  
docker run -d --name jwt -p 8080:8080 murphylan/jwt