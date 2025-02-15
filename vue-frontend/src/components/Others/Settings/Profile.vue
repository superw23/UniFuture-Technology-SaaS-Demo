//Adapted By GRPTeam202205 (MIT license)
<template>
<!--  -->
<section class="vh-100">
  <div class="container py-5 h-100">
    <div class="row d-flex justify-content-center align-items-center h-100">
      <div class="col col-lg-6 mb-4 mb-lg-0">
        <div class="card mb-3" style="border-radius: .5rem;">
          <div class="row g-0">
            <div class="col-md-4 gradient-custom text-center text-white"
              style="border-top-left-radius: .5rem; border-bottom-left-radius: .5rem;">
              <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava1-bg.webp"
                alt="Avatar" class="img-fluid my-5" style="width: 80px;" />
              <h5>{{userName}}</h5>
              <p>{{role}}</p>
                <li>
                    <i class="icon-user"></i> <span type="button" @click="setting">{{$t('Change Password')}}</span>
                </li>
            </div>
            <div class="col-md-8">
              <div class="card-body p-4">
                <h6>{{$t('Information')}}</h6>
                <hr class="mt-0 mb-4">
                <div class="row pt-1">
                  <div class="col-6 mb-3">
                    <h6>{{$t('Email')}}</h6>
                    <p class="text-muted">{{email}}</p>
                  </div>
                  <div class="col-6 mb-3">
                    <h6>{{$t('Phone')}}</h6>
                    <p class="text-muted">{{phone}}</p>
                  </div>
                </div>
                <h6>{{$t('Project')}}</h6>
                <hr class="mt-0 mb-4">
                <div class="row pt-1">
                  <div class="col-6 mb-3">
                    <h6>{{$t('CloudWalker Inventory System')}}</h6>
                    <p class="text-muted">{{$t('Procurement-Inventory-Sales Managment System')}}</p>
                  </div>
                  <div class="col-6 mb-3">
                    <h6>{{$t('SaaS')}}</h6>
                    <p class="text-muted">{{$t('Cloud-based')}}</p>
                  </div>
                </div>
                <div class="d-flex justify-content-start">
                  <a href="#!"><i class="fab fa-facebook-f fa-lg me-3"></i></a>
                  <a href="#!"><i class="fab fa-twitter fa-lg me-3"></i></a>
                  <a href="#!"><i class="fab fa-instagram fa-lg"></i></a>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</section>
</template>

<script>
import UserService from '@/services/User/UserService'
export default {
    data(){
        return{
            userName: '',
            role: '',
            email: '',
            phone: ''
        }
    },
    created(){
        this.userName = sessionStorage.getItem('username')
        this.getUserRole()
        this.getUserEmail()
        this.getUserPhone()
    },
    methods:{
        getUserRole(){
            UserService.getUserRole(this.userName).then((response)=>{
                this.role = response.data;
            });
        },
        getUserEmail(){
            UserService.getUserEmail(this.userName).then((response)=>{
                this.email = response.data;
            });
        },
        getUserPhone(){
            UserService.getUserPhone(this.userName).then((response)=>{
                this.phone = response.data;
            });
        },
        //Go to the changePassword page.
        setting(){
            this.$router.push({path:'/changePassword'})
        },
    }
}
</script>

<style>

</style>