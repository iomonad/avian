<p align="center">
  <img src="https://raw.githubusercontent.com/iomonad/avian/master/project/artworks/banner.jpg"/><br>
  <b>AVIAN</b><br>
  <a href='https://travis-ci.org/iomonad/avian'>
    <img src='https://travis-ci.org/iomonad/avian.svg?branch=master' alt='Build status'/>
  </a>
  <a href='https://coveralls.io/github/iomonad/avian?branch=master'>
    <img src='https://coveralls.io/repos/github/iomonad/avian/badge.svg?branch=master' alt='Coverage Status' />
  </a><br>
  <u>Web crawler for personal data mining project.</u><br><br>
  <img src="https://raw.githubusercontent.com/iomonad/avian/master/project/avian-akka.png"/><br>
</p>

# Environment setup
- Message Broker: Apache Kafka
```bash
sudo docker run -p 2181:2181 -p 9092:9092 \
    --env ADVERTISED_HOST=`docker-machine ip \`docker-machine active\`` \ 
    --env ADVERTISED_PORT=9092 spotify/kafka
```

```bash
sudo docker run -p 2181:2181 -p 9092:9092 \
    --env ADVERTISED_HOST=`boot2docker ip` \
    --env ADVERTISED_PORT=9092 \
    --env CONSUMER_THREADS=1 \
    --env TOPICS=my-topic,some-other-topic \
    --env ZK_CONNECT=kafka7zookeeper:2181/root/path \
    --env GROUP_ID=mymirror \
    spotify/kafkaproxy
```
- Database: Apache Cassandra
```bash
sudo docker run --name some-cassandra \
    -d -e CASSANDRA_BROADCAST_ADDRESS=10.42.42.42 \
    -p 7000:7000 cassandra:latest
```

```bash
sudo docker run --name some-cassandra -d \
    -e CASSANDRA_BROADCAST_ADDRESS=10.43.43.43 -p 7000:7000 \
    -e CASSANDRA_SEEDS=10.42.42.42 cassandra:latest
```

# Running crawler
- Execute test suite

```bash
sbt test
```
- Update your application.conf file
- Build and run

```bash 
sbt run
```
