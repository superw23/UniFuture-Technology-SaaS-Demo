// Made by GRPTeam202205
<template>
    <!-- Change password page -->
    <div>
        <h2>{{$t('Change Password')}}</h2>
        <hr />

        <div class="row col-md-4 offset-md-4">
            <!-- Show the following message if the password has been changed successfully-->
            <div class="alert alert-success" v-if="isSuccess">
                {{$t('Your password has been changed successfully!')}}
            </div>
            <!-- Show the following message if the user name or the password is not correct successfully-->
            <div class="alert alert-danger" v-if="notFound">
                {{$t('Your old password is not correct!')}}
            </div>
            <!-- Show the following message if two input passwords are not the same -->
            <div class="alert alert-danger" v-if="inConsistent">
                {{$t('Two new passwords are not the same!')}}
            </div>
            <!-- Change password form to collect info of the user -->
            <div class="panel panel-primary">
                <div class="panel-body">
                    <form @submit.prevent="reset">
                        <!-- Input name of the user -->
                        <div class="form-group">
                            <label for="userName">{{$t('User Name')}}</label>
                            <input type="text" class="form-control" id= "userName" v-model="userName" disabled="disabled" required>    
                        </div>
                        <!-- Input current password of the user -->
                        <div class="form-group">
                            <label for="password" class="form-label">{{$t('Current Password')}}</label>
                            <input type="password" class="form-control" id= "password" v-model="password" required>    
                        </div>
                        <!-- Input new password of the user -->
                        <div class="form-group">
                            <label for="newPassword1" class="form-label">{{$t('New Password')}}</label>
                            <input type="password" class="form-control" id= "newPassword1" v-model="newPassword1" required>    
                        </div>
                        <!-- Input new password of the user again-->
                        <div class="form-group">
                            <label for="newPassword2" class="form-label">{{$t('New Password Again')}}</label>
                            <input type="password" class="form-control" id= "newPassword2" v-model="newPassword2" required>    
                        </div>
                        <!-- Click the button to reset the new password -->
                        <div class="mt-3">
                            <button type="submit" class="btn btn-primary">
                                {{$t('Reset New Password')}}
                            </button>
                        </div>
                    </form>
                </div>
                <div class="panel-footer text-right">
                        <small>&copy; GRPTeam202205</small>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios';
export default {
    data() {
        return {
            userName: '',//store the name of the user
            password: '',//store the password of the user
            newPassword1: '',//store the new password1 of the user
            newPassword2: '',//store the new password2 of the user
            isSuccess: false,//true if the password is changed successfully, else false
            notFound: false,//true if the user is not found, else false
            inConsistent: false//true if the two new passwords are the same, else false
        };
    },
    created(){
        this.userName = sessionStorage.getItem('username')
    },
    methods: {
        //reset the password of the user
        reset() {
            if(this.newPassword1!=this.newPassword2){
                this.isSuccess = false;
                this.notFound = false;
                this.inConsistent = true;
            }
            else{
                axios
                .get('resetPassword', {params:{userName:this.userName,
                    oldPassword: this.password,
                    newPassword: this.newPassword1}
                    
                })
                .then((response) => {
                    if(response.data==''){
                        this.isSuccess = false;
                        this.notFound = true;
                        this.inConsistent = false;
                    }
                    else{
                        this.isSuccess = true;
                        this.notFound = false;
                        this.inConsistent = false;
                    }
                });
            }
        },
    },
};
</script>

<style>
.box{
    background-color: lightblue;
    min-height: 50px;
    width: 200px;
    position: relative;
}
</style>