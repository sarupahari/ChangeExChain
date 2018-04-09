package edu.wtamu.cis.cidm4385saru.changeexchain;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import edu.wtamu.cis.cidm4385saru.changeexchain.database.ChangeExChDbSchema;
import edu.wtamu.cis.cidm4385saru.changeexchain.database.ChangeExChainBaseHelper;
import edu.wtamu.cis.cidm4385saru.changeexchain.database.UserCursorWrapper;
import edu.wtamu.cis.cidm4385saru.changeexchain.database.ChangeExChDbSchema.UserTable;

import static edu.wtamu.cis.cidm4385saru.changeexchain.database.ChangeExChDbSchema.UserTable.Cols.*;

/**
 * Created by sarup on 4/8/2018.
 */

public class UserLab {
    private static UserLab sUserLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static UserLab get(Context context) {
        if (sUserLab == null) {
            sUserLab = new UserLab(context);
        }
        return sUserLab;
    }

    private UserLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new ChangeExChainBaseHelper(mContext)
                .getWritableDatabase();

    }

    public void addUser (User u) {
        ContentValues values = getContentValues(u);
        mDatabase.insert(ChangeExChDbSchema.UserTable.NAME, null, values);
    }

//    public List<User> getUsers() {
//        List<User> users = new ArrayList<>();
//        UserCursorWrapper cursor = queryUsers(null, null);
//        try {
//            cursor.moveToFirst();
//            while (!cursor.isAfterLast()) {
//                users.add(cursor.getUser());
//                cursor.moveToNext();
//            }
//        } finally {
//            cursor.close();
//        }
//        return users;
//    }

    public User getUser(UUID id) {
        UserCursorWrapper cursor = queryUsers(
                UserTable.Cols.UUID + " = ?",
                new String[]{id.toString()}
        );
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getUser();
        } finally {
            cursor.close();
        }
    }

    public void updateUser(User user) {
        String uuidString = user.getId().toString();
        ContentValues values = getContentValues(user);
        mDatabase.update(UserTable.NAME, values,
                UserTable.Cols.UUID + " = ?",
                new String[]{uuidString});
    }

    private UserCursorWrapper queryUsers(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                UserTable.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null  // orderBy
        );
        return new UserCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(User user) {
        ContentValues values = new ContentValues();
        values.put(UUID, user.getId().toString());
        values.put(USERNAME, user.getUsername());
        values.put(PASSWORD, user.getPassword());
        return values;
    }
}
