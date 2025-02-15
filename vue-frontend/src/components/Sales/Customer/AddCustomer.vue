// Made by GRPTeam202205
<template>
    <!-- Add customer page -->
    <div>
        <!-- Title of the page -->
        <h2>{{$t('Customer.Add Customer')}}</h2>
        <hr />

        <div class="row col-md-4 offset-md-4">
            <!-- Show the following message if the customer is to the database successfully-->
            <div class="alert alert-success" v-if="isSuccess">
                {{$t('Customer.The customer is added successfully!')}}
            </div>
            <!-- Show the following message if the customer already exists-->
            <div class="alert alert-danger" v-if="customerExist">
                {{$t('Customer.The customer already exists!')}}
            </div>
            <!-- Customer form to collect info of the customer to be added -->
            <div class="panel panel-primary">
                <div class="panel-body">
                    <form @submit.prevent="onCreatePost">
                        <!-- Input name of the customer -->
                        <div class="form-group">
                            <label for="name">{{$t('Name')}}</label>
                            <input type="text" class="form-control" :placeholder="$t('e.g. Ella')"  id= "name" v-model="name" @input="validateName" required>
                            <div v-if="showError" class="text-danger">{{ $t('Please input a valid name') }}</div>
                        </div>
                        <!-- Input address of the customer -->
                        <div class="form-group">
                            <label for="address">{{$t('Address')}}</label>
                            <input type="text" class="form-control" :placeholder="$t('e.g. Tai Kang Road 199')" id= "address" v-model="address" required>
                        </div>
                        <!-- Input contact person of the customer -->
                        <div class="form-group">
                            <label for="contact">{{$t('Email')}}</label>
                            <input type="text" class="form-control" :placeholder="$t('e.g. 123456@gmail.com')" id= "contact" v-model="contact" @input="validateEmail" required>
                            <div v-if="showErrorTwo" class="text-danger">{{ $t('Please input a valid email address') }}</div>
                        </div>
                        <!-- Input phone number of the customer -->
                        <div class="form-group">
                            <label for="phoneNumber">{{$t('Phone Number')}}</label>
                            <input type="text" class="form-control" :placeholder="$t('e.g. 12345678900')" id= "phoneNumber" v-model="phoneNumber" @input="validatePhoneNumber" required>
                            <div v-if="showErrorThree" class="text-danger">{{ $t('Please input a valid phone number') }}</div>
                        </div>
                        <!-- Input additional information of the customer -->
                        <div class="form-group">
                            <label for="remarks">{{$t('Description')}}</label>
                            <input type="text" class="form-control" :placeholder="$t('e.g. Add remarks')" id= "remarks" v-model="remarks" required>
                        </div>
                        <div class="row align-items-center">
                            <div class="empty-row2"></div>
                            <div class="col-6">
                                <!-- Click the button to submit the customer form to the backend -->
                                <button type="submit" class="btn btn-primary">
                                    {{$t('Customer.Add Customer')}}
                                </button>
                            </div>
                            <!-- Click the button to go to customer table page -->
                            <div class="col-6">
                                <router-link :to="{name:'Customer'}"><button type="button" class="btn btn-outline-primary">{{$t('Customer.View Customers')}}</button></router-link>
                            </div>
                        </div>
                    </form>
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
            name: '',//store the name of the customer
            address: '',//store the adress of the customer
            remarks: '',//store the additional information of the customer
            contact: '',//store the contact person of the customer
            phoneNumber: '',//store the phone number of the customer
            isSuccess: false,//true if the customer is added successfully, else false
            customerExist: false,//true if the customer with the same name has already exists, else false
            showError: false,
            showErrorTwo: false,
            showErrorThree: false,
        };
    },
    methods: {
        // add a new customer
        // pass the customer information to the backend
        onCreatePost() {
            if (this.showError || this.showErrorTwo || this.showErrorThree) {
                alert('Please correct the errors before submitting.');
                return;
            }

            axios
                .post(`addCustomer`, {
                    name: this.name,
                    address: this.address,
                    phoneNumber: this.phoneNumber,
                    contact: this.contact,
                    remarks: this.remarks
                })
                .then((response) => {
                    if(response.data==''){
                        this.customerExist = true
                        this.isSuccess = false
                    }
                    else{
                        this.isSuccess = true,
                        this.customerExist = false,
                        this.name = '',
                        this.address = '',
                        this.phoneNumber = '',
                        this.contact = '',
                        this.remarks = ''
                    }
                });
        },
        validateName() {
            // Regular expression for letters only validation
            const lettersRegex = /^[A-Za-z\s]+$/;

            // Check if the input is a valid name
            if (!lettersRegex.test(this.name)) {
               // Show the error message
               this.showError = true;
            } else {
               // Hide the error message
               this.showError = false;
            }
        },
        //Check if the email is valid
        validateEmail() {
            // Regular expression for email validation
            const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

            // Check if the input is a valid email address
            if (!emailRegex.test(this.contact)) {
                // Show the error message
                this.showErrorTwo = true;
            } else {
                // Hide the error message
                this.showErrorTwo = false;
            }
        },
        //Check if the phone number is valid, i.e., it only contains numbers
        validatePhoneNumber() {
            const numberRegex = /^[0-9]*$/;

            // Check if the input is a valid phone number
            if (!numberRegex.test(this.phoneNumber)) {
               // Show the error message
               this.showErrorThree = true;
            } else {
               // Hide the error message
               this.showErrorThree = false;
            }
        },
    },
};
</script>