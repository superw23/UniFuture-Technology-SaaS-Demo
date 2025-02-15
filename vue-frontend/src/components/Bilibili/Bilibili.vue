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

    <!--Income List-->
    <div class="container">
        <h1 class="test-center">{{$t('Income List')}}
            <router-link :to="{name:'AddProduct'}"><i class="fa fa-cart-plus" style="font-size:42px"></i></router-link>
        </h1>
        <!-- Success Alert will be shown if the product is successfully deleted from the database. -->
        <div class="alert alert-success" v-if="isSuccess">
            {{$t('Product.The product is deleted successfully!')}}
        </div>
        <br>
        <div class="row align-items-center">
            <div class="col"></div>
            <div class="col-2">
                {{$t('Start Date:')}} <input type="date" class="form-control me-2" :placeholder="$t('Choose a start date')" id= "date" v-model="startDate">
            </div>
            <div class="col-2">
                {{$t('End Date:')}} <input type="date" class="form-control" :placeholder="$t('Choose an end date')" id= "date" v-model="endDate">
            </div>
            <div class="col-auto">
            <img src="/static/Others/check.png" width="20" height="20" type="submit" @click="getRecordsWithGivenDates"/>
            </div>
            <div class="col"></div>
        </div>
        <br>
        <div class="table-responsive">
        <table class="table table-stripped">
            <thead>
                <th>
                    <i class="fa fa-sort-alpha-asc" type="button" aria-hidden="true" @click="sort('转入单号')"/>
                    {{$t('Reference No.')}}
                </th>
                <th>
                    <i class="fa fa-sort-alpha-asc" type="button" aria-hidden="true" @click="sort('来源')"/>
                    {{$t('Source')}}
                </th>
                <th>
                    <i class="fa fa-sort-alpha-asc" type="button" aria-hidden="true" @click="sort('贝壳')"/>
                    {{$t('Shells')}}
                </th>
                <th>
                    <i class="fa fa-sort-alpha-asc" type="button" aria-hidden="true" @click="sort('状态')"/>
                    {{$t('Status')}}
                </th>
                <th>
                    <i class="fa fa-sort-alpha-asc" type="button" aria-hidden="true" @click="sort('转入时间')"/>
                    {{$t('Time')}}
                </th>
                <!-- <th>
                    {{$t('Delete')}}
                </th> -->
            </thead>
            <tbody>
                <tr v-for = "record in filteredRecords" v-bind:key = "record.id" class="table-bordered">
                    <td>{{record.转入单号}}</td>
                    <!-- <td>
                        <router-link :to="'/typeDetails/'+ product.type">
                            <span class="badge badge-success rounded-pill" type="button">{{product.type}}</span>
                        </router-link>
                    </td> -->
                    <td>{{record.来源}}</td>
                    <td>{{record.贝壳}}</td>
                    <td>{{record.状态}}</td>
                    <!-- <td>
                        <router-link :to="'/supplierDetails/'+ product.name">
                            <fa icon="address-card" type="button" />
                        </router-link>
                    </td>   -->
                    <td>{{record.转入时间}}</td>
                    <!-- <td> -->
                        <!-- Modal -->
                        <!-- <div class="modal fade" id="onDelete" tabindex="-1" aria-labelledby="onDeleteLabel" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="onDeleteLabel">{{$t('CONFIRM')}}</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <div class="modal-body">{{$t('Are your sure to delete the record?')}}</div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">{{$t('No')}}</button>
                                    <button type="button" class="btn btn-primary" data-bs-dismiss="modal" @click="deleteRecord(this.bilibiliName)">{{$t('Yes')}}</button>
                                </div>
                                </div>
                            </div>
                        </div>
                        <fa icon="trash" type="button" data-bs-toggle="modal" data-bs-target="#onDelete"  @click="getBilibiliName(record.name)"/> -->
                    <!-- </td> -->
                </tr>
            </tbody>
        </table>
        </div>
        <!-- Show the total records quantity -->
        <h5>{{$t('Total records')}}: {{total}}</h5>
        <img src="/static/Others/pdf.png" width="38" height="35" type="button" @click="downloadPDF"/>
        &nbsp
        <img src="/static/Others/xlsx.png" width="35" height="35" type="button" @click="downloadExcel"/>
    </div>
    
    <!--Page navigation bar-->
    <nav class="navbar">
        <div class="container-fluid">
            <h5>{{$t('Total Pages: ')}}{{this.totalPages}}</h5>
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
import BilibiliService from '../../services/Bilibili/BilibiliService'
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
    name: 'Bilibili',
    data(){
        return{
            startDate: '',
            endDate: '',
            itemsPerPage: '',//the number of customer shown in one page
            pageNum: 0,//store current page number
            bilibiliName: '',
            records: [],//store all deleteRecord with pagination
            distinctRecords: [],//store deleteRecord with distinct names
            search:'',//store search content
            isSuccess: false,//true if the product info needs editing, else false
            pdfIconSize: '2em',
            excelIconSize: '2em',
        }
    },
    methods:{
        //Get all products with distinct names from the database
        // async getDistinctRecords(){
        //     await BilibiliService.getDistinctRecords().then((response)=>{
        //         this.distinctRecords = response.data;
        //         this.products = response.data
        //     });
        // },
        async getAllRecords(){
            await BilibiliService.getAllRecords().then((response)=>{
                this.distinctRecords = response.data;
                this.records = response.data
            });
        },
        //Delete a chosen product from the database
        deleteRecord(bilibiliName) {
            BilibiliService.deleteRecord(bilibiliName).then((response)=>{
                this.isSuccess = true;
                this.getDistinctRecords()
            });
        },
        getBilibiliName(name){
            this.bilibiliName = name
        },
        //按转入时间/转入单号/贝壳/来源/状态排序
        sort(field){
            BilibiliService.sortAscending(this.pageNum, this.itemsPerPage, field).then((response)=>{
                this.distinctRecords = response.data;
            });
        },
        //分页
        pagination(pageNum){
            this.pageNum = pageNum;
            if(this.pageNum == -1) {
                this.pageNum = 0;
                return;
            }
            BilibiliService.pagination(pageNum,this.itemsPerPage).then((response)=>{
                this.distinctRecords = response.data;
            });
        },
        //展示特定时间范围内的所有数据
        getRecordsWithGivenDates(){
            BilibiliService.getRecordsWithGivenDates(this.startDate,this.endDate).then((response)=>{
                this.distinctRecords = response.data;
            });
        },
        //调整每页显示记录的数量
        changeItemPerPage(num){
            this.itemsPerPage = num;
            this.pagination(this.pageNum,this.itemsPerPage);
        },
        //Jump to the product page for a given type
        jumpToTypeDetailsPage(){
            this.$router.push({path:'/typeDetails'})
        },
        //下载pdf
        downloadPDF(){
            const columns = [
                    { header: '转入时间', dataKey: '转入时间' },
                    { header: '转入单号', dataKey: '转入单号' },
                    { header: '转入贝壳', dataKey: '转入贝壳' },
                    { header: '来源', dataKey: '来源' },
                    { header: '状态', dataKey: '状态' }
                ];
            const pdf = new jsPDF();
            //text is placed using x, y coordinates
            pdf.setFontSize(20).text("Product List", 80, 10);
            //use autoTable plugin
            autoTable(pdf, {
                columns,
                body: this.distinctRecords,
                margin:{top: 30}
            });
            //show total stock
            pdf.setFontSize(16).text('Total records: ' + this.total, 80, 20);
            //download pdf
            pdf.save("Bilibili_Records.pdf");
        },
        //下载excel表格
        downloadExcel() {
          const filteredRecordsWithoutUseless = this.filteredRecords.map(records => {
            return {
                转入时间: records.转入时间,
                转入单号: records.转入单号,
                贝壳: records.贝壳,
                来源: records.来源,
                状态: records.状态
            };
          });
          // Create a new workbook
          const wb = XLSX.utils.book_new();
          // Add a new worksheet to the workbook
          const ws = XLSX.utils.json_to_sheet(filteredRecordsWithoutUseless);
          // Add the worksheet to the workbook
          XLSX.utils.book_append_sheet(wb, ws, 'Records');
          // Write the workbook to a binary string
          const wbout = XLSX.write(wb, { bookType: 'xlsx', type: 'binary' });
          // Convert the binary string to a Blob object
          const blob = new Blob([s2ab(wbout)], { type: 'application/octet-stream' });
          // Create a temporary link to the file and trigger a download
          const url = window.URL.createObjectURL(blob);
          const link = document.createElement('a');
          link.href = url;
          link.download = 'Bilibili_Records.xlsx';
          document.body.appendChild(link);
          link.click();
          document.body.removeChild(link);
        }
    },
    async created(){
        //Get all customers data with distinct names when the page is being loaded
        // await this.getDistinctRecords()
        await this.getAllRecords()
        this.itemsPerPage = this.distinctRecords.length
    },
    computed:{
        //Get required records after searching
        filteredRecords(){
            return this.distinctRecords.filter(record => record.来源.toLowerCase().includes(this.search.toLowerCase()));
        },
        //Get total number of records
        total(){
            return this.distinctRecords.reduce((total,curr)=>(total+=1),0);
        },
        totalPages(){
            return (Math.ceil(this.records.length / this.itemsPerPage))
        }
    }
}
</script>
