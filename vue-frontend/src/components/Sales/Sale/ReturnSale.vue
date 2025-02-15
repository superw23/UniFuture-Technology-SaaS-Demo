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

    <!--Sale List-->
    <div class="container">
        <h1 class="test-center">{{$t('Sale.Returned Sales')}}</h1>
        <br>
        <div class="alert alert-success" v-if="isSuccess">
            {{$t('Sale.The sale is returned successfully! Refresh the page to see the result.')}}
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
                        <i class="fa fa-sort-alpha-asc" type="button" aria-hidden="true" @click="sort('customerName')"/>
                        {{$t('Customer Name')}}
                    </th>
                </thead>
                <tbody>
                    <tr v-for = "sale in filteredSales" v-bind:key = "sale.id" class="table-bordered">
                        <td>{{sale.id}}</td>
                        <td>{{sale.name}}</td>
                        <td>{{sale.returnNum}}</td>
                        <td>{{sale.returnPrice}}</td>
                        <td>{{sale.returnTotal}}</td>
                        <td>{{sale.productId}}</td>
                        <td>{{sale.customerName}}</td>
                    </tr>
                </tbody>
            </table>
        </div>
        <h5>{{$t('Sale.Total return sales')}}: {{total}}</h5>
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
import SaleService from '@/services/Sale/SaleService';
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
            sales: [],//store sales per page 
            fixedSales: [],//store all sales
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
        //Get all returned sales records
        async getReturnSales(){
            await SaleService.getReturnSales().then((response)=>{
                this.sales = response.data;
                this.fixedSales = response.data;
            });
        },
        //Get returned sales records within given dates
        getSalesWithinGivenDates(){
            SaleService.getReturnSalesWithinGivenDates(this.startDate,this.endDate).then((response)=>{
                this.sales = response.data;
            });
        },
        //Sort returned sales records of a given field
        sort(field){
            SaleService.sortReturnSales(this.pageNum, this.itemsPerPage, field).then((response)=>{
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
            SaleService.returnPagination(pageNum,this.itemsPerPage).then((response)=>{
                this.sales = response.data;
            });
        },
        //Set the number of records shown in one page
        changeItemPerPage(num){
            this.itemsPerPage = num;
            this.pagination(this.pageNum,this.itemsPerPage);
        },
        //Download the returned sales table in pdf format
        downloadPDF(){
            const columns = [
                    { header: 'Order No.', dataKey: 'id' },
                    { header: 'Customer Name', dataKey: 'customerName' },
                    { header: 'Product ID', dataKey: 'productId' },
                    { header: 'Name', dataKey: 'name' },
                    { header: 'Quantity', dataKey: 'num' },
                    { header: 'Return Price', dataKey: 'returnPrice' },
                    { header: 'Total', dataKey: 'total' }
                ];
            const pdf = new jsPDF();
            //text is placed using x, y coordinates
            pdf.setFontSize(20).text("Returned Sales", 80, 10);
            //use autoTable plugin
            autoTable(pdf, {
                columns,
                body: this.sales,
                margin:{top: 30}
            });
            //show total stock
            pdf.setFontSize(16).text('Total returned quantity: ' + this.total, 80, 20);
            //download pdf
            pdf.save("ReturnedSales.pdf");
        },
        //Download the returned sales table in excel format
        downloadExcel() {
            const filteredReturnSales = this.filteredSales.map(sales => {
                return {
                    OrderNo: sales.id,
                    Name: sales.name,
                    ProductID: sales.productId,
                    Quantity: sales.num,
                    ReturnPrice: sales.returnPrice,
                    Total: sales.total,
                    Customer: sales.customerName,
                    Date: sales.date,
                };
            });

            // Create a new workbook
            const wb = XLSX.utils.book_new();

            // Add a new worksheet to the workbook
            const ws = XLSX.utils.json_to_sheet(filteredReturnSales);

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
            link.download = 'Returned Sales.xlsx';
            document.body.appendChild(link);
            link.click();
            document.body.removeChild(link);
        }
    },
    async created(){
        await this.getReturnSales()
        this.itemsPerPage = this.sales.length
    },
    computed:{
        //Filter returned sales records accoring to the product name
        filteredSales(){
            return this.sales.filter(sale => sale.name.toLowerCase().includes(this.search.toLowerCase()));
        },
        //Calculate total number of returned sales records
        total(){
            return this.sales.reduce((total,curr)=>(total=total+curr.returnNum),0);
        },
        //Calculate total pages of returned sales records
        totalPages(){
            return (Math.ceil(this.fixedSales.length / this.itemsPerPage))
        }
    }
}
</script>

<style>

</style>