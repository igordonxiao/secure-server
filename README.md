# 客户端授权服务器-Demo

## 技术栈
* Spring Security (2.3.7.RELEASE) + OAuth2

## 功能
* 多客户端支持
* 不同的资源（URL）使用不同的Scope控制
* 不同的客户端分配不同的Scope权限

## 客户端列表
     
| 客户端ID   |客户端密钥|Scope| Basic Authorization   |
|---------|---------|-------|-----------------------| 
| test    |   test  |  test | Basic dGVzdDp0ZXN0   | 
| index   |   index | index | Basic aW5kZXg6aW5kZXg= |


## 接口说明
* `POST` /oauth/token  登录接口
* `GET` /api/test/a    Test资源1
* `GET` /api/test/foo  Test资源2
* `GET` /api/index/a   Index资源

> 说明，拥有Test1资源的客户端(test)无法访问Index资源；拥有Index资源的客户端(index)无法访问Test的资源

## 接口访问示例(以test客户端登录)
+ 登录接口（/oauth/token）    

请求头：`Authorization`：`Basic dGVzdDp0ZXN0`

响应：    
```json
{
    "access_token": "7a487ea2-6335-4d20-ab4a-6ab5e27eea0d",
    "token_type": "bearer",
    "expires_in": 42660,
    "scope": "test"
}
```

+ 访问Test资源1接口（/api/test/a）- 有权限   

请求头：`Authorization`：`Bearer 7a487ea2-6335-4d20-ab4a-6ab5e27eea0d`

响应：
```text
test
```
+ 访问Index资源接口（/api/index/a）- 无资源

请求头：`Authorization`：`Bearer 7a487ea2-6335-4d20-ab4a-6ab5e27eea0d`

响应：
```json
{
    "error": "insufficient_scope",
    "error_description": "Insufficient scope for this resource",
    "scope": "index"
}
```

## 导出与恢复Redis数据库

> 使用工具[*redis-dump-go*](https://github.com/yannh/redis-dump-go)
    
### 导出
```bash
./redis-dump-go -db 9 -host 127.0.0.1 -port 6379 -s > dump.resp
```

命令参考：    

```shell
Usage of ./redis-dump-go:
  -batchSize int
        HSET/RPUSH/SADD/ZADD only add 'batchSize' items at a time (default 1000)
  -db uint
        only dump this database (default: all databases)
  -filter string
        Key filter to use (default "*")
  -host string
        Server host (default "127.0.0.1")
  -n int
        Parallel workers (default 10)
  -noscan
        Use KEYS * instead of SCAN - for Redis <=2.8
  -output string
        Output type - can be resp or commands (default "resp")
  -port int
        Server port (default 6379)
  -s    Silent mode (disable logging of progress / stats)
  -ttl
        Preserve Keys TTL (default true)


```
 
如果有密码，则将密码设置在环境变量`REDISDUMPGO_AUTH`中：

```shell
$ export REDISDUMPGO_AUTH=myRedisPassword
$ redis-dump-go
```

### 恢复    

```bash
redis-cli --pipe < dump.resp
```

输出示例如下：
```shell
All data transferred. Waiting for the last reply...
Last reply received from server.
errors: 0, replies: 5
```

`说明`: 导入到Redis的数据是index客户端的访问Token，导入成功后可直接使用`Authorization`: `Bearer 1854020f-b8b6-47a0-84c3-306faabf2910`访问。