# Taskly
![Version](https://img.shields.io/badge/version-v1.2.0-green)

> Taskly is a simple and intuitive To-Do application built with Kotlin and Android. This MVP project demonstrates core Android development concepts and Room persistence, while applying Material Design for a modern UX.

## 💡 Overview

Taskly helps users organize their daily tasks efficiently, providing a clean and modern interface to **create, edit, view, and persist tasks locally**.  
The project was developed to consolidate knowledge in Android fundamentals, Room database, and UI/UX design with Material Components.

Key concepts covered:

- Android project structure and Activity lifecycle  
- Layout creation using XML + ConstraintLayout  
- UI components: `TextView`, `EditText`, `Spinner`, `Button`, `FloatingActionButton`  
- List rendering with `RecyclerView`, custom `Adapter`, and **DiffUtil** for efficient updates  
- ViewBinding for safer access to views  
- Local persistence with **Room Database** (DAO, entities, queries)  
- MVP architecture for maintainable code  
- Material Design theming and components  

## 🚀 Technologies

- Kotlin  
- Android Studio  
- Android SDK / AndroidX  
- ViewBinding  
- RecyclerView + DiffUtil  
- Material Components  
- Room (SQLite abstraction library)   

## ✅ Features

**Task Management**  
- Create tasks with **title** (required) and **description** (optional)  
- Set **priority levels** (Low, Medium, High)  
- Add an optional **due date**  
- Edit and delete tasks  
- Mark tasks as completed  
- Tasks stored locally with Room database  

**UX & UI**  
- Material Design components (theming, typography, icons)  
- Modern and responsive layouts using ConstraintLayout  
- FloatingActionButton for quick task creation  
- Priority indicator with color coding  
- Dark Mode support (native Android theme)  

**Architecture & Code Quality**  
- MVP pattern for separation of concerns  
- DiffUtil for efficient RecyclerView updates  
- ViewBinding for type-safe view access  
- Improved validation and date persistence  

## 🚀 How to Run
1. Clone the repository:
```
git clone https://github.com/math-nazario/taskly.git
```
2. Open the project in **Android Studio**
3. Build and run the app on an emulator or physical device
4. Add tasks using the "+" button

## 📝 Roadmap

### High Priority ✅ Done
- Edit and delete tasks  
- Mark tasks as completed  

### Medium Priority ⏳ Next
- Task cards with richer UI (icons, better styling) 
- Animations and transitions for improved UX  

### Future Ideas 💡
- Cloud sync with API backend
- Notifications and reminders
- Search and filtering improvements
