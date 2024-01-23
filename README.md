### 목차

> 1. [시스템 아키텍처](#시스템-아키텍처)
> 2. [기술 스택](#기술-스택)
> 3. [주요 기능](#주요-기능)
> 4. [문서](#문서)
> 5. [살행화면](#실행화면)
>
> - 사용자 : [회원가입, 로그인](#회원가입-로그인) | [복습 조회 및 작성](#복습-조회-및-작성) | [복습 검색](#복습-검색) | [알림](#알림) | [히스토리](#히스토리) 

# 프로젝트 소개

> 망각곡선 알림과 히스토리 기능으로 효율적인 영어 학습을 도와주는 REVIEWUS 입니다!\
> 한 번 배우면 까먹지 않게 망각곡선에 따라 복습 알림을 제공해요.\
> 영어를 꾸준히 기록하는 즐거움을 위해 히스토리 기능을 제공해요.

> ### 개발 기간 및 인원
>
> 23.11.13 ~ 24.01.14 (6주) \
> 프론트 1명 백엔드 1명

> ### [배포 링크 ](https://web-review-us-front-5r422alqcbko0u.sel4.cloudtype.app/) 👈 클릭!
> 
| [Front Repository](https://github.com/osh6006/review-us-front) | [Backend Repository](https://github.com/minheebaek/reviewus_backend) | [분리 전 Repository](https://github.com/minheebaek/reviewus) |
|:--------------------:|------------------------|---------------------|
## 팀원

<div align="center">
<table align="center"> <!-- 팀원 표 -->
  <tr>
   <th >
    Frontend 오황석
   </th>
   <th>
    Backend 백민희
   </th>

   </tr>
  <tr>
    <td align="center">
        <img src="https://lh3.googleusercontent.com/a/ACg8ocLPdIOdwqqD0KUqg6A-r83cClfL1rEkZszkgzvlOHb2K9Q=s360-c-no" width=300px alt="오황석"> 
        <br/>
    </td>
    <td align="center">
        <img src="https://github.com/minheebaek/reviewus_backend/assets/105588896/b3a6cab2-fecf-4a30-8b28-f919789e2886" width=300px alt="백민희"> 
        <br/>
    </td>

  </tr>
  <tr>
    <td align="center" class="">
   </td>
   <td align="center" class="백민희">
        <a href="https://github.com/minheebaek"><img alt="github-link" height="25" src="https://img.shields.io/badge/GitHub-181717?style=flat-square&logo=GitHub&logoColor=white"/></a>
        <br/>   
        <a href="https://xandxlass.tistory.com/"><img alt="gmail-link" height="25" src="https://img.shields.io/badge/Tech blog-000000?style=flat-square&logo=Tistory&&logoColor=white"/></a>
   </td>
  </tr>

</table>
</div>

# 기술 스택

### Front-End

- **React**
- **TypeScript**
- **Recoil**
- **Tanstack Query**
- **Tailwind CSS**
- **Daisy UI**

### Back-end

- **Java 11**
- **SpringBoot 2.7.17**
- **Spring Data JPA**
- **Spring Security**
- **JWT(json web tokens)**
- **JUnit**
- **Redis 7**
- **AWS S3**
- **AWS RDS**

### Collaboration

- **GitHub**
- **Notion**
- **Discord**

# 주요 기능

### 회원가입, 로그인

- 사용자는 이메일, 비밀번호, 닉네임을 이용해 회원가입 및 로그인을 할 수 있다.
- 사용자는 소셜로그인을 통해 회원가입 및 로그인을 할 수 있다.

### 복습 조회 및 작성

- 사용자는 작성한 복습 목록을 조회할 수 있다.
    - 기본적으로 작성 최신순으로 목록이 조회된다.
- 사용자는 복습을 작성할 수 있다.
    - 글과 이미지를 위지윅 형태로 작성후 저장이 가능하다.
    - 알림 기능을 on/off 할 수 있다.
    - 태그 작성이 가능하다.

### 복습 검색

- 사용자는 제목 또는 내용을 입력하여 글 검색을 할 수 있다.

### 알림

- 사용자가 글을 작성할 때 알림을 설정하면 알림이 등록된다.
- 기본적으로 망각곡선 법칙에 따라 1일, 7일, 30일이 지날 경우 복습 알림이 온다.

### 히스토리

- 사용자가 글 작성 시 github 잔디처럼 히스토리가 추가되어 작성기록을 시각적으로 확인이 가능하다
- 히스토리는 1년 단위로 구분된다.

# 문서

### [API 명세서](https://documenter.getpostman.com/view/28719938/2s9YsRbUCT)
### [ERD](https://www.erdcloud.com/d/fByT9Wmu3hkcxt9g4)
![image](https://github.com/minheebaek/reviewus_backend/assets/105588896/8606d65e-556e-4d7a-8473-9b37b53b7938)

# 실행화면

### 메인화면
![image](https://github.com/minheebaek/reviewus_backend/assets/105588896/e035f75d-f8c9-418e-bd19-a3241f852c41)
### 망각곡선에 따른 알림
![React App - 프로필 1 - Microsoft_ Edge 2024-01-20 01-00-00 (1)](https://github.com/minheebaek/reviewus_backend/assets/105588896/c1ca9038-1d35-4d5a-a969-b537350d4b6f)
### 마이스터디 CRUD
![image](https://github.com/minheebaek/reviewus_backend/assets/105588896/5b579451-6a72-43c8-85f4-beefa597b9b7)
![image](https://github.com/minheebaek/reviewus_backend/assets/105588896/2fc2388e-fe1a-43c5-9214-b023d6a93ebc)
### 무한스크롤
![React App - 프로필 1 - Microsoft_ Edge 2024-01-20 01-23-17](https://github.com/minheebaek/reviewus_backend/assets/105588896/ca641796-c348-43c6-ac87-4f30cd45c398)
### 마이페이지
![image](https://github.com/minheebaek/reviewus_backend/assets/105588896/b0661e0b-816e-46c3-879d-cdc351b5046b)
### 프로필 정보 변경
![image](https://github.com/minheebaek/reviewus_backend/assets/105588896/ac7ccc18-3702-4b79-a557-dd26345bace4)
### 회원가입
![image](https://github.com/minheebaek/reviewus_backend/assets/105588896/f70f4ce1-a5b0-430d-a4fb-8361d999b442)
### 비밀번호찾기
![image](https://github.com/minheebaek/reviewus_backend/assets/105588896/0ee13c46-3415-497c-bdac-fbf3c639a359)
