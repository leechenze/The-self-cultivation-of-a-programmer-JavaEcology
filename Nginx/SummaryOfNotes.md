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

  完整代码:
    server{
      listen: 8002;
      server_name: ruoyi.tomcat;

      location / {
        proxy_pass http://localhost:8088/;
      }

      location \.(js|css|png|jpg|gif|ico)$ {
          root /home/www/static;
      }

      location = /html/ie.html {
        root /home/www/static;
      }

      location ^~ /fonts/ {
        root /home/www/static;
      }
    }










肆.缓冲区与缓存
  
  缓冲（buffer）:
    缓冲一般放在内存中，如果不适合放入内存（比如超过了指定大小），则会将响应写入磁盘临时文件中。
    启用缓冲后，nginx先将后端的请求响应（response）放入缓冲区中，等到整个响应完成后，再发给客户端。

    客户端往往是用户网络，情况复杂，可能出现网络不稳定，速度较慢的情况。
    而nginx到后端server一般处于同一个机房或者区域，网速稳定且速度极快。
    
    如果禁用了缓冲，则在客户端从代理服务器接收响应时，响应将同步发送到客户端。对于需要尽快开始接收响应的快速交互式客户端，此行为可能是可取的。
    
    如果禁用了缓冲，则在客户端从代理服务器接收响应时，响应将同步发送到客户端。对于需要尽快开始接收响应的快速交互式客户端，此行为可能是可取的。
    这就会带来一个问题：因为客户端到nginx的网速过慢，导致nginx只能以一个较慢的速度将响应传给客户端；进而导致后端server也只能以同样较慢的速度传递响应给nginx，造成一次请求连接耗时过长。
    在高并发的情况下，后端server可能会出现大量的连接积压，最终拖垮server端。

    开启代理缓冲后，nginx可以用较快的速度尽可能将响应体读取并缓冲到本地内存或磁盘中，然后同时根据客户端的网络质量以合适的网速将响应传递给客户端。
    这样既解决了server端连接过多的问题，也保证了能持续稳定的像客户端传递响应。

    使用proxy_buffering启用和禁用缓冲，nginx默认为 on 启用缓冲，若要关闭，设置为 off  。 
      proxy_buffering off;

    proxy_buffers 指令设置每个连接读取响应的缓冲区的大小和数量 。默认情况下，缓冲区大小等于一个内存页，4K 或 8K，具体取决于操作系统。
    
    来自后端服务器响应的第一部分存储在单独的缓冲区中，其大小通过 proxy_buffer_size 指令进行设置，此部分通常是相对较小的响应headers，通常将其设置成小于默认值。
      location / {
        proxy_buffers 16 4k;
        proxy_buffer_size 2k;
        proxy_pass http://localhost:8088;
      }

    如果整个响应不适合存到内存里，则将其中的一部分保存到磁盘上的临时文件中.
    proxy_max_temp_file_size  设置临时文件的最大值。
    proxy_temp_file_write_size  设置一次写入临时文件的大小。
    
    
  缓存(cache):
    启用缓存后，nginx将响应保存在磁盘中，返回给客户端的数据首先从缓存中获取，这样子相同的请求不用每次都发送给后端服务器，减少到后端请求的数量。

    缓冲优化Nginx到后端连接的响应时间
    缓存减少连接到后端的请求数量,提高相客户端的响应速度

    启用缓存，需要在http上下文中使用 proxy_cache_path 指令，定义缓存的本地文件目录，名称和大小。
    缓存区可以被多个server共享，使用proxy_cache 指定使用哪个缓存区。
      http {
        proxy_cache_path /data/nginx/cache keys_zone=mycache:10m;
        server {
          proxy_cache mycache;
          location / {
            proxy_pass http://localhost:8000;
          }
        }
      }
    缓存目录的文件名是 proxy_cache_key 的MD5值。 
    例如：/data/nginx/cache/c/29/b7f54b2df7773722d382f4809d65029c

    proxy_cache_key 默认设置如下：
      proxy_cache_key $scheme$proxy_host$uri$is_args$args;
    也可以自定义缓存的键，例如
      proxy_cache_key "$host$request_uri$cookie_user";
      
    缓存不应该设置的太敏感，可以使用proxy_cache_min_uses设置相同的key的请求，
    访问次数超过指定数量才会被缓存:
      proxy_cache_min_uses 5;

    默认情况下，响应无限期地保留在缓存中。仅当缓存超过最大配置大小时，按照时间删除最旧的数据。

    示例:
      proxy_cache_path /var/cache/nginx/data keys_zone=mycache:10m;

      server {

          listen 8001;
          server_name ruoyi.localhost;
          
          location / {
              #设置buffer
              proxy_buffers 16 4k;
              proxy_buffer_size 2k;
              proxy_pass http://localhost:8088;        

          }


          location ~ \.(js|css|png|jpg|gif|ico) {
              #设置cache
              proxy_cache mycache;
              proxy_cache_valid 200 302 10m;
              proxy_cache_valid 404      1m;
              proxy_cache_valid any 5m;

              proxy_pass http://localhost:8088;  
          }

          location = /html/ie.html {

              proxy_cache mycache;
              proxy_cache_valid 200 302 10m;
              proxy_cache_valid 404      1m;
              proxy_cache_valid any 5m;

              proxy_pass http://localhost:8088;  
          }

          location ^~ /fonts/ {

              proxy_cache mycache;
              proxy_cache_valid 200 302 10m;
              proxy_cache_valid 404      1m;
              proxy_cache_valid any 5m;

              proxy_pass http://localhost:8088;  
          }

      }











伍.负载均衡

  跨多个应用程序实例的负载平衡是一种常用技术，用于优化资源利用率、最大化吞吐量、减少延迟和确保容错配置。使用nginx作为非常有效的HTTP负载平衡器，将流量分配到多个应用程序服务器，可以提升Web应用程序的性能，提高扩展性和可靠性。

  使用 upstream定义一组服务.
  注意：upstream 位于 http上下文中，与server 并列，不要放在server中。

    upstream ruoyi-apps {
      #不写，采用轮循机制
      server localhost:8080;
      server localhost:8088;
    }

    server {
      listen 8003;
      server_name ruoyi.loadbalance;
      
      location / {
        proxy_pass http://ruoyi-apps;
      }
    }

  负载均衡策略
    1.轮循机制（round-robin）
      默认机制，以轮循机制方式分发。
    2.最小连接（least-connected ）
      将下一个请求分配给活动连接数最少的服务器（较为空闲的服务器）。
        upstream backend {
          least_conn;
          server backend1.example.com;
          server backend2.example.com;
        }
      请注意，使用轮循机制或最少连接的负载平衡，每个客户端的请求都可能分发到不同的服务器。不能保证同一客户端将始终定向到同一服务器。
    3.ip-hash 
      客户端的 IP 地址将用作哈希键，来自同一个ip的请求会被转发到相同的服务器。
        upstream backend {
          ip_hash;
          server backend1.example.com;
          server backend2.example.com;
        }
        此方法可确保来自同一客户端的请求将始终定向到同一服务器，除非此服务器不可用。
    4.hash 
      通用hash，允许用户自定义hash的key，key可以是字符串、变量或组合。
      例如，key可以是配对的源 IP 地址和端口，也可以是 URI，如以下示例所示：
        upstream backend {
          hash $request_uri consistent;
          server backend1.example.com;
          server backend2.example.com;
        }
        请注意：基于 IP 的哈希算法存在一个问题，那就是当有一个上游服务器宕机或者扩容的时候，会引发大量的路由变更，进而引发连锁反应，导致大量缓存失效等问题。
      consistent参数启用了名为 ketama 的一致哈希算法，如果在上游组中添加或删除服务器，只会重新映射部分键，从而最大限度地减少缓存失效。
        假设我们基于 key 来做 hash，现在有 4 台上游服务器，如果 hash 算法对 key 取模，请求根据用户定义的哈希键值均匀分布在所有上游服务器之间。。
        当有一台服务器宕机的时候，就需要重新对 key 进行 hash，最后会发现所有的对应关系全都失效了，从而会引发缓存大范围失效,此时就需指定consistent参数最大程度减少缓存的大范围失效.

    5.随机 (random）
      每个请求都将传递到随机选择的服务器。
      two是可选参数，NGINX 在考虑服务器权重的情况下随机选择两台服务器，然后使用指定的方法选择其中一台，
      默认为选择连接数最少（least_conn）的服务器。
        upstream backend {
          random two least_conn;
          server backend1.example.com;
          server backend2.example.com;
          server backend3.example.com;
          server backend4.example.com;
        }
    6.权重（weight）
      upstream my-server {
        server performance.server weight=3;
        server app1.server;
        server app2.server;
      }
      如上所示，每 5 个新请求将按如下方式分布在应用程序实例中：3 个请求将定向到performance.server，一个请求将转到app1.server，另一个请求将转到app2.server。
    7.健康检查
      在反向代理中，如果后端服务器在某个周期内响应失败次数超过规定值，nginx会将此服务器标记为失败，并在之后的一个周期不再将请求发送给这台服务器。
        通过 fail_timeout 来设置检查周期，默认为10秒。
        通过 max_fails 来设置检查失败次数，默认为1次。
      在以下示例中，如果NGINX无法向服务器发送请求或在30秒内请求失败次数超过3次，则会将服务器标记为不可用30秒。
        upstream backend {
          server backend1.example.com;
          server backend2.example.com max_fails=3 fail_timeout=30s; 
        }
    








陆.HTTPS配置

  HTTPS 协议是由HTTP 加上TLS/SSL 协议构建的可进行加密传输、身份认证的网络协议，主要通过数字证书、加密算法、非对称密钥等技术完成互联网数据传输加密，实现互联网传输安全保护。

  启用https需要在端口后加上 ssl
    server {
      listen 443 ssl;
    }
    
  生成证书:
    1.生成一个Key,注入一个密码:
      openssl genrsa -des3 -out server.key 2048
    2.请求生成一个新的证书:
      openssl req -new -key server.key -out server.csr
    3.证书生成:
      openssl x509 -req -days 365 -in server.csr -signkey server.key -out server.crt
    4.生成三个文件
      server.crt
      server.csr
      server.key
  配置ssl:
    server {
      listen              443 ssl;
      server_name         ruoyi.https;
      ssl_certificate     /home/ssl/server.crt;
      ssl_certificate_key /home/ssl/server.key;
      ssl_protocols       TLSv1 TLSv1.1 TLSv1.2;
      ssl_ciphers         HIGH:!aNULL:!MD5;
    
      location / {
          proxy_pass http://localhost:8088;
      }
    }
  如果设置了密码，需要加上:
    server{
      ...
      ssl_password_file   /home/ssl/cert.pass;
      ...
    } 
  https优化:
    SSL 操作会消耗额外的 CPU 资源。CPU 占用最多的操作是 SSL 握手。有两种方法可以最大程度地减少每个客户端的这些操作数：
      ● 使保持活动连接能够通过一个连接发送多个请求
      ● 重用 SSL 会话参数以避免并行连接和后续连接的 SSL 握手
    会话存储在工作进程之间共享并由 ssl_session_cache 指令配置的 SSL 会话缓存中。
    一兆字节的缓存包含大约 4000 个会话。默认缓存超时为 5 分钟。
    可以使用 ssl_session_timeout 指令增加此超时。
    以下是针对具有 10 MB 共享会话缓存的多核系统优化的示例配置：
      ssl_session_cache   shared:SSL:10m;
      ssl_session_timeout 10m;
      
      
      
      
        
      
      
      

柒.TCP反向代理

  TCP代理用 stream 关键字用来定义
    #HTTP代理
    http {
      server {
        listen 8002;
        proxy_pass http://localhost:8080/;
      }
    }

    #TCP代理
    stream {
      server {
        listen 13306;
        proxy_pass localhost:3306;
      }
    }

  tcp负载均衡:
    stream {
      upstream backend-mysql {
        server localhost:3306;
        server localhost:3307;
        keepalive 8;
      }
      server {
        listen 13306;
        proxy_pass backend-mysql;
      }
    }
    使用keepalive定义连接池里空闲连接的数量。
    keepalive_timeout 默认60s。如果连接池里的连接空闲时间超过这个值，则连接关闭。
    
    在最简单的 HTTP 实现中，客户端打开新连接，写入请求，读取响应，然后关闭连接以释放关联的资源。

    在客户端读取响应后，保持连接处于打开状态，因此可以将其重新用于后续请求。

    使用 keepalive 指令启用从 NGINX Plus 到上游服务器的保持活动连接，定义在每个工作进程的缓存中保留的与上游服务器的空闲保持活动连接的最大数量。当超过此数字时，将关闭最近最少使用的连接。如果没有 keepalives，您将增加更多的开销，并且连接和临时端口都效率低下。
    现代 Web 浏览器通常会打开 6 到 8 个保持连接。








  
捌.重写

  nginx有两个重写指令：return和rewrite

  return
    服务端停止处理并将状态码status code返回给客户端,通常有以下几种写法:
      return code URL
      return code text
      return code
      return URL
    强制所有请求使用Https
      错误写法:
        server {
          listen 8003;
          server_name ruoyi.loadbalance;
          return 301 https://localhost:8004;
        }
      正确写法:
        server {
          listen 8003;
          server_name ruoyi.loadbalance;
          return 301 https://192.168.56.105:8004;
        }

    转发和重定向
      转发是服务端行为，重定向是客户端行为。
      转发:
        发向代理proxy_pass属于转发，浏览器的访问栏输入的地址不会发生变化。
      重定向:
        return，rewrite属于重定向，在客户端进行。浏览器的访问栏输入的地址会发生变化。

      域名迁移，不让用户收藏的链接或者搜索引擎的链接失效
      将请求从 www.old-name.com old-name.com 永久重定向到 www.new-name.com，包含http和https请求
        server {
          listen 80;
          listen 443 ssl;
          server_name www.old-name.com old-name.com;
          return 301 $scheme://www.new-name.com$request_uri;
        }
      由于捕获了域名后面的 URL 部分，因此，如果新旧网站之间存在一对一的页面对应关系 (例如, www.new-name.com/about 具有与 www.old-name.com/about 相同的基本内容),则此重写是合适的。
      如果除了更改域名之外还重新组织了网站，则通过省略以下内容，将所有请求重定向到主页可能会更安全
        server {
          listen 80;
          listen 443 ssl;
          server_name www.old-name.com old-name.com;
          return 301 $scheme://www.new-name.com;
        }

    添加www
      # 输入域名自动添加 'www', 就是通过转发, www.domain.com实现的.
      server {
          listen 80;
          listen 443 ssl;
          server_name domain.com;
          return 301 $scheme://www.domain.com$request_uri;
      }
  
  rewrite
    如果指定的正则表达式与请求 URI 匹配，则 URI 将按照字符串中的指定进行更改。指令按其在配置文件中出现的先后顺序执行。
      server {
        # ...
        rewrite ^(/download/.*)/media/(\w+)\.?.*$ $1/mp3/$2.mp3 last;
        rewrite ^(/download/.*)/audio/(\w+)\.?.*$ $1/mp3/$2.ra  last;
        return  403;
        # ...
      }
      上面是使用该指令的示例 NGINX 重写规则。它匹配以字符串 /download 开头的 URL，然后在路径后面的某个位置包含 /media/ 或 /audio/ 目录。
      它将这些元素替换为 /mp3/，并添加相应的文件扩展名，.mp3 或 .ra。和 变量捕获未更改的路径元素。
      例如，/download/cdn-west/media/file1 变成了 /download/cdn-west/mp3/file1.mp3。
      如果文件名上有扩展名（如 .flv），则表达式会将其剥离，并将其替换为.mp3。
    如果字符串包含新的请求参数，则以前的请求参数将追加到这些参数之后。如果不需要这样做，则在替换字符串的末尾放置一个问号可以避免附加它们，例如：replacement
      rewrite ^/users/(.*)$ /show?user=$1? last;

  last与break
    last：如果当前规则匹配，停止处理后续rewrite规则，使用重写后的路径，重新搜索location及其块内指令
    break:如果当前规则匹配，停止处理后续rewrite规则，执行{}块内其他指令

    不使用last和break
      在root /home/AdminLTE-3.2.0/pages下创建一个1.txt，里面内容是this is a file
      访问 http://192.168.56.105:8000/old/1.txt
        server {
            listen 8000;
            server_name nginx-dev;
            rewrite_log on;
            location / {
              rewrite ^/old/(.*) /new/$1;
              rewrite ^/new/(.*) /pages/$1;
              #根目录
              root /home/AdminLTE-3.2.0;
              #首页
              index index.html index2.html index3.html;
            }
            location  /pages/1.txt {
              return 200 "this is rewrite test!";
            }
        }
      默认按顺序执行。
      结果: this is rewrite test!
      
      
    使用break
      访问 http://192.168.56.105:8000/old/1.txt
      1.匹配到了rewrite ^/old/(.*) /new/$1
      2.break指令不执行后续的rewrite规则,以新的/new/1.txt路径去执行块内的其他指令
      3.去root目录下寻找文件, 由于不再村/home/AdminLTE-3.2.0/new/1.txt这个文件，返回404
        server {
          listen 8000;
          server_name nginx-dev;
          rewrite_log on;
          location / {
              rewrite ^/old/(.*) /new/$1 break;
              rewrite ^/new/(.*) /pages/$1;
              #根目录
              root /home/AdminLTE-3.2.0;
              #首页
              index index.html index2.html index3.html;
          }
          location  /pages/1.txt {
              return 200 "this is rewrite test!";
          }
        }
      结果: 404 Not Found
    
    使用last
      访问 http://192.168.56.105:8000/old/1.txt
      1.匹配到了rewrite ^/old/(.*) /new/$1
      2.last指令不执行后续的rewrite规则,以新的/new/1.txt路径去匹配location
      3.先匹配到location /, 有匹配到location里的rewrite ^/new/(.*) /pages/$1规则，重定向到
      /pages/1.txt
      4.匹配到了location /pages/1.txt ，于是返回了this is rewrite test!
        server {
            listen 8000;
            server_name nginx-dev;
            rewrite_log on;
            location / {
                rewrite ^/old/(.*) /new/$1 last;
                rewrite ^/new/(.*) /pages/$1;
                #根目录
                root /home/AdminLTE-3.2.0;
                #首页
                index index.html index2.html index3.html;
            }
            location  /pages/1.txt {
                return 200 "this is rewrite test!";
            }
        }
      结果：this is rewrite test!
      日志: 通过 tail -f log/error.log 查看
      











玖.其他常见指令

  gzip压缩
    压缩响应通常会显著减小传输数据的大小。但是，由于压缩发生在运行时，因此它还会增加相当大的处理开销，从而对性能产生负面影响。NGINX在将响应发送到客户端之前执行压缩，但如果后端服务器已经对内容进行了压缩，则nginx不会再压缩。
    若要启用压缩，请在参数中包含 gzip 指令。
      gzip on; 
      gzip_types text/plain application/xml; 
      gzip_min_length 1000; 
    默认情况下，NGINX仅使用压缩MIME类型是text/html的响应。若要使用其他 MIME 类型压缩响应，可以使用 gzip_types 指令并列出其他类型。
    若要指定压缩响应的最小长度，请使用 gzip_min_length 指令。默认值为 20 个字节（此处调整为 1000),表示超过1000直接的内容将被压缩

  sendfile
    默认情况下，NGINX处理文件传输本身，并在发送之前将文件复制到缓冲区中。启用 sendfile 指令可消除将数据复制到缓冲区的步骤，直接将一个文件复制到另一个文件。
    启用sendfile，类似Java中的零拷贝（zero copy）
      location /download {
        sendfile           on;
        tcp_nopush         on; 
        #... 
      } 
    将 tcp_nopush 指令与 sendfile 指令一起使用。这使NGINX能够在获得数据块后立即在一个数据包中发送HTTP响应标头。
  
  try_files
    try_files指令可用于检查指定的文件或目录是否存在;如果不存在，则重定向到指定位置。
    如下，如果原始URI对应的文件不存在，NGINX将内部重定向到/www/data/images/default.gif
      server {
        root /www/data;
        location /images/ {
          try_files $uri /images/default.gif;
        }
      }
    最后一个参数也可以是状态代码（状态码之前需要加上等号）。
    在下面的示例中，如果指令的所有参数都无法解析为现有文件或目录，则会返回404错误。  
      location / {
          try_files $uri $uri/ $uri.html =404;
      }
    在下一个示例中，如果原始 URI 和附加尾随斜杠的 URI 都没有解析到现有文件或目录中，则请求将重定向到命名位置，该位置会将其传递到代理服务器。
      location / {
        try_files $uri $uri/ @backend;
      }
      location @backend {
        proxy_pass http://backend.example.com;
      }

  error_page
    为错误指定显示的页面。值可以包含变量。
      error_page 404             /404.html; 
      error_page 500 502 503 504 /50x.html;











拾.推荐写法及注意事项

  推荐写法:
    1.重复的配置可继承自父级
      例如:
        server {
          server_name www.example.com;
          
          location / {
            root /var/www/nginx-default/;
            # [...]
          }
          location /foo {
            root /var/www/nginx-default/;
            # [...]
          }
          location /bar {
            root /some/other/place;
          # [...]
          }
        }
      推荐写法:
        server {
            server_name www.example.com;
            root /var/www/nginx-default/;
            
            location / {
                # root继承父级配置
                # [...]
            }
            location /foo {
                # root继承父级配置
                # [...]
            }
            location /bar {
                # 覆盖
                root /some/other/place;
                # [...]
            }
        }
    
    2.不要将所有请求都代理到后端服务器
      不推荐:
        location / {
            proxy_pass http://localhost:8088;        
        }
      推荐:
        server {
          listen 8002;
          server_name ruoyi.tomcat;
          root /home/www/static;

          location / {
              try_files $uri $uri/ @proxy;
          }

          location @proxy {
              proxy_set_header Host $http_host;
              proxy_set_header X-Real-IP $remote_addr;
              proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;

              proxy_pass http://localhost:8080;
          }
        }

    3.若非必要，不要缓存动态请求，只缓存静态文件
      nginx关于缓存的指令非常多, 由于篇幅的原因就不深入研究记录了.
        proxy_cache
        proxy_cache_background_update
        proxy_cache_bypass
        proxy_cache_convert_head
        proxy_cache_key
        proxy_cache_lock
        proxy_cache_lock_age
        proxy_cache_lock_timeout
        proxy_cache_max_range_offset
        proxy_cache_methods
        proxy_cache_min_uses
        proxy_cache_path
        proxy_cache_purge
        proxy_cache_revalidate
        proxy_cache_use_stale
        proxy_cache_valid
        proxy_no_cache
      由于nginx服务端缓存非常复杂，在使用缓存的时候，我们要清楚的知道在什么条件下，哪些文件会被缓存。
      在配置文件中，最好能够清晰的指定哪些文件使用缓存。
    
    4.检查文件是否存在使用try_files代替if -f
      不推荐用法：
        server {
          root /var/www/example.com;
          location / {
              if (!-f $request_filename) {
                  break;
              }
          }
        }
      推荐用法：
        server {
            root /var/www/example.com;
            location / {
                try_files $uri $uri/ /index.html;
            }
        }
    
    5.在重写路径中包含http://或https://
      #推荐写法
      rewrite ^ http://example.com permanent; 
      #不推荐的写法
      rewrite ^ example.com permanent; 
    
    6.保持重写规则简单干净
      例如下面的这个例子可以变得更简单易懂。
        #复杂的写法
        rewrite ^/(.*)$ http://example.com/$1 permanent; 
        #简单有效的写法
        rewrite ^ http://example.com$request_uri? permanent; 
        return 301 http://example.com$request_uri; 

  注意事项:
    1.正确的配置未生效，请清除浏览器缓存
      当你确定修改的配置的正确的，但是未生效，请清除浏览器缓存或者禁用浏览器缓存。
    2.在HTTPS中不启用 SSLv3
      由于 SSLv3 中存在 POODLE 漏洞，建议不要在启用了 SSL 的站点中使用 SSLv3。您可以使用以下行非常轻松地禁用 SSLv3，并仅提供 TLS 协议：
        ssl_protocols TLSv1 TLSv1.1 TLSv1.2;
    3.不要将 root 目录配置成 / 或 /root。
      错误用法: 这样就会使Linux系统的所有目录都可以通过nginx和web一览无遗, 极大的安全漏洞, 尤其是配合第四步一起使用(chmod 超级权限)
        server {
          #错误用法
          root /;
          
          location /project/path {
            #错误用法
            root /root;
          }
        }
    4.谨慎使用chmod 777
      这可能是解决问题最简单的方式，同时也说明，你没有真的弄清楚哪里出了问题。
      可以使用namei -om /path/to/check显示路径上的所有权限，并找到问题的根本原因。
    5.不要将部署的项目拷贝到默认目录下
      升级或更新nginx的时候，默认目录可能被覆盖。
    