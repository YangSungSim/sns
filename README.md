# sns

### postgrelsql을 heroku에다가 가져다가 사용하기 때문에
### heroku에 접속해서 매번 사용자 이름, 데이터 베이스 이름 ,url을 확인해서 바꿔줘야 함.

#### sprngboot 3버전으로 upgrade하면서 build.gradle 에서 더이상 사용할 수없는 라이브러리를 // 이전 으로 주석처리 후 하단에 버전에 맞는 라이브러리 추가
#### 1.@Type 어노테이션 사용 변경에 따라 수정
#### 2.filterchain 수정
======
#### 1. heroky kafka add-on free service 사용 불가.


 ##### heroku upate
 #####  heroku plugins:install heroku-kafka
 ##### heroku config:get KAFKA_URL
 ##### heroku kafka:info
 #####  heroku kafka:credentials --reset --app fsimmy-sns
 #####  heroku kafka:credentials --help
 #####  heroku kafka --help
 #####  heroku kafka:info --app fsimmy-sns
 #####  heroku kafka:topics:create messages --app fsimmy-sns
 #####  heroku kafka:consumer-groups:create demo-group --app fsimmy-sns
 #####  git push heroku master
 #####  heroku  open --app fsimmy-sns
 #####  heroku config --help
 #####  heroku config:get --help
 #####  heroku config:get KEY --app fsimmy-sns
 #####  heroku config:get KAFKA_URL --app fsimmy-sns
 #####  heroku config:get KAFKA_TRUSTED_CERT --app fsimmy-sns
 #####  heroku config:get KAFKA_CLIENT_CERT_KEY --app fsimmy-sns
 #####  heroku config:get KAFKA_CLIENT_CERT --app fsimmy-sns
