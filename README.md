We developed a telecom customer onboarding platform that automates SIM activation using Aadhaar-based eKYC verification. The system verifies users through Aadhaar APIs, automatically fetches customer details, and allows personalized telecom plan selection.

The backend was built using Java and Spring Boot with secure APIs implemented using Spring Security, while the frontend was developed using React. The system was deployed on AWS EC2 with MySQL hosted on AWS RDS. We tested the platform with over 500 mock user signups to validate performance and scalability.

System Architecture :
Frontend (React)
        |
        | REST APIs
        |
Backend (Spring Boot)
        |
        | Authentication + Business Logic
        |
Aadhaar eKYC API
        |
Database (MySQL - AWS RDS)
        |
Cloud Infrastructure (AWS EC2)

Architecture on AWS :
Users
  |
React Frontend
  |
AWS EC2
  |
Spring Boot APIs
  |
AWS RDS (MySQL)

Unitel Telecom Customer Onboarding Platform is a digital onboarding system for telecom users that allows customers to quickly activate SIM services using Aadhaar-based eKYC verification.

Traditionally, telecom companies require customers to visit a store and submit documents manually. This project digitizes the process by:

Verifying customer identity using Aadhaar eKYC APIs

Automatically fetching user details

Allowing personalized telecom plan selection

Activating the connection digitally

The platform was tested with 500+ mock user signups and deployed on AWS using EC2 and RDS.
