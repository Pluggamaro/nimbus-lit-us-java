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
## Data flow
## Trust boundaries 
## Service rationale
## Environment strategy