# CareerFlow AI - Setup Instructions

## Quick Start Guide

Follow these steps to get CareerFlow AI up and running on your machine and deploy to Play Store.

---

## Part 1: Local Development Setup (Localhost in VS Code)

### Prerequisites
- Mac/Windows/Linux computer
- Git installed
- 10GB free space
- Stable internet connection

### Step 1: Install Required Software

**1.1 Install Android Studio**
```bash
# Download from: https://developer.android.com/studio
# Click Download button
# Follow installation wizard
# Open Android Studio and complete setup
```

**1.2 Install VS Code (Optional)**
```bash
# Download from: https://code.visualstudio.com
# Install and add extensions:
# - Kotlin Language
# - Android Gradle
# - Dart (for future React Native)
```

**1.3 Install Homebrew (Mac only)**
```bash
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
```

### Step 2: Clone Repository

```bash
# Open Terminal/Command Prompt
cd /path/to/projects
git clone https://github.com/Akshayakumar0207/CareerFlow-AI.git
cd CareerFlow-AI
```

### Step 3: Set Up Supabase (Free Tier)

```bash
# Go to: https://supabase.com
# 1. Click "Sign Up"
# 2. Choose "Email" option
# 3. Enter email and password
# 4. Click "Create new project"
# 5. Fill in:
#    - Project Name: CareerFlowAI
#    - Database Password: (create strong password)
#    - Region: (select closest to you)
# 6. Wait 2-3 minutes for project creation
# 7. Go to Settings → API
# 8. Copy and save:
#    - Project URL (Project URL)
#    - anon/public (ANON KEY)
```

### Step 4: Get Gemini API Key (Free)

```bash
# Go to: https://aistudio.google.com/
# 1. Sign in with Google account
# 2. Click "Get API Key"
# 3. Click "Create API key in Google Cloud"
# 4. Select default project
# 5. Copy the generated API key
# 6. Save it safely
```

### Step 5: Configure Local Properties

```bash
# In project root, copy example file:
cp local.properties.example local.properties

# Open local.properties in text editor
# Add your keys:
supabase.url=https://YOUR_PROJECT.supabase.co
supabase.anon_key=YOUR_ANON_KEY_HERE
gemini.api_key=YOUR_GEMINI_KEY_HERE

# For SDK path:
# Mac:
sdk.dir=/Users/YOUR_USERNAME/Library/Android/sdk
# Windows:
sdk.dir=C:\\Users\\YOUR_USERNAME\\AppData\\Local\\Android\\sdk
# Linux:
sdk.dir=/home/YOUR_USERNAME/Android/Sdk
```

### Step 6: Open in Android Studio

```bash
# Method 1: Command line
open -a "Android Studio" .

# Method 2: Using Android Studio UI
# 1. Open Android Studio
# 2. File → Open
# 3. Navigate to CareerFlow-AI folder
# 4. Click Open

# Wait for Gradle sync to complete
# (First time takes 5-10 minutes)
```

### Step 7: Run on Emulator

```bash
# 1. In Android Studio: Tools → Device Manager
# 2. Click "Create device"
# 3. Choose "Pixel 6 Pro" (or any Pixel phone)
# 4. Select API Level 34 (Android 14)
# 5. Click "Create"
# 6. Wait for emulator image to download
# 7. Click play button to start emulator

# Once emulator is running:
# 1. Click "Run" button (Shift + F10)
# 2. Select the running emulator
# 3. Click OK

# App should launch in emulator!
```

### Step 8: Test on Physical Device (Optional)

```bash
# On your Android phone:
# 1. Go to Settings → About Phone
# 2. Scroll to "Build Number"
# 3. Tap 7 times
# 4. Go back to Settings → Developer Options
# 5. Enable "USB Debugging"
# 6. Connect phone to Mac/PC via USB cable
# 7. Trust certificate when prompted

# In Android Studio:
# 1. Your device will appear in device dropdown
# 2. Click "Run" button
# 3. Select your device
# 4. Click OK

# App will install and run on your phone!
```

### Step 9: Development Workflow

```bash
# Make code changes
# Save file (Ctrl+S or Cmd+S)
# Click Run button (Shift+F10) or let auto-reload work

# View logs:
adb logcat | grep careerflow

# Debug:
# - Click line number to add breakpoint
# - Run → Debug (Shift+F9)
# - Step through code

# Build debug APK:
./gradlew assembleDebug
# Output: app/build/outputs/apk/debug/app-debug.apk
```

---

## Part 2: Deploy to Google Play Store

### Step 1: Create Google Play Account

```bash
# Go to: https://play.google.com/console
# 1. Sign in with Google account
# 2. Click "Create account" or "Register account"
# 3. Pay $25 one-time developer fee (credit card)
# 4. Accept Developer Agreement
# 5. Complete developer profile
# 6. Verify email
```

### Step 2: Generate Signing Key

```bash
# In Terminal/Command Prompt:
keytool -genkey -v -keystore careerflow-release.keystore \
  -keyalg RSA -keysize 2048 -validity 10000 -alias careerflow

# Enter passwords and info when prompted:
# Keystore password: (create strong password - REMEMBER THIS!)
# Key password: (same as keystore password)
# First/Last Name: Your Name
# Org Unit: Development
# Organization: Your Company
# City: Your City
# State: Your State
# Country: US (or your country code)

# File created: careerflow-release.keystore
# KEEP THIS FILE SAFE! You need it to update your app forever!
```

### Step 3: Configure Build for Release

```bash
# Edit: app/build.gradle.kts

# Find signingConfigs section, uncomment and update:
signingConfigs {
    release {
        storeFile = file("../careerflow-release.keystore")
        storePassword = "your-keystore-password"
        keyAlias = "careerflow"
        keyPassword = "your-key-password"
    }
}

# Find buildTypes release section, ensure it has:
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

# Update version:
defaultConfig {
    versionCode = 1
    versionName = "1.0.0"
}
```

### Step 4: Build Release Bundle

```bash
# Method 1: Android Studio
# 1. Build → Generate Signed Bundle/APK
# 2. Select "Android App Bundle"
# 3. Select keystore and key
# 4. Select release build variant
# 5. Click Finish
# 6. File opens at: app/release/app-release.aab

# Method 2: Command line
./gradlew bundleRelease
# Output: app/build/outputs/bundle/release/app-release.aab

# Verify it worked:
ls -lh app/build/outputs/bundle/release/app-release.aab
# Should show file with size ~15-20MB
```

### Step 5: Create App on Play Store

```bash
# In Play Console:
# 1. Click "Create app"
# 2. Fill in:
#    - App name: "CareerFlow AI"
#    - Default language: English
#    - App or game: App
#    - Free or paid: Free
# 3. Check "I confirm..."
# 4. Click "Create app"

# Go to App details
# 1. App name: CareerFlow AI
# 2. Short description (80 chars):
#    "AI job application tracker and interview prep"
# 3. Full description:
#    "CareerFlow AI helps job seekers manage their career journey.
#    Features:
#    • Track all job applications
#    • Schedule interviews
#    • AI-powered analysis
#    • Resume optimization
#    • Performance analytics"
# 4. Category: Productivity
# 5. Save
```

### Step 6: Add Screenshots

```bash
# Create screenshots using emulator:
# 1. Run app on emulator
# 2. Press Ctrl+S (Mac: Cmd+Shift+S)
# 3. Save to file
# 4. Crop to: 1440 × 2560 px
# 5. Create 4-5 screenshots showing:
#    - Dashboard
#    - Application list
#    - Interview tracking
#    - Analytics
#    - Settings

# Upload to Play Store:
# 1. App details → Graphic assets
# 2. Phone screenshots section
# 3. Upload 4-5 screenshots
# 4. Add descriptions (optional)
```

### Step 7: Add App Icon

```bash
# Create 512×512 PNG icon
# Upload to: App details → Graphic assets → App icon

# Or use existing:
# File: app/src/main/res/mipmap-xxxhdpi/ic_launcher_foreground.png
```

### Step 8: Add Privacy Policy

```bash
# Create privacy policy page:
# Use generator: https://termly.io/products/privacy-policy-generator/

# Or write basic one:
# "CareerFlow AI collects only essential data for functionality.
# - User email for authentication
# - Application data stored securely
# - No data sold to third parties
# - Users can delete data anytime"

# Upload URL to: App details → Privacy policy
```

### Step 9: Content Rating

```bash
# In Play Console:
# 1. Go to App content → Content rating
# 2. Click "Create questionnaire"
# 3. Answer questions (mostly "No" for productivity app)
# 4. Get rating certificate
```

### Step 10: Create Release

```bash
# In Play Console:
# 1. Go to Release → Production
# 2. Click "Create new release"
# 3. Upload app-release.aab file
# 4. Add release notes:
#    "Initial release of CareerFlow AI
#    • Application tracking
#    • Interview scheduling
#    • AI analysis
#    • Resume optimization
#    • Performance analytics"
# 5. Review and save
```

### Step 11: Submit for Review

```bash
# In Play Console:
# 1. Check all sections have green checkmarks
# 2. Go to Release page
# 3. Click "Review release"
# 4. Click "Start rollout to Production"
# 5. Confirm submission

# App goes to review queue!
# Typical review time: 2-3 hours
# Check email for approval notification
```

### Step 12: Monitor After Launch

```bash
# In Play Console, monitor:
# 1. Analytics → Crashes and ANRs
# 2. User feedback → Reviews
# 3. Vitals → Performance metrics

# Fix bugs and release updates:
# 1. Increment versionCode
# 2. Build new release bundle
# 3. Upload to Play Console
# 4. Repeat review process
```

---

## Troubleshooting

### Issue: "No SDK found"
```bash
# Solution:
# 1. File → Project Structure
# 2. SDK Location tab
# 3. Click Android SDK Location
# 4. Click checkbox for required SDK
# 5. Click Apply and OK
```

### Issue: "Gradle sync failed"
```bash
# Solution:
# 1. File → Invalidate Caches / Restart
# 2. Select "Invalidate and Restart"
```

### Issue: "Emulator not starting"
```bash
# Solution:
# 1. Close Android Studio
# 2. Delete emulator: ~/.android/avd/
# 3. Reopen Android Studio
# 4. Create new emulator
```

### Issue: "Play Store upload failed"
```bash
# Common reasons:
# 1. Not signed - use correct keystore
# 2. Privacy policy missing
# 3. App icon not uploaded
# 4. Duplicate package name
```

---

## Success Checklist

- [ ] Supabase account created
- [ ] Gemini API key obtained
- [ ] Code cloned to local machine
- [ ] local.properties configured
- [ ] App runs on emulator
- [ ] Signing key generated
- [ ] Release bundle built
- [ ] Play Store developer account created
- [ ] App details completed
- [ ] Screenshots uploaded
- [ ] Privacy policy added
- [ ] Release submitted for review
- [ ] App approved and live!

---

## Next Steps

1. **Gather User Feedback**
   - Monitor Play Store reviews
   - Fix reported issues

2. **Add More Features**
   - Gmail integration
   - Mock interviews
   - More analytics

3. **Marketing**
   - Create landing page
   - Share on social media
   - Get media coverage

4. **Monetization (Future)**
   - Premium features
   - Subscription model
   - In-app purchases

---

**Congratulations! Your app is now live on Google Play Store!** 🎉
