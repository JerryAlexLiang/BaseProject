package liang.com.baseproject.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import liang.com.baseproject.mine.entity.ReadLaterBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "READ_LATER_BEAN".
*/
public class ReadLaterBeanDao extends AbstractDao<ReadLaterBean, String> {

    public static final String TABLENAME = "READ_LATER_BEAN";

    /**
     * Properties of entity ReadLaterBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, String.class, "id", true, "ID");
        public final static Property Title = new Property(1, String.class, "title", false, "TITLE");
        public final static Property Author = new Property(2, String.class, "author", false, "AUTHOR");
        public final static Property Link = new Property(3, String.class, "link", false, "LINK");
        public final static Property Time = new Property(4, long.class, "time", false, "TIME");
        public final static Property ChapterName = new Property(5, String.class, "chapterName", false, "CHAPTER_NAME");
    }


    public ReadLaterBeanDao(DaoConfig config) {
        super(config);
    }
    
    public ReadLaterBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"READ_LATER_BEAN\" (" + //
                "\"ID\" TEXT PRIMARY KEY NOT NULL ," + // 0: id
                "\"TITLE\" TEXT," + // 1: title
                "\"AUTHOR\" TEXT," + // 2: author
                "\"LINK\" TEXT," + // 3: link
                "\"TIME\" INTEGER NOT NULL ," + // 4: time
                "\"CHAPTER_NAME\" TEXT);"); // 5: chapterName
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"READ_LATER_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, ReadLaterBean entity) {
        stmt.clearBindings();
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(1, id);
        }
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(2, title);
        }
 
        String author = entity.getAuthor();
        if (author != null) {
            stmt.bindString(3, author);
        }
 
        String link = entity.getLink();
        if (link != null) {
            stmt.bindString(4, link);
        }
        stmt.bindLong(5, entity.getTime());
 
        String chapterName = entity.getChapterName();
        if (chapterName != null) {
            stmt.bindString(6, chapterName);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, ReadLaterBean entity) {
        stmt.clearBindings();
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(1, id);
        }
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(2, title);
        }
 
        String author = entity.getAuthor();
        if (author != null) {
            stmt.bindString(3, author);
        }
 
        String link = entity.getLink();
        if (link != null) {
            stmt.bindString(4, link);
        }
        stmt.bindLong(5, entity.getTime());
 
        String chapterName = entity.getChapterName();
        if (chapterName != null) {
            stmt.bindString(6, chapterName);
        }
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    @Override
    public ReadLaterBean readEntity(Cursor cursor, int offset) {
        ReadLaterBean entity = new ReadLaterBean( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // title
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // author
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // link
            cursor.getLong(offset + 4), // time
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5) // chapterName
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, ReadLaterBean entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setTitle(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setAuthor(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setLink(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setTime(cursor.getLong(offset + 4));
        entity.setChapterName(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
     }
    
    @Override
    protected final String updateKeyAfterInsert(ReadLaterBean entity, long rowId) {
        return entity.getId();
    }
    
    @Override
    public String getKey(ReadLaterBean entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(ReadLaterBean entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
