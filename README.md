kot-web-demo
============

Servlet/SpringMVC demo web application written mostly in Kotlin


## Hints for Intellij

* Set working dir to kot-website dir

## Pages


Static: ``http://127.0.0.1:8080/index.html``
Freemarker: ``http://127.0.0.1:8080/hello.html``

## Start as a server

```
java -jar kot.jar
```

It takes ~400-800 msec to get page for the first request on micro instance [1] with java 7 [2].
Subsequent requests are handled much faster:

```
$ ab -n 1000 -c 20 http://<<MyIP>>.us-west-2.compute.amazonaws.com:8080/hello.html

Server Software:        Jetty(8.y.z-SNAPSHOT)
Server Hostname:        ec2-54-148-119-157.us-west-2.compute.amazonaws.com
Server Port:            8080

Document Path:          /hello.html
Document Length:        1004 bytes

Concurrency Level:      20
Time taken for tests:   22.800 seconds
Complete requests:      1000
Failed requests:        0
Write errors:           0
Total transferred:      1123000 bytes
HTML transferred:       1004000 bytes
Requests per second:    43.86 [#/sec] (mean)
Time per request:       456.008 [ms] (mean)
Time per request:       22.800 [ms] (mean, across all concurrent requests)
Transfer rate:          48.10 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:       21  127  80.5    116    1148
Processing:    74  325 102.3    321    1301
Waiting:       74  324 102.3    320    1301
Total:        224  452 101.4    442    1445

Percentage of the requests served within a certain time (ms)
  50%    442
  66%    478
  75%    504
  80%    522
  90%    574
  95%    628
  98%    670
  99%    698
 100%   1445
```

With aggressive opts:

```
java -server -XX:+AggressiveOpts -jar kot.jar
```

It takes ~1000-1600 msec on the same micro instance [1] to get /hello.html for the very first request.

Subsequent requests:

```
$ ab -n 1000 -c 20 http://<<MyIP>>.us-west-2.compute.amazonaws.com:8080/hello.html

Server Software:        Jetty(8.y.z-SNAPSHOT)
Server Hostname:        ec2-54-148-119-157.us-west-2.compute.amazonaws.com
Server Port:            8080

Document Path:          /hello.html
Document Length:        1004 bytes

Concurrency Level:      20
Time taken for tests:   22.357 seconds
Complete requests:      1000
Failed requests:        0
Write errors:           0
Total transferred:      1127492 bytes
HTML transferred:       1008016 bytes
Requests per second:    44.73 [#/sec] (mean)
Time per request:       447.145 [ms] (mean)
Time per request:       22.357 [ms] (mean, across all concurrent requests)
Transfer rate:          49.25 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:       22  198  81.3    202     473
Processing:    33  246 117.1    227    1015
Waiting:       33  245 117.0    226    1015
Total:        199  444 111.3    432    1113

Percentage of the requests served within a certain time (ms)
  50%    432
  66%    474
  75%    500
  80%    516
  90%    581
  95%    642
  98%    715
  99%    799
 100%   1113 (longest request)
```

Interestingly that latency p99 with ``-XX:+AggressiveOpts`` is actually *bigger* than without it, p50 and p90 are really
close for both versions.

## References

[1] Amazon EC2 t1.micro instance with 32-bit Ubuntu Server.

[2] Java version:

```
$ java -version
java version "1.7.0_65"
OpenJDK Runtime Environment (IcedTea 2.5.3) (7u71-2.5.3-0ubuntu0.14.04.1)
OpenJDK Client VM (build 24.65-b04, mixed mode, sharing)
```