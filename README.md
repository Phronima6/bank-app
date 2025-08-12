# Приложение "Банк"

## Быстрый старт

### Требования
- **Java 21**
- **Maven 3.9.9**
- **Kubectl**
- **Helm**
- **Minikube**
- **Jenkins** (plugin Blue Ocean)
- **Kafka**

## Запуск приложения с использованием Jenkins и K8s

### Шаг 1. Запуск Minikube
```bash
minikube start --vm-driver=docker --cpus=8 --memory=8192
minikube addons enable ingress
```

### Шаг 2. Настройка Jenkins Pipeline
1. Открыть Jenkins с установленным плагином Blue Ocean
2. Добавьте Git-проект со ссылкой на репозиторий проекта
3. Jenkins автоматически запустит пайплайн из `Jenkinsfile`

### Шаг 3. Проверка развертывания
```bash
# Проверьте статус всех pods
kubectl get pods

# Дождитесь запуска всех сервисов
```

### Шаг 4. Настройка доступа
```bash
# Запустите туннель Minikube
minikube tunnel
```

Добавьте в файл `hosts`:
```
127.0.0.1 front-service.bankapp.local
```

Очистите DNS кэш (Windows):
```bash
ipconfig /flushdns
```

### Шаг 5. Доступ к приложению
**Приложение доступно по адресу:** http://front-service.bankapp.local

## Архитектура

### Микросервисы
| Сервис | Описание |
|--------|----------|
| **front** | Веб-интерфейс пользователя |
| **accounts** | Управление пользователями и счетами |
| **exchange** | Курсы валют |
| **blocker** | Система блокировки подозрительных операций |
| **cash** | Кассовые операции |
| **transfer** | Переводы между пользователями |
| **exchange-generator** | Генератор курсов валют |
| **notifications** | Система уведомлений |

### Инфраструктура
| Сервис | Описание |
|--------|----------|
| **keycloak** | Аутентификация и авторизация |
| **postgresql** | База данных (Bitnami Helm Chart) |
| **kafka** | Очередь сообщений (готов к использованию) |
| **nginx-ingress** | Входной контроллер для маршрутизации |

### Функциональность приложения
- ✅ Регистрация и аутентификация пользователей
- ✅ Управление счетами в разных валютах (RUB, USD, CNY)
- ✅ Пополнение и снятие наличных
- ✅ Переводы между пользователями
- ✅ Конвертация валют по актуальным курсам
- ✅ Система уведомлений
- ✅ Защита от подозрительных операций

##  Технологии
### Backend
- **Spring Boot 3.5.0**
- **Spring Security + OAuth2** 
- **PostgreSQL 16** + **Liquibase**
- **Keycloak 26.1.3**
- **OpenFeign** (межсервисное взаимодействие)
### Frontend
- **Thymeleaf** (серверный рендеринг)
- **JavaScript** (AJAX для обновления курсов)
### DevOps & Infrastructure
- **Kubernetes** (оркестрация контейнеров)
- **Helm Charts** (управление K8s ресурсами)
- **Jenkins** (CI/CD пайплайны)
- **Docker** (контейнеризация)
- **Maven** (мультимодульный проект)
- **Minikube** (локальная разработка)
## Безопасность
- **OAuth2/OpenID Connect** через Keycloak
- **JWT токены** для межсервисного взаимодействия
- **Spring Security** на уровне каждого микросервиса
- **Kubernetes RBAC** для управления доступом к ресурсам

## Полезные команды для разработки

### Сборка и загрузка образов в Minikube
```bash
# Паттерн для каждого сервиса
docker build -t service-name:0.0.1-SNAPSHOT ./service-directory
minikube image load service-name:0.0.1-SNAPSHOT  
minikube ssh "docker rmi service-name:0.0.1-SNAPSHOT"

# Примеры для конкретных сервисов:
docker build -t front-service:0.0.1-SNAPSHOT ./front
minikube image load front-service:0.0.1-SNAPSHOT

docker build -t accounts-service:0.0.1-SNAPSHOT ./accounts
minikube image load accounts-service:0.0.1-SNAPSHOT

docker build -t exchange-service:0.0.1-SNAPSHOT ./exchange
minikube image load exchange-service:0.0.1-SNAPSHOT

docker build -t notifications-service:0.0.1-SNAPSHOT ./notifications
minikube image load notifications-service:0.0.1-SNAPSHOT

docker build -t blocker-service:0.0.1-SNAPSHOT ./blocker
minikube image load blocker-service:0.0.1-SNAPSHOT

docker build -t cash-service:0.0.1-SNAPSHOT ./cash
minikube image load cash-service:0.0.1-SNAPSHOT

docker build -t transfer-service:0.0.1-SNAPSHOT ./transfer
minikube image load transfer-service:0.0.1-SNAPSHOT

docker build -t exchange-generator-service:0.0.1-SNAPSHOT ./exchange-generator
minikube image load exchange-generator-service:0.0.1-SNAPSHOT

docker build -t keycloak:pr ./keycloak
minikube image load keycloak:pr
```

### Управление Helm
```bash
# Обновление зависимостей
helm dependency update ./k8s

# Установка приложения
helm install myapp ./k8s

# Переустановка приложения
helm uninstall myapp
helm install myapp ./k8s
```

### Настройка Minikube
```bash
# Запуск с оптимальными ресурсами
minikube start --vm-driver=docker --cpus=8 --memory=8192

# Включение Ingress
minikube addons enable ingress

# Туннель для доступа к сервисам
minikube tunnel
```

### Отладка и мониторинг
```bash
# Проверка статуса подов
kubectl get pods

# Просмотр логов пода
kubectl logs <pod-name>

# Очистка DNS кэша (Windows)
ipconfig /flushdns

# Запуск Zipkin для трассировки
docker run --name zipkin --rm -p 9411:9411 openzipkin/zipkin:3.5
```

---

Студент Шестаков А.В., 4-ая когорта, курс "Мидл Java-разработчик".