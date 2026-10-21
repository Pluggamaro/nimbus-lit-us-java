# AWS services and account conventions 

## Region

The project runs in `af-south-1` (Cape Town). Every service used here is available in that region, so no service has to be replaced or left out.

An Availability Zone (AZ) is a separate data centre inside a region. Each region has several, connected by fast links but physically apart, so a fault in one does not stop the others. Lambda, DynamoDB and S3 Standard store copies across multiple AZs by default, so availability within the region comes without extra configuration.

The region is a single point of failure for this MVP. A whole-region outage would stop the service. That risk is recorded in the architecture document and is listed as a production improvement.


## Naming convention 

## Tagging convention 

## Shared responsibility