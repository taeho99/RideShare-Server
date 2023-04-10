# RideShare-Server
## 실행방법(인텔리제이 기준) 
[https://jojelly.tistory.com/86](https://jojelly.tistory.com/86)
## 초기설정
1. file->settings->Build, Execution, Deployment-> Compiler -> Annotation Processors에서 enable annotation processing를 체크표시 해주세요.
![캡처](https://user-images.githubusercontent.com/70526479/229042932-646348cf-5152-410a-bdce-9fdc85a1b695.PNG)
2. RideshareApplication 파일의 main() 메서드를 실행하면 됩니다.<br>
![캡처2](https://user-images.githubusercontent.com/70526479/229047097-cf8ed5c7-6415-4326-bfbe-928179b7b0c3.PNG)
## ERD
![TTaMpsp2EsLXFTaWY](https://user-images.githubusercontent.com/70526479/230904515-2c816dc1-79c1-4003-9212-42f37ab6133e.png)
https://www.erdcloud.com/d/TTaMpsp2EsLXFTaWY
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
        "startDate": "2023-04-04",
        "startTime": "오후 02:27",
        "startPoint": "기숙사",
        "startLat": "37.865506537056945",
        "startLng": "127.7428865998153",
        "endPoint": "남춘천역",
        "currentHeadcnt": 2,
        "totalHeadcnt": 4,
        "carNumber": null,
        "content": null,
        "confirm": false,
        "finish": false
    },
    {
        "p_id": 2,
        "p_type": "택시",
        "startDate": "2023-04-04",
        "startTime": "오후 02:27",
        "startPoint": "후문",
        "startLat": "37.87254023957852",
        "startLng": "127.7428865998153",
        "endPoint": "남춘천역",
        "currentHeadcnt": 2,
        "totalHeadcnt": 4,
        "carNumber": null,
        "content": null,
        "confirm": false,
        "finish": false
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
    "p_id": 3,
    "p_type": "택시",
    "startDate": "2023-04-04",
    "startTime": "오후 02:27",
    "startPoint": "천지관",
    "startLat": "37.87120749003905",
    "startLng": "127.7431938775162",
    "endPoint": "남춘천역",
    "currentHeadcnt": 2,
    "totalHeadcnt": 4,
    "carNumber": null,
    "content": null,
    "confirm": false,
    "finish": false
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
  "startLat": "37.87120749003905",
  "startLng": "127.7431938775162",
  "endPoint": "남춘천역",
  "totalHeadcnt": 4,
  "startDate": "2023-04-04",
  "startTime": "오후 02:30"
}
```
**응답 예시(JSON)**
```json
{
    "p_id": 21,
    "p_type": "택시",
    "startDate": "2023-04-04",
    "startTime": "오후 02:30",
    "startPoint": "기숙사",
    "startLat": "37.87120749003905",
    "startLng": "127.7431938775162",
    "endPoint": "남춘천역",
    "currentHeadcnt": 1,
    "totalHeadcnt": 4,
    "carNumber": null,
    "content": null,
    "confirm": false,
    "finish": false
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
    "p_id": 22,
    "p_type": "카풀",
    "startDate": "2023-03-31",
    "startTime": "오후 04:08",
    "startPoint": "기숙사",
    "startLat": "37.87120749003905",
    "startLng": "127.7431938775162",
    "endPoint": "남춘천역",
    "currentHeadcnt": 1,
    "totalHeadcnt": 4,
    "carNumber": "12가3456",
    "content": "카풀내용예제테스트asdfgh",
    "confirm": false,
    "finish": false
}
```

- - -
### 파티 삭제
```http request
DELETE /parties/{p_id}
```
**성공**: 200 OK <br>
**실패**: 404 NOT_FOUND <br><BR>

- - -
### 파티 수정
**택시와 카풀 수정은 HTTP 요청 주소는 동일하나 요청하는 JSON에 차이가 있습니다.**<br>
**카풀의 경우 "carNumber"와 "content"를 추가하여 요청해야 합니다.**
```http request
PUT /parties/{p_id}
```
**성공**: 200 OK <br>
**실패**: 404 NOT_FOUND <br><BR>
**택시 요청 예시(JSON)**
```json
{
  "startPoint": "기숙사",
  "startLat": "37.87120749003905",
  "startLng": "127.7431938775162",
  "endPoint": "남춘천역",
  "totalHeadcnt": 4,
  "startDate": "2023-04-19",
  "startTime": "오후 09:10"
}
```
**택시 응답 예시(JSON)**
```json
{
    "p_id": 1,
    "p_type": "택시",
    "startDate": "2023-04-19",
    "startTime": "오후 09:10",
    "startPoint": "기숙사",
    "startLat": "37.87120749003905",
    "startLng": "127.7431938775162",
    "endPoint": "남춘천역",
    "currentHeadcnt": 2,
    "totalHeadcnt": 4,
    "carNumber": null,
    "content": null,
    "finish": false,
    "confirm": false
}
```
**카풀 요청 예시(JSON)**
```json
{
  "startPoint": "기숙사",
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
**카풀 응답 예시(JSON)**
```json
{
    "p_id": 11,
    "p_type": "카풀",
    "startDate": "2023-04-19",
    "startTime": "오후 09:10",
    "startPoint": "기숙사",
    "startLat": "37.87120749003905",
    "startLng": "127.7431938775162",
    "endPoint": "남춘천역",
    "currentHeadcnt": 4,
    "totalHeadcnt": 4,
    "carNumber": "98가7654",
    "content": "카풀내용수정테스트asdfgh",
    "finish": true,
    "confirm": true
}
```

## TODO
- 로그인 방식 JWT 토큰 공부

## 참고
- 주소 -> 위도/경도 변환
    https://developers.kakao.com/docs/latest/ko/local/dev-guide#address-coord-request
- 위도/경도 -> 주소 변환
    https://developers.kakao.com/docs/latest/ko/local/dev-guide#coord-to-address
- 위도/경도 찾기
    https://apis.map.kakao.com/web/sample/addMapClickEventWithMarker/
