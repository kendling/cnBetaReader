package com.ywwxhz.lib.database.sqlite;

import com.ywwxhz.lib.database.exception.DbException;
import com.ywwxhz.lib.database.table.ColumnUtils;
import com.ywwxhz.lib.database.table.Finder;
import com.ywwxhz.lib.database.table.Table;

import java.util.List;

/**
 * Author: wyouflf
 * Date: 13-9-10
 * Time: 下午10:50
 */
public class FinderLazyLoader<T> {
    private final Finder finderColumn;
    private final Object finderValue;

    public FinderLazyLoader(Finder finderColumn, Object value) {
        this.finderColumn = finderColumn;
        this.finderValue = ColumnUtils.convert2DbColumnValueIfNeeded(value);
    }

    public List<T> getAllFromDb() throws DbException {
        List<T> entities = null;
        Table table = finderColumn.getTable();
        if (table != null) {
            entities = table.db.findAll(
                    Selector.from(finderColumn.getTargetEntityType()).
                            where(finderColumn.getTargetColumnName(), "=", finderValue)
            );
        }
        return entities;
    }

    public T getFirstFromDb() throws DbException {
        T entity = null;
        Table table = finderColumn.getTable();
        if (table != null) {
            entity = table.db.findFirst(
                    Selector.from(finderColumn.getTargetEntityType()).
                            where(finderColumn.getTargetColumnName(), "=", finderValue)
            );
        }
        return entity;
    }
}
