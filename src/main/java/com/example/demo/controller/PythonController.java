// // package com.example.demo.controller;

// // import org.springframework.beans.factory.annotation.Value;
// // import org.springframework.stereotype.Controller;
// // import org.springframework.web.bind.annotation.PostMapping;
// // import org.springframework.web.bind.annotation.RequestMapping;
// // import org.springframework.web.bind.annotation.RequestParam;
// // import org.springframework.web.multipart.MultipartFile;
// // import org.springframework.web.servlet.ModelAndView;

// // import java.io.BufferedReader;
// // import java.io.File;
// // import java.io.InputStreamReader;
// // import java.nio.file.Files;
// // import java.nio.file.Path;
// // import java.nio.file.Paths;

// // @Controller
// // @RequestMapping("/api")
// // public class PythonController {

// //     @Value("${python.executable:python}")
// //     private String pythonExecutable;

// //     private static final String ANSWER_DIR = "E:\\00_Skripsi_2023_2024\\TestPython";

// //     @PostMapping("/run-python")
// //     public ModelAndView runPython(@RequestParam("file") MultipartFile file) {
// //         ModelAndView modelAndView = new ModelAndView("result");

// //         try {
// //             // Ensure the answer directory exists and is writable
// //             Path answerDirPath = Paths.get(ANSWER_DIR);
// //             if (!Files.exists(answerDirPath)) {
// //                 throw new IllegalArgumentException("Answer directory does not exist: " + ANSWER_DIR);
// //             }
// //             if (!Files.isWritable(answerDirPath)) {
// //                 throw new IllegalArgumentException("Cannot write to the answer directory: " + ANSWER_DIR);
// //             }

// //             // Save the uploaded file directly to the ANSWER_DIR
// //             File uploadedFile = new File(ANSWER_DIR, file.getOriginalFilename());
// //             file.transferTo(uploadedFile);

// //             // Determine the Python script to run based on the uploaded file's name
// //             String pythonScript = determinePythonScript(uploadedFile.getName());

// //             if (pythonScript == null) {
// //                 throw new IllegalArgumentException(
// //                         "No matching Python script found for file: " + uploadedFile.getName());
// //             }

// //             // Build the command to run the Python script
// //             String command = String.format("cmd /c \"%s %s\"", pythonExecutable, pythonScript);

// //             // Log the command for debugging purposes
// //             System.out.println("Running command: " + command);
// //             System.out.println("Working directory: " + ANSWER_DIR);

// //             // Run the command using the command line
// //             ProcessBuilder processBuilder = new ProcessBuilder("cmd", "/c", command);
// //             processBuilder.directory(new File(ANSWER_DIR));
// //             processBuilder.redirectErrorStream(true);
// //             Process process = processBuilder.start();

// //             // Capture the output
// //             StringBuilder output = new StringBuilder();
// //             try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
// //                 String line;
// //                 while ((line = reader.readLine()) != null) {
// //                     output.append(line).append("\n");
// //                     System.out.println("Output: " + line);
// //                 }
// //             }

// //             // Wait for the process to complete
// //             int exitCode = process.waitFor();
// //             if (exitCode != 0) {
// //                 modelAndView.addObject("output",
// //                         "Error running Python script. Exit code: " + exitCode + "\n\nOutput:\n" + output.toString());
// //             } else {
// //                 String rawOutput = output.toString();
// //                 String parsedOutput = parseOutput(rawOutput);
// //                 modelAndView.addObject("output", parsedOutput);
// //             }

// //             // Optionally delete the uploaded file after processing
// //             // uploadedFile.delete();

// //         } catch (Exception e) {
// //             e.printStackTrace();
// //             modelAndView.addObject("output", "Error processing file: " + e.getMessage());
// //         }

// //         return modelAndView;
// //     }

// //     private String determinePythonScript(String uploadedFileName) {
// //         System.out.println("Menentukan script untuk file: " + uploadedFileName);
        
// //         if (uploadedFileName.startsWith("answer_bab1_percobaan1")) {
// //             return "test_bab1_percobaan1.py";
// //         } else if (uploadedFileName.startsWith("answer_bab1_percobaan2")) {
// //             return "test_bab1_percobaan2.py";
// //         } else if (uploadedFileName.startsWith("answer_bab1_percobaan3")) {
// //             return "test_bab1_percobaan3.py";
// //         } else if (uploadedFileName.startsWith("answer_bab2_percobaan2")) {
// //             return "test_bab2_percobaan2.py";
// //         } else if (uploadedFileName.startsWith("answer_bab3_percobaan1")) {
// //             return "test_bab3_percobaan1.py";
// //         } else if (uploadedFileName.startsWith("answer_bab3_percobaan2")) {
// //             return "test_bab3_percobaan2.py";
// //         } else if (uploadedFileName.startsWith("answer_bab3_percobaan3")) {
// //             return "test_bab3_percobaan3.py";
// //         } else if (uploadedFileName.startsWith("answer_bab3_percobaan4")) {
// //             return "test_bab3_percobaan4.py";
// //         } else if (uploadedFileName.startsWith("answer_bab4_percobaan4")) {
// //             return "test_bab4_percobaan4.py";
// //         } else if (uploadedFileName.startsWith("answer_bab4_percobaan5")) {
// //             return "test_bab4_percobaan5.py";
// //         }
        
// //         // Pemetaan default jika tidak ada yang cocok
// //         return uploadedFileName.replace("answer_", "test_");
// //     }

// //     private String parseOutput(String rawOutput) {
// //         StringBuilder parsedOutput = new StringBuilder();
// //         String[] lines = rawOutput.split("\n");
// //         String currentTest = "";
// //         boolean testPassed = true;

// //         for (String line : lines) {
// //             if (line.startsWith("<DESCRIBE::>")) {
// //                 parsedOutput.append("## ").append(line.substring(12)).append("\n\n");
// //             } else if (line.startsWith("<IT::>")) {
// //                 if (!currentTest.isEmpty()) {
// //                     parsedOutput.append(currentTest).append(testPassed ? " - <PASSED>\n" : " - <FAILED>\n");
// //                 }
// //                 currentTest = line.substring(6);
// //                 testPassed = true;
// //             } else if (line.startsWith("<PASSED::>")) {
// //                 // Tidak perlu melakukan apa-apa, test tetap dianggap lulus
// //             } else if (line.startsWith("<FAILED::>")) {
// //                 testPassed = false;
// //                 parsedOutput.append("   Kesalahan: ").append(line.substring(10)).append("\n");
// //             } else if (line.startsWith("<COMPLETEDIN::>")) {
// //                 // Opsional: Anda bisa menambahkan waktu penyelesaian jika diperlukan
// //                 // parsedOutput.append("   Waktu: ").append(line.substring(14)).append(" ms\n");
// //             }
// //         }

// //         // Menambahkan test terakhir
// //         if (!currentTest.isEmpty()) {
// //             parsedOutput.append(currentTest).append(testPassed ? " - <PASSED>\n" : " - <FAILED>\n");
// //         }

// //         return parsedOutput.toString();
// //     }
// // }




package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://127.0.0.1:8000")
public class PythonController {

    @Value("${python.executable:python}")
    private String pythonExecutable;

    private static final String ANSWER_DIR = "E:\\00_Skripsi_2023_2024\\TestPython";

    @PostMapping("/run-python")
    public ResponseEntity<Map<String, Object>> runPython(@RequestParam("file") MultipartFile file) {
        System.out.println("File received: " + file.getOriginalFilename());
        Map<String, Object> response = new HashMap<>();

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
                response.put("status", "error");
                response.put("message", "Error running Python script. Exit code: " + exitCode);
                response.put("output", output.toString());
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            } else {
                String rawOutput = output.toString();
                String parsedOutput = parseOutput(rawOutput);
                response.put("status", "success");
                response.put("output", parsedOutput);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            // Optionally delete the uploaded file after processing
            // uploadedFile.delete();

        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", "error");
            response.put("message", "Error processing file: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String determinePythonScript(String uploadedFileName) {
        System.out.println("Menentukan script untuk file: " + uploadedFileName);
        // ?
        if (uploadedFileName.startsWith("answer_chapter1_experiments1")) {
            return "test_chapter1_experiments1.py";
        } else if (uploadedFileName.startsWith("answer_chapter1_experiments2")) {
            return "test_chapter1_experiments2.py";
        } else if (uploadedFileName.startsWith("answer_chapter2_experiments2")) {
            return "test_chapter2_experiments2.py";
        } else if (uploadedFileName.startsWith("answer_chapter2_experiments3")) {
            return "test_chapter2_experiments3.py";
        } else if (uploadedFileName.startsWith("answer_chapter3_experiments1")) {
            return "test_chapter3_experiments1.py";
        } else if (uploadedFileName.startsWith("answer_chapter3_experiments2")) {
            return "test_chapter3_experiments2.py";
        } else if (uploadedFileName.startsWith("answer_chapter3_experiments3")) {
            return "test_chapter3_experiments3.py";
        } else if (uploadedFileName.startsWith("answer_chapter3_experiments4")) {
            return "test_chapter3_experiments4.py";
        } else if (uploadedFileName.startsWith("answer_chapter3_experiments5")) {
            return "test_chapter3_experiments5.py";
        } else if (uploadedFileName.startsWith("answer_chapter4_experiments1")) {
            return "test_chapter4_experiments1.py";
        }
        
        // Pemetaan default jika tidak ada yang cocok
        return uploadedFileName.replace("answer_", "test_");
    }

    private String parseOutput(String rawOutput) {
        StringBuilder parsedOutput = new StringBuilder();
        String[] lines = rawOutput.split("\n");
        String currentTest = "";
        boolean testPassed = true;
        boolean errorOccurred = false;
        List<String> errorMessages = new ArrayList<>(); 
        String errorMessage = "";

    
        for (String line : lines) {
            if (line.startsWith("<DESCRIBE::>")) {
                parsedOutput.append("## ").append(line.substring(12)).append("\n\n");
            } else if (line.startsWith("<IT::>")) {
                if (!currentTest.isEmpty()) {
                    parsedOutput.append(currentTest).append(testPassed ? " - <PASSED>\n" : " - <FAILED>\n");
                    if (errorOccurred) {
                        parsedOutput.append("   ERROR: ").append(errorMessage).append("\n");
                    }
                }
                currentTest = line.substring(6);
                testPassed = true;
                errorOccurred = false;
            } else if (line.startsWith("<PASSED::>")) {
                // Tidak perlu melakukan apa-apa, test tetap dianggap lulus
            } else if (line.startsWith("<FAILED::>")) {
                testPassed = false;
                errorOccurred = true;
                errorMessages.add(line.substring(10));
            } else if (line.startsWith("<COMPLETEDIN::>")) {
                // Opsional: Anda bisa menambahkan waktu penyelesaian jika diperlukan
                // parsedOutput.append("   Waktu: ").append(line.substring(14)).append(" ms\n");
            }
        }
    
        // Menambahkan test terakhir
        if (!currentTest.isEmpty()) {
            parsedOutput.append(currentTest).append(testPassed ? " - <PASSED>\n" : " - <FAILED>\n");
            if (errorOccurred) {
                parsedOutput.append("   ERROR: ").append(errorMessages.get(errorMessages.size() - 1)).append("\n");
            }
        }
    
        return parsedOutput.toString();
    }
}




