package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/api")
public class PythonController {

    @Value("${python.executable:python}")
    private String pythonExecutable;

    private static final String ANSWER_DIR = "E:\\00_Skripsi_2023_2024\\TestPython";

    @PostMapping("/run-python")
    public ModelAndView runPython(@RequestParam("file") MultipartFile file) {
        ModelAndView modelAndView = new ModelAndView("result");

        try {
            // Ensure the answer directory exists and is writable
            Path answerDirPath = Paths.get(ANSWER_DIR);
            if (!Files.exists(answerDirPath)) {
                throw new IllegalArgumentException("Answer directory does not exist: " + ANSWER_DIR);
            }
            if (!Files.isWritable(answerDirPath)) {
                throw new IllegalArgumentException("Cannot write to the answer directory: " + ANSWER_DIR);
            }

            // Save the uploaded file directly to the ANSWER_DIR
            File uploadedFile = new File(ANSWER_DIR, file.getOriginalFilename());
            file.transferTo(uploadedFile);

            // Determine the Python script to run based on the uploaded file's name
            String pythonScript = determinePythonScript(uploadedFile.getName());

            if (pythonScript == null) {
                throw new IllegalArgumentException(
                        "No matching Python script found for file: " + uploadedFile.getName());
            }

            // Build the command to run the Python script
            String command = String.format("cmd /c \"%s %s\"", pythonExecutable, pythonScript);

            // Log the command for debugging purposes
            System.out.println("Running command: " + command);
            System.out.println("Working directory: " + ANSWER_DIR);

            // Run the command using the command line
            ProcessBuilder processBuilder = new ProcessBuilder("cmd", "/c", command);
            processBuilder.directory(new File(ANSWER_DIR));
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            // Capture the output
            StringBuilder output = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                    System.out.println("Output: " + line);
                }
            }

            // Wait for the process to complete
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                modelAndView.addObject("output",
                        "Error running Python script. Exit code: " + exitCode + "\n\nOutput:\n" + output.toString());
            } else {
                modelAndView.addObject("output", output.toString());
            }

            // Optionally delete the uploaded file after processing
            // uploadedFile.delete();

        } catch (Exception e) {
            e.printStackTrace();
            modelAndView.addObject("output", "Error processing file: " + e.getMessage());
        }

        return modelAndView;
    }

    private String determinePythonScript(String uploadedFileName) {
        if (uploadedFileName.equals("answer_bab1_percobaan1")) {
            return "test_bab1_percobaan1.py";
        }
        // Add more conditions here if you have other file-script mappings
        return null;
    }
}
