# Cost rules and resource log

## Cost rules

1. This project uses no EC2, RDS, NAT Gateway, Elastic IP or load balancer. These services bill by the hour even when idle.
2. Every CloudWatch log group keeps logs for 7 days, so storage costs stay near zero.
3. Every resource carries the `Project`, `Env` and `Owner` tags, so the teardown check can find it.
4. Billing is checked at the end of each working day.
5. The full teardown happens on Day 16, using the resource cost log below as the inventory.