package com.hackerswork.hsw.service.connection;

import java.util.List;

public interface ConnectionCommandService {

    void addAll(Long personId, List<String> userNames);

}
