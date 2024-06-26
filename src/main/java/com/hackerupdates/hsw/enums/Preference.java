package com.hackerupdates.hsw.enums;

import com.hackerupdates.hsw.domain.entity.Connection;

public enum Preference {
    PINNED {
        @Override
        public Connection setConnectionPreferenceForPerson(Connection connection) {
            connection.setPinned(Boolean.TRUE);
            connection.setHidden(Boolean.FALSE);
            connection.setBlocked(Boolean.FALSE);
            return connection;
        }
    },
    UNPINNED {
        @Override
        public Connection setConnectionPreferenceForPerson(Connection connection) {
            connection.setPinned(Boolean.FALSE);
            connection.setHidden(Boolean.FALSE);
            connection.setBlocked(Boolean.FALSE);
            return connection;
        }
    },
    BLOCKED {
        @Override
        public Connection setConnectionPreferenceForPerson(Connection connection) {
            connection.setBlocked(Boolean.TRUE);
            connection.setHidden(Boolean.TRUE);
            return connection;
        }
    },
    UNBLOCKED {
        @Override
        public Connection setConnectionPreferenceForPerson(Connection connection) {
            connection.setBlocked(Boolean.FALSE);
            connection.setHidden(Boolean.FALSE);
            return connection;
        }
    },
    HIDDEN {
        @Override
        public Connection setConnectionPreferenceForPerson(Connection connection) {
            connection.setHidden(Boolean.TRUE);
            connection.setBlocked(Boolean.FALSE);
            return connection;
        }
    },
    VISIBLE {
        @Override
        public Connection setConnectionPreferenceForPerson(Connection connection) {
            connection.setHidden(Boolean.FALSE);
            connection.setBlocked(Boolean.FALSE);
            return connection;
        }
    };


    public abstract Connection setConnectionPreferenceForPerson(Connection connection);

}
