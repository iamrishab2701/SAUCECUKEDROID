# SauceCukeDroid

SauceCukeDroid is a **Cucumber-based Appium automation framework** for **Android applications**. It utilizes **Appium, TestNG, Java, Allure, and Page Object Model (POM)** to execute automated test cases efficiently. The framework is designed to support **multiple environments (staging, integration, production)** and dynamically fetch configurations.

## 📌 Features
- **Environment-specific configurations** using `environment.yaml`
- **Appium Desired Capabilities** stored in `capabilities.json`
- **Appium Server Management** with automatic start/stop
- **Android Emulator Automation** (start and close emulator dynamically)
- **TestNG and Cucumber integration** for structured test execution
- **Allure Reports** for rich test reporting
- **Parallel Execution Support** (planned feature)

---
## 🚀 Installation & Setup
### **1️⃣ Prerequisites**
Ensure you have the following installed:
- Java 17 (recommended) or later
- Maven
- Appium Server (v2.15.0)
- Android SDK & Emulator
- Node.js & npm (for Appium)

#### **Verify Installations**
```sh
java -version
mvn -version
appium -v
adb --version
```

### **2️⃣ Clone the Repository**
```sh
git clone https://github.com/your-repo/SauceCukeDroid.git
cd SauceCukeDroid
```

### **3️⃣ Install Dependencies**
```sh
mvn clean install
```

### **4️⃣ Configure Environment & Capabilities**
#### `environment.yaml`
```yaml
environments:
  staging:
    username: "standard_user"
    password: "secret_sauce"
  integration:
    username: "locked_out_user"
    password: "secret_sauce"
  production:
    username: "problem_user"
    password: "secret_sauce"
```

#### `capabilities.json`
```json
{
  "staging": {
    "platformName": "ANDROID",
    "appium:deviceName": "Pixel_9",
    "appium:automationName": "UiAutomator2",
    "appium:app": "/Users/rishabsingh/Documents/SauceCukeDroid/application/SauceLab.apk"
  }
}
```

### **5️⃣ Run Tests**
#### Run tests for a specific environment:
```sh
mvn clean test -Denv=staging
```

#### Generate Allure Reports:
```sh
mvn allure:serve
```

---
## 📂 Project Structure
```
SauceCukeDroid/
│── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── config/ (Handles environment & capabilities)
│   │   │   ├── driver/ (Manages Appium server and emulator)
│   │   │   ├── pages/ (Page Object Model implementation)
│   │   │   ├── utils/ (Helper utilities)
│   ├── test/
│   │   ├── java/
│   │   │   ├── stepdefinitions/ (Cucumber step definitions)
│   │   │   ├── runners/ (Test execution)
│   │   ├── resources/
│   │   │   ├── features/ (Cucumber feature files)
│   │   │   ├── config/ (Config files like `capabilities.json`)
│── environment.yaml (Environment-specific data)
│── capabilities.json (Appium capabilities per environment)
│── pom.xml (Maven dependencies and configurations)
│── README.md
```

---
## ✅ Running Appium Server & Emulator
The framework automatically starts and stops the Appium server and emulator. If you prefer to start them manually:

#### **Start Appium Server**
```sh
appium --base-path /wd/hub
```

#### **Start Emulator**
```sh
emulator -avd Pixel_9
```

#### **Stop Emulator**
```sh
adb -s emulator-5554 emu kill
```

---
## 📊 Allure Reporting
After test execution, generate an Allure report:
```sh
mvn allure:serve
```
Ensure that test results are saved in `target/allure-results`.

---
## ❗ Troubleshooting
### **Issue: Allure report directory not found**
**Error:**
```
[ERROR] Directory target/allure-results not found.
```
**Solution:**
Ensure the test execution generates results in the correct folder:
```sh
rm -rf allure-results target/allure-results
mvn clean test
mvn allure:serve
```

### **Issue: Emulator not starting**
**Solution:** Check if the emulator exists:
```sh
emulator -list-avds
```
Manually start with:
```sh
emulator -avd Pixel_9
```

---
## 🔗 References
- [Appium Documentation](https://appium.io/docs/en/about-appium/intro/)
- [Cucumber Documentation](https://cucumber.io/docs/guides/10-minute-tutorial/)
- [Allure Reports](https://docs.qameta.io/allure/)

---
## 👨‍💻 Contributors
- **Rishab Singh** - QA Automation Engineer

---
## 📜 License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

