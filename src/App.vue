<template>
<div id="app">
    <el-container>
        <Sidebar />
        <el-main>
            <el-row>
                <el-col :span="12">
                    <Breadcrumb />
                </el-col>
                <el-col :offset="4" :span="8">
                    <el-row v-if="isLogged">
                        <UserInfo />
                    </el-row>
                </el-col>
            </el-row>
            <el-row>
                <router-view />
            </el-row>
        </el-main>
    </el-container>

</div>
</template>

<script>
import Sidebar from "./components/Sidebar.vue";
import Breadcrumb from "./components/Breadcrumb.vue";
import UserInfo from "./components/UserInfo.vue";

export default {
    name: "app",
    components: {
        Sidebar,
        Breadcrumb,
        UserInfo,
    },
    data() {
        return {
            global: this.$store.state,
            isLogged:false,
        };
    },
    mounted() {
        this.userLoggedIn()
        this.clearLocalStorage()        
    },
    methods: {
        userLoggedIn() {
            this.isLogged = localStorage && localStorage.userInfo;
        },
        clearLocalStorage() {
            if (localStorage != null) {
                localStorage.clear()
            }
        },
    }
};
</script>

<style>
#app {
    font-family: "Avenir", Helvetica, Arial, sans-serif;
    color: #2c3e50;
}

.container {
    max-width: 1200px;
    margin: auto;
}

.el-main {
    background: #eef1f4;
}

.el-row {
    margin-bottom: 20px;
}
</style>
