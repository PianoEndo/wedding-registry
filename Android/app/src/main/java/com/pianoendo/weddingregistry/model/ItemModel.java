package com.pianoendo.weddingregistry.model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class ItemModel {
    private String itemName;
    private String memo;
    private String price;

    // empty constructor required when using realtime database
    public ItemModel() {
    }

    public ItemModel(String itemName, String memo, String price) {
        this.itemName = itemName;
        this.memo = memo;
        this.price = price;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("itemName",itemName);
        result.put("memo",memo);
        result.put("price",price);

        return result;
    }
}
