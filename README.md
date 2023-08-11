# RideShare-Server
## 목차
- [실행방법](#실행방법)
- [데이터베이스 초기설정 및 테스트 데이터 주입](#데이터베이스-초기설정-및-테스트-데이터-주입)
- [인텔리제이 초기설정](#인텔리제이-초기설정)
- [ERD](#erd)
- [REST API Guide](#rest-api-guide)
  * 파티 관련
    - [택시/카풀 총 개수 조회](#택시카풀-총-개수-조회)
    - [택시/카풀 리스트 반환 및 검색](#택시카풀-리스트-반환-및-검색)
    - [택시/카풀 ID로 조회](#택시카풀-ID로-조회)
    - [파티 등록](#파티-등록)
    - [파티 삭제](#파티-삭제)
    - [파티 수정](#파티-수정)
    - [파티 현재 인원 수 1 증가시키기](#파티-현재-인원-수-1-증가시키기)
    - [파티 확정 완료하기](#파티-확정-완료하기)
    - [파티 종료하기](#파티-종료하기)
  * 멤버 관련
    - [아이디/이메일/닉네임 중복 확인](#아이디이메일닉네임-중복-확인)
    - [회원가입](#회원가입)
    - [로그인](#로그인)
    - [토큰 재발급](#토큰-재발급)
    - [마이페이지](#마이페이지)
    - [로그아웃](#로그아웃)
    - [작성글 내역 조회](#작성글-내역-조회)
    - [파티 참여내역 조회](#파티-참여내역-조회)
    - [닉네임 변경](#닉네임-변경)
    - [비밀번호 변경](#비밀번호-변경)
## 실행방법
[https://jojelly.tistory.com/86](https://jojelly.tistory.com/86)
## 데이터베이스 초기설정 및 테스트 데이터 주입
프로젝트의 `Database` 폴더 -> `dbinit.sql` 파일의 SQL 문장을 **MySQL Workbench**에서 모두 실행해주세요.
## 인텔리제이 초기설정
1. file->settings->Build, Execution, Deployment-> Compiler -> Annotation Processors에서 enable annotation processing를 체크표시 해주세요.
   ![캡처](https://user-images.githubusercontent.com/70526479/229042932-646348cf-5152-410a-bdce-9fdc85a1b695.PNG)
2. RideshareApplication 파일의 main() 메서드를 실행하면 됩니다.<br>
   ![캡처2](https://user-images.githubusercontent.com/70526479/229047097-cf8ed5c7-6415-4326-bfbe-928179b7b0c3.PNG)
## ERD
![erd](https://github.com/KNUCSE23-Capston-Design/RideShare-Server/assets/70526479/75583b02-58f3-4c0b-893f-09b9e3d97d88)
## REST API Guide
### 택시/카풀 총 개수 조회
- **택시/카풀 리스트의 총 아이템 개수를 반환합니다.**
```http
GET /parties/count
```
**성공**: 200 OK <br><br>
**실패**:
|Code|Message|Description|
|------|---|---|
|`401`|`Access Token이 만료되었습니다.`|사용자의 Access Token이 만료되었거나 유효하지 않은 경우|

**요청 헤더**
|Name|Description|
|---|---|
|`Authorization`|`Bearer` + `JWT Access Token`|

**요청 파라미터**
|필수|Params|Type|Description|
|:---:|------|---|---|
|O|`type`|`String`| `"택시"` or `"카풀"`|

**요청 예시**
```http
GET /parties/count?type=택시
```
```http header
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI0IiwiYXV0aCI6IlJPT...(이하 생략)
```

**응답 예시(TEXT)**
```text
20
```
- - -
### 택시/카풀 리스트 반환 및 검색
- **lastId 보다 작은 값의 레코드들을 amount개 반환합니다.**
- **keyword 필드를 추가하면 원하는 출발지를 검색할 수 있습니다.**
```http
GET /parties
```
**성공**: 200 OK <br>
**실패**:
|Code|Message|Description|
|------|---|---|
|`401`|`Access Token이 만료되었습니다.`|사용자의 Access Token이 만료되었거나 유효하지 않은 경우|

**요청 헤더**
|Name|Description|
|---|---|
|`Authorization`|`Bearer` + `JWT Access Token`|

**요청 파라미터**
|필수|Params|Type|Description|
|:---:|------|---|---|
|X|`lastId`|`Integer`| 마지막으로 호출 된 id (미입력시 최근 id부터 출력)|
|O|`amount`|`Integer`| 한번에 호출 할 레코드 개수|
|O|`type`|`String`| `"택시"` or `"카풀"`|
|X|`keyword`|`String`| 검색할 키워드(출발지) (미입력시 모두 출력)|

**요청 예시**
```http
GET /parties?lastId=41&amount=3&type=카풀
```
```http header
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI0IiwiYXV0aCI6IlJPT...(이하 생략)
```

**응답 예시(JSON)**
```json
[
  {
    "type": "카풀",
    "startDate": "2023-05-20",
    "startTime": "오전 11:20",
    "startPoint": "남춘천역",
    "startLat": "37.86369763697937",
    "startLng": "127.72376542374549",
    "endPoint": "동문",
    "currentHeadcnt": 1,
    "totalHeadcnt": 4,
    "isConfirm": false,
    "isFinish": false,
    "carNumber": "237더1028",
    "content": "컨텐츠내용 테스트 예제",
    "people": [
      "test5_nick",
      "test1_nick",
      "test2_nick",
      "test3_nick"
    ],
    "pid": 40
  },
  {
    "type": "카풀",
    "startDate": "2023-05-19",
    "startTime": "오전 11:20",
    "startPoint": "남춘천역",
    "startLat": "37.86369763697937",
    "startLng": "127.72376542374549",
    "endPoint": "미래도서관",
    "currentHeadcnt": 1,
    "totalHeadcnt": 4,
    "isConfirm": false,
    "isFinish": false,
    "carNumber": "23우1283",
    "content": "컨텐츠내용 테스트 예제",
    "people": [
      "test4_nick"
    ],
    "pid": 39
  },
  {
    "type": "카풀",
    "startDate": "2023-05-18",
    "startTime": "오전 11:20",
    "startPoint": "남춘천역",
    "startLat": "37.86369763697937",
    "startLng": "127.72376542374549",
    "endPoint": "강원대후문",
    "currentHeadcnt": 4,
    "totalHeadcnt": 4,
    "isConfirm": true,
    "isFinish": false,
    "carNumber": "48가9125",
    "content": "컨텐츠내용 테스트 예제",
    "people": [
      "test3_nick"
    ],
    "pid": 38
  }
]
```
- - -
### 택시/카풀 ID로 조회
```http request
GET /parties/{pId}
```
**성공**: 200 OK <br>
**실패**:
|Code|Message|Description|
|------|---|---|
|`401`|`Access Token이 만료되었습니다.`|사용자의 Access Token이 만료되었거나 유효하지 않은 경우|
|`404`|``|파티 ID가 존재하지 않은 경우|

**요청 헤더**
|Name|Description|
|---|---|
|`Authorization`|`Bearer` + `JWT Access Token`|

**요청 예시**
```http
GET /parties/40
```
```http header
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI0IiwiYXV0aCI6IlJPT...(이하 생략)
```

**응답 예시(JSON)**
```json
{
  "type": "카풀",
  "startDate": "2023-05-20",
  "startTime": "오전 11:20",
  "startPoint": "남춘천역",
  "startLat": "37.86369763697937",
  "startLng": "127.72376542374549",
  "endPoint": "동문",
  "currentHeadcnt": 1,
  "totalHeadcnt": 4,
  "isConfirm": false,
  "isFinish": false,
  "carNumber": "237더1028",
  "content": "컨텐츠내용 테스트 예제",
  "people": [
    "test5_nick",
    "test1_nick",
    "test2_nick",
    "test3_nick"
  ],
  "pid": 40
}
```
- - -
### 파티 등록
```http request
POST /parties
```
**성공**: 201 CREATED <br>
**실패**:
|Code|Message|Description|
|------|---|---|
|`401`|`Access Token이 만료되었습니다.`|사용자의 Access Token이 만료되었거나 유효하지 않은 경우|

**요청 헤더**
|Name|Description|
|---|---|
|`Authorization`|`Bearer` + `JWT Access Token`|

**요청 필드**
|필수|Field|Type|Description|
|:---:|------|---|---|
|O|`type`|`String`|`"택시"` or `"카풀"`|
|O|`startPoint`|`String`|출발지|
|O|`startLat`|`String`|출발지 위도|
|O|`startLng`|`String`|출발지 경도|
|O|`endPoint`|`String`|도착지|
|O|`totalHeadcnt`|`int`|전체 인원 수|
|O|`startDate`|`String`|출발 날짜|
|O|`startTime`|`String`|출발 시각|
|X|`carNumber`|`String`|차량 번호(only 카풀)|
|X|`content`|`String`|글 내용(only 카풀)|

**택시 등록 요청 예시(JSON)**
```http
POST /parties
```
```http header
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI0IiwiYXV0aCI6IlJPT...(이하 생략)
```
```json
{
  "type": "택시",
  "startPoint": "기숙사",
  "startLat": "37.87120749003905",
  "startLng": "127.7431938775162",
  "endPoint": "남춘천역",
  "totalHeadcnt": 4,
  "startDate": "2023-04-04",
  "startTime": "오후 02:30"
}
```
**택시 등록 응답 예시(JSON)**
```json
{
  "type": "택시",
  "startDate": "2023-04-04",
  "startTime": "오후 02:30",
  "startPoint": "기숙사",
  "startLat": "37.87120749003905",
  "startLng": "127.7431938775162",
  "endPoint": "남춘천역",
  "currentHeadcnt": 1,
  "totalHeadcnt": 4,
  "isConfirm": false,
  "isFinish": false,
  "carNumber": null,
  "content": null,
  "people": [
    "test1_nick"
  ],
  "pid": 41
}
```
**카풀 등록 요청 예시(JSON)**
```http
POST /parties
```
```http header
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI0IiwiYXV0aCI6IlJPT...(이하 생략)
```
```json
{
  "type": "카풀",
  "startPoint": "글로벌경영관",
  "startLat": "37.87120749003905",
  "startLng": "127.7431938775162",
  "endPoint": "남춘천역",
  "totalHeadcnt": 4,
  "startDate": "2023-04-19",
  "startTime": "오후 09:10",
  "carNumber": "98가7654",
  "content": "카풀내용수정테스트asdfgh"
}
```
**카풀 등록 응답 예시(JSON)**
```json
{
  "type": "카풀",
  "startDate": "2023-04-19",
  "startTime": "오후 09:10",
  "startPoint": "글로벌경영관",
  "startLat": "37.87120749003905",
  "startLng": "127.7431938775162",
  "endPoint": "남춘천역",
  "currentHeadcnt": 1,
  "totalHeadcnt": 4,
  "isConfirm": false,
  "isFinish": false,
  "carNumber": "98가7654",
  "content": "카풀내용수정테스트asdfgh",
  "people": [
    "test1_nick"
  ],
  "pid": 42
}
```
- - -
### 파티 삭제
- **글 작성자만 파티를 삭제할 수 있습니다.**
```http request
DELETE /parties/{pId}
```
**성공**: 200 OK <br>
**실패**:
|Code|Message|Description|
|------|---|---|
|`401`|`Access Token이 만료되었습니다.`|사용자의 Access Token이 만료되었거나 유효하지 않은 경우|
|`404`|``|파티 ID가 존재하지 않은 경우|

**요청 헤더**
|Name|Description|
|---|---|
|`Authorization`|`Bearer` + `JWT Access Token`|

**요청 예시**
```http
DELETE /parties/40
```
```http header
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI0IiwiYXV0aCI6IlJPT...(이하 생략)
```

- - -
### 파티 수정
- **택시 <-> 카풀 간 수정도 가능합니다. 예시를 참고해주세요.**
- **글 작성자만 파티를 수정할 수 있습니다.**
```http request
PUT /parties/{pId}
```
**성공**: 200 OK <br>
**실패**:
|Code|Message|Description|
|------|---|---|
|`401`|`Access Token이 만료되었습니다.`|사용자의 Access Token이 만료되었거나 유효하지 않은 경우|
|`404`|``|파티 ID가 존재하지 않은 경우|

**요청 헤더**
|Name|Description|
|---|---|
|`Authorization`|`Bearer` + `JWT Access Token`|

**요청 필드**
|필수|Field|Type|Description|
|:---:|------|---|---|
|O|`type`|`String`|`"택시"` or `"카풀"`|
|O|`startPoint`|`String`|출발지|
|O|`startLat`|`String`|출발지 위도|
|O|`startLng`|`String`|출발지 경도|
|O|`endPoint`|`String`|도착지|
|O|`totalHeadcnt`|`int`|전체 인원 수|
|O|`startDate`|`String`|출발 날짜|
|O|`startTime`|`String`|출발 시각|
|X|`carNumber`|`String`|차량 번호(only 카풀)|
|X|`content`|`String`|글 내용(only 카풀)|

**택시 -> 카풀 수정 요청 예시(JSON)**
```http
PUT /parties/41
```
```http header
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI0IiwiYXV0aCI6IlJPT...(이하 생략)
```
```json
{
  "type": "카풀",
  "startPoint": "기숙사",
  "startLat": "37.87120749003905",
  "startLng": "127.7431938775162",
  "endPoint": "남춘천역",
  "totalHeadcnt": 4,
  "startDate": "2023-04-04",
  "startTime": "오후 02:30",
  "carNumber": "123고4567",
  "content": "카풀 내용 수정 테스트입니다."
}
```
**택시 -> 카풀 수정 응답 예시(JSON)**
```json
{
  "type": "카풀",
  "startDate": "2023-04-04",
  "startTime": "오후 02:30",
  "startPoint": "기숙사",
  "startLat": "37.87120749003905",
  "startLng": "127.7431938775162",
  "endPoint": "남춘천역",
  "currentHeadcnt": 1,
  "totalHeadcnt": 4,
  "isConfirm": false,
  "isFinish": false,
  "carNumber": "123고4567",
  "content": "카풀 내용 수정 테스트입니다.",
  "people": [
    "test1_nick"
  ],
  "pid": 41
}
```

- - -
### 파티 참여하기
- **원하는 파티에 참여하는 기능입니다.**
- **파티의 최대 인원수를 초과하는 경우 오류 메시지를 반환합니다.**
- **파티 참여에 성공한다면 현재 인원수를 반환합니다.**
```http request
PUT /parties/{pId}/participate
```
**성공**: 200 OK <br>
**실패**:
|Code|Message|Description|
|------|---|---|
|`401`|`Access Token이 만료되었습니다.`|사용자의 Access Token이 만료되었거나 유효하지 않은 경우|

**요청 헤더**
|Name|Description|
|---|---|
|`Authorization`|`Bearer` + `JWT Access Token`|

**요청 예시**
```http
PUT /parties/40/participate
```
```http header
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI0IiwiYXV0aCI6IlJPT...(이하 생략)
```
**응답 예시(TEXT) (참여 후 현재 인원이 2인 경우)**
```json
2
```

- - -
### 파티 확정 완료하기
- **파티의 is_confirm 값을 true로 변경하는 기능입니다.**
- **글 작성자만 파티 확정 완료가 가능합니다.**
```http request
PUT /parties/{pId}/confirm
```
**성공**: 200 OK <br>
**실패**:
|Code|Message|Description|
|------|---|---|
|`401`|`Access Token이 만료되었습니다.`|사용자의 Access Token이 만료되었거나 유효하지 않은 경우|

**요청 헤더**
|Name|Description|
|---|---|
|`Authorization`|`Bearer` + `JWT Access Token`|

**요청 예시**
```http
PUT /parties/40/confirm
```
```http header
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI0IiwiYXV0aCI6IlJPT...(이하 생략)
```
- - -
### 파티 종료하기
- **파티의 is_finish 값을 true로 변경하는 기능입니다.**
- **글 작성자만 파티 종료가 가능합니다.**
```http request
PUT /parties/{pId}/finish
```
**성공**: 200 OK <br>
**실패**:
|Code|Message|Description|
|------|---|---|
|`401`|`Access Token이 만료되었습니다.`|사용자의 Access Token이 만료되었거나 유효하지 않은 경우|

**요청 헤더**
|Name|Description|
|---|---|
|`Authorization`|`Bearer` + `JWT Access Token`|

**요청 예시**
```http
PUT /parties/40/finish
```
```http header
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI0IiwiYXV0aCI6IlJPT...(이하 생략)
```
- - -
### 아이디/이메일/닉네임 중복 확인
- **아이디와 이메일, 닉네임의 중복여부를 확인하는 기능입니다.**
- **GET 요청 파라미터로 아이디, 이메일, 닉네임 중 하나를 포함하야 전송해야 합니다.**
```http request
GET /members/check
```
**성공**: 200 OK (중복 없음) <br>
**실패**:
|Code|Message|Description|
|------|---|---|
|`400`|`ID 중복`|ID가 중복된 경우|
|`400`|`이메일 중복`|이메일이 중복된 경우|
|`400`|`닉네임 중복`|닉네임 중복된 경우|
|`400`|`파라미터 에러`|올바르지 않은 요청 파라미터가 전송된 경우|

**요청 파라미터**
|필수|Params|Type|Description|
|:---:|------|---|---|
|X|`id`|`String`| 중복여부 확인할 아이디|
|X|`email`|`String`| 중복여부 확인할 이메일|
|X|`nickname`|`String`| 중복여부 확인할 닉네임|

**요청 예시 (ID 중복여부)**
```http request
GET /members/check?id=test1
```

- - -
### 회원가입
- **신규 회원 정보를 입력받고 회원가입 요청을 보내는 기능입니다.**
- **입력했던 이메일로 회원인증 링크를 전송합니다.**
```http request
POST /members/join
```
**성공**: 200 OK <br>

**요청 필드**
|필수|Field|Type|Description|
|:---:|------|---|---|
|O|`id`|`String`|아이디(최대 15자)|
|O|`pw`|`String`|비밀번호|
|O|`email`|`String`|이메일(현재는 @kangwon.ac.kr 아니여도 가능)|
|O|`nickname`|`String`|닉네임(최대 15자)|

**요청 예시(JSON)**
```json
{
  "id": "test1",
  "pw": "1q2w3e4r",
  "email": "testmail1@kangwon.ac.kr",
  "nickname": "test1_nick"
}
```

- - -
### 로그인
- **아이디와 비밀번호로 로그인을 하고 Access Token과 Refresh Token을 반환받는 기능입니다.**
- **회원가입 완료 후 이메일 인증을 한 사용자만 로그인이 가능합니다.**
```http request
POST /members/login
```
**성공**: 200 OK <br>
**실패**:
|Code|Message|Description|
|------|---|---|
|`401`|`Access Token이 만료되었습니다.`|아이디 또는 비밀번호 오류|
|`401`|`메일인증을 완료하지 않았습니다.`|이메일 인증 미완료 오류|

**요청 필드**
|필수|Field|Type|Description|
|:---:|------|---|---|
|O|`id`|`String`|아이디|
|O|`pw`|`String`|비밀번호|

**응답 필드**
|Field|Type|Description|
|------|---|---|
|`grantType`|`String`|인증타입 (JWT는 `Bearer`)|
|`accessToken`|`String`|Access Token (현재 유효기간 30분으로 설정)|
|`refreshToken`|`String`|Refresh Token (현재 유효기간 7일로 설정)|
|`accessTokenExpiresIn`|`String`|Access Token 유효기간(현재시각+30분)|

**요청 예시(JSON)**
```json
{
  "id": "test1",
  "pw": "1q2w3e4r"
}
```

**응답 예시(JSON)**
```json
{
  "grantType": "Bearer",
  "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI0IiwiYXV0aCI6IlJPTEVfVVNFUiIsImV4cCI6MTY4NDkyOTIwNn0.d0B2AXhmqUO_XIHYfuTVw_yKc-pj3puDV2XGwn8C-SF1yDes39ZZviYk4hBF5b-S2NZUHlmwg9oMnV_uiZBf3Q",
  "refreshToken": "eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE2ODU1MzIyMDZ9.dWnu2y-JoiggvhqbDbjuB1ir-KvqN00F-fXbX7p3vGdTvuOgFwL6U9t0oimwtkZ3MoYPhlskdxd7UkfUxt2lZw",
  "accessTokenExpiresIn": 1684929206327
}
```

- - -
### 토큰 재발급
- **토큰의 유효기간이 만료되면 재발급 받는 기능입니다.**
```http
POST /members/reissue
```
**성공**: 200 OK <br>
**실패**:
|Code|Message|Description|
|------|---|---|
|`401`|`Refresh Token이 유효하지 않습니다.`|잘못된 형식의 Refresh Token이 입력된 경우|
|`401`|`로그아웃된 사용자입니다. Refresh Token이 존재하지 않습니다.`|DB 테이블에 Refresh Token이 존재하지 않은 경우|
|`401`|`Refresh Token이 일치하지 않거나 만료되었습니다.`|올바른 형식의 Refresh Token이지만 DB 테이블의 Refresh Token 값과 다른 경우|
|`500`|`JWT signature does not match locally computed signature. JWT validity cannot be asserted and should not be trusted.`|Access Token의 Signature부분 위조|
|`500`|`Unable to read JSON value: ...(이하생략)`|Access Token의 Header부분이 잘못된 경우|
|`500`|`Unsupported signature algorithm ...(이하생략)`|Access Token의 Header부분이 잘못된 경우|


**요청 필드**
|Field|Type|Description|
|------|---|---|
|`accessToken`|`String`|Access Token(현재 유효기간 30분으로 설정)|
|`refreshToken`|`String`|Refresh Token(현재 유효기간 7일로 설정)|

**응답 필드**
|Field|Type|Description|
|------|---|---|
|`grantType`|`String`|인증타입 (JWT는 `Bearer`)|
|`accessToken`|`String`|Access Token (현재 유효기간 30분으로 설정)|
|`refreshToken`|`String`|Refresh Token (현재 유효기간 7일로 설정)|
|`accessTokenExpiresIn`|`String`|Access Token 유효기간(현재시각+30분)|

**요청 예시(JSON)**
```json
{
  "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI0IiwiYXV0aCI6IlJPTEVfVVNFUiIsImV4cCI6MTY4NDkyOTIwNn0.d0B2AXhmqUO_XIHYfuTVw_yKc-pj3puDV2XGwn8C-SF1yDes39ZZviYk4hBF5b-S2NZUHlmwg9oMnV_uiZBf3Q",
  "refreshToken": "eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE2ODU1MzIyMDZ9.dWnu2y-JoiggvhqbDbjuB1ir-KvqN00F-fXbX7p3vGdTvuOgFwL6U9t0oimwtkZ3MoYPhlskdxd7UkfUxt2lZw"
}
```

**응답 예시(JSON)**
```json
{
  "grantType": "Bearer",
  "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI0IiwiYXV0aCI6IlJPTEVfVVNFUiIsImV4cCI6MTY4NDkyOTcyOH0.dimTO0aPhXQhPGr6X_9CS6DxcO9eLVwyuYr7olzZfugbcfF4sKLruS113sD_fSUAnZN4UEoeTsL1Lm4P7kRcvA",
  "refreshToken": "eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE2ODU1MzI3Mjh9.ZGCwZIvmZMAg23DQFbiEDV500D2LInxmGo3Smw_Q2qo4VVPRWAI_nMIZt-vooKvOyQ-9dBn-jVpRXCBIXWnO-Q",
  "accessTokenExpiresIn": 1684929728723
}
```

- - -
### 마이페이지
- **내 정보를 반환받는 기능입니다.**
- **HTTP 헤더**의 **Authorization 필드**에 Access Token을 포함하여 전송해야 합니다.
- **꼭 파라미터나 HTTP BODY가 아닌 HTTP 헤더에 토큰을 전송해야 합니다.**
```http
GET /members/me
```
**성공**: 200 OK <br>
**실패**:
|Code|Message|Description|
|------|---|---|
|`401`|`Access Token이 만료되었습니다.`|사용자의 Access Token이 만료되었거나 유효하지 않은 경우|

**요청 헤더**
|Name|Description|
|---|---|
|`Authorization`|`Bearer` + `JWT Access Token`|

**응답 필드**
|Field|Type|Description|
|------|---|---|
|`id`|`String`|아이디|
|`pw`|`String`|비밀번호|
|`nickname`|`String`|닉네임|
|`email`|`String`|이메일|
|`authCode`|`Integer`|인증코드(6자리 랜덤숫자)|
|`authStatus`|`Boolean`|인증상태(인증완료: true, 인증미완료: false)|
|`authority`|`String`|권한(ROLE_USER / ROLE_ADMIN)|
|`mid`|`Integer`|멤버ID (DB 기본키)|

**요청 예시**
```http
GET /members/me
```
```http header
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI0IiwiYXV0aCI6IlJPT...(이하 생략)
```

**응답 예시(JSON)**
```json
{
  "id": "test1",
  "pw": "$2a$10$KSAzA388kbsSZNvgLc1enO.EXAlKlBn/XmbNPd.xKC5f3ONXyIQrK",
  "nickname": "test1_nick",
  "email": "testmail1@kangwon.ac.kr",
  "authCode": 915897,
  "authStatus": true,
  "authority": "ROLE_USER",
  "mid": 4
}
```
- - -
### 로그아웃
- **로그아웃 요청이 들어오면 Refresh Token을 삭제하는 기능입니다.**
```http request
POST /members/logout
```
**성공**: 200 OK <br>
**실패**:
|Code|Message|Description|
|------|---|---|
|`401`|`로그아웃된 사용자입니다. Refresh Token이 존재하지 않습니다.`|DB 테이블에 Refresh Token이 존재하지 않은 경우|

**요청 헤더**
|Name|Description|
|---|---|
|`Authorization`|`Bearer` + `JWT Access Token`|

**요청 예시(JSON)**
```http
POST /members/logout
```
```http header
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI0IiwiYXV0aCI6IlJPT...(이하 생략)
```
- - -
### 작성글 내역 조회
- **로그인한 사용자가 개설한 파티를 조회합니다.**
```http
GET /members/notice-list
```
**성공**: 200 OK <br>
**실패**:
|Code|Message|Description|
|------|---|---|
|`401`|`Access Token이 만료되었습니다.`|사용자의 Access Token이 만료되었거나 유효하지 않은 경우|

**요청 헤더**
|Name|Description|
|---|---|
|`Authorization`|`Bearer` + `JWT Access Token`|

**요청 예시**
```http
GET /members/notice-list
```
```http header
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI0IiwiYXV0aCI6IlJPT...(이하 생략)
```

**응답 예시(JSON)**
```json
[
  {
    "type": "택시",
    "startDate": "2023-04-01",
    "startTime": "오전 11:20",
    "startPoint": "백록관",
    "startLat": "37.868442369510475",
    "startLng": "127.7409329182267",
    "endPoint": "남춘천역",
    "currentHeadcnt": 1,
    "totalHeadcnt": 4,
    "isConfirm": false,
    "isFinish": false,
    "carNumber": null,
    "content": null,
    "people": [
      "test1_nick"
    ],
    "pid": 1
  },
  {
    "type": "택시",
    "startDate": "2023-04-06",
    "startTime": "오전 11:20",
    "startPoint": "천지관",
    "startLat": "37.87119862478267",
    "startLng": "127.74317105800883",
    "endPoint": "남춘천역",
    "currentHeadcnt": 1,
    "totalHeadcnt": 4,
    "isConfirm": false,
    "isFinish": false,
    "carNumber": null,
    "content": null,
    "people": [
      "test1_nick"
    ],
    "pid": 6
  }
]
```
- - -
### 파티 참여내역 조회
- **로그인한 사용자가 참여한 파티를 조회합니다.**
- **직접 개설한 파티는 조회되지 않으며 다른 이용자가 개설한 파티에 참여한 경우만 조회됩니다.**
```http
GET /members/participation-list
```
**성공**: 200 OK <br>
**실패**:
|Code|Message|Description|
|------|---|---|
|`401`|`Access Token이 만료되었습니다.`|사용자의 Access Token이 만료되었거나 유효하지 않은 경우|

**요청 헤더**
|Name|Description|
|---|---|
|`Authorization`|`Bearer` + `JWT Access Token`|

**요청 예시**
```http
GET /members/participation-list
```
```http header
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI0IiwiYXV0aCI6IlJPT...(이하 생략)
```

**응답 예시(JSON)**
```json
[
  {
    "type": "카풀",
    "startDate": "2023-05-20",
    "startTime": "오전 11:20",
    "startPoint": "남춘천역",
    "startLat": "37.86369763697937",
    "startLng": "127.72376542374549",
    "endPoint": "동문",
    "currentHeadcnt": 1,
    "totalHeadcnt": 4,
    "isConfirm": false,
    "isFinish": false,
    "carNumber": "237더1028",
    "content": "컨텐츠내용 테스트 예제",
    "people": [
      "test5_nick",
      "test1_nick",
      "test2_nick",
      "test3_nick"
    ],
    "pid": 40
  }
]
```
- - -
### 닉네임 변경
- **사용자의 닉네임을 변경하는 기능입니다.**
- **닉네임의 중복 체크는 /members/check 를 이용하면 됩니다.**
```http
PUT /members/nickname
```
**성공**: 200 OK <br>
**실패**:
|Code|Message|Description|
|------|---|---|
|`401`|`Access Token이 만료되었습니다.`|사용자의 Access Token이 만료되었거나 유효하지 않은 경우|

**요청 헤더**
|Name|Description|
|---|---|
|`Authorization`|`Bearer` + `JWT Access Token`|

**요청 필드**
|필수|Field|Type|Description|
|:---:|------|---|---|
|O|`nickname`|`String`|변경할 새로운 닉네임|

**요청 예시**
```http
PUT /members/nickname
```
```http header
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI0IiwiYXV0aCI6IlJPT...(이하 생략)
```

**요청 예시(JSON)**
```json
{
  "nickname": "newNick1"
}
```
- - -
### 비밀번호 변경
- **사용자의 비밀번호를 변경하는 기능입니다.**
- **비밀번호 변경 후에는 로그아웃 처리 됩니다.**
```http
PUT /members/password
```
**성공**: 200 OK <br>
**실패**:
|Code|Message|Description|
|------|---|---|
|`401`|`Access Token이 만료되었습니다.`|사용자의 Access Token이 만료되었거나 유효하지 않은 경우|
||또는 기존 비밀번호가 잘못 입력된 경우|

**요청 헤더**
|Name|Description|
|---|---|
|`Authorization`|`Bearer` + `JWT Access Token`|

**요청 필드**
|필수|Field|Type|Description|
|:---:|------|---|---|
|O|`oldPassword`|`String`|기존 비밀번호|
|O|`newPassword`|`String`|새로운 비밀번호|

**요청 예시**
```http
PUT /members/password
```
```http header
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI0IiwiYXV0aCI6IlJPT...(이하 생략)
```

**요청 예시(JSON)**
```json
{
  "oldPassword": "1q2w3e4r",
  "newPassword": "1q2w3e"
}
```

## TODO
- 로그아웃 하고나서 마이페이지 조회 가능한 오류 수정
- 파티 참여내역 조회 - 특정 시간(종료 후 24시간) 후 자동삭제 되게끔 설정 (이 시간안에 리뷰작성)
- 프로필 이미지?
- 리뷰 기능 개발

## 참고
- 주소 -> 위도/경도 변환
  https://developers.kakao.com/docs/latest/ko/local/dev-guide#address-coord-request
- 위도/경도 -> 주소 변환
  https://developers.kakao.com/docs/latest/ko/local/dev-guide#coord-to-address
- 위도/경도 찾기
  https://apis.map.kakao.com/web/sample/addMapClickEventWithMarker/
- JWT 설명 https://github.com/ParkJiwoon/PrivateStudy/blob/master/web/jwt.md
