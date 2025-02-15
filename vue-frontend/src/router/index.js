// Made by GRPTeam202205
import Login from '@/components/Login/Login.vue'
import LockScreen from '@/components/Others/Settings/LockScreen.vue'
import Master from '../components/Dashboard/Master.vue'
import Home from '../components/Dashboard/Home.vue'
import AddCustomer from '../components/Sales/Customer/AddCustomer.vue'
import Customer from '../components/Sales/Customer/Customer.vue'
import Bilibili from '../components/Bilibili/Bilibili.vue'
import Income from '../components/Income/Income.vue'
import AddProduct from '../components/Bilibili/AddProduct.vue'
import TypeDetails from '../components/Bilibili/TypeDetails.vue'
import Supplier from '../components/Procurement/Supplier/Supplier.vue'
import AddSupplier from '../components/Procurement/Supplier/AddSupplier.vue'
import SupplierDetails from '../components/Procurement/Supplier/SupplierDetails.vue'
import Sale from '../components/Sales/Sale/Sale.vue'
import AddSale from '../components/Sales/Sale/AddSale.vue'
import ReturnSale from '../components/Sales/Sale/ReturnSale.vue'
import Purchase from '../components/Procurement/Purchase/Purchase.vue'
import AddPurchase from '../components/Procurement/Purchase/AddPurchase.vue'
import ReturnPurchase from '../components/Procurement/Purchase/ReturnPurchase.vue'
import User from '../components/Others/User/UserPage.vue'
import AddUser from '../components/Others/User/AddUser.vue'
import ChangeUserPermission from '../components/Others/User/ChangeUserPermission.vue'
import ChangePassword from '../components/Others/Settings/ChangePassword.vue'
import UserGuidance from '../components/Others/Settings/UserGuidance.vue'
import UserProfile from '../components/Others/Settings/Profile.vue'
import AboutUs from '../components/Others/Settings/AboutUs.vue'
import ForgetPassword from '../components/Login/ForgetPassword.vue'
import Communication from '../components/Others/Settings/Communication.vue'
import { createRouter, createWebHistory} from 'vue-router'

const routes = [
    {
      path: '/',
      name: 'Login',
      component: Login,
    },
    {
      path: '/forgetPassword',
      name: 'ForgetPassword',
      component: ForgetPassword,
    },
    {
      path: '/lockScreen',
      name: 'LockScreen',
      component: LockScreen,
    },
    {
      path: '/master',
      name: 'Master',
      component: Master,
      redirect: '/home',
      children: [
        {
          path: '/home',
          name: 'Home',
          component: Home,
        },
        {
          path: '/addCustomer',
          name: 'AddCustomer',
          component: AddCustomer,
        },
        {
          path: '/viewCustomer',
          name: 'Customer',
          component: Customer,
        },
        {
          path: '/viewBilibili',
          name: 'Bilibili',
          component: Bilibili,
        },
        {
          path: '/viewIncome',
          name: 'Income',
          component: Income,
        },
        {
          path: '/addProduct',
          name: 'AddProduct',
          component: AddProduct,
        },
        {
          path: '/typeDetails/:typeName',
          name: 'TypeDetails',
          component: TypeDetails,
        },
        {
          path: '/user',
          name: 'User',
          component: User,
        },
        {
          path: '/supplier',
          name: 'Supplier',
          component: Supplier,
        },
        {
          path: '/addSupplier',
          name: 'AddSupplier',
          component: AddSupplier,
        },
        {
          path: '/supplierDetails/:productName',
          name: 'SupplierDetails',
          component: SupplierDetails,
        },
        {
          path: '/sale',
          name: 'Sale',
          component: Sale,
        },
        {
          path: '/addSale',
          name: 'AddSale',
          component: AddSale,
        },
        {
          path: '/returnSale',
          name: 'ReturnSale',
          component: ReturnSale,
        },
        {
          path: '/purchase',
          name: 'Purchase',
          component: Purchase,
        },
        {
          path: '/addPurchase',
          name: 'AddPurchase',
          component: AddPurchase,
        },
        {
          path: '/returnPurchase',
          name: 'ReturnPurchase',
          component: ReturnPurchase,
        },
        {
          path: '/addUser',
          name: 'AddUser',
          component: AddUser,
        },
        {
          path: '/changeUserPermission',
          name: 'ChangeUserPermission',
          component: ChangeUserPermission,
        },
        {
          path: '/changePassword',
          name: 'ChangePassword',
          component: ChangePassword,
        },
        {
          path: '/communication',
          name: 'Communication',
          component: Communication,
        },
        {
          path: '/userGuidance',
          name: 'UserGuidance',
          component: UserGuidance,
        },
        {
          path: '/userProfile',
          name: 'UserProfile',
          component: UserProfile,
        },
        {
          path: '/aboutUs',
          name: 'AboutUs',
          component: AboutUs,
        }
      ]
    }
  ]
  
  const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes,
    scrollBehavior(){
      return {
        top: 0
      }
    }
  })
  
  export default router