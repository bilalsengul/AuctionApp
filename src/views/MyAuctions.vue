<template>
<div v-if="isLogged">

    <el-row>
        <el-col :span="4" v-for="(o, index) in auctionNumber" :key="o" :offset="index > 1 ? 1 : 1">
            <!-- <el-row v-if="auctions[index].visible"> -->
            <el-row>
                <el-card :body-style="{ padding: '0px' }">
                    <!-- <img class="preview" :src="rawImage[index]"/> -->
                    <div style="padding: 14px;">
                        <div style="text-align: center;">
                            <span>{{ auctions[index].item.name }}</span>
                        </div>
                        <!-- <div class="bottom clearfix">
                        <div style="text-align:left; padding:2px" End Time>
                        End Time:
                        <time class="time">{{ auctions[index].end_time }}</time>
                        </div>
                        <div style="text-align:left; padding:2px">
                        <span>Starting Bid: {{ auctions[index].starting_bid }}</span>
                        </div>
                        <div style="text-align:center;">
                        <router-link :to="'/auctions/' + auctions[index].id">View Auction</router-link>
                        </div>
                    </div> -->
                    </div>
                </el-card>
            </el-row>
        </el-col>
    </el-row>
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
    name: "home",
    data() {
        return {
            global: this.$store.state,

            currentDate: new Date(),
            auctions: [],
            auctionNumber: 0,
            options: [],
            value: [],
            rawImage: [],
            isLogged: false
        };
    },
    watch: {
        value: "loadViaCategory"
    },
    mounted() {        
        this.userLoggedIn()
        this.updateBreadcrumb();
        // this.loadCategories();
        this.loadMyAuctions();
        //this.getImageRaw();
    },
    methods: {    
        userLoggedIn() {
            this.isLogged = localStorage && localStorage.userInfo;
        }, 
        updateBreadcrumb() {
            this.global.breadcrumbPath = [{
                path: "/",
                name: "Home"
            }];
        },
        async loadMyAuctions() {
            console.log("MyAuctions");
            console.log(this.global.apiurl);
            axios({
                method: 'get',
                url: this.global.apiurl + 'item/' + "788934a4-0aa3-43ac-ae49-72b50e15919c",
                header: {
                    "x-user-id": "58174c6c-095e-4a89-a79c-992734d1c75a",
                }
            }).then((response) => {
                console.log(response);
                console.log(response.data.length);
                console.log(response.data);
                this.auctionNumber = response.data.length;
                this.auctions = response.data;
            }).catch((err) => {
                console.log('error', err.response);
                let errorMessage = err.response.data.message
                this.$notify.error({
                    title: 'Error',
                    dangerouslyUseHTMLString: true,
                    message:  errorMessage
                });
            });
            for (let i in this.auctions) {
                this.auctions[i].visible = true;
                // if (this.auctions[i].id > 39){
                //   console.log("call getImage " + this.auctions[i].id)
                //   this.getImageRaw(i)
                // }
            }
            this.getImageRaw();
        },

        async loadCategories() {
            var url = this.global.apiurl + "categories/all";

            var response = await axios.get(url);
            this.global.categories = response.data;
            for (let i in this.global.categories) {
                this.options.push({
                    value: this.global.categories[i].id,
                    label: this.global.categories[i].name
                });
            }
        },
        async getImageRaw() {

            for (let i in this.auctions) {
                //console.log("Auction : " + this.auctions[i].id)
                // if (this.auctions[i].id < 40) {
                //   //console.log("burger")
                //   this.rawImage.push(
                //     "https://jacksonholeartauction.com/media/images/art_large/ostermiller,_dan,_(1956-_),_empty_saddle,_bronze_7,_8_x_11_x_3.jpg"
                //   );
                // }
                // else {
                //console.log("load")
                var url = this.global.apiurl + "images/getImageRaw/" + this.auctions[i].id;
                var response = await axios.get(url);
                this.rawImage.push(
                    response.data);
                //}
            }
            //console.log("RawImage Lenth: " + this.rawImage.length)
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

.image-preview {
    font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
    padding: 20px;
}

img.preview {
    width: 200px;
    background-color: white;
    border: 1px solid #ddd;
    padding: 5px;
}
</style>
