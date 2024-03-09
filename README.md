# IMDB

## Overview
This project is an Object-Oriented Programming (OOP) approach to recreate the functionalities of the Internet Movie Database (IMDB). It encompasses a broad spectrum of the film and television industry's facets, offering detailed insights into movies, TV shows, actors, directors, and producers. By leveraging Java's OOP principles, this project aims to provide an interactive platform where users can explore extensive information about their favorite entertainment pieces.

## Objectives
- Implement OOP principles learned.
- Construct a class hierarchy based on a given scenario.
- Employ an object-oriented design to solve real-world problems.
- Handle exceptions that may occur during application runtime.
- Translate a real-life problem into a functional application.

## Application Architecture

### Classes
- **IMDB**: The main class representing the application, containing details extracted from JSON files such as lists of users (Regular, Contributor, Admin), actors, requests, and productions (Movies and Series).
- **Production**: An abstract class representing cinematic productions in the system, implementing the Comparable interface for sorting based on titles.
- **Movie and Series**: Classes extending Production, containing specific attributes like movie duration, release year, seasons, and episodes.
- **Episode**: Contains details about individual episodes within a series.
- **RequestsHolder**: A static class containing a list of all user requests needing resolution.
- **Request**: Represents a user request, containing details such as the request type, creation date, and relevant usernames.
- **Rating**: Represents user ratings for productions, containing the username, score, and comments.
- **User**: An abstract class representing system users, including personal information, account type, experience, and a sorted collection of favorite productions and actors.
- **Regular, Staff, Contributor, Admin**: User subclasses defining various user roles within the system, each with specific capabilities and responsibilities.
- **Information and Credentials**: Internal classes within User, containing user credentials and personal information.

### Interfaces
- **RequestsManager**: Contains methods for creating and removing requests.
- **StaffInterface**: Defines methods for adding/removing productions and actors from the system and managing user requests.

### Enumerations
- **AccountType**: Defines user types (Regular, Contributor, Admin).
- **Genre**: Lists all movie genres.
- **RequestTypes**: Defines possible types of user requests.

## Exceptions
Custom exceptions are thrown for invalid commands and incomplete information, ensuring the application functions correctly under all circumstances.

## Design Patterns
This project implements several design patterns, including Singleton (for the IMDB class), Builder (for instantiating Information objects), Factory (for user instantiation), Observer (for notifications), and Strategy (for experience calculation).

## Application Flow
The application guides users through a series of steps, from authentication to performing actions based on their role (Regular, Contributor, Admin), and supports both terminal and graphical user interface interactions.

## Graphical Interface
An intuitive and comprehensive GUI is developed using the SWING package, enhancing user experience with recommendations, detailed views of productions and actors, and easy navigation through the system's functionalities.
