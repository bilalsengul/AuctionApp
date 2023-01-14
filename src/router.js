import Vue from 'vue'
import Router from 'vue-router'
import Home from './views/Home.vue'
import NewAuction from './views/NewAuction.vue'
import Profile from './views/Profile.vue'
import Auctions from './views/Auctions.vue'
import MyAuctions from './views/MyAuctions.vue'

Vue.use(Router)

export default new Router({
  routes: [
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
})
