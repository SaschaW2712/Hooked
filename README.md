# Hooked
A simple Ravelry aid, integrating with the [Ravelry API](https://www.ravelry.com/api) to provide easier mobile exploration of Ravelry's catalogue of knitting and crochet patterns.

## Background

Ravelry is a website for knitters and crocheters that provides a way to find and purchase patterns, track active projects, and connect with other people in the fibre arts communities. The website itself has a wide variety of features, but I've always found the site to be frustrating and clumsy to use in a mobile web browser.

As no official Ravelry mobile app exists, they have made an API for integrating with their site available, for other developers to use for creation of derivative applications, free of charge. Hooked uses this API, to provide basic functionality outlined in [Features](#features) below.

This application currently supports native Android with a fully Jetpack Compose based UI, following industry best practices and modern frameworks as endorsed by Google and the official Android Developer documentation. The architecture of the app is explained in [Architecture](#architecture).


## Features
Hooked is a relatively new application, and an active work in progress. It isn't yet available on the Google Play Store, but an approximate roadmap is provided. Most basic app functionality planned for the initial release has been completed; the core pieces remaining are in DevOps processes to support future work.

Current features:
- Connecting with a user's Ravelry account, and remaining authenticated with it through app restarts
- Finding the current most popular patterns on the site (what is "hot right now")
- Searching for specific patterns
- Viewing additional details of a pattern, with the ability to save it to the user's Ravelry favorites in a native mobile experience, or open the full pattern in the browser
- See the user's current favorites

Upcoming improvements:
1. Simple automated build pipelines with CircleCI, to test minimum requirements of compilation.
2. Instrumented tests and UI testing for the Discover screen at a minimum
    - _To be integrated within the build pipeline._
3. Integration with SonarQube (or an equivalent solution), for easier monitoring of code health and test coverage reports.
4. Registration and limited publication on the Google Play store, to be made available to alpha testers on request.


## Architecture
The app follows a multi-modular MVVM architecture.

Modules are separated between `core` and `feature`:
- `core` modules contain central app functionality, that may be necessary across features and for other core functionality to operate.
- `core` modules may have dependencies between them, and are depended upon by `feature` modules.
- `feature` modules provide distinct features of the app, such as Onboarding, Favorites, or the Discover page.
- `feature` modules cannot depend on other `feature` modules, only `core`.

![alt_text](https://github.com/SaschaW2712/Hooked/blob/main/documentation/module-architecture-diagram.png "Module architecture diagram of Hooked")

## Development Processes
This project was developed following standard agile practices with a Kanban structure. Work is tracked in a Jira board with a small backlog of tasks organised into Epics, with pull requests and commits tracked using the Jira Github integration. Planning and minimal technical documentation is stored in a Confluence space dedicated to the project.

Commit messages are written loosely following [Conventional Commits v1.0.0](https://www.conventionalcommits.org/en/v1.0.0/). This provides easier parsing of types of commits at a glance, and links all commits with the ID of the ticket that they relate to, to maintain future context of why code decisions were made.

Figma was used for some early wireframes, though all higher-fidelity work was developed through iterative Compsoe prototyping and testing.
