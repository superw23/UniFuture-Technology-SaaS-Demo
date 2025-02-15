// Made by GRPTeam202205
<template>
  <!-- Supplier list page -->
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
          <div class="dropdown"> <br><br>
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
    <!-- Click the button to go to add supplier page -->
    <h1 class="test-center">{{$t('Supplier.Suppliers List')}}
        <router-link :to="{name:'AddSupplier'}"><i class="fa fa-cart-plus" style="font-size:42px"></i></router-link>
    </h1>
    <!-- Show the following message if the supplier is deleted successfully-->
    <div class="alert alert-success" v-if="isSuccess">
        {{$t('Supplier.The supplier is deleted successfully!')}}
    </div>
    <!-- Show the following message if the supplier is edited successfully-->
    <div class="alert alert-success" v-if="isEdited">
        {{$t('Supplier.The supplier is edited successfully!')}}
    </div>
    <br>
    <div class="table-responsive">
    <table class="table table-stripped">
        <thead class="table-stripped">
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
                  <!-- Modal -->
                        <div class="modal fade" id="onDelete" tabindex="-1" aria-labelledby="onDeleteLabel" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="onDeleteLabel">{{$t('CONFIRM')}}</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <div class="modal-body">{{$t('Are you sure to delete the supplier?')}}</div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">{{$t('No')}}</button>
                                    <button type="button" class="btn btn-primary" data-bs-dismiss="modal" @click="deleteSupplier(this.supplierId)">{{$t('Yes')}}</button>
                                </div>
                                </div>
                            </div>
                        </div>
                  <fa icon="trash" type="button" data-bs-toggle="modal" data-bs-target="#onDelete" @click="getSupplierId(supplier.id)"/>&nbsp;
                  <fa icon="edit" type="button" data-bs-toggle="modal" data-bs-target="#exampleModal" data-bs-whatever="@mdo" @click="passSupplier(supplier)"/>
                </td>
            </tr>
        </tbody>
    </table>
    </div>
    <h5>{{$t('Supplier.Total suppliers')}}: {{total}}</h5>
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
import SupplierService from '@/services/Supplier/SupplierService'
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
    name: 'Suppliers',
    data(){
        return{
          itemsPerPage: 5,//the number of suppliers shown in one page
          pageNum: 0,//store current page number
          supplierId: '',
          search:'',//store search content
          editing: false,//true if the supplier info need editing, else false
          isSuccess: false,//true if the supplier is deleted successfully, else false
          isEdited: false,//true if the supplier info is edited successfully, else false
          fixedSuppliers: [],//store all suppliers
          suppliers: [],//store all suppliers
          pdfIconSize: '2em',
          excelIconSize: '2em',
        }
    },
    methods:{
        //Get all suppliers from the database
        async getSuppliers(){
            await SupplierService.getSuppliers().then((response)=>{
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
        getSupplierId(id){
          this.supplierId = id
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
      //Download the pdf format of the supplier table
        downloadPDF(){
            const columns = [
                    { header: 'Supplier ID', dataKey: 'id' },
                    { header: 'Address', dataKey: 'address' },
                    { header: 'Company', dataKey: 'company' },
                    { header: 'Phone Number', dataKey: 'phoneNumber' },
                    { header: 'Contact', dataKey: 'contact' },
                    { header: 'Remarks', dataKey: 'remarks' }
                ];
            const pdf = new jsPDF();
            //text is placed using x, y coordinates
            pdf.setFontSize(20).text("Supplier List", 80, 10);
            //use autoTable plugin
            autoTable(pdf, {
                columns,
                body: this.suppliers,
                margin:{top: 30}
            });
            //show total stock
            pdf.setFontSize(16).text('Total supliers: ' + this.total, 80, 20);
            //download pdf
            pdf.save("Suppliers.pdf");
        },
        //Download the excel format of the supplier table
        downloadExcel() {
            const filteredSuppliersWithoutUseless = this.filteredSuppliers.map(suppliers => {
                return {
                    SupplierID: suppliers.id,
                    CompanyName: suppliers.company,
                    Address: suppliers.address,
                    PhoneNumber: suppliers.phoneNumber,
                    Email: suppliers.contact,
                    Description: suppliers.remarks,
                };
            });

            // Create a new workbook
            const wb = XLSX.utils.book_new();

            // Add a new worksheet to the workbook
            const ws = XLSX.utils.json_to_sheet(filteredSuppliersWithoutUseless);

            // Add the worksheet to the workbook
            XLSX.utils.book_append_sheet(wb, ws, 'Suppliers');

            // Write the workbook to a binary string
            const wbout = XLSX.write(wb, { bookType: 'xlsx', type: 'binary' });

            // Convert the binary string to a Blob object
            const blob = new Blob([s2ab(wbout)], { type: 'application/octet-stream' });

            // Create a temporary link to the file and trigger a download
            const url = window.URL.createObjectURL(blob);
            const link = document.createElement('a');
            link.href = url;
            link.download = 'Suppliers.xlsx';
            document.body.appendChild(link);
            link.click();
            document.body.removeChild(link);
        }
    },
    async created(){
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