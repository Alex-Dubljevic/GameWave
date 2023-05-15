#!/bin/bash

echo -n Username:
read username
echo -n Password:
read password
echo -n Endpoint:
read endpoint
echo -n Method: 
read method
output=$(curl -s -X $method 0.0.0.0:80/$endpoint -d "username=$username&password=$password") > /dev/null 2>&1
echo $output