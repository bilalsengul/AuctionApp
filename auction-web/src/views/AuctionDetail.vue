<template>
<div v-if="this.$store.state.loggedIn">

    <el-row>
        <el-card class="box-card">
            <div style="text-align: center;">
                <b-img v-if="item.image == null" style="width: 300px;" thumbnail fluid src="https://jacksonholeartauction.com/media/images/art_large/ostermiller,_dan,_(1956-_),_empty_saddle,_bronze_7,_8_x_11_x_3.jpg"></b-img>
                <b-img v-else thumbnail style="width: 300px;" fluid :src="item.image"></b-img>
            </div>
            <b-card :header="item.name" class="mt-2 ">
                <p class="card-text mt-2">
                    {{ item.description }}
                </p>
                <el-form>
                    <el-row class="pb-4" style="padding: 14px; font-size: small;">
                        <div>
                            <b-list-group class="text-md">
                                <b-list-group-item><b>Opening Price: </b>{{ item.openingPrice }}</b-list-group-item>
                                <b-list-group-item><b>Current Price : </b>{{ item.currentPrice }}</b-list-group-item>
                            </b-list-group>
                        </div>

                    </el-row>
                    <el-row style="display: flex;justify-content: flex-end;">
                        <el-col class="p-2">
                            <el-switch v-model="autobid" active-text="Auto-Bid">
                            </el-switch>
                        </el-col>
                        <el-col class="p-2">
                            <el-form-item>
                                <el-input-number v-model="bidPrice" :precision="0" :step="10" :min="item.currentPrice"></el-input-number>
                            </el-form-item>
                        </el-col>
                        <el-col class="p-2">
                            <el-button :disabled="item.currentPrice >= bidPrice || !item.onAuction" variant="outline-secondary" @click="makeBid">
                                <span v-if="item.onAuction">Bid</span>
                                <span v-else>Expired</span>
                            </el-button>
                        </el-col>
                    </el-row>
                    <el-row>
                        <b-card v-if="bidsSize != 0" no-body header="History">
                            <b-list-group flush v-for="index  in bidsSize">
                                <b-list-group-item :active="index == 1" v-if="itemDetail.userInfos[index-1]">{{ itemDetail.userInfos[index-1].name + " " + itemDetail.userInfos[index-1].surname }} : <b>{{ itemDetail.bids[index-1].price }}</b></b-list-group-item>
                            </b-list-group>
                        </b-card>
                    </el-row>
                    <el-row>
                        <div class="pt-4" v-if="autobid">
                            <el-card>
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
                                <el-col :offset="6" class="p-3">
                                    <el-button @click="handleNewAuction" type="primary" plain>Update Bid Settings</el-button>
                                </el-col>
                            </el-card>
                        </div>
                    </el-row>
                </el-form>
            </b-card>
        </el-card>
    </el-row>
</div>
<div v-else>
    <el-row>
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
    </el-row>
</div>
</template>

<script>
export default {
    name: "auctionDetail",
    data() {
        return {
            global: this.$store.state,
            autobid: false,
            url: "",
            item: {
                name: "",
                description: "",
                openingPrice: 0,
                auctionEndDate: "",
            },
            itemDetail: {
                bids: [],
                userInfos: [],
            },
            iterator: [],
            rawImage: [],
            bidsSize: 0,
            bidPrice: 0,

        };
    },
    watch: {
        "$route.params.id": "updateData"
        // newBid: "minBid",
        // starting_bid: "minBid"
    },
    beforeMount() {
        if (this.global.loggedIn) {
            this.updateData();
            this.loadAuction();
            this.loadBids();
        }
    },
    computed: {
        myAuction: function () {
            // if (this.seller_id == this.global.userInfo.id) return false;
            // else return true;
            return true;
        },
    },
    mounted() {
        this.isLoggedIn()
    },
    methods: {
        isLoggedIn() {
            if (!this.global.loggedIn) {
                this.$router.push('/login')
            }
        },
        updateData() {
            console.log('DetailPage')
            this.auction_id = this.$route.params.id;
            this.url = this.global.apiurl;
        },
        updateBreadcrumb() {
            this.global.breadcrumbPath = [{
                    path: "/home",
                    name: "Home"
                },
                {
                    path: "/auctionDetail/" + this.auction_id,
                    name: "AuctionDetail / " + this.item.name
                }
            ];
        },
        loadBids() {
            axios({
                method: 'get',
                url: this.url + 'item/' + this.auction_id + '/bids',
            }).then((response) => {
                // .slice(1).slice(-3)
                this.iterator = response.data.reverse();
                console.log(this.iterator)
                this.itemDetail.bids = [];
                this.itemDetail.userInfos = [];
                if (this.iterator.length > 0) {
                    this.iterator.forEach(element => {
                        if (element.userId != "undefined") {
                            this.itemDetail.bids.push(element)
                            axios({
                                method: 'get',
                                url: this.url + 'user/' + element.userId,
                            }).then((response) => {
                                console.log('After', element.userId)
                                this.bidsSize = this.itemDetail.bids.length;
                                this.itemDetail.userInfos.push(response.data);
                            }).catch((err) => {
                                console.log('error', err.response);
                                let errorMessage = err.response.data.message
                                this.$notify.error({
                                    title: 'Error',
                                    dangerouslyUseHTMLString: true,
                                    message: errorMessage
                                });
                            });
                        }

                    });
                }
            }).catch((err) => {
                console.log('error', err.response);
                let errorMessage = err.response.data.message
                this.$notify.error({
                    title: 'Error',
                    dangerouslyUseHTMLString: true,
                    message: errorMessage
                });
            });
        },
        loadAuction() {
            axios({
                method: 'get',
                url: this.url + 'item/' + this.auction_id,
            }).then((response) => {
                this.item = response.data;
                this.bidPrice = this.item.openingPrice;
                this.updateBreadcrumb();

            }).catch((err) => {
                console.log('error', err.response);
                let errorMessage = err.response.data.message
                this.$notify.error({
                    title: 'Error',
                    dangerouslyUseHTMLString: true,
                    message: errorMessage
                });
            });;
        },
        makeBid() {
            let data = {
                bidPrice: this.bidPrice
            };
            axios({
                method: 'put',
                url: this.url + 'item/' + this.auction_id + '/bid-on',
                header: {
                    "x-user-id": this.global.userId,
                },
                data: data
            }).then(() => {
                this.loadBids();
            }).catch((err) => {
                console.log('error', err.response);
                let errorMessage = err.response.data.message
                this.$notify.error({
                    title: 'Error',
                    dangerouslyUseHTMLString: true,
                    message: errorMessage
                });
            });

        }

    }
};
</script>

<style>
.image {
    width: 50%;
    display: block;
    margin-left: auto;
    margin-right: auto;
}

.text {
    font-size: 14px;
}

.item {
    margin-bottom: 18px;
}

.clearfix:before,
.clearfix:after {
    display: table;
    content: "";
}

.clearfix:after {
    clear: both;
}

.box-card {
    /* width: 480px; */
    margin: 0 auto;
    /* Added */
    float: none;
    /* Added */
    margin-bottom: 0 auto;
    /* Added */
}

.bg-white {
    background: #e0e0e0;
}

.file-upload-form,
.image-preview {
    font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
    padding: 20px;
}

img.preview {
    width: 200px;
    background-color: white;
    border: 1px solid #ddd;
    padding: 5px;
    display: block;
    margin-left: auto;
    margin-right: auto;
}
</style>
