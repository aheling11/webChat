# webChat
A web-based instant messaging application

## 技术栈
- spring boot
- netty
- mybatis

## 已完成

- 登录接口 /user/login
- 注册接口 /user/register
- 查找用户 /user/search
- 发送加好友请求 /user/addFriendRequest
- 用户查询收到的好友请求 /user/queryFriendRequest
- 用户处理收到的好友请求 /user/operFriendRequest
- 返回用户的好友列表 /user/queryFriends
- 聊天模块
    - 连接到netty服务器
    - 发送消息
    - 接受消息
    - 签收消息

## 待完成
- 聊天模块
    - 推送离线消息
---
## 接口说明


服务器地址端口号：serverUrl:8900

netty服务器端口：ws://serverUrl:8888/ws

### 用户模块

#### 统一说明：

登录和注册接口的请求头中加上

>Content-Type：application/json

以json格式请求，request body内容如下所示

```
{
    "username":"aheling11", 
    "password":"heling11",
    "nickname":"wudige"
}
```

返回json格式,如下所示：

```
{
    "status": 200, #status状态只有200和500，错误信息请看msg
    "msg": "OK",   
    "data": { #data内容根据接口不同
        "id": "123",
        "username": "432",
        "nickname": "543",
        "face_image": "",
        "face_image_big": "",
        "qrcode": ""
    }

}
```
其他用户模块的接口直接传参数就OK



具体说明：

---- 

### 注册:
- /user/register

请求类型：POST


参数 | 数据类型 | 说明
:-: | :-: | :-: |
username| String | 用户名
password| String | 密码
nickname| String | 昵称


请求类型：POST

参数 | 数据类型 | 说明
:-: | :-: | :-: |
username| String | 用户名
password| String | 密码
nickname| String | 昵称



返回json格式
```
{
    "status": 200, 
    "msg": "OK",
    "data": {
        "id": "123",
        "username": "432",
        "nickname": "543",
        "face_image": "",
        "face_image_big": "",
        "qrcode": ""
    }

}
```

### 登录：

- /user/login

请求类型：POST

参数 | 数据类型 | 说明
:-: | :-: | :-: |
username| String | 用户名
password| String | 密码

返回json格式

```

{
    "status": 200,
    "msg": "OK",
    "data": {
        "id": "AAASF",
        "username": "ATEST",
        "nickname": "alice",
        "face_image": "",
        "face_image_big": "",
        "qrcode": ""
    }
}


```

### 搜索用户

- /user/search

    请求类型：POST

    参数 | 数据类型 | 说明
    :-: | :-: | :-: |
    myUserId| String | 我的用户Id
    friendUsername| String | 密码


### 添加好友

请求类型：POST

参数 | 数据类型 | 说明
 :-: | :-: | :-: |
username | String | 用户名
password | String | 密码

- /user/search

请求类型：POST

参数 | 数据类型 | 说明
:-: | :-: | :-: |
myUserId| String | 我的用户Id
friendUsername| String | 密码

### 用户查询收到的好友请求 

/user/queryFriendRequest

请求类型：POST

参数 | 数据类型 | 说明
:-: | :-: | :-: |
userId| String | 我的用户Id



### 用户处理收到的好友请求

 /user/operFriendRequest

 参数 | 数据类型 | 说明
:-: | :-: | :-: |
sendUserId | String | 发送方用户ID
acceptUerId| String | 接收方用户ID
operType| Integer | 操作类型，0：忽略该请求，1：同意


### 返回用户的好友列表 

/user/queryFriends


参数 | 数据类型 | 说明
:-: | :-: | :-: |
myUserId | String | 我的用户Id




   

## 聊天模块

也传json，如下所示
```{
    "action":2,
    "chatMsg":
    {
        "senderId":"1911220XS8GMC1KP",
        "receiverId":"191121D7BMD4RCSW",
        "msg":"ddd",
        "msgId":null
    },
    "extand":null 
   }
```

参数 | 数据类型 | 说明
:-: | :-: | :-: |
action| Integer | "1"：websocket连接，“2”：发送消息，“3”签收消息
chatMsg| json对象 | senderId：发送消息的用户的ID，receiverID：接受消息的用户的ID，msg：发送的消息内容，msgID：消息本身的ID
extand| String | 额外拓展字段，签收消息的IDs放在这里

说明：
1. 当前端建立websocket连接时，请发送action=1的请求，后端将绑定userID和这次websocket连接.
2. 然后发送消息时带上action=2，代表是正常发送消息。
3. 签收消息时在extand里加MsgId，以，为分隔符，action=3

