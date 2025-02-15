// Made by GRPTeam202205
import { createApp } from 'vue'
import App from './App.vue'
import i18n from './locales/i18n'
import router from './router/index'
import { library } from '@fortawesome/fontawesome-svg-core'
import {fas} from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'
import 'bootstrap'
import './axios.js'
import 'bootstrap/dist/css/bootstrap.min.css'
import '@fortawesome/fontawesome-free/css/all.css'

library.add(fas)

createApp(App).component('fa',FontAwesomeIcon).use(router).use(i18n).mount('#app')
