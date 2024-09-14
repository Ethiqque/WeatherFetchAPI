# City Temperature FullStack WebApp: Loads 100 city temperature using openweathermap API

## Introduction

Functionality requirements.
When application starts, script loads 100 city names to database and queries current temperature for all 100 cities.
After every restart, script should delete all temperature data and load new data.
Weather should be asked through this API - https://openweathermap.org/current#cityid
Frontend should have one pager where you can see table of cities and current temperatures.
Table should be sortable by city name and temperature.
Table should have pagination so that user can choose either 15 or 25 results per page
 
Nice to have:
* UI responsive design
* frontend nor backend cache for weather data
* automated scheduled task / cron job to update weather data daily
* Dockerfile to run the application as Docker container

Video of using:
https://drive.google.com/file/d/1vCaQoSEfN_X_X-or3n7iXhF0az2yi2-r/view?usp=sharing

<img width="1440" alt="Screenshot 2024-09-12 at 05 13 18" src="https://github.com/user-attachments/assets/44e75fc3-d9ff-4933-ac62-59538e6d7cca">
<img width="1440" alt="Screenshot 2024-09-12 at 05 13 53" src="https://github.com/user-attachments/assets/22e797aa-cdfd-440a-9040-7cba90ad6a5d">

