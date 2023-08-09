package com.ext.whitelist.controller;

import com.ext.whitelist.domain.HostDetails;
import com.ext.whitelist.domain.HostRepository;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.time.Instant;
import java.util.Optional;

@RestController
@RequestMapping("/white-list")
public class RequestController {

    @Autowired
    private HostRepository hostRepository;

    @GetMapping("/is-up")
    public String getRequest(){
        return "Yes!!";
    }

    @PostMapping("/add-ip-to-white-list")
    public ResponseEntity<HostDetailsResponse> addNewHost(@RequestBody HostDetails hostDetails){

        hostDetails.setActive(true);
        hostDetails.setCreateAt(Instant.now());
        hostDetails.setCreatedBy(1001l);
        HostDetails hostDetailsEntity =  hostRepository.save(hostDetails);
        HostDetailsResponse hostDetailsResponse =
                new HostDetailsResponse(hostDetailsEntity.getId(), hostDetailsEntity.getBelongsTo(), hostDetailsEntity.getIpAddress(), hostDetailsEntity.isActive());
        return ResponseEntity.status(HttpStatus.CREATED).body(hostDetailsResponse);
    }

    private record HostDetailsResponse(long id, String belongsTo, String host, boolean active) {
    }

    @GetMapping("/get/{ip-address}")
    public HostDetails getRequest(@PathVariable("ip-address") String ip){
        Optional<HostDetails> hostDetails = hostRepository.findByIpAddress(ip);
        if(hostDetails.isPresent()){
         return hostDetails.get();
        }
        return null;
    }
}
