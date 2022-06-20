package com.hackerswork.hsw.service.connection;

import com.hackerswork.hsw.enums.Preference;
import java.util.List;

public interface ConnectionCommandService {

    void addAll(Long personId, List<String> userNames);

    boolean setPreferenceForConnection(Long personId, Long connectionId, Preference preference);

}
