# CatFood-v1 开发环境指南

本文档将指导您如何设置和管理本项目的开发环境。

## 先决条件

在开始之前，请确保您的系统上已安装以下软件：
- Docker
- Docker Compose

## 开发环境

开发环境使用 `docker-compose-dev.yml` 文件进行配置。您需要使用 `-f` 参数指定此文件，并使用 `--profile` 参数选择您希望使用的数据库（PostgreSQL 或 MySQL）。

### 启动开发环境

您可以根据需要选择启动包含 PostgreSQL 或 MySQL 的环境。

- **使用 PostgreSQL**:
  ```shell
  docker-compose -f docker-compose-dev.yml --profile postgres up -d
  ```

- **使用 MySQL**:
  ```shell
  docker-compose -f docker-compose-dev.yml --profile mysql up -d
  ```

### 停止开发环境

停止环境时，同样需要指定您之前选择的配置文件和 profile。

- **停止 PostgreSQL 环境**:
  ```shell
  docker-compose -f docker-compose-dev.yml --profile postgres down
  ```

- **停止 MySQL 环境**:
  ```shell
  docker-compose -f docker-compose-dev.yml --profile mysql down
  ```

## 重要提示

- **分离的配置**: 开发和生产环境的 Docker Compose 配置是分开的，这是为了避免配置混淆。
- **专注单一环境**: 请不要在同一个目录下同时启动生产和开发环境。一次只专注于一个目标。
