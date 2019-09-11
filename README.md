# janusgraph-visualization

A simple tool for janusgraph visualization.

![Alt text](img/FireShot.png)

View janusgraph with tools.

How to run ?

Download release jar file
```$xslt
nohup java -jar janusgraph-visualization-x.x.x.jar >/dev/null 2>&1&
```

Then browser open http://localhost:8888

If you want to modify the port
```$xslt
java -jar janusgraph-visualization-x.x.x.jar --server.port=80
```

