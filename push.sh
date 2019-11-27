#!/bin/bash
docker build -t fenglex/janusgrpah-visualization:$1 .
docker tag janusgrpah-visualization:$1 fenglex/janusgrpah-visualization:latest
docker push fenglex/janusgrpah-visualization:$1
docker push fenglex/janusgrpah-visualization:latest