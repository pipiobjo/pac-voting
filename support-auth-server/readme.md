http://projects.spring.io/spring-security-oauth/docs/oauth2.html    


https://blog.pivotal.io/pivotal-cloud-foundry/products/securing-restful-web-services-with-oauth2




LDAP Connection Configuration
User: uid=admin,ou=system
Passwd: secret
Connection: localhost:33389

# oauth2 via curl

Requesting a token
```
curl -XPOST -k acme:acmesecret@localhost:9999/auth/oauth/token \
    -d grant_type=password \ 
    -d client_id=acme -d client_secret=acmesecret \
    -d redirect_uri=http://localhost:9999/auth/user \ 
    -d username=bob -d password=bobspassword | jq

```




Response

```bash
{
  "access_token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE0Njg4MDc2MjQsInVzZXJfbmFtZSI6ImJvYiIsImF1dGhvcml0aWVzIjpbIlJPTEVfREVWRUxPUEVSUyJdLCJqdGkiOiI0YjBlNDYzOC1mMjgyLTRhYmQtYTdkMi0zNjc3ZWI0YTlmNzAiLCJjbGllbnRfaWQiOiJhY21lIiwic2NvcGUiOlsib3BlbmlkIl19.boNdBPxWNK-S3NSLLU6k6eaIKeNA0kN4VpAwF9-NcsOps9ItNPWQUrmPuBFnRzD0EL8UENkraD3zsVxn4E5mtn0zrm7rqUMqBYyFpV1SNN_Qj8KdU22pwUYuvyztQC8uD3bfCjYaeHtwKLSINw8Lj_K92ikr5RVQGMS4MFnywkhf563mmccWPdMNH7-Iq2MUkunkSf6xUCmFB7qtCfZhuAED7AtCmNYlUpolrlvaNvRg-Bq2lvhdPkLUXu94E6z7Mkg-o7E2_6nknQuju2AuEIPY5qZ31M3iETSO-3-EHxIZwJbRwTUuuBNT2GLPWUkwNSjDnVq1Xf9XAK3i7K1TPA",
  "token_type": "bearer",
  "refresh_token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJib2IiLCJzY29wZSI6WyJvcGVuaWQiXSwiYXRpIjoiNGIwZTQ2MzgtZjI4Mi00YWJkLWE3ZDItMzY3N2ViNGE5ZjcwIiwiZXhwIjoxNDcxMzU2NDI0LCJhdXRob3JpdGllcyI6WyJST0xFX0RFVkVMT1BFUlMiXSwianRpIjoiMWQ1ZDllYmMtYTUzNS00NDA2LWEwOTItYjU5NTg0YTI4NjZkIiwiY2xpZW50X2lkIjoiYWNtZSJ9.aZp7v-dGq0_MC9cRwiQ9R4alV_Hq2bwTtzPoOhltIiM58QH_TTYU60_1hRFPoIsH5LQ_KdehQ7-7FWE9RBkacwilf0imhYQ6Wq8IwG4GMY74UUihTPidlBPLoC6USXuPS3ZhXm7kZz8lkRVF-AzC0qf5O2Qi8XVrEtRS7EJZwdTlPl_NoOdH-ZK5qYKqy9c395flsnX8JaNtKMmRPmyR_QnNT-UVxgkPSMHL8k0Cg1pk1c0kSYOdtG3mhOJkxrVp--PaKE41cbQ2y45KbDB5JhPnhEoW7f5xV1sDI5hnk1drrNN8-uM4j3-sqhGsSAQIHE7hCS29YjO6-qq-O43UuQ",
  "expires_in": 43199,
  "scope": "openid",
  "jti": "4b0e4638-f282-4abd-a7d2-3677eb4a9f70"
}


API Call

```bash
 curl --header "Authorization: Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE0Njg4MDc2MjQsInVzZXJfbmFtZSI6ImJvYiIsImF1dGhvcml0aWVzIjpbIlJPTEVfREVWRUxPUEVSUyJdLCJqdGkiOiI0YjBlNDYzOC1mMjgyLTRhYmQtYTdkMi0zNjc3ZWI0YTlmNzAiLCJjbGllbnRfaWQiOiJhY21lIiwic2NvcGUiOlsib3BlbmlkIl19.boNdBPxWNK-S3NSLLU6k6eaIKeNA0kN4VpAwF9-NcsOps9ItNPWQUrmPuBFnRzD0EL8UENkraD3zsVxn4E5mtn0zrm7rqUMqBYyFpV1SNN_Qj8KdU22pwUYuvyztQC8uD3bfCjYaeHtwKLSINw8Lj_K92ikr5RVQGMS4MFnywkhf563mmccWPdMNH7-Iq2MUkunkSf6xUCmFB7qtCfZhuAED7AtCmNYlUpolrlvaNvRg-Bq2lvhdPkLUXu94E6z7Mkg-o7E2_6nknQuju2AuEIPY5qZ31M3iETSO-3-EHxIZwJbRwTUuuBNT2GLPWUkwNSjDnVq1Xf9XAK3i7K1TPA" http://localhost:9000/surveys
 
 ```


All in One

```bash

	token=`curl -XPOST -k acme:acmesecret@localhost:9999/auth/oauth/token -d grant_type=password -d client_id=acme -d client_secret=acmesecret -d redirect_uri=http://localhost:9999/auth/user -d username=bob -d password=bobspassword | jq -cMCr ".access_token"`

	curl --header "Authorization: Bearer $token" http://localhost:9000/surveys

 ```


 
