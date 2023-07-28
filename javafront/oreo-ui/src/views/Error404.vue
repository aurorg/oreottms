<template>
  <div class="bg-purple">
    <div class="stars">
      <div class="central-body">
        <img class="image-404" src="../assets/404images/404.svg" width="300px">
        <el-button @click="toWelcome" style="margin-top: 20px" type="info" plain round>返回主页</el-button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Error404",
  methods: {
    toWelcome(){
      this.$router.push('/Welcome')
      return false
    }
  }
}
</script>

<style scoped>



html,
body {
  margin: 0;
  width: 100%;
  height: 100%;
  font-family: 'Times New Roman', Times, serif;
  font-weight: 300;
  -webkit-user-select: none;
  /* Safari 3.1+ */
  -moz-user-select: none;
  /* Firefox 2+ */
  -ms-user-select: none;
  /* IE 10+ */
  user-select: none;
  /* Standard syntax */
}

.bg-purple {
  background: url("../assets/404images/bg_purple.png");
  background-repeat: repeat-x;
  background-size: cover;
  background-position: left top;
  height: 100%;
  overflow: hidden;

}


ul {
  list-style-type: none;
  margin: 0;
  padding: 0;
  /*    overflow: hidden;*/
  display: flex;
  align-items: center;
}

li {
  float: left;
  padding: 0px 15px;
}

li a {
  display: block;
  color: white;
  text-align: center;
  text-decoration: none;
  letter-spacing: 2px;
  font-size: 12px;

  -webkit-transition: all 0.3s ease-in;
  -moz-transition: all 0.3s ease-in;
  -ms-transition: all 0.3s ease-in;
  -o-transition: all 0.3s ease-in;
  transition: all 0.3s ease-in;
}

li a:hover {
  color: #ffcb39;
}


.central-body {
  /*    width: 100%;*/
  padding: 17% 5% 10% 5%;
  text-align: center;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.objects img {
  z-index: 90;
  pointer-events: none;
}


.image-404 {
  position: relative;
  z-index: 100;
  pointer-events: none;
}
@media only screen and (max-width: 600px) {

  .brand-logo img {
    width: 120px;
  }

  .central-body {
    padding-top: 25%;
  }
}
</style>
