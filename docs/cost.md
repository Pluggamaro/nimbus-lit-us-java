# Cost rules and resource log

## Cost rules

1. This project uses no EC2, RDS, NAT Gateway, Elastic IP or load balancer. These services bill by the hour even when idle.
2. Every CloudWatch log group keeps logs for 7 days, so storage costs stay near zero.
3. Every resource carries the `Project`, `Env` and `Owner` tags, so the teardown check can find it.
4. Billing is checked at the end of each working day.
5. The full teardown happens on Day 16, using the resource cost log below as the inventory.

## Budget

A monthly cost budget of 5 USD, with email alerts to the project owner's address:

- At 80% of actual spend (4 USD). This means spending has already happened and needs attention.
- At 100% of forecasted spend (5 USD). This means spending is on course to reach the limit by month end.

A budget sends alerts only. It does not stop charges, so the alerts are the control, and the cost rules above are how the project avoids reaching them.

## Resource cost log

Each AWS ticket adds one row for every resource it creates. The teardown uses this table as its inventory.

| Resource | Created in | Cost note | How to delete |

| IAM user `nimbus-dev`, group `nimbus-admins`, one access key | IAM console, AWS-007 | IAM is free | IAM > Users > nimbus-dev > delete the access key, then delete the user, then delete the group |