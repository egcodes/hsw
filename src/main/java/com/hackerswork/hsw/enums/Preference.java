package com.hackerswork.hsw.enums;

import com.hackerswork.hsw.persistence.entity.Connection;

public enum Preference {
    PINNED {
        @Override
        public Connection setConnectionPreferenceForPerson(Connection connection) {
            connection.setPinned(Boolean.TRUE);
            return connection;
        }
    },
    UNPINNED {
        @Override
        public Connection setConnectionPreferenceForPerson(Connection connection) {
            connection.setPinned(Boolean.FALSE);
            return connection;
        }
    },
    BLOCKED {
        @Override
        public Connection setConnectionPreferenceForPerson(Connection connection) {
            connection.setBlocked(Boolean.TRUE);
            return connection;
        }
    },
    UNBLOCKED {
        @Override
        public Connection setConnectionPreferenceForPerson(Connection connection) {
            connection.setBlocked(Boolean.FALSE);
            return connection;
        }
    },
    HIDDEN {
        @Override
        public Connection setConnectionPreferenceForPerson(Connection connection) {
            connection.setHidden(Boolean.TRUE);
            return connection;
        }
    },
    VISIBLE {
        @Override
        public Connection setConnectionPreferenceForPerson(Connection connection) {
            connection.setHidden(Boolean.FALSE);
            return connection;
        }
    };


    public abstract Connection setConnectionPreferenceForPerson(Connection connection);

}
