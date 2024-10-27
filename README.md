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
