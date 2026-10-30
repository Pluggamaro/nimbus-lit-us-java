#!/usr/bin/env bash
set -euo pipefail

# Run from the project root, wherever the script is called from
cd "$(dirname "$0")/.."

mvn -B -q -DskipTests package
mkdir -p build
cp target/nimbus-lit-us.jar build/lambda.jar
echo "built build/lambda.jar"