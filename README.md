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
![image](https://github.com/user-attachments/assets/3c39d957-0d78-44ae-bcd0-3af544adfc8c)
![image](https://github.com/user-attachments/assets/69b832df-0fbc-4dc2-b835-370827bdf61d)
![image](https://github.com/user-attachments/assets/aa5ffad6-7e60-43a8-9919-110ce3217d42)
![image](https://github.com/user-attachments/assets/e654c554-c103-4fc6-a99a-15a07551fc72)


<br>

🔐 인증/인가: Session

🚨 400 Bad Request: 잘못된 접근(비밀번호, 이메일 등 형식)<br>
　  401 Unauthorized: 유효한 인증 자격이 없음 (로그인)<br>
　  403 Forbidden: 엑세스 권한 없음 (세션)<br>
　  404 Not Found: 찾을 수 없는 접근(요청 id)

<br><br><hr><br>

### ERD
![image](https://github.com/user-attachments/assets/c04a30c6-b2ba-480c-af43-610bd91030ce)


<br>

✅ user와 post는 1:N 관계
✅ like, bookmark는 user:post의 N:M 관계

✅ post와 comment는 1:N 관계
✅ comment_like는 post:comment의 N:M 관계
<br>
　　→  N:M 관계는 관계 테이블 → entity로 구현<br>

<br><br><hr><br>


### SQL

👤 User
```sql
CREATE TABLE user (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    interest_tag VARCHAR(255) NOT NULL,
    status VARCHAR(50) DEFAULT 'ACTIVE',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);
```

<br>

📄 post
```sql
CREATE TABLE post (
    id BIGINT NOT NULL AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    contents LONGTEXT NOT NULL,
    keyword VARCHAR(255),
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT fk_post_user FOREIGN KEY (user_id) REFERENCES user(id)
);
```

<br>

❤ post_like
```sql
CREATE TABLE post_like (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    post_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT unique_post_user UNIQUE (post_id, user_id),
    CONSTRAINT fk_post_like_post FOREIGN KEY (post_id) REFERENCES post(id) ON DELETE CASCADE,
    CONSTRAINT fk_post_like_user FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
);
```

<br>

📑 Comment
```sql
CREATE TABLE comment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    post_id BIGINT NOT NULL,
    contents TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_comment_user FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE,
    CONSTRAINT fk_comment_post FOREIGN KEY (post_id) REFERENCES post(id) ON DELETE CASCADE
);
```

<br>

💙 Comment_like
```sql
CREATE TABLE comment_like (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    comment_id BIGINT NOT NULL,
    CONSTRAINT unique_comment_user UNIQUE (user_id, comment_id),
    CONSTRAINT fk_comment_like_user FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE,
    CONSTRAINT fk_comment_like_comment FOREIGN KEY (comment_id) REFERENCES comment(id) ON DELETE CASCADE
);
```

<br>

💌 apply
```sql
CREATE TABLE apply (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    sender_id BIGINT NOT NULL,
    receiver_id BIGINT NOT NULL,
    CONSTRAINT friend_apply UNIQUE (sender_id, receiver_id),
    CONSTRAINT fk_apply_sender FOREIGN KEY (sender_id) REFERENCES user(id) ON DELETE CASCADE,
    CONSTRAINT fk_apply_receiver FOREIGN KEY (receiver_id) REFERENCES user(id) ON DELETE CASCADE
);
```

<br>
👥 Friends
```sql
CREATE TABLE friend (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    to_user_id BIGINT NOT NULL,
    from_user_id BIGINT NOT NULL,
    CONSTRAINT unique_friend UNIQUE (to_user_id, from_user_id),
    CONSTRAINT fk_friend_to_user FOREIGN KEY (to_user_id) REFERENCES user(id) ON DELETE CASCADE,
    CONSTRAINT fk_friend_from_user FOREIGN KEY (from_user_id) REFERENCES user(id) ON DELETE CASCADE
);
```

<br>

🔖 Bookmarks
```sql
CREATE TABLE bookmark (
    bookmark_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    bookmark_user_id BIGINT NOT NULL,
    bookmark_post_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_bookmark_user FOREIGN KEY (bookmark_user_id) REFERENCES user(id) ON DELETE CASCADE,
    CONSTRAINT fk_bookmark_post FOREIGN KEY (bookmark_post_id) REFERENCES post(id) ON DELETE CASCADE
);
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
