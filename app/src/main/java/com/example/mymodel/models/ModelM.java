package com.example.mymodel.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ModelM implements Parcelable, Serializable {
    @SerializedName("id")
    @Expose
    private int modelId;

    @SerializedName("title")
    @Expose
    private String modelTitle;

    @SerializedName("price")
    @Expose
    private Double modelPrice;

    @SerializedName("description")
    @Expose
    private String modelDescription;

    @SerializedName("image")
    @Expose
    private String modelImage;

    @SerializedName("count")
    @Expose
    private int quantity;

    public ModelM(int modelId, String modelTitle, Double modelPrice, String modelDescription, String modelImage, int quantity) {
        this.modelId = modelId;
        this.modelTitle = modelTitle;
        this.modelPrice = modelPrice;
        this.modelDescription = modelDescription;
        this.modelImage = modelImage;
        this.quantity = quantity;
    }

    protected ModelM(Parcel in) {
        modelId = in.readInt();
        modelTitle = in.readString();
        if (in.readByte() == 0) {
            modelPrice = null;
        } else {
            modelPrice = in.readDouble();
        }
        modelDescription = in.readString();
        modelImage = in.readString();
        quantity = in.readInt();
    }

    public static final Creator<ModelM> CREATOR = new Creator<ModelM>() {
        @Override
        public ModelM createFromParcel(Parcel in) {
            return new ModelM(in);
        }

        @Override
        public ModelM[] newArray(int size) {
            return new ModelM[size];
        }
    };


    public String getModelTitle() {
        return modelTitle;
    }
    public Double getModelPrice() {
        return modelPrice;
    }
    public String getModelDescription() {
        return modelDescription;
    }

    public String getModelImage() {
        return modelImage;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(modelId);
        dest.writeString(modelTitle);
        if (modelPrice == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(modelPrice);
        }
        dest.writeString(modelDescription);
        dest.writeString(modelImage);
        dest.writeInt(quantity);
    }

    @Override
    public String toString() {
        return "ModelM{" +
                "modelId=" + modelId +
                ", modelTitle='" + modelTitle + '\'' +
                ", modelPrice=" + modelPrice +
                ", modelDescription='" + modelDescription + '\'' +
                ", modelImage='" + modelImage + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}

