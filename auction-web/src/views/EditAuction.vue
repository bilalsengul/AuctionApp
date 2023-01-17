<template>
<div v-if="this.$store.state.loggedIn">
    <el-row v-if="this.auctionUpdated">
        <el-card class="box-card">
            <p style="text-align:center;">Your Auction has been updated!</p>
        </el-card>
    </el-row>
    <el-row v-else>
        <el-card class="box-card">

            <div class="image-preview" v-if="item.image" style="text-align: center;">
                <div class="image-preview">
                    <b-img  style="width: 300px;" thumbnail fluid :src="item.image" ></b-img>
                </div>
            </div>
            <b-list-group class="text-md p-3">
                <b-list-group-item>
                    <div class="file-upload-form">
                        Upload Image:
                        <input type="file" name="myFile" @change="previewImage" accept="image/*" />
                    </div>
                </b-list-group-item>
            </b-list-group>
            <b-list-group class="text-md p-3">
                <b-list-group-item>
                    <el-col class="column">
                        <el-form class="pt-3" label-position="left">
                            <el-form-item label="Item Name" label-pos label-width="120px">
                                <el-input v-model="item.name" placeholder="Item Name"></el-input>
                            </el-form-item>
                            <el-form-item label="Description" label-width="120px">
                                <el-input type="textarea" :rows="4" placeholder="Item Description" v-model="item.description"></el-input>
                            </el-form-item>
                            <el-form-item label="Opening Price" label-width="120px">
                                <el-input-number v-model="item.openingPrice" :precision="0" :step="10" :min="10"></el-input-number>
                            </el-form-item>
                            <el-form-item label="Min Auto Bid" label-width="120px">
                                <el-input-number v-model="item.minimumAutoBidPrice" :precision="0" :step="10" :min="10"></el-input-number>
                            </el-form-item>
                            <el-form-item label="End Date" label-width="120px">
                                <div class="block">
                                    <el-date-picker v-model="item.auctionEndDate" type="datetime" @change="dateToEpoch" placeholder="Select date and time" :picker-options="pickerOptions">
                                    </el-date-picker>
                                </div>
                            </el-form-item>
                        </el-form>

                    </el-col>
                </b-list-group-item>

            </b-list-group>

            <el-row v-if="!this.auctionUpdated" class="pt-3">
                <el-col :offset="9">
                    <el-button @click="handleEditAuction" type="primary" plain>Update Auction</el-button>
                </el-col>
            </el-row>
        </el-card>
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
    name: "editauction",
    data() {
        return {
            global: this.$store.state,
            onAuction: false,
            cratedDate: "",
            updatedDate: "",
            item: {
                name: "",
                image: "",
                description: "",
                openingPrice:0,
                auctionEndDate: "",
                onAuction:true,
                minimumAutoBidPrice:0
            },
            auctionUpdated: false,
            pickerOptions: {
                shortcuts: [{
                    text: 'Today',
                    onClick(picker) {
                        picker.$emit('pick', new Date());
                    }
                }, {
                    text: 'Quick Auction',
                    onClick(picker) {
                        const date = new Date();
                        date.setTime(date.getTime() + 300 * 1000);
                        picker.$emit('pick', date);
                    }
                }]
            },
        };
    },
    mounted() {
        this.isLoggedIn();
        this.updateData()
        this.updateBreadcrumb();
        this.loadAuction();
    },
    methods: { 
        isLoggedIn() {
            console.log('EditPage')
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
                    path: "/editAuction",
                    name: "Edit Auction"
                }
            ];
        },
        dateToEpoch(){          
            this.item.auctionEndDate = this.item.auctionEndDate.getTime();
            this.item.onAuction = true;
        },
        loadAuction() {
            let loading = this.$loading({
                    lock: true,
                    text: 'Loading',
                    spinner: 'el-icon-loading',
                    background: 'rgba(0, 0, 0, 0.7)'
                });
            axios({
                method: 'get',
                url: this.url + 'item/' + this.auction_id,
            }).then((response) => {
                this.item = response.data;
                this.updateBreadcrumb();
                loading.close()

            }).catch((err) => {
                loading.close()
                console.log('error', err.response);
                let errorMessage = err.response.data.message
                this.$notify.error({
                    title: 'Error',
                    dangerouslyUseHTMLString: true,
                    message: errorMessage
                });
            });;
        },
        handleEditAuction() {
            console.log("This is handle new auction");
            console.log(JSON.stringify(this.name));
            
            console.log(this.item)
            var url = this.global.apiurl + "item/" + this.auction_id;
            var body = this.item;
            let loading = this.$loading({
                    lock: true,
                    text: 'Loading',
                    spinner: 'el-icon-loading',
                    background: 'rgba(0, 0, 0, 0.7)'
                });
            axios({
                method: 'put',
                url: url,
                data: body,
            }).then((response) => {
                console.log(response);
                this.$router.push('/home')
                loading.close()
            }).catch((err) => {                
                loading.close()
                console.log('error', err.response);
                let errorMessage = err.response.data.message
                this.$notify.error({
                    title: 'Error',
                    dangerouslyUseHTMLString: true,
                    message:  errorMessage
                });
            });

            this.auctionUpdated = true;
        },

        previewImage: function (event) {
            var input = event.target;
            if (input.files && input.files[0]) {
                var reader = new FileReader();
                reader.onload = e => {
                    this.item.image = e.target.result;
                    console.log(this.item.image);
                };
                console.log(input.files[0]);
                reader.readAsDataURL(input.files[0]);

            }
        },
        async getImageRaw(id) {
            var url = this.global.apiurl + "images/getImageRaw/" + id;
            var response = await axios.get(url);
            this.rawImage = response.data;
        },
        async uploadImageRaw(newName) {
            console.log("image loaded");
            var url = this.global.apiurl + "images/uploadImageRaw/" + newName;
            var config = {
                headers: {
                    "Content-Type": "application/json"
                }
            };

            var response = await axios.post(
                url, {
                    image: this.image
                },
                config
            );
            console.log(this.image.length);
            console.log(JSON.stringify(response.data));
        }
    }
};
</script>

<style>
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
}

.box-card {
    width: 480px;
}
</style>
