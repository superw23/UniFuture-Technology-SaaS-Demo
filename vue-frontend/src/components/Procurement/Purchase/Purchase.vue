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

    <!--Edit Purchase-->
    <div class="modal fade" id="exampleModal" ref="purchaseModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
        <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">
            <span v-if="editing">{{$t('Purchase.Edit Purchase')}}</span>
            <span v-else>{{$t('Purchase.Add A New Purchase')}}</span>
            </h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
            <form>
            <div class="mb-3">
                <label for="id" class="col-form-label">{{$t('ID')}}:</label>
                <input type="text" class="form-control" id="id" ref="id" value="" disabled="disabled">
            </div>
            <div class="mb-3">
                <label for="name" class="col-form-label">{{$t('Name')}}:</label>
                <input type="text" class="form-control" id="name" ref="name" value="">
            </div>
            <div class="mb-3">
                <label for="type" class="col-form-label">{{$t('Type')}}:</label>
                <input type="text" class="form-control" id="type" ref="type" value="">
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
                <label for="price" class="col-form-label">{{$t('Price')}}:</label>
                <input type="text" class="form-control" id="price" ref="price" value="">
            </div>
            <div class="mb-3">
                <label for="supplierName" class="col-form-label">{{$t('Supplier Name')}}:</label>
                <input type="text" class="form-control" id="supplierName" ref="supplierName" value="">
            </div>
            <div class="mb-3">
                <label for="productId" class="col-form-label">{{$t('Product ID')}}:</label>
                <input type="text" class="form-control" id="productId" ref="productId" value="">
            </div>
            <div class="mb-3">
                <label for="remarks" class="col-form-label">{{$t('Remarks')}}:</label>
                <input type="text" class="form-control" id="remarks" ref="remarks" value="">
            </div>
            <div class="mb-3">
                <label for="date" class="col-form-label">{{$t('Date')}}:</label>
                <input type="text" class="form-control" id="date" ref="date" value="">
            </div>
            </form>
        </div>
        <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">{{$t('Close')}}</button>
            <button type="button" class="btn btn-primary" data-bs-dismiss="modal" @click="updatePurchase(1)">{{$t('Send')}}</button>
        </div>
        </div>
    </div>
    </div>

    <!-- Get return price and quantity -->
    <div class="modal fade" id="returnPurchase" ref="purchaseModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
        <div class="modal-header">
            <h5 class="modal-title" id="returnPurchase">
            <span>{{$t('Purchase.Return Purchase')}}</span>
            </h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
            <form>
            <div class="mb-3">
                <label for="returnPrice" class="col-form-label">{{$t('Return Price')}}:</label>
                <input type="text" class="form-control" id="returnPrice" ref="returnPrice" value="" @input="validatePrice(this.$refs.returnPrice.value)" required>
                <div v-show="priceError" class="text-danger">{{$t('Please input a valid purchase price')}}</div>
            </div>
            <div class="mb-3">
                <label for="returnNum" class="col-form-label">{{$t('Return Quantity')}}:</label>
                <input type="text" class="form-control" id="returnNum" ref="returnNum" value="" @input="validateQuantity(this.$refs.returnNum.value)" required>
                <div v-show="hasQuantityError" class="text-danger">{{$t('Please input a valid quantity')}}</div>
                <div v-if="invalidQuantity" class="text-danger">{{$t('The quantity cannot be larger than the original input quantity')}}</div>
            </div>
            </form>
        </div>
        <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">{{$t('Close')}}</button>
            <button type="button" class="btn btn-primary" data-bs-dismiss="modal" @click="updatePurchase(2)">{{$t('Send')}}</button>
        </div>
        </div>
    </div>
    </div>

    <!--Purchase List-->
    <div class="container">
        <h1 class="test-center">{{$t('Purchase.Purchases History')}}
            <router-link :to="{name:'AddPurchase'}"><i class="fa fa-cart-plus" style="font-size:42px"></i></router-link>
        </h1>
        <br>
        <div class="alert alert-success" v-if="isSuccess">
            {{$t('Purchase.The purchase is edited successfully!')}}
        </div>
        <div class="alert alert-success" v-if="returnSuccess">
            {{$t('Purchase.The purchase is returned successfully!')}}
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
            <img src="/static/Others/check.png" width="20" height="20" type="submit" @click="getPurchasesWithinGivenDates"/>
            </div>
            <div class="col"></div>
        </div>
        <br>
        <div class="table-responsive">
            <table class="table table-stripped">
                <!-- class="table-responsive" -->
                <thead>
                    <th>
                        <i class="fa fa-sort-numeric-asc" type="button" aria-hidden="true" @click="sort('id')"/>
                        {{$t('Purchase.Purchase ID')}} 
                    </th>
                    <th>
                        <i class="fa fa-sort-alpha-asc" type="button" aria-hidden="true" @click="sort('name')"/>
                        {{$t('Name')}}
                    </th>
                    <th>
                        <i class="fa fa-sort-alpha-asc" type="button" aria-hidden="true" @click="sort('type')"/>
                        {{$t('Type')}}
                    </th>
                    <th>
                        <i class="fa fa-sort-alpha-asc" type="button" aria-hidden="true" @click="sort('num')"/>
                        {{$t('Quantity')}}
                    </th>
                    <th>
                        <i class="fa fa-sort-alpha-asc" type="button" aria-hidden="true" @click="sort('price')"/>
                        {{$t('Price')}}
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
                        <i class="fa fa-sort-alpha-asc" type="button" aria-hidden="true" @click="sort('supplierName')"/>
                        {{$t('Supplier Name')}}
                    </th>
                    <th>
                        <i class="fa fa-sort-alpha-asc" type="button" aria-hidden="true" @click="sort('remarks')"/>
                        {{$t('Description')}}
                    </th>
                    <th>
                        <i class="fa fa-sort-alpha-asc" type="button" aria-hidden="true" @click="sort('date')"/>
                        {{$t('Date')}}
                    </th>
                    <th>{{$t('Edit')}}</th>
                </thead>
                <tbody>
                    <tr v-for = "purchase in filteredPurchases" v-bind:key = "purchase.id" class="table-bordered">
                        <td>{{purchase.id}}</td>
                        <td>{{purchase.name}}</td>
                        <td><span class="badge badge-success rounded-pill">{{purchase.type}}</span></td>
                        <td>{{purchase.num}}</td>
                        <td>{{purchase.price}}</td>
                        <td>{{purchase.total}}</td>
                        <td>{{purchase.productId}}</td>
                        <td>{{purchase.supplierName}}</td>
                        <td>{{purchase.remarks}}</td>
                        <td>{{purchase.date}}</td>
                        <td>
                            <fa icon="rotate-left" type="button" data-bs-toggle="modal" data-bs-target="#returnPurchase" @click="passPurchase(purchase)"/>&nbsp;
                            <fa icon="edit" type="button" data-bs-toggle="modal" data-bs-target="#exampleModal" data-bs-whatever="@mdo" @click="passPurchase(purchase)"/>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        <h5>{{$t('Total stock')}}:{{total}}</h5>
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
import PurchaseService from '@/services/Purchase/PurchaseService'
import axios from 'axios'
import jsPDF from 'jspdf'
import autoTable from 'jspdf-autotable'
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
    name: 'Purchases',
    data(){
        return{
            itemsPerPage: 5,//the number of customer shown in one page
            pageNum: 0,//store current page number
            purchases: [],
            fixedPurchases: [],
            search:'',//store search content
            startDate: '',
            endDate: '',
            tempReturnNum: '',
            invalidQuantity: '',
            isSuccess: false,
            editing: false,
            returnSuccess: false,
            hasQuantityError: false,
            priceError: false,
            pdfIconSize: '2em',
            excelIconSize: '2em',
        }
    },
    methods:{
        //Get all purchase records
        async getPurchases(){
            await PurchaseService.getPurchases().then((response)=>{
                this.purchases = response.data;
                this.fixedPurchases = response.data;
            });
        },
        //Sort purchase records of a given field
        sort(field){
            PurchaseService.sort(this.pageNum, this.itemsPerPage, field).then((response)=>{
                this.purchases = response.data;
            });
        },
        //Get purchase records within given dates
        getPurchasesWithinGivenDates(){
            PurchaseService.getPurchasesWithinGivenDates(this.startDate,this.endDate).then((response)=>{
                this.purchases = response.data;
            });
        },
        //Set the number of records shown in one page
        pagination(pageNum){
            this.pageNum = pageNum;
            if(this.pageNum==-1) {
                this.pageNum=0;
                return;
            }
            PurchaseService.pagination(pageNum,this.itemsPerPage).then((response)=>{
                this.purchases = response.data;
            });
        },
        //Set the number of records shown in one page
        changeItemPerPage(num){
            this.itemsPerPage = num;
            this.pagination(this.pageNum,this.itemsPerPage);
        },
        //Update or return a purchase record
        updatePurchase(i){
            if (this.hasQuantityError || this.priceError ||this.invalidQuantity) {
                alert('Please correct the errors before submitting.');
                return;
            }
            else{
                axios
                .put(`updatePurchase`, {
                    id: this.$refs.id.value,
                    name: this.$refs.name.value,
                    type: this.$refs.type.value,
                    num: this.$refs.num.value,
                    total: this.$refs.total.value,
                    price: this.$refs.price.value,
                    productId: this.$refs.productId.value,
                    supplierName: this.$refs.supplierName.value,
                    remarks: this.$refs.remarks.value,
                    returnPrice: this.$refs.returnPrice.value,
                    returnNum: this.$refs.returnNum.value,
                    date: this.$refs.date.value,
                },{params:{updateType: i}})
                .then((response) => {
                    if(i==1){
                        this.isSuccess = true
                        this.returnSuccess = false
                        this.hasQuantityError = false
                        this.priceError = false
                    }   
                    else{
                        this.isSuccess = false
                        this.returnSuccess = true
                        this.hasQuantityError = false
                        this.priceError = false
                    }
                    this.getPurchases()
                });
            }
        },
        //Pass the purchase info to the edit form
        passPurchase(purchase){
          this.editing = true;
          this.hasQuantityError = false
          this.priceError = false
          this.invalidQuantity = false
          this.$refs.id.value = purchase.id;
          this.$refs.name.value = purchase.name;
          this.$refs.num.value = purchase.num;
          this.$refs.returnNum.value = purchase.num;
          this.$refs.type.value = purchase.type;
          this.$refs.total.value = purchase.total;
          this.$refs.price.value = purchase.price,
          this.$refs.returnPrice.value = purchase.returnPrice,
          this.$refs.productId.value = purchase.productId,
          this.$refs.supplierName.value = purchase.supplierName,
          this.$refs.remarks.value = purchase.remarks,
          this.$refs.date.value = purchase.date,
          this.tempReturnNum = purchase.num;
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
        //Download the purchase form in pdf format
        downloadPDF(){
            const columns = [
                    { header: 'Order No.', dataKey: 'id' },
                    { header: 'Product ID', dataKey: 'productId' },
                    { header: 'Name', dataKey: 'name' },
                    { header: 'Type', dataKey: 'type' },
                    { header: 'Quantity', dataKey: 'num' },
                    { header: 'Purchase Price', dataKey: 'price' },
                    { header: 'Total', dataKey: 'total' },
                    { header: 'Description', dataKey: 'remarks' }
                ];
            const pdf = new jsPDF();
            //text is placed using x, y coordinates
            pdf.setFontSize(20).text("Purchases History", 80, 10);
            //use autoTable plugin
            autoTable(pdf, {
                columns,
                body: this.purchases,
                margin:{top: 30}
            });
            //show total stock
            pdf.setFontSize(16).text('Total stock: ' + this.total, 90, 20);
            //download pdf
            pdf.save("Purchases.pdf");
        },
        //Download the purchase form in excel format
        downloadExcel() {
            const filteredPurchasesWithoutUseless = this.filteredPurchases.map(purchases => {
                return {
                    PurchaseID: purchases.id,
                    Name: purchases.name,
                    Type: purchases.type,
                    Quantity: purchases.num,
                    Price: purchases.price,
                    Total: purchases.total,
                    ProductID: purchases.productId,
                    SupplierName: purchases.supplierName,
                    Description: purchases.remarks,
                    Date: purchases.date,
                    };
            });

            // Create a new workbook
            const wb = XLSX.utils.book_new();

            // Add a new worksheet to the workbook
            const ws = XLSX.utils.json_to_sheet(filteredPurchasesWithoutUseless);

            // Add the worksheet to the workbook
            XLSX.utils.book_append_sheet(wb, ws, 'Purchases');

            // Write the workbook to a binary string
            const wbout = XLSX.write(wb, { bookType: 'xlsx', type: 'binary' });

            // Convert the binary string to a Blob object
            const blob = new Blob([s2ab(wbout)], { type: 'application/octet-stream' });

            // Create a temporary link to the file and trigger a download
            const url = window.URL.createObjectURL(blob);
            const link = document.createElement('a');
            link.href = url;
            link.download = 'Purchases History.xlsx';
            document.body.appendChild(link);
            link.click();
            document.body.removeChild(link);
        },
        Quantity() {
              const quantityRegex = /^[1-9]\d*$/;
              if (!quantityRegex.test(this.quantity)) {
                this.quantityError = true;
              } else {
                this.quantityError = false;
              }
        }
    },
    async created(){
        await this.getPurchases()
        this.itemsPerPage = this.purchases.length
    },
    computed:{
        //Filter purchase records according to the product name
        filteredPurchases(){
            return this.purchases.filter(purchase => purchase.name.toLowerCase().includes(this.search.toLowerCase()));
        },
        //Caculate total price of purchase records
        caculateTotal(){
            return this.purchases.map(purchase => Number(purchase.num) * Number(purchase.sell_price))
        },
        //Caculate total number of purchase records
        total(){
            return this.purchases.reduce((total,curr)=>(total=total+curr.num),0);
        },
        //Calculate total pages of purchase records
        totalPages(){
            return (Math.ceil(this.fixedPurchases.length / this.itemsPerPage))
        }
    }
}
</script>