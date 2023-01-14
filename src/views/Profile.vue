<template>
<div v-if="this.global.loggedIn">
    <b-container fluid>
        <b-row>
            <b-col offset-md="1">
                <h1>Profile</h1>
                <hr class="my-4" />
            </b-col>
        </b-row>
        <b-row align-h="center">
            <b-col cols="4" md="1" class="my-4">
                <b-img-lazy thumbnail class="rounded" src="https://icon-library.com/images/default-user-icon/default-user-icon-8.jpg" alt="Defaul User Icon">
                </b-img-lazy>
            </b-col>

            <b-col cols="12" md="11">
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
                </b-tabs>
            </b-col>
        </b-row>
    </b-container>
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
            global: this.$store.state
        }
    },
    mounted() {
        this.updateBreadcrumb()
    },
    methods: {
        updateBreadcrumb() {
            this.global.breadcrumbPath = [{
                    path: "/",
                    name: "Home"
                },
                {
                    path: "/profile",
                    name: "Profile"
                },
            ]
        }
    }
}
</script>
