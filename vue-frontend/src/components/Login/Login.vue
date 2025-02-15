//Made by GRPTeam202205
<template>
    <div class="form">
        <!-- Login in title -->
        <h2>
            <span>   {{$t('CloudWalker')}}  </span>
            <i class="fa fa-language" type="button" @click="translate"></i>
        </h2>
        <!-- Show the following message if the user is not found-->
        <div class="alert alert-danger" v-if="userNameNotFound">
                {{$t('User Name does not exist!')}}
        </div>
        <!-- Show the following message if the password is not correct-->
        <div class="alert alert-danger" v-if="passwordNotFound">
                {{$t('Password is not correct!')}}
        </div>
        <!-- Login form -->
        <div class="form-area">
            <!-- User Name -->
            <div class="form-group">
            <label for="userName">{{$t('User Name')}}</label>
            <input type="text" class="form-control" :placeholder="$t('e.g. Ella')" maxlength="14" id= "userName" v-model="userName" required>    
            </div>
            <!-- Password -->
            <div class="form-group">
            <label for="password">{{$t('Password')}}</label>
            <input type="password" class="form-control" :placeholder="$t('e.g. Password')" maxlength="14" id= "password" v-model="password" required>    
            </div>
            
            <!-- Login button -->
            <div class="form-item login">
                <button @click="login">{{$t('Login')}}</button>
            </div>
            <!-- Forget password button -->
            <div class="forget">
                <a href="#" @click="forgetPassword">{{$t('Forget Password')}}</a>
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios';
export default {
  data () {
    return {
        userNameNotFound: false,//true if the user is not found, else false
        passwordNotFound: false,//true if the password is not found, else false
        userName: '',//store the name of the user
        password: '',//store the password of the user
        loginBg: 'url('+require('@/assets/bg.jpg')+')'//store the backgroun image
    }
  },
  methods: {
    // login in method
    // check the existence of the user before logging in
    login(){
        axios
            .put(`checkLogin`, {
                    姓名 : this.userName,
                    密码: this.password
                })
                .then((response) => {
                    if(response.data=='userNameNotFound'){
                        this.userNameNotFound = true;
                        this.passwordNotFound = false;
                    }
                    else if(response.data=='passwordNotFound'){
                        this.userNameNotFound = false;
                        this.passwordNotFound = true;
                    }
                    else{
                        sessionStorage.setItem('username',this.userName)
                        this.$router.push({path:'/master'})
                    }
                });
    },
    //go to the forget password page
    forgetPassword(){
        this.$router.push({path:'/forgetPassword'})
    },
    //change languagages between Chinese and English
    translate(){
        if(this.$i18n.locale == 'en')
            this.$i18n.locale = 'ch'
        else
            this.$i18n.locale = 'en'
    },
  },
  mounted () {
    //Add a background image
    document.body.style.backgroundSize = '100%'
    document.body.style.backgroundImage = this.loginBg
  },
  beforeUnmount () {
    // Clear the backgroun before going to another page
    document.body.style.backgroundImage = ''
  }
}
</script>
