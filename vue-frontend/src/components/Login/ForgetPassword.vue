//Made by GRPTeam202205
<template>
    <div class="form">
        <!-- Title of the page -->
        <h2>
            <span>{{$t('Forget Password')}}</span>
        </h2>
        <!-- Show the following message if the user name is not found -->
        <div class="alert alert-danger" v-if="userNameNotFound">
                {{$t('User Name does not exist!')}}
        </div>
        <!-- Show the following message if the email is not found -->
        <div class="alert alert-danger" v-if="emailNotFound">
                {{$t('Email is not correct!')}}
        </div>
        <!-- Show the following message if the password has been sent to the user email successfully  -->
        <div class="alert alert-success" v-if="success">
            {{$t('Your password has been sent to your email!')}}
        </div>
        <!-- Forget password form -->
        <div class="form-area">
            <!-- User Name -->
            <div class="form-group">
                <label for="userName">{{$t('User Name')}}</label>
                <input type="text" class="form-control" :placeholder="$t('e.g. Ella')" maxlength="14" id= "userName" v-model="userName" required>    
            </div>
            <!-- Password -->
            <div class="form-group">
                <label for="email">{{$t('Email')}}</label>
                <input type="email" class="form-control" :placeholder="$t('e.g. Ella@gmail.com')" maxlength="50" id= "email" v-model="email" required>    
            </div>
            <div class="form-item login">
                <!-- Confirm button -->
                <button @click="sendEmail">{{$t('Confirm')}}</button>
                <!-- Login link -->
                <div class="forget">
                <a href="#" @click="goToLoginPage">{{$t('Login')}}</a>
            </div>
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios'
export default {
  data () {
    return {
        userNameNotFound: false,//true if the user is not found, else false
        emailNotFound: false,//true if the email is not found, else false
        success: false,//true if the email is sent to the user successfully, else false
        userName: '',//store the name of the user
        email: '',//store the email of the user
        loginBg: 'url('+require('@/assets/bg.jpg')+')'//store the background image
    }
  },
  methods: {
    //send email to the user to inform him of his password
    sendEmail(){
        axios
            .get('sendEmail', {params:{userName: this.userName,
                    email: this.email}  
            })
            .then((response) => {
                if(response.data=='userNameNotFound'){
                    this.userNameNotFound = true;
                    this.emailNotFound = false;
                    this.success = false;
                }
                else if(response.data=='emailNotFound'){
                    this.userNameNotFound = false;
                    this.emailNotFound = true;
                    this.success = false;
                }
                else{
                    this.success = true;
                    this.userNameNotFound = false;
                    this.emailNotFound = false;
                }
            });
    },
    //go to the login page
    goToLoginPage(){
        this.$router.push({path:'/'})
    }
  },
  mounted () {
    //add backgroun image
    document.body.style.backgroundSize = '100%'
    document.body.style.backgroundImage = this.loginBg
  },
  beforeUnmount () {
    // Clear the backgroun before going to another page
    document.body.style.backgroundImage = ''
  }
}
</script>

<style>
.form {
    width: 500px;
    height: 500px;
    background: #fff;
    margin: 80px auto;
}

.form-area {
    width: 400px;
    margin-top: 90px;
    margin-left: 50px;
}

.form-item {
    width: 400px;
    height: 42px;
    position: relative;
}

.form-item.login {
    margin-top: 60px;
    border-radius: 5px;
}
.form-item button {
    width: 101%;
    height: 101%;
    font-size: 18px;
    border: 0;
    outline: none;
    background-color: #3b97b7;
    color: #fff;
    border-radius: 5px;
}
.forget {
    margin: 15px 0 20px 5px;
    font-size: 15px;
}
.forget a {
    color: #9e9e9e;
}

</style>
