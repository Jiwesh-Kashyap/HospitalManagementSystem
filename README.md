# 🏥 Hospital Management System (HMS)

[![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)](https://www.java.com/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)](https://springboot.io/)
[![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)](https://www.mysql.com/)
[![Swing](https://img.shields.io/badge/Java_Swing-007396?style=for-the-badge&logo=java&logoColor=white)](https://docs.oracle.com/javase/8/docs/technotes/guides/swing/)

A robust and intuitive **Hospital Management System** designed to streamline patient appointments, medical records, and billing processes. Built with a high-performance Java Spring Boot backend and a responsive Java Swing desktop interface.

---

## 🚀 Quick Start Guide

Follow these steps to explore the system as a patient:

1.  **Signup / Login**: Click the **Signup** button to create a patient profile. *Ensure you save your password securely!*
2.  **Patient Dashboard**: Once logged in, you'll be directed to your personalized dashboard.
3.  **Book Appointment**: Click the **Book an Appointment** button.
4.  **Specialist Selection**: Choose a doctor based on your health condition and select a convenient date and time.
5.  **Confirmation**: Your appointment will now appear on your dashboard and the selected doctor's portal.

---

## 👨‍⚕️ Doctor Portal & Dashboard

Doctors have a dedicated interface to manage their schedules and patient care:

-   **Appointment Management**: Double-click any appointment to open the management popup.
-   **Status Updates**: Switch status between *Pending*, *Confirmed*, *Completed*, or *Cancelled*.
-   **Patient Care**:
    -   📝 **Clinical Notes**: Add notes for internal reference or patient guidance.
    -   📁 **Medical Records**: Upload PDF records, lab results, or prescriptions directly to the appointment.
-   **Financials**: Generate professional bills for patient reference upon completion of services.

### 🔑 Pre-Configured Doctor Profiles
Use these credentials to test the Doctor Dashboard (Password: `doctor123` for all):

| Specialist | Email Address |
| :--- | :--- |
| **Diabetes Specialist** | `diabetes_doc@apollo.com` |
| **Pediatrics** | `pediatrics_doc@apollo.com` |
| **Dental Surgeon** | `dental_doc@apollo.com` |

> [!TIP]
> Use any of the names above in the format `name_doc@apollo.com` to access other specialist profiles.

---

## 🛠 Tech Stack

-   **Backend**: Java Spring Boot (REST API, JPA/Hibernate)
-   **Frontend**: Java Swing (Desktop UI with custom styling)
-   **Database**: MySQL (Persistent storage)
-   **Tooling**: Maven, Shields.io

---

## 🏗 Data Models

-   **Appointment**: Tracking schedules, status, and patient-doctor links.
-   **Person / Patient / Doctor**: Comprehensive identity management using inheritance.
-   **Bill**: Automated financial record generation.
-   **MedicalRecord**: Secure storage for clinical data and attachments.

---

## 📸 System Overview

![Doctor Profiles Reference](https://github.com/user-attachments/assets/7f10d904-27bd-4d33-a973-d4fb5bbe91f1)

---

*Developed with ❤️ for efficient healthcare management.*
  
