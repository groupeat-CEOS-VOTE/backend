package com.ceos.vote.domain.vote.controller;

import com.ceos.vote.domain.vote.dto.request.DemodayVoteRequest;
import com.ceos.vote.domain.vote.dto.response.DemodayVoteResponse;
import com.ceos.vote.domain.vote.dto.response.DemodayVoteResultResponse;
import com.ceos.vote.domain.vote.dto.response.DemodayVoteStatusResponse;
import com.ceos.vote.domain.vote.dto.response.DemodayTeamResponse;
import com.ceos.vote.domain.vote.service.DemodayVoteService;
import com.ceos.vote.global.apiPayload.ApiResponse;
import com.ceos.vote.global.apiPayload.code.status.SuccessStatus;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/votes/demoday")
@Tag(name = "데모데이 투표", description = "데모데이 팀 투표 참여, 현황 조회, 결과 조회 API")
public class DemodayVoteController {

    private final DemodayVoteService demodayVoteService;

    @Operation(
            summary = "데모데이 투표 참여",
            description = "로그인한 사용자가 본인 팀을 제외한 데모데이 참여 팀에 한 번 투표합니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "투표 완료",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                            {"isSuccess":true,"code":"COMMON201","message":"생성이 완료되었습니다.","result":{"voteId":1,"selectedTeam":"DITDA","selectedTeamName":"Ditda"}}
                            """))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증 필요",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                            {"isSuccess":false,"code":"COMMON401","message":"인증이 필요합니다.","result":null}
                            """))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "본인 팀 투표 불가",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                            {"isSuccess":false,"code":"VOTE4032","message":"본인이 속한 팀에는 투표할 수 없습니다.","result":null}
                            """))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "중복 투표",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                            {"isSuccess":false,"code":"VOTE4091","message":"이미 투표했습니다.","result":null}
                            """)))
    })
    @SecurityRequirement(name = "accessTokenCookie")
    @PostMapping
    public ResponseEntity<ApiResponse<DemodayVoteResponse>> vote(
            @Valid @RequestBody DemodayVoteRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.of(SuccessStatus.CREATED, demodayVoteService.vote(request)));
    }

    @Operation(
            summary = "데모데이 투표 팀 목록 조회",
            description = "투표할 수 있는 데모데이 팀 목록을 enum 정의 순서로 조회합니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                            {"isSuccess":true,"code":"COMMON200","message":"성공입니다.","result":[{"team":"IPX","teamName":"IPX"},{"team":"CONX","teamName":"CONX"},{"team":"GROUPEAT","teamName":"Groupeat"},{"team":"DITDA","teamName":"Ditda"},{"team":"JOBDRI","teamName":"JobDri"}]}
                            """)))
    })
    @GetMapping("/teams")
    public ApiResponse<List<DemodayTeamResponse>> getTeams() {
        return ApiResponse.onSuccess(demodayVoteService.getTeams());
    }

    @Operation(
            summary = "데모데이 투표 현황 조회",
            description = "팀별 현재 득표 수를 내림차순으로 조회합니다. 모든 투표 완료 여부도 함께 반환합니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                            {"isSuccess":true,"code":"COMMON200","message":"성공입니다.","result":{"totalVoterCount":25,"votedCount":12,"resultOpen":false,"teams":[{"team":"DITDA","teamName":"Ditda","voteCount":5},{"team":"IPX","teamName":"IPX","voteCount":4},{"team":"CONX","teamName":"CONX","voteCount":3},{"team":"GROUPEAT","teamName":"Groupeat","voteCount":0},{"team":"JOBDRI","teamName":"JobDri","voteCount":0}]}}
                            """)))
    })
    @GetMapping("/status")
    public ApiResponse<DemodayVoteStatusResponse> getStatus() {
        return ApiResponse.onSuccess(demodayVoteService.getStatus());
    }

    @Operation(
            summary = "데모데이 투표 결과 조회",
            description = "모든 회원의 투표가 완료된 뒤 데모데이 투표 결과를 조회합니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                            {"isSuccess":true,"code":"COMMON200","message":"성공입니다.","result":{"totalVoterCount":25,"votedCount":25,"teams":[{"team":"DITDA","teamName":"Ditda","voteCount":8},{"team":"IPX","teamName":"IPX","voteCount":7},{"team":"CONX","teamName":"CONX","voteCount":5},{"team":"GROUPEAT","teamName":"Groupeat","voteCount":3},{"team":"JOBDRI","teamName":"JobDri","voteCount":2}]}}
                            """))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "결과 미공개",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                            {"isSuccess":false,"code":"VOTE4031","message":"아직 모든 투표가 완료되지 않아 결과를 조회할 수 없습니다.","result":null}
                            """)))
    })
    @GetMapping("/results")
    public ApiResponse<DemodayVoteResultResponse> getResult() {
        return ApiResponse.onSuccess(demodayVoteService.getResult());
    }
}
