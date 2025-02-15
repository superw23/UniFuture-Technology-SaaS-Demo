//Made by GRPTeam202205
<template>
    <!-- Add customer page -->
    <div>
        <!-- Title of the page -->
        <h2>{{$t('Create New User')}}</h2>
        <hr />

        <div class="row col-md-4 offset-md-4">
            <!-- Show the following message if the user is to the database successfully-->
            <div class="alert alert-success" v-if="isSuccess">
                {{$t('The user is added successfully!   The initial password is the same as the user name by default.')}}
            </div>
            <div class="alert alert-danger" v-if="sameName">
                {{$t('The name has already existed!')}}
            </div>
            <!-- User form to collect info of the user to be added -->
            <div class="panel panel-primary">
                <div class="panel-heading text-center">

                </div>
                <div class="panel-body">
                    <form @submit.prevent="onCreatePost">
                        <!-- Input name of the user -->
                        <div class="form-group">
                            <label for="userName">{{$t('User Name')}}</label>
                            <input type="text" class="form-control" :placeholder="$t('e.g. Sally')" id= "userName" v-model="userName" @input="validateName" required>
                            <div v-if="showError" class="text-danger">{{ $t('Please input a valid user name') }}</div>
                        </div>
                        <!-- Input email of the user -->
                        <div class="form-group">
                            <label for="userName">{{$t('Email')}}</label>
                            <input type="text" class="form-control" :placeholder="$t('e.g. Sally@gmail.com')" id= "email" v-model="email" required>
                        </div>
                        <!-- Input role of the user -->
                        <div class="form-group">
                            <label for="type" class="form-label">{{$t('Role')}}</label>
                            <select class="form-select" aria-label="Default select example" v-model="role" required>
                                <option selected disabled value="">{{$t('Please choose a role')}}</option>
                                <option>{{$t('Manager')}}</option>
                                <option>{{$t('Influncer')}}</option>
                            </select>
                        </div>
                        <!-- Input role of the user -->
                        <div class="form-group">
                            <label for="type" class="form-label">{{$t('Platform')}}</label>
                            <select class="form-select" aria-label="Default select example" v-model="platform" required>
                                <option selected disabled value="">{{$t('Please choose a platform')}}</option>
                                <option>{{$t('Bilibili')}}</option>
                                <option>{{$t('Tiktok')}}</option>
                                <option>{{$t('Genshin')}}</option>
                                <option>{{$t('SMG')}}</option>
                                <option>{{$t('None')}}</option>
                            </select>
                        </div>
                        <div class="row align-items-center">
                            <div class="empty-row2"></div>
                            <div class="col-6">
                                <!-- Click the button to submit the user form to the backend -->
                                <button type="submit" class="btn btn-primary">
                                    {{$t('Add User')}}
                                </button>
                            </div>
                            <div class="col-6">
                                <router-link :to="{name:'User'}"><button type="button" class="btn btn-outline-primary">{{$t('View User')}}</button></router-link>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios'
export default {
    data() {
        return {
            userName: '',//store the name of the user
            role: '',//store the role of the customer
            email: '',
            platform: '',
            sameName: false,//true if the user name exists, else false
            isSuccess: false,//true if the user is added successfully, else false
            showError: false,
        };
    },
    methods: {
        // add a new user
        // pass the user information to the backend
        onCreatePost() {
            if (this.showError) {
                alert('Please correct the errors before submitting.');
                return;
            }
            axios
                .post(`addUser`, {
                    姓名: this.userName,
                    角色: this.role,
                    平台: this.platform,
                    邮箱: this.email
                })
                .then((response) => {
                    if(response.data=="") {
                        this.sameName = true;
                        this.isSuccess = false;
                    }
                    else {
                        this.sameName = false;
                        this.isSuccess = true;
                        this.userName = '',
                        this.role = ''
                    }
                });
        },
        //Check if the input user name is valid
        validateName() {
            // Regular expression for letters only validation
            const lettersRegex = /^[A-Za-z\s]+$/;

            // Check if the input is a valid name
            if (!lettersRegex.test(this.userName)) {
               // Show the error message
               this.showError = true;
            } else {
               // Hide the error message
               this.showError = false;
            }
        },
    },
};
</script>
