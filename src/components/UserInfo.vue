<template>
<div>
    <el-row class="float-end">
        <el-button size="small" @click="handleLogout">Logout</el-button>
    </el-row>
</div>
</template>

<script>
export default {
    name: 'userinfo',
    data() {
        return {
            global: this.$store.state
        }
    },
    mounted() {},
    methods: {
        handleLogout() {
            this.global.loggedIn = false
        }
    }
}
</script>
