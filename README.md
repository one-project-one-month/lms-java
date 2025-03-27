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

ကိုလင်းရဲ့ ဦးဆောင်မှုဖြင့် Learning Management System လေးကို တစ်လအတွင်း ရေးကြမယ်ဆိုပြီး စီစဥ်ခဲ့ကြပါတယ်။ ဒီ System လေးရဲ့ အဓိက ရည်ရွယ်ချက်တော့ သင်ကြားသူတွေရော ဆရာတွေပါ အဆင်ပြေစေမဲ့ learning platform တစ်ခု တည်ဆောင်ခြင်း ပဲဖြစ်ပါတယ်။ သင်ကြားသူတွေအနေနဲ့ course တွေကို အလွယ် တကူ category အလိုက်ရှာလို့ရမှာဖြစ်တဲ့အတွက် ကိုယ်လိုချင်တဲ့ course ကို enroll မြန်မြန် ဆန်ဆန် လုပ်လို့ရပါတယ်။ Instructor တွေကလဲ ကိုယ်သင်ချင်တဲ့ course တွေကို platform ပေါ်တင်ပြီး management လုပ်လို့ရပါတယ်။

### User
> User အချက်အလက်များသိမ်းရန်၊ Instructor, Student, Admin တို့ဖြင့် ချိတ်ဆက်ရန်

```
  user_Id       Int    
  username      String   
  email         String 
  password      String   
  phone         String   
  dob           DateTime  
  address       String
  image_url     String
  role_id       Int
  is_available  Boolean
  created_at    DateTime
  updated_at    DateTime
```
