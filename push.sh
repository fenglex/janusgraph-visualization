#!/bin/bash
docker build -t fenglex/janusgraph-visualization:$1 .
docker tag fenglex/janusgraph-visualization:$1 fenglex/janusgraph-visualization:latest
docker push fenglex/janusgraph-visualization:$1
docker push fenglex/janusgraph-visualization:latest
