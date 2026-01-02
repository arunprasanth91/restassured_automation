API (Application Programming Interface) - Intermediatory software or Interface communicates data between frontend and backend applications.

REST APIs (Representational State Transfer) - Lightweight protocol, flexible. 
BASE URI - basepath of the URL 
Endpoint - Address where API is hosted on the server.

HTTP methods: CRUD operations 
GET - used to retrieve information from the server.
POST - used to create data to the server. 
PUT - replace (update) the existing resource on the server. 
DELETE -  delete the resource from the server.
PATCH - Unlike PUT, it requires sending only specific data fields that needs updating not the entire resource representation.
HEAD - similar to GET, but retrieves only response HEADERS and no body content.

Resources - Represent API/collection which can be accessed from the server.
Eg:
Google.com/maps
Google.com/search
Google.com/mail

Parameters
PATH - are variable parts of a URL path. used to point specific resource within collection 
eg: https://www.google.com/images/123423
https://www.amazon.com/orders/112
QUERY - Used to sort, filter the resources. identified with "?"
eg: https://www.amazon.com/orders?sort_by=20/2/2025
https://www.google.com/search?q=different+http+methods&oq=different+HTTP+methods&gs_lcrp=EgRlZGdlKgcIABAAGIAEMgcIABAAGIAEMgcIARAAGIAEMgcIAhAAGIAEMggIAxAAGBYYHjIICAQQABgWGB4yCAgFEAAYFhgeMggIBhAAGBYYHjIICAcQABgWGB7SAQg0MzEwajBqMagCALACAA&sourceid=chrome&ie=UTF-8

BASEURL/RESOURCE/(QUERYORPATH)PARAMETERS.

Headers: Meta data (additional details) associated with the API request and response.
eg: authorization (username/password) or api cookies, content type. 

RequestSpecBuilder & ResponseSpecBuilder - to segregate common re-usable code. 
RequestLoggingFilter & ResponseLoggingFilter - will log all the request and response logs used with RequestSpec

API Authorization - bearer token using token id (passed in header) 
                    OAUTH using auth type as USERNAME&PASSWORD or CLIENT_ID & CLIENT_SECRET (passed as form param)

Serialization - converting java object to request/response payload.
DeSerialization - Converting request/response payload to java object.

to avoid SSL certification exceptions - we can use RestAssured method "relaxedHTTPSValidation()"



