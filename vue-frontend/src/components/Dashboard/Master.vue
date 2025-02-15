// Made by GRPTeam202205
<template>
    <div id="dashboard">
        <!-- Header -->
        <Header @layout= "changeSideBar"/>

        <!-- Content -->
        <div class="content-body">
            <router-view></router-view>
        </div>
        
        <!-- SideBar -->
        <SideBar/>
        
        <!-- Footer -->
        <div class="footer">
            <div class="copyright">
                <p>{{$t('Copyright &copy; Designed & Developed by GRP Team202205')}}</p>
            </div>
        </div>
    </div>
  
</template>

<script>
import Header from './Header.vue';
import SideBar from './SideBar.vue';
export default {
    name: 'Master',
    data() {
        return {
            sideBar: true  
        };
    },
    components:{
        Header,
        SideBar
    },
    methods: {
        //Change the layout of the sidebar
        changeSideBar() {
            this.sideBar = !(this.sideBar);
            if(this.sideBar)
                document.getElementById('dashboard').className=""
            else
                document.getElementById('dashboard').className="show menu-toggle"
        }
    }
}
</script>

<style>

</style>