//Adapted By GRPTeam202205
<template>
    <!-- Navigation bar that appears in all pages -->
    <!-- Nav header -->
    <div class="nav-header">
        <div class="brand-logo">
        <router-link :to="{name:'Home'}">
            <b class="logo-abbr"
            ><img src="/static/SoftwareIcon/ShortIcon.png" alt="" />
            </b>
            <span class="logo-compact"
            ><img src="/static/SoftwareIcon/ShortIcon.png" alt=""/>
            </span>
            <span class="brand-title" >
            <img src="/static/SoftwareIcon/CloudWalker.png" width="180" height="60" alt="" />
            </span>
        </router-link>
        </div>
    </div>
    <!--  Navigation side bar -->
    <div class="nk-sidebar">           
        <!-- <div class="navbar-lg "> -->
            <!-- <ul class="navbar-nav me-auto mb-2 mb-lg-0"> -->
        <div class="nk-nav-scroll">
            <ul class="metismenu" id="menu">  
                <!-- Dashboard navigator -->
                <li class="nav-label"><br>{{$t('Dashboard')}}</li>
                <li>
                    <a class="has-arrow" aria-expanded="false" data-bs-toggle="collapse" role="button" data-bs-target="#collapseExample">
                        <i class="fa-solid fa-house"></i><span class="nav-text">{{$t('Dashboard')}}</span>
                    </a>
                    <ul class="collapse" id="collapseExample" aria-expanded="false">
                        <li ><router-link :to="{name:'Home'}" class="nav-link"><br>{{$t('Home')}}</router-link> </li>
                    </ul>
                </li>
                <!-- Bilibili navigator --> 
                <li class="nav-label" v-if="hasManager"><br>{{$t('Bilibili')}}</li>
                <li>
                    <a class="has-arrow" aria-expanded="false" data-bs-toggle="collapse" role="button" data-bs-target="#products" v-if="hasManager">
                        <i class="fa-solid fa-briefcase"></i><span class="nav-text">{{$t('Bilibili')}}</span>
                    </a>
                    <ul class="collapse" id="products" aria-expanded="false">
                        <li><router-link :to="{name:'Bilibili'}" class="nav-link"><br>{{$t('Income List')}}</router-link></li>
                    </ul>
                </li>
                <!-- Income navigator --> 
                <li class="nav-label" v-if="isInfluncer"><br>{{$t('Income')}}</li>
                <li>
                    <a class="has-arrow" aria-expanded="false" data-bs-toggle="collapse" role="button" data-bs-target="#income" v-if="isInfluncer">
                        <i class="fa-solid fa-briefcase"></i><span class="nav-text">{{$t('Income')}}</span>
                    </a>
                    <ul class="collapse" id="income" aria-expanded="false">
                        <li><router-link :to="{name:'Income'}" class="nav-link"><br>{{$t('Income List')}}</router-link></li>
                    </ul>
                </li>
                <!-- Procurement navigator -->
                <li class="nav-label" v-if="hasManager"><br>{{$t('Procurement')}}</li>
                <li v-if="hasManager">
                    <a class="has-arrow" aria-expanded="false" data-bs-toggle="collapse" role="button" data-bs-target="#purchases">
                        <i class="fa-solid fa-cart-shopping"></i><span class="nav-text">{{$t('Purchases')}}</span>
                    </a>
                    <ul class="collapse" id="purchases" aria-expanded="false">
                        <li><router-link :to="{name:'Purchase'}" class="nav-link"><br>{{$t('Purchases History')}}</router-link></li>
                        <li><router-link :to="{name:'AddPurchase'}" class="nav-link" v-if="hasManager||hasPurchases"><br>{{$t('Add Purchase')}}</router-link></li>
                        <li><router-link :to="{name:'ReturnPurchase'}" class="nav-link"><br>{{$t('Purchase Return')}}<br><br></router-link></li>
                    </ul>
                </li>
                <!-- Supplier navigator -->
                <li  v-if="hasManager">
                    <a class="has-arrow"  aria-expanded="false" data-bs-toggle="collapse" role="button" data-bs-target="#suppliers">
                        <i class="fa-sharp fa-solid fa-store"></i> <span class="nav-text">{{$t('Supplier.Supplier')}}</span>
                    </a>
                    <ul class="collapse" id="suppliers" aria-expanded="false">
                        <li><router-link :to="{name:'Supplier'}" class="nav-link"><br>{{$t('Suppliers List')}}</router-link></li>
                        <li><router-link :to="{name:'AddSupplier'}" class="nav-link"><br>{{$t('Add Supplier')}}</router-link></li>
                    </ul>
                </li>
                <!-- <li>
                    <a class="has-arrow" aria-expanded="false" data-bs-toggle="collapse" role="button" data-bs-target="#inventory">
                        <i class="icon-note menu-icon"></i><span class="nav-text">{{$t('Inventory')}}</span>
                    </a>
                    
                    <ul class="collapse" id="inventory" aria-expanded="false">
                        <li><router-link :to="{name:'Inventory'}" class="nav-link"><br>{{$t('Stock Summary')}}<br><br></router-link></li>
                    </ul>
                </li> -->
                
                <!-- Sales navigator -->   
                <li class="nav-label" v-if="hasManager"><br>{{$t('Sale.Sales')}}</li>
                <li v-if="hasManager">
                    <a class="has-arrow" aria-expanded="false" data-bs-toggle="collapse" role="button" data-bs-target="#sales">
                        <i class="fa-solid fa-cash-register"></i><span class="nav-text">{{$t('Sale.Sales')}}</span>
                    </a>
                    <ul class="collapse" id="sales" aria-expanded="false">
                        <li><router-link :to="{name:'Sale'}" class="nav-link"><br>{{$t('Sale.Sales History')}}</router-link></li>
                        <li><router-link :to="{name:'AddSale'}" class="nav-link"><br>{{$t('Add Sale')}}</router-link></li>
                        <li><router-link :to="{name:'ReturnSale'}" class="nav-link"><br>{{$t('Sales Return')}}</router-link></li>
                    </ul>
                </li>
                <!-- Customer navigator -->   
                <li class="mega-menu mega-menu-sm" v-if="hasManager">
                    <a class="has-arrow" aria-expanded="false" data-bs-toggle="collapse" role="button" data-bs-target="#customers">
                        <i class="fa-solid fa-address-book"></i><span class="nav-text">{{$t('Customers')}}</span>
                    </a>
                    <ul class="collapse" id="customers" aria-expanded="false">
                        <li><router-link :to="{name:'Customer'}" class="nav-link"><br>{{$t('Customers List')}}</router-link> </li>
                        <li><router-link :to="{name:'AddCustomer'}" class="nav-link"><br>{{$t('Add Customer')}}</router-link></li>
                    </ul>
                </li>
                <!-- User navigator -->   
                <li class="nav-label" v-if="hasManager"><br>{{$t('Users')}}</li>
                <li v-if="hasManager">
                    <a class="has-arrow" aria-expanded="false" data-bs-toggle="collapse" role="button" data-bs-target="#users">
                        <i class="fa-solid fa-user"></i><span class="nav-text">{{$t('Users')}}</span>
                    </a>
                    <ul class="collapse" id="users" aria-expanded="false">
                        <li><router-link :to="{name:'User'}" class="nav-link"><br>{{$t('User List')}}</router-link></li>
                        <li><router-link :to="{name:'AddUser'}" class="nav-link"><br>{{$t('Add User')}}</router-link></li>
                        <li><router-link :to="{name:'ChangeUserPermission'}" class="nav-link"><br>{{$t('Change User Permission')}}<br><br></router-link></li>
                    </ul>
                </li>
                <!-- Settings navigator -->   
                <li class="nav-label">{{$t('Settings')}}</li>
                <li>
                    <a class="has-arrow" aria-expanded="false" data-bs-toggle="collapse" role="button" data-bs-target="#changePassword">
                        <i class="fa-solid fa-list"></i><span class="nav-text">{{$t('Settings')}}</span>
                    </a>
                    <ul class="collapse" id="changePassword" aria-expanded="false">
                        <li><router-link :to="{name:'ChangePassword'}" class="nav-link"><br>{{$t('Change Password')}}</router-link></li>
                        <li><router-link :to="{name:'LockScreen'}" class="nav-link"><br>{{$t('Lock Screen')}}</router-link></li>
                    </ul>
                </li>
                
                <li class="nav-label">{{$t('Others')}}</li>
                <li>
                    <a class="has-arrow" aria-expanded="false" data-bs-toggle="collapse" role="button" data-bs-target="#others">
                        <i class="icon-notebook menu-icon"></i><span class="nav-text">{{$t('Others')}}</span>
                    </a>
                    <ul class="collapse" id="others" aria-expanded="false">
                        <li><router-link :to="{name:'Communication'}" class="nav-link"><br>{{$t('Communication')}}</router-link></li>
                        <li><router-link :to="{name:'UserGuidance'}" class="nav-link"><br>{{$t('Help')}}</router-link></li>
                        <li><router-link :to="{name:'AboutUs'}" class="nav-link"><br>{{$t('About Us')}}</router-link></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</template>

<script>
import axios from 'axios';
export default {
    data(){
        return{
            manager: false,
            account: false,
            userName: ''
        }
    },
    created(){
        this.userName = sessionStorage.getItem('username')
    },
    computed:{
        //Check if the current role of the user is Manager.
        hasManager(){
            axios
                .get('currentRole', {params:{userName: this.userName,
                }})
                .then((response) => {
                    if(response.data == 'Manager') this.manager=true;
                    else this.manager=false;
                });
            return this.manager;
        },
        //Check if the current role of the user is Sales Personnel.
        isInfluncer(){
            axios
                .get('currentRole', {params:{userName: this.userName,
                }})
                .then((response) => {
                    if(response.data == 'Influncer')  this.account=true;
                    else  this.account=false;
                });
            return this.account;
        }
    }

}
</script>