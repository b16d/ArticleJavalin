package org.example.domain;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

public record User (@ColumnName("firstname") String firstname,
                    @ColumnName("famillyname") String famillyname,
                    @ColumnName("userid") int userid) {
}
