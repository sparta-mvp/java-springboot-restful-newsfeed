# 기초 프로젝트: 뉴스 피드 앱 만들기
[팀 노션 링크](https://www.notion.so/teamsparta/1-MVP-19a2dc3ef5148081ad2edcbe774baa60)

<br><br>

#### 개발 환경
1. 프로젝트 기간: 2025.02.14 - 2025.02.20
2. 사용 기술: JAVA, SpringBoot, JPA, Spring Data JPA, MySQL

<br><br>

#### 역할 분담
🦦 [juno-soodal](https://github.com/juno-soodal) : user crud, 인증/인가, 공통 로직 처리, comment like<p>
🤡 [mmj-159](https://github.com/mmj-159) : bookmark<p>
👻 [SuhyeonB](https://github.com/SuhyeonB) : post crud, post like<p>
🍊 [euuns](https://github.com/euuns) : comment crud, friend, friend-apply


<br><br><br>

## 목차
[1. API 명세서 작성](#api-명세서) <br>
[2. ERD 작성](#erd) <br>
[3. SQL 작성](#sql) <br>
[4. 요청 및 응답](#dto) <br>
[5. 트러블 슈팅](#troubleshooting) <br>
[6. 예외 처리](#exception) <br>

<br><br><br>

## 설계

### API 명세서
![API 명세서](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FkSXBU%2FbtsMekkA8es%2FL4HqsOKfnK95URwxUSrvc1%2Fimg.png)

<br>

🔐 인증/인가: Session

🚨 400 Bad Request: 잘못된 접근(비밀번호, 이메일 등 형식)<br>
　  401 Unauthorized: 유효한 인증 자격이 없음 (로그인)<br>
　  403 Forbidden: 엑세스 권한 없음 (세션)<br>
　  404 Not Found: 찾을 수 없는 접근(요청 id)

<br><br><hr><br>

### ERD
![ERD]<img width="962" alt="Image" src="https://github.com/user-attachments/assets/b39cad0a-83d5-455a-995a-6854148f5c26" />

<br>

✅ user와 schedule은 1:N 관계

✅ comment는 user와 schedule에 N:1 관계<br>
　　→  user 1 : N comment<br>
　　→  schedule 1 : N comment<br>

<br><br><hr><br>


### SQL

👤 User
```sql
CREATE TABLE user
(
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(10) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(20) NOT NULL,
    created_date DATE NOT NULL,
    modified_date DATE
);
```

<br>

📑 Schedule
```sql
CREATE TABLE schedule
(
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    title VARCHAR(30) NOT NULL,
    contents VARCHAR(500),
    created_date DATE NOT NULL,
    modified_date DATE,
    FOREIGN KEY (user_id) REFERENCES user(id)
);
```

<br>

🏷️ Comment
```sql
CREATE TABLE comment
(
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    schedule_id BIGINT NOT NULL,
    contents VARCHAR(100) NOT NULL,
    created_date DATE NOT NULL,
    modified_date DATE,
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (schedule_id) REFERENCES schedule(id)
);
```

<br>

👥 Friends
```sql
작성
```

<br>

🔖 Bookmarks
```sql
작성
```

<br><br><hr><br>

### DTO

<br>

👤 User <p>
![image](https://github.com/user-attachments/assets/2174523c-9f0d-4760-a242-b439e03c9344)

<br>

📑 Schedule <p>
![image](https://github.com/user-attachments/assets/130ca238-a075-4730-84e7-36f2271f553a)

<br>

🏷️ Comment <p>
![image](https://github.com/user-attachments/assets/ae29ba35-96e1-40e0-98fb-ead935ee6d3b)

<br>

👥 Friends <p>
![image](https://github.com/user-attachments/assets/ae29ba35-96e1-40e0-98fb-ead935ee6d3b)

<br>

🔖 Bookmarks <p>
![image](https://github.com/user-attachments/assets/ae29ba35-96e1-40e0-98fb-ead935ee6d3b)

<br><br>

☑️ headers-session: password를 대신해 유저 정보를 확인해줄 인증 수단<p>
　　user의 path-id : user의 PK<br>
　　schedule의 path-id : schedule의 PK → comment에서 FK로 사용<br>
　　comment의 path-id : comment의 PK<br>


<br><br><hr><br>

### troubleshooting

<br>

작성

<br><br><hr><br>

### exception

<br>

작성

<br><br>
