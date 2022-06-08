package com.hackerswork.hsw.service;

import static com.hackerswork.hsw.constants.Constant.DATE_FORMAT;

import com.hackerswork.hsw.dto.ConnectionShareDTO;
import com.hackerswork.hsw.dto.ShareRespDTO;
import com.hackerswork.hsw.service.connection.ConnectionQueryService;
import com.hackerswork.hsw.service.share.ShareQueryService;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ConnectionShareServiceImpl implements ConnectionShareService {

    private final ConnectionQueryService connectionQueryService;
    private final ShareQueryService shareQueryService;

    @Override
    public List<ConnectionShareDTO> findByPersonId(Long personId, String utc, int pageNumber, int pageSize) {
        var connections = connectionQueryService.findConnections(personId);
        connections.add(personId);

        var shares = shareQueryService.list(connections, pageNumber, pageSize);
        var sharesResp = shares.stream()
            .map(s -> ShareRespDTO.builder()
                .name(s.getName())
                .userName(s.getUserName())
                .text(s.getText())
                .createdTime(s.getCreatedTime().atOffset(ZoneOffset.of(utc))
                    .format(DateTimeFormatter.ofPattern(DATE_FORMAT)))
                .build())
            .collect(Collectors.toList());

        return sharesResp.stream().map(s ->
            ConnectionShareDTO.builder()
                .userName(s.getUserName())
                .name(s.getName())
                .share(s)
                .build()
        ).collect(Collectors.toList());
    }

}