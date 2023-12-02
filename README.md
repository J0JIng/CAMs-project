# SC2002-Project: Camp Application and Management System (CAMs)

![UML Class Diagram](https://img.shields.io/badge/UML%20Class%20Diagram-1976D2?style=for-the-badge&logoColor=white)
![Solid Design Principles](https://img.shields.io/badge/SOLID%20Design%20Principles-C71A36?style=for-the-badge&logoColor=white)
![OOP Concepts](https://img.shields.io/badge/OOP%20Concepts-C71A36?style=for-the-badge&logoColor=white)
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)
![Git](https://img.shields.io/badge/git-%23F05033.svg?style=for-the-badge&logo=git&logoColor=white)

## **Team:** 

[<img src="https://github.com/J0JIng.png" height="20" width="20" /> O Jing ](https://github.com/J0JIng)
[<img src="https://github.com/Zaiqin.png" height="20" width="20" /> Zai Qin](https://github.com/Zaiqin) |
[<img src="https://github.com/kyew003.png" height="20" width="20" /> Kenneth](https://github.com/kyew003) |
[<img src="https://github.com/sharpwoofer.png" height="20" width="20" /> Hongqi](https://github.com/sharpwoofer)

## Overview 

![image](https://github.com/J0JIng/SC2002-Project/assets/111691710/a532c631-bd91-4bbd-b0d2-58ab7e92f2a2)


CAMs is a Java-based Command Line Interface (CLI) application for staff and students to manage, view and register for camps within NTU. The application will act as a centralized hub for all staff and students

## Usage

Ensure you have Java 11 or above installed on your Computer. You can check by opening a command terminal and typing java -version.

## Compiling and Running the project
### Using the terminal

These setup instructions will guide you through the process of cloning the repository, navigating to the cloned repository, compiling the project, and running the project in your terminal.

1. Open your terminal

2. Clone the repository by entering the following command:

   ```bash
   git clone https://github.com/J0JIng/SC2002-Project.git
   
   ```

3. Navigate to the cloned repository by entering the following command:

   ```bash
   cd SC2002-Project
   ```

4. Compile the project by entering the following command:

   ```bash
   javac -encoding UTF-8 -cp src -d bin src/main/CAMs.java
   ```

5. Run the project by entering the following command:

   ```bash
   java -cp bin main.CAMs
   ```

Congratulations, you have successfully cloned, compiled, and run the CAMs Application!

### Using Eclipse

If you prefer to use Eclipse as your IDE, you can also set up the project there. Here are the steps you need to follow:

1. Open Eclipse
2. Click on `File` > `Import` > `Git` > `Projects from Git` > `Clone URI`
3. In the `Clone URI` window, paste the following URL:

   ```bash
   https://github.com/J0JIng/SC2002-Project.git
   ```

4. Click `Next` and follow the prompts to finish the cloning process
5. Once the project is cloned, right-click on the project folder and select `Properties`
6. In the `Properties` window, click on `Java Build Path` > `Source` > `Add Folder`
7. Select the `src` folder from the project directory and click `OK`
8. Now you can run the project by right-clicking on `CAMs.java` in the `src/main` folder and selecting `Run As` > `Java Application`

That's it! You should now have the project up and running in Eclipse.

## Login Credentials

This section contains some login credentials for users with different access levels. The full list is available in `data/staff_list.csv` and `data/student_list.csv` files. The default password is `password`.
User ID will be the NTU network user ID, that is the part before @ in email address

**Staff:**

```bash
# Staff 1
Name: Arvind
Email: ARVI@ntu.edu.sg
Faculty: NBS

# Staff 2
Name: Alexei
Email: OURIN@ntu.edu.sg
Faculty: ADM
```

**Students:**

```bash
# Student 1
Name: CHERN
Email: YCHERN@e.ntu.edu.sg
Faculty: SCSE

# Student 2
Name: KOH
Email: KOH1@e.ntu.edu.sg
Faculty: ADM
```
