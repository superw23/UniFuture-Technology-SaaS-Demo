// Made by GRPTeam202205
<template>
    <!-- Add purchase page -->
    <div>
        <!-- Title of the page -->
        <h2>{{$t('Purchase.Create New Purchase')}}</h2>
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

        <div class="row col-md-4 offset-md-4">
            <!-- Show the following message if the purchase is to the database successfully-->
            <div class="alert alert-success" v-if="isSuccess">
                {{$t('Purchase.The purchase is added successfully!')}}
            </div>
            <!-- Show the following message if the supplier is not found-->
            <div class="alert alert-danger" v-if="supplierNotFound">
                {{$t('Purchase.Supplier is not found!')}}
            </div>

            <!-- Purchase form to collect info of the purchase to be added -->
            <div class="panel panel-primary">
                <div class="icon1"></div> <div class="empty-row"></div> <div class="icon2"></div>

                <div class="panel-body">
                    <form @submit.prevent="onCreatePost">
                        <!-- Input name of the purchase -->
                        <label for="name">{{$t('Name')}}</label>
                        <div class="input-group mb-3">
                            <input type="text" list="datalistName" class="form-control" :placeholder="$t('Chips')" id= "name" v-model="name"  @blur="auto" required>
                            <datalist id="datalistName">
                                <option v-for = "product in filteredProducts" :key = "product.id">{{product.name}}</option>   
                            </datalist>
                            <div class="input-group-append" data-bs-toggle="modal" data-bs-target="#addProduct">
                                <button class="input-group-text" type="button">
                                    <i class="fa fa-search" style="font-size:24px" ></i>
                                </button>
                            </div> 
                        </div>
                        <!-- Input type of the purchase -->
                        <div class="form-group">
                            <label for="type" class="form-label" v-if="!addNewType">{{$t('Type')}}</label>
                            <select class="form-select" aria-label="Default select example" v-model="type" @click="showNewType" required>
                                <option selected disabled value="">{{$t('Please choose a type')}}</option>
                                <option v-for="type in distinctType" :key="type">{{type}}</option>
                                <option>{{$t('Add a new type')}}</option>
                            </select>
                        </div>
                        <!-- Input new type of the purchase -->
                        <div class="form-group" v-if="addNewType" id="showInput" required>
                            <label for="newType">{{$t('New Type')}}</label>
                            <input type="text" class="form-control" id= "newType" v-model="newType" required>
                        </div>
                        <!-- Input quantity of the purchase -->
                        <div class="form-group">
                            <label for="quantity">{{$t('Quantity')}}</label>
                            <input type="text" class="form-control" :placeholder="$t('e.g. 20')" id= "quantity" v-model="quantity" @input="validateQuantity" required>
                            <div v-show="quantityError" class="text-danger">{{$t('Please input a valid quantity')}}</div>
                        </div>
                        <!-- Input price of the purchase -->
                        <div class="form-group">
                            <label for="price">{{$t('Purchase.Purchase Price')}}</label>
                            <input type="text" class="form-control" :placeholder="$t('e.g. 20')" id= "price" v-model="price" @input="validatePrice" required>
                            <div v-show="priceError" class="text-danger">{{$t('Please input a valid purchase price')}}</div>
                        </div>
                        <!-- Input supplier name of the purchase -->
                        <label for="supplierName">{{$t('Supplier Name')}}</label>
                            <div class="input-group mb-3">
                            <input type="text" list="datalist" class="form-control" :placeholder="$t('e.g. Tom')" id= "supplierName" v-model="supplierName" required>
                            <datalist id="datalist">
                                <option v-for = "supplier in filteredSuppliers" :key = "supplier.id">{{supplier.company}}</option>   
                            </datalist>
                            <div class="input-group-append" data-bs-toggle="modal" data-bs-target="#addSupplier">
                                <button class="input-group-text" type="button">
                                    <i class="fa fa-search" style="font-size:24px"></i>
                                </button>
                            </div>
                            <div class="input-group-append">
                                <button class="input-group-text" type="button">
                                    <i class="fa fa-plus" style="font-size:24px" @click="jumpToAddSupplier"></i>
                                </button>
                            </div>
                        </div>
                        <!-- Input description of the purchase -->
                        <div class="form-group">
                            <label for="remarks">{{$t('Description')}}</label>
                            <input type="text" class="form-control" :placeholder="$t('e.g. Fragile; handle with care!')" id= "remarks" v-model="remarks" required>
                        </div>
                        <!-- Input date of the purchase -->
                        <div class="form-group">
                            <label for="date">{{$t('Date')}}</label>
                            <input type="date" class="form-control" id= "date" v-model="date" required>
                        </div>
                        <!-- Click the button to submit the purchase form to the backend -->
                        <div class="row align-items-center">
                            <div class="empty-row2"></div>
                            <div class="col-6">
                                <button type="submit" class="btn btn-primary">
                                    {{$t('Purchase.Add purchase')}}
                                </button>
                            </div>
                            <div class="col-6">
                                <router-link :to="{name:'Purchase'}"><button type="button" class="btn btn-outline-primary">{{$t('Purchase.View Purchases')}}</button></router-link>
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
import SupplierService from '@/services/Supplier/SupplierService'
import PurchaseService from '@/services/Purchase/PurchaseService'
import BilibiliService from '@/services/Bilibili/BilibiliService'
export default {
    data() {
        return {
            name: '',//store the name of the purchase
            type: '',//store the type of the purchase
            quantity: '',//store the quantity of the purchase
            price: '',//store the price of the purchase
            supplierName: '',//store the supplier name of the purchase
            tempSupplierName: '',//store the name of the supplier temporarily
            tempProductName: '',//store the name of the product temporarily
            remarks: '',//store the remarks of the purchase
            date: '',//store the date of the purchase
            purchases: '',
            supplierSearch: '',
            productSearch: '',
            products: [],
            productName: '',//name of the product
            isSuccess: false,//true if the purchase is added successfully, else false
            supplierNotFound: false,//true if the supplier is not found, else false
            productNotFound: false,//true if the product is not found, else false
            supplierExistence : true,//true if the supplier is not found, else false
            availability : true,//true if the number of products is available, else false
            noAvailability: '',//true if the number of products is not available, else false
            suppliers: [],//store suppliers
            addNewType: false,//true if a new type will be added, else false,
            distinctType: [],
            newType: '',//store new type of the purchase
            quantityError: false,
            priceError: false,
        };
    },
    methods: {
        // add a new purchase
        //check the validity of all inputs
        async onCreatePost() {
            if (this.quantityError || this.priceError) {
                alert('Please correct the errors before submitting.');
                return;
            }
            await this.checkSupplierExistence()
            this.addPurchase()
        },
        // pass the purchase information to the backend
        addPurchase(){
            if(this.supplierExistence){
                if(this.addNewType){
                    axios
                    .post(`addPurchase`, {
                        name: this.name,
                        type: this.newType,
                        num: this.quantity,
                        price: this.price,
                        total: this.price * this.quantity,
                        supplierName: this.supplierName,
                        remarks: this.remarks,
                        date: this.date
                    })
                    .then((response) => {
                        this.isSuccess = true;
                        this.supplierNotFound = false;
                        this.productNotFound = false;
                        this.isSuccess = true;
                        this.supplierNotFound = false;
                        this.productNotFound = false;
                        this.name = '',
                        this.type = '',
                        this.newType = ''
                        this.quantity = '',
                        this.price = '',
                        this.supplierName = '',
                        this.remarks = '',
                        this.date = ''
                    });
                    this.addNewType = false;
                }
                else{
                    axios
                    .post(`addPurchase`, {
                        name: this.name,
                        type: this.type,
                        num: this.quantity,
                        price: this.price,
                        total: this.price * this.quantity,
                        supplierName: this.supplierName,
                        remarks: this.remarks,
                        date: this.date
                    })
                    .then((response) => {
                        this.isSuccess = true;
                        this.supplierNotFound = false;
                        this.productNotFound = false;
                        this.name = '',
                        this.type = '',
                        this.newType = ''
                        this.quantity = '',
                        this.price = '',
                        this.supplierName = '',
                        this.remarks = '',
                        this.date = ''
                    });
                }
            }
        },
        //check the existence of the supplier in the database
        async checkSupplierExistence(){
            await axios
                .get('checkSupplierExistence', {params:{supplierName:this.supplierName}  
                })
                .then((response) => {
                    if(response.data == false){
                        this.supplierNotFound = true;
                        this.isSuccess = false;
                        this.productNotFound = false;
                        this.supplierExistence = false;
                    }else{
                        this.supplierNotFound = false;
                        this.isSuccess = false;
                        this.productNotFound = false;
                        this.supplierExistence = true;
                    }
                });
        },
        //get suppliers 
        getAllSuppliers(){
            SupplierService.getSuppliers().then((response)=>{
                this.suppliers = response.data
            })
        },
        //get products 
        getAllProducts(){
            BilibiliService.getDistinctProducts().then((response)=>{
                this.products = response.data
            })
        },
        //automatically fill in the purchase form 
        auto(){
            PurchaseService.getPurchaseContent(this.name).then((response)=>{
                this.type = response.data.type,
                this.quantity = response.data.num,
                this.price = response.data.price,
                this.supplierName = response.data.supplierName,
                this.remarks = response.data.remarks,
                this.date = response.data.date
            })
        },
        setProduct(){
            this.name = this.tempProductName
        },
        setSupplier(){
            this.supplierName = this.tempSupplierName
        },
        setTempSupplier(company){
            this.tempSupplierName = company
        },
        setTempProduct(name){
            this.tempProductName = name
        },
        //go to the add supplier page
        jumpToAddSupplier(){
            this.$router.push({path:'/addSupplier'})
        },
        //show adding new type input bar
        showNewType(){
            PurchaseService.getPurchasesWithDistinctTypes().then((response)=>{
                this.distinctType = response.data
            })
            if(this.type == 'Add a new type'){
                this.addNewType = true
                $('showInput').show()
            }else{
                this.addNewType = false
                $('showInput').hide()
            }
        },
        //Check if the input quantity is valid
        validateQuantity() {
              const quantityRegex = /^[1-9]\d*$/;
              if (!quantityRegex.test(this.quantity)) {
                this.quantityError = true;
              } else {
                this.quantityError = false;
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
        }
    },
    computed:{
        //Get required suppliers after searching
        filteredSuppliers(){
            return this.suppliers.filter(supplier => supplier.company.toLowerCase().includes(this.supplierSearch.toLowerCase()));
        },
        //Get required products after searching
        filteredProducts(){
            return this.products.filter(product => product.name.toLowerCase().includes(this.productSearch.toLowerCase()));
        },
    },
    created(){
        this.getAllSuppliers()
        this.getAllProducts()
    }
};
</script>