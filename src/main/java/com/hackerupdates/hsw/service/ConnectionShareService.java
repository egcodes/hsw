package com.hackerupdates.hsw.service;

import com.hackerupdates.hsw.domain.dto.ConnectionShareDTO;
import com.hackerupdates.hsw.domain.dto.ShareDTO;
import com.hackerupdates.hsw.domain.dto.ShareRespDTO;
import com.hackerupdates.hsw.domain.mapper.ShareMapper;
import com.hackerupdates.hsw.service.connection.ConnectionQueryService;
import com.hackerupdates.hsw.service.person.PersonQueryService;
import com.hackerupdates.hsw.service.share.ShareQueryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConnectionShareService {

    private final ConnectionQueryService connectionQueryService;
    private final ShareQueryService shareQueryService;
    private final PersonQueryService personQueryService;
    private final ShareMapper mapper;

    public List<ConnectionShareDTO> findByPersonId(Long personId, int pageNumber, int pageSize) {
        var connections = getConnections(personId);
        var shares = shareQueryService.listByPersonIds(connections, pageNumber, pageSize);
        return getConnectionShareDTOS(shares);
    }

    public List<ConnectionShareDTO> findByOffsetAndPersonId(Long personId, Long offset) {
        var connections = getConnections(personId);
        var shares = shareQueryService.listByPersonIdsFrom(connections, offset);
        return getConnectionShareDTOS(shares);
    }

    public List<ConnectionShareDTO> findAllShares(int pageNumber, int pageSize) {
        var shares = shareQueryService.list(pageNumber, pageSize);
        return getConnectionShareDTOS(shares);
    }

    public List<ConnectionShareDTO> findByOffset(Long offset) {
        var shares = shareQueryService.listFrom(offset);
        return getConnectionShareDTOS(shares);
    }

    public ConnectionShareDTO findByShareId(Long shareId) {
        var share = shareQueryService.findBy(shareId);
        var shareDTO = mapper.toDTO(share);
        var person = personQueryService.find(share.getPersonId());
        shareDTO.setName(person.getName());
        shareDTO.setUserName(person.getUserName());

        return getConnectionShareDTOS(List.of(shareDTO)).getFirst();
    }

    private List<Long> getConnections(Long personId) {
        var connections = connectionQueryService.findConnectionIds(personId);
        connections.add(personId);
        return connections;
    }

    private List<ConnectionShareDTO> getConnectionShareDTOS(List<ShareDTO> shares) {
        var sharesResp = shares.stream()
            .map(s -> ShareRespDTO.builder()
                .id(s.getId())
                .name(s.getName())
                .userName(s.getUserName())
                .text(s.getText())
                .createdTime(Long.toString(s.getCreatedTime().toEpochMilli()))
                .build())
            .toList();

        return sharesResp.stream().map(s ->
            ConnectionShareDTO.builder()
                .userName(s.getUserName())
                .name(s.getName())
                .share(s)
                .build()
        ).toList();
    }

}