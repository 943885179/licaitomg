package greendao.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import chen.com.myaccount.bean.Outaccount;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "OUTACCOUNT".
*/
public class OutaccountDao extends AbstractDao<Outaccount, Long> {

    public static final String TABLENAME = "OUTACCOUNT";

    /**
     * Properties of entity Outaccount.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Money = new Property(1, double.class, "money", false, "MONEY");
        public final static Property Time = new Property(2, String.class, "time", false, "TIME");
        public final static Property Type = new Property(3, String.class, "type", false, "TYPE");
        public final static Property Address = new Property(4, String.class, "address", false, "ADDRESS");
        public final static Property Mark = new Property(5, String.class, "mark", false, "MARK");
    };


    public OutaccountDao(DaoConfig config) {
        super(config);
    }
    
    public OutaccountDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"OUTACCOUNT\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"MONEY\" REAL NOT NULL ," + // 1: money
                "\"TIME\" TEXT NOT NULL ," + // 2: time
                "\"TYPE\" TEXT NOT NULL ," + // 3: type
                "\"ADDRESS\" TEXT," + // 4: address
                "\"MARK\" TEXT);"); // 5: mark
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"OUTACCOUNT\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Outaccount entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindDouble(2, entity.getMoney());
        stmt.bindString(3, entity.getTime());
        stmt.bindString(4, entity.getType());
 
        String address = entity.getAddress();
        if (address != null) {
            stmt.bindString(5, address);
        }
 
        String mark = entity.getMark();
        if (mark != null) {
            stmt.bindString(6, mark);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Outaccount entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindDouble(2, entity.getMoney());
        stmt.bindString(3, entity.getTime());
        stmt.bindString(4, entity.getType());
 
        String address = entity.getAddress();
        if (address != null) {
            stmt.bindString(5, address);
        }
 
        String mark = entity.getMark();
        if (mark != null) {
            stmt.bindString(6, mark);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Outaccount readEntity(Cursor cursor, int offset) {
        Outaccount entity = new Outaccount( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getDouble(offset + 1), // money
            cursor.getString(offset + 2), // time
            cursor.getString(offset + 3), // type
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // address
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5) // mark
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Outaccount entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setMoney(cursor.getDouble(offset + 1));
        entity.setTime(cursor.getString(offset + 2));
        entity.setType(cursor.getString(offset + 3));
        entity.setAddress(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setMark(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Outaccount entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Outaccount entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
