package com.nighthawk.spring_portfolio.mvc.leaderboard;

import java.util.ArrayList;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:4200/")
@RequestMapping("/leaderboard")
public class LeaderBoardController {
    private ArrayList<Player> players = new ArrayList<>();
    private static final Logger logger = LoggerFactory.getLogger(LeaderBoardController.class);

    @PostMapping("/add")
    public ResponseEntity<String> addPlayer(@RequestBody Player player) {
        try {
            players.add(player);
            logger.info("Player added: {}", player);
            return ResponseEntity.ok("Player added successfully");
        } catch (Exception e) {
            logger.error("Error adding player", e);
            return ResponseEntity.status(500).body("Error adding player");
        }
    }

    @GetMapping("/allPlayers")
    public ResponseEntity<ArrayList<Player>> getAllPlayers() {
        return ResponseEntity.ok(players);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<String> removePlayer(@RequestParam String name) {
        boolean removed = players.removeIf(player -> player.getName().equals(name));
        if (removed) {
            return ResponseEntity.ok("Player removed successfully");
        } else {
            return ResponseEntity.status(404).body("Player not found");
        }
    }

    @DeleteMapping("/removeAll")
    public ResponseEntity<String> removeAllPlayers() {
        players.clear();
        return ResponseEntity.ok("All players removed successfully");
    }
}
