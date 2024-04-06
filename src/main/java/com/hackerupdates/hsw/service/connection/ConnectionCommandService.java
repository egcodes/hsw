package com.hackerupdates.hsw.service.connection;

import com.hackerupdates.hsw.enums.Preference;
import java.util.List;

public interface ConnectionCommandService {

    void addAll(Long personId, List<String> userNames);

    boolean setPreferenceForConnection(Long personId, Long connectionId, Preference preference);

}
