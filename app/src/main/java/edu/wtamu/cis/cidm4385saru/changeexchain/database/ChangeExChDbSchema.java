package edu.wtamu.cis.cidm4385saru.changeexchain.database;

/**
 * Created by sarup on 4/1/2018.
 */

public class ChangeExChDbSchema {

        public static final class PriceAlarmTable {
            public static final String NAME = "timer";

                public static final class Cols {
                public static final String UUID = "uuid";
                public static final String CURRENCYCODE = "currencyCode";
                public static final String PRICE = "price";
                public static final String THRESHOLD = "threshold";
            }
        }
}

