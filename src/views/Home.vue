<template>
<div v-if="!this.global.loggedIn">
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
<div v-else>
    <el-row>
        <el-col class="p-3" :span="4" v-for="(auction, index) in auctions" :key="index">
            <!-- <el-row v-if="auction.visible"> -->
            <el-row>
                <el-card style="font-size: small;" :body-style="{ padding: '8px' }">
                    <b-img v-if="true" thumbnail fluid src="https://jacksonholeartauction.com/media/images/art_large/ostermiller,_dan,_(1956-_),_empty_saddle,_bronze_7,_8_x_11_x_3.jpg" alt="Image 2"></b-img>
                    <!-- <img class="preview" :src="rawImage[index]" /> -->
                    <div style="padding: 14px; font-size: small;" >
                        <b-list-group class="text-md">
                            <b-list-group-item><b>Name : </b>{{ auction.name }}</b-list-group-item>
                            <b-list-group-item><b>Descriptions : </b>{{ auction.description }}</b-list-group-item>
                            <b-list-group-item><b>Opening Price: </b>{{ auction.openingPrice }}</b-list-group-item>
                            <b-list-group-item><b>Current Price : </b>{{ auction.currentPrice }}</b-list-group-item>
                        </b-list-group>
                        <div class="bottom clearfix">
                            <div style="text-align:right;">
                                <b-button :to="'/auctions/' + auction.id" size="sm">Get Details
                                    <b-avatar size="sm" icon="arrow-right" />
                                </b-button>
                            </div>
                        </div>
                    </div>
                </el-card>
            </el-row>
        </el-col>
    </el-row>
</div>
</template>

<script>
export default {
    name: "home",
    data() {
        return {
            global: this.$store.state,

            currentDate: new Date(),
            auctions: [],
            auctionNumber: 0,
            options: [],
            value: [],
            select: "",
            input: "",
            max: null,
            min: 0,
            rawImage: [],
            search: {
                keyword:"deneme",
                exactMatch:false,
                searchInDescription:true,
                currentPage: 1,
                size: 10
            },
        };
    },
    watch: {
        "global.loggedIn": "loadAllAuctions"
    },
    mounted() {
        this.updateBreadcrumb();
        this.loadAllAuctions();
    },
    methods: {
        updateBreadcrumb() {
            this.global.breadcrumbPath = [{
                path: "/",
                name: "Home"
            }];
        },
        async loadAllAuctions() {
            console.log("Home Page 1");
            console.log(this.global.apiurl);

            axios({
                method: 'post',
                url: this.global.apiurl + "item/search",
                data: this.search,
                header: {
                    "x-user-id": "58174c6c-095e-4a89-a79c-992734d1c75a",
                    "Content-type": "application/json"
                }
            }).then((response) => {
                console.log(response.data);
                //this.auctions.push(response.data);
                this.auctions = response.data;
            }).catch((err) => {
                console.log(err);
            });


            // axios({
            //     method: 'get',
            //     url: this.global.apiurl + 'item/' + "6734f3ed-a65e-4560-9c51-3cb01adcee77",
            //     header: {
            //         "x-user-id": "58174c6c-095e-4a89-a79c-992734d1c75a",
            //         "Content-type": "application/json"
            //     }
            // }).then((response) => {
            //     this.auctions.push(response.data);
            // }).catch((err) => {
            //     console.log(err);
            // });

            console.log(this.auctions);
            // for (let i in this.auctions) {
            //   this.auctions[i].remaining = this.getRemainingTime(
            //     this.auctions[i].end_time
            //   );
            //   if (this.auctions[i].remaining == "Expired")
            //     this.auctions[i].visible = false;
            //   else {
            //     if (this.global.loggedIn) {
            //       if (this.global.userInfo.id == this.auctions[i].seller_id) {
            //         this.auctions[i].visible = false;
            //       } else this.auctions[i].visible = true;
            //     } else this.auctions[i].visible = true;
            //   }
            // }
            this.global.auctions = this.auctions;
            this.getImageRaw()
        },

        getRemainingTime(end_time) {
            var seconds = (new Date(end_time) - this.calcTime(6)) / 1000;
            if (seconds < 0) return "Expired";

            var sec_num = parseInt(seconds);
            var days = Math.floor(sec_num / 86400);
            var hours = Math.floor((sec_num - days * 86400) / 3600);
            var minutes = Math.floor((sec_num - days * 86400 - hours * 3600) / 60);
            var seconds = sec_num - days * 86400 - hours * 3600 - minutes * 60;

            if (days < 10) {
                days = "0" + days;
            }
            if (hours < 10) {
                hours = "0" + hours;
            }
            if (minutes < 10) {
                minutes = "0" + minutes;
            }
            if (seconds < 10) {
                seconds = "0" + seconds;
            }
            return days + "d " + hours + "h " + minutes + "m";
        },
        calcTime(offset) {
            var d = new Date();
            var utc = d.getTime() + d.getTimezoneOffset() * 60000;
            return new Date(utc + 3600000 * offset);
        },
        searchViaPrice() {
            console.log(this.select)
            if (this.min == 0 && this.max == 0) {
                this.loadAllAuctions()
                return
            }
            this.callSearch("auctions/searchAuctionViaPrice/" + this.min + "/" + this.max)
        },
        searchVia() {
            console.log(this.select)
            if (this.input == "") {
                this.loadAllAuctions()
                return;
            }
            if (this.select == "Category") {
                this.callSearch("auctions/searchAuctionViaCategory/" + this.input)
            } else if (this.select == "Country") {
                this.callSearch("auctions/searchAuctionViaCountry/" + this.input)
            } else if (this.select == "Text") {
                this.callSearch("auctions/searchAuctionViaFreeText/" + this.input)
            }
        },
        async callSearch(param) {
            console.log(param)

            var url = this.global.apiurl + param;

            var response = await axios.get(url);
            this.auctionNumber = response.data.length;
            this.auctions = response.data;

            for (let i in this.auctions) {
                this.auctions[i].visible = false;
                this.auctions[i].remaining = this.getRemainingTime(this.auctions[i].end_time);
                if (this.auctions[i].remaining == "Expired")
                    this.auctions[i].visible = false;
                else {
                    if (this.global.loggedIn) {
                        if (this.global.userInfo.id == this.auctions[i].seller_id) {
                            this.auctions[i].visible = false;
                        } else this.auctions[i].visible = true;
                    } else this.auctions[i].visible = true;
                    console.log(JSON.stringify(this.auctions[i]))
                }
            }
            this.global.auctions = this.auctions;
            this.getImageRaw()
        },
        async getImageRaw() {
            this.rawImage = [];
            for (let i in this.auctions) {
                console.log("Auction : " + this.auctions[i].id)
                if (true) {
                    console.log("burger")
                    this.rawImage.push(
                        "https://jacksonholeartauction.com/media/images/art_large/ostermiller,_dan,_(1956-_),_empty_saddle,_bronze_7,_8_x_11_x_3.jpg"
                    );
                    console.log(this.rawImage)
                } else {
                    console.log("load")
                    var url = this.global.apiurl + "images/getImageRaw/" + this.auctions[i].id;
                    var response = await axios.get(url);
                    this.rawImage.push(
                        response.data);
                }
            }
            console.log("RawImage Lenth: " + this.rawImage.length)
            console.log("RawImage Lenth: " + this.rawImage)
            console.log("RawImage Lenth: " + this.auctions)
        },
    }
};
</script>

<style>
.time {
    font-size: 13px;
    color: #999;
}

.bottom {
    margin-top: 14px;
    line-height: 12px;
}

.button {
    padding: 0;
    float: center;
}

.image {
    width: 50%;
    display: block;
    margin-left: auto;
    margin-right: auto;
}

.clearfix:before,
.clearfix:after {
    display: table;
    content: "";
}

.clearfix:after {
    clear: both;
}

.el-select .el-input {
    width: 110px;
}

.input-with-select .el-input-group__prepend {
    background-color: #fff;
}

.image-preview {
    font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
    padding: 20px;
}

img.preview {
    width: 200px;
    height: 200px;
    background-color: white;
    border: 1px solid #ddd;
    /* padding: 5px; */
    display: block;
    /* margin-left: auto;
  margin-right: auto; */
}
</style>
