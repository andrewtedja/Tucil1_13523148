# Tucil1_13523148

> Tugas Kecil 1 IF2211 Strategi Algoritma - Penyelesaian IQ Puzzler Pro dengan Algoritma Brute Force

## Table of Contents

-   [Description](#description)
-   [Pre-requisites](#pre-requisites)
-   [Features](#features)
-   [How to Run](#how-to-run)
-   [Bonus Status](#bonus-status)
-   [Technologies Used](#technologies-used)
-   [Screenshots](#screenshots)
-   [Room for Improvement](#room-for-improvement)
-   [Project Structure](#project-structure)
-   [Authors](#authors)

## 📝 Description

This project is a solution finder for the IQ Puzzler Pro game implemented using a pure Bruteforce Algorithm in Java. The goal of IQ Puzzler Pro is to fill an empty board of (N x M) with uniquely shaped puzzle pieces, ensuring all pieces are used without heuristics. The program reads the input and test case files (.txt), finds a valid configuration (or report validation in terminal if none exists), and provides solution/execution details such as runtime, iteration/cases count, and a feature for file saving. This project is made both in CLI and GUI with (Maven and JavaFX).

## 🪛 Pre-requisites

-   **Running Java**: JDK 11 or higher (tested on JDK 17)
-   **Running Java supported IDE**: IDE for building project (IntelliJ IDEA, Eclipse, VSCode)
-   **Running OS**: compatible with Windows and Linux
-   **Maven**: For dependency management

## 🏃‍♂️How to Run

1. **Install Java**: Download and install Java (tested on JDK 17) from [here](https://www.oracle.com/java/technologies/downloads/).
2. **Install Maven**: Download and install Maven from [here](https://maven.apache.org/download.cgi).
3. **Clone the Repository**: Use one of the following commands to clone the repository:

    ```bash
    git clone https://github.com/andrewtedja/Tucil1_13523148.git
    ```

4. ```bash
   cd demo
   ```
5. **Build the Project**: Navigate to the project directory and run the following command to build the project:

    ```bash
    mvn clean install
    ```

6. **Run the Project**: Navigate to the project directory and run the following command to run the project:

    ```bash
    mvn javafx:run
    ```

## 🌐 Features

-   Reading file input upload from (.txt) file
-   Solving the IQPuzzler Pro using Brute force Algorithm
-   Showing information on board size (rows, columns), type of puzzle (Default/Custom/Pyramid), and the number of puzzle pieces
-   Show the solving process in both GUI and CLI (debug mode)
-   Show the solution of the board in both GUI and CLI, and also the statistics for runtime and iteration cases of the solving process
-   Allows user to save the solution in the in both matrix form (test/output.txt) and also image (test/solution.png)

## 📷 Screenshots

![Front Page](./img/front_page.png)
![Solved Page](./img/solved_page.png)
![CLI Output (debug mode)](./img/CLI.png)
![Save Solution (test/solution.png)](./img/save_solution_png.png)

![Save Solution (test/output.txt)](./img/save_output_txt.png)

## 🎁 Bonus Status

| Feature       | Status |
| ------------- | ------ |
| GUI           | ✅     |
| Save to Image | ✅     |

## 📁 Project Structure

### NOTE:

Projek ini dibuat menggunakan Maven dan JavaFX dan tidak menggunakan pendekatan executable di folder bin. Program dapat dijalankan dengan perintah Maven di bagian [How to Run](#how-to-run). Selain itu, folder demo berisi file-file yang dibutuhkan untuk menjalankan program termasuk folder src dan target. Folder doc berisi dokumen laporan.

Struktur folder:

```bash
Tucil1_13523148
├── demo
│   ├── src
│   │   └── main
│   │       ├── java
│   │       │   └── stima
│   │       │       ├── model
│   │       │       │   ├── Main.java
│   │       │       │   ├── Board.java
│   │       │       │   ├── Piece.java
│   │       │       │   ├── Solver.java
│   │       │       │   ├── ReadInput.java
│   │       │       │   └── FileData.java
│   │       │       ├── App.java
│   │       │       ├── PrimaryController.java
│   │       │       └── SecondaryController.java
│   │       └── resources
│   │           ├── stima
│   │           │   ├── primary.fxml
│   │           │   └── secondary.fxml
│   │           └── stima.css
│   └── target
│       ├── classes
│       │   └── stima
│       │       ├── model
│       │       ├── App.class
│       │       ├── PrimaryController.class
│       │       └── SecondaryController.class
│       └── demo-1.0-SNAPSHOT.jar
├── doc
├── img
├── test
│   ├── output.txt
│   └── solution.png
└── README.md

```

## 🛠️ Technologies Used

-   **Java & JDK**: Version 11 or higher (tested on JDK 17)
-   **JavaFX**: For GUI
-   **Maven**: For dependency management

## 🚧 Room for Improvement

-   Enhance type for custom and pyramid puzzle boards
-   Enhance speed and efficacy of the algorithm solver
-   Better user interface

## 🪪 Author

<table>
    <thead>
        <tr>
            <th>NIM</th>
            <th>Nama</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>13523148</td>
            <td><a href="https://github.com/andrewtedja">Andrew Tedjapratama</a></td>
        </tr>
    </tbody>
</table>
