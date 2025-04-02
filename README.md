## Learning Management System - Java
> Learning Management System ရဲ့ ရည်ရွယ်ချက်ကတော့ Learner တွေ ပညာသင်ကြားနိုင်ပြီး Instructorများ lectureတွေ သင်ကြားပို့ချနိုင်တဲ့ platform တစ်ခုဖြစ်ပါတယ်

 ---------------------------------

ဒီ System ကို front-end နှင့် back-end ခွဲရေးခဲ့ပြီး အခု project ကိုတော့ back-end အနေနှင့် API endpoint ကို Java ဖြင့်ရေးခဲ့ကြပါတယ်

Tech Stack
- [API](https://lms-java-production.up.railway.app/) - Spring Boot, Java, MySQL

ဒီ project ရဲ့ Reposirtory လေးကိုတော့ ဒီ [Link](https://github.com/one-project-one-month/lms-java.git) ကနေ ကြည့်လို့ရပါတယ်

---------------------------------

LMS တွင် ပါဝင်သော Table များ

1. User
2. Student
3. Instructor
4. Admin
5. Role
6. Token
7. Category
8. Enrollment
9. Course
10. Lesson
11. Social Link

## Contributors

### Java Team
<table>
 <thead>
  <tr>
   <th colspan="12">Contributors</th>
  </tr>
 </thead>
    <tbody>
        <tr>
           <td><a href="https://github.com/thanthtooaung-coding"><img src="https://avatars.githubusercontent.com/u/148937860?v=4" width="60px;"/></a></td>
           <td><a href="https://github.com/QuarrMMTK"><img src="https://avatars.githubusercontent.com/u/134800254?v=4" width="60px;"/></a></td>
           <td><a href="https://github.com/Myintzu28"><img src="https://avatars.githubusercontent.com/u/120035328?v=4" width="60px;"/></a></td>
           <td><a href="https://github.com/linnmyatmaung"><img src="https://avatars.githubusercontent.com/u/179186794?v=4" width="60px;"/></a></td>
           <td><a href="https://github.com/HtetShine1868"><img src="https://avatars.githubusercontent.com/u/156557746?v=4" width="60px;"/></a></td>
           <td><a href="https://github.com/HlanHtetKyaw"><img src="https://avatars.githubusercontent.com/u/133634944?s=96&v=4" width="60px;"/></a></td>
        </tr>
    </tbody>
</table>

Description

Learning Management System လေးကို တစ်လအတွင်း ရေးကြမယ်ဆိုပြီး စီစဥ်ခဲ့ကြပါတယ်။ ဒီ System လေးရဲ့ အဓိက ရည်ရွယ်ချက်တော့ သင်ကြားသူတွေရော ဆရာတွေပါ အဆင်ပြေစေမဲ့ learning platform တစ်ခု တည်ဆောင်ခြင်း ပဲဖြစ်ပါတယ်။ သင်ကြားသူတွေအနေနဲ့ course တွေကို အလွယ် တကူ category အလိုက်ရှာလို့ရမှာဖြစ်တဲ့အတွက် ကိုယ်လိုချင်တဲ့ course ကို enroll မြန်မြန် ဆန်ဆန် လုပ်လို့ရပါတယ်။ Instructor တွေကလဲ ကိုယ်သင်ချင်တဲ့ course တွေကို platform ပေါ်တင်ပြီး management လုပ်လို့ရပါတယ်။


## Project တွင်ပါဝင်သော Table များ


### User
> User အချက်အလက်များသိမ်းရန်၊ Instructor, Student, Admin တို့ဖြင့် ချိတ်ဆက်ရန်

```
  user_Id        int    
  username       String   
  email          String 
  password       String   
  phone          String   
  dob            DateTime  
  address        String
  image_url      String
  role_id        Int
  is_available   boolean
  created_at     DateTime
  updated_at     DateTime
```

### Student
> Student အချက်အလက်များသိမ်းရန်၊ User ဖြင့် ချိတ်ဆက်ရန်

```
  student_Id        int    
  name              String   
```

### Instructor
> Instructor အချက်အလက်များသိမ်းရန်၊ User ဖြင့် ချိတ်ဆက်ရန်

```
  instructor_Id     int    
  nrc               String
  edu_background    String
```

### Admin
> Admin အချက်အလက်များသိမ်းရန်၊ User ဖြင့် ချိတ်ဆက်ရန်

```
  id     int    
```

### Token
> Refresh Token အချက်အလက်များသိမ်းရန်၊ User ဖြင့် ချိတ်ဆက်ရန်

```
  token_Id       int    
  user_Id        int
  token          String
  started_date   DateTime
  expired_date   DateTime
```

### Category
> Category အချက်အလက်များသိမ်းရန်၊ Course ဖြင့် ချိတ်ဆက်ရန်

```
  category_Id      int    
  course_Id        int
  name             String
```

### Enrollment
> Enrollment အချက်အလက်များသိမ်းရန်၊ User,Course တို့ဖြင့် ချိတ်ဆက်ရန်

```
  enrollment_id    int
  user_Id          int    
  course_Id        int
  name             String
```

### Course
> Course အချက်အလက်များသိမ်းရန်၊ Instructor,Socail Link,Category တို့ဖြင့် ချိတ်ဆက်ရန်

```
  course_id          int
  instructor_Id      int    
  course_name        int
  thumbnail          String
  is_available       boolean
  type               String
  level              String
  social_link_id     String
  description        String
  duration           DateTime
  original_price     int
  current_price      int
  category_id        int
  created_at         DateTime
  updated_at         DateTime
```

### Lesson
> Lesson အချက်အလက်များသိမ်းရန်၊ Course ဖြင့် ချိတ်ဆက်ရန်

```
  lesson_id          int
  course_Id          int    
  title              String
  video_url          String
  lesson_detail      String
  is_available       boolean
  created_at         DateTime
  updated_at         DateTime
```

### Social Link
> Social Link အချက်အလက်များသိမ်းရန်၊ Course ဖြင့် ချိတ်ဆက်ရန်

```
  social_id          int
  course_Id          int    
  facebook           String
  x                  String
  telegram           String
  phone              String
  email              String
```

