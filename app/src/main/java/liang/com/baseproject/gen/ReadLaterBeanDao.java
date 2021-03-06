package liang.com.baseproject.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import java.util.List;
import liang.com.baseproject.entity.TagsBeanListConvert;

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
        public final static Property Title = new Property(0, String.class, "title", true, "TITLE");
        public final static Property Author = new Property(1, String.class, "author", false, "AUTHOR");
        public final static Property Link = new Property(2, String.class, "link", false, "LINK");
        public final static Property Time = new Property(3, long.class, "time", false, "TIME");
        public final static Property ChapterName = new Property(4, String.class, "chapterName", false, "CHAPTER_NAME");
        public final static Property SuperChapterName = new Property(5, String.class, "superChapterName", false, "SUPER_CHAPTER_NAME");
        public final static Property EnvelopePic = new Property(6, String.class, "envelopePic", false, "ENVELOPE_PIC");
        public final static Property Desc = new Property(7, String.class, "desc", false, "DESC");
        public final static Property TagsBeanList = new Property(8, String.class, "tagsBeanList", false, "TAGS_BEAN_LIST");
    }

    private final TagsBeanListConvert tagsBeanListConverter = new TagsBeanListConvert();

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
                "\"TITLE\" TEXT PRIMARY KEY NOT NULL UNIQUE ," + // 0: title
                "\"AUTHOR\" TEXT," + // 1: author
                "\"LINK\" TEXT," + // 2: link
                "\"TIME\" INTEGER NOT NULL ," + // 3: time
                "\"CHAPTER_NAME\" TEXT," + // 4: chapterName
                "\"SUPER_CHAPTER_NAME\" TEXT," + // 5: superChapterName
                "\"ENVELOPE_PIC\" TEXT," + // 6: envelopePic
                "\"DESC\" TEXT," + // 7: desc
                "\"TAGS_BEAN_LIST\" TEXT);"); // 8: tagsBeanList
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"READ_LATER_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, ReadLaterBean entity) {
        stmt.clearBindings();
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(1, title);
        }
 
        String author = entity.getAuthor();
        if (author != null) {
            stmt.bindString(2, author);
        }
 
        String link = entity.getLink();
        if (link != null) {
            stmt.bindString(3, link);
        }
        stmt.bindLong(4, entity.getTime());
 
        String chapterName = entity.getChapterName();
        if (chapterName != null) {
            stmt.bindString(5, chapterName);
        }
 
        String superChapterName = entity.getSuperChapterName();
        if (superChapterName != null) {
            stmt.bindString(6, superChapterName);
        }
 
        String envelopePic = entity.getEnvelopePic();
        if (envelopePic != null) {
            stmt.bindString(7, envelopePic);
        }
 
        String desc = entity.getDesc();
        if (desc != null) {
            stmt.bindString(8, desc);
        }
 
        List tagsBeanList = entity.getTagsBeanList();
        if (tagsBeanList != null) {
            stmt.bindString(9, tagsBeanListConverter.convertToDatabaseValue(tagsBeanList));
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, ReadLaterBean entity) {
        stmt.clearBindings();
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(1, title);
        }
 
        String author = entity.getAuthor();
        if (author != null) {
            stmt.bindString(2, author);
        }
 
        String link = entity.getLink();
        if (link != null) {
            stmt.bindString(3, link);
        }
        stmt.bindLong(4, entity.getTime());
 
        String chapterName = entity.getChapterName();
        if (chapterName != null) {
            stmt.bindString(5, chapterName);
        }
 
        String superChapterName = entity.getSuperChapterName();
        if (superChapterName != null) {
            stmt.bindString(6, superChapterName);
        }
 
        String envelopePic = entity.getEnvelopePic();
        if (envelopePic != null) {
            stmt.bindString(7, envelopePic);
        }
 
        String desc = entity.getDesc();
        if (desc != null) {
            stmt.bindString(8, desc);
        }
 
        List tagsBeanList = entity.getTagsBeanList();
        if (tagsBeanList != null) {
            stmt.bindString(9, tagsBeanListConverter.convertToDatabaseValue(tagsBeanList));
        }
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    @Override
    public ReadLaterBean readEntity(Cursor cursor, int offset) {
        ReadLaterBean entity = new ReadLaterBean( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // title
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // author
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // link
            cursor.getLong(offset + 3), // time
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // chapterName
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // superChapterName
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // envelopePic
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // desc
            cursor.isNull(offset + 8) ? null : tagsBeanListConverter.convertToEntityProperty(cursor.getString(offset + 8)) // tagsBeanList
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, ReadLaterBean entity, int offset) {
        entity.setTitle(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setAuthor(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setLink(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setTime(cursor.getLong(offset + 3));
        entity.setChapterName(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setSuperChapterName(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setEnvelopePic(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setDesc(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setTagsBeanList(cursor.isNull(offset + 8) ? null : tagsBeanListConverter.convertToEntityProperty(cursor.getString(offset + 8)));
     }
    
    @Override
    protected final String updateKeyAfterInsert(ReadLaterBean entity, long rowId) {
        return entity.getTitle();
    }
    
    @Override
    public String getKey(ReadLaterBean entity) {
        if(entity != null) {
            return entity.getTitle();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(ReadLaterBean entity) {
        return entity.getTitle() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
