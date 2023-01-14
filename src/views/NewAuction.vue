<template>
<div v-if="this.global.loggedIn">
    <el-row v-if="this.auctionCreated">
        <el-card class="box-card">
            <p style="text-align:center;">Your Auction has been created!</p>
        </el-card>
    </el-row>
    <el-row v-else>
        <el-col class="column" :span="12">
            <el-form>
                <el-form-item label="Item Name" label-width="120px">
                    <el-input v-model="item.name"></el-input>
                </el-form-item>
                <el-form-item label="Description" label-width="120px">
                    <el-input type="textarea" :rows="4" placeholder="Item Description" v-model="item.description"></el-input>
                </el-form-item>
                <el-form-item label="Opening Price" label-width="120px">
                    <el-input-number v-model="item.openingPrice" :precision="1" :step="0.1" :min="0"></el-input-number>
                </el-form-item>
                <el-form-item label="End Date" label-width="120px">
                    <el-date-picker type="date" placeholder="Pick a date" v-model="item.auctionEndDate" style="width: 100%;"></el-date-picker>
                    <el-time-picker placeholder="Pick a time" v-model="item.auctionEndDate" style="width: 100%;"></el-time-picker>
                </el-form-item>
            </el-form>
            <div>
                <div class="file-upload-form">
                    Upload an image file:
                    <input type="file" name="myFile" @change="previewImage" accept="image/*" />
                </div>
                <!-- <div class="image-preview" v-if="imagePath.length > 0"> -->
                <div class="image-preview">
                    <img class="preview" :src="item.imagePath" />
                </div>
            </div>
        </el-col>
    </el-row>

    <el-row v-if="!this.auctionCreated">
        <el-col :offset="9">
            <el-button type="primary" @click="handleNewAuction">Create Auction</el-button>
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
    name: "newauction",
    data() {
        return {
            global: this.$store.state,
            onAuction: false,
            cratedDate: "",
            updatedDate: "",

            item: {
                name: "",
                imagePath: "C:\Users\AnastasiaBayraktar\Pictures\Screenshots\response.png",
                description: "",
                openingPrice: 0,
                auctionEndDate: "",
            },
            imageLoaded: false,
            auctionCreated: false
        };
    },
    mounted() {
        this.updateBreadcrumb();
    },
    watch: {
        imagePath: "imagePathLoaded"
    },
    methods: {
        updateBreadcrumb() {
            this.global.breadcrumbPath = [{
                    path: "/",
                    name: "Home"
                },
                {
                    path: "/newAuction",
                    name: "New Auction"
                }
            ];
        },
        async handleNewAuction() {
            console.log("This is handle new auction");
            console.log(JSON.stringify(this.name));

            var url = this.global.apiurl + "item";
            var body = this.item;

            axios({
                method: 'post',
                url: url,
                data: body,
            }).then((response) => {
                console.log(response);
            }).catch((err) => {
                console.log(err);
            });

            var url = this.global.apiurl + "image/upload";
            var body = this.imagePath;

            axios({
                method: 'post',
                url: url,
                data: body,
            }).then((response) => {
                console.log(response);
            }).catch((err) => {
                console.log(err);
            });

            // this.uploadImageRaw(response.data.id);

            this.auctionCreated = true;
        },

        previewImage: function (event) {
            var input = event.target;
            if (input.files && input.files[0]) {
                var reader = new FileReader();
                reader.onload = e => {
                    this.imagePath = e.target.result;
                    console.log(this.imagePath);
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
        imagePathLoaded() {
            this.imageLoaded = true;
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
                    imagePath: this.imagePath
                },
                config
            );
            console.log(this.imagePath.length);
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
