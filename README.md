# SMART COLLEGE ERP MANAGEMENT SYSTEM - Basaveshwar Engineering College, Bagalkote

A modern, responsive React-based Smart College ERP Web Application designed for **Basaveshwar Engineering College, Bagalkote**.

---

## 🏛️ STEP 1 Implementation Scope

This project implements **STEP 1** of the final-year project:
- **Home Landing Section**: Full-screen 3-image background carousel auto-changing every 2 seconds with dark readability overlay and college ERP overview.
- **About College Section**: Overview of Basaveshwar Engineering College, Bagalkote, plus responsive **All Departments** cards.
- **College Location (Google Map)**: Separate section featuring a responsive embedded Google Map of the BEC Bagalkote campus and full address details.
- **Placements Section**: Clean, focused informational section displaying:
  1. Students Placed (`[NUMBER]`)
  2. Highest Package (`[PACKAGE]`)
  3. Highest Package Student Photo (`/placement/highest-package-student.jpg`)
  4. Highest Package Student Name (`[STUDENT NAME]`)
- **Login Page (`/login`)**: Standalone portal featuring the **exact 5 roles**:
  1. `Administrator`
  2. `Accountant`
  3. `Employee`
  4. `Faculty`
  5. `Student`
  *(Includes Show/Hide password toggle and Back to Home navigation)*

---

## 📁 Project Structure

```
COLLAGE ERPP SYSTEM/
├── index.html
├── package.json
├── vite.config.js
├── README.md
│
├── public/
│   ├── college-images/
│   │   ├── college1.jpg                     <-- Campus Image 1 (Replace with your actual photo)
│   │   ├── college2.jpg                     <-- Campus Image 2 (Replace with your actual photo)
│   │   └── college3.jpg                     <-- Campus Image 3 (Replace with your actual photo)
│   └── placement/
│       └── highest-package-student.jpg      <-- Highest Package Student Photo (Replace with actual photo)
│
└── src/
    ├── components/
    │   ├── Navbar.jsx                       <-- Responsive sticky navigation bar
    │   ├── Hero.jsx                         <-- Hero section & call-to-actions
    │   ├── ImageCarousel.jsx                <-- 2-second automatic cross-fade background carousel
    │   ├── About.jsx                        <-- About College overview
    │   ├── DepartmentList.jsx               <-- All Departments configurable cards
    │   ├── MapSection.jsx                   <-- Google Maps embed & location details
    │   ├── PlacementSection.jsx             <-- Placements information cards
    │   └── Footer.jsx                       <-- College footer & Back to top
    │
    ├── pages/
    │   ├── Home.jsx                         <-- Main landing page
    │   └── Login.jsx                        <-- Standalone /login page (5 roles)
    │
    ├── styles/
    │   ├── index.css                        <-- Theme variables, fonts & resets
    │   ├── navbar.css                       <-- Navbar styles
    │   ├── hero.css                         <-- Hero & carousel animations
    │   ├── about.css                        <-- About & departments styles
    │   ├── map.css                          <-- Location & map styles
    │   ├── placement.css                    <-- Placement statistics styles
    │   ├── login.css                        <-- Login card & inputs styles
    │   ├── footer.css                       <-- Footer styles
    │   └── responsive.css                   <-- Mobile & tablet breakpoints
    │
    ├── App.jsx                              <-- React Router configuration
    └── main.jsx                             <-- App entry point
```

---

## 🚀 How to Run in Visual Studio Code (or Terminal)

1. Open the project folder in **Visual Studio Code**:
   ```bash
   cd "COLLAGE ERPP SYSTEM"
   ```

2. Install dependencies (if not already installed):
   ```bash
   npm install
   ```

3. Start the local development server:
   ```bash
   npm run dev
   ```

4. Open `http://localhost:5173` in your browser.

5. Build for production:
   ```bash
   npm run build
   ```

---

## 🖼️ Replacing Images with Your Actual College Photos

- **Campus Carousel Images**: Place your 3 campus JPG photos inside `public/college-images/` named:
  - `college1.jpg`
  - `college2.jpg`
  - `college3.jpg`
- **Placement Student Photo**: Place the photo of the student who received the highest package in `public/placement/` named:
  - `highest-package-student.jpg`

---

## 🛠️ Technology Stack
- **Frontend**: React 19, JavaScript (ES6+), HTML5, CSS3, Lucide React Icons, React Router DOM
- **Backend (Upcoming in STEP 2)**: Java 21, Spring Boot, Spring Security, JWT, Spring Data JPA / Hibernate, MySQL
