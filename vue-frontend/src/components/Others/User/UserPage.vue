// Made by GRPTeam202205
<template>
<div class="modal fade" id="exampleModal" ref="userModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">
        <div class="icon1"></div> <div class="empty-row2"></div> <div class="icon2"></div>
          <span v-if="editing">{{$t('User.Edit User')}}</span>
          <span v-else>{{$t('User.Add New User')}}</span>
        </h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>

      <!--User List-->
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
            <label for="email" class="col-form-label">{{$t('Email')}}:</label>
            <input type="text" class="form-control" id="email" ref="email" value="">
          </div>
          <div class="mb-3">
            <label for="platform" class="col-form-label">{{$t('Platform')}}:</label>
            <select class="form-select" id="platform" ref="platform">
              <option>{{$t('Manager')}}</option>
              <option>{{$t('Warehouse Manager')}}</option>
              <option>{{$t('Purchase Manager')}}</option>
              <option>{{$t('Sales Personnel')}}</option>
            </select>
          </div>
        </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">{{$t('Close')}}</button>
        <button type="button" class="btn btn-primary" data-bs-dismiss="modal" @click="updateUser()">{{$t('Send')}}</button>
      </div>
    </div>
  </div>
</div>

    <nav class="navbar">
        <div class="container-fluid">
          <!--Drop-down menu to choose the number of users per page-->
          <div class="dropdown">
              <button class="btn dropdown-toggle" type="button" id="dropdownMenu2" data-bs-toggle="dropdown" aria-expanded="false">
                  {{$t('User.Users Per Page')}}
              </button>
              <ul class="dropdown-menu" aria-labelledby="dropdownMenu2">
                  <li><button class="dropdown-item" type="button" @click="changeUserPerPage(5)">5</button></li>
                  <li><button class="dropdown-item" type="button" @click="changeUserPerPage(10)">10</button></li>
                  <li><button class="dropdown-item" type="button" @click="changeUserPerPage(15)">15</button></li>
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
    <h1 class="test-center" data-test="userList">{{$t('User.User List')}}
        <router-link :to="{name:'AddUser'}"><i class="fa fa-cart-plus" style="font-size:42px"></i></router-link>
        </h1>
        
    <!-- Success Alert will be shown if the user is successfully deleted from the database. -->
    <div class="alert alert-success" v-if="isSuccess">
        {{$t('The user is deleted successfully!')}}
    </div>
    <!-- Success Alert will be shown if the user is successfully edited from the database. -->
    <div class="alert alert-success" v-if="isEdited">
        {{$t('The user is edited successfully!')}}
    </div>
    <br>
    <div class="table-responsive">
    <table class="table table-stripped">
        <thead>
            <th>
              <i class="fa fa-sort-numeric-asc" type="button" aria-hidden="true" @click="sort('id')"/>
              {{$t('User ID')}}
            </th>
            <th>
              <i class="fa fa-sort-alpha-asc" type="button" aria-hidden="true" @click="sort('姓名')"/>
              {{$t('Name')}}
            </th>
            <th>
              <i class="fa fa-sort-alpha-asc" type="button" aria-hidden="true" @click="sort('邮箱')"/>
              {{$t('Email')}}
            </th>
            <th>
              <i class="fa fa-sort-alpha-asc" type="button" aria-hidden="true" @click="sort('平台')"/>
              {{$t('Platform')}} 
            </th>
            <th>{{$t('Edit')}}</th>
        </thead>
        <tbody>
            <tr v-for = "user in filteredUsers" v-bind:key = "user.id" class="table-bordered">
                <td>{{user.id}}</td>
                <td>{{user.姓名}}</td>
                <td>{{user.邮箱}}</td>
                <td>{{user.平台}}</td>
                <td>
                  <!-- Modal -->
                        <div class="modal fade" id="onDelete" tabindex="-1" aria-labelledby="onDeleteLabel" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="onDeleteLabel">{{$t('CONFIRM')}}</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <div class="modal-body">{{$t('Are you sure to delete the user?')}}</div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">{{$t('No')}}</button>
                                    <button type="button" class="btn btn-primary" data-bs-dismiss="modal" @click="deleteUser(this.userId)">{{$t('Yes')}}</button>
                                </div>
                                </div>
                            </div>
                        </div>
                  <fa icon="trash" type="button" data-bs-toggle="modal" data-bs-target="#onDelete" @click="getUserId(user.id)"/>&nbsp;
                  <fa icon="edit" type="button" data-bs-toggle="modal" data-bs-target="#exampleModal" data-bs-whatever="@mdo" @click="passUser(user)"/>    
                </td>
            </tr>
        </tbody>
    </table>
    </div>
    <h5>{{$t('User.Total users')}}: {{total}}</h5>
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
import UserService from '@/services/User/UserService'
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
    name: 'Users',
    data(){
        return{
          itemsPerPage: 5,//the number of customer shown in one page
          pageNum: 0,//store current page number
          search:'',//store search content
          userId: '',
          editing: false,//true if the user info needs editing, else false
          isSuccess: false,//true if the customer is deleted successfully, else false
          isEdited: false,//true if the customer is edited successfully, else false
          users: [],//store users per page
          fixedUsers: [],//store all users
          pdfIconSize: '2em',
          excelIconSize: '2em',
        }
    },
    methods:{
        //Get all users from the database
        async getUsers(){
            await UserService.getUsers().then((response)=>{
                this.users = response.data;
                this.fixedUsers = response.data;
            });
        },
        //Delete a chosen user from the database
        async deleteUser(userId) {
            const response = await axios.delete(`deleteUser/${userId}`);
            this.isSuccess = true;
            this.isEdited = false;
            this.getUsers()
        },
        //Sort users by a given field, such as id, name
        sort(field){
            UserService.sort(this.pageNum, this.itemsPerPage, field).then((response)=>{
                this.users = response.data;
            });
        },
        //Update the information of a chosen user
        updateUser(){
           axios
                .put(`updateUser`, {
                    id: this.$refs.id.value,
                    姓名: this.$refs.name.value,
                    邮箱: this.$refs.email.value,
                    平台: this.$refs.platform.value
                })
                .then((response) => {
                    this.isEdited = true
                    this.isSuccess = false
                    this.getUsers()
                    console.log(response)
                });
          
        },
        //Get users information as required after paging
        pagination(pageNum){
            this.pageNum = pageNum;
            if(this.pageNum==-1) {
                this.pageNum=0;
                return;
            }
            console.log(this.pageNum) ;
            UserService.pagination(pageNum,this.itemsPerPage).then((response)=>{
                this.users = response.data;
            });
        },
        //Pass the chosen user information to the modal dialog box
        passUser(user){
          this.editing = true;
          this.$refs.id.value = user.id;
          this.$refs.name.value = user.姓名;
          this.$refs.email.value = user.邮箱;
          this.$refs.platform.value = user.平台
        },
        //Change the number of users shown per page 
        changeUserPerPage(num){
            this.itemsPerPage = num;
            this.pagination(this.pageNum,this.itemsPerPage);
        },
        //Download the pdf format of the user table
        downloadPDF(){
            const columns = [
                    { header: 'User ID', dataKey: 'id' },
                    { header: 'Name', dataKey: '姓名' },
                    { header: 'Email', dataKey: '邮箱' },
                    { header: 'Platform', dataKey: '平台' }
                ];
            const pdf = new jsPDF();
            //text is placed using x, y coordinates
            pdf.setFontSize(20).text("User List", 80, 10);
            //use autoTable plugin
            autoTable(pdf, {
                columns,
                body: this.users,
                margin:{top: 30}
            });
            //show total stock
            pdf.setFontSize(16).text('Total users: ' + this.total, 80, 20);
            //download pdf
            pdf.save("Users.pdf");
        },
        //Download the excel format of the user table
        downloadExcel() {
            // Create a new array of objects that excludes the 'password' property
            const filteredUsersWithoutPassword = this.filteredUsers.map(user => {
               return {
                 UserID: user.id,
                 Name: user.姓名,
                 Email: user.邮箱,
                 Platform: user.平台,
               };
            });

            // Create a new workbook
            const wb = XLSX.utils.book_new();

            // Add a new worksheet to the workbook
            const ws = XLSX.utils.json_to_sheet(filteredUsersWithoutPassword);

            // Add the worksheet to the workbook
            XLSX.utils.book_append_sheet(wb, ws, 'Users');

            // Write the workbook to a binary string
            const wbout = XLSX.write(wb, { bookType: 'xlsx', type: 'binary' });

            // Convert the binary string to a Blob object
            const blob = new Blob([s2ab(wbout)], { type: 'application/octet-stream' });

            // Create a temporary link to the file and trigger a download
            const url = window.URL.createObjectURL(blob);
            const link = document.createElement('a');
            link.href = url;
            link.download = 'Users.xlsx';
            document.body.appendChild(link);
            link.click();
            document.body.removeChild(link);
            },
            getUserId(id){
              this.userId = id
            },
    },
    created(){
      //Get all users data when the page is being loaded
      this.getUsers()
    },
    computed:{
      //Get required users after searching
      filteredUsers(){
          return this.users.filter(user => user.姓名.toLowerCase().includes(this.search.toLowerCase()));
      },
      //Get total number of users
      total(){
        return this.users.length;
      },
      totalPages(){
        return (Math.ceil(this.fixedUsers.length / this.itemsPerPage))
      }
    },
}
</script>

<style>

</style>