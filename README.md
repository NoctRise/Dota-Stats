
<div align="center">
  <a >
    <img src="images/logo.png" alt="Logo" width="200" height="200">
  </a>
  </div>  



# Dota Stats

  

>[!NOTE] 
>This app requieres a valid network connection to pull data from the API.<br>Minimal [SDK version](https://apilevels.com) required to run the app: **24**

  
<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#overview">Overview</a></li>
    <li><a href="#features">Features</a></li>
    <li><a href="#tech-usage">Tech Usage</a></li>
    <li><a href="#built-with">Built with</a></li>
    <li><a href="#author">Author</a></li>
  </ol>
</details>



## Overview

  

Dota Stats is a kotlin based android app which provides information of played matches for Dota 2. This app relies on the [OpenDota](https://www.opendota.com/) API
to get the latest match results.

## Features
- **Recent Matches**
- **Team Ranking**
- **Team Profile**
- **Match Details**
- **Player Profile**

## Screenshots

<p float="left">
  <img src="images/home.png" width="200" />
  <img src="images/team_ranking.png" width="200" />
  <img src="images/team_profile.png" width="200" />
  <img src="images/match_detail1.png" width="200" />
  <img src="images/match_detail2.png" width="200" />
  <img src="images/match_detail3.png" width="200" />
  <img src="images/match_detail4.png" width="200" />
  <img src="images/player_profile.png" width="200" />
  
</p>


## Tech Usage
- **[MVVM Pattern](https://www.geeksforgeeks.org/mvvm-model-view-viewmodel-architecture-pattern-in-android/)**
- **[Fragments](https://developer.android.com/guide/fragments)**
- **[ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)**
- **[LiveData](https://developer.android.com/topic/libraries/architecture/livedata)**
- **[Navigation components](https://developer.android.com/guide/navigation/get-started)**
- **[RecyclerView](https://developer.android.com/reference/androidx/recyclerview/widget/RecyclerView)** 


## Built with
- **[Room Database](https://developer.android.com/training/data-storage/room)** for caching
- **[Retrofit2](https://github.com/square/retrofit)** for network requests
- **[Moshi](https://github.com/square/moshi)** for JSON serialization/deserialization
- **[Coil](https://github.com/coil-kt/coil)** for image handling
- **[MPAndroidChart](https://github.com/PhilJay/MPAndroidChart)** displaying charts
- **[android-gif-drawable](https://github.com/koral--/android-gif-drawable)** displaying gifs
- **[Ksp](https://github.com/google/ksp)** annotation processing 
- **[Material 3](https://m3.material.io)** components for UI


## Author
[NoctRise](https://github.com/noctRise/)



