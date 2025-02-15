// Made by GRPTeam202205
<template>
    <!-- Add a new product page -->
    <div>
        <h2>{{$t('Product.Create New Product')}}</h2>
        <hr />

        <div class="row col-md-4 offset-md-4">
            <div class="alert alert-success" v-if="isSuccess">
                {{$t('Product.The product is added successfully!')}}
            </div>
            <div class="alert alert-danger" v-if="supplierNotExist">
                {{$t('The supplier does not exist!')}}
            </div>
            <div class="icon1"></div> <div class="empty-row"></div> <div class="icon2"></div>
            <div class="panel panel-primary">
                <div class="panel-body">
                    <form @submit.prevent="onCreatePost">
                        <div class="form-group">
                            <label for="name">{{$t('Name')}}</label>
                            <input type="text" class="form-control" :placeholder="$t('e.g. Wine')" id= "name" v-model="name" required>
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
                        <div class="form-group">
                            <label for="quantity">{{$t('Quantity')}}</label>
                            <input type="text" class="form-control" :placeholder="$t('e.g. 20')" id= "quantity" v-model="quantity" @input="validateQuantity" required>
                            <div v-if="quantityError" class="text-danger">{{$t('Please input a valid quantity')}}</div>
                        </div>
                        <div class="form-group">
                            <label for="purchasePrice">{{$t('Purchase.Purchase Price')}}</label>
                            <input type="text" class="form-control" :placeholder="$t('e.g. 20')" id= "purchasePrice" v-model="purchasePrice" @input="validatePurchasePrice" required>
                            <div v-if="PurchasePriceError" class="text-danger">{{$t('Please input a valid purchase price')}}</div>
                        </div>
                        <div class="form-group">
                            <label for="sellPrice">{{$t('Sale Price')}}</label>
                            <input type="text" class="form-control" :placeholder="$t('e.g. 20')" id= "sellPrice" v-model="sellPrice" @input="validateSellPrice" required>
                            <div v-if="SellPriceError" class="text-danger">{{$t('Please input a valid sale price')}}</div>
                        </div>
                        <!-- Input supplier name of the purchase -->
                        <label for="supplierName">{{$t('Supplier Name')}}</label>
                            <div class="input-group mb-3">
                            <input type="text" list="datalist" class="form-control" :placeholder="$t('e.g. Tom')" id= "supplierName" v-model="supplierName" required>
                            <datalist id="datalist">
                                <option v-for = "supplier in filteredSuppliers" :key = "supplier.id">{{supplier.company}}</option>   
                            </datalist>
                            <div class="input-group-append">
                                <button class="input-group-text" type="button">
                                    <i class="fa fa-plus" style="font-size:24px" @click="jumpToAddSupplier"></i>
                                </button>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="remarks">{{$t('Description')}}</label>
                            <input type="text" class="form-control" :placeholder="$t('e.g. Fragile; handle with care!')" id= "remarks" v-model="remarks" required>
                        </div>
                        <div class="row align-items-center">
                            <div class="empty-row2"></div>
                            <div class="col-6">
                                <!-- Click the button to submit the user form to the backend -->
                                <button type="submit" class="btn btn-primary">
                                    {{$t('Product.Add Product')}}
                                </button>
                            </div>
                            <div class="col-6">
                                <router-link :to="{name:'Product'}"><button type="button" class="btn btn-outline-primary">{{$t('Product.View Products')}}</button></router-link>
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
import PurchaseService from '@/services/Purchase/PurchaseService'
import SupplierService from '../../services/Supplier/SupplierService'
export default {
    data() {
        return {
            name: '',
            type: '',
            newType: '',
            quantity: '',
            purchasePrice: '',
            sellPrice: '',
            supplierName: '',
            remarks: '',
            suppliers: [],//store suppliers
            distinctType: [],
            addNewType: false,
            isSuccess: false,
            supplierNotExist: false,
            quantityError: false,
            PurchasePriceError: false,
            SellPriceError: false,
        };
    },
    methods: {
        onCreatePost() {
            if (this.quantityError || this.PurchasePriceError || this.SellPriceError) {
                alert('Please correct the errors before submitting.');
                return;
            }
            if(this.addNewType){
                axios
                .post(`addProduct`, {
                    name: this.name,
                    type: this.newType,
                    quantity: this.quantity,
                    purchasePrice: this.purchasePrice,
                    sellPrice: this.sellPrice,
                    supplierName: this.supplierName,
                    remarks: this.remarks
                })
                .then((response) => {
                    if(response.data==""){
                        this.supplierNotExist = true
                        this.isSuccess = false
                    }
                    else{
                        this.isSuccess = true
                        this.supplierNotExist = false
                        this.name = ''
                        this.type = ''
                        this.newType = ''
                        this.quantity = ''
                        this.purchasePrice = ''
                        this.sellPrice = ''
                        this.supplierName = ''
                        this.remarks = ''
                        this.quantityError= false
                        this.PurchasePriceError= false
                        this.SellPriceError= false
                        this.addNewType = false
                        $('showInput').hide()
                    }
                });
            }
            else{
                axios
                .post(`addProduct`, {
                    name: this.name,
                    type: this.type,
                    quantity: this.quantity,
                    purchasePrice: this.purchasePrice,
                    sellPrice: this.sellPrice,
                    supplierName: this.supplierName,
                    remarks: this.remarks
                })
                .then((response) => {
                    if(response.data==""){
                        this.supplierNotExist = true
                        this.isSuccess = false
                    }
                    else{
                        this.isSuccess = true
                        this.supplierNotExist = false
                        this.name = ''
                        this.type = ''
                        this.newType = ''
                        this.quantity = ''
                        this.purchasePrice = ''
                        this.sellPrice = ''
                        this.supplierName = ''
                        this.remarks = ''
                        this.quantityError= false
                        this.PurchasePriceError= false
                        this.SellPriceError= false
                    }
                });
            }
        },
        //go to the add supplier page
        jumpToAddSupplier(){
            this.$router.push({path:'/addSupplier'})
        },
        //get suppliers 
        getAllSuppliers(){
            SupplierService.getSuppliers().then((response)=>{
                this.suppliers = response.data
            })
        }, 
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
        validateQuantity() {
              const quantityRegex = /^[1-9]\d*$/;
              if (!quantityRegex.test(this.quantity)) {
                this.quantityError = true;
              } else {
                this.quantityError = false;
              }
        },
        validatePurchasePrice() {
              const PurchasePriceRegex = /^[0-9]\d*$/;
              if (!PurchasePriceRegex.test(this.purchasePrice)) {
                this.PurchasePriceError = true;
              } else {
                this.PurchasePriceError = false;
              }
        },

        validateSellPrice() {
              const SellPriceRegex = /^[0-9]\d*$/;
              if (!SellPriceRegex.test(this.sellPrice)) {
                this.SellPriceError = true;
              } else {
                this.SellPriceError = false;
              }
        },
    },
    computed:{
        //Get required suppliers 
        filteredSuppliers(){
            return this.suppliers.filter(supplier => supplier.company.toLowerCase().includes(this.supplierName.toLowerCase()));
        }
    },
    created(){
        this.getAllSuppliers()
    }
};
</script>
