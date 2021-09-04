# AuroScholar MicroScholarship Android  library

![alt text](./AuroSholar.png "AuroScholar Logo")

### Requirements
- JDK 8
- Latest Android SDK tools
- Latest Android platform tools
- Android SDK 21 or newer
- AndroidX

### Configure
- ./keystore.properties
- Firebase - google-services.json


### Auro Scholar SDK Implementation Steps

Step 1 : Add the dependency in your app/build.gradle(:app)
----

```swift
dependencies {
	          implementation 'com.github.AuroScholar:microscholarship:1.2.4'

	}
```


Step 2 : Enable the databinding and add compileOptions in your app/build.gradle(:app) .
----

```swift

	android {
 		compileOptions {
 			sourceCompatibility = '1.8'
 			targetCompatibility = '1.8'
 		}
 		dataBinding {
 			enabled = true
 		}
	}

```
Step 3 : Add the JitPack repository in your root build.gradle at the end of repositories and then use authToken and username.
----

```swift

allprojects {
		repositories {
			maven { url 'https://jitpack.io' }
		}
	}

```


Step 4 : Add google gms service dependency in your build.gradle(Application).
----

```swift

 repositories {
 maven { url 'https://jitpack.io' }
 google()
 jcenter()

 }

 dependencies {
 classpath 'com.android.tools.build:gradle:3.6.2'
 classpath 'com.google.gms:google-services:+'
 }
}

```


Step 5 : For open ,AuroScholar Activity or Start the Auro Scholar sdk
----
```swift

AuroScholarInputModel inputModel= new AuroScholarInputModel();
// Important Params
inputModel.setMobileNumber("mobile number here"); //Mandatory
inputModel.setActivity(Activity Context Here); //Mandatory
inputModel.setStudentClass("put student class here"); //Mandatory
inputModel.setReferralLink("Put here branch or any other referral Link");
inputModel.setRegitrationSource("put your company unqiue id here"); //Mandatory
inputModel.setPartnerSource("Your Id here"); // This id provided by Auro Scholar to the partner.
inputModel.setPartnerLogoUrl(""); //Mandatory
inputModel.setSchoolName(""); //optional Field
inputModel.setBoardType("");//optional Filed
inputModel.setSchoolType(" ");//optional Filed
inputModel.setGender("");//optional Filed
inputModel.setEmail("");//optional Filed
inputModel.setPartnerName("");//Mandatory
//optional Filed
//This method start the SDK
AuroScholar.startAuroSDK(inputModel);



```
Step 6 : Add these lines in activity in onActivityResult( ) Method.
----

```swift

@Override
protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
 super.onActivityResult(requestCode, resultCode, data);
 //must param to get the acitivity
 for (Fragment fragment : getSupportFragmentManager().getFragments()) {
 fragment.onActivityResult(requestCode, resultCode, data);
 }
}

```


### Visit Our WebSite
https://auroscholar.com/

## AuroScholar MicroScholarship

Auro Scholar is the world's 1st Micro Scholarship program aimed at encouraging Indian students to improve their learning continuously by authenticating their learning levels (through Mobile Quizzes) &amp; identity (through Mobile Wallets), thereby creating mass incentives to learn.  At Auro Scholar platform, students can take curriculum aligned 10-min Mobile Quizzes at their preferred place and time. If you score 80% in a Quiz, you can get a scholarship of up to Rs 1000 every month! Students who score less than 80% can study and retake the Quizzes to win scholarships.  Auro Scholar is an initiative of Sri Aurobindo Society, Puducherry, which has a 60+ year legacy and 300+ centres around the world. The Society has a deep focus on Education Transformation, in addition to multiple other social objectives.


## License & copyright

© 2019 AuroScholar

Licensed under the [MIT License](LICENSE).
