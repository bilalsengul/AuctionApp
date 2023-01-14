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
                    <Login v-if="!global.loggedIn" />
                    <UserInfo v-else />
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
import Login from "./components/Login.vue";
import Breadcrumb from "./components/Breadcrumb.vue";
import UserInfo from "./components/UserInfo.vue";

export default {
    name: "app",
    components: {
        Login,
        Sidebar,
        Breadcrumb,
        UserInfo,
    },
    data() {
        return {
            global: this.$store.state
        };
    },
    mounted() {},
    methods: {

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
