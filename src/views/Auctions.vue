<template>
<div>
    <el-row>
        <el-card class="box-card">
            <b-img v-if="true" thumbnail fluid src="https://jacksonholeartauction.com/media/images/art_large/ostermiller,_dan,_(1956-_),_empty_saddle,_bronze_7,_8_x_11_x_3.jpg" alt="Image 2"></b-img>
            <b-img v-else thumbnail fluid :src="rawImage" alt="Image 2"></b-img>
            <!-- <img v-else class="preview" :src="rawImage" /> -->
            <b-card :header="this.item.name" class="mt-2 ">
                <p class="card-text mt-2">
                    {{ item.description }}
                </p>
                <el-form>
                    <el-row>
                        <div style="padding: 14px; font-size: small;">
                            <b-list-group class="text-md">
                                <b-list-group-item><b>Opening Price: </b>{{ item.openingPrice }}</b-list-group-item>
                                <b-list-group-item><b>Current Price : </b>{{ item.currentPrice }}</b-list-group-item>
                            </b-list-group>
                        </div>
                    </el-row>
                    <el-row style="display: flex;justify-content: flex-end;padding-right: 20px;">
                        <el-col :span="10">
                            <el-switch v-model="autobid" active-text="Auto-Bid">
                            </el-switch>
                        </el-col>
                        <el-col :span="14">
                            <el-form-item>
                                <el-input-number v-model="item.currentPrice" :precision="1" :step="0.1" :min="item.openingPrice"></el-input-number>
                            </el-form-item>
                        </el-col>
                        <el-col :span="2">
                            <el-button variant="outline-secondary">Bid</el-button>
                        </el-col>
                    </el-row>
                    <el-row>
                        <b-card no-body header="History">
                            <b-list-group flush>
                                <b-list-group-item  active>Bilal Sengul : <b>100 ₺</b></b-list-group-item>
                                <b-list-group-item >Ahmet Akın : <b>90 ₺</b></b-list-group-item>
                                <b-list-group-item >Bilal Sengul : <b>80 ₺</b></b-list-group-item>
                            </b-list-group>
                        </b-card>
                    </el-row>

                </el-form>

            </b-card>
        </el-card>
    </el-row>
</div>
</template>

<script>
// import axios from "axios";

export default {
    name: "auctions",
    data() {
        return {
            global: this.$store.state,
            autobid: false,
            item: {
                name: "",
                description: "",
                openingPrice: 0,
                auctionEndDate: "",
            },
            rawImage: []
        };
    },
    watch: {
        "$route.params.id": "updateData"
        // newBid: "minBid",
        // starting_bid: "minBid"
    },
    mounted() {
        this.loadAuction();
        this.updateData();
        this.updateBreadcrumb();
        // this.getImageRaw();
    },
    computed: {
        myAuction: function () {
            // if (this.seller_id == this.global.userInfo.id) return false;
            // else return true;
            return true;
        },
    },
    methods: {
        updateData() {
            this.auction_id = this.$route.params.id;
            //this.bid.amount = this.starting_bid
        },
        updateBreadcrumb() {
            this.global.breadcrumbPath = [{
                    path: "/",
                    name: "Home"
                },
                {
                    path: "/auctions/" + this.auction_id,
                    name: "Auctions/" + this.item.name
                }
            ];
        },
        findAuctionFromGlobal() {
            for (let i in this.global.auctions) {
                if (this.global.auctions[i].id == this.auction_id) {

                    this.item = this.global.auctions[i];
                    break;
                }
            }
        },
        async loadAuction() {
            axios.defaults.headers.common["x-user-id"] = this.global.id;
            console.log("MyAuctions");
            console.log(this.global.apiurl);
            axios({
                method: 'get',
                url: this.global.apiurl + 'item/' + "6734f3ed-a65e-4560-9c51-3cb01adcee77",
            }).then((response) => {
                console.log("Test Page");
                console.log(response.data);
                this.item = response.data;
            }).catch((err) => {
                console.log(err);
            });

            // this.auction_obj.remaining = this.getRemainingTime(this.auction_obj.end_time)
            // this.minBid();
        },
        // async minBid() {
        //   if (this.auction_obj.highest_bid_id == null) {
        //     console.log("No bids " + this.auction_obj.starting_bid);
        //     this.starting_bid = this.auction_obj.starting_bid;
        //   } else {
        //     var url =
        //       this.global.apiurl + "bids/models/" + this.auction_obj.highest_bid_id;

        //     var response = await axios.get(url);

        //     console.log(JSON.stringify(response.data));
        //     this.starting_bid = response.data[0].amount;
        //   }
        // },
        // calcTime(offset) {
        //   var d = new Date();
        //   var utc = d.getTime() + d.getTimezoneOffset() * 60000;
        //   return new Date(utc + 3600000 * offset);
        // },
        // makeNewBid() {
        //   this.bid.time = this.calcTime("+6");
        //   this.bid.bidder_id = this.global.userInfo.id;
        //   this.bid.auction_id = this.auction_obj.id;

        //   console.log(this.bid.time);
        //   console.log(this.bid.bidder_id);
        //   console.log(this.bid.auction_id);
        //   console.log(this.bid.amount);
        //   this.createNewBid();
        // },
        // async createNewBid() {
        //   var url = this.global.apiurl + "bids/newBid";
        //   var body = this.bid;
        //   var config = { headers: { "Content-Type": "application/json" } };

        //   var response = await axios.post(url, body, config);

        //   console.log(JSON.stringify(response.data));
        //   this.newBid = response.data;
        //   console.log("New Bid " + this.newBid.id);
        //   this.auction_obj.bid_count++;

        //   url =
        //     this.global.apiurl +
        //     "auctions/newBid/" +
        //     this.auction_obj.id +
        //     "/" +
        //     this.newBid.id +
        //     "/" +
        //     this.auction_obj.bid_count;

        //   this.auction_obj.highest_bid_id = this.newBid.id;

        //   response = await axios.patch(url);

        //   console.log(JSON.stringify(response.data));
        //   this.minBid();
        // },
        // async disableAuction() {
        //   var url =
        //     this.global.apiurl + "auctions/disableAuction/" + this.auction_obj.id;

        //   var response = await axios.patch(url);

        //   console.log(JSON.stringify(response.data));
        // },
        // changeStartingBid() {
        //   console.log(this.bid.amount);
        //   this.updateStartingBid()
        // },
        // async updateStartingBid() {
        //   var url =
        //     this.global.apiurl +
        //     "auctions/updateStartingBid/" +
        //     this.auction_obj.id +
        //     "/" +
        //     this.bid.amount;

        //   this.auction_obj.starting_bid = this.bid.amount;

        //   var response = await axios.patch(url);
        //   console.log(JSON.stringify(response.data));
        //   this.minBid();
        // },
        // getRemainingTime(end_time){
        //   var seconds = (new Date(end_time) - this.calcTime(6))/1000
        //   if(seconds < 0) return "Expired"

        //   var sec_num = parseInt(seconds)
        //   var days    = Math.floor(sec_num / 86400)
        //   var hours   = Math.floor((sec_num - (days * 86400)) / 3600);
        //   var minutes = Math.floor((sec_num - (days * 86400) - (hours * 3600)) / 60);
        //   var seconds = sec_num - (days * 86400) - (hours * 3600) - (minutes * 60);

        //   if (days    < 10) {days    = "0"+days;}
        //   if (hours   < 10) {hours   = "0"+hours;}
        //   if (minutes < 10) {minutes = "0"+minutes;}
        //   if (seconds < 10) {seconds = "0"+seconds;}
        //   return days+'d '+hours+'h '+minutes+'m';
        // },

        // async getImageRaw() {
        //   var url = this.global.apiurl + "images/getImageRaw/" + this.auction_id;
        //   var response = await axios.get(url);
        //   this.rawImage = response.data
        // },
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
