package com.wasir.droid.core.data.util

import com.google.gson.Gson
import com.wasir.droid.core.data.model.ImageResponse

object MockData {

    private const val mockData = "{\n" +
            "\t\"total\": 28734,\n" +
            "\t\"totalHits\": 500,\n" +
            "\t\"hits\": [{\n" +
            "\t\t\t\"id\": 794978,\n" +
            "\t\t\t\"pageURL\": \"https://pixabay.com/illustrations/book-dog-fairy-tales-child-kid-794978/\",\n" +
            "\t\t\t\"type\": \"illustration\",\n" +
            "\t\t\t\"tags\": \"book, dog, fairy tales\",\n" +
            "\t\t\t\"previewURL\": \"https://cdn.pixabay.com/photo/2015/06/02/12/59/book-794978_150.jpg\",\n" +
            "\t\t\t\"previewWidth\": 150,\n" +
            "\t\t\t\"previewHeight\": 84,\n" +
            "\t\t\t\"webformatURL\": \"https://pixabay.com/get/g7322361455064cd13f668df9eaa3055d58d5082fab37ec3374b6f44acd6de60a24c97861f61443ede410f60405296162_640.jpg\",\n" +
            "\t\t\t\"webformatWidth\": 640,\n" +
            "\t\t\t\"webformatHeight\": 360,\n" +
            "\t\t\t\"largeImageURL\": \"https://pixabay.com/get/g4644565f2dc6ec887ec59d8aab70ea339a06be165975211ef8fcdf2fd53a0f12de0a2f1b30092023665f1b54ffe5e7645b5488278c6cb9d662ce91243d214d6d_1280.jpg\",\n" +
            "\t\t\t\"imageWidth\": 3000,\n" +
            "\t\t\t\"imageHeight\": 1688,\n" +
            "\t\t\t\"imageSize\": 1628041,\n" +
            "\t\t\t\"views\": 884833,\n" +
            "\t\t\t\"downloads\": 360307,\n" +
            "\t\t\t\"collections\": 2742,\n" +
            "\t\t\t\"likes\": 3192,\n" +
            "\t\t\t\"comments\": 614,\n" +
            "\t\t\t\"user_id\": 51581,\n" +
            "\t\t\t\"user\": \"0fjd125gk87\",\n" +
            "\t\t\t\"userImageURL\": \"https://cdn.pixabay.com/user/2020/04/09/10-26-16-313_250x250.jpg\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"id\": 2785074,\n" +
            "\t\t\t\"pageURL\": \"https://pixabay.com/photos/puppy-pet-canine-dog-animal-lying-2785074/\",\n" +
            "\t\t\t\"type\": \"photo\",\n" +
            "\t\t\t\"tags\": \"puppy, pet, canine\",\n" +
            "\t\t\t\"previewURL\": \"https://cdn.pixabay.com/photo/2017/09/25/13/12/puppy-2785074_150.jpg\",\n" +
            "\t\t\t\"previewWidth\": 150,\n" +
            "\t\t\t\"previewHeight\": 99,\n" +
            "\t\t\t\"webformatURL\": \"https://pixabay.com/get/g38919c2400789dc521ff1ed56c4637980c803115788d355981870a918311e4c5ab5b26c002eaee712178d9f965ff1f2cc289af5bf966121dc03ac237577adce6_640.jpg\",\n" +
            "\t\t\t\"webformatWidth\": 640,\n" +
            "\t\t\t\"webformatHeight\": 426,\n" +
            "\t\t\t\"largeImageURL\": \"https://pixabay.com/get/ge7054c888640da8c1eed5376bb1c87ccb456f576d0ec04766d599af26169c3a4e7d1c79c8fe53c4217b711b36d1cc6b71df9345ff2ded8137bc47c79dd1b6dbd_1280.jpg\",\n" +
            "\t\t\t\"imageWidth\": 3943,\n" +
            "\t\t\t\"imageHeight\": 2628,\n" +
            "\t\t\t\"imageSize\": 2235576,\n" +
            "\t\t\t\"views\": 900769,\n" +
            "\t\t\t\"downloads\": 585125,\n" +
            "\t\t\t\"collections\": 1640,\n" +
            "\t\t\t\"likes\": 1574,\n" +
            "\t\t\t\"comments\": 241,\n" +
            "\t\t\t\"user_id\": 6087762,\n" +
            "\t\t\t\"user\": \"PicsbyFran\",\n" +
            "\t\t\t\"userImageURL\": \"https://cdn.pixabay.com/user/2020/05/08/15-39-26-890_250x250.jpg\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"id\": 1728494,\n" +
            "\t\t\t\"pageURL\": \"https://pixabay.com/vectors/dog-animal-domestic-animal-doggie-1728494/\",\n" +
            "\t\t\t\"type\": \"vector/svg\",\n" +
            "\t\t\t\"tags\": \"dog, animal, domestic animal\",\n" +
            "\t\t\t\"previewURL\": \"https://cdn.pixabay.com/photo/2016/10/10/14/13/dog-1728494_150.png\",\n" +
            "\t\t\t\"previewWidth\": 150,\n" +
            "\t\t\t\"previewHeight\": 134,\n" +
            "\t\t\t\"webformatURL\": \"https://pixabay.com/get/g3f0cd395ba41d224252c4c5c1fe95371880560c01ffebdce2a49f32297dbeb73a142b9ec1268ea91638ec0c0bb863a9198d41065f75a92578785e7bc9020d4d2_640.png\",\n" +
            "\t\t\t\"webformatWidth\": 640,\n" +
            "\t\t\t\"webformatHeight\": 573,\n" +
            "\t\t\t\"largeImageURL\": \"https://pixabay.com/get/gdca0e2ac504955da50f9b07970d1b1c97aaf0f5278e78c8f25ce515f3523c7ecd600eddef8c633cdba5b03f78ad4bfb225f80d53a9cc7c8aa37e36b74bbb445a_1280.png\",\n" +
            "\t\t\t\"imageWidth\": 1920,\n" +
            "\t\t\t\"imageHeight\": 1719,\n" +
            "\t\t\t\"imageSize\": 1387975,\n" +
            "\t\t\t\"views\": 357457,\n" +
            "\t\t\t\"downloads\": 207730,\n" +
            "\t\t\t\"collections\": 1317,\n" +
            "\t\t\t\"likes\": 1164,\n" +
            "\t\t\t\"comments\": 262,\n" +
            "\t\t\t\"user_id\": 268986,\n" +
            "\t\t\t\"user\": \"Gorkhs\",\n" +
            "\t\t\t\"userImageURL\": \"https://cdn.pixabay.com/user/2016/06/09/18-08-22-28_250x250.png\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"id\": 1903313,\n" +
            "\t\t\t\"pageURL\": \"https://pixabay.com/photos/puppy-dog-pet-collar-dog-collar-1903313/\",\n" +
            "\t\t\t\"type\": \"photo\",\n" +
            "\t\t\t\"tags\": \"puppy, dog, pet\",\n" +
            "\t\t\t\"previewURL\": \"https://cdn.pixabay.com/photo/2016/12/13/05/15/puppy-1903313_150.jpg\",\n" +
            "\t\t\t\"previewWidth\": 150,\n" +
            "\t\t\t\"previewHeight\": 99,\n" +
            "\t\t\t\"webformatURL\": \"https://pixabay.com/get/ge95f965e6e7e001b6fcd1bdef6e62598e17773d9cdd3ef16b3a4874ccc0db1bf38bf4d6880206a0853bdc2053665224d265562398201338c8e92d60ab08f23cc_640.jpg\",\n" +
            "\t\t\t\"webformatWidth\": 640,\n" +
            "\t\t\t\"webformatHeight\": 426,\n" +
            "\t\t\t\"largeImageURL\": \"https://pixabay.com/get/gc7b9499d1a144573a149f6f786cabb7e6c903d883a58d0f88112984f52299470e566191f081a2562548cfa915c628e841526c4cf7b64524a405af5758a16f377_1280.jpg\",\n" +
            "\t\t\t\"imageWidth\": 5760,\n" +
            "\t\t\t\"imageHeight\": 3840,\n" +
            "\t\t\t\"imageSize\": 6406019,\n" +
            "\t\t\t\"views\": 964007,\n" +
            "\t\t\t\"downloads\": 622241,\n" +
            "\t\t\t\"collections\": 1052,\n" +
            "\t\t\t\"likes\": 1084,\n" +
            "\t\t\t\"comments\": 160,\n" +
            "\t\t\t\"user_id\": 3194556,\n" +
            "\t\t\t\"user\": \"3194556\",\n" +
            "\t\t\t\"userImageURL\": \"\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"id\": 3277416,\n" +
            "\t\t\t\"pageURL\": \"https://pixabay.com/photos/dog-pet-canine-animal-fur-snout-3277416/\",\n" +
            "\t\t\t\"type\": \"photo\",\n" +
            "\t\t\t\"tags\": \"dog, pet, canine\",\n" +
            "\t\t\t\"previewURL\": \"https://cdn.pixabay.com/photo/2018/03/31/06/31/dog-3277416_150.jpg\",\n" +
            "\t\t\t\"previewWidth\": 150,\n" +
            "\t\t\t\"previewHeight\": 99,\n" +
            "\t\t\t\"webformatURL\": \"https://pixabay.com/get/g8e986370921e38385a24914755bbaf59af119fe962cf3cae03735686e8c729265ce8740745769d44b589177e153413d83883f9afdac6ba183cc25f724362a6af_640.jpg\",\n" +
            "\t\t\t\"webformatWidth\": 640,\n" +
            "\t\t\t\"webformatHeight\": 426,\n" +
            "\t\t\t\"largeImageURL\": \"https://pixabay.com/get/g751606e6cb65fe21353e13997ed0883fab167ce3e25df5c22effd6a637beda9479588362c75c0d8a55c06e60dc2582364bee07a0ed71ab84dfc93563dd31f60a_1280.jpg\",\n" +
            "\t\t\t\"imageWidth\": 3939,\n" +
            "\t\t\t\"imageHeight\": 2626,\n" +
            "\t\t\t\"imageSize\": 1623766,\n" +
            "\t\t\t\"views\": 392121,\n" +
            "\t\t\t\"downloads\": 262726,\n" +
            "\t\t\t\"collections\": 1084,\n" +
            "\t\t\t\"likes\": 892,\n" +
            "\t\t\t\"comments\": 133,\n" +
            "\t\t\t\"user_id\": 9868721,\n" +
            "\t\t\t\"user\": \"Vizslafotozas\",\n" +
            "\t\t\t\"userImageURL\": \"https://cdn.pixabay.com/user/2018/08/19/23-35-04-514_250x250.png\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"id\": 4415649,\n" +
            "\t\t\t\"pageURL\": \"https://pixabay.com/photos/corgi-dog-pet-canine-rain-animal-4415649/\",\n" +
            "\t\t\t\"type\": \"photo\",\n" +
            "\t\t\t\"tags\": \"corgi, dog, pet\",\n" +
            "\t\t\t\"previewURL\": \"https://cdn.pixabay.com/photo/2019/08/19/07/45/corgi-4415649_150.jpg\",\n" +
            "\t\t\t\"previewWidth\": 150,\n" +
            "\t\t\t\"previewHeight\": 99,\n" +
            "\t\t\t\"webformatURL\": \"https://pixabay.com/get/g9547189eec1c83fc1f669b7618b09b075c267fa9829b8940478a46c843e9d5ef69e9ebb5e482e99f422c313cbc22cd3035976a13b212767d6bb2e2a5fa7193e5_640.jpg\",\n" +
            "\t\t\t\"webformatWidth\": 640,\n" +
            "\t\t\t\"webformatHeight\": 423,\n" +
            "\t\t\t\"largeImageURL\": \"https://pixabay.com/get/g97155e1392754fff27de2fa3dc890a90f7db936d213e7a48b492ada9a574e25e1403314b7d39ec17ea41af08a68a15f95995342cd6cd4d0ea01cb654468d6695_1280.jpg\",\n" +
            "\t\t\t\"imageWidth\": 5130,\n" +
            "\t\t\t\"imageHeight\": 3396,\n" +
            "\t\t\t\"imageSize\": 3129143,\n" +
            "\t\t\t\"views\": 480541,\n" +
            "\t\t\t\"downloads\": 306797,\n" +
            "\t\t\t\"collections\": 791,\n" +
            "\t\t\t\"likes\": 1004,\n" +
            "\t\t\t\"comments\": 174,\n" +
            "\t\t\t\"user_id\": 8934889,\n" +
            "\t\t\t\"user\": \"huoadg5888\",\n" +
            "\t\t\t\"userImageURL\": \"\"\n" +
            "\t\t}\n" +
            "\t]\n" +
            "}"

    fun getMockImageResponse(): ImageResponse {
        return Gson().fromJson(mockData, ImageResponse::class.java)
    }

}