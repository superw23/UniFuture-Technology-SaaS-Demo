// Made by GRPTeam202205
<template>
    <!-- Product list page for a given type-->
    <!-- Search bar by searching the name of the product -->
    <nav class="navbar">
        <div class="container-fluid">
            <!--Drop-down menu to choose the number of products per page-->
            <div class="dropdown">
                <router-link :to="{name:'Product'}">
                    <img src="/static/Others/back-button.png" width="30" height="30" type="button" @click="backToProductPage"/>
                </router-link>
                <br>
                <button class="btn dropdown-toggle" type="button" id="dropdownMenu2" data-bs-toggle="dropdown" aria-expanded="false">
                    {{$t('Records Per Page')}}
                </button>
                <ul class="dropdown-menu" aria-labelledby="dropdownMenu2">
                    <li><button class="dropdown-item" type="button" @click="changeItemPerPage(5)">5</button></li>
                    <li><button class="dropdown-item" type="button" @click="changeItemPerPage(10)">10</button></li>
                    <li><button class="dropdown-item" type="button" @click="changeItemPerPage(15)">15</button></li>
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
        {{$t('Product.Products List')}}
        </h1>
        <h2>
        {{$t('Type of Products: ')}}{{this.typeName}}
        </h2>
        <!-- Success Alert will be shown if the product is successfully deleted from the database. -->
        <div class="alert alert-success" v-if="isSuccess">
            {{$t('Product.The product is deleted successfully!')}}
        </div>
        <div class="table-responsive">
        <table class="table table-stripped">
            <thead>
                <th>
                    <i class="fa fa-sort-alpha-asc" type="button" aria-hidden="true" @click="sort('name')"/>
                    {{$t('Name')}}
                </th>
                <th>
                    <i class="fa fa-sort-alpha-asc" type="button" aria-hidden="true" @click="sort('quantity')"/>
                    {{$t('Quantity')}}
                </th>
                <th>
                    <i class="fa fa-sort-alpha-asc" type="button" aria-hidden="true" @click="sort('purchasePrice')"/>
                    {{$t('Purchase.Purchase Price')}}
                </th>
                <th>
                    <i class="fa fa-sort-alpha-asc" type="button" aria-hidden="true" @click="sort('sellPrice')"/>
                    {{$t('Sale Price')}}
                </th>
                <th>
                    {{$t('Supplier Info')}}
                </th>
                <th>
                    <i class="fa fa-sort-alpha-asc" type="button" aria-hidden="true" @click="sort('remarks')"/>
                    {{$t('Description')}}
                </th>
                <th>{{$t('Edit')}}</th>
            </thead>
            <tbody>
                <tr v-for = "product in filteredProducts" v-bind:key = "product.id" class="table-bordered">
                    <td>{{product.name}}</td>
                    <td>{{product.quantity}}</td>
                    <td>{{product.purchasePrice}}</td>
                    <td>{{product.sellPrice}}</td>
                    <td>
                        <router-link :to="'/supplierDetails/'+ product.name">
                            <fa icon="address-card" type="button" />
                        </router-link>
                    </td>  
                    <td>{{product.remarks}}</td>
                    <td>
                        <fa icon="trash" type="button" data-bs-toggle="modal" @click="deleteProduct(product.id)"/>
                    </td>
                </tr>
            </tbody>
        </table>
        </div>
        <!-- Show the total product quantity -->
        <h5>{{$t('Total stock')}}: {{total}}</h5>
    </div>

    <!--Page navigation bar-->
    <nav class="navbar">
        <div class="container-fluid">
            <a class="navbar-brand"></a>
            <nav aria-label="Page navigation example">
                <ul class="pagination">
                    <li class="page-item"><a class="page-link" href="#" @click="pagination(this.pageNum - 1)">{{$t('Previous')}}</a></li>
                    <li class="page-item"><a class="page-link" href="#" @click="pagination(0)">1</a></li>
                    <li class="page-item"><a class="page-link" href="#" @click="pagination(1)">2</a></li>
                    <li class="page-item"><a class="page-link" href="#" @click="pagination(2)">3</a></li>
                    <li class="page-item"><a class="page-link" href="#" @click="pagination(pageNum+1)">{{$t('Next')}}</a></li>
                </ul>
            </nav>
        </div>
    </nav>
</template>

<script>
import BilibiliService from '../../services/Bilibili/BilibiliService'
export default {
    name: 'ProductDetails',
    data(){
        return{
          itemsPerPage: 5,//the number of products shown in one page
          pageNum: 0,//store current page number
          search:'',//store search content
          typeName: '',//store the name of the product
          isSuccess: false,//true if the product is deleted successfully, else false
          products: [],//store all products
        }
    },
    methods:{
        //Get all products of a given type from the database
        getProducts(){
            BilibiliService.getProductsWithGivenType(this.typeName).then((response)=>{
                this.products = response.data;
            });
        },
        //Delete a chosen product from the database
        deleteProduct(productId) {
            BilibiliService.deleteProduct(productId).then((response)=>{
                this.isSuccess = true;
                this.getProducts()
            });
        },
        //Sort products by a given field, such as id, name
        sort(field){
            BilibiliService.sortAscending(field).then((response)=>{
                this.products = response.data;
            });
        },
        //Get products information as required after paging
        pagination(pageNum){
            this.pageNum = pageNum;
            if(this.pageNum==-1) {
                this.pageNum=0;
                return;
            }
            BilibiliService.pagination(pageNum,this.itemsPerPage).then((response)=>{
                this.products = response.data;
            });
        },
        //Change the number of products shown per page 
        changeItemPerPage(num){
            this.itemsPerPage = num;
            this.pagination(this.pageNum,this.itemsPerPage);
        },
        //go to product list page
        backToProductPage(){
            this.$router.push({path:'/viewProduct'})
        }
    },
    created(){
      this.typeName = this.$route.params.typeName
      //Get all products data when the page is being loaded
      this.getProducts()
    },
    computed:{
        //Get required products after searching
        filteredProducts(){
            return this.products.filter(product => product.name.toLowerCase().includes(this.search.toLowerCase()));
        },
        //Get total number of products
        total(){
          return this.products.length;
        }
    },
}
</script>

<style>

</style>