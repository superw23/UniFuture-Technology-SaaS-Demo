//Adapted By GRPTeam202205 
<template>
    <!-- Home page -->
    <div class="container-fluid mt-3" >
        <!-- Figures to show the statistics of the management -->
        <div class="row">
            <!-- Show total revenue -->
            <div class="col-lg-3 col-sm-6">
                <div class="card gradient-4">
                    <div class="card-body">
                        <h3 class="card-title text-white">{{$t('Total Revenue')}}</h3>
                        <div class="d-inline-block">
                            <h2 class="text-white">{{totalPrice}}</h2>
                            <p class="text-white mb-0">{{$t('Jan - April 2023')}}</p>
                        </div>
                        <span class="float-right display-5 opacity-5"><i class="fa fa-heart"></i></span>
                    </div>
                </div>
            </div>
            <!-- Show total net profit -->
            <div class="col-lg-3 col-sm-6">
                <div class="card gradient-2">
                    <div class="card-body">
                        <h3 class="card-title text-white">{{$t('Net Profit')}}</h3>
                        <div class="d-inline-block">
                            <h2 class="text-white">$ {{netProfit}}</h2>
                            <p class="text-white mb-0">{{$t('Jan - April 2023')}}</p>
                        </div>
                        <span class="float-right display-5 opacity-5"><i class="fa fa-money"></i></span>
                    </div>
                </div>
            </div>
            <!-- Show total number of customers -->
            <div class="col-lg-3 col-sm-6">
                <div class="card gradient-3">
                    <div class="card-body">
                        <h3 class="card-title text-white">{{$t('Total Influencers')}}</h3>
                        <div class="d-inline-block">
                            <h2 class="text-white">{{totalCustomers}}</h2>
                            <p class="text-white mb-0">{{$t('Jan - April 2023')}}</p>
                        </div>
                        <span class="float-right display-5 opacity-5"><i class="fa fa-users"></i></span>
                    </div>
                </div>
            </div>
            <!-- Show total products sold -->
            <div class="col-lg-3 col-sm-6">
                <div class="card gradient-1">
                    <div class="card-body">
                        <h3 class="card-title text-white">{{$t('Products Sold')}}</h3>
                        <div class="d-inline-block">
                            <h2 class="text-white">{{total}}</h2>
                            <p class="text-white mb-0">{{$t('Jan - April 2023')}}</p>
                        </div>
                        <span class="float-right display-5 opacity-5"><i class="fa fa-shopping-cart"></i></span>
                    </div>
                </div>
            </div>
        </div>
<!--        <div>-->
<!--            <h1>File Upload</h1>-->
<!--            <input type="file" ref="fileInput" accept=".xlsx, .xls">-->
<!--            <button @click="uploadFile">Upload</button>-->
<!--        </div>-->
<!--        <div>-->
<!--            <button>提现</button>-->
<!--        </div>-->
        <!-- Show the number of products in the inventory using a bar chart -->
        <div class="row">
            <div class="col-lg-12">
                <div class="row">
                    <div class="col-12">
                        <div class="card">
                            <div class="card-body pb-0 d-flex justify-content-between">
                                <div class="text-center">
                                </div>
                                <div>
                                    <ul>
                                        <li class="d-inline-block mr-3"><a class="text-dark" type="button" @click="createOverallChart">{{$t('Overall')}}</a></li>
                                        <li class="d-inline-block mr-3"><a class="text-dark" type="button" @click="createWeekChart">{{$t('Week')}}</a></li>
                                        <li class="d-inline-block mr-3"><a class="text-dark" type="button" @click="createMonthChart">{{$t('Month')}}</a></li>
                                        <li class="d-inline-block"><a class="text-dark" type="button" @click="createYearChart">{{$t('Year')}}</a></li>
                                    </ul>
                                </div>
                            </div>
                            <div class="chart-wrapper">
                                <canvas width="50;" height="306" ref="myChart" v-show="this.$i18n.locale=='en'" responsive=true></canvas>
                                <canvas width="50;" height="306" ref="myChartCh" v-show="this.$i18n.locale=='ch'" responsive=true></canvas>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Show the number of ordered products using a pie chart -->
        <div class="row">
            <div class="col-lg-6 col-md-12">
                <div class="card">
                    <div class="card-body" >
                        <h5 class="text-muted">{{$t('Order Summary')}}</h5>
                        <div class="chart-wrapper" > 
                        <canvas ref="doughnutChart" v-show="this.$i18n.locale=='en'" responsive=true></canvas>
                        <canvas ref="doughnutChartCh" v-show="this.$i18n.locale=='ch'" responsive=true></canvas>
                        </div>
                    </div>
                </div>                
            </div>    
            <div class="col-lg-3 col-md-6">
                <div class="card card-widget">
                    <div class="card-body">
                        <h5 class="text-muted">{{$t('Order Overview')}}</h5>
                        <h3 class="text-muted">{{$t('Top 4 popular products')}}</h3>
                        <div class="mt-4">
                            <h6 class="m-t-10 text-muted">{{productWithHighestPopularity}}</h6>
                            <div class="progress mb-3" style="height: 10px">
                                <div class="progress-bar bg-primary" :style="mostPopularProductStyle" role="progressbar">
                                </div>
                            </div>
                        </div>
                        <div class="mt-4">
                            <h6 class="m-t-10 text-muted">{{productWithSecondPopularity}}</h6>
                            <div class="progress mb-3" style="height: 10px">
                                <div class="progress-bar bg-success" :style="secondPopularProductStyle" role="progressbar">
                                </div>
                            </div>
                        </div>
                        <div class="mt-4">
                            <h6 class="m-t-10 text-muted">{{productWithThirdPopularity}}</h6>
                            <div class="progress mb-3" style="height: 10px">
                                <div class="progress-bar bg-warning" :style="thirdPopularProductStyle" role="progressbar">
                                </div>
                            </div>
                        </div>
                        <div class="mt-4">
                            <h6 class="m-t-10 text-muted">{{productWithFourthPopularity}}</h6>
                            <div class="progress mb-3" style="height: 10px">
                                <div class="progress-bar bg-warning" :style="fourthPopularProductStyle" role="progressbar">
                                </div>
                            </div>
                        </div>
                        <h3 class="text-muted">{{$t('Best selling product')}}</h3>
                        <h3 class="text-muted">{{productWithHighestPopularity}}</h3>
                        <span></span>
                    </div>
                </div>             
            </div>
            <div class="col-lg-3 col-md-6">
                <div class="card">
                    <div class="card-body px-0">
                        <h4 class="text-muted">{{$t('Todo')}}</h4>
                        <div class="todo-list">
                            <div class="tdl-holder">
                                <div class="tdl-content" style="position:relative;height:400px;overflow:auto" >
                                    <ul id="todo_list" v-for="item in uncheckedItems" :key="item.id">
                                        <li><label><input type="checkbox" @click="markCompleted(item.content)" required/><i></i><span>{{item.content}}</span><a class="ti-trash" type="button" @click="deleteToDoItem(item.content)"></a></label></li> 
                                    </ul>
                                    <ul id="todo_list" v-for="item in checkedItems" :key="item.id">
                                        <li><label><input type="checkbox" checked @click="markUncompleted(item.content)" required/><i></i><span>{{item.content}}</span><a class="ti-trash" type="button" @click="deleteToDoItem(item.content)"></a></label></li> 
                                    </ul>
                                </div>
                                <div class="px-4">
                                    <input type="text" class="tdl-new form-control" v-model="this.todoContent" :placeholder="$t('Hit Enter to add new items')" @keyup.enter="addToDo">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Show recent six sales -->
        <div class="row">
            <div class="col-lg-12">
                <div class="card">
                    <div class="card-body">
                        <div class="active-member">
                            <h4 class="mb-1 " type="">{{$t('Recent Sales')}}</h4>
                            <div class="table-responsive">
                                <table class="table table-xs mb-0">
                                    <thead>
                                        <tr>
                                            <th>{{$t('Customer.Customer')}}</th>
                                            <th>{{$t('Product.Product')}}</th>
                                            <th>{{$t('Quantity')}}</th>
                                            <th>{{$t('Total')}}</th>
                                            <th>{{$t('Supplier.Supplier')}}</th>
                                            <th>{{$t('Date')}}</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr v-for = "sale in recentSales" v-bind:key = "sale.id" class="table-bordered">
                                            <td>{{sale.customerName}}</td>
                                            <td>{{sale.name}}</td>
                                            <td>{{sale.num}}</td>
                                            <td>{{sale.total}}</td>
                                            <td>{{sale.supplierName}}</td>
                                            <td>{{sale.date}}</td>                                      
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>                        
            </div>
        </div>

        <!-- Show important managers -->
        <div class="row">
            <div class="col-lg-3 col-sm-6">
                <div class="card">
                    <div class="card-body">
                        <div class="text-center">
                            <img src="../../../public/frontend/images/users/8.png" class="rounded-circle" alt="">
                            <h5 class="mt-3 mb-1">{{$t('Sally')}}</h5>
                            <p class="m-0">{{$t('Manager')}}</p>
                            <a href="javascript:void()" class="btn btn-sm btn-warning" @click="groupChat">{{$t('Send Message')}}</a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-lg-3 col-sm-6">
                <div class="card">
                    <div class="card-body">
                        <div class="text-center">
                            <img src="../../../public/frontend/images/users/5.png" class="rounded-circle" alt="">
                            <h5 class="mt-3 mb-1">{{$t('Tom')}}</h5>
                            <p class="m-0">{{$t('Warehouse Manager')}}</p>
                            <a href="javascript:void()" class="btn btn-sm btn-warning" @click="groupChat">{{$t('Send Message')}}</a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-lg-3 col-sm-6">
                <div class="card">
                    <div class="card-body">
                        <div class="text-center">
                            <img src="../../../public/frontend/images/users/7.png" class="rounded-circle" alt="">
                            <h5 class="mt-3 mb-1">{{$t('Ella')}}</h5>
                            <p class="m-0">{{$t('Purchase Manager')}}</p>
                            <a href="javascript:void()" class="btn btn-sm btn-warning"  @click="groupChat">{{$t('Send Message')}}</a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-lg-3 col-sm-6">
                <div class="card">
                    <div class="card-body">
                        <div class="text-center">
                            <img src="../../../public/frontend/images/users/1.png" class="rounded-circle" alt="">
                            <h5 class="mt-3 mb-1">{{$t('Beta')}}</h5>
                            <p class="m-0">{{$t('Sales Personnel')}}</p>
                            <a href="javascript:void()" class="btn btn-sm btn-warning"  @click="groupChat">{{$t('Send Message')}}</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Show friends and followers -->
        <div class="row">
            <div class="col-lg-3 col-sm-6">
                <div class="card">
                    <div class="social-graph-wrapper widget-facebook">
                        <span class="s-icon"><i class="fa fa-solid fa-shop"></i></span>
                    </div>
                    <div class="row">
                        <div class="col-6 border-right">
                            <div class="pt-3 pb-3 pl-0 pr-0 text-center">
                                <h4 class="m-1">89k</h4>
                                <p class="m-0">{{$t('Friends')}}</p>
                            </div>
                        </div>
                        <div class="col-6">
                            <div class="pt-3 pb-3 pl-0 pr-0 text-center">
                                <h4 class="m-1">119k</h4>
                                <p class="m-0">{{$t('Followers')}}</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-lg-3 col-sm-6">
                <div class="card">
                    <div class="social-graph-wrapper widget-linkedin">
                        <span class="s-icon"><i class="fa fa-solid fa-warehouse"></i></span>
                    </div>
                    <div class="row">
                        <div class="col-6 border-right">
                            <div class="pt-3 pb-3 pl-0 pr-0 text-center">
                                <h4 class="m-1">89k</h4>
                                <p class="m-0">{{$t('Friends')}}</p>
                            </div>
                        </div>
                        <div class="col-6">
                            <div class="pt-3 pb-3 pl-0 pr-0 text-center">
                                <h4 class="m-1">119k</h4>
                                <p class="m-0">{{$t('Followers')}}</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-lg-3 col-sm-6">
                <div class="card">
                    <div class="social-graph-wrapper widget-googleplus">
                        <span class="s-icon"><i class="fa fa-brands fa-sellsy"></i></span>
                    </div>
                    <div class="row">
                        <div class="col-6 border-right">
                            <div class="pt-3 pb-3 pl-0 pr-0 text-center">
                                <h4 class="m-1">89k</h4>
                                <p class="m-0">{{$t('Friends')}}</p>
                            </div>
                        </div>
                        <div class="col-6">
                            <div class="pt-3 pb-3 pl-0 pr-0 text-center">
                                <h4 class="m-1">119k</h4>
                                <p class="m-0">{{$t('Followers')}}</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-lg-3 col-sm-6">
                <div class="card">
                    <div class="social-graph-wrapper widget-twitter">
                        <span class="s-icon"><i class="fa fa-solid fa-globe"></i></span>
                    </div>
                    <div class="row">
                        <div class="col-6 border-right">
                            <div class="pt-3 pb-3 pl-0 pr-0 text-center">
                                <h4 class="m-1">89k</h4>
                                <p class="m-0">{{$t('Friends')}}</p>
                            </div>
                        </div>
                        <div class="col-6">
                            <div class="pt-3 pb-3 pl-0 pr-0 text-center">
                                <h4 class="m-1">119k</h4>
                                <p class="m-0">{{$t('Followers')}}</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import SaleService from '../../services/Sale/SaleService'
import RevenueService from '../../services/Sale/RevenueService'
import PopularSaleService from '../../services/Sale/PopularSaleService'
import CustomerService from '../../services/Customer/CustomerService'
import BilibiliService from '../../services/Bilibili/BilibiliService'
import ToDoService from '../../services/TodoList/ToDoService'
import Chart from 'chart.js/auto'
import axios from 'axios'
export default {
    name: 'Home',
    data(){
        return{
            customers: [],
            products: [],
            sales: [],
            labels: [],
            chLabels: [
                    '哔哩哔哩',
                    '抖音',
                    '原神',
                    '上海广播电视台'
                ],
            enLabels: [
                    'Bilibili',
                    'Tik Tok',
                    'Genshin',
                    'SMG'
                ],
            label: '',
            chLabel: '数量',
            enLabel: 'Quantity',
            barLabel: '',
            enBarLabel: 'Income',
            chBarLabel: '收入',
            language: '',
            food: '',
            clothes: '',
            appliances: '',
            drink: '',
            bilibiliRecords: '',
            tiktokRecords: '',
            genshinRecords: '',
            smgRecords: '',
            bilibiliRevenue: '',
            tiktokRevenue: '',
            genshinRevenue: '',
            smgRevenue: '',
            mostPopularType: '',
            secondPopularType: '',
            thirdPopularType: '',
            otherType: '',
            mostPopularTypeNum: '',
            secondPopularTypeNum: '',
            thirdPopularTypeNum: '',
            otherTypeNum: '',
            daySale:'',
            periodSale:'',
            recentSales: '',
            productWithHighestPopularity: '',
            productWithSecondPopularity: '',
            productWithThirdPopularity: '',
            productWithFourthPopularity:'',
            firstProductSaleNum: 0,
            secondProductSaleNum: 0,
            thirdProductSaleNum: 0,
            fourthProductSaleNum: 0,
            todoHTML: '',//todo-list html
            todoContent: '',//new item to be added to the todo-list
            todoItems: [],//todo items
            chart: Chart,
            pieChart: Chart,
            userName: ''
        }
    },
    methods:{
        uploadFile() {
            const file = this.$refs.fileInput.files[0];

            if (file) {
                const reader = new FileReader();
                reader.onload = () => {
                    const fileDataUrl = reader.result;

                    axios.post("/upload", { file: fileDataUrl }).then(response => {
                        console.log(response.data);
                    }).catch(error => {
                        console.error(error);
                    })
                };

                reader.readAsDataURL(file);
            }
        },
        //获取b站总收益
        getRevenueOfBilibili(){
            RevenueService.getRevenueOfBilibili().then((response)=>{
                this.bilibiliRevenue = response.data;
                console.log(this.bilibiliRevenue)
            });
        },
        //Add to-do item in the todo-list
        async addToDo(){
            if(this.todoContent!=''){
                await axios
                    .post(`addTodoItem`, {
                        content: this.todoContent,
                        completed: 'false'
                    })
                    .then((response) => {
                    });
            }
            this.getToDoItems()
            this.todoContent = ''
        },
        //Get all to-do items 
        getToDoItems(){
            ToDoService.getToDoItems().then((response)=>{
                this.todoItems = response.data;
            });
        },
        //Mark the todo item as completed
        markCompleted(content){
            ToDoService.markCompleted(content);
        },
        //Mark the todo item as uncompleted
        markUncompleted(content){
            ToDoService.markUncompleted(content);
        },
        //Delete a given todo item 
        deleteToDoItem(content){
            ToDoService.deleteItemByContent(content);
            this.getToDoItems()
        },
        //Get all sales from the database
        getSales(){
            SaleService.getSales().then((response)=>{
                this.sales = response.data;
            });
        },
        //Get all customers from the database
        getCustomers(){
            CustomerService.getCustomers().then((response)=>{
                this.customers = response.data;
            });
        },
        //Get all products from the database
        getProducts(){
            BilibiliService.getAllInfo().then((response)=>{
                this.products = response.data;
            });
        },
        //Get the number of products of the most popular type
        getNumOfMostPopularType(){
            PopularSaleService.getNumOfMostPopularType(this.mostPopularType).then((response)=>{
                this.mostPopularTypeNum = response.data;
            });
        },
        //Get the number of products of the second popular type
        getNumOfSecondPopularType(){
            PopularSaleService.getNumOfSecondPopularType(this.secondPopularType).then((response)=>{
                this.secondPopularTypeNum = response.data;
            });
        },
        //Get the number of products of the third popular type
        getNumOfThirdPopularType(){
            PopularSaleService.getNumOfThirdPopularType(this.thirdPopularType).then((response)=>{
                this.thirdPopularTypeNum = response.data;
            });
        },
        //Get the number of remaining products of the remaining types 
        async getNumOfOtherTypes(){
            await PopularSaleService.getNumOfOtherTypes(this.mostPopularType, this.secondPopularType, this.thirdPopularType).then((response)=>{
                this.otherTypeNum = response.data;
                this.createPieChart();
            });
        },
        //Get the total price of most Popular Type Revenue
        getRevenueOfMostPopularType(){
            RevenueService.getTotalOfMostPopularTypeRevenue().then((response)=>{
                this.mostPopularTypeRevenue = response.data;
            });
        },
        //Get the total price of Second Popular Type
        getRevenueOfSecondPopularType(){
            RevenueService.getTotalOfSecondPopularTypeRevenue().then((response)=>{
                this.secondPopularTypeRevenue = response.data;
            });
        },
        //Get the total price of Third Popular Type
        getRevenueOfThirdPopularType(){
            RevenueService.getTotalOfThirdPopularType().then((response)=>{
                this.thirdPopularTypeRevenue = response.data;
            });
        },
        //Get the total price of others revenue
        getRevenueOfOthersRevenue(){
            this.getRevenueOfMostPopularType(),
            this.getRevenueOfSecondPopularType(),
            this.getRevenueOfThirdPopularType(),
            RevenueService.getTotalOfOthersRevenue().then((response)=>{
              this.othersRevenue = response.data; 
              this.createChart();
            });
        },
        //Get total revenue of a given date
        async getOneDaySaleTotal(date){
            await RevenueService.getOneDaySaleTotal(date).then((response)=>{
              this.daySale = response.data;
            });
            return this.daySale;
        },
        //Get total revenue within given dates
        async getPeriodSaleTotal(startDate,endDate){
            await RevenueService.getPeriodSaleTotal(startDate,endDate).then((response)=>{
                this.periodSale = response.data;
            });
            return this.periodSale;
        },
        //4个平台收益的柱状图
        createChart(){
            var chartID = this.$refs.myChart;
            if(this.$i18n.locale=='ch'){
                chartID = this.$refs.myChartCh
                this.label = this.chBarLabel;
                this.labels = this.chLabels;
            }
            else{
              this.label = this.enBarLabel;
              this.labels = [
                'Bilibili',
                'Tik Tok',
                'Genshin',
                'SMG'
              ];
            }
            this.chart = new Chart(
                chartID,
                {
                    type: 'bar',
                    locale: this.$i18n.locale,
                    data: {
                        labels: this.labels,
                        datasets: [
                            {
                                label: this.label,
                                data: [this.bilibiliRevenue, this.tiktokRevenue, this.genshinRevenue, this.smgRevenue],
                                backgroundColor: 'rgb(153, 102, 255, 0.2)',
                                borderColor: 'rgb(153, 102, 255, 1)'
                            }
                        ]
                    },
                    responsiveOptions:true
                }
            );
        },
        //Create a bar chart that shows the overall revenue according to product types
        createOverallChart(){
            this.chart.destroy()
            var chartID = this.$refs.myChart;
            if(this.$i18n.locale=='ch'){
              chartID = this.$refs.myChartCh
              this.barLabel = this.chBarLabel;
              this.labels = this.chLabels;
            }
            else{
              this.barLabel = this.enBarLabel;
              this.labels = [
                    'Bilibili',
                    'Tik Tok',
                    'Genshin',
                    'SMG'
                ];
            }
            this.chart = new Chart(
                chartID,
                {
                type: 'bar',
                locale: this.$i18n.locale,
                data: {
                    labels: this.labels,
                    datasets: [
                    {
                        label: this.barLabel,
                        data: [this.mostPopularTypeRevenue, this.secondPopularTypeRevenue, this.thirdPopularTypeRevenue, this.othersRevenue],
                        backgroundColor: 'rgb(153, 102, 255, 0.2)',
                        borderColor: 'rgb(153, 102, 255, 1)'
                    }
                    ]
                },
                responsiveOptions:true
                }
            );
        },
        //Create a bar chart that shows the weekly revenue according to product types
        async createWeekChart(){
            this.chart.destroy()
            var date = new Date();
            function getDate(date,i){
              let oneDay = 24 * 60 * 60 * 1000;
              var now = new Date(date-i*oneDay);
              var nowDate = now.getDate();
              var mon = now.getMonth()+1;
              var year = now.getFullYear();
              var s = year+"-"+(mon<10?('0'+mon):mon)+"-"+(nowDate<10?('0'+nowDate):nowDate);
              return s;
            }
            var chartID = this.$refs.myChart;
            if(this.$i18n.locale=='ch'){
              chartID = this.$refs.myChartCh
              this.barLabel = this.chBarLabel;
              this.labels = this.chLabels;
            }
            else{
              this.barLabel = this.enBarLabel;
              this.labels = this.enLabels;
            }
            this.chart = new Chart(
                chartID,
                {
                  type: 'bar',
                  locale: this.$i18n.locale,
                  data: {
                    labels: [getDate(date,6),getDate(date,5),getDate(date,4),getDate(date,3),getDate(date,2),getDate(date,1),getDate(date,0)],
                    datasets: [
                      {
                        label: this.barLabel,
                        data: [await this.getOneDaySaleTotal(getDate(date,6)),
                          await this.getOneDaySaleTotal(getDate(date,5)),
                          await this.getOneDaySaleTotal(getDate(date,4)),
                          await this.getOneDaySaleTotal(getDate(date,3)),
                          await this.getOneDaySaleTotal(getDate(date,2)),
                          await this.getOneDaySaleTotal(getDate(date,1)),
                          await this.getOneDaySaleTotal(getDate(date,0))],
                        backgroundColor: 'rgb(153, 102, 255, 0.2)',
                        borderColor: 'rgb(153, 102, 255, 1)'
                      }
                    ]
                  },
                  responsiveOptions:true
                }
            );
        },
        //Create a bar chart that shows the monthly revenue according to product types
        async createMonthChart(){
          this.chart.destroy()
          var chartID = this.$refs.myChart;
          var date = new Date();
          function getBeforeMonth(date, i) {
            var now = new Date(date);
            var ds = new Date(now.setMonth(now.getMonth() - i));
            var year = ds.getFullYear();
            var mon = ds.getMonth() + 1;
            var s = year+"-"+(mon<10?('0'+mon):mon);
            return s;
          }
          if(this.$i18n.locale=='ch'){
            chartID = this.$refs.myChartCh
            this.barLabel = this.chBarLabel;
            this.labels = this.chLabels;
          }
          else{
            this.barLabel = this.enBarLabel;
            this.labels = this.enLabels;
          }
          this.chart = new Chart(
              chartID,
              {
                type: 'bar',
                locale: this.$i18n.locale,
                data: {
                  labels: [getBeforeMonth(date, 5), getBeforeMonth(date, 4), getBeforeMonth(date, 3), getBeforeMonth(date, 2), getBeforeMonth(date, 1), getBeforeMonth(date, 0)],
                  datasets: [
                    {
                      label: this.barLabel,
                      data: [await (this.getPeriodSaleTotal(getBeforeMonth(date, 5)+'-01',getBeforeMonth(date, 4)+'-01')-this.getOneDaySaleTotal(getBeforeMonth(date, 4)+'-01')),
                        await (this.getPeriodSaleTotal(getBeforeMonth(date, 4)+'-01',getBeforeMonth(date, 3)+'-01')-this.getOneDaySaleTotal(getBeforeMonth(date, 3)+'-01')),
                        await (this.getPeriodSaleTotal(getBeforeMonth(date, 3)+'-01',getBeforeMonth(date, 2)+'-01')-this.getOneDaySaleTotal(getBeforeMonth(date, 2)+'-01')),
                        await (this.getPeriodSaleTotal(getBeforeMonth(date, 2)+'-01',getBeforeMonth(date, 1)+'-01')-this.getOneDaySaleTotal(getBeforeMonth(date, 1)+'-01')),
                        await (this.getPeriodSaleTotal(getBeforeMonth(date, 1)+'-01',getBeforeMonth(date, 0)+'-01')-this.getOneDaySaleTotal(getBeforeMonth(date, 0)+'-01')),
                        await this.getPeriodSaleTotal(getBeforeMonth(date, 0)+'-01',date)],
                      backgroundColor: 'rgb(153, 102, 255, 0.2)',
                      borderColor: 'rgb(153, 102, 255, 1)'
                    }
                  ]
                },
                responsiveOptions:true
              }
          );
        },
        //Create a bar chart that shows the yearly revenue according to product types
        async createYearChart(){
          this.chart.destroy()
          var chartID = this.$refs.myChart;
          var date = new Date();
          function getBeforeYear(date, i) {
            var now = new Date(date);
            var ds = new Date(now.setFullYear(now.getFullYear() - i));
            var year = ds.getFullYear();
            var s = year;
            return s;
          }
          if(this.$i18n.locale=='ch'){
            chartID = this.$refs.myChartCh
            this.barLabel = this.chBarLabel;
            this.labels = this.chLabels;
          }
          else{
            this.barLabel = this.enBarLabel;
            this.labels = this.enLabels;
          }
          this.chart = new Chart(
              chartID,
              {
                type: 'bar',
                locale: this.$i18n.locale,
                data: {
                  labels: [getBeforeYear(date, 3),getBeforeYear(date, 2),getBeforeYear(date, 1), getBeforeYear(date, 0)],
                  datasets: [
                    {
                      label: this.barLabel,
                      data: [await (this.getPeriodSaleTotal(getBeforeYear(date, 3)+'-01'+'-01',getBeforeYear(date,2)+'-01'+'-01')-this.getOneDaySaleTotal(getBeforeYear(date,2)+'-01'+'-01')),
                        await (this.getPeriodSaleTotal(getBeforeYear(date, 2)+'-01'+'-01',getBeforeYear(date,1)+'-01'+'-01')-this.getOneDaySaleTotal(getBeforeYear(date,1)+'-01'+'-01')),
                        await (this.getPeriodSaleTotal(getBeforeYear(date, 1)+'-01'+'-01',getBeforeYear(date,0)+'-01'+'-01')-this.getOneDaySaleTotal(getBeforeYear(date,0)+'-01'+'-01')),
                        await this.getPeriodSaleTotal(getBeforeYear(date, 3)+'-01'+'-01',date)],
                      backgroundColor: 'rgb(153, 102, 255, 0.2)',
                      borderColor: 'rgb(153, 102, 255, 1)'
                    }
                  ]
                },
                responsiveOptions:true
              }
          );
        },
        //Create a pie chart to show the number of ordered products according to product types
        createPieChart(){
            var chartID = this.$refs.doughnutChart;
            if(this.$i18n.locale=='ch'){
                chartID = this.$refs.doughnutChartCh
                this.label = this.chLabel;
                this.labels = this.chLabels;
            }  
            else{
                this.label = this.enLabel;
                this.labels = [
                    'Bilibili',
                    'Tik Tok',
                    'Genshin',
                    'SMG'
                ];
            } 
            const doughnutData = {
                labels: this.labels,
                datasets: [{
                    label: this.label,
                    data: [this.bilibiliRevenue, this.tiktokRevenue, this.genshinRevenue, this.smgRevenue],
                    backgroundColor: [
                    'rgba(255, 99, 132)',
                    'rgba(34,139,34)',
                    'rgba(54, 162, 235)',
                    'rgba(255, 205, 86)'
                    ],
                    hoverOffset: 4
                }]
            };
            const config = {
                type: 'doughnut',
                locale: this.$i18n.locale,
                responsiveOptions:true,
                data: doughnutData
            };
            this.pieChart = new Chart(
                chartID,
                config
            );
        },
        //Get the most popular product
        async getMostPopularProduct(){
            await PopularSaleService.getMostPopularProduct().then((response)=>{
                this.productWithHighestPopularity = response.data
                this.getFirstSaleNum(this.productWithHighestPopularity)
            })
            return this.productWithHighestPopularity;
        },
        //Get the second popular product
        async getSecondPopularProduct(){
            await PopularSaleService.getSecondPopularProduct(this.productWithHighestPopularity).then((response)=>{
                this.productWithSecondPopularity = response.data
                this.getSecondSaleNum(this.productWithSecondPopularity)
            })
            return this.productWithSecondPopularity;
        },
        //Get the third popular product
        async getThirdPopularProduct(){
            await PopularSaleService.getThirdPopularProduct(this.productWithHighestPopularity, this.productWithSecondPopularity).then((response)=>{
                this.productWithThirdPopularity = response.data
                this.getThirdSaleNum(this.productWithThirdPopularity)
            })
            return this.productWithThirdPopularity;
        },
        //Get the fourth popular product
        async getFourthPopularProduct(){
            await PopularSaleService.getFourthPopularProduct(this.productWithHighestPopularity, this.productWithSecondPopularity, this.productWithThirdPopularity).then((response)=>{
                this.productWithFourthPopularity = response.data
                this.getFourthSaleNum(this.productWithFourthPopularity)
            })
            return this.productWithFourthPopularity;
        },
        //Get the most popular product type
        getMostPopularType(){
            PopularSaleService.getMostPopularType().then((response)=>{
                this.mostPopularType = response.data
                this.getNumOfMostPopularType(this.mostPopularType)
            })
        },
        //Get the second popular product type
        getSecondPopularType(){
            PopularSaleService.getSecondPopularType().then((response)=>{
                this.secondPopularType = response.data
                this.getNumOfSecondPopularType(this.secondPopularType)
            })
        },
        //Get the third popular product type
        async getThirdPopularType(){
            this.getMostPopularType()
            this.getSecondPopularType()
            await PopularSaleService.getThirdPopularType().then((response)=>{
                this.thirdPopularType = response.data
                this.getNumOfThirdPopularType(this.thirdPopularType)
                this.getNumOfOtherTypes()
            })
        },
        //Get total quantity of the most popular product 
        getFirstSaleNum(){
            if(this.productWithHighestPopularity!= ''){
                PopularSaleService.getProductSaleNum(this.productWithHighestPopularity).then((response)=>{
                    this.firstProductSaleNum = response.data
                })
            }
        },
        //Get total quantity of the second popular product 
        getSecondSaleNum(){
            if(this.productWithSecondPopularity != ''){
                PopularSaleService.getProductSaleNum(this.productWithSecondPopularity).then((response)=>{
                    this.secondProductSaleNum = response.data
                })
            }
        },
        //Get total quantity of the third popular product 
        getThirdSaleNum(){
            if(this.productWithThirdPopularity!= ''){
                PopularSaleService.getProductSaleNum(this.productWithThirdPopularity).then((response)=>{
                    this.thirdProductSaleNum = response.data
                })
            }
        },
        //Get total quantity of the fourth popular product 
        getFourthSaleNum(){
            if(this.productWithFourthPopularity!= ''){
                PopularSaleService.getProductSaleNum(this.productWithFourthPopularity).then((response)=>{
                    this.fourthProductSaleNum = response.data
                })
            }
        },
        //Get six recent sales records
        getRecentSales(){
            SaleService.getRecentSales().then((response)=>{
                this.recentSales = response.data
            })
        },
        //Go to group chat page.
        groupChat(){
            this.$router.push({path:'/communication'})
        },
    },
    //Execute the following functions when the page is being loaded
    async created(){
        this.userName = sessionStorage.getItem('username')
        this.getRevenueOfBilibili()

        this.getThirdPopularType()
        this.getRevenueOfOthersRevenue()

        await this.getMostPopularProduct()
        await this.getSecondPopularProduct()
        await this.getThirdPopularProduct()
        await this.getFourthPopularProduct()

        this.getSales(),
        this.getCustomers(),
        this.getProducts(),
        
        this.getToDoItems(),

        this.getRecentSales()
    },
    computed:{
        //Check if the current role of the user is Manager.
        hasManager(){
            axios
                .get('currentRole', {params:{userName: this.userName,
                }})
                .then((response) => {
                    if(response.data == 'Manager') this.manager=true;
                    else this.manager=false;
                });
            return this.manager;
        },
        //Check if the current role of the user is Sales Personnel.
        isInfluncer(){
            axios
                .get('currentRole', {params:{userName: this.userName,
                }})
                .then((response) => {
                    if(response.data == 'Influncer')  this.account=true;
                    else  this.account=false;
                });
            return this.account;
        },
        //Get total revenue
        totalPrice(){
            return this.sales.reduce((total,curr)=>(total=total + curr.price * curr.num),0).toFixed(2);
        },
        //Get total number of products sold 
        total(){
            return this.sales.reduce((total,curr)=>(total=total+curr.num),0);
        },
        //Get total number of customers 
        totalCustomers(){
            return this.customers.length;
        },
        //Get total net profit
        netProfit(){
            return this.products.reduce((total,curr)=>(total=total+(curr.sellPrice - curr.purchasePrice) * curr.quantity),0).toFixed(2);
        },
        //Get total number of products in the inventory
        totalProducts(){
            return this.products.reduce((total,curr)=>(total=total+curr.quantity),0);
        },
        //Get todo items that have been completed
        checkedItems(){
            return this.todoItems.filter(item=>item.completed=='true')
        },
        //Get todo items that have not been completed yet
        uncheckedItems(){
            return this.todoItems.filter(item=>item.completed=='false')
        },
        //Set the percentage of the most popular product
        mostPopularProductStyle(){
            var percent = this.firstProductSaleNum / this.total
            return "width: " + percent*100 + "%"
        },
        //Set the percentage of the second popular product
        secondPopularProductStyle(){
            var percent = this.secondProductSaleNum / this.total
            return "width: " + percent*100 + "%"
        },
        //Set the percentage of the third popular product
        thirdPopularProductStyle(){
            var percent = this.thirdProductSaleNum / this.total
            return "width: " + percent*100 + "%"
        },
        //Set the percentage of the fourth popular product
        fourthPopularProductStyle(){
            var percent = this.fourthProductSaleNum / this.total
            return "width: " + percent*100 + "%"
        }
    },
    watch:{
        '$i18n.locale'(newValue){
            if(this.$i18n.locale=='ch'){
                this.chart.destroy()
                this.pieChart.destroy()
                this.createWeekChart()
                this.createPieChart()
            }else{
                this.chart.destroy()
                this.pieChart.destroy()
                this.createChart()
                this.createPieChart()
            }
        }
    }      
}
</script>
