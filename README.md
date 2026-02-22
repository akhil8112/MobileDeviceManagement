📱 Mobile Device Management (MDM) System
Moveinsync App – Backend Implementation (Spring Boot)
📌 Project Overview

This project implements a Mobile Device Management (MDM) Backend System for managing devices running the Moveinsync mobile application.

The system enables:

Centralized device registry

Version control and compliance enforcement

Controlled region-based update rollouts

Strict downgrade prevention

Update lifecycle tracking

Complete auditability of device updates

This solution is built using:

Java 17

Spring Boot

Spring Data JPA

MySQL

Maven
                         ┌─────────────────────────────┐
                         │        Admin (Web UI)       │
                         │  Schedules Update Rules     │
                         └──────────────┬──────────────┘
                                        │
                                        ▼
                         ┌─────────────────────────────┐
                         │   UpdateScheduleController  │
                         │   (/api/update/schedule)    │
                         └──────────────┬──────────────┘
                                        │
                                        ▼
                         ┌─────────────────────────────┐
                         │  UpdateScheduleService      │
                         │  - Downgrade Prevention     │
                         │  - Version Validation       │
                         └──────────────┬──────────────┘
                                        │
                                        ▼
                         ┌─────────────────────────────┐
                         │       update_schedule DB    │
                         └─────────────────────────────┘



                         ┌─────────────────────────────┐
                         │     Mobile Device (App)     │
                         │  Sends Heartbeat & Status   │
                         └──────────────┬──────────────┘
                                        │
                                        ▼
                         ┌─────────────────────────────┐
                         │     DeviceController        │
                         │  (/api/device/heartbeat)    │
                         │  (/api/device/update-status)│
                         └──────────────┬──────────────┘
                                        │
                                        ▼
                         ┌─────────────────────────────┐
                         │       DeviceService         │
                         │  - Update lastOpenTime      │
                         │  - Check Schedule Rules     │
                         │  - Create Tracking Record   │
                         │  - Update Version on SUCCESS│
                         └──────────────┬──────────────┘
                                        │
                                        ▼
          ┌─────────────────────────────┼─────────────────────────────┐
          │                             │                             │
          ▼                             ▼                             ▼
 ┌─────────────────┐         ┌─────────────────────┐        ┌─────────────────┐
 │   device DB     │         │  update_schedule DB │        │ device_update DB│
 │ (Registry)      │         │ (Admin Rules)       │        │ (Lifecycle Log) │
 └─────────────────┘         └─────────────────────┘        └─────────────────┘

🎯 Objectives Achieved

This project fulfills the following business and technical requirements:

👉🏻 1. Centralized Device Registry

Each device is uniquely registered using:

deviceId

IMEI

Region

App Version

OS / Model

Stores:

createdAt

lastOpenTime

Provides region-wise device inventory APIs.

👉🏻  2. Version Repository & Update Scheduling

Admin can schedule updates based on:

Region

From Version

To Version

Supports:

Mandatory updates

Version transition control

Downgrade prevention enforced at scheduling level.

👉🏻 3. Heartbeat-Based Compliance Check

Each time app opens, device calls heartbeat API.

Backend:

Updates last open time

Validates version compliance

Checks if update is required

Creates device-level update tracking record

👉🏻 4. Downgrade Prevention

System blocks downgrade attempts at:

Admin scheduling stage

Device heartbeat validation stage

Update execution stage

Example blocked scenario:

Current Version: 4.3
Scheduled Version: 4.1
→ Rejected

👉🏻 5. Device Update Lifecycle Tracking

Every update follows a strict lifecycle:

SCHEDULED
DOWNLOAD_STARTED
INSTALLATION_STARTED
SUCCESS
FAILED

All update attempts are stored in device_update table for full audit tracking.

👉🏻  6. Update Status Reporting

Devices report update progress via:

POST /api/device/update-status

On SUCCESS:

Device version is updated automatically in device table.

🏗 System Architecture
Controller Layer
    ↓
Service Layer (Business Logic)
    ↓
Repository Layer (JPA)
    ↓
Database (MySQL)
📂 Project Structure
com.moveinsync.mdm
│
├── MdmApplication.java
│
├── controller
│   ├── DeviceController.java
│   ├── UpdateScheduleController.java
│   └── DeviceUpdateController.java
│
├── service
│   └── DeviceService.java
│
├── repository
│   ├── DeviceRepository.java
│   ├── UpdateScheduleRepository.java
│   └── DeviceUpdateRepository.java
│
├── entity
│   ├── Device.java
│   ├── UpdateSchedule.java
│   ├── DeviceUpdate.java
│   └── UpdateStatus.java
│
├── dto
│   ├── HeartbeatRequest.java
│   ├── HeartbeatResponse.java
│   └── UpdateStatusRequest.java
🗄 Database Tables

1️⃣ device

Stores registered device details.

Column	Description
device_id	Unique device identifier
imei	Device IMEI
app_version	Current installed version
region	Device region
last_open_time	Last heartbeat time
2️⃣ update_schedule

Admin-defined update rules.

Column	Description
from_version	Current version
to_version	Target version
region	Region to apply
mandatory	Is update mandatory
3️⃣ device_update

Tracks update lifecycle per device.

Column	Description
device_id	Device ID
from_version	Previous version
to_version	Target version
status	Update stage
updated_at	Timestamp
🔌 API Documentation (Postman Testing)
🔹 Register Device
POST /api/device/register

Registers a new device.

🔹 Get Device
GET /api/device/{deviceId}

Returns device details.

🔹 Get Devices by Region
GET /api/device/region/{region}

Returns region-wise device list.

🔹 Schedule Update (Admin)
POST /api/update/schedule

Creates update rule for region and version.

🔹 Heartbeat API
POST /api/device/heartbeat

Updates last_open_time

Checks version compliance

Creates update tracking if required

🔹 Update Status API
POST /api/device/update-status

Device reports progress:

DOWNLOAD_STARTED
INSTALLATION_STARTED
SUCCESS
FAILED

On SUCCESS:

Device version auto-updated.

🔄 Full Update Workflow

1️⃣ Device registers (version 4.1)
2️⃣ Admin schedules update (4.1 → 4.3)
3️⃣ Device sends heartbeat
4️⃣ Backend detects update required
5️⃣ Creates tracking record (SCHEDULED)
6️⃣ Device installs update
7️⃣ Device sends update-status SUCCESS
8️⃣ Backend updates device version to 4.3


🧪 How to Run
git clone <repo-url>
cd mdm
mvn clean install
mvn spring-boot:run

Server runs at:

http://localhost:8080
