package edu.wtamu.cis.cidm4385saru.changeexchain;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.UUID;

import edu.wtamu.cis.cidm4385saru.changeexchain.database.ChangeExChDbSchema;
import edu.wtamu.cis.cidm4385saru.changeexchain.database.ChangeExChainBaseHelper;
import edu.wtamu.cis.cidm4385saru.changeexchain.database.UserSettingsCursorWrapper;

import static edu.wtamu.cis.cidm4385saru.changeexchain.database.ChangeExChDbSchema.UserSettingsTable.Cols.USERNAME;
import static edu.wtamu.cis.cidm4385saru.changeexchain.database.ChangeExChDbSchema.UserSettingsTable.Cols.TIMEFORMAT;
import static edu.wtamu.cis.cidm4385saru.changeexchain.database.ChangeExChDbSchema.UserSettingsTable.Cols.CURRENCYPREFERENCE;
import static edu.wtamu.cis.cidm4385saru.changeexchain.database.ChangeExChDbSchema.UserSettingsTable.Cols.COLORMODE;

public class UserSettingLab {

    private static UserSettingLab sUserSettingLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static UserSettingLab get(Context context) {
        if (sUserSettingLab == null) {
            sUserSettingLab = new UserSettingLab(context);
        }

        return sUserSettingLab;
    }

    private UserSettingLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new ChangeExChainBaseHelper(mContext)
                .getWritableDatabase();

    }

    public void addUserSetting(UserSetting uS) {
        ContentValues values = getContentValues(uS);
        mDatabase.insert(ChangeExChDbSchema.UserSettingsTable.NAME, null, values);
    }

    public UserSetting getUserSetting(UUID id) {
        UserSettingsCursorWrapper cursor = queryUserSettings(
                ChangeExChDbSchema.UserSettingsTable.Cols.UUID + " = ?",
                new String[]{id.toString()}
        );
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getUserSettings();
        } finally {
            cursor.close();
        }
    }

    public void updateUserSetting(UserSetting userSetting) {
        String uuidString = userSetting.getId().toString();
        ContentValues values = getContentValues(userSetting);
        mDatabase.update(ChangeExChDbSchema.UserSettingsTable.NAME, values,
                ChangeExChDbSchema.UserSettingsTable.Cols.UUID + " = ?",
                new String[]{uuidString});
    }

    private UserSettingsCursorWrapper queryUserSettings(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                ChangeExChDbSchema.UserSettingsTable.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null  // orderBy
        );
        return new UserSettingsCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(UserSetting userSetting) {
        ContentValues values = new ContentValues();
        values.put(ChangeExChDbSchema.UserSettingsTable.Cols.UUID, userSetting.getId().toString());
        values.put(USERNAME, userSetting.getUserName());
        values.put(TIMEFORMAT, userSetting.getTimeFormat());
        values.put(CURRENCYPREFERENCE, userSetting.getCurrencyPreference());
        values.put(COLORMODE, userSetting.getColorMode());
        return values;
    }
}
