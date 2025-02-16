package com.smartstock.Stock.Market.Controller;

import org.python.jline.internal.InputStreamReader;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/python")
@CrossOrigin(origins = "*")
public class StockPredictionController {

    @PostMapping("/predict")
    public Map<String, Object> predictStock(@RequestBody Map<String, String> request) {
        String ticker = request.get("ticker");

        try {
            // Execute Python script and get prediction
            String scriptPath = "src/main/python/predict.py"; // Adjust the path if needed
            ProcessBuilder processBuilder = new ProcessBuilder("python", scriptPath, ticker);
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            StringBuilder output = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                output.append(line);
            }

            Map<String, Object> response = new HashMap<>();
            response.put("ticker", ticker);
            response.put("image_url", "http://localhost:8080/static/" + ticker + "_prediction.png");
            return response;

        } catch (IOException e) {
            e.printStackTrace();
            return Collections.singletonMap("error", "Failed to execute prediction");
        }
    }

    @GetMapping("/test")
    public String test() {
        return "API is working!";
    }
}