package com.example.eployeeretentionpredection.controller;

import com.example.eployeeretentionpredection.pojo.FilePojo;
import com.example.eployeeretentionpredection.service.TrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class TrainingController {

    private final TrainingService trainRandomForestModel;

    @PostMapping("/train-by-file")
    public String trainModelByFile(@ModelAttribute FilePojo file) {
        try {
            trainRandomForestModel.trainModel(file.getFile());
            return "Model trained and saved successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to train model: " + e.getMessage();
        }
    }

    @GetMapping("/train")
    public String trainModel() {
        try {
            trainRandomForestModel.trainModel();
            return "Model trained and saved successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to train model: " + e.getMessage();
        }
    }

}