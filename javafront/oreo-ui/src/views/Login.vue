<template>
  <div class="login_container">
    <div class="login_box">
      <div class="title_box">
        <p>影院管理登录</p>
      </div>
      <!-- 登录表单区域 -->
      <el-form class="login_form" :model="loginForm" :rules="loginFormRules" ref="loginFormRef">
        <!-- 用户名 -->
        <el-form-item prop="userName">
          <!--双向数据绑定 输入框的值与组件内部变量绑定，实现数据的同步更新-->
          <el-input v-model="loginForm.userName" placeholder="请输入用户名" clearable
                    prefix-icon="iconfont icon-user"></el-input>
        </el-form-item>
        <!-- 密码 -->
        <el-form-item prop="password">
          <el-input v-model="loginForm.password" type="password" placeholder="请输入密码" show-password
                    prefix-icon="iconfont icon-lock"></el-input>
        </el-form-item>
        <!-- 按扭区域 -->
        <el-form-item class="btns">
          <el-button size="medium" class="btn" :round="true" type="primary" @click="login">点击登录</el-button>
          <el-button size="medium" class="btn" :round="true" type="info" @click="resetLoginForm">恢复默认</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  name: "Login",
  data() {
    return {
      //登录表单数据对象
      loginForm: {
        userName: 'zouge',
        password: '123456'
      },
      //表单验证规则
      loginFormRules: {
        //验证用户名
        userName: [
          { required: true, message: "请输入用户名称", trigger: "blur"},
          { min:2, max: 20, message: "长度在2到20个字符之间", trigger: "blur"}
        ],
        //验证密码
        password: [
          { required: true, message: "请输入密码", trigger: "blur"},
          { min:6, max: 16, message: "长度在6到16个字符之间", trigger: "blur"}
        ]
      }
    }
  },
  methods:{
    success(params) {
      console.log(params);
      this.login()
    },
    //点击重置按钮，重置表单
    //通过访问组件中的$refs属性来获取表单的引用，然后调用表单的resetFields()方法来将表单中的所有字段重置为初始值
    resetLoginForm(){
      console.log(this.$refs)
      this.$refs.loginFormRef.resetFields();
    },
    login() {
      //验证合法
      this.$refs.loginFormRef.validate(async valid => {``
        if(!valid) return;
        //axios请求头的Content-Type为application/json，发送POST请求到sysUser/login接口，将登录表单数据以JSON格式传递
        axios.defaults.headers.post['Content-Type'] = 'application/json'
        const { data: res} = await axios.post('sysUser/login', JSON.stringify(this.loginForm));
        if(res.code !== 200) return this.$message.error(res.msg);
        //控制登录权限 如果登录用户的角色没有子角色或第一个子角色为空，则提示没有权限登录
        if(res.data.sysUser.sysRole.children === null || res.data.sysUser.sysRole.children[0] === null) {
          this.$message.error("抱歉，您没有权限登录，请联系管理员获取权限")
          return
        }
        //如果登录成功，则保存token和登录用户信息到sessionStorage中，并根据用户角色设置按钮权限
        this.$message.success("登录成功")
        // console.log(res.data);
        //保存token
        window.sessionStorage.setItem("token", res.data.token)
        window.sessionStorage.setItem("loginUser", JSON.stringify({sysUser : res.data.sysUser, cinemaId : res.data.cinemaId, cinemaName : res.data.cinemaName}));
        // window.sessionStorage.setItem("btnPermission", res.data.sysUser.sysRole.roleId === 1 ? "admin" : "normal")
        window.sessionStorage.setItem("btnPermission", res.data.sysUser.sysRole.roleId === 1 ? "admin" : "admin")
        //导航跳转到首页
        await this.$router.push('/welcome');
      })
    }
  }
}
</script>

<style scoped>
.login_container{
  background-image: url("../assets/login-background.jpg");
  background-size: cover;
  height: 100%;
}

.login_box{
  width: 450px;
  height: 300px;
  background-color: rgba(255,255,255,0.5);
  border-radius: 3px;
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
}

.avatar_box > img{
  width: 100%;
  height: 100%;
  border-radius: 50%;
  background-color: #eee;
}

.title_box{
  text-align: center;
  font-size: 200%;
}

.login_form{
  position: absolute;
  bottom: 0;
  width: 100%;
  padding: 0 20px;
  box-sizing: border-box;
}

.btns{
  display: flex;
  justify-content: center;
}
.btn {
  background-color: #000;
  border-color: #000;
}
.btn:hover {
  background-color: #666;
  border-color: #000;
}
</style>
