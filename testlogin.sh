#!/bin/bash

export TOKEN=`curl -X POST -u "user1@abc.com:password" localhost:8080/token`
curl -H "Authorization: Bearer $TOKEN" localhost:8080/hello && echo