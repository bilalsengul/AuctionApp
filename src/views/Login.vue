<template>
<div @keyup.enter="handleLogin">
    <el-col :span="10" class="p-2">
        <el-input v-model="username"   placeholder="Username" size="small"></el-input>
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
    mounted() {
        this.updateBreadcrumb()
    },
    methods: {
        updateBreadcrumb() {
            this.global.breadcrumbPath = [{
                path: "/login",
                name: "Login"
            }];

        },
        async handleLogin() {
            if (this.username == "" || this.password == "") {
                this.$notify.error({
                    title: 'Error',
                    message: 'Invalid credentials !'
                });
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
                    console.log("response", response)
                    this.global.userInfo = response.data
                    localStorage.userInfo = true
                    this.global.userId = response.data.id
                    localStorage.setItem("userInfo", JSON.stringify(response.data));
                    this.$router.push('/')

                })
                .catch((err) => {
                    console.log('error', err);
                    let errorMessage = err.response.data.message
                    this.$notify.error({
                        title: 'Error',
                        dangerouslyUseHTMLString: true,
                        message:  errorMessage
                        
                    });
                });
                console.log("localStorage",localStorage);

        }
    }
}
</script>
