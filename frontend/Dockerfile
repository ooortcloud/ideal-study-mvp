# Node.js 기반 이미지로 React 앱 빌드
FROM node:18 AS build
LABEL authors="jin66"


# 작업공간
RUN mkdir /app
WORKDIR /app

COPY package.json ./
COPY package-lock.json ./
RUN npm install

# 애플리케이션 소스 코드 복사
COPY . ./

# React 애플리케이션 빌드
RUN npm run build

# Nginx로 제공
FROM nginx:1.23

# 빌드된 파일을 Nginx로 복사
COPY --from=build /app/build /usr/share/nginx/html

# Nginx 설정 파일 복사 
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Nginx 컨테이너 실행
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
# ENTRYPOINT ["top", "-b"]