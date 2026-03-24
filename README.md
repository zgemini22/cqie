#### 项目初始化
- 设置jdk版本为jdk11
- 首次编译：
  - mvn clean install -Dmaven.test.skip=true
  - mvn clean package -Dmaven.test.skip=true
#### 项目结构
- api-gateway：网关模块
- biz-common：基础通用模块
    - vo：所有接口入回参
        - request
        - response
- service-file：文件服务模块
    - 数据库：gas_data_file
- service-flow：流程引擎服务模块
    - 数据库：gas_data_flow
- service-user：用户服务模块
    - 数据库：gas_data_user
    - 内容：部门、用户、角色、菜单、字典
    - controller：
        - 命名规则：后台接口(Admin)、大屏接口(Bi)、App接口(App)
    - po：实体
    - dao：持久层接口
    - resources.mapper：持久层实现

#### 打包命令

- 本地环境
```
mvn clean package -pl service-api -am -amd -P dev -Dmaven.test.skip=true
```
- 测试环境
```
mvn clean package -pl service-api -am -amd -P test -Dmaven.test.skip=true
```
- 正式环境
```
mvn clean package -pl service-api -am -amd -P prod -Dmaven.test.skip=true
```

#### 接口文档地址

<http://localhost:8080/doc.html>

#### 默认管理员

- 账号、密码: admin

#### 日志输出路径

- /log

#### po、dao、xml生成策略

- biz-common.src.test.java.com.Test,执行main方法即可生成所有服务的po、dao、xml

#### service层规则

- 尽量避免出现service平级引用，会被多个service复用的代码，写到manager层

#### controller层规则

- controller的所有接口，入参、回参必须有对应的vo类，并添加swagger注解，入参、回参不允许直接使用数据库表实体，入回参的类应放在biz-common模块

#### git提交规则

- 写完一个功能或接口时才提交，并在提交信息里简略描述变更
- 推送之前，需保证项目能正常启动运行

#### Token-JWT

- 通用入参header字段token，作为用户登录标识
- 用户登录成功后，基础信息通过JWT加密成token返回前端
- Redis缓存token，并设置有效期
- 需要登录验证的接口，加上注解@Authorization即可
- 登录验证之后，需要使用当前用户信息，则调用ThreadLocalUtil

#### 数据库表基础字段

- ID bigint id
- 创建时间 datatime create_time
- 创建人ID bigint create_id
- 修改时间 datatime update_time
- 修改人ID bigiint update_id
- 是否删除 tinyint(1) deleted

#### 数据库表字段命名规范

- 文件字段以file_开头

#### dao切面编程

- 新增SQL，自动补全创建时间、创建人ID、修改时间、修改人ID、是否删除
- 修改SQL，自动补全修改时间、修改人ID

# cqie
# cqie
