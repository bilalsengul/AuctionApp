<template>
<div v-if="this.$store.state.loggedIn">
    <el-card class="w-25 m-auto">
        <b-container fluid>
            <b-row>
                <b-col offset-md="3">
                    <h1>Profile</h1>
                    <hr class="my-4" />
                </b-col>
            </b-row>
            <b-row align-h="center">

                <b-col cols="12" md="3" class="my-4">
                    <b-img-lazy thumbnail class="rounded" src="https://icon-library.com/images/default-user-icon/default-user-icon-8.jpg" alt="Defaul User Icon">
                    </b-img-lazy>
                </b-col>

                <b-col cols="12" md="9">
                    <b-tabs content-class="mt-3" card id="app">
                        <b-tab title="Info" active>
                            <b-list-group>
                                <b-list-group-item>
                                    <span class="p-1 mr-3">
                                        <b-avatar icon="person-badge" /></span>
                                    {{global.userInfo.username}}
                                </b-list-group-item>
                                <b-list-group-item>
                                    <span class="p-1 mr-3">
                                        <b-avatar icon="person" /></span>
                                    {{global.userInfo.name}} {{global.userInfo.surname}}
                                </b-list-group-item>
                                <b-list-group-item>
                                    <span class="p-1 mr-3">
                                        <b-avatar icon="at" /></span>
                                    {{global.userInfo.email}}
                                </b-list-group-item>
                            </b-list-group>
                            <br />
                        </b-tab>
                        <b-tab title="Roles">
                            <b-list-group>
                                <b-list-group-item v-for="item in this.global.userInfo.roles">
                                    <span class="p-1 mr-3">
                                        <b-avatar icon="nut" /></span>
                                    <span class="text-capitalize">{{item.toLowerCase()}}</span>
                                </b-list-group-item>
                            </b-list-group>
                        </b-tab>
                        <b-tab title="Bid Settings">
                            <b-list-group>
                                <b-list-group-item>
                                    <el-form class="pt-3" label-position="left">
                                        <el-form-item label="Min Auto Bid" label-width="120px">
                                            <el-input-number v-model="this.minimumAutoBidPrice" :precision="0" :step="10" :min="10"></el-input-number>
                                        </el-form-item>
                                        <el-form-item label="Auto Bid Percent" label-width="120px">
                                            <el-input-number v-model="this.minimumAutoBidPrice" :precision="0" :step="10" :min="10"></el-input-number>
                                        </el-form-item>
                                        <el-form-item label="Auto Bid Budget" label-width="120px">
                                            <el-input v-model="this.minimumAutoBidPrice" :precision="0" :step="10" :min="10"></el-input>
                                        </el-form-item>
                                    </el-form>
                                </b-list-group-item>
                            </b-list-group>
                            <el-col :offset="9" class="p-3">
                                <el-button @click="handleNewAuction" type="primary" plain>Update Bid Settings</el-button>
                            </el-col>
                        </b-tab>
                    </b-tabs>
                </b-col>
            </b-row>
        </b-container>
    </el-card>
</div>
<div v-else>
    <div>
        <b-alert show variant="danger">
            <h4 class="alert-heading">Oopss!</h4>
            <p>
                If you receive this error. Please try logging in. If you are logged in but continue to receive errors, you are not authorized to this area.
            </p>
            <hr>
            <p class="mb-0">
                Please contact the user admin for detailed information.
            </p>
        </b-alert>
    </div>
</div>
</template>

<script>
export default {
    name: 'profile',
    data() {
        return {
            global: this.$store.state,
            auctions: [],
            metaData: [],
            minimumBid: 0,
            search: {
                keyword: "",
                exactMatch: false,
                searchInDescription: true,
                sortByPriceDescending: null,
                withExpired: true,
                currentPage: 1,
                size: 2,
            },
        }
    },
    mounted() {
        this.isLoggedIn();
        this.updateBreadcrumb()
        this.loadAllAuctions()
    },
    methods: {
        isLoggedIn() {
            if (!this.global.loggedIn) {
                this.$router.push('/login')
            }
        },
        updateBreadcrumb() {
            this.global.breadcrumbPath = [{
                    path: "/home",
                    name: "Home"
                },
                {
                    path: "/profile",
                    name: "Profile"
                },
            ]
        },
        loadAllAuctions() {
            if (this.global.loggedIn) {
                const loading = this.$loading({
                    lock: true,
                    text: 'Loading',
                    spinner: 'el-icon-loading',
                    background: 'rgba(0, 0, 0, 0.7)'
                });
                axios({
                    method: 'post',
                    url: this.global.apiurl + "item/search",
                    data: this.search,
                    header: {
                        "x-user-id": this.global.userId,
                    },
                }).then((response) => {
                    console.log(response)
                    this.auctions = response.data.result;
                    this.metaData = response.data;
                    loading.close();
                }).catch((err) => {
                    console.log('error', err.response);
                    let errorMessage = err.response.data.message
                    this.$notify.error({
                        title: 'Error',
                        dangerouslyUseHTMLString: true,
                        message: errorMessage
                    });
                });

                console.log(this.auctions);
                this.global.auctions = this.auctions;
            }

        },

    }
}
</script>
