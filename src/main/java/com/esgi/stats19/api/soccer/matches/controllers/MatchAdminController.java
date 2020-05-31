//package com.esgi.stats19.api.soccer.matches.controllers;
//
//import com.esgi.stats19.api.common.services.URIService;
//import com.esgi.stats19.api.soccer.matches.DTO.CreateMatchDTO;
//import com.esgi.stats19.api.soccer.matches.DTO.GetMatchDTO;
//import com.esgi.stats19.api.soccer.matches.DTO.UpdateMatchDTO;
//import com.esgi.stats19.api.soccer.matches.services.MatchDTOService;
//import com.esgi.stats19.api.soccer.matches.services.MatchService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/admin/matchs")
//public class MatchAdminController {
//
//    private final MatchService matchService;
//    private final MatchDTOService matchDTOService;
//    private final URIService uriService;
//
//    @Autowired
//    public MatchAdminController(MatchService matchService, MatchDTOService matchDTOService, URIService uriService) {
//        this.matchService = matchService;
//        this.matchDTOService = matchDTOService;
//        this.uriService = uriService;
//    }
//
//    @PostMapping
//    public ResponseEntity<GetMatchDTO> createMatch(@Validated @RequestBody CreateMatchDTO match) {
//        var createdMatch = this.matchDTOService.toResponse(this.matchService.createMatch(match));
//        return ResponseEntity.created(this.uriService.getMatch(createdMatch.getMatchId()))
//                .body(createdMatch);
//    }
//
//    @PutMapping("/{matchId}")
//    public GetMatchDTO updateMatch(@Validated @RequestBody UpdateMatchDTO updateMatch,
//                                 @PathVariable Integer matchId) {
//        return this.matchDTOService.toResponse(this.matchService.updateMatch(updateMatch, matchId));
//    }
//
//    @DeleteMapping("/{matchId}")
//    public void deleteMatch(@PathVariable Integer matchId){
//        this.matchService.deleteMatch(matchId);
//    }
//}
