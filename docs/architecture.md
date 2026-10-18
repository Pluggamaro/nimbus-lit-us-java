# Nimbus-lit-us architecture

## Problem and users 
Developers and students who want a personal quick-capture backend without 
maintaining a server. Nimbus-lit-us saves short notes and exports them upon request.

Why cloud: we pay per request(OpEx), there is no server patch, and the service scales
to zero when not in use.

## MVP scope 
In scope:
- `POST /notes` creates a note of up to 280 characters
- `GET /notes` lists notes, newest first
- `GET /export` exposes a time-limited download link for all notes as JSON

Out of scope:
- User accounts and authentication
- A frontend
- A VPC, containers, or EC2 instances
- Multiple environments (dev only)

## Architecture 
  curl / client
        | HTTPS (TLS)
        v
  API Gateway (HTTP, public)
        |
        v
     Lambda                    
        |                       |
        v                        +---> S3(private bucket: export)
  DynamoDB (notes)                       |
        |                              presigned url on `/export` sent back 
        v                              to client via gateway back to client
  CloudWatch(alarms, metrics, logs)      |  
                                            |
  curl/ client <------Download link-----<|

  IAM roles guard every arrow. Region: one. Environment: dev only.
 
## Data flow
POST /notes.

The client sends a JSON body  containing  a `text` field. Lambda first checks the request size.
A body over 2048 bytes gets a 413 response and is never parsed. Lambda then checks that the body
is valid JSON, that `text` is present and is a string, that is not blank after trimming, and that
it is no longer than 280 characters. If any check fails, the client receives a 400 response with a 
short error message and nothing is stored. If all checks pass, Lambda generates a unique `note_id`,
records the creation time, and writes the note to DynamoDB. The client receives a 201 response 
containing the saved note, including the server-generated ID and timestamp.

GET /notes.

The request has no body. Lambda reads every note from DynamoDB, sorts them by creation time with the 
newest first, and returns them as a JSON array with a `200` response. If there are no notes, the 
response is an empty array with the same 200 status code, not a 404, because the collection exists
and is simply empty. If DynamoDB cannot be reached, the client receives a 500 response with the message
`internal error`, and the detials are written only to the CloudWatch logs.

GET /export.

The request has no body. Lambda reads all notes from DynamoDB, writes them as a JSON file to the private 
S3 bucket under a key that includes the timestamp, such as `exports/notes-<timestamp>.json`, and generates
a presigned URL for that file valid for 300 seconds. The client receives a 200 response containing the URL
and the expiry time in seconds. The client receives a 200 response containing the URL and the expiry time 
in seconds. The client then downloads the file directly from S3 using that URL. Lambda never sends the file
contents itself. After the link expires, it stops working, and the lifecycle rule deletes the file after 7 days.

## Trust boundaries 

Each arow in the architecture diagram is a trust boundary. At each one, the receiving side decides whether
to trust the caller.

**Internet to API Gateway.**

The endpoint is public, and this MVP has no authentication. The protections at this boundary are HTTPS, which
encrypts traffic in transit, input validation in the Lambda code (size, shape, and length), and throttling on
the stage, set to a rate of 2 requests per second with a burst of 5.

**API Gateway to Lambda.**

A resource-based policy on the function grants API Gateway permission to invoke it. This controls who may call
the function. It does not control what the function may do.

**Lambda to DynamoDB and S3.** 

The Lambda execution role governs these actions. Lambda holds no access keys. It receives temporary credentials 
from the role. The role allows only `dynamodb:PutItem` and `dynamodb:Scan` on the notes table, and `s3:PutObject` 
and `s3:GetObject` on the `exports/` prefix.

**Client to S3.** 

The export bucket blocks all public access. The only way to read an export is a presigned URL, which grants access 
to one object for 300 seconds. The URL carries the permissions of the role that signed it, so it can do no more than 
that role can.

**Accepted risk.** 

Because the API has no authentication, anyone who can reach the endpoint can read every note through `GET /notes` and 
`GET /export`. Notes in this MVP are therefore not private. Adding authentication, such as Cognito or IAM auth on the 
API, is the first production change, listed under Known risks in `docs/security.md`.

## Service rationale
## Environment strategy