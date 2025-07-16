This is still not done for /v1/users/{userId}:
'400':
description: The request didn't supply all the necessary data
content:
application/json:
schema:
$ref: "#/components/schemas/BadRequestErrorResponse"