package com.nighthawk.spring_portfolio.mvc.leaderboard;

import java.util.ArrayList;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class LeaderBoardController {
    private ArrayList<Player> players = new ArrayList<Player>();

    @PostMapping("/add")
    public ResponseEntity<String> addPlayer(@RequestBody Player player) {
        players.add(player);
        return ResponseEntity.ok("Player added successfully");
    }

    @GetMapping("/allWorkers")
    public ResponseEntity<ArrayList<Player>> getAllWorkers() {
        return ResponseEntity.ok(players);
    }
}
