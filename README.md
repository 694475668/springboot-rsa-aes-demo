# -springboot-rsa-aes-demo
RSA和AES前端数据加密，后端数据解密，以及返回参数加密，前端解密，全套源码，由于是市面上常用的是这二种加解密方式，所以就写了这二种，每天会自动更换密钥，后期我会陆续更新其它的加解密算法,技术点采用在新的spring-boot 2.3.1版本+mybatisPlus+mysql数据库
介绍
RSA和AES前端数据加密，后端数据解密，以及返回参数加密，前端解密，全套源码，由于是市面上常用的是这二种加解密方式，所以就写了这二种，每天会自动更换密钥，后期我会陆续更新其它的加解密算法,如果需要运用该代码直接clone项目，然后打包到本地或者私服，在通过注解就可以实现RSA和AES加密解密，无需你写任何的代码。

采用的技术
springboot2.3.1
spring-boot-starter-freemarker
mybatis-plus-boot-starter
安装教程
1.首先先把我的项目clone下来
输入图片说明

2. 通过install打包到本地或者deploy打包到你们的公司私服
输入图片说明

3.在你的项目中引用该依赖
输入图片说明

只需要在你的方法上加上这二个注解就可以实现切换使用不同的加密方式
输入图片说明

输入图片说明

输入图片说明

前端加密解密
RSA模板
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>RSA登陆</title>
    <!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <script src="https://cdn.bootcss.com/jquery/3.4.1/jquery.min.js"></script>
    <script src="/js/rsa/jsencrypt.js"></script>
    <script src="/js/rsa/rsautil.js"></script>
    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js"
            integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
            crossorigin="anonymous"></script>
</head>
<body>
<center>
    <div>
        <form class="form-horizontal">
            <div class="form-group">
                <label for="username" class="col-sm-2 control-label">用户名：</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" name="username" id="username" placeholder="请输入用户名">
                </div>
            </div>
            <div class="form-group">
                <label for="password" class="col-sm-2 control-label">密码：</label>
                <div class="col-sm-3">
                    <input type="password" class="form-control" name="password" id="password" placeholder="请输入密码">
                </div>
            </div>
            <div class="form-group">
                <div>
                    <button type="button" class="btn btn-primary" id="login">登陆</button>
                </div>
            </div>
        </form>
    </div>
</center>

<script>
    $(function () {
        var publicKey;
        //获取服务器的公钥
        $.post("/rsa/publicKey", "text", function (data) {
            publicKey = data;
        })
        $("#login").click(function () {
            //需要转换的json参数对象
            var json = {
                username: $("#username").val(),
                password: $("#password").val()
            }
            //进行参数加密,必须把对象转换json字符串，不然加密不了
            var param = RSAEncrypt(JSON.stringify(json), publicKey)
            //加密后请求服务器
            $.ajax(
                {
                    url: "/rsa/login",
                    data: "{\"requestData\":" + param + "}",
                    type: "post",
                    dataType: "text",
                    contentType: "application/json",
                    success: function (data) {
                        console.log("已经通过服务器公钥加密的数据=====" + data)
                        if (data != null) {
                            //获取服务器的私钥
                            $.post("/rsa/privateKey", "text", function (key) {
                                console.log("私钥=====" + key)
                                //通过私钥进行数据解密
                                var msg = RSAEncryptByFrontEnd(data, key);
                                console.log("数据解密后数据=====" + msg)
                                // 但是在后端加密的时候没有，解密就有那个鬼东西,所有我通过截取把那个鬼东西去掉了
                                var dataJson = msg.substr(msg.indexOf("{"), msg.length);
                                //通过截取数据，不知道为什么后端数据加密后，前端解密出现个无法识别的字符，
                                console.log("截取后的数据=====" + dataJson)
                                //转换成json
                                let parse = JSON.parse(dataJson);
                                console.log("转换成json后获取数据===" + parse.username + "=====" + parse.password)
                            })
                        } else {
                            alert("认证失败")
                        }
                    }
                }
            )
        })
    })
</script>
</body>
</html>
AES模板
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>AES登陆</title>
    <!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <script src="https://cdn.bootcss.com/jquery/3.4.1/jquery.min.js"></script>
    <script src="/js/aes/crypto-js.js"></script>
    <script src="/js/aes/aesutil.js"></script>
    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js"
            integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
            crossorigin="anonymous"></script>
</head>
<body>
<center>
    <div>
        <form class="form-horizontal">
            <div class="form-group">
                <label for="username" class="col-sm-2 control-label">用户名：</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" name="username" id="username" placeholder="请输入用户名">
                </div>
            </div>
            <div class="form-group">
                <label for="password" class="col-sm-2 control-label">密码：</label>
                <div class="col-sm-3">
                    <input type="password" class="form-control" name="password" id="password" placeholder="请输入密码">
                </div>
            </div>
            <div class="form-group">
                <div>
                    <button type="button" class="btn btn-primary" id="login">登陆</button>
                </div>
            </div>
        </form>
    </div>
</center>

<script>
    $(function () {
        alert(AESDecrypt("HBljtGuZwKqOsMVhPT9LfEroiq2VpGWePAyXcUELHRe8WpY0vlFfzRz9dtYvjUd8R9ja11JxPv5AGluGnSI9p8Ja+x62jSCYKnAKkQgVk/06TvWG3OuyZpZcZPuDKXJ71yObUjUKT2WYpic2P5Do/cGd7At39RkqoJgVFaFHfz8=", "8sHn3Uu5bGank662"))
        var key;
        //获取服务器的密钥
        $.post("/aes/key", "text", function (data) {
            key = data;
        })
        $("#login").click(function () {
            //需要转换的json参数对象
            var json = {
                username: $("#username").val(),
                password: $("#password").val()
            }
            //进行参数加密,必须把对象转换json字符串，不然加密不了
            var param = AESEncrypt(JSON.stringify(json), key);
            //加密后请求服务器
            $.ajax(
                {
                    url: "/aes/login",
                    data: "{\"requestData\":" + param + "}",
                    type: "post",
                    //必须返回json不然出现二个双引号,当然你可以截取
                    dataType: "json",
                    contentType: "application/json",
                    success: function (data) {
                        console.log("已经通过服务器公钥加密的数据=====" + data)
                        if (data != null) {
                            console.log("key=====" + key)
                            //通过私钥进行数据解密
                            let aesDecrypt = AESDecrypt(data, key);
                            console.log("数据解密数据=====" + aesDecrypt)
                            let parse = JSON.parse(aesDecrypt);
                            console.log("转换成json后获取数据===" + parse.username + "=====" + parse.password)
                        } else {
                            alert("认证失败")
                        }
                    }
                }
            )
        })
    })
</script>
</body>
</html>
测试效果如下
输入图片说明

输入图片说明

输入图片说明

输入图片说明
