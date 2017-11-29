# EnterpiseSystemsDevelopment

Project files in ESD_assignment 

Web service files in ClaimsWebService

How to run using NetBeans with Java EE:

- download project files
- download web service files
- create Derby database ESDDB with credentials root/password
- run XYZ_AssocDerby.sql
- populate the database with an admin account
- using GlassFish (localhost:4848), deploy ESD_assignment.war and ClaimsWebService.war
- if 404 issues arise when doing claims related tasks, re-deploy the web service, if issues persist, in NetBeans, go to tools -> options -> under proxy settings select 'No proxy'

NOTE: 

If address lookup fails, the API key may have expired. To get a new key go to https://getaddress.io/ and follow the steps, then in AdrressAPI.java update the field 'private final String key = ""'


