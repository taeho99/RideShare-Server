# RideShare-Server
## 목차
- [실행방법](#실행방법)
- [데이터베이스 초기설정 및 테스트 데이터 주입](#데이터베이스-초기설정-및-테스트-데이터-주입)
- [인텔리제이 초기설정](#인텔리제이-초기설정)
- [ERD](#erd)
- [REST API Guide](#rest-api-guide)
  * [택시/카풀 리스트 반환 및 검색](#택시카풀-리스트-반환-및-검색)
  * [택시/카풀 ID로 조회](#택시카풀-ID로-조회)
  * [파티 등록](#파티-등록)
  * [파티 삭제](#파티-삭제)
  * [파티 수정](#파티-수정)
  * [파티 현재 인원 수 1 증가시키기](#파티-현재-인원-수-1-증가시키기)
  * [파티 확정 완료하기](#파티-확정-완료하기)
  * [파티 종료하기](#파티-종료하기)
- [TODO](#todo)
- [참고](#참고)
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
![erd](https://github.com/KNUCSE23-Capston-Design/RideShare-Server/assets/70526479/d84dba09-f380-4b1c-a866-7f35d1a90562)
## REST API Guide
### 택시/카풀 리스트 반환 및 검색
- **lastId 보다 작은 값의 레코드들을 amount개 반환합니다.**
- **keyword 필드를 추가하면 원하는 출발지를 검색할 수 있습니다.**
```http
GET /parties
```
**성공**: 200 OK <br><br>
**요청 필드**
|Field|Type|Description|
|------|---|---|
|`lastId`|`int`|마지막으로 호출 된 id|
|`amount`|`int`|한번에 호출 할 레코드 개수|
|`type`|`String`|`"택시"` or `"카풀"`|
|`keyword`|`String`|검색할 키워드(출발지) (빈칸이면 모두 출력)|

**요청 예시(JSON)**
```json
{
  "lastId": 35,
  "amount": 3,
  "type": "카풀",
  "keyword": ""
}
```

**응답 예시(JSON)**
```json
[
    {
        "type": "카풀",
        "startDate": "2023-05-14",
        "startTime": "오전 11:20",
        "startPoint": "춘천시외버스터미널",
        "startLat": "37.86288933799438",
        "startLng": "127.71893919844631",
        "endPoint": "천지관",
        "currentHeadcnt": 1,
        "totalHeadcnt": 4,
        "isConfirm": false,
        "isFinish": false,
        "carNumber": "32고9831",
        "content": "컨텐츠내용 테스트 예제",
        "pid": 34
    },
    {
        "type": "카풀",
        "startDate": "2023-05-13",
        "startTime": "오전 11:20",
        "startPoint": "남춘천역",
        "startLat": "37.86369763697937",
        "startLng": "127.72376542374549",
        "endPoint": "강원대정문",
        "currentHeadcnt": 3,
        "totalHeadcnt": 4,
        "isConfirm": false,
        "isFinish": false,
        "carNumber": "48거4812",
        "content": "컨텐츠내용 테스트 예제",
        "pid": 33
    },
    {
        "type": "카풀",
        "startDate": "2023-05-12",
        "startTime": "오전 11:20",
        "startPoint": "남춘천역",
        "startLat": "37.86369763697937",
        "startLng": "127.72376542374549",
        "endPoint": "백록관",
        "currentHeadcnt": 1,
        "totalHeadcnt": 4,
        "isConfirm": false,
        "isFinish": false,
        "carNumber": "122더4925",
        "content": "컨텐츠내용 테스트 예제",
        "pid": 32
    }
]
```
- - -
### 택시/카풀 ID로 조회
```http request
GET /parties/{pId}
```
**성공**: 200 OK <br>
**실패**: 404 NOT_FOUND <BR><br>
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
    "pid": 40
}
```
- - -
### 파티 등록
```http request
POST /parties
```
**성공**: 201 CREATED <br><BR>

**요청 필드**
|Field|Type|Description|
|------|---|---|
|`type`|`String`|`"택시"` or `"카풀"`|
|`startPoint`|`String`|출발지|
|`startLat`|`String`|출발지 위도|
|`startLng`|`String`|출발지 경도|
|`endPoint`|`String`|도착지|
|`totalHeadcnt`|`int`|전체 인원 수|
|`startDate`|`String`|출발 날짜|
|`startTime`|`String`|출발 시각|
|`carNumber`|`String`|차량 번호(only 카풀)|
|`content`|`String`|글 내용(only 카풀)|


**택시 등록 요청 예시(JSON)**
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
    "pid": 48
}
```
**카풀 등록 요청 예시(JSON)**
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
    "pid": 49
}
```
- - -
### 파티 삭제
```http request
DELETE /parties/{pId}
```
**성공**: 200 OK <br>
**실패**: 404 NOT_FOUND <br><BR>

- - -
### 파티 수정
- 택시 <-> 카풀 간 수정도 가능합니다. 예시를 참고해주세요.
```http request
PUT /parties/{pId}
```
**성공**: 200 OK <br>
**실패**: 404 NOT_FOUND <br><BR>

**요청 필드**
|Field|Type|Description|
|------|---|---|
|`type`|`String`|`"택시"` or `"카풀"`|
|`startPoint`|`String`|출발지|
|`startLat`|`String`|출발지 위도|
|`startLng`|`String`|출발지 경도|
|`endPoint`|`String`|도착지|
|`totalHeadcnt`|`int`|전체 인원 수|
|`startDate`|`String`|출발 날짜|
|`startTime`|`String`|출발 시각|
|`carNumber`|`String`|차량 번호(only 카풀)|
|`content`|`String`|글 내용(only 카풀)|

**택시 -> 카풀 수정 요청 예시(JSON)**
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
    "pid": 48
}
```

- - -
### 파티 현재 인원 수 1 증가시키기
- **파티의 current_headcnt 값을 1증가 시키는 기능입니다.**
```http request
PUT /parties/{pId}/current-headcnt
```
**성공**: 200 OK <br>

**응답 예시(TEXT) (1 -> 2로 증가시킨 경우)**
```json
2
```

- - -
### 파티 확정 완료하기
- **파티의 is_confirm 값을 true로 변경하는 기능입니다.**
```http request
PUT /parties/{pId}/confirm
```
**성공**: 200 OK <br>

- - -
### 파티 종료하기
- **파티의 is_finish 값을 true로 변경하는 기능입니다.**
```http request
PUT /parties/{pId}/finish
```
**성공**: 200 OK <br>


## TODO
- 로그인 방식 JWT 토큰 공부
- MySQL 데이터베이스 깃허브에 공유하는법 찾아보기
- 로그인 공통관심사 처리
- 커서 기반 페이지네이션 공부하기
- 통합검색 <택시>, <카풀> 라디오버튼 추가?? 검색하면 타입에 맞는 글 목록 페이지로 이동하고 결과 출력
- ID로 검색 실패시 404 반환하게 수정
- GET /parties type 영어로 바꾸기

## 참고
- 주소 -> 위도/경도 변환
  https://developers.kakao.com/docs/latest/ko/local/dev-guide#address-coord-request
- 위도/경도 -> 주소 변환
  https://developers.kakao.com/docs/latest/ko/local/dev-guide#coord-to-address
- 위도/경도 찾기
  https://apis.map.kakao.com/web/sample/addMapClickEventWithMarker/
