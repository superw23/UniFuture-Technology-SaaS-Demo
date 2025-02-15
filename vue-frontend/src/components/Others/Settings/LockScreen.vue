// Made by GRPTeam202205
<template>
    <!-- Lock screen page -->
    <div class="login-form-bg h-100">
        <div class="container h-100">
            <div class="row justify-content-center h-100">
                <div class="col-xl-6">
                    <div class="form-input-content">
                        <div class="card login-form mb-0">
                            <div class="card-body pt-5">
                                <!-- Show the name of the current user -->
                                <h4>{{userName}}</h4>
                                <form class="mt-5 mb-3 login-input" @submit.prevent="checkPassword">
                                    <div class="form-group">
                                        <input type="password" class="form-control" :placeholder="$t('Password')" v-model = "inputPassword" required>
                                    </div>
                                    <!-- Click the button to unlock the screen -->
                                    <button class="btn login-form__btn submit w-100">{{$t('Unlock')}}</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import UserService from '../../../services/User/UserService'
export default {
    data(){
        return{
            userName: '',//store the name of the user
            password: '',//store the password of the user
            inputPassword: ''//store the input password of the user
        }
    },
    methods:{
        //Get the password of the current user
        getPassword() {
            UserService.getPassword(this.userName).then((response)=>{
                this.password = response.data;
            });
        },
        //Go to the home page
        checkPassword(){
            console.log(this.inputPassword)
            if(this.inputPassword==this.password)
                this.$router.push({path:'/master'})
        }
    },
    //Activate the functions when loading the page
    created(){
        //Get the name of the current user
        this.userName = sessionStorage.getItem('username')
        this.getPassword()
    }
}
</script>

<style>

</style>