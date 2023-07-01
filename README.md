# security-servlet
security-servlet

初始用户名：user1@abc.com / user2@abc.com
密码：password

# Generate RSAPublicKey and RSAPrivateKey

## Generating the Private Key
```
openssl genrsa -out rsa.private 2048
```
## Generating the Public Key
```
openssl rsa -in rsa.private -out rsa.public -pubout -outform PEM
```

Then copy above rsa.public and rsa.private files into resources folder.

# Test
```
curl -X POST -u "user1@abc.com:password" localhost:8080/token
export TOKEN=`curl -X POST -u "user1@abc.com:password" localhost:8080/token`
curl -H "Authorization: Bearer $TOKEN" localhost:8080/hello && echo
```
