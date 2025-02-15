// Made by GRPTeam202205
<template>
  <!-- Supplier list page for a given product-->
  <!-- Show the modal dialog box in order to change info of existing supplier  -->
<div class="modal fade" id="exampleModal" ref="supplierModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <!-- Title of the modal dialog box -->
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">
          <span v-if="editing">{{$t('Supplier.Edit Supplier')}}</span>
          <span v-else>{{$t('Supplier.Add New Supplier')}}</span>
        </h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <!-- Body of the modal dialog box consists of information of a chosen supplier -->
      <div class="modal-body">
        <form>
          <div class="mb-3">
            <label for="id" class="col-form-label">{{$t('ID')}}:</label>
            <input type="text" class="form-control" id="id" ref="id" value="" disabled="disabled">
          </div>
          <div class="mb-3">
            <label for="company" class="col-form-label">{{$t('Company Name')}}:</label>
            <input type="text" class="form-control" id="company" ref="company" value="">
          </div>
          <div class="mb-3">
            <label for="address" class="col-form-label">{{$t('Address')}}:</label>
            <input type="text" class="form-control" id="address" ref="address" value="">
          </div>
          <div class="mb-3">
            <label for="phoneNumber" class="col-form-label">{{$t('Phone Number')}}:</label>
            <input type="text" class="form-control" id="phoneNumber" ref="phoneNumber" value="">
          </div>
          <div class="mb-3">
            <label for="contact" class="col-form-label">{{$t('Contact')}}:</label>
            <input type="text" class="form-control" id="contact" ref="contact" value="">
          </div>
          <div class="mb-3">
            <label for="remarks" class="col-form-label">{{$t('Remarks')}}:</label>
            <input type="text" class="form-control" id="remarks" ref="remarks" value="">
          </div>
        </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">{{$t('Close')}}</button>
        <button type="button" class="btn btn-primary" data-bs-dismiss="modal" @click="updateSupplier()">{{$t('Send')}}</button>
      </div>
    </div>
  </div>
</div>
  <!-- Search bar by searching the name of the supplier -->
    <nav class="navbar">
        <div class="container-fluid">
            <!--Drop-down menu to choose the number of suppliers per page-->
            <div class="dropdown">
                <router-link :to="{name:'Product'}">
                    <img src="/static/Others/back-button.png" width="30" height="30" type="button" @click="backToProductPage"/>
                </router-link>
                <br>
                <button class="btn dropdown-toggle" type="button" id="dropdownMenu2" data-bs-toggle="dropdown" aria-expanded="false">
                    {{$t('Supplier.Suppliers Per Page')}}
                </button>
                <ul class="dropdown-menu" aria-labelledby="dropdownMenu2">
                    <li><button class="dropdown-item" type="button" @click="changeSupplierPerPage(5)">5</button></li>
                    <li><button class="dropdown-item" type="button" @click="changeSupplierPerPage(10)">10</button></li>
                    <li><button class="dropdown-item" type="button" @click="changeSupplierPerPage(15)">15</button></li>
                </ul>
            </div>
            <a class="navbar-brand"></a>
            <form class="d-flex" role="search">
            <input class="form-control me-2" type="search" :placeholder="$t('Search')" aria-label="Search" v-model="search">
            <button class="btn btn-outline-success" type="submit">{{$t('Search')}}</button>
            </form>
        </div>
    </nav>

  <div class="container">
    <h1 class="test-center">
      {{$t('Supplier.Suppliers List')}}
    </h1>
    <h2>
      {{$t('Product Name: '+ this.productName)}}
    </h2>
    <!-- Show the following message if the supplier is deleted successfully-->
    <div class="alert alert-success" v-if="isSuccess">
        {{$t('Supplier.The supplier is deleted successfully!')}}
    </div>
    <!-- Show the following message if the supplier is edited successfully-->
    <div class="alert alert-success" v-if="isEdited">
        {{$t('Supplier.The supplier is edited successfully!')}}
    </div>
    <div class="table-responsive">
    <table class="table table-stripped">
        <thead>
            <th>
              <i class="fa fa-sort-numeric-asc" type="button" aria-hidden="true" @click="sort('id')"/>
              {{$t('Supplier ID')}}
            </th>
            <th>
              <i class="fa fa-sort-alpha-asc" type="button" aria-hidden="true" @click="sort('company')"/>
              {{$t('Company Name')}}
            </th>
            <th>
              <i class="fa fa-sort-alpha-asc" type="button" aria-hidden="true" @click="sort('address')"/>
              {{$t('Address')}}
            </th>
            <th>
              <i class="fa fa-sort-alpha-asc" type="button" aria-hidden="true" @click="sort('phoneNumber')"/>
              {{$t('Phone Number')}}
            </th>
            <th>
              <i class="fa fa-sort-alpha-asc" type="button" aria-hidden="true" @click="sort('contact')"/>
              {{$t('Email')}}
            </th>
            <th>{{$t('Edit')}}</th>
        </thead>
        <tbody>
            <tr v-for = "supplier in filteredSuppliers" v-bind:key = "supplier.id" class="table-bordered">
                <td>{{supplier.id}}</td>
                <td>{{supplier.company}}</td>
                <td>{{supplier.address}}</td>
                <td>{{supplier.phoneNumber}}</td>
                <td>{{supplier.contact}}</td>
                <td>
                  <fa icon="trash" type="button" data-bs-toggle="modal" data-bs-target="#returnSale" @click="deleteSupplier(supplier.id)"/>&nbsp;
                  <fa icon="edit" type="button" data-bs-toggle="modal" data-bs-target="#exampleModal" data-bs-whatever="@mdo" @click="passSupplier(supplier)"/>
                </td>
            </tr>
        </tbody>
    </table>
    </div>
    <h5>{{$t('Supplier.Total suppliers')}}: {{total}}</h5>
  </div>

  <!--Page navigation bar-->
    <nav class="navbar">
        <div class="container-fluid">
            <h5>Total Pages: {{this.totalPages}}</h5>
            <a class="navbar-brand"></a>
            <nav aria-label="Page navigation example">
                <ul class="pagination">
                    <li class="page-item"><a class="page-link" href="#" @click="pagination(this.pageNum - 1)">{{$t('Previous')}}</a></li>
                    <li class="page-item"><a class="page-link" href="#" @click="pagination(0)">{{this.pageNum+1}}</a></li>
                    <li class="page-item"><a class="page-link" href="#" @click="pagination(pageNum+1)">{{$t('Next')}}</a></li>
                </ul>
            </nav>
        </div>
    </nav>
</template>

<script>
import SupplierService from '@/services/Supplier/SupplierService'
import axios from 'axios';

export default {
    name: 'SupplierDetails',
    data(){
        return{
          itemsPerPage: 5,//the number of suppliers shown in one page
          pageNum: 0,//store current page number
          search:'',//store search content
          productName: '',//store the name of the product
          editing: false,//true if the supplier info need editing, else false
          isSuccess: false,//true if the supplier is deleted successfully, else false
          isEdited: false,//true if the supplier info is edited successfully, else false
          fixedSuppliers: [],//store all suppliers
          suppliers: [],//store suppliers per page
        }
    },
    methods:{
        //Get all suppliers from the database
        async getSuppliers(){
            await SupplierService.getSuppliersWithGivenProduct(this.productName).then((response)=>{
                this.suppliers = response.data;
                this.fixedSuppliers = response.data
            });
        },
        //Delete a chosen supplier from the database
        deleteSupplier(supplierId) {
            SupplierService.deleteSupplier(supplierId).then((response)=>{
              this.isSuccess = true;
              this.getSuppliers();
            });
        },
        //Update the information of a chosen supplier
        updateSupplier(){
           axios
                .put(`updateSupplier`, {
                    id: this.$refs.id.value,
                    address: this.$refs.address.value,
                    phoneNumber: this.$refs.phoneNumber.value,
                    contact: this.$refs.contact.value,
                    remarks: this.$refs.remarks.value,
                    company: this.$refs.company.value
                })
                .then((response) => {
                    this.isEdited = true;
                    console.log(response);
                    this.$emit('postcreated');
                });
          
        },
        //Pass the chosen supplier information to the modal dialog box
        passSupplier(supplier){
          this.editing = true;
          this.$refs.id.value = supplier.id;
          this.$refs.address.value = supplier.address;
          this.$refs.phoneNumber.value = supplier.phoneNumber,
          this.$refs.contact.value = supplier.contact,
          this.$refs.remarks.value = supplier.remarks,
          this.$refs.company.value = supplier.company
        },
        //Sort suppliers by a given field, such as id, name
        sort(field){
            SupplierService.sortAscending(this.pageNum, this.itemsPerPage, field).then((response)=>{
                this.suppliers = response.data;
            });
        },
        //Get suppliers information as required after paging
        pagination(pageNum){
            this.pageNum = pageNum;
            if(this.pageNum==-1) {
                this.pageNum=0;
                return;
            }
            console.log(this.pageNum) ;
            SupplierService.pagination(pageNum,this.itemsPerPage).then((response)=>{
                this.suppliers = response.data;
            });
        },
        //Change the number of suppliers shown per page 
        changeSupplierPerPage(num){
            this.itemsPerPage = num;
            this.pagination(this.pageNum,this.itemsPerPage);
        },
        //go to product list page
        backToProductPage(){
            this.$router.push({path:'/viewProduct'})
        }
    },
    async created(){
      console.log(this.$route.params.productName)
      this.productName = this.$route.params.productName
      //Get all suppliers data when the page is being loaded
      await this.getSuppliers()
      this.itemsPerPage = this.suppliers.length
      this.fixedSuppliers = this.getSuppliers()
    },
    computed:{
      //Get required suppliers after searching
      filteredSuppliers(){
          return this.suppliers.filter(supplier => supplier.company.toLowerCase().includes(this.search.toLowerCase()));
      },
      //Get total number of suppliers
      total(){
        return this.suppliers.length;
      },
      totalPages(){
        return (Math.ceil(this.fixedSuppliers.length / this.itemsPerPage))
      }
    },
}
</script>

<style>

</style>