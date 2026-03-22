package com.example.math_race.controller;

import com.example.math_race.dto.request.*;
import com.example.math_race.dto.response.ApiResponse;
import com.example.math_race.dto.response.CreateRaceResponse;
import com.example.math_race.dto.response.JoinRaceResponse;
import com.example.math_race.dto.response.RaceInfoResponse;
import com.example.math_race.dto.wsMessage.request.SubmitQuestionRequest;
import com.example.math_race.exception.ErrorCode;
import com.example.math_race.service.RaceService;
import com.example.math_race.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/race")
public class RaceController {

    private final RaceService raceService;
    private final WebSocketService webSocketService;

    @Autowired
    public  RaceController(RaceService raceService,  WebSocketService webSocketService) {
        this.raceService = raceService;
        this.webSocketService = webSocketService;
    }

    @MessageMapping("/race/{roomCode}/player/submit")
    public void handleSubmit(@DestinationVariable String roomCode, @Payload SubmitQuestionRequest request , StompHeaderAccessor accessor) {
        if (request == null || request.getAnswer() == null || request.getAnswer().isEmpty()) {
            webSocketService.sendErrorToQueueSession(WebSocketService.QUEUE_RACE_FEEDBACK,ErrorCode.INVALID_INPUT,accessor);
        }

        raceService.handleSubmitQuestion(roomCode,request, accessor);
    }

    @MessageMapping("/race/{roomCode}/host/start")
    public void handleChangeRaceStatus(@DestinationVariable String roomCode, StompHeaderAccessor accessor) {
        raceService.handleStartRace(roomCode, accessor);
    }

    @MessageMapping({"/race/{roomCode}/host/sync", "/race/{roomCode}/player/sync"})
    public void handleRaceSync(@DestinationVariable String roomCode, StompHeaderAccessor accessor){
        raceService.sendRaceState(roomCode, accessor);
    }

    // http://localhost:8085/api/race/create
    @PostMapping("/create")
    public ApiResponse<CreateRaceResponse> createRace(@RequestBody CreateRaceRequest request, RequestMetadata metadata) {
        if (request == null || request.getTargetScore() == null || request.getTargetScore() <=0 ) {
            return ApiResponse.error(ErrorCode.INVALID_INPUT);
        }

        CreateRaceResponse createRaceResponse = raceService.creatRace(request, metadata);
        return ApiResponse.success(createRaceResponse);
    }

   @PostMapping("/join")
    public ApiResponse<JoinRaceResponse> join(@RequestBody JoinRaceRequest request, RequestMetadata metadata){
        if (request == null || request.getRoomCode() == null || request.getRoomCode().isEmpty()) {
            return ApiResponse.error(ErrorCode.INVALID_INPUT);
        }

        JoinRaceResponse joinRaceResponse = raceService.joinRace(request,metadata);
        return ApiResponse.success(joinRaceResponse);
    }

    @PostMapping("/info")
    public ApiResponse<RaceInfoResponse> raceInfo(@RequestBody RaceInfoRequest request, RequestMetadata metadata){
        if (request == null || request.getRoomCode() == null || request.getRoomCode().isEmpty()) {
            return ApiResponse.error(ErrorCode.INVALID_INPUT);
        }

        RaceInfoResponse raceInfoResponse = raceService.raceInfo(request,metadata);
        return ApiResponse.success(raceInfoResponse);
    }
}
