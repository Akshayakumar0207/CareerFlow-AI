# CareerFlow AI - Android Application

## Overview
CareerFlow AI is a comprehensive Android application designed to help job seekers manage their entire job application journey using AI-powered insights and automation.

## Features
- 📋 **Application Tracking**: Track all job applications in one place
- 🗓️ **Interview Management**: Schedule and manage interview rounds
- 🤖 **AI Analysis**: Get insights on job descriptions and resume optimization
- 📊 **Analytics**: View success rates and performance metrics
- 📧 **Gmail Integration**: Auto-sync emails from recruiters
- 🔔 **Smart Notifications**: Reminders for upcoming interviews
- 💾 **Offline Support**: All data synced with Supabase

## Tech Stack
- **Language**: Kotlin
- **UI Framework**: Jetpack Compose with Material 3
- **Architecture**: MVVM + Clean Architecture
- **Database**: Room (local) + Supabase PostgreSQL (cloud)
- **DI**: Hilt
- **AI**: Google Gemini API (free tier)
- **Authentication**: Supabase Auth
- **Async**: Coroutines + StateFlow

## Prerequisites
- Android Studio 2023.1 or higher
- JDK 17
- Android SDK 24+ (minSdkVersion)
- Supabase account (free tier)
- Google Gemini API key
- Gmail API credentials (optional)

## Installation

### 1. Clone the Repository
```bash
git clone https://github.com/Akshayakumar0207/CareerFlow-AI.git
cd CareerFlow-AI
```

### 2. Set Up Supabase
1. Go to [supabase.com](https://supabase.com) and create a free account
2. Create a new project
3. In Project Settings, copy:
   - Project URL
   - Anon/Public API Key

### 3. Set Up Gemini API
1. Go to [Google AI Studio](https://aistudio.google.com/)
2. Get your free API key

### 4. Configure Local Properties
```bash
cp local.properties.example local.properties
```

Edit `local.properties`:
```properties
supabase.url=https://your-project.supabase.co
supabase.anon_key=your-anon-key
gemini.api_key=your-gemini-key
sdk.dir=/path/to/android/sdk
```

### 5. Build and Run
```bash
# Open in Android Studio
open -a "Android Studio" .

# Or build via command line
./gradlew build
./gradlew installDebug
```

## Project Structure
```
app/src/main/java/com/careerflow/ai/
├── data/
│   ├── local/           # Room Database
│   ├── remote/          # Supabase, Gemini, Gmail APIs
│   └── repository/      # Repository Pattern
├── domain/
│   └── model/           # Business Logic Models
├── presentation/
│   ├── ui/              # Jetpack Compose Screens
│   ├── viewmodel/       # MVVM ViewModels
│   └── theme/           # Material 3 Theme
├── di/                  # Hilt DI Configuration
└── MainActivity.kt      # Entry Point
```

## Database Schema

### Room Entities
- **users**: User profiles
- **applications**: Job applications
- **interviews**: Interview schedules
- **interview_feedback**: Interview feedback
- **resume_analysis**: Resume analysis results
- **job_analysis**: Job description analysis
- **notifications**: Push notifications
- **offers**: Job offers
- **rejections**: Rejection analysis

### Supabase Tables
Same schema as Room entities for cloud sync

## API Integration

### Supabase
- Authentication: Email/Password + Google Sign-In
- Database: PostgreSQL (500MB free)
- Storage: File upload (1GB free)

### Gemini API
- Job description analysis
- Resume analysis
- Mock interview question generation
- Feedback analysis

### Gmail API (Optional)
- Auto-detect application confirmations
- Track interview schedules
- Identify offer letters

## Building for Release

### Generate Signing Key
```bash
keytool -genkey -v -keystore release.keystore \
  -keyalg RSA -keysize 2048 -validity 10000 \
  -alias careerflow-key
```

### Configure Signing
Edit `app/build.gradle.kts`:
```gradle
signingConfigs {
    release {
        storeFile file('path/to/release.keystore')
        storePassword 'your-password'
        keyAlias 'careerflow-key'
        keyPassword 'your-password'
    }
}

buildTypes {
    release {
        signingConfig signingConfigs.release
        minifyEnabled true
        shrinkResources true
        proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
    }
}
```

### Build Release APK/AAB
```bash
# APK
./gradlew assembleRelease

# AAB (Recommended for Play Store)
./gradlew bundleRelease
```

## Deployment to Play Store

### 1. Create Play Store Account
- Go to [Google Play Console](https://play.google.com/console)
- Sign up and create a developer account ($25 one-time fee)
- Accept agreements

### 2. Create App
- Click "Create app"
- Fill in app details:
  - App name: "CareerFlow AI"
  - Default language: English
  - App type: Apps
  - Category: Productivity

### 3. Prepare Store Listing
- Screenshots (min 2, max 8)
- Feature graphic (1024 × 500 px)
- App icon (512 × 512 px)
- Short description (80 chars)
- Full description (4000 chars)
- Privacy policy URL

### 4. Set Up Release
1. Go to "Release" → "Create new release"
2. Select "Production" track
3. Upload `app-release.aab`
4. Review release details
5. Submit for review

### 5. Monitor Review Process
- Review takes 2-3 hours typically
- Check Play Console for updates
- App will be live once approved

## Development Guidelines

### Code Style
- Use Kotlin conventions
- Follow Clean Architecture principles
- Use ViewModels for state management
- Reactive programming with Flow/StateFlow

### Git Workflow
```bash
# Create feature branch
git checkout -b feature/your-feature

# Commit changes
git add .
git commit -m "Add: your feature description"

# Push to remote
git push origin feature/your-feature

# Create Pull Request
```

## Troubleshooting

### Build Issues
```bash
# Clean build
./gradlew clean build

# Update dependencies
./gradlew --refresh-dependencies build
```

### Gradle Sync Failed
- File → Sync Now
- Check SDK path in local.properties
- Update Android Studio

### Deployment Issues
- Verify signing key
- Check app version code/name
- Ensure all permissions declared
- Test on emulator first

## Support
For issues and feature requests, open an issue on GitHub.

## License
MIT License - See LICENSE file

## Contributors
Akshaya Kumar (@Akshayakumar0207)

## Changelog

### v1.0.0 - Initial Release
- Complete application tracking
- Interview scheduling
- AI-powered analysis
- Gmail integration
- Push notifications
- Analytics dashboard