import Vue from 'vue'
import Router from 'vue-router'
import Home from './views/Home.vue'
import NewAuction from './views/NewAuction.vue'
import EditAuction from './views/EditAuction.vue'
import Profile from './views/Profile.vue'
import AuctionDetail from './views/AuctionDetail.vue'
import Login from './views/Login.vue'

Vue.use(Router)


const routes = [
  {
    path: '/login',
    name: 'login',
    component: Login
  },
  {
    path: '/home',
    name: 'home',
    component: Home
  },
  {
    path: '/newAuction',
    name: 'newauction',
    component: NewAuction
  },  
  {
    path: '/editAuction/:id',
    name: 'editauction',
    component: EditAuction
  },
  {
    path: '/profile',
    name: 'profile',
    component: Profile
  },
  {
    path: '/auctionDetail/:id',
    name: 'auctionDetail',
    component: AuctionDetail
  },
]

const router = Vue.router = new Router({routes:routes});

export default router

