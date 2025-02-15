// Made by GRPTeam202205
<template>
    <!--Search bar-->
    <nav class="navbar">
        <div class="container-fluid">
            <!--Drop-down menu to choose the number of Records Per Page-->
            <div class="dropdown">
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

    <!--Edit Sale-->
    <div class="modal fade" id="exampleModal" ref="saleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
        <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">
            <span v-if="editing">{{$t('Sale.Edit Sale')}}</span>
            <span v-else>{{$t('Add A New Sale')}}</span>
            </h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
            <form>
            <div class="mb-3">
                <label for="id" class="col-form-label">{{$t('Order No.')}}:</label>
                <input type="text" class="form-control" id="id" ref="id" value="" disabled="disabled">
            </div>
            <div class="mb-3">
                <label for="name" class="col-form-label">{{$t('Name')}}:</label>
                <input type="text" class="form-control" id="name" ref="name" value="">
            </div>
            <div class="mb-3">
                <label for="total" class="col-form-label">{{$t('Total')}}:</label>
                <input type="text" class="form-control" id="total" ref="total" value="">
            </div>
            <div class="mb-3">
                <label for="num" class="col-form-label">{{$t('Number')}}:</label>
                <input type="text" class="form-control" id="num" ref="num" value="" disabled="disabled">
            </div>
            <div class="mb-3">
                <label for="price" class="col-form-label">{{$t('Sale.Sale Price')}}:</label>
                <input type="text" class="form-control" id="price" ref="price" value="">
            </div>
            <div class="mb-3">
                <label for="productId" class="col-form-label">{{$t('Product ID')}}:</label>
                <input type="text" class="form-control" id="productId" ref="productId" value="" disabled="disabled">
            </div>
            <div class="mb-3">
                <label for="customerName" class="col-form-label">{{$t('Customer Name')}}:</label>
                <input type="text" list="datalist" class="form-control" id="customerName" v-model="this.customerName">
                <datalist id="datalist">
                    <option v-for = "customer in filteredCustomers" :key = "customer.id">{{customer.name}}</option>   
                </datalist> 
            </div>
            <div class="mb-3">
                <label for="date" class="col-form-label">{{$t('Date')}}:</label>
                <input type="text" class="form-control" id="date" ref="date" value="">
            </div>
            </form>
        </div>
        <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">{{$t('Close')}}</button>
            <button type="button" class="btn btn-primary" data-bs-dismiss="modal" @click="updateSale('update')">{{$t('Send')}}</button>
        </div>
        </div>
    </div>
    </div>

    <!-- Get Return Price -->
    <div class="modal fade" id="returnSale" ref="saleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
        <div class="modal-header">
            <h5 class="modal-title" id="returnSale">
            <span>{{$t('Sale.Return Sale')}}</span>
            </h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
            <form>
            <div class="mb-3">
                <label for="returnPrice" class="col-form-label">{{$t('Return Price')}}:</label>
                <input type="text" class="form-control" id="returnPrice" ref="returnPrice" value="" @input="validatePrice(this.$refs.returnPrice.value)" required>
                <div v-if="priceError" class="text-danger">{{$t('Please input a valid sale price')}}</div>
            </div>
            <div class="mb-3">
                <label for="returnNum" class="col-form-label">{{$t('Return Quantity')}}:</label>
                <input type="text" class="form-control" id="returnNum" ref="returnNum" value="" @input="validateQuantity(this.$refs.returnNum.value)" required>
                <div v-if="hasQuantityError" class="text-danger">{{$t('Please input a valid quantity')}}</div>
                <div v-if="invalidQuantity" class="text-danger">{{$t('The quantity cannot be larger than the original input quantity')}}</div>
            </div>
            </form>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">{{$t('Close')}}</button>
                <button type="button" class="btn btn-primary" data-bs-dismiss="modal" @click="updateSale('return')">{{$t('Send')}}</button>
            </div>
        </div> 
        </div>
    </div>
    </div>

    <!--Sale List-->
    <div class="container">
        <h1 class="test-center">{{$t('Sale.Sales History')}}
            <router-link :to="{name:'AddSale'}"><i class="fa fa-cart-plus" style="font-size:42px"></i></router-link>
        </h1>
        <br>
        <div class="alert alert-success" v-if="isReturned">
            {{$t('Sale.The sale is returned successfully!')}}
        </div>
        <div class="alert alert-success" v-if="isEdited">
            {{$t('Sale.The sale is updated successfully!')}}
        </div>
        <div class="row align-items-center">
            <div class="col"></div>
            <div class="col-2">
                {{$t('Start Date:')}} <input  type="date" class="form-control me-2" :placeholder="$t('Choose a start date')" id= "date" v-model="startDate">
            </div>
            <div class="col-2">
                {{$t('End Date:')}} <input type="date" class="form-control" :placeholder="$t('Choose an end date')" id= "date" v-model="endDate">
            </div>
            <div class="col-auto">
            <img src="/static/Others/check.png" width="20" height="20" type="submit" @click="getSalesWithinGivenDates"/>
            </div>
            <div class="col"></div>
        </div>
        <br>
        <div class="table-responsive">
            <table class="table table-stripped">
                <thead>
                    <th>
                        <i class="fa fa-sort-numeric-asc" type="button" aria-hidden="true" @click="sort('id')"/>
                        {{$t('Order No.')}}
                    </th>
                    <th>
                        <i class="fa fa-sort-alpha-asc" type="button" aria-hidden="true" @click="sort('name')"/>
                        {{$t('Name')}}
                    </th>
                    <th>
                        <i class="fa fa-sort-alpha-asc" type="button" aria-hidden="true" @click="sort('num')"/>
                        {{$t('Quantity')}}
                    </th>
                    <th>
                        <i class="fa fa-sort-alpha-asc" type="button" aria-hidden="true" @click="sort('price')"/>
                        {{$t('Sale.Sale Price')}}
                    </th>
                    <th>
                        <i class="fa fa-sort-alpha-asc" type="button" aria-hidden="true" @click="sort('total')"/>
                        {{$t('Total')}}
                    </th>
                    <th>
                        <i class="fa fa-sort-alpha-asc" type="button" aria-hidden="true" @click="sort('productId')"/>
                        {{$t('Product ID')}}
                    </th>
                    <th>
                        <i class="fa fa-sort-alpha-asc" type="button" aria-hidden="true" @click="sort('customerName')"/>
                        {{$t('Customer Name')}}
                    </th>
                    <th>
                        <i class="fa fa-sort-alpha-asc" type="button" aria-hidden="true" @click="sort('date')"/>
                        {{$t('Date')}}
                    </th>
                    <th>{{$t('Edit')}}</th>
                </thead>
                <tbody>
                    <tr v-for = "sale in filteredSales" v-bind:key = "sale.id" class="table-bordered">
                        <td>{{sale.id}}</td>
                        <td>{{sale.name}}</td>
                        <td>{{sale.num}}</td>
                        <td>{{sale.price}}</td>
                        <td>{{sale.total}}</td>
                        <td>{{sale.productId}}</td>
                        <td>{{sale.customerName}}</td>
                        <td>{{sale.date}}</td>
                        <td>
                            <fa icon="rotate-left" type="button" data-bs-toggle="modal" data-bs-target="#returnSale" @click="passSale(sale)"/>&nbsp;
                            <fa icon="edit" type="button" data-bs-toggle="modal" data-bs-target="#exampleModal" data-bs-whatever="@mdo" @click="passSale(sale)"/>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        <h5>{{$t('Total stock')}}: {{total}}</h5>
         <img src="/static/Others/pdf.png" width="38" height="35" type="button" @click="downloadPDF"/>
        &nbsp
        <img src="/static/Others/xlsx.png" width="35" height="35" type="button" @click="downloadExcel"/>
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
import SaleService from '@/services/Sale/SaleService'
import CustomerService from '@/services/Customer/CustomerService'
import axios from 'axios';
import jsPDF from 'jspdf';
import autoTable from 'jspdf-autotable';
import * as XLSX from 'xlsx'

function s2ab(s) {
  // Convert a string to an ArrayBuffer
  const buf = new ArrayBuffer(s.length);
  const view = new Uint8Array(buf);
  for (let i = 0; i < s.length; i++) {
    view[i] = s.charCodeAt(i) & 0xFF;
  }
  return buf;
}
export default {
    name: 'Sales',
    data(){
        return{
            itemsPerPage: 5,//the number of customer shown in one page
            pageNum: 0,//store current page number
            sales: [],
            fixedSales: [],
            search:'',//store search content
            customers: [],
            startDate: '',
            endDate: '',
            tempReturnNum: '',
            editing: false,
            isReturned: false,
            isEdited: false,
            hasQuantityError: false,
            priceError: false,
            invalidQuantity: false,
            customerName: '',
            pdfIconSize: '2em',
            excelIconSize: '2em',
        }
    },
    methods:{
        //Get all sales records
        async getSales(){
            await SaleService.getSales().then((response)=>{
                this.sales = response.data;
                this.fixedSales = response.data;
            });
        },
        //Get sales records within given dates
        getSalesWithinGivenDates(){
            SaleService.getSalesWithinGivenDates(this.startDate,this.endDate).then((response)=>{
                this.sales = response.data;
            });
        },
        //Get all customers
        getCustomers(){
            CustomerService.getCustomers().then((response)=>{
                this.customers = response.data;
            })
        },
        //Sort sales records of a given field
        sort(field){
            SaleService.sort(this.pageNum, this.itemsPerPage, field).then((response)=>{
                this.sales = response.data;
            });
        },
        //Set the number of records shown in one page
        pagination(pageNum){
            this.pageNum = pageNum;
            if(this.pageNum==-1) {
                this.pageNum=0;
                return;
            }
            SaleService.pagination(pageNum,this.itemsPerPage).then((response)=>{
                this.sales = response.data;
            });
        },
        //Set the number of records shown in one page
        changeItemPerPage(num){
            this.itemsPerPage = num;
            this.pagination(this.pageNum,this.itemsPerPage);
        },
        //Update or return a sales record
        updateSale(updateType){
            if (this.hasQuantityError || this.priceError ||this.invalidQuantity) {
                alert('Please correct the errors before submitting.');
                return;
            }
            else{
                 axios
                .put(`updateSale`, {
                    id: this.$refs.id.value,
                    name: this.$refs.name.value,
                    num: this.$refs.num.value,
                    total: this.$refs.total.value,
                    price: this.$refs.price.value,
                    customerName: this.customerName,
                    productId: this.$refs.productId.value,
                    returnPrice: this.$refs.returnPrice.value,
                    returnNum: this.$refs.returnNum.value,
                    date: this.$refs.date.value
                },{params:{updateType: updateType}})
                .then((response) => {
                    if(updateType == 'return'){
                        this.isReturned = true
                        this.isEdited = false
                        this.hasQuantityError = false
                        this.invalidQuantity = false
                        this.priceError = false
                    }
                    else{
                        this.isEdited = true
                        this.isReturned = false
                        this.hasQuantityError = false
                        this.invalidQuantity = false
                        this.priceError = false
                    }
                    this.getSales()
                });
            }
        },
        //Pass the sales info to the edit form
        passSale(sale){
            this.editing = true
            this.hasQuantityError = false
            this.priceError = false
            this.invalidQuantity = false
            this.$refs.id.value = sale.id;
            this.$refs.name.value = sale.name;
            this.$refs.num.value = sale.num;
            this.$refs.returnNum.value = sale.num;
            this.$refs.total.value = sale.total;
            this.$refs.price.value = sale.price,
            this.$refs.returnPrice.value = sale.price,
            this.$refs.productId.value = sale.productId,
            this.customerName = sale.customerName,
            this.$refs.date.value = sale.date
            this.tempReturnNum = sale.num;
        },
        //Check if the input quantity is valid
        validateQuantity(quantity) {
              const quantityRegex = /^[1-9]\d*$/;
              if (!quantityRegex.test(quantity)) {
                this.hasQuantityError = true
              } else if(quantity > this.tempReturnNum){
                this.invalidQuantity = true
              }else {
                this.hasQuantityError = false
                this.invalidQuantity = false
              }
        },
        //Check if the input price is valid
        validatePrice(price) {
              const priceRegex = /^[1-9]\d*(\.\d{1,2})?$/;
              if (!priceRegex.test(price)) {
                this.priceError = true;
              } else {
                this.priceError = false;
              }
        },
        //Download the sales form in pdf format
        downloadPDF(){
            const columns = [
                    { header: 'Order No.', dataKey: 'id' },
                    { header: 'Customer Name', dataKey: 'customerName' },
                    { header: 'Product ID', dataKey: 'productId' },
                    { header: 'Name', dataKey: 'name' },
                    { header: 'Quantity', dataKey: 'num' },
                    { header: 'Sale Price', dataKey: 'price' },
                    { header: 'Total', dataKey: 'total' }
                ];
            const pdf = new jsPDF();
            //text is placed using x, y coordinates
            pdf.setFontSize(20).text("Sales History", 80, 10);
            //use autoTable plugin
            autoTable(pdf, {
                columns,
                body: this.sales,
                margin:{top: 30}
            });
            //show total stock
            pdf.setFontSize(16).text('Total stock: ' + this.total, 90, 20);
            //download pdf
            pdf.save("Sales.pdf");
        },
        //Download the sales form in excel format
        downloadExcel() {
            const filteredSalesWithoutReturn = this.filteredSales.map(sales => {
                return {
                    OrderNo: sales.id,
                    Name: sales.name,
                    ProductID: sales.productId,
                    Quantity: sales.num,
                    Price: sales.price,
                    Total: sales.total,
                    Customer: sales.customerName,
                    Date: sales.date,
                    };
            });

           // Create a new workbook
           const wb = XLSX.utils.book_new();
           // Add a new worksheet to the workbook
           const ws = XLSX.utils.json_to_sheet(filteredSalesWithoutReturn);
           // Add the worksheet to the workbook
           XLSX.utils.book_append_sheet(wb, ws, 'Sales');
           // Write the workbook to a binary string
           const wbout = XLSX.write(wb, { bookType: 'xlsx', type: 'binary' });
           // Convert the binary string to a Blob object
           const blob = new Blob([s2ab(wbout)], { type: 'application/octet-stream' });
           // Create a temporary link to the file and trigger a download
           const url = window.URL.createObjectURL(blob);
           const link = document.createElement('a');
           link.href = url;
           link.download = 'Sales History.xlsx';
           document.body.appendChild(link);
           link.click();
           document.body.removeChild(link);
        }
    },
    async created(){
        await this.getSales()
        this.getCustomers()
        this.itemsPerPage = this.sales.length
    },
    computed:{
        //Filter sales records according to the product name
        filteredSales(){
            return this.sales.filter(sale => sale.name.toLowerCase().includes(this.search.toLowerCase()));
        },
        //Filter customers according to user input
        filteredCustomers(){
            return this.customers.filter(customer => customer.name.toLowerCase().includes(this.customerName.toLowerCase()));
        },
        //Caculate total price of sales records
        caculateTotal(){
            return this.sales.map(sale => Number(sale.num) * Number(sale.sell_price))
        },
        //Caculate total number of sales records
        total(){
            return this.sales.reduce((total,curr)=>(total=total+curr.num),0);
        },
        //Calculate total pages of sales records
        totalPages(){
            return (Math.ceil(this.fixedSales.length / this.itemsPerPage))
        }
    }
}
</script>

<style>

</style>