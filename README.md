# Spring Shell 수도요금 조회 시스템

Spring Shell 기반의 CLI 수도요금 조회 애플리케이션입니다.  
로그인 인증 후 지자체별 업종별 수도 요금을 조회하고 사용량에 따른 청구 금액을 계산할 수 있습니다.

---

## 프로젝트 구조
```
src/main/java/com/nhnacademy/springshell/
├── aop/
│   ├── AccountAop.java         # 로그인/로그아웃 로깅 AOP
│   └── PriceAop.java           # 요금 조회 인증 및 로깅 AOP
├── command/
│   └── MyCommands.java         # Shell 커맨드 정의
├── domain/
│   ├── Account.java            # 계정 도메인
│   ├── Price.java              # 요금 도메인
│   └── WaterRate.java          # 수도요금 구간 도메인
├── exception/
│   ├── CityNotFoundException.java
│   ├── SectorNotFoundException.java
│   └── UserNotFoundException.java
├── formatter/
│   ├── OutputFormatter.java             # 출력 포맷 인터페이스
│   └── impl/
│       ├── EnglishOutputFormatter.java  # 영문 출력 (en 프로파일)
│       └── KoreanOutputFormatter.java   # 한글 출력 (ko 프로파일)
├── parser/
│   ├── AbstractDataParser.java          # 공통 파싱 로직
│   ├── DataParser.java                  # 파서 인터페이스
│   └── impl/
│       ├── CsvDataParser.java           # CSV 파서 (기본값)
│       └── JsonDataParser.java          # JSON 파서
└── service/
    ├── AuthenticationService.java       # 인증 서비스
    └── PriceService.java                # 요금 조회 서비스

src/main/resources/
├── application.properties
├── application-en.properties
├── application-ko.properties
├── logback-spring.xml
└── data/
    ├── account.csv / account.json
    └── Tariff.csv / Tariff.json
```

---

## 실행 방법

### 프로파일 설정

출력 언어와 파일 파서는 Spring 프로파일로 제어합니다.

| 프로파일 | 출력 언어 |
|----------|-----------|
| `ko`     | 한국어    |
| `en`     | 영어      |
```bash
# 한국어 출력으로 실행
./mvnw spring-boot:run -Dspring-boot.run.profiles=ko

# 영어 출력으로 실행
./mvnw spring-boot:run -Dspring-boot.run.profiles=en
```

### 데이터 파서 설정

`application.properties`의 `file.parser` 값으로 파서를 선택합니다.
```properties
# CSV 파서 사용 (기본값)
file.parser=csv

# JSON 파서 사용
file.parser=json
```

---

## 사용 가능한 커맨드

> ⚠️ `login` 및 `logout`을 제외한 모든 커맨드는 **로그인 후 사용** 가능합니다.

### 인증

| 커맨드 | 설명 | 예시 |
|--------|------|------|
| `login` | 아이디와 비밀번호로 로그인 | `login 1 1` |
| `logout` | 현재 세션 로그아웃 | `logout` |
| `current-user` | 현재 로그인된 사용자 정보 조회 | `current-user` |

### 요금 조회

| 커맨드 | 설명 | 예시 |
|--------|------|------|
| `city` | 전체 지자체 목록 조회 | `city` |
| `sector` | 특정 지자체의 업종 목록 조회 | `sector 광주시` |
| `price` | 특정 지자체 + 업종의 단가 조회 | `price 광주시 가정용` |
| `bill-total` | 사용량에 따른 청구 금액 계산 | `bill-total 광주시 가정용 15` |

---

## 출력 형식 예시

### 한국어 프로파일 (`ko`)
```
지자체명: 광주시, 업종: 가정용, 구간금액(원): 400, 총금액(원): 6000
```

### 영어 프로파일 (`en`)
```
city: 광주시, sector: 가정용, unit price(won): 400, bill total(won): 6000
```

---

## 로그

| 로그 파일 | 내용 |
|-----------|------|
| `./log/account.log` | 로그인 / 로그아웃 이력 |
| `./log/price.log`   | 요금 조회 커맨드 실행 이력 (사용자명, 메서드, 인자, 결과) |

로그 설정은 `logback-spring.xml`에서 관리됩니다.

---

## 데이터 파일

### account.csv / account.json
계정 정보를 담고 있으며 아이디, 비밀번호, 이름 컬럼으로 구성됩니다.

| 아이디 | 비밀번호 | 이름 |
|--------|----------|------|
| 1 | 1 | 선도형 |
| 2 | 2 | sando |
| ... | ... | ... |

### Tariff.csv / Tariff.json
지자체별 업종별 구간 요금 데이터로, 순번, 지자체명, 업종, 단계, 구간시작, 구간끝, 구간금액, 기본요금 컬럼으로 구성됩니다.  
현재 동두천시, 파주시, 광주시, 양주시 등 총 다수 지자체의 요금 데이터가 포함되어 있습니다.

---

## 기술 스택

- **Java 17+**
- **Spring Boot**
- **Spring Shell** - CLI 커맨드 처리
- **Spring AOP** - 인증 및 로깅 관점 분리
- **OpenCSV** - CSV 파싱
- **Jackson** - JSON 파싱
- **Lombok** - 보일러플레이트 코드 제거
- **Logback** - 파일 및 콘솔 로깅
