# CareerFlow AI - Complete Deployment Guide

## Table of Contents
1. [Local Development Setup](#local-development-setup)
2. [Android Studio Configuration](#android-studio-configuration)
3. [Running on Emulator](#running-on-emulator)
4. [Running on Physical Device](#running-on-physical-device)
5. [Building for Play Store](#building-for-play-store)
6. [Uploading to Play Store](#uploading-to-play-store)
7. [Post-Launch Monitoring](#post-launch-monitoring)

---

## Local Development Setup

### Step 1: Clone Repository
```bash
git clone https://github.com/Akshayakumar0207/CareerFlow-AI.git
cd CareerFlow-AI
```

### Step 2: Install Android Studio
1. Download from [developer.android.com](https://developer.android.com/studio)
2. Follow installation wizard
3. Choose: Android SDK, Android SDK Platform, Android Virtual Device

### Step 3: Configure JDK
Android Studio uses built-in JDK 17. Verify:
```bash
File → Project Structure → SDK Location
# Verify JDK is set to "bundled JDK"
```

### Step 4: Set Up Supabase

**Create Supabase Project:**
```bash
1. Go to https://supabase.com
2. Sign up with email/GitHub
3. Click "New Project"
4. Fill in:
   - Project Name: CareerFlowAI
   - Database Password: (secure password)
   - Region: (closest to you)
5. Wait for project creation (2-3 minutes)
```

**Copy Credentials:**
```bash
Project Settings → API
Copy:
- Project URL (supabase_url)
- anon/public key (supabase_anon_key)
```

### Step 5: Set Up Gemini API

```bash
1. Go to https://aistudio.google.com/
2. Click "Get API Key"
3. Click "Create API key in Google Cloud"
4. Copy the API key
```

### Step 6: Configure Project

**Create local.properties:**
```bash
cp local.properties.example local.properties
```

**Edit local.properties:**
```properties
# Supabase
supabase.url=https://your-project.supabase.co
supabase.anon_key=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...

# Gemini API
gemini.api_key=AIzaSyD...

# Gmail API (optional)
gmail.client_id=your-app.apps.googleusercontent.com

# Android SDK
sdk.dir=/Users/username/Library/Android/sdk
```

---

## Android Studio Configuration

### Step 1: Open Project
```bash
# Open in Android Studio
open -a "Android Studio" .

# Or from File menu:
# File → Open → Select CareerFlow-AI folder
```

### Step 2: Wait for Gradle Sync
- Android Studio will automatically sync Gradle
- Takes 3-5 minutes first time
- Shows progress at bottom

### Step 3: Configure SDK
```bash
# If SDK not installed:
1. File → Project Structure
2. SDK Location tab
3. Click "Android SDK Location"
4. Follow SDK Manager setup
5. Install:
   - Android SDK Platform 34
   - Android SDK Build-Tools 34.0.0
   - Emulator (optional)
```

### Step 4: Verify Dependencies
```bash
# Build project to check all dependencies
Build → Make Project

# Should complete without errors
```

---

## Running on Emulator

### Step 1: Create Virtual Device
```bash
# In Android Studio:
1. Tools → Device Manager
2. Click "Create device"
3. Select "Pixel 6 Pro" (recommended)
4. Choose API Level 34 (Android 14)
5. Click "Create"
```

### Step 2: Start Emulator
```bash
# In Device Manager, click play button next to device
# Or via command line:
adb devices  # List connected devices
```

### Step 3: Run App
```bash
# In Android Studio:
1. Select emulator from device dropdown
2. Click "Run" button (Shift + F10)

# Or via command line:
./gradlew installDebug
adb shell am start -n com.careerflow.ai/com.careerflow.ai.MainActivity
```

### Step 4: Debug App
```bash
# Debug logs:
adb logcat | grep careerflow

# Connect debugger:
Run → Debug (Shift + F9)

# Set breakpoints by clicking line numbers
```

---

## Running on Physical Device

### Step 1: Enable Developer Mode
```bash
# On Android Device:
Settings → About Phone
Find "Build Number"
Tap 7 times → "Developer options enabled"
```

### Step 2: Enable USB Debugging
```bash
Settings → Developer Options → USB Debugging → Enable
```

### Step 3: Connect Device
```bash
# Connect via USB cable
# Trust certificate when prompted

# Verify connection:
adb devices
# Should show your device
```

### Step 4: Run App
```bash
# In Android Studio:
1. Device dropdown will show your phone
2. Click "Run" button (Shift + F10)

# Or via command line:
./gradlew installDebug
```

### Step 5: Troubleshooting
```bash
# If device not showing:
adb kill-server
adb start-server
adb devices

# Restart device connection
```

---

## Building for Play Store

### Step 1: Generate Signing Key

```bash
# Create keystore file
keytool -genkey -v -keystore careerflow-release.keystore \
  -keyalg RSA -keysize 2048 -validity 10000 -alias careerflow

# Enter when prompted:
# Keystore password: (enter secure password)
# Key password: (same as keystore)
# First and Last Name: Your Name
# Organizational Unit: Development
# Organization: Your Company
# City: Your City
# State/Province: Your State
# Country Code: US (or your country code)
```

**Keep this keystore file SAFE! You'll need it forever.**

### Step 2: Configure Signing in Gradle

**Edit app/build.gradle.kts:**
```gradle
android {
    signingConfigs {
        release {
            storeFile = file("../careerflow-release.keystore")
            storePassword = "your-keystore-password"
            keyAlias = "careerflow"
            keyPassword = "your-key-password"
        }
    }

    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}
```

### Step 3: Update App Version

**Edit app/build.gradle.kts:**
```gradle
defaultConfig {
    applicationId = "com.careerflow.ai"
    minSdk = 24
    targetSdk = 34
    versionCode = 1
    versionName = "1.0.0"
}
```

### Step 4: Create Release Build

```bash
# Via Android Studio:
1. Build → Generate Signed Bundle/APK
2. Select "Android App Bundle"
3. Select keystore and key
4. Select "release" variant
5. Click "Finish"

# Via Command Line:
./gradlew bundleRelease
# Output: app/release/app-release.aab
```

### Step 5: Verify Release Build

```bash
# Test signed APK on device
./gradlew assembleRelease
./gradlew installRelease

# Check signing:
keytool -printcert -jarfile app/release/app-release.aab
```

---

## Uploading to Play Store

### Step 1: Create Google Play Developer Account

```bash
1. Go to https://play.google.com/console
2. Sign in with Google account
3. Pay one-time fee: $25
4. Agree to agreements
5. Complete developer profile
```

### Step 2: Create Application

```bash
1. Click "Create app" button
2. Fill in:
   - App name: "CareerFlow AI"
   - Default language: English
   - App or game: Select "App"
   - Free or paid: Select "Free"
3. Check required declarations
4. Click "Create app"
```

### Step 3: Complete App Details

**Navigate to:** App details section

```bash
1. App name: CareerFlow AI
2. Short description (80 chars):
   "AI-powered job application tracker and interview prep"

3. Full description (4000 chars):
   "CareerFlow AI helps job seekers manage their entire career journey.
   - Track all job applications
   - Schedule and manage interviews
   - AI-powered job analysis
   - Interview preparation
   - Performance analytics
   - Resume optimization
   - Gmail integration"

4. Category: Productivity
5. Content rating: Everyone
6. Target audience: Everyone
```

### Step 4: Upload Screenshots

**Required: Phone screenshots (min 2, max 8)**

```bash
1. Go to "Graphic assets" section
2. Phone screenshots:
   - Recommended: 1440 × 2560 px
   - Min 2, max 8 screenshots
   - Show:
     a) Dashboard/main screen
     b) Application list
     c) Analytics
     d) Interview management
     e) AI features

3. Add descriptions for each (max 80 chars)
```

### Step 5: Upload App Icon

```bash
1. Go to "Graphic assets"
2. App icon:
   - Dimensions: 512 × 512 px
   - Format: PNG
   - No rounded corners (Play Store adds them)
   - Upload in "Graphic assets" section
```

### Step 6: Feature Graphic

```bash
1. Go to "Graphic assets"
2. Feature graphic:
   - Dimensions: 1024 × 500 px
   - Format: PNG/JPG
   - Shows app highlight
   - Optional but recommended
```

### Step 7: Privacy Policy

```bash
1. Go to "App content"
2. Privacy policy URL: Add URL
   (You can use: https://careerflow-ai.example.com/privacy)

3. Template:
   - Explain what data you collect
   - How it's stored
   - Security measures
   - User rights
```

### Step 8: Content Rating

```bash
1. Go to "Content rating"
2. Complete questionnaire:
   - Violence: None
   - Sexual content: None
   - Profanity: None
   - Gambling: No
3. Get rating certificate
```

### Step 9: Upload Release

```bash
1. Go to "Release" → "Production"
2. Click "Create new release"
3. Upload:
   - app-release.aab (from bundleRelease)
4. Fill in release notes:
   "Initial release of CareerFlow AI
   - Complete application tracking
   - Interview scheduling
   - AI-powered analysis
   - Resume optimization
   - Performance analytics"
5. Review and submit
```

### Step 10: Submit for Review

```bash
1. Review all sections (green checkmarks)
2. Go to "Release" page
3. Click "Review release"
4. Click "Start rollout to Production"
5. Confirm submission
```

**Review Process:** 2-3 hours typically

---

## Post-Launch Monitoring

### Step 1: Monitor Reviews

```bash
# In Play Console:
1. Go to "User feedback" → "Reviews"
2. Read user reviews
3. Respond to feedback
4. Address bugs/issues
```

### Step 2: Track Analytics

```bash
# In Play Console:
1. Go to "Analytics"
2. Monitor:
   - Downloads
   - Uninstalls
   - Crashes
   - Rating
3. Identify trends
```

### Step 3: Monitor Crashes

```bash
# In Play Console:
1. Go to "Vitals" → "Crashes"
2. Check crash reports
3. Fix issues
4. Release update
```

### Step 4: Release Updates

```bash
# Increment versionCode in build.gradle.kts
versionCode = 2  # Was 1
versionName = "1.0.1"

# Build new release
./gradlew bundleRelease

# Upload to Play Console
# Follow same process as initial release
```

---

## Quick Reference Commands

```bash
# Build debug APK
./gradlew assembleDebug

# Build release APK
./gradlew assembleRelease

# Build App Bundle
./gradlew bundleRelease

# Run on emulator
./gradlew installDebug

# Run tests
./gradlew test

# Clean build
./gradlew clean

# Check dependencies
./gradlew dependencies

# Update dependencies
./gradlew --refresh-dependencies build

# View logcat
adb logcat

# List devices
adb devices

# Clear app data
adb shell pm clear com.careerflow.ai
```

---

## Troubleshooting

### Issue: "SDK not found"
```bash
# Solution:
1. File → Project Structure
2. Click SDK Location
3. Click Android SDK Location
4. Follow setup wizard
```

### Issue: "Gradle sync failed"
```bash
# Solution:
1. File → Invalidate Caches / Restart
2. Select "Invalidate and Restart"
3. Wait for project to reopen
```

### Issue: "Device not recognized"
```bash
# Solution:
adb kill-server
adb start-server
adb devices
```

### Issue: "Build failed: Keystore not found"
```bash
# Solution:
1. Verify path in build.gradle.kts
2. Path should be relative or absolute
3. Ensure file exists: careerflow-release.keystore
```

### Issue: "Play Store upload rejected"
```bash
# Common reasons:
1. Unsigned APK - must use valid keystore
2. Duplicate app - different package name needed
3. Privacy policy missing
4. Age rating needed
5. Screenshots required
```

---

## Success!

Your CareerFlow AI app is now live on Play Store! 🎉

Continuously monitor and update based on user feedback.