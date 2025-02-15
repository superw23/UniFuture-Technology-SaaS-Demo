// Made by GRPTeam202205
<template>
  <!-- Customer list page -->
  <!-- Show the modal dialog box in order to change info of existing customer  -->
  <div class="modal fade" id="exampleModal" ref="customerModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <!-- Title of the modal dialog box -->
        <div class="modal-header">
          <h5 class="modal-title" id="exampleModalLabel">
            <span v-if="editing">{{$t('Customer.Edit Customer')}}</span>
            <span v-else>{{$t('Customer.Add New Customer')}}</span>
          </h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <!-- Body of the modal dialog box consists of information of a chosen customer -->
        <div class="modal-body">
          <form>
            <!-- ID of the customer cannot be changed -->
            <div class="mb-3">
              <label for="id" class="col-form-label">{{$t('ID')}}:</label>
              <input type="text" class="form-control" id="id" ref="id" value="" disabled="disabled">
            </div>
            <!-- Name of the customer can be changed -->
            <div class="mb-3">
              <label for="name" class="col-form-label">{{$t('Name')}}:</label>
              <input type="text" class="form-control" id="name" ref="name" value="">
            </div>
            <!-- Address of the customer can be changed -->
            <div class="mb-3">
              <label for="address" class="col-form-label">{{$t('Address')}}:</label>
              <input type="text" class="form-control" id="address" ref="address" value="">
            </div>
            <!-- Phone number of the customer can be changed -->
            <div class="mb-3">
              <label for="phoneNumber" class="col-form-label">{{$t('Phone Number')}}:</label>
              <input type="text" class="form-control" id="phoneNumber" ref="phoneNumber" value="">
            </div>
            <!-- Contact person of the customer can be changed -->
            <div class="mb-3">
              <label for="contact" class="col-form-label">{{$t('Contact')}}:</label>
              <input type="text" class="form-control" id="contact" ref="contact" value="">
            </div>
            <!-- Additional information of the customer can be changed -->
            <div class="mb-3">
              <label for="remarks" class="col-form-label">{{$t('Remarks')}}:</label>
              <input type="text" class="form-control" id="remarks" ref="remarks" value="">
            </div>
          </form>
        </div>
        <div class="modal-footer">
          <!-- Click the button to close the current modal dialog box -->
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">{{$t('Close')}}</button>
          <!-- Click the button to submit the changed customer information -->
          <button type="button" class="btn btn-primary" data-bs-dismiss="modal" @click="updateCustomer()">{{$t('Send')}}</button>
        </div>
      </div>
    </div>
  </div>

  <!-- Search bar by searching the name of the customer -->
  <nav class="navbar">
    <div class="container-fluid">
      <!--Drop-down menu to choose the number of customers per page-->
      <div class="dropdown">
        <button class="btn dropdown-toggle" type="button" id="dropdownMenu2" data-bs-toggle="dropdown" aria-expanded="false">
          {{$t('Records Per Page')}}
        </button>
        <ul class="dropdown-menu" aria-labelledby="dropdownMenu2">
          <li><button class="dropdown-item" type="button" @click="changeCustomerPerPage(5)">5</button></li>
          <li><button class="dropdown-item" type="button" @click="changeCustomerPerPage(10)">10</button></li>
          <li><button class="dropdown-item" type="button" @click="changeCustomerPerPage(15)">15</button></li>
        </ul>
      </div>
      <span class="navbar-brand"></span>
      <form class="d-flex" role="search">
      <input class="form-control me-2" type="search" :placeholder="$t('Search')" aria-label="Search" v-model="search">
      <button class="btn btn-outline-success" type="submit">{{$t('Search')}}</button>
      </form>
    </div>
  </nav>
  
  <div class="container">
    <h1>{{$t('Customer.Customers List')}}
    <!-- Click the button to go to add customer page -->
    <router-link :to="{name:'AddCustomer'}"><i class="fa fa-cart-plus" style="font-size:42px"></i></router-link>
    </h1>
    
    <br>
    <!-- Show the following message if the customer is deleted successfully-->
    <div class="alert alert-success" v-if="isSuccess">
        {{$t('Customer.The customer is deleted successfully!')}}
    </div>
    <!-- Show the following message if the customer is edited successfully-->
    <div class="alert alert-success" v-if="isEdited">
        {{$t('Customer.The customer is edited successfully!')}}
    </div>
    <!-- Customer table to show existing customers in the database -->
    <div class="table-responsive">
      <table class="table table-stripped">
        <thead >
          <!-- ID of the customer -->
          <th>
            <i class="fa fa-sort-numeric-asc" type="button" aria-hidden="true" @click="sort('id')"/>
              {{$t('Customer ID')}}
          </th>
          <!-- Name of the customer -->
          <th >
            <i class="fa fa-sort-alpha-asc" type="button" aria-hidden="true" @click="sort('name')"/>
              {{$t('Name')}}
          </th>
          <!-- Address of the customer -->
          <th>
            <i class="fa fa-sort-alpha-asc" type="button" aria-hidden="true" @click="sort('address')"/>
              {{$t('Address')}}
          </th>
          <!-- Email of the customer -->
          <th>
            <i class="fa fa-sort-alpha-asc" type="button" aria-hidden="true" @click="sort('contact')"/>
              {{$t('Email')}}
          </th>
          <!-- Phone number of the customer -->
          <th>
            <i class="fa fa-sort-alpha-asc" type="button" aria-hidden="true" @click="sort('phoneNumber')"/>
              {{$t('Phone Number')}}
          </th>
          <!-- Edit information of the customer -->
          <th>{{$t('Edit')}}</th>
        </thead>
        <tbody>
          <tr v-for = "customer in filteredCustomers" v-bind:key = "customer.id" class="table-bordered">
            <td>{{customer.id}}</td>
            <td>{{customer.name}}</td>
            <td>{{customer.address}}</td>
            <td>{{customer.contact}}</td>
            <td>{{customer.phoneNumber}}</td>
            <td>
              <div class="modal fade" id="onDelete" tabindex="-1" aria-labelledby="onDeleteLabel" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="onDelete">{{$t('CONFIRM')}}</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <div class="modal-body">{{$t('Are your sure to delete the customer?')}}</div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">{{$t('No')}}</button>
                                    <button type="button" class="btn btn-primary" data-bs-dismiss="modal" @click="deleteCustomer(this.customerId)">{{$t('Yes')}}</button>
                                </div>
                                </div>
                            </div>
                        </div>
              <fa icon="trash" type="button" data-bs-toggle="modal" data-bs-target="#onDelete"  @click="getCustomerId(customer.id)"/>&nbsp;
              <fa icon="edit" type="button" data-bs-toggle="modal" data-bs-target="#exampleModal" data-bs-whatever="@mdo" @click="passCustomer(customer)"/>    
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <!-- Show the total number of customers -->
    <h5>{{$t('Customer.Total customers')}}: {{total}}</h5>
    <!-- Download PDF format of the customer table -->
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
    name: 'Customers',
    data(){
      return{
        itemsPerPage: 5,//the number of customer shown in one page
        pageNum: 0,//store current page number
        customerId: '',
        search:'',//store search content
        editing: false,//true if the customer info need editing, else false
        isSuccess: false,//true if the customer is deleted successfully, else false
        isEdited: false,//true if the customer info is edited successfully, else false
        fixedCustomers: [],//store all customers
        customers: [],//store customers per page
        pdfIconSize: '2em',
        excelIconSize: '2em',
      }
    },
    methods:{
      //Get all customers from the database
      async getCustomers(){
        await CustomerService.getCustomers().then((response)=>{
          this.customers = response.data;
          this.fixedCustomers = response.data
        });
      },
      //Delete a chosen customer from the database
      deleteCustomer(customerId) {
        CustomerService.deleteCustomer(customerId).then((response)=>{
          this.isSuccess = true
          this.isEdited = false
          this.getCustomers()
        });
      },
      //Update the information of a chosen customer
      updateCustomer(){
        axios
          .put(`updateCustomer`, {
            id: this.$refs.id.value,
            name: this.$refs.name.value,
            address: this.$refs.address.value,
            phoneNumber: this.$refs.phoneNumber.value,
            contact: this.$refs.contact.value,
            remarks: this.$refs.remarks.value
          })
          .then((response) => {
            this.isEdited = true;
            this.isSuccess = false
            console.log(response);
            this.getCustomers(),
            this.id = '',
            this.name = '',
            this.address = '',
            this.phoneNumber = '',
            this.contact = '',
            this.remarks = ''

          });
      },
      //Pass the chosen customer information to the modal dialog box
      passCustomer(customer){
        this.editing = true;
        this.$refs.id.value = customer.id;
        this.$refs.name.value = customer.name;
        this.$refs.address.value = customer.address;
        this.$refs.phoneNumber.value = customer.phoneNumber,
        this.$refs.contact.value = customer.contact,
        this.$refs.remarks.value = customer.remarks
      },
      //Get customers information as required after paging
      pagination(pageNum){
        this.pageNum = pageNum;
        if(this.pageNum==-1) {
          this.pageNum=0;
          return;
        }
        CustomerService.pagination(pageNum,this.itemsPerPage).then((response)=>{
          this.customers = response.data;
        });
      },
      //Change the number of customers shown per page 
      changeCustomerPerPage(num){
        this.itemsPerPage = num;
        this.pagination(this.pageNum,this.itemsPerPage);
      },
      getCustomerId(id){
        this.customerId = id
      },
      //Sort customers by a given field, such as id, name
      sort(field){
        CustomerService.sortAscending(this.pageNum, this.itemsPerPage, field).then((response)=>{
          this.customers = response.data;
        });
      },
      //Download the pdf format of the customer table
      downloadPDF(){
            const columns = [
                    { header: 'Customer ID', dataKey: 'id' },
                    { header: 'Name', dataKey: 'name' },
                    { header: 'Address', dataKey: 'address' },
                    { header: 'Phone Number', dataKey: 'phoneNumber' },
                    { header: 'Contact', dataKey: 'contact' },
                    { header: 'Remarks', dataKey: 'remarks' }
                ];
            const pdf = new jsPDF();
            //Text is placed using x, y coordinates
            pdf.setFontSize(20).text("Customer List", 80, 10);
            //Use autoTable plugin
            autoTable(pdf, {
                columns,
                body: this.customers,
                margin:{top: 30}
            });
            //Show total stock
            pdf.setFontSize(16).text('Total customers: ' + this.total, 80, 20);
            //Download pdf
            pdf.save("Customers.pdf");
        },
      //Download the excel format of the customer table
      downloadExcel() {
        const filteredCustomersWithoutUseless = this.filteredCustomers.map(customers => {
            return {
                CustomersID: customers.id,
                Name: customers.name,
                Address: customers.address,
                PhoneNumber: customers.phoneNumber,
                Email: customers.contact,
                Description: customers.remarks,
            };
        });

        // Create a new workbook
        const wb = XLSX.utils.book_new();

        // Add a new worksheet to the workbook
        const ws = XLSX.utils.json_to_sheet(filteredCustomersWithoutUseless);

        // Add the worksheet to the workbook
        XLSX.utils.book_append_sheet(wb, ws, 'Customers');

        // Write the workbook to a binary string
        const wbout = XLSX.write(wb, { bookType: 'xlsx', type: 'binary' });

        // Convert the binary string to a Blob object
        const blob = new Blob([s2ab(wbout)], { type: 'application/octet-stream' });

        // Create a temporary link to the file and trigger a download
        const url = window.URL.createObjectURL(blob);
        const link = document.createElement('a');
        link.href = url;
        link.download = 'Customers.xlsx';
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
      }
    },
    async created(){
      //Get all customers data when the page is being loaded
      await this.getCustomers()
      this.itemsPerPage = this.customers.length
    },
    computed:{
      //Get required customers after searching
      filteredCustomers(){
        return this.customers.filter(customer => customer.name.toLowerCase().includes(this.search.toLowerCase()));
      },
      //Get total number of customers
      total(){
        return this.customers.length;
      },
      totalPages(){
        return (Math.ceil(this.fixedCustomers.length / this.itemsPerPage))
      }
    },
}
</script>

<style>

</style>