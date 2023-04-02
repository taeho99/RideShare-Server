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
**출력 예시**
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
---
## TODO

- PartyRepository에서 store 통합
- 컨트롤러에서 등록기능 구현