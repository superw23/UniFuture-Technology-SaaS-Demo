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

    <!--Purchase List-->
    <div class="container">
        <h1 class="test-center">{{$t('Purchase.Returned Purchases')}}</h1>
        <div class="alert alert-success" v-if="isSuccess">
            {{$t('Purchase.The purchase is returned successfully! Refresh the page to see the result.')}}
        </div>
        <br>
        <div class="row align-items-center">
            <div class="col"></div>
            <div class="col-2">
                {{$t('Start Date:')}} <input  type="date" class="form-control me-2" :placeholder="$t('Choose a start date')" id= "date" v-model="startDate">
            </div>
            <div class="col-2">
                {{$t('End Date:')}} <input type="date" class="form-control" :placeholder="$t('Choose an end date')" id= "date" v-model="endDate">
            </div>
            <div class="col-auto">
            <img src="/static/check.png" width="20" height="20" type="submit" @click="getPurchasesWithinGivenDates"/>
            </div>
            <div class="col"></div>
        </div>
        <br>
        <div class="table-responsive">
            <table class="table table-stripped">
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
                        <i class="fa fa-sort-alpha-asc" type="button" aria-hidden="true" @click="sort('returnPrice')"/>
                        {{$t('Return Price')}}
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
                </thead>
                <tbody>
                    <tr v-for = "purchase in filteredPurchases" v-bind:key = "purchase.id" class="table-bordered">
                        <td>{{purchase.id}}</td>
                        <td>{{purchase.name}}</td>
                        <td><span class="badge badge-success rounded-pill">{{purchase.type}}</span></td>
                        <td>{{purchase.returnNum}}</td>
                        <td>{{purchase.returnPrice}}</td>
                        <td>{{purchase.returnTotal}}</td>
                        <td>{{purchase.productId}}</td>
                        <td>{{purchase.supplierName}}</td>
                        <td>{{purchase.remarks}}</td>
                    </tr>
                </tbody>
            </table>
        </div>
        <h5>{{$t('Purchase.Total return purchases')}}: {{total}}</h5>
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
import PurchaseService from '@/services/Purchase/PurchaseService';
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
            isSuccess: false,
            editing: false,
            isEdited: false,
            pdfIconSize: '2em',
            excelIconSize: '2em',
        }
    },
    methods:{
        //Get all returned purchase records
        async getReturnPurchases(){
            await PurchaseService.getReturnPurchases().then((response)=>{
                this.purchases = response.data;
                this.fixedPurchases = response.data
            });
        },
        //Sort returned purchase records of a given field
        sort(field){
            PurchaseService.sortReturnPurchases(this.pageNum, this.itemsPerPage, field).then((response)=>{
                this.purchases = response.data;
            });
        },
        //Get returned purchase records within given dates
        getPurchasesWithinGivenDates(){
            PurchaseService.getReturnPurchasesWithinGivenDates(this.startDate,this.endDate).then((response)=>{
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
            console.log(this.pageNum) ;
            PurchaseService.returnPagination(pageNum,this.itemsPerPage).then((response)=>{
                this.purchases = response.data;
            });
        },
        //Set the number of records shown in one page
        changeItemPerPage(num){
            this.itemsPerPage = num;
            this.pagination(this.pageNum,this.itemsPerPage);
        },
        //Download the returned purchase table in pdf format
        downloadPDF(){
            const columns = [
                    { header: 'Order No.', dataKey: 'id' },
                    { header: 'Product ID', dataKey: 'productId' },
                    { header: 'Name', dataKey: 'name' },
                    { header: 'Type', dataKey: 'type' },
                    { header: 'Quantity', dataKey: 'num' },
                    { header: 'Return Price', dataKey: 'returnPrice' },
                    { header: 'Total', dataKey: 'total' },
                    { header: 'Description', dataKey: 'remarks' }
                ];
            const pdf = new jsPDF();
            //text is placed using x, y coordinates
            pdf.setFontSize(20).text("Returned Purchases", 80, 10);
            //use autoTable plugin
            autoTable(pdf, {
                columns,
                body: this.purchases,
                margin:{top: 30}
            });
            //show total stock
            pdf.setFontSize(16).text('Total returned quantity: ' + this.total, 80, 20);
            //download pdf
            pdf.save("ReturnedPurchases.pdf");
        },
        //Download the returned purchase table in excel format
        downloadExcel() {
             const filteredReturnedPurchases = this.filteredPurchases.map(purchases => {
                return {
                    PurchaseID: purchases.id,
                    Name: purchases.name,
                    Type: purchases.type,
                    Quantity: purchases.num,
                    ReturnPrice: purchases.returnPrice,
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
            const ws = XLSX.utils.json_to_sheet(filteredReturnedPurchases);

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
            link.download = 'Returned Purchases.xlsx';
            document.body.appendChild(link);
            link.click();
            document.body.removeChild(link);
        }
    },
    async created(){
        await this.getReturnPurchases()
        this.itemsPerPage = this.purchases.length
    },
    computed:{
        //Filter returned purchase records accoring to the product name
        filteredPurchases(){
            return this.purchases.filter(purchase => purchase.name.toLowerCase().includes(this.search.toLowerCase()));
        },
        //Calculate total number of returned purchase records
        total(){
            return this.purchases.reduce((total,curr)=>(total=total+curr.returnNum),0);
        },
        //Calculate total pages of returned purchase records
        totalPages(){
            return (Math.ceil(this.fixedPurchases.length / this.itemsPerPage))
        }
    }
}
</script>