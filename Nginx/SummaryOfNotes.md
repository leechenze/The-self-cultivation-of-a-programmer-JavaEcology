博学之, 审问之, 慎思之, 明辨之, 笃行之;
零、壹、贰、叁、肆、伍、陆、柒、捌、玖、拾;








零.Nginx简介和安装

  文档参考: https://www.yuque.com/wukong-zorrm/cql6cz/uoz0cq

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
    
    



壹.静态Web配置
  
  将 /Users/lee/MySkills/The-self-cultivation-of-a-programmer-JavaEcology/Nginx/AdminLTE-3.2.0 配置为一个Web服务
  
  






贰.







叁.







肆.







伍.







陆.







柒.







捌.







玖.







拾.







