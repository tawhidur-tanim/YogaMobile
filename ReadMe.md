# Yoga App (d’Yoga)

The Yoga App is a mobile application for managing and booking yoga classes.  
It consists of two connected applications:

- **Admin App** – for creating and managing yoga courses and class schedules  
- **Customer App** – for browsing, filtering, booking, and checking out yoga classes  

The project is built using **Android (Java)** and **React Native**, with **Firebase** handling authentication and cloud data synchronization.

---

## Screenshots

### Admin App

**Add a new course**
![Add Course](screenshots/image1.jpg)

**Form validation for required fields**
![Validation Error](screenshots/image3.jpg)

**View and manage courses**
![Your Courses](screenshots/image2.jpg)

**Search courses**
![Search Courses](screenshots/image5.png)

**Empty state when no courses exist**
![Empty State](screenshots/image6.jpg)

---

### Customer App

**Browse available class instances**
![Class List](screenshots/image15.jpg)

**Search and filter classes**
![Filter Classes](screenshots/image16.jpg)

**Apply booking and time filters**
![Apply Filter](screenshots/image14.jpg)

**Add classes to cart**
![Add to Cart](screenshots/image13.png)

**Checkout selected classes**
![Checkout](screenshots/image4.png)

---

## Technology Used

- Android (Java) – Admin application  
- React Native with Expo – Customer application  
- Firebase Authentication  
- Firebase Firestore (Cloud Database)

---

## Installation

The repository contains two main folders:

1. **dyoga** – Admin application (Android Studio)
2. **dyogaCustomer** – Customer application (React Native)

---

### dyoga (Admin App)

**Requirements**
- Android Studio  
- Android emulator or physical device  

Open the `/dyoga` folder in Android Studio, allow the project to build, and run it on an emulator or physical device.

---

### dyogaCustomer (Customer App)

**Requirements**
- Node.js and npm  
- Android or iOS emulator  
- Visual Studio Code (recommended)

**Steps**
1. Open the `/dyogaCustomer` folder  
2. Install dependencies:
   ```bash
   npm install
