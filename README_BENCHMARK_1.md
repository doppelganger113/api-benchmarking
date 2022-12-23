# HTTP load testing with database

This benchmark will consist of testing the interaction of the service with Postgresql database 
when concurrency starts to increase with presence of I/O waits.

We will be using [Vegeta](https://github.com/tsenart/vegeta) tool for this test.

## Docker images sizes

- spring-todo 284MB
- spring-reactive-todo 267MB
- fastify-nodejs-todo 166MB
- nodejs-todo 159MB
- go-todo 26MB

Following scenarios will be tested:

2. High latency in **I/O** that tests reliability by setting transform=true, size=50 and sleep
    1. Sleep 500
        1. Rate 300
        2. Rate 500
    2. Sleep 1000
        1. Rate 300
        2. Rate 500
    3. Sleep 2000
        1. Rate 300
        2. Rate 500

```bash
echo "GET http://localhost:3040/api/todos?transform=true&sleep=0&size=100&skipData=false" | vegeta attack -name=spring_reactive_100qps -duration=10s -rate 100 | tee results.spring_reactive.bin | vegeta report
# Generate report from results
vegeta report -type=json results.bin > metrics.json
# Generate plot graph from results
cat results.bin | vegeta plot > plot.html
# Generate histogram from results
cat results.bin | vegeta report -type="hist[0,500ms,2000ms,5000ms,20000ms]"
cat results.bin | vegeta report -type=hdrplot > hdrplot.hgrm

# Generate combined plot
BENCH_SUFFIX="transform_true_sleep_0_size_1000_rate_50"
vegeta plot \
  output/fastify_${BENCH_SUFFIX}/results.bin \
  output/nestjs_${BENCH_SUFFIX}/results.bin \
  output/go_${BENCH_SUFFIX}/results.bin \
  output/spring_${BENCH_SUFFIX}/results.bin \
  output/spring_reactive_${BENCH_SUFFIX}/results.bin \
  > ${BENCH_SUFFIX}.plot.html 
```

If you want to change plot colors, search in the generated html for:

```
"colors": [
```

Here you can change the HEX values of colors to anything you want, suggested colors for diversity:

To replace:

```
# Greens
"#E9D758",
"#297373",
"#39393A",
"#A1CDF4",
"#593C8F",
"#171738",
"#A1674A",

# Reds
"#EE7860",
"#DD624E",
"#CA4E3E",
"#B63A30",
"#9F2823",
"#881618",
"#6F050E",
```

To replace with

```
"#593C8F",
"#539bf5",
"#E9D758",
"#297373",
"#39393A",
"#488A3A",
"#A1CDF4",
"#593C8F",
"#171738",
"#053E0A",
"#A1674A",
```

## HDR Histogram plot

https://hdrhistogram.github.io/HdrHistogram/plotFiles.html
