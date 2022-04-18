- sampleshop-jpa

- Java 8, Spring boot 2.6.5, MairaDB (jdbc Driver 3.0.4), hibernate 5.6.7


- 현재까지 issue?

1. DB id/password 는 원래 java encrypt로 암호화 해야하나? 지금은 깃 이그노어로 .yml 파일을 제거해서 업로드함.

## 해결
2. merge 후 되돌리기로 reset HEAD 를 사용하고 다시 issue#1 브랜치로 pull 했는데, 적용이 안됨.
    - 이슈 해결후 브랜치로 push 후 pull request 가 안가짐.
