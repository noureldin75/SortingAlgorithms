# 🔀 Sorting Algorithm Visualizer & Comparator

A **JavaFX desktop application** that benchmarks and visualizes six classic sorting algorithms side-by-side. Built for the Data Structures & Algorithms course (Lab 1).

![Java](https://img.shields.io/badge/Java-17%2B-orange?logo=openjdk)
![JavaFX](https://img.shields.io/badge/JavaFX-21-blue?logo=java)
![Maven](https://img.shields.io/badge/Maven-3.9-C71A36?logo=apachemaven)

---

## ✨ Features

### 📊 Sorting Comparison
- Benchmark **6 sorting algorithms** on arrays of configurable size
- Choose array type: **Random**, **Sorted**, or **Reversed**
- Run multiple iterations and view **averaged results**
- Metrics tracked: **Avg/Min/Max Time (ms)**, **Comparisons**, **Interchanges**
- Load arrays from **text/CSV files** or auto-generate them
- **Export results to CSV** for external analysis

### 🎬 Sorting Visualization
- Animated bar-chart visualization of any algorithm in action
- **Color-coded bars**: 🔴 current element · 🟢 compared element · 🔵 default
- Adjustable **speed slider** (5ms – 200ms per frame)
- **Play / Pause / Reset** controls
- Live metrics: comparisons, interchanges, and step counter
- Array sizes from 5 to 100 elements

---

## 📋 Supported Algorithms

| Algorithm | Best | Average | Worst | Space |
|---|---|---|---|---|
| Selection Sort | O(n²) | O(n²) | O(n²) | O(1) |
| Insertion Sort | O(n) | O(n²) | O(n²) | O(1) |
| Bubble Sort | O(n²) | O(n²) | O(n²) | O(1) |
| Merge Sort | O(n log n) | O(n log n) | O(n log n) | O(n) |
| Heap Sort | O(n log n) | O(n log n) | O(n log n) | O(1) |
| Quick Sort | O(n log n) | O(n log n) | O(n²) | O(log n) |

---

## 🏗️ Project Structure

```
src/main/java/com/example/sorting/
├── HelloApplication.java          # JavaFX Application entry point
├── Launcher.java                  # Main class launcher
├── MainController.java            # FXML Controller (comparison + visualization)
│
├── algorithms/                    # Sorting implementations
│   ├── SortingAlgorithms.java     # Strategy interface
│   ├── SelectionSort.java
│   ├── InsersionSort.java
│   ├── BubbleSort.java
│   ├── MergeSort.java
│   ├── HeapSort.java
│   ├── QuickSort.java
│   ├── SortResult.java            # Sort metrics container
│   └── sortStep.java              # Visualization step (Builder pattern)
│
├── model/
│   └── ComparisonResult.java      # TableView data model (JavaFX Properties)
│
└── util/
    ├── AlgorithmFactory.java      # Factory for algorithm instantiation
    ├── ArrayGenerator.java        # Random / Sorted / Reversed array generation
    ├── DrawHelper.java            # Bar-chart rendering
    └── FileHandler.java           # File import & CSV export

src/main/resources/com/example/sorting/
├── sorting-view.fxml              # UI layout
└── styles.css                     # Catppuccin dark theme
```

---

## 🎨 Design Patterns

| Pattern | Where | Purpose |
|---|---|---|
| **Strategy** | `SortingAlgorithms` interface | Interchangeable algorithm implementations |
| **Factory** | `AlgorithmFactory` | Create algorithms from string names |
| **Builder** | `sortStep.Builder` | Flexible visualization step construction |
| **MVC** | Controller + FXML + Model | Separation of UI, logic, and data |

---

## 🚀 Getting Started

### Prerequisites

- **Java 17** or later
- **Maven 3.9+**
- JavaFX SDK (managed via Maven dependencies)

### Build & Run

```bash
# Clone the repository
git clone https://github.com/noureldin75/SortingAlgorithms.git
cd SortingAlgorithms

# Build the project
mvn clean compile

# Run the application
mvn javafx:run
```

### Alternative — Run from IDE

1. Open the project in **IntelliJ IDEA** (recommended) or Eclipse
2. Ensure Maven dependencies are resolved
3. Run `Launcher.java` as the main class

---

## 📁 Input File Format

The application accepts `.txt` or `.csv` files containing integers separated by commas or whitespace:

```
42, 17, 89, 3, 56, 12, 95, 28, 64, 7
```

Or one number per line:

```
42
17
89
3
56
```

---

## 📸 Screenshots

### Comparison Tab
The comparison tab allows selecting algorithms, configuring array parameters, and viewing benchmarked results in a data table with progress tracking.

### Visualization Tab
The visualization tab renders a real-time bar-chart animation of the selected sorting algorithm with play/pause controls and live performance metrics.

---

## 🛠️ Tech Stack

- **Language:** Java 17
- **UI Framework:** JavaFX 21
- **Build Tool:** Apache Maven
- **Architecture:** MVC (Model–View–Controller)
- **Styling:** Custom CSS (Catppuccin Mocha palette)

---

## 📄 License

This project is developed for educational purposes as part of the DSA course curriculum.
