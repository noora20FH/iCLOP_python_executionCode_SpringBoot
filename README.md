

## iCLOP Learning Platform 2024

This project emphasizes full-stack development, combining expertise in Python testing, backend development with Laravel, and frontend development to create an effective and user-friendly learning system.

## Contributions
- Developing and maintaining an automated testing framework using Python to validate code and learning materials.

- Integrating this testing framework with the Laravel backend of the iCLOP platform.

- Designing and developing the user interface (user pages) for an optimal learning experience, including:

    - Data Analytics with Python Learning Page

    - Detailed Material Page

- Building an API to connect the Python testing framework with the Spring Boot framework, used in the Python code validation process.
  
  ## SpringBoot: 
    - create logic to execute the Python files
    - get the Output in JSON format

## Technologies Used:
- Frameworks: SpringBoot, Bootstrap

- Programming Languages: Java


## Notes:
- check the python directory destination path inside PythonController.java file:
      - iCLOP_python_executionCode_SpringBoot\src\main\java\com\example\demo\controller\PythonController.java
- Python testing file location in local: "E:\\00_Skripsi_2023_2024\\iCLOP_learning_platform_2024\\iCLOP_learning_platform_2024\\TestPython"; 
      

install libraries inside the directory where the TestPython folder is located
- numpy: pip install numpy
- pandas: pip install pandas
- codewars_test framework: pip install git+https://github.com/codewars/python-test-framework.git#egg=codewars_test              

Start SpringBoot: 
- mvn spring-boot:run
