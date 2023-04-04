# RideShare-Server
## 실행방법(인텔리제이 기준) 
[https://jojelly.tistory.com/86](https://jojelly.tistory.com/86)
## 초기설정
1. file->settings->Build, Execution, Deployment-> Compiler -> Annotation Processors에서 enable annotation processing를 체크표시 해주세요.
![캡처](https://user-images.githubusercontent.com/70526479/229042932-646348cf-5152-410a-bdce-9fdc85a1b695.PNG)
2. RideshareApplication 파일의 main() 메서드를 실행하면 됩니다.<br>
![캡처2](https://user-images.githubusercontent.com/70526479/229047097-cf8ed5c7-6415-4326-bfbe-928179b7b0c3.PNG)
## REST API Guide
### 모든 택시/카풀 리스트 반환
```http
GET /parties/taxis
```
```http
GET /parties/carpools
```
**성공**: 200 OK <br><br>
**응답 예시(JSON)**
```json
[
    {
        "p_id": 1,
        "p_type": "택시",
        "startTime": "2023-03-31T07:08:55.880+00:00",
        "startPoint": "기숙사",
        "endPoint": "남춘천역",
        "currentHeadcnt": 2,
        "totalHeadcnt": 4,
        "carNumber": null,
        "content": null,
        "startDateStr": "2023-03-31",
        "startTimeStr": "오후 04:08",
        "confirm": false
    },
    {
        "p_id": 2,
        "p_type": "택시",
        "startTime": "2023-03-31T07:08:55.880+00:00",
        "startPoint": "후문",
        "endPoint": "남춘천역",
        "currentHeadcnt": 2,
        "totalHeadcnt": 4,
        "carNumber": null,
        "content": null,
        "startDateStr": "2023-03-31",
        "startTimeStr": "오후 04:08",
        "confirm": false
    }
]
```
- - -
### 택시/카풀 ID로 조회
```http request
GET /parties/{p_id}
```
**성공**: 200 OK <br>
**실패**: 404 NOT_FOUND <BR><br>
**응답 예시(JSON)**
```json
{
    "p_id": 21,
    "p_type": "카풀",
    "startDate": "2023-03-31",
    "startTime": "오후 04:08",
    "startPoint": "기숙사",
    "endPoint": "남춘천역",
    "currentHeadcnt": 1,
    "totalHeadcnt": 4,
    "carNumber": "12가3456",
    "content": "카풀내용예제테스트asdfgh",
    "confirm": false
}
```
- - -
### 택시 파티 등록
```http request
POST /parties/taxis
```
**성공**: 201 CREATED <br><BR>
**요청 예시(JSON)**
```json
{
  "startPoint": "기숙사",
  "endPoint": "남춘천역",
  "totalHeadcnt": 4,
  "startDate": "2023-03-31",
  "startTime": "오후 04:08"
}
```
**응답 예시(JSON)**
```json
{
    "p_id": 23,
    "p_type": "택시",
    "startDate": "2023-03-31",
    "startTime": "오후 04:08",
    "startPoint": "기숙사",
    "endPoint": "남춘천역",
    "currentHeadcnt": 1,
    "totalHeadcnt": 4,
    "carNumber": null,
    "content": null,
    "confirm": false
}
```
- - -
### 카풀 파티 등록
```http request
POST /parties/carpools
```
**성공**: 201 CREATED <br><BR>
**요청 예시(JSON)**
```json
{
  "startPoint": "기숙사",
  "endPoint": "남춘천역",
  "totalHeadcnt": 4,
  "startDate": "2023-03-31",
  "startTime": "오후 04:08",
  "carNumber": "12가3456",
  "content": "카풀내용예제테스트asdfgh"
}
```
**응답 예시(JSON)**
```json
{
  "p_id": 24,
  "p_type": "카풀",
  "startDate": "2023-03-31",
  "startTime": "오후 04:08",
  "startPoint": "기숙사",
  "endPoint": "남춘천역",
  "currentHeadcnt": 1,
  "totalHeadcnt": 4,
  "carNumber": "12가3456",
  "content": "카풀내용예제테스트asdfgh",
  "confirm": false
}
```
## TODO
- SQL 테이블에서 startTime 데이터 타임을 startDate, startTime으로 변경
- Party.java 에서 isFinish 필드 추가하기
- 도로명주소 -> 위도, 경도 변환 api 알아보기
- 위도, 경도 데이터베이스 스키마에 
