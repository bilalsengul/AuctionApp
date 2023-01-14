<template>
<div>
    <el-col :span="10" class="p-2">
        <el-input v-model="username" placeholder="Username" size="small"></el-input>
    </el-col>
    <el-col :span="10" class="p-2">
        <el-input v-model="password" placeholder="Password" size="small" show-password></el-input>
    </el-col>
    <el-col :span="4" class="p-2">
        <el-button size="small" @click="handleLogin">Login</el-button>
    </el-col>
    <el-col :span="8" class="h-25 d-inline-block p-3" v-if="showWarning">
        <b-alert show variant="danger">Invalid credentials !</b-alert>
    </el-col>
</div>
</template>

<script>
import axios from "axios";

export default {
    name: 'login',
    data() {
        return {
            global: this.$store.state,

            username: "",
            password: "",

            showWarning: false,
        }
    },
    methods: {
        async handleLogin() {
            if (this.username == "" || this.password == "") {
                this.showWarning = true
                return
            }

            console.log("Login");
            let data = {
                username: this.username,
                password: this.password
            };
            console.log(this.global.apiurl);

            axios({
                method: 'post',
                url: this.global.apiurl + 'global/login',
                data: data,
            }).then((response) => {
                console.log(response)
                this.global.id = response.data.id
                axios.defaults.headers.common['x-user-id'] = this.global.id
                this.global.userInfo = response.data
                this.global.loggedIn = true
            }).catch((err) => {
                this.showWarning = true
            });

        }
    }
}
</script>
