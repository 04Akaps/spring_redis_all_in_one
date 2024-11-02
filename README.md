<div align="center">

</br>
<!-- logo -->

### ✅ Redis Use Case ✅

</div>

## 📝 소개

실무에서 사용하는 Redis를 Spring으로 구현하고 사용하는 프로젝트 입니다.
Distributed Lock 및 일반적인 Collection을 먼저 다루고 있고, 해당 부분을 API로써 관리를 하고 있습니다.

추후 시간이 된다면, Pub/Sub 및 Stream 형태도 함께 다룰 예정입니다.

## 🗂️ 제공하는 Redis

1️⃣ 일반적인 String

2️⃣ Sorted Set 및 Set

3️⃣ List

4️⃣ Bits

5️⃣ Hashes

6️⃣ Strategy [ 작업 예정 ]

> PER 알고리즘을 사용하는 형태로 최대한 Cache Hit을 유도하는 전략을 사용 할 예정

## ⚙ 기술 스택

> module에서 사용하는 디펜던씨를 정리합니다.

`gson` : json의 직렬화에 대해서 사용하였습니다.

`redis` : InMemory로 사용되었습니다.

`spring-boot-starter-web` : API 구성을 위해 사용되었습니다.

`swagger` : API 제공을 위해 사용되었습니다.

`lombok` : 코드 개선을 위해 사용되엇습니다.

## ⚙ Redis 운영하기

해당 프로젝트에서 사용하는 Redis는 직접 운영을 하며 테스트가 진행이 되었습니다.

1️⃣ `brew install redis` 를 통해서 기본적인 `redis` 를 설치

</br>

2️⃣ `/opt/homebrew/etc` 경로를 통해서 `redis-server` 커맨드를 활용해 직접 운영을 진행한다.

> `redis-server redis.conf`를 통해서 가장 기본적으로 실행 가능하다.

</br>

3️⃣ Slave 노드도 운영하고 싶다면, 간단하세 다음과 같은 `conf`를 추가로 활용하며 Slave 노드를 활용하자.

```
replicaof 127.0.0.1 <마스터 노드 Port>

repl-ping-replica-period 10

repl-timeout 60

port <Slave 노드 Port>
```

</br>

4️⃣ 해당 과정에서 `locale` 설정에 문제가 있을 수가 있다. 해당 문제는 `locale` 설정이 기본적으로 `LANG="ko_KR.UTF-8"` 로 설정이 되어 있는 경우 `redis`가 정상적으로 값을 판단하지 못한다.

```
export LANG=ex_US.UTF8
export LC_ALL=en_US.UTF-8

source ~/.bashrc
```

해당 명령어를 통해서 간단하게 `locale` 변경하여 해결 가능하다.

</br>

5️⃣ Sentinel 기능 까지 제공하고 싶다면, 추가적인 conf 수정이 필요하다.

<div align="center">

<img width="1239" alt="스크린샷 2024-11-02 오후 10 04 54" src="https://github.com/user-attachments/assets/23d94405-9410-42d1-81e3-e4724848210d">

</div>
