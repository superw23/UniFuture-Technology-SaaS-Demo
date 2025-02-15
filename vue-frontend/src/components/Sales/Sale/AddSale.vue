// Made by GRPTeam202205
<template>
    <!-- Add sale page -->
    <div>
        <!-- Title of the page -->
        <h2>{{$t('Sale.Create New Sale')}}</h2>
        <hr />

        <!-- Find a product -->
        <div class="modal fade" id="addProduct" tabindex="-1" aria-labelledby="addProductLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="addProductLabel">{{$t('Find a Product')}}</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <div class="input-group rounded">
                            <input type="search" class="form-control rounded" :placeholder="$t('Search')" v-model="productSearch"/>
                        </div>
                        <ul class="list-group">
                            <li class="list-group-item" v-for="product in filteredProducts" :key="product.id">
                                <input class="form-check-input " type="radio" name="productRadio" id="productRadio" @click="setTempProduct(product.name)" aria-label="...">
                                <span>{{product.name}}</span>
                            </li>
                        </ul>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">{{$t('No')}}</button>
                        <button type="button" class="btn btn-primary" data-bs-dismiss="modal" @click="setProduct">{{$t('Yes')}}</button>
                    </div>
                </div>
            </div>
        </div>
        <!-- Find a supplier -->
        <div class="modal fade" id="addSupplier" tabindex="-1" aria-labelledby="addSupplierLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-scrollable">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="addSupplierLabel">{{$t('Find a Supplier')}}</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <div class="input-group rounded">
                            <input type="search" class="form-control rounded" :placeholder="$t('Search')" v-model="supplierSearch"/>
                        </div>
                        <ul class="list-group" v-if="productName">
                            <li class="list-group-item" v-for="supplier in suppliersWithGivenProduct" :key="supplier.id">
                                <input class="form-check-input me-1" type="radio" name="certainSuppliers" id="certainSuppliers" @click="setTempSupplier(supplier.company)" aria-label="...">
                                {{supplier.company}}
                            </li>
                        </ul>
                        <ul class="list-group" v-else>
                            <li class="list-group-item" v-for="supplier in filteredSuppliers" :key="supplier.id">
                                <input class="form-check-input " type="radio" name="supplierRadio" id="supplierRadio" @click="setTempSupplier(supplier.company)" aria-label="...">
                                <span>{{supplier.company}}</span>
                            </li>
                        </ul>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">{{$t('Cancel')}}</button>
                        <button type="button" class="btn btn-primary" data-bs-dismiss="modal" @click="setSupplier">{{$t('OK')}}</button>
                    </div>
                </div>
            </div>
        </div>
        <!-- Find a customer -->
        <div class="modal fade" id="addCustomer" tabindex="-1" aria-labelledby="addCustomerLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="addCustomerLabel">{{$t('Find a Customer')}}</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <div class="input-group rounded">
                            <input type="search" class="form-control rounded" :placeholder="$t('Search')" v-model="customerSearch"/>
                        </div>
                        <ul class="list-group">
                            <li class="list-group-item" v-for="customer in filteredCustomers" :key="customer.id">
                                <input class="form-check-input " type="radio" name="customerRadio" id="customerRadio" @click="setTempCustomer(customer.name)" aria-label="...">
                                <span>{{customer.name}}</span>
                            </li>
                        </ul>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">{{$t('No')}}</button>
                        <button type="button" class="btn btn-primary" data-bs-dismiss="modal" @click="setCustomer">{{$t('Yes')}}</button>
                    </div>
                </div>
            </div>
        </div>

        <div class="row col-md-4 offset-md-4">
            <!-- Show the following message if the sale is to the database successfully-->
            <div class="alert alert-success" v-if="isSuccess">
                {{$t('Sale.The sale is added successfully!')}}
            </div>
            <!-- Show the following message if the product is not found-->
            <div class="alert alert-danger" v-if="productNotFound">
                {{$t('Product is not found!')}}
            </div>
            <!-- Show the following message if the supplier is not found-->
            <div class="alert alert-danger" v-if="supplierNotFound">
                {{$t('Supplier is not found!')}}
            </div>
            <!-- Show the following message if the customer is not found-->
            <div class="alert alert-danger" v-if="customerNotFound">
                {{$t('Customer is not found!')}}
            </div>
            <!-- Show the following message if the sale is to the database successfully-->
            <div class="alert alert-danger" v-if="noAvailability">
                {{$t('The sale number exceeds the total stock in the inventory!')}}
            </div>
            <!-- Sale form to collect info of the sale to be added -->
            <div class="panel panel-primary">
                <div class="panel-body">
                    <form @submit.prevent="onCreatePost">
                        <!-- Input name of the sale -->
                        <label for="productName">{{$t('Product Name')}}</label>
                        <div class="input-group mb-3">
                            <input type="text" list="datalist" class="form-control" :placeholder="$t('e.g., Chips')" id= "productName" v-model="productName" @blur="auto" required>
                            <datalist id="datalist">
                                <option v-for = "product in filteredProducts" :key = "product.id">{{product.name}}</option>   
                            </datalist> 
                            <div class="input-group-append" data-bs-toggle="modal" data-bs-target="#addProduct">
                                <button class="input-group-text" type="button">
                                    <i class="fa fa-search" style="font-size:24px" ></i>
                                </button>
                            </div> 
                        </div>
                        <!-- Input supplier name of the sale -->
                        <label for="supplierName">{{$t('Supplier Name')}}</label>
                        <div class="input-group mb-3">
                            <input type="text" list="datalistSupplier" class="form-control" :placeholder="$t('e.g. Ella')" id= "supplierName" v-model="supplierName" required> 
                            <datalist id="datalistSupplier">
                                <option v-for = "supplier in filteredSuppliers" :key = "supplier.id">{{supplier.company}}</option>   
                            </datalist>
                            <div class="input-group-append" data-bs-toggle="modal" data-bs-target="#addSupplier" @click="getSuppliersWithGivenProduct">
                                <button class="input-group-text" type="button">
                                    <i class="fa fa-search" style="font-size:24px"></i>
                                </button>
                            </div>
                        </div>
                        <!-- Input quantity of the sale -->
                        <div class="form-group">
                            <label for="quantity">{{$t('Quantity')}}</label>
                            <input type="text" class="form-control" :placeholder="$t('e.g. 20')" id= "quantity" v-model="quantity" @input="validateQuantity" @focus="showRemainingQuantity">
                            <div v-if="hasQuantityError" class="text-danger">{{$t('Please input a valid quantity')}}</div>
                            <div v-if="show" id="showRemaining">{{$t('Remaining Quantity:')}} {{remainingQuantity}}</div>
                        </div>
                        <!-- Input sale price of the sale -->
                        <div class="form-group">
                            <label for="price">{{$t('Sale.Sale Price')}}</label>
                            <input type="text" class="form-control" :placeholder="$t('e.g. 20')" id= "price" v-model="price" @input="validatePrice" required>
                            <div v-if="priceError" class="text-danger">{{$t('Please input a valid sale price')}}</div>
                        </div>
                        <!-- Input customer name of the sale -->
                        <label for="customerName">{{$t('Customer Name')}}</label>
                        <div class="input-group mb-3">
                            <input type="text" list="datalistCustomer" class="form-control" :placeholder="$t('e.g. Ella')" id= "customerName" v-model="customerName" required>
                            <datalist id="datalistCustomer">
                                <option v-for="customer in filteredCustomers" :key="customer.id">{{customer.name}}</option>   
                            </datalist>
                            <div class="input-group-append" data-bs-toggle="modal" data-bs-target="#addCustomer">
                                <button class="input-group-text" type="button">
                                    <i class="fa fa-search" style="font-size:24px"></i>
                                </button>
                            </div>
                            <div class="input-group-append"  @click="jumpToAddCustomer">
                                <button class="input-group-text" type="button">
                                    <i class="fa fa-plus" style="font-size:24px"></i>
                                </button>
                            </div>
                        </div>
                        <!-- Input date of the sale -->
                        <div class="form-group">
                            <label for="date">{{$t('Date')}}</label>
                            <input type="date" class="form-control" id= "date" v-model="date" required >    
                        </div>
                        <!-- Click the button to submit the sale form to the backend -->
                        <div class="row align-items-center">
                            <div class="empty-row2"></div>
                            <div class="col-6">
                                <button type="submit" class="btn btn-primary">
                                    {{$t('Sale.Add Sale')}}
                                </button>
                            </div>
                            <div class="col-6">
                                <router-link :to="{name:'Sale'}"><button type="button" class="btn btn-outline-primary">{{$t('Sale.View Sales')}}</button></router-link>
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
import CustomerService from '@/services/Customer/CustomerService'
import SupplierService from '@/services/Supplier/SupplierService'
import BilibiliService from '@/services/Bilibili/BilibiliService'
import SaleService from '@/services/Sale/SaleService'
export default {
    data() {
        return {
            quantity: '',//store the name of the sale
            remainingQuantity: '',//store the remaining quantity of a certain product
            price: '',//store the name of the sale
            productName: '',//store the name of the sale
            supplierName: '',//store the name of the sale
            customerName: '',//store the name of the sale
            tempSupplierName: '',//store the name of the supplier temporarily
            tempProductName: '',//store the name of the product temporarily
            tempCustomerName: '',//store the name of the customer temporarily
            date: '',//store the name of the sale
            productSearch: '',//store search content
            supplierSearch: '',//store search content
            customerSearch: '',//store search content
            isSuccess: false,//true if the sale is added successfully, else false
            productNotFound: false,//true if the product is not found, else false
            supplierNotFound: false,//true if the supplier is not found, else false
            customerNotFound: false,//true if the customer is not found, else false
            customerExistence : true,//true if the customer exists, else false
            supplierExistence : true,//true if the supplier exists, else false
            productExistence : true,//true if the product exists, else false
            availability : true,//true if the number of products is available, else false
            show: false,//true if the remaining product can be shown
            noAvailability: '',//true if the number of products is not available, else false
            suppliersWithGivenProduct: '',//store suppliers for a given product
            allSuppliers: [],//store all suppliers
            allProducts: [],//store all products
            allCustomers: [],//store all customers
            hasQuantityError: false,
            priceError: false,
        };
    },
    methods: {
        // add a new sale
        //check the validity of all inputs
        async onCreatePost() {
            if (this.hasQuantityError || this.priceError) {
                alert('Please correct the errors before submitting.');
                return;
            }
            await this.checkSupplierExistence()
            await this.checkCustomerExistence()
            await this.checkProductExistence()
            await this.checkAvailability()
            this.addSale()  
        },
        // pass the sale information to the backend
        addSale(){ 
            if(this.supplierExistence&&this.customerExistence&&this.productExistence&&this.availability){
                axios
                    .post(`addSale`, {
                        name: this.productName,
                        num: this.quantity,
                        price: this.price,
                        total: this.price * this.quantity,
                        customerName: this.customerName,
                        name: this.productName,
                        supplierName: this.supplierName,
                        date: this.date
                    })
                    .then((response) => {
                        this.isSuccess = true;
                        this.productNotFound = false;
                        this.supplierNotFound = false;
                        this.customerNotFound = false;
                        this.noAvailability = false;
                        this.productName = '',//clear the form
                        this.quantity = '',
                        this.price = '',
                        this.customerName = '',
                        this.supplierName = '',
                        this.date = ''
                        this.priceError = false;
                    })
                    this.show = false
                    this.hasQuantityError = false
                    $('showRemaining').hide()
            };
        },
        //check the existence of the supplier in the database
        async checkSupplierExistence(){
            await axios
                .get('checkSupplierExistence', {params:{supplierName:this.supplierName}  
                })
                .then((response) => {
                    if(response.data == false){
                        this.supplierNotFound = true;
                        this.supplierExistence = false;
                        this.isSuccess = false;
                        this.productNotFound = false;
                        this.customerNotFound = false;
                        this.noAvailability = false;
                    }
                    else{
                        this.supplierExistence = true;
                    }
                });
        },
        //check the customer of the supplier in the database
        async checkCustomerExistence(){
            await axios
                .get('checkCustomerExistence', {params:{customerName:this.customerName}  
                })
                .then((response) => {
                    if(response.data == false){
                        this.customerNotFound = true;
                        this.customerExistence = false;
                        this.isSuccess = false;
                        this.productNotFound = false;
                        this.supplierNotFound = false;
                        this.noAvailability = false;
                    }
                    else{
                        this.customerExistence = true;
                    }
                });
        },
        //check the existence of the product in the database
        async checkProductExistence(){
            await axios
                .get('checkProductExistence', {params:{
                    productName:this.productName,
                    supplierName: this.supplierName
                }})
                .then((response) => {
                    if(response.data == false){
                        this.productNotFound = true;
                        this.productExistence = false;
                        this.isSuccess = false;
                        this.supplierNotFound = false;
                        this.customerNotFound = false;
                        this.noAvailability = false;
                    }
                    else{
                        this.productExistence = true;
                    }
                });
        },
        //check the availability of the products in the database
        async checkAvailability(){
            await axios
                .get('checkAvailability', {params:{
                    productName:this.productName,
                    supplierName: this.supplierName,
                    saleNum: this.quantity,
                }})
                .then((response) => {
                    if(response.data == false){
                        this.noAvailability = true;
                        this.isSuccess = false;
                        this.productNotFound = false;
                        this.supplierNotFound = false;
                        this.customerNotFound = false;
                        this.availability = false;
                    }
                    else{
                         this.noAvailability = false;
                    }
                });
        },
        //Get all suppliers from the database
        async getSuppliersWithGivenProduct(){
            await SupplierService.getSuppliersWithGivenProduct(this.productName).then((response)=>{
                this.suppliersWithGivenProduct = response.data;
            });
        },
        //automatically fill in the sale form 
        auto(){
            SaleService.getSaleContent(this.productName).then((response)=>{
                if(response.data!=''){
                    this.productName = response.data.name,
                    this.quantity = response.data.num,
                    this.customerName = response.data.customerName,
                    this.price = response.data.price,
                    this.supplierName = response.data.supplierName,
                    this.date = response.data.date
                }
            })
        },
         //go to the add supplier page
        jumpToAddCustomer(){
            this.$router.push({path:'/addCustomer'})
        },
        //get all suppliers
        getAllSuppliers(){
            SupplierService.getSuppliers().then((response)=>{
                this.allSuppliers = response.data
            })
        },
        //get all products
        getAllInfo(){
            BilibiliService.getDistinctProducts().then((response)=>{
                this.allProducts = response.data
            })
        },
        //get all customers
        getAllCustomers(){
            CustomerService.getCustomers().then((response)=>{
                this.allCustomers = response.data
            })
        },
        setSupplier(){
            this.supplierName = this.tempSupplierName
        },
        setProduct(){
            this.productName = this.tempProductName
        },
        setCustomer(){
            this.customerName = this.tempCustomerName
        },
        setTempSupplier(company){
            this.tempSupplierName = company
        },
        setTempProduct(name){
            this.tempProductName = name
        },
        setTempCustomer(name){
            this.tempCustomerName = name
        },
        //Check if the input quantity is valid
        validateQuantity() {
              const quantityRegex = /^[1-9]\d*$/;
              if (!quantityRegex.test(this.quantity)) {
                this.hasQuantityError = true
              } else {
                this.hasQuantityError = false
              }
        },
        //Check if the input price is valid
        validatePrice() {
              const priceRegex = /^[1-9]\d*(\.\d{1,2})?$/;
              if (!priceRegex.test(this.price)) {
                this.priceError = true;
              } else {
                this.priceError = false;
              }
        },
        //Show remaining quantity of a given product
        showRemainingQuantity(){
            if(this.supplier!='' && this.name!=''){
                BilibiliService.getRemainingQuantity(this.productName, this.supplierName).then((response)=>{
                    this.remainingQuantity = response.data
                })
                this.show = true
                $('showRemaining').show()
            }
        }
    },
    computed:{
        //Get required suppliers after searching
        filteredSuppliers(){
            return this.allSuppliers.filter(supplier => supplier.company.toLowerCase().includes(this.supplierSearch.toLowerCase()));
        },
        //Get required products after searching
        filteredProducts(){
            return this.allProducts.filter(product => product.name.toLowerCase().includes(this.productSearch.toLowerCase()));
        },
        //Get required customers after searching
        filteredCustomers(){
            return this.allCustomers.filter(customer => customer.name.toLowerCase().includes(this.customerSearch.toLowerCase()));
        },
    },
    created(){
        this.getAllSuppliers()
        this.getAllInfo()
        this.getAllCustomers()
    }
};
</script>
