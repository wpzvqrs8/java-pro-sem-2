`folder structure = ├── data/`        
`...................│   ├── app/(all apps code )` 
`...................│   ├── data/(downloads/other apps local data , ex icons etc...)`
`...................│   └── media/(photo/video/documents/music)`
                   `main java file to be run (main.java)`

# lockscreen
- time 12/24hour format 
- swipe up(enter) animation  
- camera{`right-bottom`}(ask password then open camera app in mobile else show black screen)
- emergency phone{`left-bottom`}(redirect the call from the mobile port)

# homescreen `mainScene`
- top10px - time + space +wifi icon +mobile network icon +battery percentage (use file `@get_wifi_battery.java`)
- fixed bottom apps - {`phone` , `contacts` , `messages`}
- all home screen apps -  
  - 1-browser 
  - 2-youtube
  - 3-mail
  - 4-gallery( folder - /DCIM)
  - 5-calculator
  - 6-tic-tac-toe
  - 7-clock
  - 8-payment app
                  
- google search bar(when clicked takes to browser)
- google search bar(when clicked takes to browser opening url - youtube.com)
- google search bar(when clicked takes to browser opening url - mail.google.com )
- use media folder to store images to be imported from tne 

# phone call app

A JavaFX desktop application can control Android SIM-based calling through a locally hosted Android bridge app.  
The Android app exposes HTTP APIs over WiFi/Hotspot and uses Android Telecom APIs with `CALL_PHONE` permission.  
The desktop app sends requests like `/call?number=9876543210` to initiate calls from the connected phone.  
This enables desktop-based dialing, contacts access, call history, and SMS integration while using the user's real mobile number.  
The solution avoids external telecom providers and requires only a local network connection between laptop and phone.  
Future enhancements can include WebSocket realtime sync, incoming call popups, audio routing, and remote connectivity.
- 3 tabs
- 1)show recent calls (from calls table)
- 2)numpad
- 3)show favorites + all contacts sorted by name from database table - contacts
- when called  , add entry to the calls table , 

# contacts
- import from any online resource + add some dummy contacts of members to showcase calling and store in the database
- show random colored icon of a profile picture with initial of name in it 
- dial button in it will open a call app and dial the number by itself
- show sorted version with a sidebar to scroll on A-Z 0-9

# messages
- use database table to store and fetch messages  , ( encrypt/decrypt them if possible - base64/sha256.)
- layout =    
- = `| [profile_pic] name (number)...|`
- = `| whole message ................|`
- = `|...............................|`


# -------------------------------------------------------------------
# |    home page                                                    |
# -------------------------------------------------------------------

# browser
- use webview/webengine
- similar to chrome
- store entries of history in database 
- delete history button
- 

# mail
- use webview/webengine directly open url of mail

# youtube 
- use webview/webengine directly open url of youtube

# gallery
- show one by one all the images from the media folder in a grid format 

# Tic-Tac-Toe
- single player double player option (player-1/player2) names changable 
- use minimax for bot

# calculator
- normal calculator with  + - x / % ^
- show history stored in a txt file in data folder , directly store per line and show last 5 entries of history in the history tab in menu

# clock
- 4-tabs = clock/timer/world clock(from system time)/stop watch
- circle clock with showing 3 hands(use animation in fx) 
- timer will set timer of hh-mm-ss time form the drop down for all 
- show time from system - 3 by default - India  , usa , Russia
- stop watch start a new time object and will show its time difference from localtime.now() - startedtime() = timer time  hh-mm-ss

# payment app
- first time user store name in database  if not made account(empty file) ask details from user 
- default balance - 10000₹
- entries stored in the db
- ask user for payment = user_id search through database and if not find the user -> error 
- if correct payment_id generate otp  show in popup, store in the database  , confirm from database
- show profile tab from the database
- in transaction tab show all transaction of user via payment_id (query - group by payment_id)from the database
- when payment is done then update the balance in the user table 

