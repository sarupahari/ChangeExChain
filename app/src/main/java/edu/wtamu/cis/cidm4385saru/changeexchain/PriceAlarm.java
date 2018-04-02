package edu.wtamu.cis.cidm4385saru.changeexchain;

import java.util.Date;
import java.util.UUID;

public class PriceAlarm {

    private UUID mId;
    private String mCurrencyCode;
    private int mPrice;
    private String mThreshold;

    public PriceAlarm() {
        this(UUID.randomUUID());
    }

    public PriceAlarm(UUID id) {
        mId = id;
    }

    public UUID getId() {
        return mId;
    }

    public String getCurrencyCode() {
        return mCurrencyCode;
    }

    public void setCurrencyCode(String mCurrencyCode) {
        this.mCurrencyCode = mCurrencyCode;
    }

    public int getPrice() {
        return mPrice;
    }

    public void setPrice(int mPrice) {
        this.mPrice = mPrice;
    }

    public String getThreshold() {
        return mThreshold;
    }

    public void setThreshold(String mThreshold) {
        this.mThreshold = mThreshold;
    }

}
