<template>
<div v-if="!isLogged">
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
        <el-tooltip class="item" effect="dark" content="Search" placement="top-start">
            <el-button v-model="searchIsActive" @click=" searchIsActive = !searchIsActive" circle> <i class="el-icon-search"></i> </el-button>
        </el-tooltip>
        <el-tooltip class="item" effect="dark" content="Add Item" placement="top-start">
            <el-button v-model="searchIsActive" @click="createItem" circle> <i class="el-icon-plus"></i> </el-button>
        </el-tooltip>
        <el-row>
            <el-col></el-col>
        </el-row>
        <el-card v-if="searchIsActive" class="col-7">

            <el-input class="col-3 p-2" placeholder="Search" @change="loadAllAuctions" v-model="search.keyword"></el-input>

            <el-switch class="p-3" v-model="search.searchInDescription" active-text="In Description">
            </el-switch>
            <el-switch class="p-3" v-model="search.exactMatch" active-text="Exact Match">
            </el-switch>
            <el-switch class="p-3" v-model="search.withExpired" active-text="With Expired">
            </el-switch>

            <el-checkbox label="Sort" v-model="isSortSwitch" @change="sortSwitch"></el-checkbox>
            <el-switch v-show="isSortSwitch" class="p-3" v-model="search.sortByPriceDescending" active-text="Desc" inactive-text="Asc">
            </el-switch>

            <el-button class="p-2 float-end m-3" @click="loadAllAuctions" type="primary" plain> Search </el-button>

        </el-card>

    </el-row>
    <el-row>
        <el-col class="p-3" :span="4" v-for="(auction, index) in auctions" :key="index">
            <!-- <el-row v-if="auction.visible"> -->
            <el-row>
                <el-card style="font-size: small;" :body-style="{ padding: '8px' }">
                    <b-img v-if="auction.image == null" thumbnail fluid src="https://jacksonholeartauction.com/media/images/art_large/ostermiller,_dan,_(1956-_),_empty_saddle,_bronze_7,_8_x_11_x_3.jpg"></b-img>
                    <b-img v-else thumbnail fluid :src="auction.image"></b-img>

                    <el-row>
                        <el-col></el-col>
                    </el-row>
                    <flip-countdown :labels="label" :deadline="toDate(auction.auctionEndDate)" labelSize="15px" countdownSize="20px"></flip-countdown>
                    <div style="padding: 14px; font-size: small;">

                        <b-list-group class="text-md pt-2">

                            <b-list-group-item><b>Name : </b>{{ auction.name }}</b-list-group-item>
                            <b-list-group-item><b>Descriptions : </b>{{ auction.description }}</b-list-group-item>
                            <b-list-group-item><b>Opening Price: </b>{{ auction.openingPrice }}</b-list-group-item>
                            <b-list-group-item><b>Current Price: </b>{{ auction.currentPrice }}</b-list-group-item>

                        </b-list-group>
                        <div class="bottom clearfix">
                            <div style="text-align:right;">
                                <b-button :to="'/auctions/' + auction.id" size="sm">
                                    <span>Get Details
                                        <b-avatar size="sm" icon="arrow-right" /></span>
                                </b-button>
                            </div>
                        </div>
                    </div>
                </el-card>
            </el-row>
        </el-col>
    </el-row>
    <el-footer class="footer">
        <el-button-group>
            <el-button :disabled="metaData.currentPage == 1" icon="el-icon-arrow-left" @click="previousPage">Previous Page</el-button>
            <el-button :disabled="((metaData.currentPage - 1)  * metaData.size) + metaData.count == metaData.total" @click="nextPage">Next Page <i class="el-icon-arrow-right"></i></el-button>
        </el-button-group>
    </el-footer>
</div>
</template>

<script>
import FlipCountdown from 'vue2-flip-countdown'
import moment from 'moment'

export default {
    name: "home",
    data() {
        return {
            global: this.$store.state,
            searchIsActive: false,
            currentDate: new Date(),
            auctions: [],
            auctionNumber: 0,
            options: [],
            value: [],
            select: "",
            input: "",
            max: null,
            isSortSwitch: false,
            min: 0,
            rawImage: [],
            metaData: [],
            isLogged: false,
            endDate: 1674585320,
            search: {
                keyword: "",
                exactMatch: false,
                searchInDescription: true,
                sortByPriceDescending: null,
                withExpired: true,
                currentPage: 1,
                size: 2,
            },
            label: {
                days: 'Day',
                hours: 'Hour',
                minutes: 'Min',
                seconds: 'Sec',
            },
        };
    },
    components: {
        FlipCountdown
    },
    watch: {
        "global.loggedIn": "loadAllAuctions"
    },
    mounted() {
        this.userLoggedIn()
        this.updateBreadcrumb()
        this.loadAllAuctions()

    },
    methods: {
        userLoggedIn() {
            this.isLogged = localStorage && localStorage.userInfo;
        },
        sortSwitch() {
            if (!this.isSortSwitch) {
                this.search.sortByPriceDescending = null
            } else {
                this.search.sortByPriceDescending = this.isSortSwitch
            }
        },
        nextPage() {
            this.search.currentPage = this.search.currentPage + 1
            this.loadAllAuctions()
        },
        previousPage() {
            this.search.currentPage = this.search.currentPage - 1
            this.loadAllAuctions()
        },
        toDate(epoch) {
            let date = epoch == null ? new Date() : new Date(epoch)
            return moment(date).format("yyyy-MM-DD HH:mm:ss");
        },
        updateBreadcrumb() {
            this.global.breadcrumbPath = [{
                path: "/",
                name: "Home"
            }];

        },
        createItem() {
            this.$router.push('/newAuction')
        },
        loadAllAuctions() {
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
.flip-card__top,
.flip-card__bottom,
.flip-card__back-bottom,
.flip-card__back::before,
.flip-card__back::after {
    color: #fff !important;
}

.flip-card__top-4digits,
.flip-card__bottom-4digits,
.flip-card__back-bottom-4digits,
.flip-card__back-4digits::before,
.flip-card__back-4digits::after {
    color: #fff !important;
}

.flip-card__top-4digits,
.flip-card__bottom-4digits,
.flip-card__back-bottom-4digits,
.flip-card__back-4digits::before,
.flip-card__back-4digits::after {
    color: rgb(156, 156, 156) !important;
}

.footer {

    position: fixed;
    left: 0;
    bottom: 0;
    width: 100%;
    text-align: center;
}

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
