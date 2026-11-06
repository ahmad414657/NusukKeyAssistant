# Nusuk Key Assistant (Android project)

এটি একটি সাধারণ সহায়ক অ্যাপের সোর্স প্রজেক্ট। এটি:
- Nusuk Code Bank লিংক খুলতে পারে;
- ব্যবহারকারী-প্রদত্ত Key কপি করতে পারে;
- Nusuk অ্যাপ চালু করতে পারে;
- ব্যবহারকারীর লেখা সফল/ব্যর্থ ফলাফল স্থানীয়ভাবে লগ করে।

## ইচ্ছাকৃত সীমাবদ্ধতা
অ্যাপটি ওয়েবসাইট থেকে স্বয়ংক্রিয়ভাবে কোড স্ক্র্যাপ করে না, Nusuk-এর UI-তে নিজে থেকে জমা দেয় না, এবং ধারাবাহিক/গণহারে সাবমিশন করে না। প্রতিটি কোড Nusuk-এ ব্যবহার করার সিদ্ধান্ত ও জমা দেওয়া ব্যবহারকারীর হাতে থাকে। CAPTCHA, নিরাপত্তা যাচাই বা সেবার সীমা এড়ানোর কোনো ব্যবস্থা নেই।

## APK তৈরি
1. কম্পিউটারে Android Studio (Hedgehog বা নতুন) ইনস্টল করুন।
2. Android Studio থেকে `NusukKeyAssistant` ফোল্ডারটি Open করুন।
3. Gradle sync শেষ হওয়া পর্যন্ত অপেক্ষা করুন।
4. Build > Build Bundle(s) / APK(s) > Build APK(s) নির্বাচন করুন।
5. `app/build/outputs/apk/debug/app-debug.apk` ফাইলে APK তৈরি হবে।
6. APK ফোনে পাঠিয়ে ইনস্টল করুন। প্রয়োজনে Android সেটিংসে ওই উৎস থেকে ইনস্টলের অনুমতি দিন।

## নোট
Nusuk অ্যাপের package name ভবিষ্যতে ভিন্ন হলে `MainActivity.kt`-এর `com.moh.nusukapp` মানটি সংশোধন করতে হতে পারে। এই প্রজেক্টে নেটওয়ার্ক বা অ্যাকাউন্ট লগইন অনুমতি চাওয়া হয় না।

## শুধু Android ফোন দিয়ে Cloud Build (কম্পিউটার ছাড়া)
1. GitHub অ্যাকাউন্ট তৈরি/লগইন করুন: https://github.com
2. GitHub-এ New repository তৈরি করুন (যেমন `NusukKeyAssistant`)।
3. এই ZIP Extract করে সব ফাইল ও `.github` ফোল্ডারসহ repository-তে আপলোড/কমিট করুন।
4. Repository-এর Actions ট্যাবে যান।
5. `Build Android APK` workflow নির্বাচন করে `Run workflow` চাপুন।
6. Build শেষ হলে workflow run-এর নিচে Artifacts থেকে `NusukKeyAssistant-debug-apk` ডাউনলোড করুন।
7. ZIP artifact খুলে `app-debug.apk` ফোনে ইনস্টল করুন।

GitHub-এর মোবাইল ওয়েবসাইটে ফাইল আপলোডের অভিজ্ঞতা ভিন্ন হতে পারে; `.github/workflows/build-apk.yml` ফাইলটি অবশ্যই repository-তে থাকতে হবে।
