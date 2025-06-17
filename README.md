
# JiffyMeals Microservices Deployment on Minikube (Develop Environment)

Triển khai hệ thống microservices sử dụng:
- **Minikube** (Kubernetes cục bộ)
- **Jenkins** (CI/CD)
- **Docker** (container hóa)
- **Trivy** (quét bảo mật)
- **SonarQube** (phân tích mã nguồn)
- **Maven** (build tool)
- **PostgreSQL** (database)

---

## 🚀 Mục tiêu

Tự động hóa pipeline CI/CD để:
- Build project Java với Maven
- Phân tích mã bằng SonarQube
- Quét bảo mật Docker Image với Trivy
- Build & push Docker image lên Docker Hub
- Deploy dịch vụ lên Minikube

---

## 📦 Clone Project

```bash
git clone https://github.com/HuuPhat125/jiffy-meals-backend-microservice.git
cd jiffy-meals-backend-microservice
````

---

## 🧩 Jenkins Setup

### ✅ Cài plugin:

* SonarQube Scanner
* Maven Integration
* Kubernetes
* Kubernetes CLI
* Docker Pipeline

### ✅ Thêm Credentials:

1. **GitHub**

   * Kind: *Username & Password*
   * Username: GitHub Email
   * Password: Personal Access Token

2. **DockerHub**

   * Kind: *Username & Password*
   * Username: DockerHub username
   * Password: DockerHub Access Token

3. **SonarQube**

   * Kind: *Secret Text*
   * Secret: Token từ SonarQube

4. **Minikube Config**

   * Export file `~/.kube/config`, thay đường dẫn `certificate-authority`, `client-certificate`, `client-key` bằng nội dung base64
   * Kind: *Secret File*
   * Upload file config đã chỉnh sửa

---

## ☸️ Kubernetes - Minikube

```bash
minikube start
kubectl create namespace jiffymeals
```

---

## ⚙️ Jenkinsfile Pipeline Logic

Pipeline chạy theo `GIT_BRANCH`, gồm các stage:

### 1. Determine Environment

* Xác định nhánh `main`, `develop`, hoặc khác.

### 2. Build

* Dùng Maven để build project:

```bash
mvn clean package -DskipTests
```

### 3. Code Analysis (SonarQube)

```bash
mvn sonar:sonar \
  -Dsonar.projectKey=jiffymeals \
  -Dsonar.host.url=http://<sonarqube-host>:9000 \
  -Dsonar.login=$SONAR_TOKEN
```

### 4. Container Security Scan (Trivy)

* Trivy cần được cài đặt sẵn trong Jenkins agent hoặc sử dụng container image có sẵn Trivy.

```bash
trivy image --severity CRITICAL,HIGH ujusophy/gateway-server:latest
```

* Nếu phát hiện lỗi nghiêm trọng, fail pipeline.

### 5. Publish Image

* Build image:

```bash
docker build -t ujusophy/gateway-server:latest .
```

* Push image:

```bash
docker login -u $DOCKER_USER -p $DOCKER_PASS
docker push ujusophy/gateway-server:latest
```

### 6. Deploy to Kubernetes

```bash
kubectl apply -f deployment.yaml -n jiffymeals
```

---

## 📁 Dockerfile

```dockerfile
FROM openjdk:21-jdk
ARG JAR_FILE=target/gateway-server.jar
WORKDIR /opt/app
COPY ${JAR_FILE} gateway-server.jar
ENTRYPOINT ["java", "-jar", "gateway-server.jar"]
```

---

## 🧾 Kubernetes Manifest (`deployment.yaml`)

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: gateway-server
  namespace: jiffymeals
spec:
  replicas: 1
  selector:
    matchLabels:
      app: gateway-server
  template:
    metadata:
      labels:
        app: gateway-server
    spec:
      containers:
        - name: gateway-server
          image: ujusophy/gateway-server:latest
          ports:
            - containerPort: 8080
---
apiVersion: v1
kind: Service
metadata:
  name: gateway-server
  namespace: jiffymeals
spec:
  selector:
    app: gateway-server
  ports:
    - protocol: TCP
      port: 8080
      targetPort: 8080
      nodePort: 30000
  type: NodePort
```

---

## ✅ Kiểm tra sau deploy

```bash
kubectl get pods -n jiffymeals
kubectl get deployments -n jiffymeals
kubectl get svc -n jiffymeals
```

Truy cập ứng dụng:

```bash
minikube service gateway-server -n jiffymeals
```

---

## 📌 Lưu ý

* Mỗi service nên có `Multibranch Pipeline` riêng trong Jenkins.
* Jenkins, Minikube và Docker nên chạy trên cùng môi trường hoặc cùng host.
---

