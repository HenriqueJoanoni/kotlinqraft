# Data Transfer Protocols - Kotlin & GraphQL (Android Demo)

Welcome to **Data Transfer Protocols**, an Android demo project built in Kotlin to showcase GraphQL as a flexible data transfer protocol.

## Students
- Theo Picar
- Harris Teh Kai Ze
- Cheryl Kong
- Jose Henrique Pinto Joanoni

## 🚀 Project Overview

This project demonstrates how to integrate GraphQL within a native Android app using Kotlin. It includes basic usage of Apollo GraphQL to query protocol data and visualize results.

## ⚡ Why GraphQL?

- **Flexible queries:** Fetch exactly what you need.
- **Efficiency:** Reduces the amount of data transferred.
- **Strong typing & introspection:** Better developer experience.

## 🛠️ Tech Stack

- **Android Studio**
- **Kotlin**
- **Apollo GraphQL (Apollo Kotlin)**
- **Sample UI**: Basic display of query results

## 📦 Getting Started

1. **Clone the repository**
    ```
    git clone https://github.com/HenriqueJoanoni/kotlinqraft.git
    cd KotlinQraft
    ```

2. **Open with Android Studio**
    - Select "Open an existing project" and choose the cloned directory.

3. **Install Dependencies**
    - Apollo is configured in `build.gradle`.
    - Make sure you have an internet connection and let Gradle sync.

4. **Run the App**
    - Connect an emulator or Android device.
    - Click **Run** in Android Studio.

## 📝 Example GraphQL Query

Apollo GraphQL will make a query like:
```graphql
query {
  protocols {
    name
    description
  }
}
```
Typical result:
```json
{
  "data": {
    "protocols": [
      {"name": "HTTP", "description": "HyperText Transfer Protocol"},
      {"name": "WebSocket", "description": "Full-duplex protocol"}
    ]
  }
}
```

## 🧩 Project Structure

- `/app/src/main/java` - Kotlin source code
- `/app/src/main/graphql` - GraphQL queries and schema
- `/app/src/main/res/layout` - UI files
- `/build.gradle` - Project dependencies

## ✨ Learning Goals

- Understand and implement GraphQL queries in Android (Kotlin)
- Experience the efficiency and flexibility of modern data transfer protocols

## 📚 References

- [Apollo Kotlin for Android](https://www.apollographql.com/docs/kotlin/)
- [Kotlin Android Documentation](https://developer.android.com/kotlin)
- [GraphQL Official Docs](https://graphql.org/)
