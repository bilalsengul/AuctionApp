import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    apiurl: "http://localhost:8080/",
    loggedIn: false,
    breadcrumbPath: [{ path: "/", name: "Home" }],
    userInfo: {},
    auctions: [],
    userId: "",
  },
  mutations: {

  },
  actions: {

  }
})
