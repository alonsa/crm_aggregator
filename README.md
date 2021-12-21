##Running the microservices\
1. Run the RepositoryServer.class
2. Run the WebServer.class
3. Run the DemoCrmServer.class (in order to ger demo data)
4. Run the ׳CollectorServer.class


##Usage
###Get a report
Method: GET\
Path: <host>:8080/case/case\
Params:\
1. status(OPEN/CLOSED) - optional
2. providerName(<string>) - optional
3. errorCode(<string>) - optional
4. Response: application/json\
[{\
"provider": "<provider name>",\
"errorCode": "<error code>",\
"productsEffected": ["effected product"],\
"supportCases": ["{support cases json}"],\
"supportCaseNumber": 1\
}]\

###Refresh data a report\
Method: PUT\
Path: <host>:8080/case/aggregate/all
