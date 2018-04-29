package edu.wtamu.cis.cidm4385saru.changeexchain.Classes;

import java.util.Date;
import java.util.UUID;

public class PriceAlarm {

    //TODO Change back to private
    private UUID mId;
    private String mCurrencyCode;
    private String mPrice;
    private String mThreshold;
    private boolean mEnabled;

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

    public String getPrice() {
        return mPrice;
    }

    public void setPrice(String mPrice) {
        this.mPrice = mPrice;
    }

    public String getThreshold() {
        return mThreshold;
    }

    public void setThreshold(String mThreshold) {
        this.mThreshold = mThreshold;
    }

    public boolean isEnabled() {
        return mEnabled;
    }

    public void setEnabled(boolean enabled) {
        mEnabled = enabled;
    }


    public static PriceAlarm createDefault(){
        PriceAlarm pa = new PriceAlarm();

        pa.setCurrencyCode("local");
        pa.setPrice("$0.00");
        pa.setEnabled(false);

        return pa;
    }

}
