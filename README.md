# evcp-server

业务后台

# JDK17

# gradle 8.4

```
├── Readme.md 项目简介
├── config 
│        └── checkstyle
├── ic-iot 充电桩数据接入网关
├── ic-cloud-gateway 微服务网关
├── ic-server SAAS管理平台&运管商管理后台
├── ic-common 公共组件
│        ├── common 公共包
│        ├── ocpi 协议相关
│        ├── ocpi-client 协议数据客户端
│        ├── cpo web平台相关
│        ├── emsp web平台相关
│        └── server web平台相关
├── ic-pay 支付模块
├── ic-cpo CPO相关接口
└── ic-eMSP eMSP相关接口
```

## 部署

### 开发环境

| NO. |  Service Type  |          Service Name          |  Username  |          Password          | Remark                  |
|:---:|:--------------:|:------------------------------:|:----------:|:--------------------------:|-------------------------|
|  1  |     Linux      |     `ssh evcp@10.0.11.50`      |   `evcp`   | `xiji$o92x.nssi5tged3iJli` |                         |
|  2  | Platform Login |   `http://10.0.11.50:10667`    |  `admin`   |         `Aa123456`         |                         |
|  3  | Operator Login |   `http://10.0.11.50:10778`    |            |                            |                         |
|  4  |     Nacos      | `http://10.0.11.50:8848/nacos` |  `nacos`   |          `nacos`           | *`namespace: evcp-dev`* |
|  5  |     MySQL      |       `10.0.11.50:33360`       |   `root`   |        `root@2023`         | *`evcp-*`*              |
|  6  |     Redis      |       `10.0.11.50:16379`       |            |                            | *`database: 0`*         |
|  7  |    MongoDB     |       `10.0.11.50:27917`       | `evcp-dev` |           `evcp`           | *`database: evcp-dev`*  |

### 测试环境

| NO. |  Service Type  |          Service Name          |  Username   |      Password      | Remark                   |
|:---:|:--------------:|:------------------------------:|:-----------:|:------------------:|--------------------------|
|  1  |     Linux      |     `ssh root@10.0.11.239`     |   `root`    | `6x&2U^6f$e$&Utf@` | *8vcpu 16g 100g*         |
|  2  | Platform Login |   `http://10.0.11.239:10667`   |   `admin`   |     `Aa123456`     |                          |
|  3  | Operator Login |   `http://10.0.11.239:10778`   |             |                    |                          |
|  4  |     Nacos      | `http://10.0.11.50:8848/nacos` |   `nacos`   |      `nacos`       | *`namespace: evcp-test`* |
|  5  |     MySQL      |       `10.0.11.21:33068`       |   `root`    |       `root`       | *`evcp-*`*               |
|  6  |     Redis      |       `10.0.11.50:16379`       |             |                    | *`database: 1`*          |
|  7  |    MongoDB     |       `10.0.11.50:27917`       | `evcp-test` |       `evcp`       | *`database: evcp-test`*  |
