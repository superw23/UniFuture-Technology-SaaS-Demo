// Made by GRPTeam202205
<template>
    <div>
        <!-- Change User Permission page -->
        <h2>{{$t('Change User Permission')}}</h2>
        <hr />

        <!-- Find a user -->
        <div class="modal fade" id="addUser" tabindex="-1" aria-labelledby="addUserLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-scrollable">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="addUserLabel">{{$t('Find a User')}}</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <div class="input-group rounded">
                            <input type="search" class="form-control rounded" :placeholder="$t('Search')" v-model="userSearch"/>
                        </div>
                        <ul class="list-group">
                            <li class="list-group-item" v-for="user in filteredUsers" :key="user.id">
                                <input class="form-check-input me-1" type="radio" @click="setTempUser(user.name)" aria-label="...">
                                {{user.name}}
                            </li>
                        </ul>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">{{$t('Cancel')}}</button>
                        <button type="button" class="btn btn-primary" data-bs-dismiss="modal" @click="setUser">{{$t('OK')}}</button>
                    </div>
                </div>
            </div>
        </div>

        <div class="row col-md-4 offset-md-4">
            <!-- Show the following message if the user permission has been changed successfully-->
            <div class="alert alert-success" v-if="isSuccess">
                {{$t('The user permission has been changed successfully!')}}
            </div>
            <!-- Show the following message if the user name is not found-->
            <div class="alert alert-danger" v-if="notFound">
                {{$t('User name is not found!')}}
            </div>
            <!-- Change User Permission form -->
            <div class="panel panel-primary">
                <div class="panel-body">
                    <form @submit.prevent="reset">
                        <!-- Input user name -->
                        <label for="userName">{{$t('User Name')}}</label>
                        <div class="input-group mb-3">
                            <input type="text" list="datalistUser" class="form-control" :placeholder="$t('e.g. Ella')" id= "userName" v-model="userName" required> 
                            <datalist id="datalistUser">
                                <option v-for = "user in filteredUsers" :key = "user.id">{{user.name}}</option>   
                            </datalist>
                            <div class="input-group-append" data-bs-toggle="modal" data-bs-target="#addUser">
                                <button class="input-group-text" type="button">
                                    <i class="fa fa-search" style="font-size:24px"></i>
                                </button>
                            </div>
                        </div>
                        <!-- User permission -->
                        <div class="form-group">
                            <label for="newRole" class="form-label">{{$t('Reset Permission')}}</label>
                            <select class="form-select" aria-label="Default select example" v-model="newRole" required>
                                <option selected disabled value="">{{$t('Please choose a role')}}</option>
                                <option>{{$t('Manager')}}</option>
                                <option>{{$t('Warehouse Manager')}}</option>
                                <option>{{$t('Purchase Manager')}}</option>
                                <option>{{$t('Sales Personnel')}}</option>
                            </select>
                        </div>
                        <!-- Click the button to submit the form to the backend -->
                        <div class="mt-3">
                            <button type="submit" class="btn btn-primary" >
                                {{$t('Confirm')}}
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios'
import UserService from '@/services/User/UserService'
export default {
    data() {
        return {
            userName: '',//store the name of the user
            userSearch: '',//store search content
            tempUserName: '',//store the name of the user temporarily
            role: '',//store the role of the user
            newRole: '',//store the new role of the user
            isSuccess: false,//true if the user permission has been changed successfully, else false
            notFound: false,//true if the user name is not found, else false
            users:[],
        };
    },
    methods: {
        //reset the user permission
        reset() {
            axios
                .get('resetUserPermission', {params:{userName:this.userName,
                    newRole: this.newRole}
                })
                .then((response) => {
                    if(response.data==''){
                        this.isSuccess = false;
                        this.notFound = true;
                    }
                    else{
                        this.isSuccess = true;
                        this.notFound = false;
                        this.userName = ''
                        this.newRole = ''
                    }
                });
        },
        //get all users
        getUsers(){
            UserService.getUsers().then((response)=>{
                this.users = response.data;
            });
        },
        setUser(){
            this.userName = this.tempUserName
        },
        setTempUser(name){
            this.tempUserName = name
        },
    },
    computed:{
        //Get required users after searching
        filteredUsers(){
            return this.users.filter(user => user.name.toLowerCase().includes(this.userSearch.toLowerCase()));
        },
    },
    created(){
        this.getUsers()
    }
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