package com.hackerswork.hsw.service;

import static com.hackerswork.hsw.constants.Constant.DATE_FORMAT;

import com.hackerswork.hsw.dto.ConnectionShareDTO;
import com.hackerswork.hsw.dto.ShareDTO;
import com.hackerswork.hsw.dto.ShareRespDTO;
import com.hackerswork.hsw.mapper.ShareMapper;
import com.hackerswork.hsw.service.connection.ConnectionQueryService;
import com.hackerswork.hsw.service.person.PersonQueryService;
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
    private final PersonQueryService personQueryService;
    private final ShareMapper mapper;

    @Override
    public List<ConnectionShareDTO> findByPersonId(Long personId, String utc, int pageNumber, int pageSize) {
        var connections = getConnections(personId);
        var shares = shareQueryService.list(connections, pageNumber, pageSize);
        return getConnectionShareDTOS(utc, shares);
    }

    @Override
    public List<ConnectionShareDTO> findByOffsetAndPersonId(Long personId, Long offset, String utc) {
        var connections = getConnections(personId);
        var shares = shareQueryService.listFrom(connections, offset);
        return getConnectionShareDTOS(utc, shares);
    }

    @Override
    public ConnectionShareDTO findByShareId(Long shareId, String utc) {
        var share = shareQueryService.findBy(shareId);
        var shareDTO = mapper.toDTO(share);
        var person = personQueryService.findPerson(share.getPersonId());
        shareDTO.setName(person.getName());
        shareDTO.setUserName(person.getUserName());

        return getConnectionShareDTOS(utc, List.of(shareDTO)).get(0);
    }

    private List<Long> getConnections(Long personId) {
        var connections = connectionQueryService.findConnections(personId);
        connections.add(personId);
        return connections;
    }

    private List<ConnectionShareDTO> getConnectionShareDTOS(String utc, List<ShareDTO> shares) {
        var sharesResp = shares.stream()
            .map(s -> ShareRespDTO.builder()
                .id(s.getId())
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