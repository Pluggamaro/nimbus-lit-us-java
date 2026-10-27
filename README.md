# Elective Tracker : WTC-RCRRUA7C

# nimbus-lit-us-java
A Secure, Test-Driven Serverless Application on AWS. It stores short notes in DynamoDB and exports them to a private S3 bucket through a time-limited download link.

Status: in progress. Foundation, documentation, IAM identity and the private S3 bucket are complete. The Lambda function, API Gateway routes and DynamoDB table are next.

## About Nimbus-lit-us
Nimbus Notes is a learning project for my Cloud Elective. It is built to demonstrate cloud fundamentals in practice: managed services, least-privilege access, 
test-driven development, and cost control. The scope is deliberately small. See [MVP scope](docs/architecture.md#mvp-scope) for what is in and out.

## Planned API

| Method | Route | Purpose |
|---|---|---|
| POST | `/notes` | Create a note of up to 280 characters |
| GET | `/notes` | List notes, newest first |
| GET | `/export` | Write all notes to S3 and return a 5-minute download link |

## Architecture

client (curl)
| HTTPS (TLS)
v
API Gateway (HTTP API, public)
|
v
Lambda (Java 17)
| |
| notes | export: write JSON file
v v
DynamoDB (notes) S3 (private bucket: exports/)

Client downloads an export directly from S3 with a presigned URL.
API Gateway, Lambda, DynamoDB and S3 send logs and metrics to CloudWatch.

The full design, including data flow, trust boundaries and the reasoning behind each service, is in [docs/architecture.md](docs/architecture.md).

## Cloud concepts in this project

- **Serverless compute:** Lambda runs code only when a request arrives, so idle time costs nothing.
- **Managed storage:** DynamoDB and S3 remove the need to run database or file servers.
- **Least privilege:** IAM roles grant each component only the actions it needs, and no access keys are stored in code.
- **Private by default:** the export bucket blocks all public access, and downloads use short-lived presigned URLs.
- **Regions and Availability Zones:** the project runs in `eu-west-1`, where managed services spread copies across three zones.
- **Cost control:** a monthly budget with alerts, pay-per-use services only, and a resource log that records how to delete everything.

## Repository layout

docs/ Architecture, AWS conventions, cost rules
src/main/java Application code (nimbus package)
src/test/java Local tests and AWS verification tests
.github/ CI workflow


## Prerequisites

- JDK 17 and Maven 3.9 or later
- AWS CLI v2
- An AWS account with a configured `nimbus` profile (see [AWS services and conventions](docs/aws-services.md))

## Quick start

Run the local test suite. It needs no AWS account:

```bash
mvn -B test
```

## Running the AWS tests

These tests call real AWS resources, so they are excluded from the default build. They need the variables from `.env.example` set in your shell:

```bash
set -a; source .env; set +a
mvn -B test -Dgroups=aws -DexcludedGroups=none
```

## Workflow
feature branch -> commits -> pull request -> merge to main

## Commit style used in this project 
chore: ..., feat: ..., test: ..., docs: ..., fix: ....

## Work on a branch 
git checkout -b chore/repo-foundation, commit, push, open a PR, merge.

## Security
-No credentials exist in repo, scanner tests exist.
-Known risks are recorded in the architecture document. The API has no authentication yet, so anyone who can reach it can read notes. This is the first production change listed.

## Cost

The project uses only pay-per-use services, with no EC2, NAT Gateway or load balancer. Every resource is recorded in [docs/cost.md](docs/cost.md) with how to delete it.

## Documentation

- [Architecture](docs/architecture.md): problem, scope, design, trust boundaries, production improvements
- [AWS services and conventions](docs/aws-services.md): region, naming, tagging, IAM
- [Cost rules and resource log](docs/cost.md): budget, cost rules, teardown inventory

## Demo video

Coming soon.

## What I would improve

See the Production improvements section of [docs/architecture.md](docs/architecture.md). The main items are authentication, alarm notifications, a query-friendly key design for notes, separate accounts per environment, and a deployment pipeline that uses OIDC instead of stored keys.

