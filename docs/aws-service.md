# AWS services and account conventions 

## Region

The project runs in `eu-west-1` (Ireland). Every service used here is available in that region, so no service has to be replaced or left out.
In this account, the region has three Availability Zones, `eu-west-1a`, `eu-west-1b` and `eu-west-1c`. Zone letters are assigned per account, so the stable identifiers are the zone IDs, `euw1-az1` to `euw1-az3`.

An Availability Zone (AZ) is a separate data centre inside a region. Each region has several, connected by fast links but physically apart, so a fault in one does not stop the others. Lambda, DynamoDB and S3 Standard store copies across multiple AZs by default, so availability within the region comes without extra configuration.

The region is a single point of failure for this MVP. A whole-region outage would stop the service. That risk is recorded in the architecture document and is listed as a production improvement.

## Naming convention 

Resources are named `nimbus-<thing>-<env>`, where `<env>` is `dev` for this project. Examples: `nimbus-notes-dev` for the table, `nimbus-notes-handler-dev` for the Lambda function, `nimbus-lambda-role-dev` for the execution role.

S3 bucket names are global across all AWS accounts, so the export bucket adds a unique suffix, for example `nimbus-exports-dev-<initials><digits>`.

## Tagging convention 

Every resource carries these tags:

- `Project=nimbus-notes` identifies the project that owns the resource.
- `Env=dev` identifies the environment.
- `Owner=<your-handle>` identifies the person responsible for the resource.

Tags are applied when each resource is created. Untagged resources are missed by the teardown check and by cost reporting.

## Shared responsibility

AWS secures the cloud: the data centres, the hardware, and the software inside managed services such as Lambda, DynamoDB and S3.

This project secures what it puts in the cloud: who can access each resource (IAM roles and policies), the data stored in the table and bucket, whether the bucket stays private, and how each service is configured.

For example, AWS keeps the DynamoDB servers patched. This project decides whether the notes table can be read by anyone, and it keeps the export bucket private.

## IAM

The dev user `nimbus-dev` is a member of the `nimbus-admins` group, which has AdministratorAccess. This is a learning choice: the single user needs to create and delete every resource in the project. Production would grant only the specific actions each workload needs.

Learning credentials are a long-lived access key stored in `~/.aws/`, outside the repository. Production would use IAM Identity Center with `aws configure sso`, which issues short-lived credentials, so no long-lived key exists on any machine. The `nimbus-dev` user has MFA assigned to its console sign-in.