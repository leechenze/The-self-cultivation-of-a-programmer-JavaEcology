博学之, 审问之, 慎思之, 明辨之, 笃行之;
零、壹、贰、叁、肆、伍、陆、柒、捌、玖、拾;








零.Nginx简介和安装

  文档参考: https://www.yuque.com/wukong-zorrm/cql6cz/uoz0cq
  资源链接: https://pan.baidu.com/s/1NmCR-vdAcZLouRRn9V1yTA  密码: 1b60
    从Github中宕下来也可以,里面包含所有使用到的资源.

  Nginx是一款轻量级的Web服务器,反向代理服务器,由于他们的内存占用少,启动极快,高并发能力强,在互联网项目中广泛应用.

  MacOS环境:
    nginx 依赖于 pcre、zlib、openssl
      nginx的http模块使用pcre来解析正则表达式，需要在linux上安装pcre库
        brew install pcre
      nginx使用zlib对http包的内容进行gzip，需要在linux上安装zlib库
        brew install zlib
      安装openssl库，让 nginx 支持 https（即在ssl协议上传输http）
        brew install openssl
    下载Nginx
      官网：http://nginx.org/
    
      我这里下载的是1.22.1的包，将你下载的 nginx-1.22.1.tar.gz 放在你想放的路径，
      解压：tar -zxvf nginx-1.22.1.tar.gz
      编译：sudo ./configure，sudo是 super user do 的简称，使用管理员执行命令，主要是为辅助超级管理员完成一些超级管理员不能登录下的操作
      安装：sudo make && make install
      报错：
        mkdir: /usr/local/nginx: Permission denied
        make[1]: *** [install] Error 1
        make: *** [install] Error 2
      问题: 意思为没有权限创建 /usr/local/nginx/ 这个目录, 所以手动创建，并改权限即可
        sudo mkdir -p /usr/local/nginx
        sudo chmod -R 777 /usr/local/nginx
        sudo make && make install
        此时再次查看 /usr/local/nginx 目录已经有了数据, 着success
      运行
        cd /usr/local/nginx/sbin
        ./nginx
      访问:
        http://localhost:80
      配置Nginx全局环境:
        vi /etc/profile
        export NGINX_HOME=/usr/local/nginx
        export PATH=PATH:NGINX_HOME/sbin
        source /etc/profile
      日志目录:
        /usr/local/nginx/logs
      获取日志: 
        tail -f error.log

  CentOS环境:
    MacOS 连接 CentOS:
    ssh root@172.16.168.130
    
    关闭防火墙
      sudo systemctl stop firewalld
      sudo systemctl disable firewalld
    安装yum
      sudo yum install yum-utils net-tools
    配置nginx仓库
      cat > /etc/yum.repos.d/nginx.repo << EOF
      [nginx-stable]
      name=nginx stable repo
      baseurl=http://nginx.org/packages/centos/\$releasever/\$basearch/
      gpgcheck=1
      enabled=1
      gpgkey=https://nginx.org/keys/nginx_signing.key
      module_hotfixes=true
      EOF
    安装nginx
      sudo yum install nginx --nogpgcheck
    日志目录:
      /var/log/nginx/
    获取日志: 
      tail -f error.log
    
    使用systemctl启动、停止、重新加载
      systemctl start nginx

      systemctl status nginx

      #查看日志
      journalctl -xe

      systemctl stop nginx

      systemctl reload nginx

      #配置开机启动
      systemctl enable nginx

    查看是否启动成功:
      curl 127.0.0.1
    
    目录解读:
      配置文件位于 /etc/nginx/nginx.conf , 下列命令会引用/etc/nginx/conf.d目录下所有的.conf文件，这样可以保持主配置文件的简洁，同时配个多个.conf文件方便区分，增加可读性。
      
  常用命令:
    启动:
      nginx
    停止:
      nginx -s stop
    执行完当前请求后停止:
      nginx -s quit
    重新加载配置文件,相当于restart:
      nginx -s reload
    将日志写入一个新的文件
      nginx -s reopen
    测试配置文件:
      nginx -t
  通过systemctl启动nginx
    启动:
      systemctl start nginx
    查看状态:
      systemctl status nginx
  nginx配置文件在 nginx/config
  配置文件结构:
    http {
      server{ # 虚拟主机
        location {
          listen 80；
          server_name localhost;
        }
        location {}
        location {}
      }
      server{
      }
    }
      
  Visual Studio Code 插件安装:
    Remote SSH
      在命令行连接 CentOS 编辑配置文件非常麻烦
      Remote SSH 可以用来远程连接 CentOS
      ssh root@172.16.168.130

      配置免密登陆:
        在本地生成一对密钥:
          ssh-keygen -t rsa -b 4096
        将本地密钥文件拷贝到服务器端:
          ssh-copy-id -i /Users/lee/.ssh/id_rsa.pub root@172.16.168.130
        命令面板输入: > Open SSH Configuration File
          打开/Users/lee/.ssh/config 的配置文件
          添加配置:
            Host 172.16.168.130
            HostName 172.16.168.130
            User root
            PreferredAuthentications publickey
            IdentityFile ~/.ssh/id_rsa
          再次连接CentOS远程地址,不在需要输入密码.
    NGINX Configuration
      一个Nginx配置文件高亮显示的插件
    

    注:后续环境都是在CentOS下进行
    注:后续环境都是在CentOS下进行
    注:后续环境都是在CentOS下进行
    











壹.静态Web配置
  将 /learn/AdminLTE-3.2.0 配置为一个Web服务
  
  问题记录:
    注意：在centos 7中，用systemctl启动nginx可能出现如下错误，
    nginx: [emerg] bind() to 0.0.0.0:8000 failed (13: Permission denied)
    这是由于selinux的安全策略引起的。解决方法如下：
    ● setenforce 0 （临时）
    ● 修改/etc/selinux/config，设置SELINUX=disabled （永久有效，需重启）
  
  文件配置:
    listen
      监听可以配置成IP或端口或IP+端口 
      listen 127.0.0.1:8000;
      listen 127.0.0.1;（ 端口不写,默认80 ） 
      listen 8000; 
      listen *:8000; 
      listen localhost:8000;
    server_name
      server_name主要用于区分，可以随便起。
      也可以使用变量 $hostname 配置成主机名。
      或者配置成域名： example.org   www.example.org   *.example.org 
      如果多个server的端口重复，那么根据域名或者主机名去匹配 server_name 进行选择。
      下面的例子中：
        curl http://localhost:80会访问/usr/share/nginx/html
        curl http://nginx-dev:80会访问/home/AdminLTE-3.2.0

    location
      /请求指向 root 目录
      location 总是从/目录开始匹配，如果有子目录，例如/css，他会指向/static/css, 也就是会直接追加到 配置的root根目录之后.
        location /css {
          root /static;
        }











贰.HTTP反向代理
  正向代理:
    在客户端代理转发请求称为正向代理,例如:VPN.
  反向代理:
    在服务器端的代理转发请求称为方向代理,例如:Nginx
  配置代理服务:
    这里我们用若依管理系统作为方向代理的后台, 将若依系统的8088端口 通过nginx 代理到 8001的端口
      把 ruoyi-admin.jar 包上传到 /learn 目录下
      jar包地址:
        RuoYi是一个基于Spring Boot的后台管理系统，ruoyi-admin.jar资源都在 SummaryOfNotes.md 的同级目录下
        也可以根据官网文档（http://doc.ruoyi.vip/ruoyi/），自己编译打包。
        源码下载：https://codeload.github.com/yangzongzhuan/RuoYi/zip/refs/tags/v4.7.4
      启动ruoyi后台服务，端口为8088:
        java -jar ruoyi-admin.jar
    没有能力或懒的配置若依环境的话也可以使用docker官网的一个getting-started, 反正能跑来一个8088端口的页面就行了
      docker run -d --name docker_demo -p 8088:80 docker/getting-started 这里就用这个代替上面的若依系统进行演示了
      
    nginx配置文件：
      server {
        listen 8001;
        server_name ddemo.localhost;
        location / {
          proxy_pass http://localhost:8088;
        }
      }

    重启Nginx后再次访问 http://172.16.168.130:8001 (访问前首先确定 http://172.16.168.130:8088 这个被代理的地址可以正常访问)
      nginx -s reload

    问题:
      通过Nginx将请求转发给后端服务器, 所以后端服务获取到的 IP 变成了Nginx的主机IP.
    解决:
      用户可以重新定义或追加header信息传递给后端服务器, 可以包含文本,变量及其组合, 默认情况下, 仅重定义两个字段:
        proxy_set_header Host       $proxy_host;
      但由于使用方向代理之后, 后端服务无法获取用户的真实IP, 所以一般反向代理都会设置一下header信息:
        location / {
          # nginx的主机地址: 向后端服务声明是从哪个Nginx转发的请求
          proxy_set_header Host $http_host;
          # 用户端真实的IP，即客户端IP
          proxy_set_header X-Real-IP $remote_addr;
          proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
          proxy_pass http://localhost:8088;
        }
      常用变量:
        $host：nginx主机IP，例如172.16.168.130
        $http_host：nginx主机IP和端口，172.16.168.130:8001
        $proxy_host：localhost:8088，proxy_pass里配置的主机名和端口
        $remote_addr:用户的真实IP，即客户端IP。

      proxy_pass配置说明:
        如果proxy-pass的地址只配置到端口，不包含/或其他路径，那么location将被追加到转发地址中:
          location /some/path/ {
            proxy_pass http://localhost:8080;
          }
          如上配置: 访问 http://localhost/some/path/page.html 将被代理到 http://localhost:8080/some/path/page.html
        如果proxy-pass的地址包括/或其他路径，那么/some/path将会被替换:
          location /some/path/ {
            proxy_pass http://localhost:8080/zh-cn/;
          }
          如上配置: 访问 http://localhost/some/path/page.html 将被代理到 http://localhost:8080/zh-cn/page.html

      非HTTP代理:
        如果要将请求传递到非 HTTP 代理服务器，可以使用下列指令：
          ● fastcgi_pass 将请求转发到FastCGI服务器（多用于PHP）
          ● scgi_pass 将请求转发到SCGI server服务器（多用于PHP）
          ● uwsgi_pass 将请求转发到uwsgi服务器（多用于python）
          ● memcached_pass 将请求转发到memcached服务器
      
    


    

    


    

    
叁.动静分离

  动静分离的好处:
    Apache Tocmat 严格来说是一款java EE服务器，主要是用来处理 servlet请求。处理css、js、图片这些静态文件的IO性能不够好，
    因此，将静态文件交给nginx处理，可以提高系统的访问速度，减少tomcat的请求次数，有效的给后端服务器降压。

  文档参考
    https://www.yuque.com/wukong-zorrm/cql6cz/rvmsl7
  
  代码片段:
    server{
      location / {
        proxy_pass http://localhost:8080/;
      }
      
      location = /html/ie.html {
        root  /home/www/static;
      }
      
      location ^~ /fonts/ {
      
        root  /home/www/static;
      }
      
      location ~ \.(css|js|png|jpg|gif|ico) {
        root /home/www/static;
      }
    }

  location 修饰符
    ● location可以使用修饰符或正则表达式
      修饰符：
        =     等于，严格匹配 ，匹配优先级最高。
        ^~    表示普通字符匹配。使用前缀匹配。如果匹配成功，则不再匹配其它 location。优先级第二高。
        ~     区分大小写
        ~*    不区分大小写
    ● 优先级
      优先级从高到低依次为：。
        1. 精确匹配（=）
        2. 前缀匹配（^~）
        3. 正则匹配（~和～*）
        4. 不写

    代码片段:
      location ^~ /images/ {
        proxy_pass http://localhost:8080;
      }

      location ~ \.jpg {
        proxy_pass http://localhost:8080;
      }

      如上所示:
        /images/1.jpg代理到 http://localhost:8080/images/1.jpg
        /some/path/1.jpg 代理到http://localhost:8080/some/path/1.jpg
      








肆.
伍.
陆.
柒.
捌.
玖.
拾.
