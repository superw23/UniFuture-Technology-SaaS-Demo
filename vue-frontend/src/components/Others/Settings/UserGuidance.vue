// Made by GRPTeam202205
<template>
    <!-- User Guidance page -->
    <div>
        <h2>{{$t('User Guidance')}}</h2>
        <div class="row col-md-8 offset-md-2">
            <videoPlay class="center" v-bind="options"/>
        </div>
        <hr />
        <h2>{{$t('Choose a user guidance type')}}</h2>
        <div class="row col-md-2 offset-md-5">
            <select class="form-select" aria-label="Default select example" v-model="type" required>
                <option>{{$t('Purchase.Purchase')}}</option>
                <option>{{$t('Sale.Sale')}}</option>
                <option>{{$t('Inventory.Inventory')}}</option>
                <option>{{$t('Customer.Customer')}}</option>
                <option>{{$t('Supplier.Supplier')}}</option>
                <option>{{$t('User.User')}}</option>
                <option>{{$t('LockAndUnlockScreen')}}</option>
            </select>
        </div>
        <br>
        
        <section>
            <embed :src="showUserManual" id="guidanceType" type="application/pdf" style="height:100vh; width:100vh">
        </section>
    </div>
</template>

<script>
import 'vue3-video-play/dist/style.css'
import {videoPlay} from 'vue3-video-play'
import { reactive } from 'vue'
export default {
    components:{
        videoPlay
    },
     data() {
        return {
            pdfFile: '',
            type: 'Purchase',
            options: reactive({
                width: '800px',
                height: '450px',
                muted:false,
                speedRate:['0.75', '1.0', '1.25', '1.5', '2.0'],
                autoPlay: false,
                loop: false,
                control: true,
                title: 'Demo',
                src: "/static/VideoDemo/demo.mp4"    
            }),
        }
    },
    methods:{
        
    },
    computed:{
        //Show a given user manual
        showUserManual(){
            return "/static/UserManual/"+this.type+".pdf"
        }
    }
}
</script>

<style scoped="scoped">
embed{
    width: 100%;
    height:"100%";
}
</style>