# Retrieve Authentication Types Status REST Service


**1. Background**

Retrieve Authentication Types Status REST Service can be used by Resident Services Module in MOSIP to retrieve status (locked or unlocked) of Auth Types of an Individual using VID/UIN.


 ***1.1.Target users -***  
Resident Services module in MOSIP can send request to get lock or unlock status of authenticate types of an Individual using VID/UIN.


 ***1.2. Key requirements -***   
-	Resident Services portal will send Individual’s UIN/VID to get the authentication transactions history.
-	Check Individual’s UIN/VID for authenticity and validity
-	Validate the request format and if it contains only the supported auth types to lock/unlock.
-	Respond with status `true` of the locking/unlocking of auth types is successfull, otherwise, return error response.

 ***1.3. Key non-functional requirements -***   
-	Logging :
	-	Log each stage of authentication process
	-	Log all the exceptions along with error code and short error message
	-	As a security measure, Individual’s UIN or PI/PA should not be logged
-	Audit :
	-	Audit all transaction details during authentication process in database
	-	Individual’s UIN or PI/PA details should not be audited
	-	Audit any invalid UIN or VID incidents
-	Exception :
	-	Any failure in authentication/authorization of Partner and validation of UIN and VID needs to be handled with appropriate error code and message in Auth Response
	-	Any error in Individual authentication also should be handled with appropriate error code and message in Auth Response 
-	Security :


**2. Solution**   
ID  Authentication Types Lock/Unlock REST Service addresses the above requirements as explained below. 

1.	Resident Services portal to construct a GET request Request URL `GET /idauthentication/v1/internal/authtypes/status/individualIdType/:IDType/individualId/:ID` .     
Sample Request URL:
`https://mosip.io/idauthentication/v1/internal/authtypes/status/individualIdType/VID/individualId/9830872690593682`

2.	Authenticate the request based on the authentication token. Authroize the request based on LDAP roles.
3.	Integrate with kernel UIN Validator and VID Validator to check UIN/VID for validity. Validate UIN/VID for authenticity and active status using ID Repo service.
7.	Retrive the lock/unlock status of all Authentication Types in the Auth Database.
8.	Send response as below - 
```JSON
{
  "id": "mosip.identity.authtype.status.read",
  "version": "1.0",
  "requestTime": "2019-02-15T10:01:57.086+05:30",
  "individualId": "9830872690593682",
  "individualIdType": "VID",
  "request": {
    // Status of AuthTypes and AuthSubTypes
    "authTypes": [
      {
        "authType": "otp",
        "isLocked": false
      },
      {
        "authType": "demo",
        "isLocked": false
      },
      {
        "authType": "bio",
        "authSubType": "FMR",
        "isLocked": true
      },
      {
        "authType": "bio",
        "authSubType": "FIR",
        "isLocked": true
      },
      {
        "authType": "bio",
        "authSubType": "IIR",
        "isLocked": true
      },
      {
        "authType": "bio",
        "authSubType": "FID",
        "isLocked": true
      }
    ]
  }
}
```

**2.1. Class Diagram:**   
The below class diagram shows relationship between all the classes which are required for this service.

![Auth Transactions Class Diagram](_images/Auth_Type_Status_Retrieve_Design-Class_Diagram.png)

**2.2. Sequence Diagram:**   
![Auth Transactions Sequence Diagram](_images/Auth_Type_Status_Retrieve_Design-Sequence_Diagram.png)

**3. Proxy Implementations -**   
Below are the proxy implementations used in ID-Authentication:
- ***Digital Signature in request*** - Any digital signature added in the Auth request is not currently validated .
- ***Digital Signature in response*** - The Auth response is digitally signed using the MOSIP private key with reference ID **SIGN** and application ID **KERNEL**, and public key of the same should be used to verify the signature. 
