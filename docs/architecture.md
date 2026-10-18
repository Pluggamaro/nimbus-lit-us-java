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
short error message and nothing is stored.

## Trust boundaries 
## Service rationale
## Environment strategy