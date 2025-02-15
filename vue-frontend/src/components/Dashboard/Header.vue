//Adapted By GRPTeam202205 (MIT license)
<template>
  <!-- Header that appears in all pages -->
  <div class="header">
    <div class="header-content clearfix">
      <!-- Hamburger button to control the state of the navigation bar -->
      <div class="nav-control">
        <div class="hamburger">
          <span class="toggle-icon" @click="changeSideBar"><i class="icon-menu"></i></span>
        </div>
      </div>
      
      <!-- Search bar to search for a certain page according to its name -->
      <div class="header-left">
        <div class="input-group icons">
          <input
            type="search"
            list="datalistName"
            class="form-control"
            :placeholder="$t('Jump to')"
            aria-label="Search Dashboard"
            v-model="search"
            @keyup.enter="searchPage"
          />
          <datalist id="datalistName">
            <option v-for = "page in pageList" :key = "page.id">{{page}}</option>   
          </datalist>
          <!-- <input type="text" list="datalistName" class="form-control" :placeholder="$t('Chips')" id= "name" v-model="name"  @blur="auto" required> -->
                            <!-- <datalist id="datalistName">
                                <option v-for = "product in filteredProducts" :key = "product.id">{{product.name}}</option>   
                            </datalist> -->
          <div class="input-group-prepend">
            <span
              class="input-group-text bg-transparent border-0 pr-2 pr-sm-3"
              id="basic-addon1"
              ><i class="mdi mdi-magnify" type="button" @click="searchPage"></i
            ></span>
          </div>
          <div class="drop-down animated flipInX d-md-none">
            <form action="#">
              <input type="text" class="form-control" :placeholder="$t('Search')" />
            </form>
          </div>
        </div>
      </div>
      <!-- Message box, language switcher and the user profile -->
      <div class="header-right">
        <ul class="clearfix">
          <li class="icons dropdown">
            <a href="javascript:void(0)">
              <img src="/static/Others/question.png" width="20" height="20" @click="help"/>
            </a>
          </li>
          <!-- Language switcher (English, Chinese) -->
          <li class="icons dropdown">
            <span 
              data-toggle="dropdown" type="button">{{language}}</span>
              <i class="fa fa-angle-down f-s-14" aria-hidden="true"></i>
            <div
              class="drop-down dropdown-language animated fadeIn dropdown-menu"
            >
              <div class="dropdown-content-body">
                <ul>
                  <li @click="translateToEnglish" type="button">{{$t('English')}}</li>
                  <li @click="translateToChinese" type="button">{{$t('Chinese')}}</li>
                </ul>
              </div>
            </div>
          </li>
          <!-- User profile -->
          <li class="icons dropdown">
            <div
              class="user-img c-pointer position-relative"
              data-toggle="dropdown"
            >
              <span class="activity active"></span>
              <img
                src="../../../public/frontend/images/user/1.png"
                height="40"
                width="40"
                alt=""
              />
            </div>
            <div
              class="drop-down dropdown-profile animated fadeIn dropdown-menu"
            >
              <div class="dropdown-content-body">
                <ul>
                  <li>
                    <i class="icon-user"></i> <span type="button" @click="setting">{{$t('Profile')}}</span>
                  </li>
                  <li>
                      <img src="/static/Others/bubble-chat.png" width="20" height="20"/> <span type="button" @click="groupChat">{{$t('Group Chat')}}</span>
                  </li>

                  <hr class="my-2" />
                  <li @click="lockScreen" type="button">
                    <i class="icon-lock"></i> <span>{{$t('Lock Screen')}}</span>
                  </li>
                  <li @click="logout" type="button">
                    <i class="icon-key"></i> <span>{{$t('Logout')}}</span>
                  </li>
                </ul>
              </div>
            </div>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data(){
    return{
      language: '',//store current language 
      search: '',//store search content
      pageList: ['home', 'purchase', 'addPurchase','returnPurchase', 'supplier', 'addSupplier', 'sale', 'addSale', 'returnSale', 'addCustomer', 'viewCustomer', 'addProduct', 'viewBilibili', 'user', 'addUser','changeUserPermission', 'userProfile', 'changePassword', 'communication', 'userGuidance', 'aboutUs', 'LockScreen']//valid search content, 
    }
  },
  created(){
    this.languageChecker()
  },
  methods: {
    //Inform the parent component to change the layout of the navigation bar. 
    changeSideBar() {
      this.$emit('layout')
    },
    //Translate the language swicher to Chinese version.
    translateToChinese(){
      this.language = '中文'
      this.$i18n.locale = 'ch'
    },
    //Translate the language swicher to English version.
    translateToEnglish(){
      this.language = 'English'
      this.$i18n.locale = 'en'
    },
    //Go back to login page.
    logout(){
      this.$router.push({path:'/'})
    },
    //Go to the lock screen page.
    lockScreen(){
      this.$router.push({path:'/lockScreen'})
    },
    //Go to the user profile page.
    setting(){
      this.$router.push({path:'/userProfile'})
    },
    //Go back to group chat page.
    groupChat(){
      this.$router.push({path:'/communication'})
    },
    //Go back to help page.
    help(){
      this.$router.push({path:'/userGuidance'})
    },
    //Go to the required page according to the search content.
    searchPage(){
      var searchContent = this.search.replace(/\s+/g,"").toLowerCase()
      for(var i=0; i<this.pageList.length; i++){
        if(this.pageList[i].toLowerCase()==this.search.replace(/\s+/g,"").toLowerCase()){
          this.$router.push({path:`/${this.search.replace(/\s+/g,"")}`})
          break
        }
        else if(searchContent == ('product'.toLowerCase())||searchContent==('product list'.replace(/\s+/g,"").toLowerCase())||searchContent==('products'.toLowerCase())||searchContent==('products list'.replace(/\s+/g,"").toLowerCase())){
          this.$router.push({path:'/viewBilibili'})
          break
        }
        else if(searchContent==('purchases'.toLowerCase())||searchContent==('purchase history'.replace(/\s+/g,"").toLowerCase())||searchContent==('purchases history'.replace(/\s+/g,"").toLowerCase())){
          this.$router.push({path:'/purchase'})
          break
        }
        else if(searchContent==('sale history'.replace(/\s+/g,"").toLowerCase())||searchContent==('sale'.toLowerCase())||searchContent==('sales history'.replace(/\s+/g,"").toLowerCase())){
          this.$router.push({path:'/sale'})
          break
        }
        else if(searchContent==('help'.toLowerCase())||searchContent==('demo'.toLowerCase())||searchContent==('user manual'.replace(/\s+/g,"").toLowerCase())){
          this.$router.push({path:'/addFile'})
          break
        }
      }
      this.search = ''
    },
    languageChecker(){
      if(this.$i18n.locale == 'en')
        this.language = 'English'
      else
        this.language = '中文'
    }
  },
};
</script>
