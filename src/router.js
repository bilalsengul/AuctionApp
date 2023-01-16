import Vue from 'vue'
import Router from 'vue-router'
import Home from './views/Home.vue'
import NewAuction from './views/NewAuction.vue'
import Profile from './views/Profile.vue'
import Auctions from './views/Auctions.vue'
import MyAuctions from './views/MyAuctions.vue'
import Login from './views/Login.vue'

const isNotAuthRoute = route => route.path.indexOf('/login') === -1
const isLogged = () => localStorage.userInfo != null

const beforeEach =  (to, from, next) => {
  if (isNotAuthRoute(to) && !isLogged()) {
    next('/login')
  } else {
    next()
  }
}

Vue.use(Router)


const routes = [
  {
    path: '/login',
    name: 'login',
    component: Login
  },
  {
    path: '/',
    name: 'home',
    component: Home
  },
  {
    path: '/newAuction',
    name: 'newauction',
    component: NewAuction
  },
  {
    path: '/profile',
    name: 'profile',
    component: Profile
  },
  {
    path: '/auctions/:id',
    name: 'auctions',
    component: Auctions
  },
  {
    path: '/myAuctions',
    name: 'myAuctions',
    component: MyAuctions
  }
]

const router = Vue.router = new Router({routes:routes});

router.beforeEach(beforeEach);

export default router

