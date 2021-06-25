package com.android.demo.chachishafashi

import android.media.Image

class ProductInfo{

    var text: String?= null

    constructor(){}

    constructor(text: String?, image: String?) {
        this.text = text
        this.image = image
    }

    var image: String?= null
}

