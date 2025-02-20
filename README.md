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
[1. API 및 기능 명세서](#api-및-기능-명세서) <br>
[2. ERD 작성](#erd) <br>
[3. SQL 작성](#sql) <br>
[4. 트러블 슈팅](#troubleshooting) <br>
[5. 예외 처리](#exception) <br>

<br><br><br>

## 설계

### API 및 기능 명세서
<br>

#### 👤 Users
![users1](https://github.com/user-attachments/assets/95c9a833-75a3-42cf-8ac7-2405bdd7073e)
![users2](https://github.com/user-attachments/assets/fac660c0-e820-4d0b-a342-69506b341e1d)

<br><br>

#### 📑 Posts
![posts1](https://github.com/user-attachments/assets/c38f05c7-a7e0-4a97-8bd9-195d7f1c327c)
![posts2](https://github.com/user-attachments/assets/6d55c677-23d7-4648-85ab-3dfc57d5f904)

<br><br>

#### 🏷️ Comments
![comments](https://github.com/user-attachments/assets/2f01f057-6222-4cb6-8641-1d2d99ba178a)

<br><br>

#### 👥 Friends
![friends1](https://github.com/user-attachments/assets/5ecb660b-2ad7-4e7b-b22d-c8a5e5c6c4f2)
![friedns2](https://github.com/user-attachments/assets/68ef93df-cf92-4339-8b42-76c872740a9a)

<br><br>

#### 🔖 Bookmarks
![bookmarks](https://github.com/user-attachments/assets/cdd31ffe-bf8d-4f25-a545-0f1c94ff3ef9)

<br><br>

🔐 인증/인가: Session

🚨 400 Bad Request: 잘못된 접근(비밀번호, 이메일 등 형식)<br>
　  401 Unauthorized: 유효한 인증 자격이 없음 (로그인)<br>
　  403 Forbidden: 엑세스 권한 없음 (세션)<br>
　  404 Not Found: 찾을 수 없는 접근(요청 id)

<br><br><hr><br>

### ERD
<img width="962" alt="Image" src="https://github.com/user-attachments/assets/b39cad0a-83d5-455a-995a-6854148f5c26" />

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

### troubleshooting

<br>

![image](https://github.com/user-attachments/assets/a1d4ef32-2fb4-4b59-8d39-c20bd4694018)

<br><br>

🔎 상황 <p>
댓글 목록 조회 시 작성자(User)와 게시글 제목(Post) 정보를 함께 반환.<br>
Comment entity에서 User와 Post를 @ManyToOne(fetch = FetchType.LAZY)로 설정.<br>
CommentResponseDto에서 getUser().getName() 또는 getPost().getTitle()을 호출하면 추가 쿼리가 발생<br>

<br><br>

💥 N+1 문제 발생 <p>
1개 쿼리 후 추가적으로 N개의 쿼리가 발생하는 비효율적인 데이터 조회 문제 <br>

<br><br>

🚀 JOIN FETCH 활용으로 해결<p>

<br>

![image](https://github.com/user-attachments/assets/dfe5274b-66ab-4484-86a6-9c812b43f4ec)

<br>

✅ JOIN FETCH가 적용된 후,<p>
findCommentsWithPostAndUserByPostId() 실행 시<br>
Comment 엔티티를 조회하면서 즉시 User와 Post 객체를 함께 호출<br>
<br>
따라서 proxy 객체가 아니라 실제 entity 로드<br>
추가적인 LazyInitializationException 없이 getUser().getName() 안전하게 호출 가능

<br><br><hr><br>

### exception

<br>

![image](https://github.com/user-attachments/assets/9c393839-5e0e-41d2-961a-41c73f28b658)

🎯 common/exception/ErrorCode.java

<br>

_※ 모든 예외가 아닌 일부 예외 작성_

<br><br>
