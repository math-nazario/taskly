# Taskly
![Version](https://img.shields.io/badge/version-v1.0.0-green)

> Taskly is a simple and intuitive To-Do application built with Kotlin and Android. This MVP project demonstrates core Android development concepts and Room persistence, while applying Material Design for a modern UX.

## 💡 Overview

Taskly helps users organize their daily tasks efficiently, providing a simple interface to **create, view, and persist tasks locally**.  
This educational project was developed to consolidate knowledge in Android fundamentals, Room database, and Material Design.

Key concepts covered:

- Basic structure of an Android project
- Usage of `Activity` and lifecycle methods
- Layout creation using XML
- UI components: `TextView`, `EditText`, `Button`, `FloatingActionButton`
- List rendering with `RecyclerView` and custom `Adapter`
- ViewBinding for safer access to views
- Navigation between screens
- Local data persistence using **Room Database** (DAO, entities, queries)
- MVP architecture implementation
- UX improvements with Material Design

> **Note:** This is a Minimum Viable Product (MVP). Features like task editing, deletion, completion status, priorities, and due dates will be implemented in future versions.

## 🚀 Technologies

- Kotlin  
- Android Studio  
- Android SDK / AndroidX  
- ViewBinding  
- RecyclerView  
- Material Components  
- Room (SQLite abstraction library)  

## ✅ Features

**Task Management**  
- Create tasks with title and description  
- List all saved tasks in a RecyclerView  
- Local persistence using Room database (DAO, Entity, Database)  

**UX & UI**  
- Material Design components  
- FloatingActionButton for quick task creation  
- Responsive layouts using ConstraintLayout  

**Architecture & Code Quality**  
- MVP pattern for maintainable code  
- ViewBinding for safer view access  

## 🚀 How to Run
1. Clone the repository:
```
git clone https://github.com/math-nazario/taskly.git
```
2. Open the project in **Android Studio**
3. Build and run the app on an emulator or physical device
4. Add tasks using the "+" button

## 📝 Next Steps

**High Priority**
- Edit and delete tasks
- Mark tasks as completed

**Medium Priority**
- Add due dates and priorities

**Low Priority**
- Task cards with icons and better styling
- Animations and improved Material components
