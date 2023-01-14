<template>
<div>
    <el-aside width="230px">
        <el-menu background-color="#545c64" text-color="#fff" active-text-color="#fff" :router="true">
            <el-menu-item index="/">
                <i class="el-icon-menu"></i>
                <span>Home</span>
            </el-menu-item>
            <el-menu-item v-if="this.global.loggedIn" index="/newAuction">
                <i class="el-icon-setting"></i>
                <span>New Auction</span>
            </el-menu-item>
            <el-menu-item v-if="this.global.loggedIn" index="/myAuctions">
                <i class="el-icon-setting"></i>
                <span>My Auctions</span>
            </el-menu-item>
            <el-menu-item v-if="this.global.loggedIn" index="/profile">
                <i class="el-icon-user"></i>
                <span>Profile</span>
            </el-menu-item>
        </el-menu>
    </el-aside>
</div>
</template>

<script>
export default {
    name: "sidebar",
    data() {
        return {
            global: this.$store.state,
        };
    }
};
</script>

<style>
.logo-container {
    padding: 12px;
    background: #434a50;
    color: white;
    font-family: "Lucida Sans", "Lucida Sans Regular", "Lucida Grande",
        "Lucida Sans Unicode", Geneva, Verdana, sans-serif;
}

.el-aside {
    background: #545c64;
    color: white;
    min-height: 100vh;
}

.el-menu {
    border-right: 0 !important;
}
</style>
