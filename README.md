# spring-web-security-oauth2
# This project is web project, which authenticate the user with identity provider and return access token. You can use this acces token to invoke any protected resource server
References - 
https://github.com/simplyi/PhotoAppWebClient/tree/rest-template-example

https://github.com/simplyi/PhotoAppWebClient/tree/webclient-example

GitHub Page
https://github.com/simplyi/SocialLoginWebClient

Zip file
https://github.com/simplyi/SocialLoginWebClient/archive/master.zip

# API gateway changes for routes


spring.clod.gateway.routes[0].id= any-name
spring.clod.gateway.routes[0].uri= lb://resource-server-name
spring.clod.gateway.routes[0].predicates[0]=Path=/users/status
spring.clod.gateway.routes[0].predicates[1]=Method=Get
spring.clod.gateway.routes[0].filters[0]=RemoveRequestHeaders=Cookie
