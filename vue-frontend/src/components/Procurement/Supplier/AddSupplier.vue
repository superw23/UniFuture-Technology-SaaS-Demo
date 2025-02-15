// Made by GRPTeam202205
<template>
    <!-- Add supplier page -->
    <div>
        <!-- Title of the page -->
        <h2>{{$t('Supplier.Add Supplier')}}</h2>
        <hr />
        <div class="icon1"></div> <div class="empty-row"></div> <div class="icon2"></div>

        <div class="row col-md-4 offset-md-4">
            <!-- Show the following message if the supplier is to the database successfully-->
            <div class="alert alert-success" v-if="isSuccess">
                {{$t('Supplier.The supplier is added successfully!')}}
            </div>
            <!-- Show the following message if the supplier already exists-->
            <div class="alert alert-danger" v-if="supplierExist">
                {{$t('Supplier.The supplier already exists!')}}
            </div>
            <!-- Supplier form to collect info of the supplier to be added -->
            <div class="panel panel-primary">
                <div class="panel-body">
                    <form @submit.prevent="onCreatePost">
                        <!-- Input company of the supplier -->
                        <div class="form-group">
                            <label for="company">{{$t('Company')}}</label>
                            <input type="text" class="form-control" :placeholder="$t('e.g. Company A')" id= "company" v-model="company" required>
                        </div>
                        <!-- Input address of the customer -->
                        <div class="form-group">
                            <label for="address">{{$t('Address')}}</label>
                            <input type="text" class="form-control" :placeholder="$t('e.g. Tai Kang Road 199 ')" id= "address" v-model="address" required>
                        </div>
                        <!-- Input email of the customer -->
                        <div class="form-group">
                            <label for="contact">{{$t('Email')}}</label>
                            <input type="text" class="form-control" :placeholder="$t('e.g. Sally@gmail.com')" id= "contact" v-model="contact" @input="validateEmail" required>
                            <div v-if="showError" class="text-danger">{{ $t('Please input a valid email address') }}</div>
                        </div>
                        <!-- Input phone number of the customer -->
                        <div class="form-group">
                            <label for="phoneNumber">{{ $t('Phone Number') }}</label>
                            <input type="text" class="form-control" :placeholder="$t('e.g. 12345678900')" id="phoneNumber" v-model="phoneNumber" @input="validatePhoneNumber" required>
                            <div v-if="showError2" class="text-danger">{{ $t('Please input a valid phone number') }}</div>
                        </div>
                        <!-- Input description of the customer -->
                        <div class="form-group">
                            <label for="remarks">{{$t('Description')}}</label>
                            <input type="text" class="form-control" :placeholder="$t('e.g. Add remarks')" id= "remarks" v-model="remarks" required>
                        </div>
                        <div class="row align-items-center">
                            <div class="empty-row2"></div>
                            <div class="col-6">
                                <!-- Click the button to submit the supplier form to the backend -->
                                <button type="submit" class="btn btn-primary">
                                    {{$t('Supplier.Add Supplier')}}
                                </button>
                            </div>
                            <div class="col-6">
                                <router-link :to="{name:'Supplier'}">
                                    <!-- Click the button to go to supplier table page -->
                                    <button type="button" class="btn btn-outline-primary">{{$t('Supplier.View Suppliers')}}</button>
                                </router-link>
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
            address: '',//store the address of the supplier
            remarks: '',//store the remarks of the supplier
            contact: '',//store the email of the supplier
            phoneNumber: '',//store the phone number of the supplier
            company: '',//store the company name of the supplier
            isSuccess: false, //true if the supplier is added successfully, else false
            supplierExist: false,//true if the customer with the same name has already exists, else false
            contact: '',
            showError: false,
            showError2: false,


        };
    },
    methods: {
        // add a new supplier
        // pass the supplier information to the backend
        onCreatePost() {
            // Check if there is an error in the email or phone number input
            if (this.showError || this.showError2) {
                alert('Please correct the errors before submitting.');
                return;
            }

            // Send a POST request to add a new supplier
            axios
                .post(`addSupplier`, {
                    address: this.address,
                    phoneNumber: this.phoneNumber,
                    contact: this.contact,
                    remarks: this.remarks,
                    company: this.company
                })
                .then((response) => {
                    if(response.data==''){
                        this.supplierExist = true
                        this.isSuccess = false
                    }
                    // Set isSuccess to true if the supplier is added successfully
                    else{
                        this.isSuccess = true
                        this.supplierExist = false
                        this.address = '',
                        this.phoneNumber = '',
                        this.contact = '',
                        this.remarks = '',
                        this.company = ''
                    }
                });
        },
        //Check if the email is valid
         validateEmail() {
              // Regular expression for email validation
              const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

              // Check if the input is a valid email address
              if (!emailRegex.test(this.contact)) {
                // Show the error message
                this.showError = true;
              } else {
                // Hide the error message
                this.showError = false;
              }
         },
        //Check if the phone number is valid, i.e., it only contains phone number
         validatePhoneNumber() {
             const numberRegex = /^[0-9]*$/;

             // Check if the input is a valid phone number
             if (!numberRegex.test(this.phoneNumber)) {
             // Show the error message
                this.showError2 = true;
             } else {
                // Hide the error message
                this.showError2 = false;
             }
         },
    },
};
</script>