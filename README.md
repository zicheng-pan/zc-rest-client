# zc-rest-client
自己实现的一个restclient，测试案例请启动BootstrapTest类
由于项目是单纯的maven项目，简单起见，使用了python flask来进行模拟服务端交互

运行需要安装flask
pip install flask

###可以得到结果如下
测试 get 请求
HTTP/1.0 200 OKContent-Type: text/html; charset=utf-8Content-Length: 31Server: Werkzeug/2.0.1 Python/3.8.6Date: Wed, 21 Jul 2021 16:20:26 GMT{"code": "bbb", "store": "aaa"}
测试 post 请求
HTTP/1.0 200 OKContent-Type: text/html; charset=utf-8Content-Length: 27Server: Werkzeug/2.0.1 Python/3.8.6Date: Wed, 21 Jul 2021 16:20:26 GMTquery1=query1&query2=query2
模拟测试 post 请求 /services/actuator/shutdown 接口
HTTP/1.0 200 OKContent-Type: text/html; charset=utf-8Content-Length: 9Server: Werkzeug/2.0.1 Python/3.8.6Date: Wed, 21 Jul 2021 16:20:28 GMTshut down