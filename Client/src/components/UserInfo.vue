<template>
<div class="float-end">
    <el-button size="small" @click="handleLogout" class="mb-2">Logout</el-button>
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
    methods: {
        handleLogout() {
            this.global.loggedIn = false
            this.$router.push('/login')
        }
    }
}
</script>
