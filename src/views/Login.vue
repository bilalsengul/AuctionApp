<template>
<div @keyup.enter="handleLogin">
    <el-main>
        <el-row>
            <el-card class="box-card" style="width:360px !important">
                <div>
                    <el-row>
                        <el-col :span="6" :offset="9" class="mb-3">
                            <b-img-lazy thumbnail rounded="circle"  src="https://icon-library.com/images/default-user-icon/default-user-icon-8.jpg" alt="Defaul User Icon">
                            </b-img-lazy>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="4" :offset="3" >
                            <span>Login </span>
                        </el-col>
                        <el-col :span="12" :offset="2">
                            <el-input v-model="username" placeholder="Username" size="small"></el-input>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="4" :offset="3">
                            <span>Password </span>
                        </el-col>
                        <el-col :span="12" :offset="2">
                            <el-input v-model="password" placeholder="Password" size="small" show-password></el-input>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="6" :offset="10">
                            <el-button size="x-large" @click="handleLogin">Login</el-button>
                        </el-col>
                    </el-row>
                </div>
            </el-card>
        </el-row>
    </el-main>

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
        this.isLoggedIn()
    },
    methods: {
        isLoggedIn() {
            if (this.global && this.global.loggedIn) {
                this.$router.push('/home')
            }
        },
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
                    this.global.userId = response.data.id
                    this.global.loggedIn = true;
                    console.log(this.global);
                    this.$router.push('/home')

                })
                .catch((err) => {
                    console.log('error', err);
                    let errorMessage = err.response.data.message
                    this.$notify.error({
                        title: 'Error',
                        dangerouslyUseHTMLString: true,
                        message: errorMessage

                    });
                });
            console.log("localStorage", localStorage);

        }
    }
}
</script>
